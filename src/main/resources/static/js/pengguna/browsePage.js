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

  //search bar browse page
  const searchInput = document.getElementById("search-input");
  const searchIcon = document.getElementById("search-icon");

  // Fungsi untuk menangani pencarian
  function performSearch() {
      const query = searchInput.value.trim();
      if (query) {
          window.location.href = `/browse?search=${encodeURIComponent(query)}`;
      }
  }

  // Fungsi untuk menangani reset pencarian
  function resetSearch() {
      searchInput.value = "";  // Kosongkan input
      searchIcon.src = "assets/img/search-bar-logo.png";  // Kembali ke ikon pencarian
      searchIcon.classList.remove("reset");  // Hapus class reset
  }

  // Event listener untuk klik ikon
  searchIcon.addEventListener("click", () => {
      const query = searchInput.value.trim();
      if (query) {
          // Ganti ikon menjadi "X"
          searchIcon.src = "assets/img/close-icon.png";
          searchIcon.classList.add("reset"); // Tambahkan class reset
          performSearch();  // Lakukan pencarian
      } else {
          // Jika input kosong, reset pencarian
          resetSearch();  // Reset pencarian
      }
  });

  // Event listener untuk tekan tombol Enter
  searchInput.addEventListener("keydown", (event) => {
      if (event.key === "Enter") {
          event.preventDefault();  // Mencegah form submit
          performSearch();  // Lakukan pencarian
      }
  });

  // Menangani klik pada ikon "X" untuk mereset pencarian
  document.getElementById("search-icon").addEventListener("click", function () {
      if (searchInput.value.trim() === "") {
          resetSearch();  // Reset pencarian jika input kosong
      }
  });



  
});
