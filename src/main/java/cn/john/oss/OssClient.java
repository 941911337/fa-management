package cn.john.oss;

import java.io.File;
import java.util.List;

/**
 * @Author yanzhengwei
 * @Description OssClient
 * @Date 2021/7/20
 **/
public abstract class OssClient  {

    private static OssInterface ossInterface;

    public static void setOssInterface(OssInterface ossInterface) {
        OssClient.ossInterface = ossInterface;
    }

    public static String uploadFile(File file, String fileName) {
        return ossInterface.uploadFile(file,fileName);
    }


    public static void delFile(List<String> filePathList) throws Exception {
         ossInterface.delFile(filePathList);
    }


    public static boolean download(String path){
        return ossInterface.download(path);
    }
}
