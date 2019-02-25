package com.credit.diversion.util.commons;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/6/14 0014.
 * 页数转起始位置
 */
public class NumChangeStart {

    public static Integer getStart(Integer pageNum,Integer size,Integer total){
        if(pageNum<1){
            pageNum=1;
        }
        if(total==null||total.equals("")){
            total=0;
        }
        Integer start=(pageNum-1)*size>=total?(new BigDecimal(total).divide(new BigDecimal(size),0,BigDecimal.ROUND_UP).intValue()-1)*size:(pageNum-1)*size;

       return start<0?0:start;
    }
    public static Integer getNum(Integer pageNum,Integer size,Integer total){
        if(pageNum<1){
            pageNum=1;
        }
        if(total==null||total.equals("")){
            total=0;
        }
        if(total==0){
            return 1;
        }
        return (pageNum-1)*size>=total?new BigDecimal(total).divide(new BigDecimal(size),0,BigDecimal.ROUND_UP).intValue():pageNum;
    }

    public static void main(String[] args) {

        System.err.print(getStart(50,3,0));
    }
}
