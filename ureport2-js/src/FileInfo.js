/**
 * Created by Jacky.Gao on 2017-03-01.
 */
export default class FileInfo{
    setFile(name){
        if(!this.fileInfo){
            this.fileInfo=$(`<div style="float: right;font-size: 12px;color: #009688;margin-top: 8px;margin-right: 5px"></div>`);
            $('.ud-toolbar').append(this.fileInfo);
        }
        let suffix='.ureport.xml';
        let pos=name.indexOf(suffix);
        if(pos>-1){
            name=name.substring(0,pos);
        }
        name=decodeURI(decodeURI(name));
        this.fileInfo.html('报表:'+name);
    }
};