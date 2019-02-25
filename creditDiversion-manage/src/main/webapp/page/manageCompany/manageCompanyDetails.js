var oldpassWord,accountRole,personalData,resultData,imageFlag,imageUrl;
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'code', 'laydate'], function () {
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
    if(id!=0){
        //加载数据
        $.get("/manage/tmanageCompanyInfo/selectOne", {id: id}, function (result) {
            if (result.code == 1) {
                if(type === "select"){//普通管理员不能修改其他管理员
                    $("input").attr("readonly", "readonly");
                    $("select").attr("disabled", "disabled");
                    $("#submit_div").hide();
                }else{
                    $("#submit_div").show();
                }
                form.val("formTest", result.data);
                form.render();
                resultData=result;
            }
        });
    }
    form.verify({
        companyName: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(value===null||value===""){
                return '公司名称不能为空'
            }
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '公司名称不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '公司名称首尾不能出现下划线\'_\'';
            }
            if(/^\d+\d+\d$/.test(value)){
                return '公司名称不能全为数字';
            }
        }
    });



    //立即提交
    form.on("submit(submit)", function (data) {
        data.field.id = id;
        var index = layer.open({
            type: 3
        });
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
        var url="/manage/tmanageCompanyInfo/update";
        if(data.field.id==0){
            url="/manage/tmanageCompanyInfo/add";
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
