package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 *  图片文件查看下载Controller
 * </p>
 *
 * @author algerfan
 * @since 2019/4/22 19
 */
@Controller
@RequestMapping("/file")
@Api(value = "图片文件查看下载", tags = "图片文件查看下载")
public class PropertiesController extends BaseController {

    /**
     * 处理图片显示请求
     * @param filePath
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @ApiOperation(value = "处理图片显示请求", notes = "处理图片显示请求 参数：filePath-图片路径",
            httpMethod = "GET")
    public void showPicture(String filePath, HttpServletResponse response){
        propertiesService.show(filePath,response);
    }

    /**
     * 处理文件图片下载请求
     * @param filePath
     * @param response
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ApiOperation(value = "处理文件图片下载请求", notes = "处理文件图片下载请求 参数：filePath-文件图片路径",
            httpMethod = "GET")
    public void download(String filePath, HttpServletResponse response){
        propertiesService.download(filePath,response);
    }

    /**
     * 批量文件压缩后下载
     * @return
     * @throws IOException
     */
    @RequestMapping("/download2")
    public ResponseEntity<byte[]> download2() throws IOException {

        //需要压缩的文件
        List<String> list = new ArrayList<>();
        list.add("D:/project/uploadData/2019/4/17/王五/4fbc62b9-cb4f-4279-82e0-3b8ed964fd91.jpg");
        list.add("D:/project/uploadData/2019/4/17/王五/e3edbe31-40b6-4dc4-bb5c-ec971f6c42ed.jpg");
        list.add("D:/project/uploadData/2019/4/17/张三/d22c9498-2c09-40a1-87d0-9ee3ddbc2b92.jpg");
        list.add("D:/project/uploadData/2019/4/17/张三/e15ef677-7b54-4916-9c34-a46acdc17160.jpg");

        //压缩后的文件
        String resourcesName = "test.zip";

        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("D:/"+resourcesName));
        InputStream input;

        for (String str : list) {
            input = new FileInputStream(new File(str));
            zipOut.putNextEntry(new ZipEntry(str));
            int temp;
            while((temp = input.read()) != -1){
                zipOut.write(temp);
            }
            input.close();
        }
        zipOut.close();
        File file = new File("D:/"+resourcesName);
        HttpHeaders headers = new HttpHeaders();
        String filename = new String(resourcesName.getBytes("utf-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    /**
     * 文件压缩下载
     * @param request
     * @param response
     */
    /*@RequestMapping("/download3")
    public void download3(HttpServletRequest request, HttpServletResponse response) {
        //响应头的设置
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");

        //设置压缩包的名字
        //解决不同浏览器压缩包名字含有中文时乱码的问题

        String downloadName = "test02.zip";
        //返回客户端浏览器的版本号、类型
        String agent = request.getHeader("USER-AGENT");
        try {
            //针对IE或者以IE为内核的浏览器：
            if (agent.contains("MSIE") || agent.contains("Trident")) {
                downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
            } else {
                //非IE浏览器的处理：
                downloadName = new String(downloadName.getBytes("UTF-8"),"ISO-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment;fileName=\"" + downloadName + "\"");

        //设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zip = null;
        try {
            zip = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            //设置压缩方法
            zip.setMethod(ZipOutputStream.DEFLATED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //循环将文件写入压缩流
        DataOutputStream os = null;
        //从数据库中取出要下载的图片路径、并循环写入压缩
        List<String> list = new ArrayList<>();
        list.add("D:/project/uploadData/2019/4/17/王五/4fbc62b9-cb4f-4279-82e0-3b8ed964fd91.jpg");
        list.add("D:/project/uploadData/2019/4/17/王五/e3edbe31-40b6-4dc4-bb5c-ec971f6c42ed.jpg");
        list.add("D:/project/uploadData/2019/4/17/张三/d22c9498-2c09-40a1-87d0-9ee3ddbc2b92.jpg");
        list.add("D:/project/uploadData/2019/4/17/张三/e15ef677-7b54-4916-9c34-a46acdc17160.jpg");
        list.add("D:/project/uploadData/2018/4/17/王五/4fbc62b9-cb4f-4279-82e0-3b8ed964fd91.jpg");
        list.add("D:/project/uploadData/2018/4/17/王五/e3edbe31-40b6-4dc4-bb5c-ec971f6c42ed.jpg");
        list.add("D:/project/uploadData/2018/4/17/张三/d22c9498-2c09-40a1-87d0-9ee3ddbc2b92.jpg");
        list.add("D:/project/uploadData/2018/4/17/张三/e15ef677-7b54-4916-9c34-a46acdc17160.jpg");
        list.add("D:/project/uploadData/2017/4/17/王五/4fbc62b9-cb4f-4279-82e0-3b8ed964fd91.jpg");
        list.add("D:/project/uploadData/2017/4/17/王五/e3edbe31-40b6-4dc4-bb5c-ec971f6c42ed.jpg");
        list.add("D:/project/uploadData/2017/4/17/张三/d22c9498-2c09-40a1-87d0-9ee3ddbc2b92.jpg");
        list.add("D:/project/uploadData/2017/4/17/张三/e15ef677-7b54-4916-9c34-a46acdc17160.jpg");
        list.add("D:/project/uploadData/2016/4/17/王五/4fbc62b9-cb4f-4279-82e0-3b8ed964fd91.jpg");
        list.add("D:/project/uploadData/2016/4/17/王五/e3edbe31-40b6-4dc4-bb5c-ec971f6c42ed.jpg");
        list.add("D:/project/uploadData/2016/4/17/张三/d22c9498-2c09-40a1-87d0-9ee3ddbc2b92.jpg");
        list.add("D:/project/uploadData/2016/4/17/张三/e15ef677-7b54-4916-9c34-a46acdc17160.jpg");
        list.add("D:/project/uploadData/2015/4/17/王五/4fbc62b9-cb4f-4279-82e0-3b8ed964fd91.jpg");
        list.add("D:/project/uploadData/2015/4/17/王五/e3edbe31-40b6-4dc4-bb5c-ec971f6c42ed.jpg");
        list.add("D:/project/uploadData/2015/4/17/张三/d22c9498-2c09-40a1-87d0-9ee3ddbc2b92.jpg");
        list.add("D:/project/uploadData/2015/4/17/张三/e15ef677-7b54-4916-9c34-a46acdc17160.jpg");
        for (String worker2 : list) {
            *//*String filename =worker2;
            String removeStr = "/upload/workerCard";
            //去掉相对路径中的两个目录路径
            filename = filename.replace(removeStr, "");
            String path = request.getSession().getServletContext().getRealPath("/upload/workerCard"+filename);*//*
            File file = new File(worker2);
            if(file.exists() && zip!=null){
                try {
                    //添加ZipEntry，并ZipEntry中写入文件流
                    //这里，加上i是防止要下载的文件有重名的导致下载失败
                    zip.putNextEntry(new ZipEntry(worker2));
                    os = new DataOutputStream(zip);
                    InputStream is = new FileInputStream(file);
                    byte[] b = new byte[100];
                    int length;
                    while((length = is.read(b))!= -1){
                        os.write(b, 0, length);
                    }
                    is.close();
                    zip.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //关闭流
        if(zip!=null && os!=null) {
            try {
                os.flush();
                os.close();
                zip.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @ResponseBody
    @RequestMapping("/download4")
    public void download4() {
        boolean b = new MultiPartDownLoad("http://underwriting.algerfan.cn/file/show?filePath=/uploadData/project.zip", "D:/project").executeDownLoad();
        log.info(b);
    }*/

}
