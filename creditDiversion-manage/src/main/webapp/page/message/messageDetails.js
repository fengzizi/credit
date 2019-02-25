var oldpassWord,accountRole,personalData,startTime,finishTime;
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'code', 'laydate','upload','util'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,upload = layui.upload,
        laypage = layui.laypage,laydate = layui.laydate;
        $ = layui.jquery;
    var util = layui.util;
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
        $("#createTime").parent().parent().hide();
        $("#submit_div").show();
        //日期时间范围
        laydate.render({
            elem: '#time'
            ,theme: '#393D49'
            ,type: 'datetime'
            ,calendar: true
            ,range: true
            ,done: function(value, date,endDate){
                startTime=new Date(date.year+"-"+date.month+"-"+date.date+" "+date.hours+":"+date.minutes+":"+date.seconds);
                finishTime=new Date(endDate.year+"-"+endDate.month+"-"+endDate.date+" "+endDate.hours+":"+endDate.minutes+":"+endDate.seconds);
            }
        });
    };
    $("#createTime").attr("readonly", "readonly");
    if(id!=0){
        //加载数据
        $.get("/manage/tmessage/selectOne", {id: id}, function (result) {
            if (result.code == 1) {
                if(type === "select"){//普通管理员不能修改其他管理员
                    $("input").attr("readonly", "readonly");
                    $("select").attr("disabled", "disabled");
                    $("#submit_div").hide();
                }else{
                    $("#submit_div").show();
                }

                if(result.data!=null){
                    if(result.data.startTime!=null&&result.data.finishTime!=null){
                        $("#time").val(result.data.startTime+" - "+result.data.finishTime);
                    }
                    /*if(result.data.userId!=null&&result.data.userId!=0){
                        getUserName(result.data.userId,null);
                    }*/

                }
                form.val("formTest", result.data);
                form.render();
            }
        });
    }

    //立即提交
    form.on("submit(submit)", function (data) {
        data.field.id = id;
        var index = layer.open({
            type: 3
        });
        delete data.field["createTime"];
        submit(data,index);
        return false;
    });

    function submit(data,index){
        var url="/manage/tmessage/update";
        if(data.field.id==0){
            url="/manage/tmessage/add";
        }
        data.field.startTime = startTime;
        data.field.finishTime = finishTime;
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

    function getUserName(userId,loginName){
        console.log(loginName);
        //角色选择列表
        $.ajax({
            url: "/manage/tuserLoginInfo/selectListName",
            data: {
                loginName: loginName,
                id:userId
            },
            async: false,
            type: "POST",
            success: function (result) {
                if (result.code == 1) {
                    if(result.data!=null){
                        var html='<option value=""></option><option value="0">所有用户</option>';
                        var userId=$("#userId").val();
                        for(var i=0;i<result.data.length;i++){
                            html+='<option value="'+result.data[i].id+'" '+(userId==result.data[i].id?"selected":"")+' >'+result.data[i].loginName+'</option>';
                        }
                        $("#userId").html(html);
                        form.render();
                    }
                }
            }, error: function (x, m, e) {
                layer.msg("系统异常！", {icon: 5, shift: 6});
            }
        });
    }
});
