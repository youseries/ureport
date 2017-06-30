$(document).ready(function () {
    _buildChart("_canvas_A10-0", {
        type: "pie",
        data: {
            labels: ["硕士", "大专", "博士", "本科", "高中", "大学", "研究生", "中专", "博士后", "小学"],
            datasets: [{
                label: "人员",
                backgroundColor: "rgba(255, 99, 132,0.3)",
                borderColor: "rgb(255, 99, 132)",
                borderWidth: 1,
                data: [33975.0, 575682.0, 32827.0, 8150.0, 4408.0, 125962.0, 86553.0, 48825.0, 45521.0, 60112.0]
            }]
        },
        options: {}
    });
});
new Chart(document.getElementById("chartjs-5"), {
    "type": "polarArea",
    "data": {
        "labels": ["Red", "Green", "Yellow", "Grey", "Blue"],
        "datasets": [{
            "label": "My First Dataset",
            "data": [11, 16, 7, 3, 14],
            "backgroundColor": ["rgb(255, 99, 132)", "rgb(75, 192, 192)", "rgb(255, 205, 86)", "rgb(201, 203, 207)", "rgb(54, 162, 235)"]
        }]
    }
});