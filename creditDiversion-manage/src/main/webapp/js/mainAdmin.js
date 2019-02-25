layui.config({
    base: "js/"
}).use(['form', 'element', 'layer', 'jquery'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        element = layui.element,
        $ = layui.jquery;

    $.post('/manage/tuserLoginInfo/selectSum', function (result) {
        if (result.code == 1) {
            $("#countNowRegister").html(result.data.countNowRegister);
            $("#countNowRegisterRatio").html(((result.data.countNowRegister / result.data.countAll)*100).toFixed(2));
            $("#countNowLogin").html(result.data.countNowLogin);
            $("#countNowLoginRatio").html(((result.data.countNowLogin / result.data.countAll)*100).toFixed(2));
            $("#countNowAuth").html(result.data.countNowAuth);
            $("#countNowAuthRatio").html(((result.data.countNowAuth / result.data.countAuthAll)*100).toFixed(2));
            $("#countAll").html(result.data.countAll);
            $("#countAuthAll").html(result.data.countAuthAll);
            $("#creditCountAll").html(result.data.creditCountAll);
            $("#creditCountReAll").html(result.data.creditCountReAll);
            $("#creditAllRate").html((result.data.creditCountAll==0?0:((result.data.creditCountReAll / result.data.creditCountAll)*100)).toFixed(2));
        }
    });

    //分组统计
    $.post('/manage/tuserLoginInfo/selectSumGroup', function (result) {
        if (result.code == 1) {
            var reg = result.data.registers;
            var labels =new Array();
            var data = new Array();
            for (var i = 0; i < reg.length; i++) {
                labels.push(reg[i].days);
                data.push(reg[i].countNowRegister);
            }
            ChartFun({
                title:"注册",
                backgroundColor:'rgba(255, 99, 132, 0.2)',
                borderColor:'rgba(255,99,132,1)'
            },"myChart", labels, data);


            var auths = result.data.auths;
            labels =new Array();
            data = new Array();
            for (var i = 0; i < auths.length; i++) {
                labels.push(auths[i].days);
                data.push(auths[i].countNowAuth);
            }
            ChartFun({
                title:"认证",
                backgroundColor:'rgba(54, 162, 235, 0.2)',
                borderColor:'rgba(54, 162, 235, 1)'
            },"myChart1", labels, data);

            labels =new Array();
            data = new Array();
            var data1 = new Array();
            var data2 = new Array();
            var credits = result.data.credits;
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
            myChartFun("creditChart",labels,data,data1,data2,countAll,countRateAll);
        }
    });


});

function ChartFun(set,id, labels, data) {
    var ctx1 = document.getElementById(id).getContext('2d');
    var myChart = new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: set.title+'人数',
                yAxisID: 'num',
                data: data,
                backgroundColor:set.backgroundColor,// 'rgba(255, 99, 132, 0.2)',
                borderColor: set.borderColor,//'rgba(255,99,132,1)',
                yAxisIndex: 0,
                borderWidth: 1
            }]
        },
        options: {
            title: {//显示标题
                display: true,
                text: '最近15日'+set.title
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
                    /*scaleLabel: {
                        display: true,
                        labelString: "注册人数",
                    }*/
                }
                ]
            }
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
