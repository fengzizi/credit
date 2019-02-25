layui.config({
    base: "js/"
}).extend({
    treetable: '../../../js/treetable-lay/treetable'
}).use(['form','table', 'layer', 'jquery', 'laypage','treetable'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;

    var table = layui.table;
    /*
    //执行渲染
    tableIns =table.render({
        elem: '#adminTable' //指定原始表格元素选择器（推荐id选择器）
        , height: 'full-'+($("blockquote").height()+30) //容器高度
        ,page: true
        ,cols:  [[ //标题栏
            {field: 'osType', title: '操作系统'}
            ,{field: 'osVersion', title: '系统版本号'}
            ,{field: 'auditSwitch', title: '审核开关',toolbar:"#auditSwitchBar"}
            ,{field: 'osBuildNum', title: 'Build号'}
            ,{field: 'createdBy', title: '创建人'}
            ,{field: 'createdAt', title: '创建时间',width:170}
            ,{field: 'updatedBy', title: '更新人'}
            ,{field: 'updatedAt', title: '更新时间',width:170}
            ,{field: 'operatorUpdate', title: '操作',fixed: 'right',align:'center', width:170,toolbar: '#barDemo'}
        ]]
        ,url: '/manage/tversionControl/selectList'
        ,response: {
            statusCode: 1 //成功的状态码，默认：0
        }
        ,done: function(res, curr, count){
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
        }
        ,even: true //开启隔行背景
        ,limit:20
        ,limits:[10,15,20, 25, 30,40,50]
        ,loading: true
    });*/

    var treetable = layui.treetable;

    // 渲染表格
    renderTable = function () {
        layui.layer.load(2);
        // 渲染表格
        treetable.render({
            treeColIndex: 1,          // treetable新增参数
            treeSpid: 0,             // treetable新增参数
            treeIdName: 'id',       // treetable新增参数
            treePidName: 'pid',     // treetable新增参数
            treeDefaultClose: false,   // treetable新增参数
            treeLinkage: true,        // treetable新增参数
            elem: '#adminTable',
            height: 'full-'+($("blockquote").height()+20), //容器高度
            url: '/manage/tmanageResource/selectList',
            response: {
                statusCode: 1 //成功的状态码，默认：0
            },
            loading: true,
            size:"sm",
            cols: [[
                {type: 'numbers'},
                {field: 'resourceName', title: '模块名称',align:'left'},
                {field: 'resourceType', title: '类型',toolbar: '#resourceTypeBar'},
                {field: 'resourceStatus', title: '状态',toolbar: '#resourceStatusBar'},
                {field: 'resourceGroup', title: '分组信息',toolbar:"#resourceGroupBar"},
                {field: 'resourceOrder', title: '排序'},
                {field: 'operatorUpdate', title: '操作',fixed: 'right',align:'center',toolbar: '#barDemo'}
            ]],
            done: function () {
                layui.layer.closeAll('loading');
            }
        });
    };
    renderTable();

    $("#btn-expand").click(function () {
        treetable.expandAll('#adminTable');
    });
    $('#btn-fold').click(function () {
        treetable.foldAll('#adminTable');
    });

    $(".insert").click(function(){//添加
        openRisk(0, "添加")
    });

    table.on('tool(adminTable)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if(layEvent === 'del'){ //删除
            layer.confirm('确定删除该菜单及其子菜单?',{icon: 3,title:'系统提示'}, function(index){
                $.ajax({
                    url:"/manage/tmanageResource/deleteById",
                    data: {
                        id:data.id
                    },
                    type:"POST",
                    success: function (result) {
                        if(result.code==1){
                            renderTable();
                            layer.close(index);
                        }
                    },error:function(x,m,e ){
                        layer.msg("系统异常！", {icon: 5, shift: 6});
                    }
                });
            });
        }
    });
   //查询
    $('#btn-search').click(function () {
        search();
    });
    $("#edt-search").keydown(function(event){
        if(event.keyCode ==13){
            search();
            return false;
        }
        return true;
    });

    function search() {

        var keyword = $('#edt-search').val();
        var searchCount = 0;
        $('#adminTable').next('.treeTable').find('.layui-table-body tbody tr td').each(function () {
            $(this).css('background-color', 'transparent');
            var text = $(this).text();
            if (keyword != '' && text.indexOf(keyword) >= 0) {
                $(this).css('background-color', 'rgba(250,230,160,0.5)');
                if (searchCount == 0) {
                    treetable.expandAll('#adminTable');
                    $('html,body').stop(true);
                    $('html,body').animate({scrollTop: $(this).offset().top - 150}, 500);
                }
                searchCount++;
            }
        });
        if (keyword == '') {
            layer.msg("请输入搜索内容", {icon: 5});
        } else if (searchCount == 0) {
            layer.msg("没有匹配结果", {icon: 5});
        }
    }

});
function updateHtml(data){
    $(".search_btn").click();
    renderTable();
}


function openRisk(id, title) {
    parent.layui.layer.open({
        title: title + "菜单详情",
        area: ['1000px','550px'],
        maxmin: true,
        type: 2,
        content: "page/manageResource/manageResourceDetails.html?id=" + id +"&type=" + ((title == "查看") ? "select" : "update"),
        success: function (layero, index) {
            setTimeout(function () {
                layui.layer.tips('点击此处返回菜单列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }, 500)
        }
    });
}



