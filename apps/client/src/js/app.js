const uploadButton = document.getElementById("upload-button");
const fileList = document.getElementById("file-list");
const error = document.getElementById("error")
const uploadForm = document.getElementById("upload-form");
const close = document.getElementsByClassName('close');
const nodeList = document.getElementsByTagName('li');
const submitButton = document.getElementById("submit-button")
const deleted = [];
uploadButton.addEventListener('change', (e) => {
 
    Array.from(uploadButton.files).forEach(file => {
        fileHandler(file)
    })
})


const fileHandler = (file) => {
    fileList.innerHTML=""
    if (file.type.split("/")[1] !== 'json') {
        error.innerText = "Please upload a json file";
        return false;
    }
    error.innerText = "";
    const fileReader = new FileReader();
    fileReader.readAsDataURL(file);
    fileReader.onloadend = () => {
        let listItem = document.createElement("li");
        let closeButton= document.createElement("button");
        let textSpan = document.createElement("span")

        closeButton.innerHTML = "x";
        closeButton.className = "close";
        closeButton.onclick = handleRemove;
        textSpan.innerHTML = file.name;
        listItem.appendChild(textSpan)
        listItem.appendChild(closeButton)
        fileList.appendChild(listItem)
       submitButton.classList.add("show")
    }
}
const handleRemove = (e) => {
    const button = e.target;
    const li = button.parentElement;
    deleted.push(li.firstElementChild.innerText);
    li.remove();
}
uploadForm.addEventListener('dragenter', (e) => {
    e.preventDefault();
    e.stopPropagation();
    uploadForm.classList.add("upload-form--active");
}, false)
uploadForm.addEventListener(
  "dragover",
  (e) => {
    e.preventDefault();
    e.stopPropagation();
    uploadForm.classList.add("upload-form--active");
  },
  false
);
uploadForm.addEventListener(
  "dragleave",
  (e) => {
    e.preventDefault();
    e.stopPropagation();
    uploadForm.classList.remove("upload-form--active");
  },
  false
);
uploadForm.addEventListener("drop", (e) => {
     e.preventDefault();
    e.stopPropagation();
    Array.from(e.dataTransfer.files).forEach(file => {
        fileHandler(file)
    })
}, false)

uploadForm.addEventListener("submit", function (e) {
    e.preventDefault();
    const formData = new FormData(this);
    const files = formData.getAll('fileInput')
    if (files.length === 0) {
      uploadButton.style.display('none')
  }
    files.filter(file=>!deleted.includes(file.name)).forEach((file) => {
        console.log(`File name: ${file.name}`);
        console.log(`File size: ${file.size}`);
        console.log(`File type: ${file.type}`);
    });
  submitButton.classList.remove('show')
});

