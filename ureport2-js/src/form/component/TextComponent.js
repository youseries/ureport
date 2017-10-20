/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Component from './Component.js';
import TextInstance from '../instance/TextInstance.js';
import TextProperty from '../property/TextProperty.js';
import Utils from '../Utils.js';
export default class TextComponent extends Component{
    constructor(options){
        super(options);
        this.property=new TextProperty();
    }
    newInstance(){
        var seq=Utils.seq(this.id);
        return new TextInstance("输入框"+seq);
    }
    getType(){
        return TextInstance.TYPE;
    }
    getId(){
        this.id="component_texteditor";
        return this.id;
    }
}
