/**
 * Created by Jacky.Gao on 2017-10-16.
 */
import Property from './Property.js';
export default class TextProperty extends Property{
    constructor(report){
        super();
        this.init(report);
    }
    init(report){
        this.col.append(this.buildBindParameter());
        this.positionLabelGroup=this.buildPositionLabelGroup();
        this.col.append(this.positionLabelGroup);
        this.col.append(this.buildLabelGroup());
    }
    refreshValue(current){
        super.refreshValue(current);
        if(this.typeSelect){
            this.typeSelect.val(current.editorType);
        }
    }
}
