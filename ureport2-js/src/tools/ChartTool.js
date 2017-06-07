/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import Tool from './Tool.js';

export default class ChartTool extends Tool{
    execute(){

    }
    buildButton(){
        const group=$(`<div class="btn-group"></div>`);
        const mainBtn=$(`<button type="button" class="btn btn-default dropdown-toggle" style="border:none;border-radius:0;background: #f8f8f8;padding: 6px 5px;" data-toggle="dropdown" title="图表">
            <i class="ureport ureport-pie" style="color: #0e90d2;"></i>
            <span class="caret"></span>
        </button>`);
        const ul=$(`<ul class="dropdown-menu" role="menu"></ul>`);
        const pie=$(`<li>
                <a href="###">
                    <i class="ureport ureport-pie" style="color: #0e90d2;"></i> 饼状图
                </a>
            </li>`);
        ul.append(pie);
        const line=$(`<li>
                <a href="###">
                    <i class="ureport ureport-line" style="color: #0e90d2;"></i> 曲线图
                </a>
            </li>`);
        ul.append(line);
        const bar=$(`<li>
                <a href="###">
                    <i class="ureport ureport-bar" style="color: #0e90d2;"></i> 柱状图
                </a>
            </li>`);
        ul.append(bar);
        const horBar=$(`<li>
                <a href="###">
                    <i class="ureport ureport-horizontal-bar" style="color: #0e90d2;"></i> 水平柱状图
                </a>
            </li>`);
        ul.append(horBar);
        const area=$(`<li>
                <a href="###">
                    <i class="ureport ureport-area" style="color: #0e90d2;"></i> 面积图
                </a>
            </li>`);
        ul.append(area);
        const radar=$(`<li>
                <a href="###">
                    <i class="ureport ureport-radar" style="color: #0e90d2;"></i> 雷达图
                </a>
            </li>`);
        ul.append(radar);
        const polar=$(`<li>
                <a href="###">
                    <i class="ureport ureport-polar" style="color: #0e90d2;"></i> 极坐标图
                </a>
            </li>`);
        ul.append(polar);
        const scatter=$(`<li>
                <a href="###">
                    <i class="ureport ureport-scatter" style="color: #0e90d2;"></i> 散点图
                </a>
            </li>`);
        ul.append(scatter);
        const bubble=$(`<li>
                <a href="###">
                    <i class="ureport ureport-bubble" style="color: #0e90d2;"></i> 气泡图
                </a>
            </li>`);
        ul.append(bubble);

        group.append(mainBtn);
        group.append(ul);
        return group;
    }
}