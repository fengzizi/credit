var oldpassWord, accountRole, personalData, resultData;
layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'code', 'laydate'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    var id = getQueryString("id");//id为0时代表新增

    var type = getQueryString("type");
    if (type === "select") {
        $("input").attr("readonly", "readonly");
        $("select").attr("disabled", "disabled");
        $("input").attr("disabled", "disabled");
        $("#submit_div").hide();
        $(".update").show();

    } else {
        $("#createTime").parent().parent().hide();
    };

    //角色选择列表
    $.ajax({
        url: "/manage/tmanageRoleInfo/selectListName",
        data: {
            roleStatus: 0//只查询正常的
        },
        async: false,
        type: "POST",
        success: function (result) {
            if (result.code == 1) {
                if(result.data!=null){
                    var html='<option value=""></option>';
                    for(var i=0;i<result.data.length;i++){
                        html+='<option value="'+result.data[i].id+'">'+result.data[i].roleName+'</option>';
                    }
                    $("#userRoleId").html(html);
                    form.render();
                }
            }
        }, error: function (x, m, e) {
            layer.msg("系统异常！", {icon: 5, shift: 6});
        }
    });

    //公司列表
    $.ajax({
        url: "/manage/tmanageCompanyInfo/selectListName",
        data: {
            companyStatus: 0//只查询正常的
        },
        async: false,
        type: "POST",
        success: function (result) {
            if (result.code == 1) {
                if(result.data!=null){
                    var html='<option value=""></option>';
                    for(var i=0;i<result.data.length;i++){
                        html+='<option value="'+result.data[i].id+'">'+result.data[i].companyName+'</option>';
                    }
                    $("#companyId").html(html);
                    form.render();
                }
            }
        }, error: function (x, m, e) {
            layer.msg("系统异常！", {icon: 5, shift: 6});
        }
    });
    form.on('select(companyId)',function(data){
        getSelectDep(null,data.value)
    });
    $("#createTime").attr("readonly", "readonly");
    //加载数据
    $.get("/manage/tmanageLoginAccount/selectOne", {id: id}, function (result) {
        if (result.code == 1) {
            if ((result.data != null && localStorage.getItem("loginName") != "admin" && result.data.loginName != localStorage.getItem("loginName")) || type === "select") {//普通管理员不能修改其他管理员
                $("input").attr("readonly", "readonly");
                $("select").attr("disabled", "disabled");
                $("#submit_div").hide();
            } else {
                if (result.data != null) {
                    if (result.data.loginName == localStorage.getItem("loginName")) {
                        personalData = true;
                    }
                }
                $("#submit_div").show();
                $("#loginPassword").blur(function () {
                    var value = $(this).val();
                    if (value == null || value == "") {
                        if (oldpassWord != null && oldpassWord != "") {
                            $(this).val("******");
                        }
                    }
                });
                $("#loginPassword").focus(function () {
                    if ($(this).val() == "******") {
                        $(this).val("");
                    }
                });
            }
            if (result.data != null && result.data.loginName == localStorage.getItem("loginName")) {//普通管理员不能修改自己以下信息
                $(".noUpdate").find("input").attr("disabled", "disabled");
                $(".noUpdate").find("input").attr("readonly", "readonly");
                $(".noUpdate").find("select").attr("disabled", "disabled");
                $(".noUpdate").find("select").attr("readonly", "readonly");
            }
            if (result.data != null) {
                id = result.data.id;
                if(result.data.departmentId!=null&&result.data.departmentId!=""&&result.data.departmentId!=0){
                    getSelectDep(result.data.departmentId,null)
                }
            }


            form.val("formTest", result.data);
            form.render();
            resultData = result;
            fillData(result);
        }
    });



    form.verify({
        loginName: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value === null || value === "") {
                return '用户名不能为空'
            }
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '用户名不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '用户名首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '用户名不能全为数字';
            }
        }
        //我们既支持上述函数式的方式，也支持下述数组的形式
        //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
        , loginPassword: [
            /^[\S]{6,16}$/
            , '密码必须6到16位，且不能出现空格'
        ]
    });


//立即提交
    form.on("submit(submit)", function (data) {
        data.field.id = id;
        if (data.field.loginPassword == "******") {
            delete data.field["loginPassword"];//没修改密码
        }
        delete data.field["lastLoginTime"];//不修改时间
        delete data.field["lastLoginIp"];
        var index = layer.open({
            type: 3
        });
        $.ajax({
            url: "/manage/tmanageLoginAccount/update",
            data: data.field,
            type: "POST",
            success: function (result) {
                layer.close(index);
                if (result.code == 1) {
                    if (result.msg == "yes") {
                        layer.open({
                            title: '系统提示'
                            , content: "修改成功，请重新登录！"
                            , icon: 1
                            , end: function () {
                                window.top.location = '/page/login/login.html';
                            }
                        });
                    } else {
                        layer.msg("修改成功！");
                        layer.closeAll("iframe");
                        if (getQueryString("id") == null || getQueryString("id") == "" || personalData) {
                            result.data.personalData = "personalData"
                        }
                        parent.updateHtml(result.data);
                        if (getQueryString("id") != null && getQueryString("id") != '' && personalData) {
                            parent.reloadIframe()
                        }
                    }
                }
            }, error: function (x, m, e) {
                layer.close(index);
                layer.msg("系统异常！", {icon: 5, shift: 6});
            }
        });
        return false;
    });

//填充数据方法
    function fillData(result) {
        var data = result.data;
        if (data != null) {
            oldpassWord = data.loginPassword;
            $("#loginPassword").val(((data.loginPassword == null || data.loginPassword == '') ? "" : "******"));
        }
        form.render();
    }

    function getSelectDep(id,companyId){
        //查询部门
        $.ajax({
            url: "/manage/tmanageDepartmentInfo/selectListName",
            data: {
                departmentStatus: 0,//只查询正常的
                companyId:companyId,
                id:id
            },
            async: false,
            type: "POST",
            success: function (result) {
                if (result.code == 1) {
                    if(result.data!=null){
                        var nowValue=$("#departmentId").val()
                        var html='<option value=""></option>';
                        for(var i=0;i<result.data.length;i++){
                            html+='<option value="'+result.data[i].id+'" '+(nowValue==result.data[i].id?"selected":"")+'>'+result.data[i].departmentName+'</option>';
                        }
                        $("#departmentId").html(html);
                        form.render();
                    }
                }
            }, error: function (x, m, e) {
                layer.msg("系统异常！", {icon: 5, shift: 6});
            }
        });
    }
});

