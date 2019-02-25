package com.credit.diversion.util.qiniu;

/**@className FileResultEntity.java
 * @desc 上传文件结果集实体
 * @date 2016年5月7日 下午7:31:44
 * @author tangliang
 * @version 0.0.1-SNAPSHOT
 */
public class FileResultEntity {

    /**
     * 上传图片的名称
     */
    private String imageName;

    /**
     * 上传到七牛的图片的绝对路径
     */
    private String imageUrl;

    /**
     * 上传到七牛的图片的相对路径
     */
    private String imageRelativepath;

    /**
     * 上传图片错误信息。如果为null、说明上传图片成功
     */
    private String errorMsg;

    /**
     * 上传图片是否成功。true表示成功、false表示失败.
     */
    private boolean isSuccess;

    /**
     * 无参数构造方法
     */
    public FileResultEntity() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 有参构造方法
     * @param imageName
     */
    public FileResultEntity(String imageName) {
        this.imageName=imageName;
    }

    /**
     * 有参构造方法
     * @param imageName
     */
    public FileResultEntity(String imageName,boolean isSuccess) {
        this.imageName=imageName;
        this.isSuccess = isSuccess;
    }

    public String getImageRelativepath() {
        return imageRelativepath;
    }

    public void setImageRelativepath(String imageRelativepath) {
        this.imageRelativepath = imageRelativepath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

}