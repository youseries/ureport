/**
 * Created by Jacky.Gao on 2017-01-25.
 */
import {alert} from './MsgBox.js';

export default class Context{
    constructor(reportTable){
        this.reportTable=reportTable;
        this.reportDef=reportTable.reportDef;
        this.hot=reportTable.hot;
        this.hot.context=this;
        this._initLetters();
        this.cellsMap=reportTable.cellsMap;
        this.rowHeaders=[];
    }

    _initLetters(){
        const letters=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
        this.LETTERS=letters.concat([]);
        for(let i=0;i<letters.length;i++){
            let name=letters[i];
            for(let j=0;j<letters.length;j++){
                this.LETTERS.push(name+letters[j]);
            }
        }
    }

    adjustInsertRowHeaders(row){
        for(let header of this.rowHeaders){
            if(header.rowNumber>=row){
                header.rowNumber+=1;
            }
        }
    }

    adjustDelRowHeaders(row){
        let targetHeader=null;
        for(let header of this.rowHeaders){
            if(header.rowNumber===row){
                targetHeader=header;
                break;
            }
        }
        if(targetHeader){
            const index=this.rowHeaders.indexOf(targetHeader);
            this.rowHeaders.splice(index,1);
        }
    }

    addRowHeader(row,band){
        let targetHeader=null;
        for(let header of this.rowHeaders){
            if(header.rowNumber===row){
                targetHeader=header;
                break;
            }
        }
        if(targetHeader){
            targetHeader.band=band;
        }else{
            const newHeader={band,rowNumber:row};
            this.rowHeaders.push(newHeader);
        }
    }

    getCellName(rowIndex,colIndex){
        if(rowIndex!=null){
            return this.LETTERS[colIndex]+(rowIndex+1);
        }else{
            return this.LETTERS[colIndex];
        }
    }

    getCell(rowIndex,colIndex){
        let key=(rowIndex+1) + ',' + (colIndex+1);
        return this.cellsMap.get(key);
    }

    addCell(cell){
        let key=cell.rowNumber+','+cell.columnNumber;
        this.cellsMap.set(key,cell);
    }

    removeCell(cell){
        let key=cell.rowNumber+','+cell.columnNumber;
        this.cellsMap.delete(key);
    }

    deleteCell(rowNumber,columnNumber){
        let key=rowNumber+','+columnNumber;
        this.cellsMap.delete(key);
    }

    getSelectedCells(){
        const selected=this.hot.getSelected();
        if(!selected){
            return null;
        }
        const startRow=selected[0], startCol=selected[1], endRow=selected[2], endCol=selected[3];
        const cells=[];
        for(let i=startRow;i<=endRow;i++){
            for(let j=startCol;j<=endCol;j++){
                const cell=this.hot.getCell(i,j,true);
                const exist=cells.indexOf(cell);
                if(exist===-1){
                    cells.push(cell);
                }
            }
        }
        return cells;
    }
}