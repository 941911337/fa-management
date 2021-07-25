package cn.john.oss;

import com.qingstor.sdk.exception.QSException;

import java.io.File;
import java.util.List;

/**
 * @Author John Yan
 * @Description OSSInterface
 * @Date 2021/7/19
 **/
public interface OssInterface {

    /**
     * 上传文件
     * @param file 文件
     * @param fileName 文件名 不传默认使用 文件名称
     * @return 文件路径
     */
     String uploadFile(File file,String fileName);


    /**
     * 删除文件
     * @param filePathList
     * @return
     * @throws Exception
     */
    boolean delFile(List<String> filePathList) throws Exception;


    /**
     * 下载文件
     * @param path 需要下载文件名
     * @return
     */
    boolean download(String path) ;
}
