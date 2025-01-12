const okButton = document.querySelector(".confirmationPopUpButton");
const confirmation = document.querySelector(".confirmationPopUp");
okButton.addEventListener("click", (event) => {
    confirmation.classList.add("hidden");
});