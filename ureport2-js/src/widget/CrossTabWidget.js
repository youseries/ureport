/**
 * Created by Jacky.Gao on 2017-03-15.
 */
import Raphael from 'raphael';
import saveSvgAsPng from 'save-svg-as-png';

export default class CrossTabWidget{
    constructor(context,rowIndex,colIndex,cellDef){
        this.context=context;
        this.hot=context.hot;
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
        this.refreshCell(cellDef);
    }
    refreshCell(cellDef){
        const $td=$(this.hot.getCell(this.rowIndex,this.colIndex));
        this.rowSpan=$td.prop('rowspan'),this.colSpan=$td.prop('colspan');
        this.width=-2,this.height=-4;
        const rowStart=this.rowIndex,rowEnd=this.rowIndex+this.rowSpan;
        for(let i=rowStart;i<rowEnd;i++){
            this.height+=this.hot.getRowHeight(i);
        }
        const colStart=this.colIndex,colEnd=this.colIndex+this.colSpan;
        for(let i=colStart;i<colEnd;i++){
            this.width+=this.hot.getColWidth(i);
        }
        if(!cellDef){
            this._buildSlashes();
        }
    }
    _buildSlashes(){
        const colStart=this.colIndex,colEnd=this.colIndex+this.colSpan;
        let colWidth=0;
        for(let i=colStart;i<colEnd;i++){
            colWidth+=this.hot.getColWidth(i);
        }
        let rowHeight=0;
        const rowStart=this.rowIndex,rowEnd=this.rowIndex+this.rowSpan;
        for(let i=rowStart;i<rowEnd;i++){
            rowHeight+=this.hot.getRowHeight(i);
        }
        let index=1;
        const slashes=[];
        for(let i=0;i<this.rowSpan;i++){
            let height=0;
            for(let j=0;j<i;j++){
                height+=this.hot.getRowHeight(j);
            }
            if(i===0 || i+1<this.rowSpan){
                height+=8;
            }else{
                height-=3;
            }
            const degree=this._computeDegree(colWidth,height);
            const width=this.hot.getColWidth(this.colIndex+(this.colSpan-1));
            const x=parseInt(colWidth-30);
            slashes.push({
                degree,
                x,
                y:height,
                text:'项目'+index
            });
            index++;
        }
        const degree=this._computeDegree(colWidth,rowHeight);
        let x=colWidth;
        if(this.colSpan>1){
            x-=this.hot.getColWidth(this.colIndex+(this.colSpan-1));
        }else{
            x-=parseInt(x/5);
        }
        let y=rowHeight;
        if(this.rowSpan>1){
            y-=parseInt(this.hot.getRowHeight(this.rowIndex+(this.rowSpan-1))/2)+5;
        }else{
            y-=parseInt(y/2);
        }
        slashes.push({
            degree,
            x,
            y,
            text:'项目'+index
        });
        index++;
        for(let i=0;i<this.colSpan;i++){
            let width=0;
            for(let j=0;j<i;j++){
                width+=this.hot.getColWidth(j);
            }
            width+=20;
            const degree=this._computeDegree(rowHeight,width);
            const y=rowHeight-20;
            slashes.push({
                degree,
                x:width,
                y,
                text:'项目'+index
            });
            index++;
        }
        const cellDef=this.context.getCell(this.rowIndex,this.colIndex);
        const cellStyle=cellDef.cellStyle;
        cellDef.value={
            slashes,
            type:'slash'
        };
    }
    doDraw(cellDef){
        const slashValue=cellDef.value;
        const cellStyle=cellDef.cellStyle;
        if(!cellStyle.forecolor){
            cellStyle.forecolor='0,0,0';
        }
        let index=0;
        const td=this.hot.getCell(this.rowIndex,this.colIndex);
        const $td=$(td);
        $td.empty();
        const container=$(`<div></div>`);
        $td.append(container);
        this.paper=Raphael(container.get(0),this.width,this.height);
        /*
        if(cellStyle.bgcolor){
            let colWidth=0;
            const colStart=this.colIndex,colEnd=this.colIndex+this.colSpan;
            for(let i=colStart;i<colEnd;i++){
                colWidth+=this.hot.getColWidth(i);
            }
            let rowHeight=0;
            const rowStart=this.rowIndex,rowEnd=this.rowIndex+this.rowSpan;
            for(let i=rowStart;i<rowEnd;i++){
                rowHeight+=this.hot.getRowHeight(i);
            }
            const color=rgbToHex(cellStyle.bgcolor);
            this.paper.rect("0","0",colWidth,rowHeight).attr({stroke:color,fill:color});
        }
        */
        let fontStyle=cellStyle.fontSize+"pt "+(cellStyle.fontFamily ? cellStyle.fontFamily : "宋体");
        let bold=cellStyle.bold ? 'bold' : 'normal';
        let italic=cellStyle.italic ? 'italic' : 'normal';
        let underline=cellStyle.underline ? 'underline' : 'none';
        let textStyle={
            'fill':rgbToHex(cellStyle.forecolor),
            'font':fontStyle,
            'font-weight':bold,
            'font-style' : italic,
            'text-decoration' : underline
        };
        const slashes=slashValue.slashes;
        for(let i=0;i<(this.rowSpan-1);i++){
            let h=0;
            for(let j=0;j<=i;j++){
                h+=this.hot.getRowHeight(this.rowIndex+j);
            }
            this.paper.path("M0 0L"+this.width+" "+h).attr({stroke:rgbToHex(cellStyle.forecolor)});
            let slash=slashes[index];
            let text=this.paper.text(0,0,slash.text).attr(textStyle);
            text.attr({
                transform:'T'+slash.x+","+slash.y+"R"+slash.degree
            });
            index++;
        }
        let h=this.height-(this.hot.getRowHeight(this.rowIndex+(this.rowSpan-1)))/3;
        this.paper.path("M0 0L"+this.width+" "+h).attr({stroke:rgbToHex(cellStyle.forecolor)});

        let slash=slashes[index];
        index++;
        let text=this.paper.text(0,0,slash.text).attr(textStyle);
        text.attr({
            transform:'T'+slash.x+","+slash.y+"R"+slash.degree
        });

        let w=this.width-(this.hot.getColWidth(this.colIndex+(this.colSpan-1)))/3;
        this.paper.path("M0 0L"+w+" "+this.height).attr({stroke:rgbToHex(cellStyle.forecolor)});

        slash=slashes[index];
        index++;
        text=this.paper.text(0,0,slash.text).attr(textStyle);
        text.attr({
            transform:'T'+slash.x+","+slash.y+"R"+slash.degree
        });

        for(let i=0;i<(this.colSpan-1);i++){
            let w=0;
            for(let j=0;j<=i;j++){
                w+=this.hot.getColWidth(this.colIndex+j);
            }
            this.paper.path("M0 0L"+w+" "+this.height).attr({stroke:rgbToHex(cellStyle.forecolor)});

            slash=slashes[index];
            index++;
            text=this.paper.text(0,0,slash.text).attr(textStyle);
            text.attr({
                transform:'T'+slash.x+","+slash.y+"R"+slash.degree
            });
        }

        slash=slashes[index];
        index++;
        text=this.paper.text(0,0,slash.text).attr(textStyle);
        text.attr({
            transform:'T'+slash.x+","+slash.y+"R"+slash.degree
        });
        const svg=container.children('svg').get(0);
        saveSvgAsPng.svgAsPngUri(svg,{encoderOptions:1},function(base64Data){
            slashValue.base64Data=base64Data;
        });
    }

    _computeDegree(a,b){
        const c=Math.sqrt(a*a+b*b);
        const sin=Math.sin(b/c);
        const degree=(180/Math.PI)*Math.asin(sin);
        return parseInt(degree);
    }
}

function rgbToHex(rgb) {
    rgb=rgb.split(',');
    const r=parseInt(rgb[0]),g=parseInt(rgb[1]),b=parseInt(rgb[2]);
    return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
}

function componentToHex(c) {
    var hex = c.toString(16);
    return hex.length == 1 ? "0" + hex : hex;
}