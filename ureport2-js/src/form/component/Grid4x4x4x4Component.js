/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import GridComponent from './GridComponent.js';
import Grid4x4x4x4Instance from '../instance/Grid4x4x4x4Instance.js';
export default class Grid4x4x4x4Component extends GridComponent{
    constructor(options){
        super(options);
    }
    newInstance(){
        return new Grid4x4x4x4Instance();
    }
    getType(){
        return Grid4x4x4x4Instance.TYPE;
    }
    getId(){
        this.id="component_grid4x4x4x4";
        return this.id;
    }
}
