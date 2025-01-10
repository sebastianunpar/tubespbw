document.getElementById('download-pdf').addEventListener('click', function() {
    const element = document.querySelector('.chart-container');
    
    html2canvas(element).then(function(canvas) {
        let base64Image = canvas.toDataURL('image/png');
        console.log(canvas.width, canvas.height)

        let pdf = new jsPDF('h', 'px', [1000, 500]);
        pdf.addImage(base64Image, 'PNG', 0, 0, 1001, 500)
        pdf.save('income-graph.pdf');
    });
});

//src: https://www.youtube.com/watch?v=DV2tAG5E-F0&t=791s
