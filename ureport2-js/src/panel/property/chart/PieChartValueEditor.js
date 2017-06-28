/**
 * Created by Jacky.Gao on 2017-06-27.
 */
import BarChartValueEditor from './BarChartValueEditor.js';
export default class PieChartValueEditor extends BarChartValueEditor{
    constructor(parentContainer,context){
        super(parentContainer,context,'pie');
        this.axisLI.hide();
    }
}