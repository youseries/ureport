/**
 * Created by Jacky.Gao on 2017-10-23.
 */
import Component from './Component.js';
import DatetimeProperty from '../property/DatetimeProperty.js';
import DatetimeInstance from '../instance/DatetimeInstance.js';
export default class DatetimeComponent extends Component{
    constructor(options){
        super(options);
        this.property=new DatetimeProperty();
    }
    newInstance(){
        return new DatetimeInstance();
    }
    getType(){
        return DatetimeInstance.TYPE;
    }
    getId(){
        this.id="datetime_component";
        return this.id;
    }
}