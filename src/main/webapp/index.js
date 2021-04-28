
let items = new Map()

function getItemList() {
    let status = createGetTasksRequest();
    fetch(`task.do?all=${status}`).then(response => {
        return response.json();
    })
        .then(data => {
            console.log(data)
            saveAndDrawTasks(data)
        })
}

function sendItem(item) {
    let message = JSON.stringify(item);
    console.log(message)
    fetch(`add.do`, {
        method : "POST",
        body : message
    }).then(getItemList);
}

function createNewItem() {
    let taskDescription = document.querySelector("#task-desc").value
    return {id : 0,
            description : taskDescription,
            done : false}
}

function addNewItem() {
    let item = createNewItem();
    sendItem(item)
}

function changeItemStatus() {
    console.log(items)
    console.log(this.lastChild.innerText)
    let item = items.get(Number.parseInt(this.lastChild.innerText))
    item.done = !item.done
    sendItem(item)
}

function saveAndDrawTasks(tasks) {
    let list = document.querySelector("#task-list")
    list.innerHTML = ""
    items.clear()
    tasks.forEach(x => {
        items.set(x.id, x)
        let taskNode = document.createElement("li");
        taskNode.setAttribute("class", "list-group-item")
        if (x.done) {
            taskNode.setAttribute("style", "text-decoration: line-through")
        }
        let authorBadge = document.createElement("span")
        authorBadge.classList.add("badge")
        authorBadge.classList.add("badge-secondary")
        authorBadge.textContent = x.user.name
        authorBadge.setAttribute("style", "margin-right: 20px")
        taskNode.appendChild(authorBadge)
        let text = document.createTextNode(x.description)
        taskNode.appendChild(text)
        addHiddenId(x.id, taskNode)
        taskNode.addEventListener("click", changeItemStatus)
        list.appendChild(taskNode)
    })
}

function createGetTasksRequest() {
    return document.querySelector("#status").checked
}

function addHiddenId(id, node) {
    let idElement = document.createElement("div")
    idElement.setAttribute("class", "d-none")
    let text = document.createTextNode(id)
    idElement.appendChild(text)
    node.appendChild(idElement)
}

function exitUser() {
    fetch(`exit.do`).then(response => {
        if (response.ok === true) {
            window.location.reload(true)
        }
    })
}

getItemList();
document.querySelector("#status").addEventListener("click", getItemList)
document.querySelector("#add-task-button").addEventListener("click", addNewItem)
document.querySelector('#userField').prepend(localStorage.getItem("userName"))
document.querySelector("#exitButton").addEventListener("click", exitUser)

