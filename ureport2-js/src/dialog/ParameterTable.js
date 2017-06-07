/**
 * Created by Jacky.Gao on 2017-02-06.
 */
import ParameterDialog from './ParameterDialog.js';

export default class ParameterTable{
    constructor(container,data){
        this.data=data;
        const addButton=$(`<button type="button" class="btn btn-info">添加参数</button>`);
        container.append(addButton);
        const _this=this;
        const table=$(`<table class="table table-bordered">
            <thead>
                <tr style="background: #f4f4f4;height: 30px;"><td style="vertical-align: middle">参数名</td><td style="vertical-align: middle">数据类型</td><td style="vertical-align: middle">默认值</td><td style="vertical-align: middle">删除</td></tr>
            </thead>
        </table>`);
        this.body=$(`<tbody></tbody>`);
        table.append(this.body);
        this._buildParameters();
        container.append(table);
        const parameterDialog=new ParameterDialog(data);
        addButton.click(function(){
            parameterDialog.show(function(name,type,defaultValue){
                const obj={name,type,defaultValue};
                data.push(obj);
                const newTr=$(`<tr style="height: 35px;"><td style="vertical-align: middle">${name}</td><td style="vertical-align: middle">${type}</td><td style="vertical-align: middle">${defaultValue}</td></tr>`);
                const removeTd=$(`<td style="vertical-align: middle"></td>`);
                const removeSpan=$(`<span><a href="###"><i class="glyphicon glyphicon-trash" style="font-size: 12pt;color: #d30a16;"></a></span>`);
                removeTd.append(removeSpan);
                newTr.append(removeTd);
                _this.body.append(newTr);
                removeSpan.click(function(){
                    let index=data.indexOf(obj);
                    data.splice(index,1);
                    newTr.remove();
                })
            })
        });
    }
    refreshData(){
        this.body.empty();
        this._buildParameters();
    }
    _buildParameters(){
        const _this=this;
        for(let param of this.data){
            const tr=$(`<tr><td>${param.name}</td><td>${param.type}</td><td>${param.defaultValue}</td></tr>`);
            const deleteTd=$(`<td></td>`);
            const deleteSpan=$(`<span><a href="###"><i class="glyphicon glyphicon-trash" style="font-size: 12pt;color: #d30a16;"></a></span>`);
            deleteTd.append(deleteSpan);
            tr.append(deleteTd);
            deleteSpan.click(function(){
                let index=_this.data.indexOf(param);
                _this.data.splice(index,1);
                tr.remove();
            });
            this.body.append(tr);
        }
    }
}