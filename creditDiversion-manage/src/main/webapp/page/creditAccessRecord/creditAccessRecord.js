layui.config({
    base: "js/"
}).use(['form','table', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;

    var table = layui.table;
    //执行渲染
    tableIns =table.render({
        elem: '#adminTable' //指定原始表格元素选择器（推荐id选择器）
        , height: 'full-'+($("blockquote").height()+30) //容器高度
        ,page: true
        ,cols:  [[ //标题栏
            {field: 'creditName', title: '信贷名称'}
            ,{field: 'userId', title: '用户id'}
            ,{field: 'userName', title: '用户名称'}
            ,{field: 'isRegister', title: '是否在该信贷已注册',toolbar: '#isRegisterBar'}
            ,{field: 'createTime', title: '访问时间'}
        ]]
        ,url: '/manage/tcreditAccessRecord/selectList'
        ,response: {
            statusCode: 1 //成功的状态码，默认：0
        }
        ,done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
        }
        ,even: true //开启隔行背景
        ,size:"sm"
        ,limit:20
        ,limits:[10,15,20, 25, 30,40,50]
        ,loading: true
    });

    $.ajax({
        url: "/manage/tcreditShopItem/selectListAll",
        async: false,
        type: "POST",
        success: function (result) {
            if (result.code == 1) {
                if(result.data!=null){
                    var html='<option value="">全部</option>';
                    for(var i=0;i<result.data.length;i++){
                        html+='<option value="'+result.data[i].id+'">'+result.data[i].appName+'</option>';
                    }
                    $("#categoryId").html(html);
                    form.render();
                }
            }
        }, error: function (x, m, e) {
            layer.msg("系统异常！", {icon: 5, shift: 6});
        }
    });

    //查询
    $(".search_btn").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                creditId: $("#categoryId").val(),
                isRegister: $("#state_search1").val(),
            },page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //重置
    $(".layui-btn-primary").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                creditName: ""
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    $(".insert").click(function(){//添加
        openRisk(0, "添加")
    });
    
});

function openRisk(id, title) {
    parent.layui.layer.open({
        title: title + "用户登录信息详情",
        area: ['1000px','550px'],
        maxmin: true,
        type: 2,
        content: "page/userLoginInfo/userLoginInfoDetails.html?id=" + id +"&type=" + ((title == "查看") ? "select" : "update"),
        success: function (layero, index) {
            setTimeout(function () {
                layui.layer.tips('点击此处返回用户登录信息列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }, 500)
        }
    });
}

function updateHtml(data){
    $(".search_btn").click();
}

