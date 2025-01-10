document.addEventListener("DOMContentLoaded", function () {
    const dialog = document.getElementById("filter-dialog");
    const openButton = document.getElementById("open-dialog");
    const closeButton = document.getElementById("close-dialog");
    const searchForm = document.querySelector('form[th\\:action="@{/browse}"]') || document.querySelector('form[action="/browse"]');

    if (!searchForm) {
        console.error("Search form not found!");
        return;
    }

    // Open the filter dialog
    openButton.addEventListener("click", () => dialog.showModal());

    // Close the filter dialog
    closeButton.addEventListener("click", () => dialog.close());

    // Preserve selected filters on form submission
    searchForm.addEventListener("submit", function (e) {
        e.preventDefault(); // Prevent the form from submitting immediately

        const selectedActors = Array.from(
            document.querySelectorAll('input[name="actorName"]:checked')
        ).map(input => input.value);

        const selectedGenres = Array.from(
            document.querySelectorAll('input[name="genreName"]:checked')
        ).map(input => input.value);

        const movieName = document.querySelector('#search-input').value;

        // Clear existing hidden inputs
        document.querySelectorAll(".hidden-filter").forEach(input => input.remove());

        // Add hidden inputs for movie name
        if (movieName) {
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "movieName";
            input.value = movieName;
            input.classList.add("hidden-filter");
            searchForm.appendChild(input);
        }

        // Add hidden inputs for selected actors
        selectedActors.forEach(actor => {
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "actorName";
            input.value = actor;
            input.classList.add("hidden-filter");
            searchForm.appendChild(input);
        });

        // Add hidden inputs for selected genres
        selectedGenres.forEach(genre => {
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "genreName";
            input.value = genre;
            input.classList.add("hidden-filter");
            searchForm.appendChild(input);
        });

        // Now submit the form
        searchForm.submit();
    });
});
