const BASE_URL = "http://localhost:8080/api/v1";
const INTERVAL = 500;
const REFECTCHINTERVAL = 5000;

const uploadButton = document.getElementById("upload-button");
const fileList = document.getElementById("file-list");
const error = document.getElementById("error");
const uploadForm = document.getElementById("upload-form");
const submitButton = document.getElementById("submit-button");
const searchInput = document.getElementById("search-input");
const statusBar = document.getElementById("main__status-bar");
const tableBody = document.getElementById("table-body");
const player = document.getElementById("app__player");
const nextButton = document.getElementById("next");
const prevButton = document.getElementById("prev");
const pageNo = document.getElementById("count");

let loading = false;
let fetchError = null;
let searchTerm = "";
let fetchedData = [];
let deletedFiles = [];
let currentPage = 1;
let hasNextPage = false;
let hasPrevPage = false;
let pageSize = 50;
prevButton.ariaDisabled = false;

let timer;

let refetch;

window.onload = () => {
  fetchData();
};
searchInput.addEventListener("keyup", handleSearch);
uploadButton.addEventListener("change", handleFileUpload);
uploadForm.addEventListener("dragenter", handleDragEnter, false);
uploadForm.addEventListener("dragover", handleDragOver, false);
uploadForm.addEventListener("dragleave", handleDragLeave, false);
uploadForm.addEventListener("drop", handleDrop, false);
uploadForm.addEventListener("submit", handleSubmit, false);
nextButton.addEventListener("click", handleNext, false);
prevButton.addEventListener("click", handlePrev, false);

function handleNext() {
  if (currentPage * pageSize < fetchedData.length) {
    currentPage++;

    renderTable(fetchedData);
  }
}
function handlePrev() {
  if (currentPage > 1) {
    currentPage--;
    renderTable(fetchedData);
  }
}

function handleSearch(event) {
  clearTimeout(timer);
  const searchTerm = event.target.value;
  timer = setTimeout(() => liveSearch(searchTerm), INTERVAL);
}

function liveSearch(searchTerm) {
  if (!fetchedData) return;

  const filtered = fetchedData.filter((carModel) => {
    const makeModel = `${carModel.make}${carModel.model}`.toLowerCase();
    const term = searchTerm.toLowerCase().replace(/\s/g, "");
    return (
      carModel.modelYear.includes(searchTerm) ||
      carModel.make.toLowerCase().includes(term) ||
      carModel.model.toLowerCase().includes(term) ||
      makeModel.includes(term)
    );
  });

  renderTable(filtered);
}

function handleFileUpload() {
  showSubmitButton();
  const arr = Array.from(uploadButton.files);
  const files = Array.from(new Set(arr));
  files.forEach((file) => fileHandler(file));
  if (Array.from(uploadButton.files).length < 1) {
    hideSubmitButton();
  }
}

function fileHandler(file) {
  clearError();

  if (!isValidJsonFile(file)) {
    displayError("Please upload a JSON file");
    return;
  }

  readFile(file);
}

function isValidJsonFile(file) {
  return file.type.split("/")[1] === "json";
}

function displayError(message) {
  error.style.display = "block";
  error.innerText = message;
}

function clearError() {
  error.innerText = "";
  error.style.display = "none";
}

function readFile(file) {
  const fileReader = new FileReader();
  fileReader.readAsDataURL(file);
  fileReader.onloadend = () => addFileToList(file);
}

function addFileToList(file) {
  const listItem = createListItem(file.name);
  fileList.appendChild(listItem);
  showSubmitButton();
}

function createListItem(fileName) {
  const listItem = document.createElement("li");
  const textSpan = document.createElement("span");
  const closeButton = document.createElement("button");

  textSpan.innerText = fileName;
  closeButton.innerText = "x";
  closeButton.className = "close";
  closeButton.onclick = handleRemove;

  listItem.appendChild(textSpan);
  listItem.appendChild(closeButton);

  return listItem;
}

function showStatusBar(text, className, where = "") {
  statusBar.textContent = text;
  statusBar.className = className;
  statusBar.style.display = "block";
}

function hideStatusBar() {
  statusBar.style.display = "none";
}

function showSuccessGif() {
  player.style.display = "block";
}

function hideSuccessGif() {
  setTimeout(() => {
    player.style.display = "none";

    fetchData();
  }, 3000);
}

function showSubmitButton() {
  submitButton.style.display = "block";
}

function hideSubmitButton() {
  submitButton.style.display = "none";
}

function handleRemove(event) {
  const button = event.target;
  const li = button.parentElement;
  const ul = li.parentElement;
  deletedFiles.push(li.firstElementChild.innerText);
  li.remove();
  if (!ul.hasChildNodes()) {
    hideSubmitButton();
    clearError();
  }
}

function handleDragEnter(event) {
  preventDefaults(event);
  uploadForm.classList.add("upload-form--active");
}

function handleDragOver(event) {
  preventDefaults(event);
  uploadForm.classList.add("upload-form--active");
}

function handleDragLeave(event) {
  preventDefaults(event);
  uploadForm.classList.remove("upload-form--active");
}

function handleDrop(event) {
  preventDefaults(event);
  Array.from(event.dataTransfer.files).forEach((file) => fileHandler(file));
  if (Array.from(event.dataTransfer.files).length > 0) {
    showSubmitButton();
  }
}

function preventDefaults(event) {
  event.preventDefault();
  event.stopPropagation();
}

function handleSubmit(event) {
  event.preventDefault();
  const formData = new FormData(uploadForm);
  const allFiles = formData.getAll("fileInput");

  if (allFiles.length === 0) {
    hideSubmitButton();
    return;
  }

  const files = allFiles.filter((file) => !deletedFiles.includes(file.name));
  uploadFiles(files);
}

async function uploadFiles(files) {
  hideSubmitButton();
  try {
    showStatusBar("Uploading...", "loading");

    const formData = new FormData();
    files.forEach((file) => formData.append("files", file));

    const response = await fetch(`${BASE_URL}/car-models/upload`, {
      method: "POST",
      body: formData,
    });
    hideStatusBar();
    if (!response.ok) {
      throw new Error("Something went wrong");
    }

    tableBody.innerHTML = "";
    showStatusBar("Files uploaded successfully!", "success", "251");
    showSuccessGif();
    fileList.innerHTML = "";
  } catch (error) {
    console.error("Failed to upload");
    displayError("Failed to upload files");
  } finally {
    hideSuccessGif();
  }
}

async function fetchData() {
  try {
    loading = true;
    showStatusBar("Loading...", "loading");

    const response = await fetch(`${BASE_URL}/car-models`);
    if (!response.ok) {
      throw new Error("Something went wrong..");
    }
    hideStatusBar();
    const data = await response.json();

    if (data && response.ok && data.content.length < 1) {
      showStatusBar(`You currently have no records saved `, "success", "263");
      console.log("No content");
    } else {
      const { content, pageNo, pageSize, hasPrev, hasNext } = data;
      fetchedData = content;
      hasNextPage = hasNext;
      hasPrevPage = hasPrev;

      renderTable(fetchedData);
      tableBody.classList.add("animate");
      hideStatusBar();
    }
  } catch (error) {
    showStatusBar(`${error.message} data`, "error");
    error = error;
  } finally {
    loading = false;
  }
}

function renderTable(dataItems) {
  tableBody.innerHTML = "";

  if (dataItems.length === 0) {
    showStatusBar("No records available currently", "loading");
    return;
  }

  dataItems
    .filter((row, index) => {
      let start = (currentPage - 1) * pageSize;
      let end = currentPage * pageSize;

      if (index >= start && index < end) return true;
    })
    .forEach((dataItem) => renderTableItem(dataItem));
}

function renderTableItem(dataItem) {
  if (currentPage == 1) {
    prevButton.ariaDisabled = true;
    prevButton.style.background = "grey";
  } else {
    prevButton.ariaDisabled = false;
    prevButton.style.background = "";
  }
  if (Math.ceil(fetchedData.length / pageSize) == currentPage) {
     nextButton.ariaDisabled = true;
     nextButton.style.background = "grey";
  } else {
     nextButton.ariaDisabled = true;
     nextButton.style.background = "";
  }

  pageNo.innerText = currentPage;
  const newRow = document.createElement("tr");
  const { modelYear, make, model, rejectionPercentage, reasons } = dataItem;

  const reasonsArr = reasons.length > 1 ? reasons : ["", "", ""];
  newRow.appendChild(createNewCell(modelYear));
  newRow.appendChild(createNewCell(make));
  newRow.appendChild(createNewCell(model));
  newRow.appendChild(createNewCell(rejectionPercentage));
  reasonsArr.forEach((reason) => newRow.appendChild(createNewCell(reason)));

  tableBody.appendChild(newRow);
}

function createNewCell(value) {
  const newCell = document.createElement("td");
  newCell.textContent = value || "";
  return newCell;
}
