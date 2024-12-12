const dialog = document.getElementById('filter-dialog');
const openButton = document.getElementById('open-dialog');
const closeButton = document.getElementById('close-dialog');

openButton.addEventListener('click', () => {
  dialog.showModal();
});

closeButton.addEventListener('click', () => {
  dialog.close();
});

