/**
 * Created by Jacky.Gao on 2017-10-20.
 */
import ButtonInstance from './ButtonInstance.js';

export default class SubmitButtonInstance extends ButtonInstance{
    constructor(label){
        super(label);
        this.editorType="submit-button";
    }
    toJson(){
        return {
            label:this.label,
            style:this.style,
            align:this.align,
            type:SubmitButtonInstance.TYPE
        };
    }
    toXml(){
        return `<button-submit label="${this.label}" align="${this.align}" type="${SubmitButtonInstance.TYPE}" style="${this.style}"></button-submit>`;
    }
}
SubmitButtonInstance.TYPE="Submit-button";
