package com.wangfugui.apprentice.controller;

import com.wangfugui.apprentice.common.util.MinioUtil;
import com.wangfugui.apprentice.common.util.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

@Api(tags = "文件操作接口")
@RestController
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    MinioUtil minioUtil;

    @ApiOperation("上传一个文件")
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public ResponseUtils fileupload(@RequestParam MultipartFile uploadfile, @RequestParam String bucket,
                                    @RequestParam(required = false) String objectName) throws Exception {
        minioUtil.createBucket(bucket);
        if (objectName != null) {
            minioUtil.uploadFile(uploadfile.getInputStream(), bucket, objectName + "/" + uploadfile.getOriginalFilename());
        } else {
            minioUtil.uploadFile(uploadfile.getInputStream(), bucket, uploadfile.getOriginalFilename());
        }
        return ResponseUtils.success();
    }

    @ApiOperation("列出所有的桶")
    @RequestMapping(value = "/listBuckets", method = RequestMethod.GET)
    public ResponseUtils listBuckets() throws Exception {
        return ResponseUtils.success(minioUtil.listBuckets());
    }

    @ApiOperation("递归列出一个桶中的所有文件和目录")
    @RequestMapping(value = "/listFiles", method = RequestMethod.GET)
    public ResponseUtils listFiles(@RequestParam String bucket) throws Exception {
        return ResponseUtils.success(minioUtil.listFiles(bucket));
    }

    @ApiOperation("下载一个文件")
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void downloadFile(@RequestParam String bucket, @RequestParam String objectName,
                             HttpServletResponse response) throws Exception {
        InputStream stream = minioUtil.download(bucket, objectName);
        ServletOutputStream output = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName.substring(objectName.lastIndexOf("/") + 1), "UTF-8"));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(stream, output);
    }


    @ApiOperation("删除一个文件")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
    public ResponseUtils deleteFile(@RequestParam String bucket, @RequestParam String objectName) throws Exception {
        minioUtil.deleteObject(bucket, objectName);
        return ResponseUtils.success();
    }

    @ApiOperation("删除一个桶")
    @RequestMapping(value = "/deleteBucket", method = RequestMethod.GET)
    public ResponseUtils deleteBucket(@RequestParam String bucket) throws Exception {
        minioUtil.deleteBucket(bucket);
        return ResponseUtils.success();
    }


    @ApiOperation("复制一个文件")
    @GetMapping("/copyObject")
    public ResponseUtils copyObject(@RequestParam String sourceBucket, @RequestParam String sourceObject, @RequestParam String targetBucket, @RequestParam String targetObject) throws Exception {
        minioUtil.copyObject(sourceBucket, sourceObject, targetBucket, targetObject);
        return ResponseUtils.success();
    }

    @GetMapping("/getObjectInfo")
    @ApiOperation("获取文件信息")
    public ResponseUtils getObjectInfo(@RequestParam String bucket, @RequestParam String objectName) throws Exception {

        return ResponseUtils.success(minioUtil.getObjectInfo(bucket, objectName));
    }

    @GetMapping("/getPresignedObjectUrl")
    @ApiOperation("获取一个连接以供下载")
    public ResponseUtils getPresignedObjectUrl(@RequestParam String bucket, @RequestParam String objectName, @RequestParam Integer expires) throws Exception {

        return ResponseUtils.success(minioUtil.getPresignedObjectUrl(bucket, objectName, expires));
    }

    @GetMapping("/listAllFile")
    @ApiOperation("获取minio中所有的文件")
    public ResponseUtils listAllFile() throws Exception {

        return ResponseUtils.success(minioUtil.listAllFile());
    }


}