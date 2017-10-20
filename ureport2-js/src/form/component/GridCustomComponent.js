/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import GridComponent from './GridComponent.js';
import GridCustomInstance from '../instance/GridCustomInstance.js';
export default class GridCustomComponent extends GridComponent{
    constructor(options){
        super(options);
    }
    newInstance(cols){
        return new GridCustomInstance(cols);
    }
    getType(){
        return GridCustomInstance.TYPE;
    }
    getId(){
        this.id="component_gridcustom";
        return this.id;
    }
}
