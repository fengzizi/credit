var $form;
var form;
var $;
layui.config({
    base: "../../js/"
}).use(['form', 'layer', 'upload', 'laydate'], function () {
    form = layui.form;
    var layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    $form = $('form');
    $(".accountName").val(localStorage.getItem("loginName"));
    form.verify({
        accountName: function (value, item) { //value：表单的值、item：表单的DOM对象
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
        , accountPwd: [
            /^[\S]{6,16}$/
            , '密码必须6到16位，且不能出现空格'
        ],
        confirmPwd: function (value, item) {
            if (!new RegExp($("#oldPwd").val()).test(value)) {
                return "两次输入密码不一致，请重新输入！";
            }
        }
    });
    //修改密码
    form.on("submit(changePwd)", function (data) {
        var index = layer.open({
            type: 3
        });
        $.ajax({
            url: "/manage/tmanageLoginAccount/updatePwd",
            data: data.field,
            type: "POST",
            success: function (result) {
                layer.close(index);
                if (result.code == 1) {
                    $(".pwd").val('');
                    layer.open({
                        title: '系统提示'
                        ,content:"修改成功，请重新登录！"
                        ,icon: 1
                        ,end:function(){
                            window.top.location='/page/login/login.html';
                        }
                    });
                }
            }, error: function (x, m, e) {
                layer.close(index);
                layer.msg("系统异常！", {icon: 5, shift: 6});
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    //重置
    $(".layui-btn-primary").click(function () {
        setTimeout(function(){
            $(".accountName").val(localStorage.getItem("loginName"));
        },1);
    });
});
