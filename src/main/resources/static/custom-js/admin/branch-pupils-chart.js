$(document).ready(function () {
    const salesChartCanvas = document.getElementById('new-pupils-chart');
    const prevMonthButton = document.getElementById('prevMonthButtonPupils');
    const nextMonthButton = document.getElementById('nextMonthButtonPupils');
    const branchId = document.getElementById('branchId').value;
    let month = 0;
    let salesChart = null;


    prevMonthButton.addEventListener('click', function (e) {
        e.preventDefault();
        month += 1;
        updateChart();
    });

    nextMonthButton.addEventListener('click', function (e) {
        e.preventDefault();
        if (month > 0) {
            month -= 1;
            updateChart();
        } else {
            alert("Следующие месяцы недоступны");
        }
    });


    function updateChart() {
        if (salesChart) {
            salesChart.destroy();
        }

        $.ajax({
            url: '/super-admin-owner/branches/branch-analytics/new-pupils?branchId=' + branchId + '&monthShift=' + month,
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                const labels = data.map(item => item.label);
                const values = data.map(item => item.value);

                const chartData = {
                    labels: labels,
                    datasets: [{
                        label: 'кол-во',
                        data: values,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1,
                        fill: true
                    }],
                };

                salesChart = new Chart(salesChartCanvas, {
                    type: 'bar',
                    data: chartData,
                    options: {
                        responsive: true,
                        scales: {
                            y: {
                                beginAtZero: true,
                            },
                        },
                    },
                });
            },

            error: function (error) {
                alert("Произошла ошибка при загрузке данных. Попробуйте перезагрузить страницу");
            },
        });
    }

    updateChart();
});
