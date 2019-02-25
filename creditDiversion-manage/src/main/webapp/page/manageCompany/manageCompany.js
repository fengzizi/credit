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
            {field: 'companyName', title: '公司名称'}
            ,{field: 'companyAddress', title: '公司地址'}
            ,{field: 'companyType', title: '类型',toolbar:"#companyTypeBar",width:110}
            ,{field: 'companyStatus', title: '状态',toolbar:"#companyStatusBar",width:110}
            ,{field: 'companyPhone', title: '联系电话',width:150}
            ,{field: 'remark', title: '备注'}
            ,{field: 'operatorUpdate', title: '操作',fixed: 'right',align:'center', width:170,toolbar: '#barDemo'}
        ]]
        ,url: '/manage/tmanageCompanyInfo/selectList'
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
                companyName: $(".search_input").val()
            },page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //重置
    $(".layui-btn-primary").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                companyName: ""
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
            layer.confirm('确定删除【'+data.companyName+'】及其下属部门?',{icon: 3,title:'系统提示'}, function(index){
                $.ajax({
                    url:"/manage/tmanageCompanyInfo/deleteById",
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
    /*parent.layui.layer.open({
        title: title + "公司详情",
        area: ['1000px','550px'],
        maxmin: true,
        type: 2,
        content: "page/manageCompany/manageCompanyTab.html?id=" + id +"&type=" + ((title == "查看") ? "select" : "update"),
        success: function (layero, index) {
            setTimeout(function () {
                layui.layer.tips('点击此处返回公司列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }, 500)
        }
    });*/

    var tabContent=[{
        title: title + "公司详情",
        content:"",
        contentNow: "<iframe src='/page/manageCompany/manageCompanyDetails.html?id="+id+"&type=" + ((title == "查看") ? "select" : "update")+"'></iframe>"
    },{
        title: "部门管理",
        content:"",
        contentNow: "<iframe src='/page/manageCompany/manageDepartmentInfo.html?companyId="+id+"'></iframe>"
    }]
    tabContent[0].content=tabContent[0].contentNow;
    layer.tab({
        area: ['1000px','85%'],
        id:"tabAlert",
        maxmin: true,
        type: 1,
        tab: tabContent
    });
    $(".layui-layer-title span").click(function(){
        var index = $(".layui-layer-title span").index(this);
        var htmlNow=$("#tabAlert").find(".layui-layer-tabli").eq(index).html();
        if(htmlNow==null||htmlNow==''||htmlNow=="no  content"){
            $("#tabAlert").find(".layui-layer-tabli").eq(index).html(tabContent[index].contentNow);
        }
    });

}

function updateHtml(data){
    $(".search_btn").click();
}

