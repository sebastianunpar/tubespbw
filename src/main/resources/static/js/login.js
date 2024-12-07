const loginContainer = document.querySelector(".loginContainer")
const toRegister = document.getElementById("switchToReg")
const toLogin = document.getElementById("switchToLog")
const title = document.querySelector("h2")
toRegister.addEventListener("click", (event) => {
    const loginForm = document.getElementById("loginForm")
    const registerForm = document.getElementById("registerForm")
    const toRegisterContainer = document.getElementById("toRegisterContainer")
    const toLoginContainer = document.getElementById("toLoginContainer")
    loginForm.classList.add("hidden")
    toRegisterContainer.classList.add("hidden")
    registerForm.classList.remove("hidden")
    toLoginContainer.classList.remove("hidden")
    loginContainer.classList.add("addHeight")
    title.textContent = "Register"
});

toLogin.addEventListener("click", (event) => {
    const loginForm = document.getElementById("loginForm")
    const registerForm = document.getElementById("registerForm")
    const toRegisterContainer = document.getElementById("toRegisterContainer")
    const toLoginContainer = document.getElementById("toLoginContainer")
    registerForm.classList.add("hidden")
    toLoginContainer.classList.add("hidden")
    loginForm.classList.remove("hidden")
    toRegisterContainer.classList.remove("hidden")
    loginContainer.classList.remove("addHeight")
    title.textContent = "Log in"
});



