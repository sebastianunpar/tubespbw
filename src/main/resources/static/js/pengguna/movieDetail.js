// Elemen pembayaran
const dialog = document.getElementById('payment-dialog');
const openBtn = document.getElementById('open-dialog');
const closeBtn = document.getElementById('close-dialog');

// Elemen popup Virtual Account
const dialog2 = document.getElementById('virtual-account-section');
const accountNumber = document.getElementById('account-number');
const bankLogo = document.getElementById('bank-logo');
const paymentMethods = document.getElementsByName('payment-method');

// Tampilkan dialog pembayaran
if (openBtn) {
  openBtn.addEventListener('click', () => {
    dialog.showModal();
  });
}

// Tutup dialog pembayaran
if (closeBtn) {
  closeBtn.addEventListener('click', () => {
    dialog.close();
  });
}

// Event Listener untuk metode pembayaran
paymentMethods.forEach((method) => {
  method.addEventListener('change', () => {
    if (method.value === 'bca') {
      accountNumber.innerText = '1234 5678 9012 3456'; // Ganti dengan nomor BCA
      bankLogo.innerText = 'BCA Virtual Account';
    } else if (method.value === 'mandiri') {
      accountNumber.innerText = '8077 7000 1414 8659'; // Ganti dengan nomor Mandiri
      bankLogo.innerText = 'Mandiri Virtual Account';
    }
    dialog2.style.display = 'block'; // Tampilkan bagian Virtual Account
  });
});

// Salin nomor ke clipboard
function copyToClipboard() {
  const textToCopy = accountNumber.innerText;
  navigator.clipboard.writeText(textToCopy)
    .then(() => alert('Nomor Virtual Account berhasil disalin!'))
    .catch(() => alert('Gagal menyalin nomor Virtual Account.'));
}

// Fungsi kembali ke halaman sebelumnya
function goBack() {
  window.history.back();
}
