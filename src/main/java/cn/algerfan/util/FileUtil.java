package cn.algerfan.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtil {

    /**
     * 响应输出文件
     * @param response
     * @param imgFile
     */
    public void responseFile(HttpServletResponse response, File imgFile) {
        try(InputStream is = new FileInputStream(imgFile);
            OutputStream os = response.getOutputStream();){
            byte [] buffer = new byte[1024]; // 图片文件流缓存池
            while(is.read(buffer) != -1){
                os.write(buffer);
            }
            os.flush();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
