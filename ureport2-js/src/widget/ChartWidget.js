/**
 * Created by Jacky.Gao on 2017-06-22.
 */
import Chart from 'chart.js';
import {alert} from '../MsgBox.js';

export default class ChartWidget{
    constructor(container,cellDef){
        this.container=container;
        this.cellDef=cellDef;
    }
    renderChart(container,context,rowIndex,colIndex){
        if(container){
            this.container=container;
        }
        this.hot=context.hot;
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
        const $td=this.container;
        this.rowSpan=$td.prop('rowspan'),this.colSpan=$td.prop('colspan');
        this.width=-2,this.height=-2;
        const rowStart=this.rowIndex,rowEnd=this.rowIndex+this.rowSpan;
        for(let i=rowStart;i<rowEnd;i++){
            this.height+=this.hot.getRowHeight(i);
        }
        const colStart=this.colIndex,colEnd=this.colIndex+this.colSpan;
        for(let i=colStart;i<colEnd;i++){
            this.width+=this.hot.getColWidth(i);
        }
        this.container.empty();
        const canvas=$(`<div style="position: relative;"><canvas style="width: ${this.width}px;height: ${this.height}px;;"></canvas></div>`);
        this.container.append(canvas);
        const type=this.cellDef.value.chart.dataset.type;
        let data=null,options={},chartType;

        const defaultOptions=this.cellDef.value.chart.options;
        if(defaultOptions) {
            for(let option of defaultOptions){
                options[option.type]=option;
            }
        }
        const xaxes=this.cellDef.value.chart.xaxes;
        if(xaxes){
            let scales=options.scales;
            if(!scales){
                scales={};
                options.scales=scales;
            }
            let xAxes=scales.xAxes;
            if(!xAxes){
                xAxes=[];
                scales.xAxes=xAxes;
            }
            if(xaxes.rotation){
                let ticks=xaxes.ticks;
                if(!ticks){
                    ticks={};
                    xaxes.ticks=ticks;
                }
                ticks.minRotation=xaxes.rotation;
            }
            xAxes.push(xaxes);
        }
        const yaxes=this.cellDef.value.chart.yaxes;
        if(yaxes){
            let scales=options.scales;
            if(!scales){
                scales={};
                options.scales=scales;
            }
            let yAxes=scales.yAxes;
            if(!yAxes){
                yAxes=[];
                scales.yAxes=yAxes;
            }
            if(yaxes.rotation){
                let ticks=yaxes.ticks;
                if(!ticks){
                    ticks={};
                    yaxes.ticks=ticks;
                }
                ticks.minRotation=yaxes.rotation;
            }
            yAxes.push(yaxes);
        }

        const color = Chart.helpers.color;
        switch (type){
            case 'bar':
                chartType='bar';
                data={
                    labels: ["类型1", "类型2", "类型3", "类型4", "类型5", "类型6"],
                    datasets: [{
                        label: '系列1',
                        backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.red,
                        borderWidth: 1,
                        data: [21,25, 8,12,31,19]
                    }, {
                        label: '系列2',
                        backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.blue,
                        borderWidth: 1,
                        data: [11,13, 18,9,23,29]
                    }]
                };
                break;
            case 'horizontalBar':
                chartType='horizontalBar';
                data={
                    labels: ["类型1", "类型2", "类型3", "类型4", "类型5", "类型6"],
                    datasets: [{
                        label: '系列1',
                        backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.red,
                        borderWidth: 1,
                        data: [21,25, 8,12,31,19]
                    }, {
                        label: '系列2',
                        backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.blue,
                        borderWidth: 1,
                        data: [11,13, 18,9,23,29]
                    }]
                };
                break;
            case 'line':
                chartType='line';
                data={
                    labels: ["类型1", "类型2", "类型3", "类型4", "类型5", "类型6"],
                    datasets: [{
                        label: '系列1',
                        backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.red,
                        borderWidth: 1,
                        fill: false,
                        data: [21,25, 8,12,31,19]
                    }, {
                        label: '系列2',
                        backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.blue,
                        borderWidth: 1,
                        fill: false,
                        data: [11,13, 18,9,23,29]
                    }]
                };
                break;
            case 'area':
                chartType='line';
                data={
                    labels: ["类型1", "类型2", "类型3", "类型4", "类型5", "类型6"],
                    datasets: [{
                        label: '系列1',
                        backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.red,
                        borderWidth: 1,
                        data: [21,25, 8,12,31,19]
                    }, {
                        label: '系列2',
                        backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.blue,
                        borderWidth: 1,
                        data: [11,13, 18,9,23,29]
                    }]
                };
                options.scales= {
                    yAxes: [{
                        stacked: true
                    }]
                };
                break;
            case 'pie':
                chartType='pie';
                data={
                    labels: ["类型1", "类型2", "类型3", "类型4"],
                    datasets: [{
                        label: '系列1',
                        backgroundColor: [
                            window.chartColors.red,
                            window.chartColors.orange,
                            window.chartColors.yellow,
                            window.chartColors.green
                        ],
                        data: [21,25, 8,12]
                    }]
                };
                break;
            case 'doughnut':
                chartType='doughnut';
                data={
                    labels: ["类型1", "类型2", "类型3", "类型4"],
                    datasets: [{
                        label: '系列1',
                        backgroundColor: [
                            window.chartColors.red,
                            window.chartColors.orange,
                            window.chartColors.yellow,
                            window.chartColors.green
                        ],
                        data: [21,25, 8,12]
                    }]
                };
                break;
            case 'radar':
                chartType='radar';
                data={
                    labels: ["类型1", "类型2", "类型3", "类型4", "类型5"],
                    datasets: [{
                        label: '系列1',
                        backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.red,
                        borderWidth: 1,
                        data: [21,25, 8,12,31]
                    }, {
                        label: '系列2',
                        backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.blue,
                        borderWidth: 1,
                        data: [11,13,18,9,23,9]
                    }]
                };
                break;
            case 'polarArea':
                chartType='polarArea';
                data={
                    labels: ["类型1", "类型2", "类型3", "类型4"],
                    datasets: [{
                        label: '系列1',
                        backgroundColor: [
                            window.chartColors.red,
                            window.chartColors.orange,
                            window.chartColors.yellow,
                            window.chartColors.green
                        ],
                        data: [21,25,12,31]
                    }]
                };
                break;
            case 'scatter':
                chartType="scatter";
                data = {
                    datasets: [
                        {
                            label: '系列1',
                            borderColor: window.chartColors.red,
                            backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
                            data: [
                                {x: 10,y: 10},
                                {x: 5,y: 15},
                                {x: 8,y: 12},
                                {x: 18,y: 10}
                            ]
                        },
                        {
                            label: '系列2',
                            borderColor: window.chartColors.blue,
                            backgroundColor: color(window.chartColors.blue).alpha(0.2).rgbString(),
                            data: [
                                {x: 13,y: 6},
                                {x: 25,y: 10},
                                {x: 18,y: 11},
                                {x: 14,y: 16}
                            ]
                        }
                    ]
                };
                break;
            case 'bubble':
                chartType="bubble";
                data = {
                    datasets: [
                        {
                            label: '系列1',
                            borderColor: window.chartColors.red,
                            backgroundColor: color(window.chartColors.red).alpha(0.2).rgbString(),
                            data: [
                                {x: 10,y: 10,r:4},
                                {x: 5,y: 15,r:6},
                                {x: 8,y: 12,r:2},
                                {x: 18,y: 10,r:8}
                            ]
                        },
                        {
                            label: '系列2',
                            borderColor: window.chartColors.blue,
                            backgroundColor: color(window.chartColors.blue).alpha(0.2).rgbString(),
                            data: [
                                {x: 13,y: 6,r:3},
                                {x: 25,y: 10,r:9},
                                {x: 18,y: 11,r:2},
                                {x: 14,y: 16,r:10}
                            ]
                        }
                    ]
                };
                break;
            case 'mix':
                chartType='bar';
                data={
                    labels: ["类型1", "类型2", "类型3", "类型4", "类型5", "类型6"],
                    datasets: [{
                        type: 'line',
                        label: '系列1',
                        backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.red,
                        data: [21,25, 8,12,31,19]
                    }, {
                        type: 'bar',
                        label: '系列2',
                        backgroundColor: color(window.chartColors.blue).alpha(0.5).rgbString(),
                        borderColor: window.chartColors.blue,
                        borderWidth: 1,
                        data: [11,13, 18,9,23,29]
                    }]
                };
                break;
            default:
                alert('不能识别的图表类型：'+type);
        };
        const optionList=this.cellDef.value.chart.options || [];
        for(let op of optionList){
            switch (op.type){
                case "title":
                    if(op.display){
                        options.title={
                            display:true,
                            position:op.position,
                            text:op.text
                        }
                    }
                    break;
                case "legend":
                    if(op.display){
                        options.legend={
                            display:true,
                            position:op.position
                        }
                    }else{
                        options.display={
                            display:false
                        }
                    }
                    break;
                case "layout":
                    if(op.padding){
                        options.layout={
                            padding:{
                                left:op.padding.left,
                                right:op.padding.right,
                                top:op.padding.top,
                                bottom:op.padding.bottom
                            }
                        }
                    }
            }
        }
        this.chart=new Chart(canvas.children('canvas').get(0),{
            type:chartType,
            data:data,
            options:options || {}
        });
    }
};
window.chartColors = {
    red: 'rgb(255, 99, 132)',
    orange: 'rgb(255, 159, 64)',
    yellow: 'rgb(255, 205, 86)',
    green: 'rgb(75, 192, 192)',
    blue: 'rgb(54, 162, 235)',
    purple: 'rgb(153, 102, 255)',
    grey: 'rgb(201, 203, 207)'
};