// Chart.js integration for the bar chart
document.addEventListener('DOMContentLoaded', () => {
    const chartElement = document.getElementById('chart');
    const ctx = chartElement.getContext('2d');
    const data = JSON.parse(chartElement.getAttribute('data-data'));

    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        datasets: [{
          label: 'Film sewa',
          data: data,
          backgroundColor: ['#FFE37E', '#FFE37E', '#FFE37E', '#FFE37E', '#FFE37E', '#FFE37E', '#FFE37E', '#FFE37E', '#FFE37E', '#FFE37E', '#FFE37E', '#FEC600'],
          borderRadius: 10,
          borderSkipped: false
        }]
      },
      options: {
        plugins: {
          legend: { display: false }
        },
        scales: {
          x: {
            grid: { display: false },
            ticks: { color: '#cccccc' }
          },
          y: {
            grid: { color: '#444' },
            ticks: { color: '#cccccc' }
          }
        }
      }
    });
  });