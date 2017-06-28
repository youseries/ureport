/**
 * Created by Jacky.Gao on 2017-01-31.
 */
import CrossTabWidget from '../widget/CrossTabWidget.js';
import ChartWidget from '../widget/ChartWidget.js';

export function afterRenderer(td,row,col,prop,value,cellProperties){
    if(!this.context){
        return;
    }
    let cellDef=this.context.getCell(row,col);
    if(!cellDef){
        return;
    }

    const cellStyle=cellDef.cellStyle,cellValue=cellDef.value;
    const $td=$(td),valueType=cellValue.type;
    let tip='';
    if(valueType==='dataset'){
        tip=cellValue.datasetName+"."+cellValue.aggregate+"(";
        let prop=cellValue.property;
        tip+=prop;
    }else if(valueType==='expression'){
        tip=cellValue.value || '';
    }else if(valueType==='image'){
        tip='图片：'+cellValue.value;
        const imagePath=window._server+'/res/asserts/icons/image.svg';
        const image=$(`<img src="${imagePath}" width="40px">`);
        $td.append(image);
    }else if(valueType==='slash'){
        tip='斜线表头';
        if(!cellDef.crossTabWidget){
            cellDef.crossTabWidget=new CrossTabWidget(this.context,row,col,cellDef);
        }
        cellDef.crossTabWidget.doDraw(cellDef);
    }else if(valueType==='zxing'){
        let imagePath=window._server+'/res/asserts/icons/qrcode.svg';
        tip='二维码';
        if(cellValue.category==='barcode'){
            tip='条码';
            imagePath=window._server+'/res/asserts/icons/barcode.svg';
        }
        const width=cellValue.width,height=cellValue.height;
        const image=$(`<img src="${imagePath}" width="${width}px" height="${height}px">`);
        $td.append(image);
    }else if(valueType==='chart'){
        tip='图表';
        if(!cellDef.chartWidget){
            cellDef.chartWidget=new ChartWidget($td,cellDef);
        }
        cellDef.chartWidget.renderChart();
    }else{
        tip=cellValue.value || "";
    }
    $td.prop('title',tip);
    if(valueType==="simple"){
        let text=$td.text();
        if(text && text!==""){
            text=text.replace(new RegExp('\r\n','gm'),'<br>');
            text=text.replace(new RegExp('\n','gm'),'<br>');
            text=text.replace(new RegExp(' ','gm'),'&nbsp;');
            $td.html(text);
        }
    }
    $td.css({'word-break':'break-all','line-height':'normal','white-space':'nowrap',padding:'0 1px'});
    if(cellDef.expand === 'Down'){
        let url=window._server+"/res/asserts/icons/expr-expand-down.svg";
        if(valueType==='dataset'){
            url=window._server+"/res/asserts/icons/expand-down.svg";
        }
        $td.prepend(`<image src="${url}"></image>`);
    }else if(cellDef.expand === 'Right'){
        let url=window._server+"/res/asserts/icons/expr-expand-right.svg";
        if(valueType==='dataset'){
            url=window._server+"/res/asserts/icons/expand-right.svg";
        }
        $td.prepend(`<image src="${url}" style="display: block;"></image>`);
    }else{
        if(valueType==='dataset'){
            let url=window._server+"/res/asserts/icons/property.svg";
            $td.prepend(`<image src="${url}" style="display: inline-block;"></image>`);
        }else if(valueType==='expression'){
            let url=window._server+"/res/asserts/icons/expression.svg";
            $td.prepend(`<image src="${url}" style="display: inline-block;"></image>`);
        }
    }
    if(cellStyle.align){
        $td.css("text-align",cellStyle.align);
    }
    if(cellStyle.valign){
        $td.css("vertical-align",cellStyle.valign);
    }
    if(cellStyle.bold){
        $td.css("font-weight",'bold');
    }
    if(cellStyle.italic){
        $td.css("font-style",'italic');
    }
    if(cellStyle.underline){
        $td.css("text-decoration",'underline');
    }
    if(cellStyle.forecolor){
        $td.css("color","rgb("+cellStyle.forecolor+")");
    }
    if(cellStyle.bgcolor){
        $td.css("background-color","rgb("+cellStyle.bgcolor+")");
    }
    if(cellStyle.fontSize){
        $td.css("font-size",cellStyle.fontSize+"pt");
    }
    if(cellStyle.fontFamily){
        $td.css("font-family",cellStyle.fontFamily);
    }

    const leftBorder=cellStyle.leftBorder;
    if(leftBorder){
        if(leftBorder==='' || leftBorder.style==="none"){
            $td.css({
                'border-left':''
            });
        }else{
            let borderStyle='double';
            let borderWidth=leftBorder.width;
            if(borderWidth===null || borderWidth===undefined || borderWidth===''){
                borderWidth=0;
            }else{
                borderWidth=parseInt(borderWidth);
            }
            if(leftBorder.style!=='solid' && borderWidth>0){
                borderStyle=leftBorder.style;
                borderWidth++;
            }
            let style=borderStyle+" "+ borderWidth+ "px rgb("+ leftBorder.color+")";
            $td.css({
                'border-left':style
            });
        }
    }

    const rightBorder=cellStyle.rightBorder;
    if(rightBorder){
        if(rightBorder==='' || rightBorder.style==="none"){
            $td.css({
                'border-right':''
            });
        }else{
            let style=rightBorder.style+" "+ rightBorder.width+ "px rgb("+ rightBorder.color+")";
            $td.css({
                'border-right':style
            });
        }
    }
    const topBorder=cellStyle.topBorder;
    if(topBorder){
        if(topBorder==='' || topBorder.style==="none"){
            $td.css({
                'border-top':''
            });
        }else{
            let borderStyle='double';
            let borderWidth=topBorder.width;
            if(borderWidth===null || borderWidth===undefined || borderWidth===''){
                borderWidth=0;
            }else{
                borderWidth=parseInt(borderWidth);
            }
            if(topBorder.style!=='solid' && borderWidth>0){
                borderStyle=topBorder.style;
                borderWidth++;
            }
            let style=borderStyle+" "+ borderWidth+ "px rgb("+ topBorder.color+")";
            $td.css({
                'border-top':style
            });
        }
    }
    const bottomBorder=cellStyle.bottomBorder;
    if(bottomBorder){
        if(bottomBorder==='' || bottomBorder.style==="none"){
            $td.css({
                'border-bottom':''
            });
        }else{
            let style=bottomBorder.style+" "+ bottomBorder.width+ "px rgb("+ bottomBorder.color+")";
            $td.css({
                'border-bottom':style
            });
        }
    }
};