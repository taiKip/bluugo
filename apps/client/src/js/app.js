const uploadButton = document.getElementById("upload-button");
const fileList = document.getElementById("file-list");
const error = document.getElementById("error");
const uploadForm = document.getElementById("upload-form");
const submitButton = document.getElementById("submit-button");
const searchBar = document.getElementById("search-bar");
const searchInput = document.getElementById('search-input')
let tableBody = document.getElementById("table-body");
let timer;
const interval = 500;
let page;


const BASE_URL = "http://localhost:8080/api/v1";
const state = {
  loading: false,
  error: null,
  searchTerm:"",
  fetchedData: null,
  deletedFiles: [],
};
window.onload = async () => {
  fetchData();
};

searchInput.addEventListener('keyup',handleSearch)
uploadButton.addEventListener("change", handleFileUpload);
uploadForm.addEventListener("dragenter", handleDragEnter, false);
uploadForm.addEventListener("dragover", handleDragOver, false);
uploadForm.addEventListener("dragleave", handleDragLeave, false);
uploadForm.addEventListener("drop", handleDrop, false);
uploadForm.addEventListener("submit", handleSubmit);

function handleSearch(event) {
 clearTimeout(timer)
  const searchTerm = event.target.value;
  console.log("Search Term::",searchTerm)
  timer = setTimeout(() => liveSearch( searchTerm), interval);
 
}


const liveSearch = ( searchTerm) => {
  console.log("Search called")
  let data = [...state.fetchedData]


    const filtered = data.filter(
      (carModel) => {
        console.log(searchTerm.replace(/\s/g, ""));
        const makeModel = carModel.make + carModel.model;
        return (
          carModel.modelYear.includes(searchTerm) ||
          carModel.make.toLowerCase().includes(searchTerm.toLowerCase()) ||
          carModel.model.toLowerCase().includes(searchTerm.toLowerCase()) ||
          makeModel.toLowerCase().includes(searchTerm.replace(/\s/g, ""))
        );
      
      }
      
    );
   
      renderTable(filtered);
 
}



//file upload
function handleFileUpload(event) {
  Array.from(uploadButton.files).forEach(fileHandler);
}

function fileHandler(file) {
  fileList.innerHTML = "";

  if (!isValidJsonFile(file)) {
    displayError("Please upload a json file");
    return;
  }

  clearError();
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

  textSpan.innerHTML = fileName;
  closeButton.innerHTML = "x";
  closeButton.className = "close";
  closeButton.onclick = handleRemove;

  listItem.appendChild(textSpan);
  listItem.appendChild(closeButton);

  return listItem;
}

function showSubmitButton() {
  submitButton.classList.add("show");
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
  Array.from(event.dataTransfer.files).forEach(fileHandler);
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
    uploadButton.style.display = "none";
  }

 const files =  allFiles
    .filter((file) => !state.deletedFiles.includes(file.name))
  uploadFiles(files);
console.log("Files::",files)
  hideSubmitButton();
}
async function uploadFiles(files) {
  try {
     tableBody.innerHTML = "";
    const formData = new FormData();
    for (let i = 0; i < files.length; i++){
      formData.append('files',files[i])
    }
    const response = await fetch(`${BASE_URL}/car-models/upload`, {
      method: 'POST',
      body:formData
    })
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
     tableBody.innerHTML = "";
  } catch (error) {
      console.error("There was a problem with the fetch operation:", error);
  } finally {
    fetchData();
  }
}


function hideSubmitButton() {
  submitButton.classList.remove("show");
}

async function fetchData() {
  try {
    state.loading = true;
     tableBody.innerHTML = "<span>Loading...</span>";
    const response = await fetch(`${BASE_URL}/car-models`);

    if (!response.ok) {
      throw new Error("Network response was not ok");
    }

    const data = await response.json();
   
    if (data) {
      state.loading = false;
  state.fetchedData = data.content;
    }
 tableBody.innerHTML = "";

renderTable(state.fetchedData);
  } catch (error) {
    console.error("There was a problem with the fetch operation:", error);
    state.error = error;
    return null;
  } finally {
    state.loading = false;
   
  }
  
}


const renderTable = (dataItems) => {
  tableBody.innerHTML = "";
  
  console.log("Render table called::")
  dataItems.map(dataItem=>renderTableItems(dataItem))
}
/**
 * 
 * @param {*} dataItem 
 * @description render table takes in object of type carmodel:{
 * modelYear:string,
 * make:string,
 * model:string,
 * rejectionPercentage:string,
 * reasons:string[]
 * }
 * @todo fix table using css instead of  extra arr.
 */
const renderTableItems = (dataItem) => {
  const newRow = document.createElement("tr");
  let reasonsArr;
  const { modelYear, make, model, rejectionPercentage, reasons } = dataItem;
  reasonsArr = reasons.length>1?reasons:["","",""]
  newRow.appendChild(createNewCell(modelYear));
  newRow.appendChild(createNewCell(make));
  newRow.appendChild(createNewCell(model));
  newRow.appendChild(createNewCell(rejectionPercentage));
    reasonsArr.forEach(reason => newRow.appendChild(createNewCell(reason)));
  tableBody.appendChild(newRow);
};

const createNewCell = (value) => {
  const newCell = document.createElement("td");
  newCell.textContent = value || "";
  return newCell;
};
