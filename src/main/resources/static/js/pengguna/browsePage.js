document.addEventListener("DOMContentLoaded", function() {
  const dialog = document.getElementById('filter-dialog');
  const openButton = document.getElementById('open-dialog');
  const closeButton = document.getElementById('close-dialog');

  openButton.addEventListener('click', () => {
      dialog.showModal();
  });

  closeButton.addEventListener('click', () => {
      dialog.close();
  });

  // ambil search input actor dan genre
  const actorSearchInput = document.getElementById("actor-search");
  const genreSearchInput = document.getElementById("genre-search");

  // ambil container actor dan genre
  const actorOptions = document.getElementById("actor-options");
  const genreOptions = document.getElementById("genre-options");

  // filter actor berdasarkan input
  actorSearchInput.addEventListener("input", function() {
      filterOptions(actorSearchInput, actorOptions);
  });

  // filter genre berdasaran input
  genreSearchInput.addEventListener("input", function() {
      filterOptions(genreSearchInput, genreOptions);
  });

  //fungsi filter
  function filterOptions(input, container) {
      const filterText = input.value.toLowerCase();
      const options = container.querySelectorAll("label");

      options.forEach(function(option) {
          const optionText = option.querySelector("span").textContent.toLowerCase();
          if (optionText.includes(filterText)) {
              option.style.display = "";
          } else {
              option.style.display = "none";
          }
      });
  }
});