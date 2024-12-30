// Elemen pembayaran
const dialog = document.getElementById('payment-dialog');
const openBtn = document.getElementById('open-dialog');
const closeBtn = document.getElementById('close-dialog');

// Elemen popup Virtual Account
const dialog2 = document.getElementById('virtual-account-section');
const closeBtn2 = document.getElementById('close-dialog-2');
const bcaMethod = document.getElementById('bca-method');
const mandiriMethod = document.getElementById('mandiri-method');
const accountNumber = document.getElementById('account-number');
const bankLogo = document.getElementById('bank-logo');

// Tampilkan dialog pembayaran
openBtn.addEventListener('click', () => {
  dialog.showModal();
});

// Tutup dialog pembayaran
closeBtn.addEventListener('click', () => {
  dialog.close();
});

// Klik metode pembayaran BCA
bcaMethod.addEventListener('click', () => {
  accountNumber.innerText = '1234 5678 9012 3456'; // Ganti dengan nomor BCA
  bankLogo.innerText = 'BCA Virtual Account';
  dialog2.style.display = 'block';  // Show virtual account section
});

// Klik metode pembayaran Mandiri
mandiriMethod.addEventListener('click', () => {
  accountNumber.innerText = '8077 7000 1414 8659'; // Ganti dengan nomor Mandiri
  bankLogo.innerText = 'Mandiri Virtual Account';
  dialog2.style.display = 'block';  // Show virtual account section
});

// Salin nomor ke clipboard
function copyToClipboard() {
  const textToCopy = accountNumber.innerText;
  navigator.clipboard.writeText(textToCopy)
    .then(() => alert('Nomor Virtual Account berhasil disalin!'))
    .catch(() => alert('Gagal menyalin nomor Virtual Account.'));
}

function goBack() {
  window.history.back();
}