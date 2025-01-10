document.addEventListener("DOMContentLoaded", function () {
    const dialog = document.getElementById("filter-dialog");
    const openButton = document.getElementById("open-dialog");
    const closeButton = document.getElementById("close-dialog");

    const actorSearchInput = document.getElementById("actor-search");
    const genreSearchInput = document.getElementById("genre-search");
    const actorOptions = document.getElementById("actor-options");
    const genreOptions = document.getElementById("genre-options");
    const searchForm = document.querySelector('form[th\\:action="@{/browse}"]');
    const searchInput = document.getElementById("search-input");

    // Retrieve selected filters from localStorage
    let selectedActors = JSON.parse(localStorage.getItem('selectedActors')) || [];
    let selectedGenres = JSON.parse(localStorage.getItem('selectedGenres')) || [];

    // Show dialog
    openButton.addEventListener("click", () => {
        dialog.showModal();
        restoreFilterStates(); // Restore the selected filter states when opening the dialog
    });

    // Close dialog (save filters but don't trigger search yet)
    closeButton.addEventListener("click", () => {
        dialog.close();
    });

    // Filter options based on input
    actorSearchInput.addEventListener("input", function () {
        filterOptions(actorSearchInput, actorOptions);
    });

    genreSearchInput.addEventListener("input", function () {
        filterOptions(genreSearchInput, genreOptions);
    });

    // Function to filter options
    function filterOptions(input, container) {
        const filterText = input.value.toLowerCase();
        const options = container.querySelectorAll("label");

        options.forEach(function (option) {
            const optionText = option.querySelector("span").textContent.toLowerCase();
            if (optionText.includes(filterText)) {
                option.style.display = "";
            } else {
                option.style.display = "none";
            }
        });
    }

    // Update selected actors and genres
    document.querySelectorAll('input[name="actorName"]').forEach((input) => {
        input.addEventListener("change", function () {
            if (this.checked) {
                if (!selectedActors.includes(this.value)) {
                    selectedActors.push(this.value);
                }
            } else {
                selectedActors = selectedActors.filter((actor) => actor !== this.value);
            }
            // Save selected actors to localStorage
            localStorage.setItem('selectedActors', JSON.stringify(selectedActors));
        });
    });

    document.querySelectorAll('input[name="genreName"]').forEach((input) => {
        input.addEventListener("change", function () {
            if (this.checked) {
                if (!selectedGenres.includes(this.value)) {
                    selectedGenres.push(this.value);
                }
            } else {
                selectedGenres = selectedGenres.filter((genre) => genre !== this.value);
            }
            // Save selected genres to localStorage
            localStorage.setItem('selectedGenres', JSON.stringify(selectedGenres));
        });
    });

    // Add selected filters to the form when submitting
    searchForm.addEventListener("submit", function (e) {
        // Prevent form submission until we add hidden inputs
        e.preventDefault();

        // Add hidden inputs for selected actors
        selectedActors.forEach((actor) => {
            const hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.name = "actorName";
            hiddenInput.value = actor;
            searchForm.appendChild(hiddenInput);
        });

        // Add hidden inputs for selected genres
        selectedGenres.forEach((genre) => {
            const hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.name = "genreName";
            hiddenInput.value = genre;
            searchForm.appendChild(hiddenInput);
        });

        // Now submit the form
        searchForm.submit();
    });

    // Function to restore the selected filter states when reopening the dialog
    function restoreFilterStates() {
        // Restore actor filter states
        document.querySelectorAll('input[name="actorName"]').forEach((input) => {
            input.checked = selectedActors.includes(input.value);
        });

        // Restore genre filter states
        document.querySelectorAll('input[name="genreName"]').forEach((input) => {
            input.checked = selectedGenres.includes(input.value);
        });
    }

    // Restore selected filters when the page loads
    restoreFilterStates();
});
