layui.config({
    base: "js/"
}).use(['form', 'element', 'layer', 'jquery'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        element = layui.element,
        $ = layui.jquery;

    $.get('/manage/riskCompany/baseInfoQuery', function (result) {
        $('#accountAmount').html((result.data.accountAmount==null||result.data.accountAmount=='')?"0":result.data.accountAmount);
        $('#requestCount').html((result.data.requestCount==null||result.data.requestCount=='')?"0":result.data.requestCount);
        $('#totalPaymentAmount').html((result.data.totalPaymentAmount==null||result.totalPaymentAmount=='')?"0":result.data.totalPaymentAmount);
        $('#totalRechargeAmount').html((result.data.totalRechargeAmount==null||result.totalRechargeAmount=='')?"0":result.data.totalRechargeAmount);
    });

});
