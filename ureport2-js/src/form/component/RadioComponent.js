/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Component from './Component.js';
import RadioProperty from '../property/RadioProperty.js';
import RadioInstance from '../instance/RadioInstance.js';
export default class RadioComponent extends Component{
    constructor(options){
        super(options);
        this.property=new RadioProperty();
    }
    newInstance(){
        return new RadioInstance();
    }
    getType(){
        return RadioInstance.TYPE;
    }
    getId(){
        this.id="radio_component";
        return this.id;
    }
}