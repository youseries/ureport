/**
 * Created by Jacky.Gao on 2017-10-20.
 */
import Component from './Component.js';
import SelectMultipleProperty from '../property/SelectMultipleProperty.js';
import Utils from '../Utils.js';
import SelectMultipleInstance from '../instance/SelectMultipleInstance.js';
export default class SelectMultipleComponent extends Component{
    constructor(options){
        super(options);
        this.property=new SelectMultipleProperty();
    }
    newInstance(){
        var seq=Utils.seq(this.id);
        return new SelectMultipleInstance(seq);
    }
    getType(){
        return SelectMultipleInstance.TYPE;
    }
    getId(){
        this.id= "multiple_select";
        return this.id;
    }
}