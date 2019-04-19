package cn.algerfan.util;

/**
 * @Description 检验文件格式
 **/
public class CheckUtil {

    public boolean verify(String fileName){
        String reg="(?i).+?\\.(jpg|gif|bmp|png|jpeg|pdf|word)";
        return fileName.matches(reg);
    }

    public boolean verify(String[] fileNames){
        String reg="(?i).+?\\.(jpg|gif|bmp|png|jpeg|pdf|word)";
        for (String fileName : fileNames) {
            if (!fileName.matches(reg)) {
                return false;
            }
        }
        return true;
    }
}
