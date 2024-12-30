// Chart.js integration for the bar chart
document.addEventListener('DOMContentLoaded', () => {
    const ctx = document.getElementById('chart').getContext('2d');
    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
        datasets: [{
          label: 'Film sewa',
          data: [35, 47, 22, 40, 60, 30, 20, 90, 50, 70, 30, 40],
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
  