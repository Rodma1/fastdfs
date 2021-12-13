package com.chen;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@SpringBootTest
class FastdfsApplicationTests {

    @Test
    void contextLoads() throws IOException, MyException {
        ClientGlobal.initByProperties("fastdfs-client.properties");
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getConnection();
        StorageServer storageServer=null;
        StorageClient1 client1=new StorageClient1(trackerServer,storageServer);

//        额外携带信息
        NameValuePair pairs[]=null;
//        删除文件
        String fileId=client1.upload_file1("E:\\图片\\代码图片\\pexels-luis-gomes-546819.jpg","jpg",pairs);
        System.out.println(fileId);
    }

    @Test
    void download() throws IOException, MyException {
        ClientGlobal.initByProperties("fastdfs-client.properties");
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getConnection();
        StorageServer storageServer=null;
        StorageClient1 client1=new StorageClient1(trackerServer,storageServer);

//       下载文件
        byte[] bytes=client1.download_file1("group1/M00/00/00/CgAED2G1q2GAblQ7AAKCfiOCiM4121_big.png");
//        保存文件
        FileOutputStream fos=new FileOutputStream(new File("1.jpg"));
//        打印文件名字
        fos.write(bytes);
//        关闭
        fos.close();
    }
//    token安全链
    @Test
    void testToken() throws UnsupportedEncodingException, MyException, NoSuchAlgorithmException {
//        拿一个时间错
        int ts=(int)Instant.now().getEpochSecond();
//        ts是令牌，第三个是在fastdfs-client里面的http_secret_key
        String token=ProtoCommon.getToken("M00/00/00/CgAED2G1q2GAblQ7AAKCfiOCiM4121_big.png",ts,"FastDFS1234567890");
        StringBuilder stringBuilder=new StringBuilder();
//        追加
        stringBuilder.append("http://101.35.145.209")
                .append("/group1/M00/00/00/CgAED2G1q2GAblQ7AAKCfiOCiM4121_big.png")
                .append("?token=")
                .append(token)
                .append("&ts=")
                .append(ts);
        System.out.println(stringBuilder.toString());
    }

}
