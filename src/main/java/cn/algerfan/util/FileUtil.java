package cn.algerfan.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author AlgerFan
 */
public class FileUtil {

    /**
     * 响应输出文件
     * @param response
     * @param imgFile
     */
    public void responseFile(HttpServletResponse response, File imgFile) {
        try(InputStream is = new FileInputStream(imgFile);
            OutputStream os = response.getOutputStream();){
            // 图片文件流缓存池
            byte [] buffer = new byte[1024];
            while(is.read(buffer) != -1){
                os.write(buffer);
            }
            os.flush();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
