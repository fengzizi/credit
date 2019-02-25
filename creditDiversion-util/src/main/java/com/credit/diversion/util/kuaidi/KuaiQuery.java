package com.credit.diversion.util.kuaidi;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * @author tangliang
 * @version 0.0.1-SNAPSHOT
 * @className KuaiQuery.java
 * @desc 快递工具类 获取类
 * @date 2016年11月14日 下午1:20:03
 */
public class KuaiQuery {

    private static final String key = "GhxCbjVE2521";
    private static final String customer = "8CF08AB1D15116B735DB446E990BA358";

    /**
     * @param com 快递公司代号
     * @param num 快递单号
     * @return ResultItem 实体集合
     * @desc 返回快递数据动态集合
     * @author tangliang
     * @date 2016年11月14日 下午1:59:44
     */
    public static List<ResultItem> getKuaiSuccessData(String com, String num) {
        String context = getKuaiResult(com, num);
        JSONObject jsonObject = JSONObject.parseObject(context);
        if (null == jsonObject.get("data") || "".equals(jsonObject.get("data"))) {
            return null;
        }
        String json = jsonObject.get("data").toString();

        return JacksonHelper.fromJSONList(json, ResultItem.class);
    }

    /**
     * @param com 快递公司代号
     * @param num 快递单号
     * @return Result
     * @desc 获取快递单号所有的数据
     * @author tangliang
     * @date 2016年11月14日 下午2:01:39
     */
    public static Result getKuaiAllData(String com, String num) {
        String context = getKuaiResult(com, num);
        return JSONObject.parseObject(context, Result.class);
        //return (Result) JacksonHelper.fromJSON(context,Result.class);
    }


    /**
     * @param com 快递公司代号
     * @param num 快递单号
     * @return
     * @desc
     * @author tangliang
     * @date 2016年11月14日 下午1:30:47
     */
    @SuppressWarnings("static-access")
    public static String getKuaiResult(String com, String num) {
        StringBuffer sb = new StringBuffer("{\"com\":\"");
        sb.append(com).append("\",\"num\":\"").append(num + "\"}");
        String sign = MD5.encode(sb.toString() + key + customer);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("param", sb.toString());
        params.put("sign", sign);
        params.put("customer", customer);
        String resp = null;
        try {
            resp = new HttpRequest().postData("http://poll.kuaidi100.com/poll/query.do", params, "utf-8").toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resp;
    }

    public static void main(String[] args) throws Exception {

        String param = "{\"com\":\"yuantong\",\"num\":\"889093939309570944\"}";
        String customer = "8CF08AB1D15116B735DB446E990BA358";
        String key = "GhxCbjVE2521";
        String sign = MD5.encode(param + key + customer);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("param", param);
        params.put("sign", sign);
        params.put("customer", customer);
        String resp;
        try {
            resp = new HttpRequest().postData("http://poll.kuaidi100.com/poll/query.do", params, "utf-8").toString();
            System.out.println(resp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Result re = getKuaiAllData("yuantong", "889093939309570944");
        List resul = re.getData();
        ResultItem ri = new ResultItem();
        System.out.println(ri);


        String context = getKuaiResult("zhongtong", "420341382585");
        Result rs = (Result) JSONObject.parseObject(context, Result.class);
        System.out.println(rs.getState());
        getKuaiResult("shentong", "3317392581535");
    }
}
