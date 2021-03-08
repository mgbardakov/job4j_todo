function sendData() {
    let requestObject = createGetTasksRequest();
    let message = JSON.stringify(requestObject);
    console.log(message)
    fetch('task.do', {
        method : 'POST',
        body : message
    }).then(response => {
        return response.json();
    })
        .then(data => {
            console.log(data)
            drawTasks(data)
        })
}

function drawTasks(tasks) {
    let list = document.querySelector("#task-list")
    list.innerHTML = ""
    tasks.forEach(x => {
        let taskNode = document.createElement("li");
        taskNode.setAttribute("class", "list-group-item")
        if (x.done) {
            taskNode.setAttribute("style", "text-decoration: line-through")
        }
        let text = document.createTextNode(x.description)
        taskNode.appendChild(text)
        addHiddenId(x.id, taskNode)
        taskNode.addEventListener("click", changeTaskStatus)
        list.appendChild(taskNode)
    })
}

function createGetTasksRequest() {
    return {status : document.querySelector("#status").checked}
}

function changeTaskStatus() {
    let message = JSON.stringify({id: this.lastChild.innerText});
    console.log(message)
    fetch('change.do', {
        method : 'POST',
        body : message
    }).then(() => {
            sendData()
        })
}

function addNewTask() {
    let taskDescription = document.querySelector("#task-desc").value
    let message = JSON.stringify({description: taskDescription});
    console.log(message)
    fetch('add.do', {
        method : 'POST',
        body : message
    }).then(() => {
        sendData()
    })
}

function addHiddenId(id, node) {
    let idElement = document.createElement("div")
    idElement.setAttribute("class", "d-none")
    let text = document.createTextNode(id)
    idElement.appendChild(text)
    node.appendChild(idElement)
}

sendData();
document.querySelector("#status").addEventListener("click", sendData)
document.querySelector("#add-task-button").addEventListener("click", addNewTask)

