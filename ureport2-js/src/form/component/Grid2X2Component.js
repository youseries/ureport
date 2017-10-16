/**
 * Created by Jacky.Gao on 2017-10-15.
 */
import GridComponent from './GridComponent.js';
import Grid2X2Instance from '../instance/Grid2X2Instance.js';
export default class Grid2X2Component extends GridComponent{
    constructor(options){
        super(options);
    }
    getId(){
        this.id="component_grid2x2";
        return this.id;
    }
    newInstance(){
        return new Grid2X2Instance();
    }
    getType(){
        return Grid2X2Instance.TYPE;
    }
}