
function createRequestJSON() {
    let login = document.querySelector("#inputLogin").value;
    let password = document.querySelector("#inputPassword").value;
    return {login: login, password: password};
}

function sendRequest() {
    let request = createRequestJSON();
    let message = JSON.stringify(request);
    console.log(message);
    fetch(`auth.do`, {
        method : "POST",
        body : message
    }).then(response => {return response.json()})
        .then(data => {
            console.log(data)
            if (data.hasOwnProperty("errorMessage")) {
                let elem = document.querySelector("#error")
                elem.textContent = data.errorMessage
                elem.classList.remove("d-none")
                elem.classList.add("d-block")
            } else {
                localStorage.setItem("userName", data.name);
                window.location.href = "index.html"
            }
        })
}

document.querySelector("#enterButton").addEventListener("click", sendRequest);
document.querySelector("#registerButton").addEventListener("click",
    () => window.location.href = "reg.html");