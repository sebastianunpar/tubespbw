document.getElementById('download-pdf-report').addEventListener('click', function() {
    const element = document.querySelector('.pdf-report');
    
    html2canvas(element).then(function(canvas) {
        let base64Image = canvas.toDataURL('image/png');
        console.log(canvas.width, canvas.height)

        let pdf = new jsPDF('p', 'px', [600, 500]);
        pdf.addImage(base64Image, 'PNG', 0, 0, 500, 350)
        pdf.save('monthly-report.pdf');
    });
});

//src: https://www.youtube.com/watch?v=DV2tAG5E-F0&t=791s