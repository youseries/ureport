$(document).ready(function () {
    _buildChart("37f572f7-52a9-4566-a4d2-0298968fff6d", {
        type: "pie",
        data: {
            labels: ["201706", "201707", "201708"],
            datasets: [{
                label: "教师资格证",
                backgroundColor: ["rgb(255, 99, 132)", "rgb(54, 162, 235)", "rgb(255, 205, 86)"],
                data: [10.0, 2.0, 3.0]
            }, {label: "普通话等级证", backgroundColor: ["rgb(54, 162, 235)", "rgb(255, 205, 86)"], data: [5.0, 5.0, 7.0]}]
        },
        options: {scales: {yAxes: [{ticks: {min: 0}}]}}
    });
});