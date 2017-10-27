/**
 * Created by Jacky.Gao on 2017-10-20.
 */
import ButtonInstance from './ButtonInstance.js';

export default class ResetButtonInstance extends ButtonInstance{
    constructor(label){
        super(label);
        this.editorType="reset-button";
    }
    toJson(){
        return {
            label:this.label,
            style:this.style,
            type:ResetButtonInstance.TYPE
        };
    }
    toXml(){
        return `<button-reset label="${this.label}" style="${this.style}"></button-reset>`;
    }
}
ResetButtonInstance.TYPE='Reset-button';