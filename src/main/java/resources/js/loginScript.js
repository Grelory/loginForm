const regex = /^([a-zA-Z0-9\s\!\?_,\.''-])+$/;

const content = document.querySelector("div.content");
const sessionStorage = window.sessionStorage;
let logged = false;

function validateAnswers(login, password) {
    this.login.style.backgroundColor = regex.test(login) ? "white":"yellow";
    this.password.style.backgroundColor = regex.test(password) ? "white":"yellow";
}

function loadLoggedContent(name) {
    content.innerHTML = `
    <h1>Hello ${name}!!!</h1>
    <button type="button">Log out</button>
    `;
    content.querySelector("div.content button")
            .addEventListener("click", function() {
                sessionStorage.removeItem("name");
                loadContent();
            });
}

function loadLoginContent() {
    content.innerHTML = `
    <form class="login">
        <h2 class="logger">sign in</h2>
        <hr class="break">
        <label for="login">login:</label>
        <input type="text" id="login">
        <label for="password">password:</label>
        <input type="password" id="password">
        <button type="submit">log in</button>
    </form>
    `;
    addSubmitEventListener();
}

function addSubmitEventListener() {
    content.querySelector("form.login").addEventListener("submit", function(event) {
        event.preventDefault();
        validateAnswers(this.login.value, this.password.value);
        let data = `login=${this.login.value}&password=${this.password.value}`;
        fetch("http://localhost:8000/handle", {
            method: "POST",
            body: data
        })
        .then( function(response) {
            console.log(response.status);
            if (response.status == 401) return alert("Invalid login or password");
            return response.json();
        })
        .then( function(message) {
            console.log(message);
            sessionStorage.setItem("name", message.name);
            location.href = "http://localhost:8000/login";
        })
    });
}

function loadContent() {
    const name = sessionStorage.getItem("name");
    logged = (name !== null);
    if (logged) return loadLoggedContent(name);
    return loadLoginContent();
}

loadContent();