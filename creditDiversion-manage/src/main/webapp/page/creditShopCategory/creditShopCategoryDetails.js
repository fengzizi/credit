var oldpassWord,accountRole,personalData,resultData,imageFlag,imageUrl;
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'code', 'laydate','upload'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,upload = layui.upload,
        laypage = layui.laypage,
        $ = layui.jquery;

    var id = getQueryString("id");//id为0时代表新增
    var defaultParentId = getQueryString("parentId");//id为0时代表新增
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
    $.ajax({
        url: "/manage/tcreditShopCategory/selectListName",
        data: {
            status: 1,//只查询正常的
            parentId:0  //只要父类目
        },
        async: false,
        type: "POST",
        success: function (result) {
            if (result.code == 1) {
                if(result.data!=null){
                    var html='<option value="0">无</option>';
                    for(var i=0;i<result.data.length;i++){
                        html+='<option '+(defaultParentId!=null&&defaultParentId!=''&&result.data[i].id==defaultParentId?"selected":"")+' value="'+result.data[i].id+'">'+result.data[i].name+'</option>';
                    }
                    $("#parentId").html(html);
                    form.render();
                }
            }
        }, error: function (x, m, e) {
            layer.msg("系统异常！", {icon: 5, shift: 6});
        }
    });
    if(id!=0){
        //加载数据
        $.get("/manage/tcreditShopCategory/selectOne", {id: id}, function (result) {
            if (result.code == 1) {
                form.val("formTest", result.data);
                form.render();
            }
        });
    }
    form.verify({
        name: function(value, item) { //value：表单的值、item：表单的DOM对象
            if (value === null || value === "") {
                return '名称不能为空'
            }
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '名称不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '名称首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '名称不能全为数字';
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
        var url="/manage/tcreditShopCategory/update";
        if(data.field.id==0){
            url="/manage/tcreditShopCategory/add";
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
