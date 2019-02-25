package com.credit.diversion.util.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author tangliang
 * @version 0.0.1-SNAPSHOT
 * @className FileUtil.java
 * @desc 七牛文件上传工具类
 * @date 2016年5月6日 下午4:01:19
 */
public class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class);

    //构造一个带指定Zone对象的配置类
    static Configuration cfg = new Configuration(Zone.zone0());
    //密钥配置
    static Auth auth = Auth.create(FileConstants.ACCESS_KEY, FileConstants.SECRET_KEY);
    //创建上传对象
    static UploadManager uploadManager = new UploadManager(cfg);
    //创建BucketManager对象
    static BucketManager bucketManager = new BucketManager(auth, cfg);

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String getUpToken() {
        return auth.uploadToken(FileConstants.BUCKET_NAME);
    }

    public static String privateDownloadUrl(String url){
        return auth.privateDownloadUrl(url);
    }

    /**
     * @param file      流文件
     * @param request   httprequest
     * @param imageType 图片类型  。根据自己的模块名传入图片要存放在云上的文件夹
     * @return 结果集
     * @throws IOException
     * @desc 根据图片类型调用方法
     * @date 2016年5月7日 下午9:20:14
     * @author tangliang
     */
    public static FileResultEntity upload(MultipartFile file, HttpServletRequest request, String imageType){

        String fileName = String.valueOf(System.currentTimeMillis()) + "." +
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        FileResultEntity resultEntity = new FileResultEntity(fileName, false);

        if (file == null || file.isEmpty()) {
            logger.error("文件为空");
            resultEntity.setErrorMsg("文件为空");
            return resultEntity;
        }
        if (!StringUtils.isEmpty(imageType) && !imageType.endsWith("/")) {
            imageType = imageType + "/";
        } else if (StringUtils.isEmpty(imageType)) {
            imageType = FileConstants.DEFAULT_FOLD;
        }

        /**
         * 创建文件夹
         *
         */
        String webPath = request.getSession().getServletContext().getRealPath("/") + "upload/";
        File webfile = new File(webPath);
        if (!webfile.exists()) {
            webfile.mkdirs();
        }

        String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/" + fileName;

        File tempFile = new File(filePath);
        if (tempFile.exists()) {
            tempFile.delete();
        }


        try {

            /**
             * 转存文件
             */
            file.transferTo(new File(filePath));

            //调用put方法上传
            Response res = uploadManager.put(filePath, imageType + fileName, getUpToken());
            //打印返回的信息
            logger.info(res.bodyString());

        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            logger.error(r.toString());

            try {
                //响应的文本信息
                logger.info(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
                logger.error("上传文件出错");
                resultEntity.setErrorMsg("上传文件出错");
                return resultEntity;
            }
            return resultEntity;
        }catch (IOException e) {
            //ignore
            logger.error("上传文件出错");
            resultEntity.setErrorMsg("上传文件出错");
            return resultEntity;
        }
        resultEntity.setImageRelativepath(imageType + resultEntity.getImageName());
        String url=FileConstants.IMAGE_ADDRESS + imageType + resultEntity.getImageName();
        resultEntity.setImageUrl(url);
        resultEntity.setSuccess(true);
        return resultEntity;
    }

    /**
     * @param absolutePath 参数--需要删除文件的绝对路径 如:http://o86fcoxw9.bkt.clouddn.com/my-java.JPG
     * @return 实体 --当实体属性isSuccess=true时、表示删除成功
     * @desc 删除单个文件-----
     * @date 2016年6月7日 下午1:11:30
     * @author tangliang
     */
    public static FileResultEntity deleteSingleFile(String absolutePath) {
        if(absolutePath.contains(FileConstants.IMAGE_ADDRESS)){
            String fileName = absolutePath.substring(FileConstants.IMAGE_ADDRESS.length(), absolutePath.length());
            FileResultEntity resultEntity = new FileResultEntity(fileName, false);
            resultEntity.setImageUrl(absolutePath);
            resultEntity.setImageRelativepath(fileName);
            try {
                bucketManager.delete(FileConstants.BUCKET_NAME, fileName);
                resultEntity.setSuccess(true);
            } catch (QiniuException e) {
                logger.error("deleteSingleFile has error! " + e);
                resultEntity.setErrorMsg("deleteSingleFile has error!" + e.response.toString());
                e.printStackTrace();
            }
            return resultEntity;
        }else{
            FileResultEntity resultEntity = new FileResultEntity();
            resultEntity.setSuccess(false);
            resultEntity.setErrorMsg("deleteSingleFile has error:非该空间图片！");
            return resultEntity;
        }
    }

    /**
     * @param absolutePath 参数--需要获取文件的绝对路径 如:http://o86fcoxw9.bkt.clouddn.com/my-java.JPG
     * @return FileInfo 云盘存储信息
     * @desc 获取七牛对象存储单个对象
     * @date 2016年6月7日 下午1:32:05
     * @author tangliang
     */
    public FileInfo getSingleFileInfo(String absolutePath) {
        String file_key = absolutePath.substring(FileConstants.IMAGE_ADDRESS.length(), absolutePath.length());
        FileInfo info = null;
        try {
            info = bucketManager.stat(FileConstants.BUCKET_NAME, file_key);

        } catch (QiniuException e) {
            Response r = e.response;
            logger.error("getSingleFileInfo has error! " + r);
            e.printStackTrace();
        }
        return info;
    }

    /**
     * @param absolutePath          图片绝对路径
     * @param geometricScalingValue 等比缩放值（一般是百分比、如70%：等比缩放70%）
     * @return 处理后的图片绝对路径
     * @desc 按照传入等比数据等比缩放图片、返回图片的绝对路径、可以直接在浏览器访问图片
     * @date 2016年6月7日 下午3:28:54
     * @author tangliang
     */
    public String zoomImage(String absolutePath, String geometricScalingValue) {
        StringBuffer buffer = new StringBuffer(absolutePath);
        if (!StringUtils.isEmpty(geometricScalingValue) && geometricScalingValue.endsWith(FileConstants.GEOMETRIC_SCALING_UNIT)) {
            buffer.append(FileConstants.ZOOMIMAGE_INTERFACE_SUFFIX).append(geometricScalingValue.substring(0, geometricScalingValue.indexOf(FileConstants.GEOMETRIC_SCALING_UNIT))).append("p");
        }
        return buffer.toString();
    }

    /**
     * @param absolutePath 需要处理图片的绝对路径
     * @param width        裁剪后的宽度
     * @param height       裁剪后的高度
     * @return 裁剪后图片的绝对路径、可以直接访问图片
     * @desc 裁剪图片、在原图片上按照新传入的宽度和高度裁剪成新图片(该方法是从图片左上角为原点开始计算裁剪距离)
     * @date 2016年6月7日 下午3:40:39
     * @author tangliang
     */
    public String cropPicture(String absolutePath, Integer width, Integer height) {
        StringBuffer buffer = new StringBuffer(absolutePath);
        if (width > 0 && height > 0) {
            buffer.append(FileConstants.CROP_PICTURE_INTERFACE_SUFFIX).append(width).append("x").append(height);
        }
        return buffer.toString();
    }


}
