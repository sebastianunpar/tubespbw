document.addEventListener("DOMContentLoaded", function () {
    const dialog = document.getElementById("filter-dialog");
    const openButton = document.getElementById("open-dialog");
    const closeButton = document.getElementById("close-dialog");
    const genreSearchInput = document.getElementById("genre-search");
    const actorSearchInput = document.getElementById("actor-search");
    const genreOptions = document.getElementById("genre-options");
    const actorOptions = document.getElementById("actor-options");

    const searchForm = document.querySelector('form[th\\:action="@{/browse}"]') || document.querySelector('form[action="/browse"]');

    if (!searchForm) {
        console.error("Search form not found!");
        return;
    }

    // Open the filter dialog
    openButton.addEventListener("click", () => dialog.showModal());

    // Close the filter dialog without submitting the form
    closeButton.addEventListener("click", () => {
        const selectedActors = Array.from(
            document.querySelectorAll('input[name="actorName"]:checked')
        ).map(input => input.value);

        const selectedGenres = Array.from(
            document.querySelectorAll('input[name="genreName"]:checked')
        ).map(input => input.value);

        // Store selected filters in hidden inputs
        storeSelectedFilters(selectedActors, selectedGenres);
        dialog.close(); // Close the dialog
    });

    // Store selected actors and genres in hidden inputs
    function storeSelectedFilters(selectedActors, selectedGenres) {
        // Clear existing hidden filters
        document.querySelectorAll(".hidden-filter").forEach(input => input.remove());

        const uniqueActors = new Set(selectedActors);
        const uniqueGenres = new Set(selectedGenres);

        // Add hidden inputs for selected actors
        uniqueActors.forEach(actor => {
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "actorName";
            input.value = actor;
            input.classList.add("hidden-filter");
            searchForm.appendChild(input);
        });

        // Add hidden inputs for selected genres
        uniqueGenres.forEach(genre => {
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "genreName";
            input.value = genre;
            input.classList.add("hidden-filter");
            searchForm.appendChild(input);
        });
    }

    // Handle form submission (when user clicks search)
    searchForm.addEventListener("submit", function (e) {
        e.preventDefault(); // Prevent form submission immediately

        const movieName = document.querySelector('#search-input').value;
        let movieNameInput = document.querySelector('input[name="movieName"]');

        // Remove any existing movie name hidden input before adding a new one
        if (movieNameInput) {
            movieNameInput.remove();
        }

        // Add hidden input for movie name only if it's not empty
        if (movieName) {
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "movieName";
            input.value = movieName;
            input.classList.add("hidden-filter");
            searchForm.appendChild(input);
        }

        // Now submit the form
        searchForm.submit();
    });

    // Function to filter options
    function filterOptions(input, optionsContainer, type) {
        const query = input.value.toLowerCase();
        const options = optionsContainer.querySelectorAll('label');

        options.forEach(option => {
            const text = option.querySelector('span').textContent.toLowerCase();
            if (text.includes(query)) {
                option.style.display = '';
            } else {
                option.style.display = 'none';
            }
        });
    }

    // Add event listeners for real-time search
    genreSearchInput.addEventListener("input", () => filterOptions(genreSearchInput, genreOptions, 'genre'));
    actorSearchInput.addEventListener("input", () => filterOptions(actorSearchInput, actorOptions, 'actor'));
});
