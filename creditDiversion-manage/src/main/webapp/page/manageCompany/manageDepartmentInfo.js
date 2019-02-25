layui.config({
    base: "js/"
}).use(['form','table', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    companyId = getQueryString("companyId");

    var table = layui.table;
    //执行渲染
    tableIns =table.render({
        elem: '#adminTable' //指定原始表格元素选择器（推荐id选择器）
        , height: 'full-'+($("blockquote").height()+30) //容器高度
        ,page: true
        ,cols:  [[ //标题栏
            {field: 'departmentName', title: '部门名称'}
            ,{field: 'departmentType', title: '类型',toolbar:"#departmentTypeBar",width:110}
            ,{field: 'departmentStatus', title: '状态',toolbar:"#departmentStatusBar",width:110}
            ,{field: 'remark', title: '备注'}
            ,{field: 'operatorUpdate', title: '操作',fixed: 'right',align:'center', width:170,toolbar: '#barDemo'}
        ]]
        ,url: '/manage/tmanageDepartmentInfo/selectList'
        ,response: {
            statusCode: 1 //成功的状态码，默认：0
        }
        ,where: { //设定异步数据接口的额外参数，任意设
            companyId: companyId
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
                companyId: companyId,
                departmentName: $(".search_input").val()
            },page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //重置
    $(".layui-btn-primary").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                departmentName: "",
                companyId: companyId
            },
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    $(".insert").click(function(){//添加
        openRisk(0, "添加")
    });

    table.on('tool(adminTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if(layEvent === 'del'){ //删除
            layer.confirm('确定删除【'+data.departmentName+'】?',{icon: 3,title:'系统提示'}, function(index){
                $.ajax({
                    url:"/manage/tmanageDepartmentInfo/deleteById",
                    data: {
                        id:data.id
                    },
                    type:"POST",
                    success: function (result) {
                        if(result.code==1){
                            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                            layer.close(index);
                        }
                    },error:function(x,m,e ){
                        layer.msg("系统异常！", {icon: 5, shift: 6});
                    }
                });
            });
        }
    });
});

function openRisk(id, title) {
    layui.layer.open({
        title: title + "部门详情",
        area: ['100%','100%'],
        maxmin: true,
        type: 2,
        skin: 'demo-class',
        content: "/page/manageCompany/manageDepartmentInfoDetails.html?id=" + id +"&companyId="+companyId+"&type=" + ((title == "查看") ? "select" : "update"),
        success: function (layero, index) {
            setTimeout(function () {
                layui.layer.tips('点击此处返回部门列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }, 500)
        }
    });
}

function updateHtml(data){
    $(".search_btn").click();
}

