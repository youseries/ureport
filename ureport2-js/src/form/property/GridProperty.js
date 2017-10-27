/**
 * Created by Jacky.Gao on 2017-10-15.
 */
import Property from './Property.js';
export default class GridProperty extends Property{
    constructor(){
        super();
        this.init();
    }
    init(){
        var showBorderGroup=$("<div class='form-group'><label>显示边线</label></div>");
        this.col.append(showBorderGroup);
        var showLineRadioGroup=$("<div class='checkbox-inline'>");
        showBorderGroup.append(showLineRadioGroup);
        var radioName="show_grid_line_radio_";
        this.showBorderRadio=$("<span style='margin-right: 10px'>是<input type='radio' name='"+radioName+"'></span>");
        showBorderGroup.append(this.showBorderRadio);
        var self=this;
        this.showBorderRadio.change(function(){
            var value=$(this).find("input").prop("checked");
            if(value){
                self.current.showBorder=true;
                self.borderPropGroup.show();
                self.borderWidthText.val(self.current.borderWidth);
                self.borderColorText.val(self.current.borderColor);
                self.current.setBorderWidth(self.current.borderWidth);
            }
        });

        this.hideBorderRadio=$("<span>否<input type='radio' name='"+radioName+"'></span>");
        showBorderGroup.append(this.hideBorderRadio);
        this.hideBorderRadio.change(function(){
            var value=$(this).find("input").prop("checked");
            if(value){
                self.current.showBorder=false;
                self.borderPropGroup.hide();
                self.current.setBorderWidth();
            }
        });

        this.borderPropGroup=$("<div>");
        this.col.append(this.borderPropGroup);
        var borderWidthGroup=$("<div class='form-group'><label>边线宽度(单位px)</label></div>");
        this.borderWidthText=$("<input type='number' class='form-control'>");
        borderWidthGroup.append(this.borderWidthText);
        this.borderPropGroup.append(borderWidthGroup);
        this.borderWidthText.change(function(){
            var width=$(this).val();
            self.current.setBorderWidth(width);
        });

        var borderColorGroup=$("<div class='form-group'><label>边线颜色</label></div>")
        this.borderPropGroup.append(borderColorGroup);
        this.borderColorText=$("<input type='color' class='form-control'>");
        borderColorGroup.append(this.borderColorText);
        this.borderColorText.change(function(){
            var color=$(this).val();
            self.current.setBorderColor(color);
        });
        this.borderPropGroup.hide();
    }
    refreshValue(current){
        this.current=current;
        if(current.showBorder){
            this.showBorderRadio.find("input").prop("checked",true);
            this.borderPropGroup.show();
            this.borderWidthText.val(current.borderWidth);
            this.borderColorText.val(current.borderColor);
        }else{
            this.hideBorderRadio.find("input").prop("checked",true);
            this.borderPropGroup.hide();
        }
    }
}
