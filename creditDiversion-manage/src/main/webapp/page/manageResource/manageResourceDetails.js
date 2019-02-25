var oldpassWord,accountRole,personalData,resultData;
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
        $("textarea").attr("readonly", "readonly");
        $(".update").show();
        $("#submit_div").hide();
        $("#test10").hide();
    } else {
        $("#submit_div").show();
    };
    $("#resourceType").attr("disabled", "disabled");
    $("#resourceType").attr("readonly", "readonly");

    $.ajax({
        url: "/manage/tmanageResource/selectList",
        data: {
            resourceStatus: 0,//只查询正常的
            resourceType: 0,
        },
        async: false,
        type: "POST",
        success: function (result) {
            if (result.code == 1) {
                if(result.data!=null){
                    var html='<option value="0">无</option>';
                    for(var i=0;i<result.data.length;i++){
                        html+='<option  value="'+result.data[i].id+'">'+result.data[i].resourceName+'</option>';
                    }
                    $("#pid").html(html);
                    form.render();
                }
            }
        }, error: function (x, m, e) {
            layer.msg("系统异常！", {icon: 5, shift: 6});
        }
    });
    $("#createTime").attr("readonly", "readonly");
    if(id!=0){
        //加载数据
        $.get("/manage/tmanageResource/selectOne", {id: id}, function (result) {
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
        var url="/manage/tmanageResource/update";
        if(data.field.id==0){
            url="/manage/tmanageResource/add";
        }
        $.ajax({
            url:url,
            data: data.field,
            type:"POST",
            success: function (result) {
                layer.close(index);
                if (result.code == 1) {
                    layer.msg("成功！");
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
