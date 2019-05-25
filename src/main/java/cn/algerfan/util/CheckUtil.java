package cn.algerfan.util;

/**
 * @author AlgerFan
 * @Description 检验文件格式
 **/
public class CheckUtil {

    public boolean verify(String fileName){
        String reg="(?i).+?\\.(jpg|gif|bmp|png|jpeg|docx|doc|xlsx|xls)";
        return fileName.matches(reg);
    }

    public boolean checkImage(String imageName){
        String reg="(?i).+?\\.(jpg|gif|bmp|png|jpeg)";
        return imageName.matches(reg);
    }

    public boolean checkFile(String fileName){
        String reg="(?i).+?\\.(docx|doc|xlsx|xls)";
        return fileName.matches(reg);
    }

    public boolean verify(String[] fileNames){
        String reg="(?i).+?\\.(jpg|gif|bmp|png|jpeg|docx|doc|xlsx|xls)";
        for (String fileName : fileNames) {
            if (!fileName.matches(reg)) {
                return false;
            }
        }
        return true;
    }
}
