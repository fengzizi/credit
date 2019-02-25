var categoryId,categoryName,categoryArray,categoryIds;
layui.config({
    base: "js/"
}).use(['form','table', 'layer', 'jquery', 'laypage','tree'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    categoryId==null;
    $("input").attr("readonly", "readonly");
    $("select").attr("disabled", "disabled");
    $("#demo-view").css("height",$(window).height()-13+"px");
    $("#demo").css("height",$(window).height()+"px")
    //加载树菜单
    $.ajax({
        url:"/manage/tcreditShopCategory/selectListTree",
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
                        if(categoryId==null||categoryId==""||categoryId==0){
                            $("#form").addClass("layui-hide");
                            $("#update").addClass("layui-btn-disabled");
                            $("#update").attr("disabled","disabled");
                            $("#update").addClass("layui-hide");
                        }else{
                            $("#form").removeClass("layui-hide");
                            $("#update").removeClass("layui-btn-disabled");
                            $("#update").removeAttr("disabled","disabled");
                            $("#update").removeClass("layui-hide");
                            select(form);
                        }
                        form.render();
                    }
                    ,nodes: result.data.tree
                });
                categoryArray=result.data.list;
            }
        },error:function(x,m,e ){
            layer.msg("系统异常！", {icon: 5, shift: 6});
        }
    });
    if(categoryArray!=null){
        var html='<option value="0">无</option>';
        for(var i=0;i<categoryArray.length;i++){
            html+='<option value="'+categoryArray[i].id+'">'+categoryArray[i].name+'</option>';
        }
        $("#parentId").html(html);
    }
    $("#demo").find("a").eq(0).css("color","#1E9FFF");
    $("#demo").find("a").click(function(){
        $("#demo").find("a").css("color","#333")
        $(this).css("color","#1E9FFF");
    });
    form.render();

    $(".insert").click(function(){//添加
        openRisk(0, "添加",categoryId)
    });

    $("#update").click(function(){//添加
        openRisk(categoryId, "添加",null)
    });
});

function openRisk(id, title,parentId) {
    parent.layui.layer.open({
        title: title + "类目详情",
        area: ['1000px','85%'],
        maxmin: true,
        type: 2,
        content: "page/creditShopCategory/creditShopCategoryDetails.html?id=" + id +"&parentId="+parentId+"&type=" + ((title == "查看") ? "select" : "update"),
        success: function (layero, index) {
            setTimeout(function () {
                layui.layer.tips('点击此处返回类目列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }, 500)
        }
    });
}

function updateHtml(data){
    window.location.reload(true);
}

function select(form) {
    $.get("/manage/tcreditShopCategory/selectOne", {id: categoryId}, function (result) {
        if (result.code == 1) {
            form.val("formTest", result.data);
            form.render();
        }
    });
}

