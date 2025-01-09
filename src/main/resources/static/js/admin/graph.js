const filmCountButton = document.getElementById("filmCountButton");
const incomeButton = document.getElementById("incomeButton");

filmCountButton.addEventListener("click", (event) => {
    filmCountButton.classList.add("active");
    incomeButton.classList.remove("active");
});

incomeButton.addEventListener("click", (event) => {
    incomeButton.classList.add("active");
    filmCountButton.classList.remove("active");
});