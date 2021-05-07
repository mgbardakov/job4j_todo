
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

function getCategoryList() {
    fetch("category.do").then(response => {
        return response.json();
    }).then(data => {
        drawCategories(data)
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
    let categories = createCategoryList()
    return {id : 0,
            description : taskDescription,
            done : false,
            categories: categories}
}

function createCategoryList() {
    let menu = document.querySelector("#category-menu")
    let checkboxes = menu.querySelectorAll("[type = checkbox]")
    let rsl = []
    checkboxes.forEach(x => {
        if (x.checked === true) {
            rsl.push({id: x.value})
        }
    })
    return rsl
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
        let categoryDiv = document.createElement("div");
        categoryDiv.setAttribute("style", "width: 130px")
        categoryDiv.classList.add("float-right")
        x.categories.forEach(x => {
            let categoryBadge = document.createElement("span")
            categoryBadge.classList.add("badge")
            categoryBadge.classList.add("badge-pill")
            categoryBadge.classList.add("badge-info")
            categoryBadge.setAttribute("style", "margin-right: 5px")
            categoryBadge.textContent = x.name
            categoryDiv.appendChild(categoryBadge)
        })
        taskNode.appendChild(categoryDiv)
        addHiddenId(x.id, taskNode)
        taskNode.addEventListener("click", changeItemStatus)
        list.appendChild(taskNode)
    })
}

function drawCategories(categories) {
    let menu = document.querySelector("#category-menu")
    categories.forEach(x => {
        menu.insertAdjacentHTML(
            "beforeend", `<li>&nbsp;<input type="checkbox" value="${x.id}"/>&nbsp;${x.name}</li>`)
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
getCategoryList();
document.querySelector("#status").addEventListener("click", getItemList)
document.querySelector("#add-task-button").addEventListener("click", addNewItem)
document.querySelector('#userField').prepend(localStorage.getItem("userName"))
document.querySelector("#exitButton").addEventListener("click", exitUser)

let options = [];

$( '.dropdown-menu a' ).on( 'click', function( event ) {

    let $target = $( event.currentTarget ),
        val = $target.attr( 'data-value' ),
        $inp = $target.find( 'input' ),
        idx;

    if ( ( idx = options.indexOf( val ) ) > -1 ) {
        options.splice( idx, 1 );
        setTimeout( function() { $inp.prop( 'checked', false ) }, 0);
    } else {
        options.push( val );
        setTimeout( function() { $inp.prop( 'checked', true ) }, 0);
    }

    $( event.target ).blur();

    console.log( options );
    return false;
});

