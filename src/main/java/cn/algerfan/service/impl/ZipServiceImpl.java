package cn.algerfan.service.impl;

import cn.algerfan.domain.Result;
import cn.algerfan.domain.Zip;
import cn.algerfan.enums.ResultCodeEnum;
import cn.algerfan.mapper.ZipMapper;
import cn.algerfan.service.WebSocket;
import cn.algerfan.service.ZipService;
import cn.algerfan.util.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author algerfan
 * @since 2019/8/2 09
 */
@Service
public class ZipServiceImpl implements ZipService {
    @Value("${filePath}")
    private String filePath;

    @Resource
    private ZipMapper zipMapper;
    @Autowired
    private WebSocket webSocket;

    @Override
    public PageInfo<Zip> selectMonth(int pageNum, int pageSize) {
        //判断是否文件表中是否存在当前月份
        String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
        String year = strNow[0];
        String month = strNow[1];
        Zip zip = zipMapper.selectByMonth(year + month);
        if(zip == null) {
            Zip zip1 = new Zip();
            zip1.setMonth(year+month);
            zip1.setCompress("0");
            zipMapper.insert(zip1);
        }
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(zipMapper.selectMonth());
    }

    @Override
    public Result getZip(Integer zipId, String url) {
        if(zipId == null || url == null || "".equals(url)) {
            return new Result(ResultCodeEnum.FAIL);
        }
        Zip zip = zipMapper.selectByPrimaryKey(zipId);
        String year = zip.getMonth().substring(0,4);
        String month = zip.getMonth().substring(4);
        String downloadPath = "/"+ zip.getMonth() +".zip";
        FileUtil book = new FileUtil();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(filePath + downloadPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        book.toZip(filePath +"/uploadData/" + year + "/" + month, fileOutputStream,true);
        String path = url + "/file/files?filename=" + filePath + downloadPath;
        zip.setDownload(path);
        zip.setCompress("1");
        zipMapper.updateByPrimaryKey(zip);
        //发送websocket消息通知
        webSocket.sendMessage(year + month + "月份数据打包完成，删除之前请务必确保已下载完毕！");
        return new Result(ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result deleteZip(Integer zipId) {
        Zip zip = zipMapper.selectByPrimaryKey(zipId);
        FileUtil fileUtil = new FileUtil();

        //删除该月份文件夹
        String substring = zip.getDownload().substring(zip.getDownload().indexOf("=") + 1, zip.getDownload().length()-4);
        //对数据进行处理：D:/project/201908
        substring = substring.substring(0,substring.length()-7) + "/uploadData" + substring.substring(substring.length()-7, substring.length()-2) + "/" + substring.substring(substring.length()-2);
        System.out.println(substring);
        fileUtil.deleteDir(new File(substring));

        //删除该月份压缩包
        fileUtil.deleteDir(new File(zip.getDownload().substring(zip.getDownload().indexOf("=") + 1)));
        //更新文件表
        zip.setDownload(null);
        zip.setCompress("2");
        zipMapper.updateByPrimaryKey(zip);
        return new Result(ResultCodeEnum.SUCCESS);
    }

}
