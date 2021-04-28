function createRequestJSON() {
    let login = document.querySelector("#inputLogin").value;
    let password = document.querySelector("#inputPassword").value;
    let name = document.querySelector("#inputName").value;
    return {login: login, password: password, name: name};
}

function sendRequest() {
    let request = createRequestJSON();
    let message = JSON.stringify(request);
    console.log(message);
    fetch(`reg.do`, {
        method : "POST",
        body : message
    }).then(response => {
        let elem = document.querySelector("#message")
            elem.classList.remove("d-none")
            elem.classList.add("d-block")
            if (response.ok === true) {
                elem.classList.remove("alert-danger")
                elem.classList.add("alert-success")
             } else {
                elem.classList.remove("alert-success")
                elem.classList.add("alert-danger")
            }
        return response.json()}
        )
        .then(data => {
            console.log(data)
            let elem = document.querySelector("#message")
            elem.textContent = data.message
        })
}

document.querySelector("#submitButton").addEventListener("click", sendRequest);
document.querySelector("#authorizeButton").addEventListener("click",
    () => window.location.href = "auth.html");