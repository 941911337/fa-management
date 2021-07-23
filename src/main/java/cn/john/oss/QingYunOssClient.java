package cn.john.oss;

import cn.hutool.core.io.FileUtil;
import cn.john.common.Constants;
import cn.john.exception.BusinessException;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.Types;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yanzhengwei
 * @Description QingYunOssClient
 * @Date 2021/7/20
 **/
@Slf4j
@Component
public class QingYunOssClient implements OssInterface{

    @NacosValue(value = "${oss-folder}")
    private String folder ;

    @NacosValue(value = "${oss-qy-accessKey}")
    private String accessKey ;

    @NacosValue(value = "${oss-qy-accessSecret}")
    private String accessSecret ;

    @NacosValue(value = "${oss-qy-zone}")
    private  String zoneKey ;

    @NacosValue(value = "${oss-qy-bucket}")
    private  String bucketName;

    private Bucket bucket;

    @PostConstruct
    private void init(){
        EnvContext env = new EnvContext(accessKey, accessSecret);
        bucket = new Bucket(env, zoneKey, bucketName);
    }


    @Override
    public String uploadFile(File file, String fileName) {
        String objectName = folder + fileName;
        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        input.setBodyInputFile(file);
        try {
            Bucket.PutObjectOutput output = bucket.putObject(objectName, input);
            if (output.getStatueCode() != Constants.QyCode.UPLOAD_SUCCESS_CODE) {
                throw new BusinessException("上传失败:"+output.getMessage());
            }
            return objectName;
        } catch (QSException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean  delFile(List<String> filePathList){
        Bucket.DeleteMultipleObjectsInput deleteInput = new Bucket.DeleteMultipleObjectsInput();
        List<Types.KeyModel> keyList = new ArrayList<>();
        for (String key : filePathList) {
            Types.KeyModel keyModel = new Types.KeyModel();
            keyModel.setKey(key);
            keyList.add(keyModel);
        }
        deleteInput.setObjects(keyList);
        Bucket.DeleteMultipleObjectsOutput output = null;
        try {
            output = bucket.deleteMultipleObjects(deleteInput);
        } catch (QSException qsException) {
            log.error(qsException.getMessage());
        }
        if (output.getStatueCode() == Constants.QyCode.SUCCESS_CODE
                || output.getStatueCode() == Constants.QyCode.DEL_SUCCESS_CODE) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean download(String path) {
        String localKeptPath = "/opt/backup/"+path;
        try {
            Bucket.GetObjectInput input = new Bucket.GetObjectInput();
            Bucket.GetObjectOutput output = bucket.getObject(path, input);
            InputStream inputStream = output.getBodyInputStream();

            if (output.getStatueCode() == Constants.QyCode.SUCCESS_CODE) {
                if (inputStream != null) {
                    FileUtil.writeFromStream(inputStream,localKeptPath);
                    return true;
                }
            } else {
                // Failed
                log.error("Failed to get object.");
                log.error("StatueCode = " + output.getStatueCode());
                log.error("Message = " + output.getMessage());
                log.error("RequestId = " + output.getRequestId());
                log.error("Code = " + output.getCode());
                log.error("Url = " + output.getUrl());
            }
            if (inputStream != null){
                inputStream.close();
            }
        } catch (QSException | IOException e) {
            log.error(e.getMessage());
        }
        return false;
    }




}
