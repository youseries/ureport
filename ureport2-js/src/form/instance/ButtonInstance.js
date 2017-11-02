/**
 * Created by Jacky.Gao on 2017-10-20.
 */
import Instance from './Instance.js';

export default class ButtonInstance extends Instance{
    constructor(label){
        super();
        this.element=$('<div></div>');
        this.label=label;
        this.style="btn-default";
        this.button=$(`<button type='button' class='btn btn-default btn-sm'>${label}</button>`);
        this.element.append(this.button);
        this.element.uniqueId();
        this.id=this.element.prop("id");
        this.editorType="button";
        this.align='left';
    }
    setStyle(style){
        this.button.removeClass(this.style);
        this.button.addClass(style);
        this.style=style;
    }
    setAlign(align){
        this.element.css('text-align',align);
        this.align=align;
    }
    setLabel(label){
        this.label=label;
        this.button.html(label);
    }
    initFromJson(json){
        this.setLabel(json.label);
        this.setStyle(json.style);
        this.setAlign(json.align);
    }
    toJSON(){

    }
}