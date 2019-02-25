package com.credit.diversion.util.qiniu;

/**
 * @author tangliang
 * @version 0.0.1-SNAPSHOT
 * @className FileConstant.java
 * @desc 七牛文件上传常量类
 * @date 2016年5月6日 下午4:05:34
 */
public class FileConstants {

    /**
     * ACCESS_KEY
     */
    public static final String ACCESS_KEY = "8R8s2o69KQMlhgqBdvO2DOpDtP4_lGGRigEGi9_7";

    /**
     * SECRET_KEY
     */
    public static final String SECRET_KEY = "yzvlRh-gFuRtRxyhbwrXlTWAlLCOxoeU1ktaSkPI";

    /**
     * 要上传的空间
     */
    public static final String BUCKET_NAME = "other";

    /**
     * 七牛存放文件网站地址
     */
    public static final String IMAGE_ADDRESS = "http://pcxmz350k.bkt.clouddn.com/";

    /**
     * 默认上传的文件夹名称
     */
    public static final String DEFAULT_FOLD = "api/";

    /**
     * 等比缩放单位
     */
    public static final String GEOMETRIC_SCALING_UNIT = "%";

    /**
     * 缩放路径后缀
     */
    public static final String ZOOMIMAGE_INTERFACE_SUFFIX = "?imageMogr2/thumbnail/!";

    /**
     * 裁剪后缀
     */
    public static final String CROP_PICTURE_INTERFACE_SUFFIX = "?imageMogr2/crop/";

}