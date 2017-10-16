/**
 * Created by Jacky.Gao on 2017-02-07.
 */
import {setDirty} from '../../Utils.js';

export default class SlashValueEditor{
    constructor(parentContainer,context){
        this.context=context;
        this.container=$(`<div><label>${window.i18n.property.slash.content}</label></div>`);
        parentContainer.append(this.container);
        this.headerContainer=$(`<div></div>`);
        this.container.append(this.headerContainer);
        this.container.hide();
    }
    show(cellDef,rowIndex,colIndex,row2Index,col2Index){
        this.cellDef=cellDef;
        this.rowIndex=rowIndex;
        this.colIndex=colIndex;
        this.container.show();
        this.headerContainer.empty();
        const slashContainer=$(`<div></div>`);
        this.headerContainer.append(slashContainer);
        this._buildSlashes(cellDef,slashContainer);
        const _this=this;
        const refreshButton=$(`<button type="button" class="btn btn-danger" style="margin-bottom: 10px;margin-top: 10px;float: right"><i class="glyphicon glyphicon-refresh"></i> ${window.i18n.property.slash.refresh}</button>`);
        refreshButton.click(function(){
            const crossTabWidget=cellDef.crossTabWidget;
            crossTabWidget.refreshCell();
            crossTabWidget.doDraw(cellDef,rowIndex,colIndex);
            slashContainer.empty();
            _this._buildSlashes(cellDef,slashContainer);
        });
        this.headerContainer.append(refreshButton);
    }

    _buildSlashes(cellDef,slashContainer){
        const slashes=cellDef.value.slashes;
        for(let slash of slashes){
            const itemContainer=$(`<div style="margin-left: 10px;margin-top: 10px;"></div>`);
            slashContainer.append(itemContainer);
            const nameContainer=$(`<span>${window.i18n.property.slash.name}</span>`);
            itemContainer.append(nameContainer);
            const nameEditor=$(`<input type="text" class="form-control" style="width:90px;display: inline-block;padding: 5px;height:28px">`);
            nameContainer.append(nameEditor);
            nameEditor.val(slash.text);
            nameEditor.change(function(){
                slash.text=$(this).val();
                const crossTabWidget=cellDef.crossTabWidget;
                crossTabWidget.doDraw(cellDef);
                setDirty();
            });

            const xContainer=$(`<span style="margin-left: 8px">X:</span>`);
            itemContainer.append(xContainer);
            const xEditor=$(`<input type="number" class="form-control" style="display: inline-block;width:50px;padding: 1px;height:28px">`);
            xContainer.append(xEditor);
            xEditor.change(function(){
                slash.x=$(this).val();
                const crossTabWidget=cellDef.crossTabWidget;
                crossTabWidget.doDraw(cellDef);
                setDirty();
            });
            xEditor.val(slash.x);

            const yContainer=$(`<span style="margin-left: 8px">Y:</span>`);
            itemContainer.append(yContainer);
            const yEditor=$(`<input type="number" class="form-control" style="display: inline-block;width:50px;padding: 1px;height:28px">`);
            yContainer.append(yEditor);
            yEditor.change(function(){
                slash.y=$(this).val();
                const crossTabWidget=cellDef.crossTabWidget;
                crossTabWidget.doDraw(cellDef);
                setDirty();
            });
            yEditor.val(slash.y);

            const degreeContainer=$(`<span style="margin-left: 8px">${window.i18n.property.slash.angle}</span>`);
            itemContainer.append(degreeContainer);
            const degreeEditor=$(`<input type="number" class="form-control" style="display: inline-block;width:50px;padding: 1px;height:28px">`);
            degreeContainer.append(degreeEditor);
            degreeEditor.change(function(){
                slash.degree=$(this).val();
                const crossTabWidget=cellDef.crossTabWidget;
                crossTabWidget.doDraw(cellDef);
                setDirty();
            });
            degreeEditor.val(slash.degree);
        }
    }

    hide(){
        this.container.hide();
    }
}