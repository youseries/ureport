/**
 * Created by Jacky.Gao on 2017-10-24.
 */
import FormBuilder from './FormBuilder.js';

$(document).ready(function(){
    const formBuilder=new FormBuilder($("#container"));
    formBuilder.initData(window.parent.__current_report_def);
});