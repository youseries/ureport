/**
 * Created by Jacky.Gao on 2017-10-20.
 */
import Component from './Component.js';
import ResetButtonInstance from '../instance/ResetButtonInstance.js';
import ButtonProperty from '../property/ButtonProperty.js';
import Utils from '../Utils.js';
export default class ResetButtonComponent extends Component{
    constructor(options){
        super(options);
        this.property=new ButtonProperty();
    }
    newInstance(){
        var seq=Utils.seq(this.id);
        return new ResetButtonInstance("重置"+seq);
    }
    getType(){
        return ResetButtonInstance.TYPE;
    }
    getId(){
        this.id="reset_button";
        return this.id;
    }
}