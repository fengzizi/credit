layui.config({
    base: "js/"
}).use(['form','table', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    if("admin"==localStorage.getItem("loginName")){
        $(".insert").show()
    }else{
        $(".insert").hide()
    }

    var table = layui.table;
    //执行渲染
    var tableIns =table.render({
        elem: '#adminTable' //指定原始表格元素选择器（推荐id选择器）
        , height: 'full-'+($("blockquote").height()+30) //容器高度
        ,page: true
        ,cols:  [[ //标题栏
            {field: 'loginName', title: '登录名'}
            ,{field: 'userName', title: '用户名'}
            ,{field: 'userPhone', title: '手机号'}
            ,{field: 'userQq', title: 'QQ'}
            ,{field: 'userWorkNumber', title: '工号'}
            ,{field: 'accountStatus', title: '状态',toolbar:"#state_html", width:90}
            ,{field: 'lastLoginTime', title: '最后登录时间'}
            ,{field: 'operatorUpdate', title: '操作',fixed: 'right',align:'center', width:170,toolbar: '#barDemo'}
        ]]
        ,url: '/manage/tmanageLoginAccount/selectList'
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
    //查询
    $(".search_btn").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                loginName: $(".search_input").val()
            },page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //重置
    $(".layui-btn-primary").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                loginName: ""
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
        title: title + "管理员详情",
        area: ['1000px','85%'],
        maxmin: true,
        type: 2,
        content: "page/admin/riskAdminDetails.html?id=" + id +"&type=" + ((title == "查看") ? "select" : "update"),
        success: function (layero, index) {
            setTimeout(function () {
                layui.layer.tips('点击此处返回管理员列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }, 500)
        }
    });
}

function updateHtml(data){
    window.location.reload(true);
}

