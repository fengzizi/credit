var oldpassWord,accountRole,personalData,resultData,imageFlag,imageUrl,avgPayRate=10,avgCollection=10,avgDifficulty=10;
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'code', 'laydate','upload',"slider"], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,upload = layui.upload,
        slider = layui.slider;
        laypage = layui.laypage,
        $ = layui.jquery;

    var id = getQueryString("id");//id为0时代表新增

    var type = getQueryString("type");
    //定义初始值
    var avgPayRateS=slider.render({
        elem: '#avgPayRate'
        ,value: 100 //初始值
        ,disabled: type === "select" //禁用滑块
        ,setTips: function(value){ //自定义提示文本
            return value/10;
        }
        ,change: function(value){
            avgPayRate=value;
        }
    });
    var avgDifficultyS=slider.render({
        elem: '#avgDifficulty'
        ,value: 100 //初始值
        ,disabled: type === "select" //禁用滑块
        ,setTips: function(value){ //自定义提示文本
            return value/10;
        }
        ,change: function(value){
            avgDifficulty=value;
        }
    });
    var avgCollectionS=slider.render({
        elem: '#avgCollection'
        ,value: 100 //初始值
        ,disabled: type === "select" //禁用滑块
        ,setTips: function(value){ //自定义提示文本
            return value/10;
        }
        ,change: function(value){
            avgCollection=value;
        }
    });

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
    $.ajax({
        url: "/manage/tcreditShopCategory/selectListName",
        data: {
            status: 1,//只查询正常的
            notParentId:0//不要父类的
        },
        async: false,
        type: "POST",
        success: function (result) {
            if (result.code == 1) {
                if(result.data!=null){
                    var html='<option value=""></option>';
                    for(var i=0;i<result.data.length;i++){
                        html+='<option value="'+result.data[i].id+'">'+result.data[i].name+'</option>';
                    }
                    $("#categoryId").html(html);
                    form.render();
                }
            }
        }, error: function (x, m, e) {
            layer.msg("系统异常！", {icon: 5, shift: 6});
        }
    });
    var uploadListIns = upload.render({
        elem: '#test10'
        , url: '/manage/tcreditShopItem/uploadImg'
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
        $.get("/manage/tcreditShopItem/selectOne", {id: id}, function (result) {
            if (result.code == 1) {
                if(result.data!=null){
                    id=result.data.id;
                    if(result.data.appIconUrl!=null&&result.data.appIconUrl!=''){
                        var htm= '<span style="position: relative;display: inline-block;">' +
                            '<img src="' + result.data.appIconUrl + '" style="height: 106px;" class="layui-upload-img">' +
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
                    result.data.sucessRate=result.data.sucessRate*100;
                    result.data.dayInterest=result.data.dayInterest*100;
                    if(result.data.titemComment!=null){
                        avgPayRateS.setValue(result.data.titemComment.avgPayRate*10);
                        avgDifficultyS.setValue(result.data.titemComment.avgDifficulty*10);
                        avgCollectionS.setValue(result.data.titemComment.avgCollection*10)
                    }
                    if(result.data.titemAttach!=null){
                        Object.assign(result.data,result.data.titemAttach);
                    }
                }

                form.val("formTest", result.data);
                form.render();
                resultData=result;
            }
        });
    }
    form.verify({
        appName: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(value===null||value===""){
                return '名称不能为空'
            }
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '名称不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '名称首尾不能出现下划线\'_\'';
            }
            if(/^\d+\d+\d$/.test(value)){
                return '名称不能全为数字';
            }
        },
        dayInterest: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(value<0||value>100){
                return "日利息只能在0%-100%之间"
            }
        },
        sucessRate: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(value<0||value>100){
                return "成功率只能在0%-100%之间"
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
        delete data.field["imageFile"];
        delete data.field["updated"];
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
        data.field.appIconUrl = imageUrl;
        data.field.sucessRate=data.field.sucessRate/100;
        data.field.dayInterest=data.field.dayInterest/100;

        data.field.avgPayRate=avgPayRate;
        data.field.avgCollection=avgCollection;
        data.field.avgDifficulty=avgDifficulty;
        var url="/manage/tcreditShopItem/update";
        if(data.field.id==0){
            url="/manage/tcreditShopItem/add";
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
