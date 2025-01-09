const currentRentalTab = document.getElementById('currentRentalTab');
const historyTab = document.getElementById('historyTab');
const currentRentalSection = document.getElementById('currentRental');
const historySection = document.getElementById('rentalHistory');

currentRentalTab.addEventListener('click', function() {
    currentRentalSection.classList.remove('hidden');
    historySection.classList.add('hidden');
    historyTab.classList.remove('active');
    currentRentalTab.classList.add('active');
});

historyTab.addEventListener('click', function() {
    historySection.classList.remove('hidden');
    currentRentalSection.classList.add('hidden');
    currentRentalTab.classList.remove('active');
    historyTab.classList.add('active');
});