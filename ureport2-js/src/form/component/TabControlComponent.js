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
        this.tool.attr(Component.TAB,"1");
        this.property=new TabProperty();
    }
    newInstance(){
        const seq=Utils.seq(this.getId());
        return new TabControlInstance(seq);
    }
    getType(){
        return TabControlInstance.TYPE;
    }
    getId(){
        this.id="tab_control_component";
        return this.id;
    }
}