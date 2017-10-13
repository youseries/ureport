/**
 * Created by Jacky.Gao on 2017-10-12.
 */
import Component from './Component.js'
import TabProperty from '../property/TabProperty.js';
import TabControlInstance from '../instance/TabControlInstance.js';
import Utils from '../Utils.js';

export default class TabControlComponent extends Component{
    constructor(options){
        super(options);
        this.property=new TabProperty();
    }
    newInstance(){
        const seq=Utils.seq(this.id);
        return new TabControlInstance(seq);
    }
    getType(){
        return TabControlInstance.TYPE;
    }
}