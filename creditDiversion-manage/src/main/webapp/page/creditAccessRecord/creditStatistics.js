layui.config({
    base: "js/"
}).use(['form','table', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    $("#tab").css("height",$(window).height()-$("blockquote").height()-30+"px")
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
    selectCredit(null,15);
    //查询
    $(".search_btn").click(function () {
        selectCredit($("#categoryId").val(),$("#state_search1").val());
    });

    //重置
    $(".layui-btn-primary").click(function () {
        selectCredit(null,15);
    });

});

function selectCredit(id,day) {
    //分组统计
    $.post('/manage/tcreditAccessRecord/selectSumGroup',{
        id:id,
        day:day
    }, function (result) {
        if (result.code == 1) {
            var labels =new Array();
            var data = new Array();
            var data1 = new Array();
            var data2 = new Array();
            var credits = result.data;
            var countAll=0;
            var countRateAll=0;
            for (var i = 0; i < credits.length; i++) {
                labels.push(credits[i].days);
                countAll+=credits[i].creditCount;
                countRateAll+=credits[i].creditCountRate;
                data.push(credits[i].creditCount);
                data1.push(credits[i].creditCountRate);
                data2.push((credits[i].creditCountRate/credits[i].creditCount*100).toFixed(2));
            }
            $("#div_canvas").html('<canvas id="creditChart" width="100%"></canvas>');
            myChartFun("creditChart",labels,data,data1,data2,countAll,countRateAll);
        }
    });
}

function myChartFun(id,labels,data,data1,data2,countAll,countRateAll) {
    var ctx1 = document.getElementById(id).getContext('2d');
    var myChart = new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'UV数(次)',
                yAxisID: 'num',
                data: data,
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255,99,132,1)',
                yAxisIndex: 0,
                borderWidth: 1
            }, {
                label: '注册数(人)',
                yAxisID: 'num',
                data: data1,
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }, {
                label: 'UV转化(%)',
                yAxisID: 'ratio',
                type: 'line',
                data: data2,
                backgroundColor: 'rgba(28, 145, 130, 0.2)',
                borderColor: 'rgba(28, 145, 130, 1)',
                borderWidth: 1
            }]
        },
        options: {
            title: {//显示标题
                display: true,
                text: '最近15日信贷统计\t\t'+"UV总数"+countAll+"次\t\t"+"注册总数"+countRateAll+
                "人\t\t"+"UV总转化"+(countAll==0?"0.00":(countRateAll/countAll*100).toFixed(2))+"%"
            },
            scales: {
                xAxes: [{
                    ticks: {
                        beginAtZero: true,
                        maxTicksLimit:15,
                        stepSize:1,
                    },
                }],
                yAxes: [{
                    id: "num",
                    ticks: {
                        beginAtZero: true,
                        callback: function(value, index, values) {
                            return value%1 === 0?value:"";//去掉小数显示
                        }
                    },
                    gridLines: {//该轴的网格线配置
                        display: false
                    },
                    scaleLabel: {
                        display: true,
                        labelString: "人数",
                    }
                }, {
                    id: "ratio",
                    position: "right",//轴显示位置
                    ticks: {
                        beginAtZero: true,
                    },
                    gridLines: {//该轴的网格线配置
                        display: false
                    },
                    scaleLabel: {
                        display: true,
                        labelString: "百分比(%)",
                    }
                }
                ]
            }
        }
    });

}
