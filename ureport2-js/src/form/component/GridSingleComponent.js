/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import GridComponent from './GridComponent.js';
import GridSingleInstance from '../instance/GridSingleInstance.js';
export default class GridSingleComponent extends GridComponent{
    constructor(options){
        super(options);
    }
    newInstance(){
        return new GridSingleInstance();
    }
    getType(){
        return GridSingleInstance.TYPE;
    }
    getId(){
        this.id="component_gridsingle";
        return this.id;
    }
}