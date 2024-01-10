$(document).ready(function () {
    const salesChartCanvas = document.getElementById('pupils-subject-chart');
    const branchId = document.getElementById('branchId').value;
    let salesChart = null;

    let doughnutPieOptions = {
        responsive: true,
        animation: {
            animateScale: true,
            animateRotate: true
        }
    };


    function updateChart() {
        if (salesChart) {
            salesChart.destroy();
        }

        $.ajax({
            url: `/super-admin-owner/branches/branch-analytics/pupils-subjects?branchId=${branchId}`,
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                const labels = data.map(item => item.label);
                const values = data.map(item => item.value);

                const chartData = {
                    labels: labels,
                    datasets: [{
                        label: 'Ученики в предметах',
                        data: values,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.5)',
                            'rgba(54, 162, 235, 0.5)',
                            'rgba(255, 206, 86, 0.5)',
                            'rgba(75, 192, 192, 0.5)',
                            'rgba(153, 102, 255, 0.5)',
                            'rgba(255, 159, 64, 0.5)'
                        ],
                        borderColor: [
                            'rgba(255,99,132,1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1,
                    }],
                };

                salesChart = new Chart(salesChartCanvas, {
                    type: 'doughnut',
                    data: chartData,
                    options: doughnutPieOptions,
                });
            },

            error: function (error) {
                alert("Произошла ошибка при загрузке данных. Попробуйте перезагрузить страницу");
            },
        });
    }

    updateChart();
});
