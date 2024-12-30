$('input').on('change', function() {
    $('body').toggleClass('blue');
});

// Highlight the active tab when switching between pages
const navLinks = document.querySelectorAll('.nav-links a');

navLinks.forEach(link => {
    link.addEventListener('click', () => {
        navLinks.forEach(item => item.classList.remove('active'));
        link.classList.add('active');
    });
});