/**
 * Created by Jacky.Gao on 2017-10-20.
 */
import Component from './Component.js';
import SubmitButtonInstance from '../instance/SubmitButtonInstance.js';
import ButtonProperty from '../property/ButtonProperty.js';
import Utils from '../Utils.js';
export default class SubmitButtonComponent extends Component{
    constructor(options){
        super(options);
        this.property=new ButtonProperty();
    }
    newInstance(){
        var seq=Utils.seq(this.id);
        return new SubmitButtonInstance("提交"+seq);
    }
    getType(){
        return SubmitButtonInstance.TYPE;
    }
    getId(){
        this.id="submit_button";
        return this.id;
    }
}