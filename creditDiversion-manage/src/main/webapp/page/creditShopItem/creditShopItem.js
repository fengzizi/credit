var categoryId,categoryName,categoryArray,categoryIds;
layui.config({
    base: "js/"
}).use(['form','table', 'layer', 'jquery', 'laypage','tree'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    categoryId==null;
    $("#demo").css("height",$(window).height()+"px")
    //加载树菜单
    $.ajax({
        url:"/manage/tcreditShopCategory/selectListTree",
        data: {
            status:1//只查询正常的
        },
        async:false,
        type:"POST",
        success: function (result) {
            if(result.code==1){
                layui.tree({
                    elem: '#demo' //传入元素选择器
                    ,click:function(obj){
                        $(this).css("color","red")
                        categoryId=obj.id;
                        categoryName=obj.name;
                        categoryIds=obj.ids;
                        $(".search_btn").click();
                    }
                    ,nodes: result.data.tree
                });
                categoryArray=result.data.list;
            }
        },error:function(x,m,e ){
            layer.msg("系统异常！", {icon: 5, shift: 6});
        }
    });

    var table = layui.table;
    //执行渲染
    tableIns =table.render({
        elem: '#adminTable' //指定原始表格元素选择器（推荐id选择器）
        , height: 'full-'+($(".news_search").height()+30) //容器高度
        ,page: true
        ,cols:  [[ //标题栏
            {field: 'appName', title: '信贷名称'}
            ,{field: 'subTitle', title: '副标题'}
            ,{field: 'categoryName', title: '信贷类目',toolbar:"#categoryNameBar"}
            ,{field: 'state', title: '状态',toolbar:"#stateBar",width:100}
            ,{field: 'tag', title: '标签', width:80,toolbar:"#tagBar"}
            ,{field: 'creditCount', title: 'UV数', width:100}
            ,{field: 'creditCountRe', title: '注册数', width:100}
            ,{field: 'creditCountRate', title: 'UV转化(%)', width:100,toolbar:"#creditCountRateBar"}
            ,{field: 'operatorUpdate', title: '操作',fixed: 'right',align:'center', width:170,toolbar: '#barDemo'}
        ]]
        ,url: '/manage/tcreditShopItem/selectList'
        ,response: {
            statusCode: 1 //成功的状态码，默认：0
        }
        ,done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
        }
        ,where: { //设定异步数据接口的额外参数，任意设
            appName: $(".search_input").val(),
            tag:$("#states_search").val(),
            categoryId:categoryId,
            categoryIds:categoryIds
        }
        ,even: true //开启隔行背景
        ,limit:20
        ,limits:[10,15,20, 25, 30,40,50]
        ,loading: true
    });
    //查询
    $(".search_btn").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                appName: $(".search_input").val(),
                tag:$("#states_search").val(),
                categoryIds:categoryIds
            },page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    });

    //重置
    $(".layui-btn-primary").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                appName: "",
                tag:"",
                categoryIds:categoryIds
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
            layer.confirm('确定删除【'+data.appName+'】?',{icon: 3,title:'系统提示'}, function(index){
                $.ajax({
                    url:"/manage/tcreditShopItem/deleteById",
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

    //监听状态操作
    form.on('switch(sexDemo)', function(obj){
        $.ajax({
            url:"/manage/tcreditShopItem/update",
            data: {
                id:this.value,
                state:obj.elem.checked?1:0
            },
            type:"POST",
            success: function (result) {
                if(result.code!=1){
                    layer.msg("系统异常！", {icon: 5, shift: 6});
                    form.render();
                }else{
                    /*tableIns.reload({
                        where: { //设定异步数据接口的额外参数，任意设
                            appName: $(".search_input").val(),
                            tag:$("#states_search").val()
                        }
                    });*/
                }

            },error:function(x,m,e ){
                obj.elem.checked=obj.elem.checked?false:true;
                layer.msg("系统异常！", {icon: 5, shift: 6});
                form.render();
            }
        });
    });
    $("#demo").find("a").eq(0).css("color","#1E9FFF");
    $("#demo").find("a").click(function(){
        $("#demo").find("a").css("color","#333")
        $(this).css("color","#1E9FFF");
    });
});

function openRisk(id, title) {
    parent.layui.layer.open({
        title: title + "产品详情",
        area: ['1000px','85%'],
        maxmin: true,
        type: 2,
        content: "page/creditShopItem/creditShopItemDetails.html?id=" + id +"&type=" + ((title == "查看") ? "select" : "update"),
        success: function (layero, index) {
            setTimeout(function () {
                layui.layer.tips('点击此处返回产品列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }, 500)
        }
    });
}

function updateHtml(data){
    tableIns.reload({
        where: { //设定异步数据接口的额外参数，任意设
            categoryIds:categoryIds,
            appName: $(".search_input").val(),
            tag:$("#states_search").val()
        }
    });
}

