/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import GridComponent from './GridComponent.js';
import Grid3x3x3Instance from '../instance/Grid3x3x3Instance.js';
export default class Grid3x3x3Component extends GridComponent{
    constructor(options){
        super(options);
    }
    newInstance(){
        return new Grid3x3x3Instance();
    }
    getType(){
        return Grid3x3x3Instance.TYPE;
    }
    getId(){
        this.id="component_grid3x3x3";
        return this.id;
    }
}