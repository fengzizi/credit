var oldpassWord,accountRole,personalData,resultData,imageFlag,imageUrl;
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'code', 'laydate','upload'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,upload = layui.upload,
        laypage = layui.laypage,
        $ = layui.jquery;

    var id = getQueryString("id");//id为0时代表新增

    var type = getQueryString("type");
    if (type === "select") {
        $("input").attr("readonly", "readonly");
        $("select").attr("disabled", "disabled");
        $(".update").show();
        $("#submit_div").hide();
        $("#test10").hide();
    } else {
        $("#createTime").parent().parent().hide();
        $("#submit_div").show();

    };
    $("#createTime").attr("readonly", "readonly");

    var uploadListIns = upload.render({
        elem: '#test10'
        , url: '/manage/tbanner/uploadImg'
        , size: 2048 //限制文件大小，单位 KB
        , auto: false
        , multiple: false
        , accept:"images"
        , field:"imageFile"//文件名
        , acceptMime: 'image/*'
        , bindAction: '#testListAction2'
        , choose: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                var htm = '<span style="position: relative;display: inline-block;">' +
                    '<img src="' + result + '" style="height: 106px;" class="layui-upload-img">' +
                    '<span id="delete_' + index + '" style="position: absolute;top: 0px;right: 0px;width: 24px;height: 24px;line-height: 24px;text-align: center;background: #121110;color: white;"> <i class="layui-icon" style="font-size: 20px;font-weight: bolder;">&#x1006;</i></span>' +
                    '</span>\n';
                $("#demo2").html(htm);
                $("#demo2").parent().css("display", "inline-block");
                imageFlag=true;
                //删除
                $("#delete_" + index).on('click', function () {
                    $(this).parent().remove();
                    if ($("#demo2").children().length == 0) {
                        $("#demo2").parent().css("display", "none");
                    }
                    imageFlag=false;
                    imageUrl="";
                    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                });
            });
        }
        , done: function (res, index, upload) {
            //上传完毕
            if (res.code == 1) {//成功
                console.log(res)
                if(res.data!=null){
                    imageUrl=res.data.imageUrl;
                }
            }
        }
    });
    if(id!=0){
        //加载数据
        $.get("/manage/tbanner/selectOne", {id: id}, function (result) {
            if (result.code == 1) {
                if((result.data!=null&&localStorage.getItem("loginName")!="admin"&&result.data.loginName!=localStorage.getItem("loginName"))||type === "select"){//普通管理员不能修改其他管理员
                    $("input").attr("readonly", "readonly");
                    $("select").attr("disabled", "disabled");
                    $("#submit_div").hide();
                }else{
                    $("#submit_div").show();
                    $("#passWord").blur(function () {
                        var value = $(this).val();
                        if (value == null || value == "") {
                            if (oldpassWord != null && oldpassWord != "") {
                                $(this).val("******");
                            }
                        }
                    });
                    $("#passWord").focus(function () {
                        if ($(this).val() == "******") {
                            $(this).val("");
                        }
                    });
                }
                if(result.data!=null&&result.data.loginName==localStorage.getItem("loginName")){//管理员不能修改自己的状态
                    $("#state").attr("disabled", "disabled");
                }
                if(result.data!=null){
                    id=result.data.id;
                    if(result.data.picUrl!=null&&result.data.picUrl!=''){
                        var htm= '<span style="position: relative;display: inline-block;">' +
                            '<img src="' + result.data.picUrl + '" style="height: 106px;" class="layui-upload-img">' +
                            '<span class="delete" style="position: absolute;top: 0px;right: 0px;width: 24px;height: 24px;line-height: 24px;text-align: center;background: #121110;color: white;"> <i class="layui-icon" style="font-size: 20px;font-weight: bolder;">&#x1006;</i></span>' +
                            '</span>\n';
                        $("#demo2").html(htm);
                        $("#demo2").parent().css("display", "inline-block");
                        if (type === "select") {
                            $("#demo2").find(".delete").hide();
                        }else{
                            //删除
                            $("#demo2").find(".delete").on('click', function () {
                                $(this).parent().remove();
                                if ($("#demo2").children().length == 0) {
                                    $("#demo2").parent().css("display", "none");
                                }
                                imageUrl="";
                            });
                        }
                    }

                }
                form.val("formTest", result.data);
                form.render();
                resultData=result;
            }
        });
    }
    form.verify({
        subTitle: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(value===null||value===""){
                return '标题不能为空'
            }
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '标题不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '标题首尾不能出现下划线\'_\'';
            }
            if(/^\d+\d+\d$/.test(value)){
                return '标题不能全为数字';
            }
        }
    });



    //立即提交
    form.on("submit(submit)", function (data) {
        data.field.id = id;
        var index = layer.open({
            type: 3
        });
        delete data.field["created"];
        delete data.field["updated"];
        delete data.field["imageFile"];
        submit(data,index);
        return false;
    });

    function submit(data,index){
        if(imageFlag&&(imageUrl==null||imageUrl=="")){
            setTimeout(function () {
                submit(data,index);
            }, 100);
            return;
        }
        data.field.picUrl = imageUrl;
        var url="/manage/tbanner/update";
        if(data.field.id==0){
            url="/manage/tbanner/add";
        }
        $.ajax({
            url:url,
            data: data.field,
            type:"POST",
            success: function (result) {
                layer.close(index);
                if (result.code == 1) {
                    layer.msg("修改成功！");
                    layer.closeAll("iframe");
                    parent.updateHtml(result.data);
                }
            },error:function(x,m,e ){
                layer.close(index);
                layer.msg("系统异常！", {icon: 5, shift: 6});
            }
        });
    }
});
