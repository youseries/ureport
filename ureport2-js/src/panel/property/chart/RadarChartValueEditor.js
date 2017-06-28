/**
 * Created by Jacky.Gao on 2017-06-27.
 */
import BarChartValueEditor from './BarChartValueEditor.js';
export default class RadarChartValueEditor extends BarChartValueEditor{
    constructor(parentContainer,context){
        super(parentContainer,context,'radar');
        this.axisLI.hide();
    }
}