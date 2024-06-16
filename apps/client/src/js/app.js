
const BASE_URL = "http://localhost:8080/api/v1";
const INTERVAL = 500;


const uploadButton = document.getElementById("upload-button");
const fileList = document.getElementById("file-list");
const error = document.getElementById("error");
const uploadForm = document.getElementById("upload-form");
const submitButton = document.getElementById("submit-button");
const searchInput = document.getElementById("search-input");
const statusBar = document.getElementById("main__status-bar");
const tableBody = document.getElementById("table-body");
const player = document.getElementById("app__player");


const state = {
  loading: false,
  error: null,
  searchTerm: "",
  fetchedData: null,
  deletedFiles: [],
};

let timer;

window.onload = fetchData;
searchInput.addEventListener("keyup", handleSearch);
uploadButton.addEventListener("change", handleFileUpload);
uploadForm.addEventListener("dragenter", handleDragEnter, false);
uploadForm.addEventListener("dragover", handleDragOver, false);
uploadForm.addEventListener("dragleave", handleDragLeave, false);
uploadForm.addEventListener("drop", handleDrop, false);
uploadForm.addEventListener("submit", handleSubmit);

// Function Definitions

function handleSearch(event) {
  clearTimeout(timer);
  const searchTerm = event.target.value;
  timer = setTimeout(() => liveSearch(searchTerm), INTERVAL);
}

function liveSearch(searchTerm) {
  if (!state.fetchedData) return;

  const filtered = state.fetchedData.filter((carModel) => {
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
  Array.from(uploadButton.files).forEach((file) => fileHandler(file));
}

function fileHandler(file) {
  clearError();
  fileList.innerHTML = "";

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
  error.innerText = message;
}

function clearError() {
  error.innerText = "";
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

function showStatusBar(text, className) {
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
  }, INTERVAL);
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
  state.deletedFiles.push(li.firstElementChild.innerText);
  li.remove();
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

  const files = allFiles.filter(
    (file) => !state.deletedFiles.includes(file.name)
  );
  uploadFiles(files);
  hideSubmitButton();
}

async function uploadFiles(files) {
  try {
    showStatusBar("Uploading...", "loading");

    const formData = new FormData();
    files.forEach((file) => formData.append("files", file));

    const response = await fetch(`${BASE_URL}/car-models/upload`, {
      method: "POST",
      body: formData,
    });

    if (!response.ok) {
      throw new Error("Network response was not ok");
    }

    tableBody.innerHTML = "";
    showStatusBar("Files uploaded successfully!", "success");
    showSuccessGif();
  } catch (error) {
    console.error("There was a problem with the fetch operation:", error);
  } finally {
    fetchData();
    hideSuccessGif();
  }
}

async function fetchData() {
  try {
    state.loading = true;
    showStatusBar("Loading...", "loading");

    const response = await fetch(`${BASE_URL}/car-models`);
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }

    const data = await response.json();
    state.fetchedData = data.content;

    renderTable(state.fetchedData);
    hideStatusBar();
  } catch (error) {
    console.error("There was a problem with the fetch operation:", error);
    state.error = error;
  } finally {
    state.loading = false;
  }
}

function renderTable(dataItems) {
  tableBody.innerHTML = "";

  if (dataItems.length === 0) {
    showStatusBar("No records available currently", "loading");
    return;
  }

  dataItems.forEach((dataItem) => renderTableItem(dataItem));
}

function renderTableItem(dataItem) {
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
