/**
 * Created by Jacky.Gao on 2017-10-20.
 */
import Component from './Component.js';
import SelectProperty from '../property/SelectProperty.js';
import Utils from '../Utils.js';
import SelectInstance from '../instance/SelectInstance.js';
export default class SelectComponent extends Component{
    constructor(options){
        super(options);
        this.property=new SelectProperty();
    }
    newInstance(){
        var seq=Utils.seq(this.id);
        return new SelectInstance(seq);
    }
    getType(){
        return SelectInstance.TYPE;
    }
    getId(){
        this.id= "single_select";
        return this.id;
    }
}