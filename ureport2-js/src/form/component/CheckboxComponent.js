/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Component from './Component.js';
import CheckboxInstance from '../instance/CheckboxInstance.js';
import CheckboxProperty from '../property/CheckboxProperty.js';
export default class CheckboxComponent extends Component{
    constructor(options){
        super(options);
        this.property=new CheckboxProperty();
    }
    newInstance(){
        return new CheckboxInstance();
    }
    getType(){
       return CheckboxInstance.TYPE;
    }
    getId(){
        this.id="checkbox_component";
        return this.id;
    }
}