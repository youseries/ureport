/**
 * Created by Jacky.Gao on 2017-06-27.
 */
import BarChartValueEditor from './BarChartValueEditor.js';
export default class PolarChartValueEditor extends BarChartValueEditor{
    constructor(parentContainer,context){
        super(parentContainer,context,'polar');
        this.axisLI.hide();
    }
}