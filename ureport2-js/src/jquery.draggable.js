/**
 * Created by Jacky.gao on 2016/7/12.
 */
(function ($) {
    $.fn.draggable = function (options) {
        var settings = $.extend({handle: 0, exclude: 0}, options);
        return this.each(function () {
            var dx, dy, el = $(this), handle = settings.handle ? $(settings.handle, el) : el;
            handle.on({
                mousedown: function (e) {
                    var target=e.target;
                    var className='',iscm=false;
                    if(target){
                        className=target.className;
                        if(className){
                            className=className.trim();
                            if(className.indexOf('cm-')>-1){
                                iscm=true;
                            }else if(className.indexOf('CodeMirror-')>-1){
                                iscm=true;
                            }
                        }
                    }
                    if(className && (className==='form-control' || className==='presentation' || iscm)){
                        return;
                    }
                    if(className.indexOf("ureport")>-1  || className.indexOf("ds_name")>-1 || className.indexOf("dataset_name")>-1 || target.tagName==="A"){
                        return;
                    }
                    if(target && $(target).attr('role')==='presentation'){
                        return;
                    }
                    if (settings.exclude && ~$.inArray(e.target, $(settings.exclude, el))) return;
                    var os = el.offset();
                    dx = e.pageX - os.left, dy = e.pageY - os.top;
                    $(document).on('mousemove.drag', function (e) {
                        el.offset({top: e.pageY - dy, left: e.pageX - dx});
                    });
                },
                mouseup: function (e) {
                    $(document).off('mousemove.drag');
                }
            });
        });
    }
}(jQuery));