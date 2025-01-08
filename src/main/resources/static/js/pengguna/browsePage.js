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

  // Ambil search input actor dan genre
  const actorSearchInput = document.getElementById("actor-search");
  const genreSearchInput = document.getElementById("genre-search");

  // Ambil container actor dan genre
  const actorOptions = document.getElementById("actor-options");
  const genreOptions = document.getElementById("genre-options");

  // Filter actor berdasarkan input
  actorSearchInput.addEventListener("input", function() {
      filterOptions(actorSearchInput, actorOptions);
  });

  // Filter genre berdasarkan input
  genreSearchInput.addEventListener("input", function() {
      filterOptions(genreSearchInput, genreOptions);
  });

  // Fungsi filter
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

  const searchInput = document.getElementById("search-input");
  const searchIcon = document.getElementById("search-icon");

  // Fungsi untuk melakukan pencarian
  function performSearch() {
      const query = searchInput.value.trim();
      if (query) {
          window.location.href = `/browse?search=${encodeURIComponent(query)}`;
      }
  }

  // Event listener untuk klik ikon pencarian
  searchIcon.addEventListener("click", function() {
      const query = searchInput.value.trim();
      if (query) {
          searchIcon.src = "assets/img/close-icon.png";
          searchIcon.classList.add("reset");
          performSearch();  // Lakukan pencarian
      } 
  });


});
