package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Agent;
import cn.algerfan.domain.Result;
import cn.algerfan.domain.Underwriting;
import cn.algerfan.enums.ResultCodeEnum;
import cn.algerfan.mapper.AgentMapper;
import cn.algerfan.mapper.UnderwritingMapper;
import cn.algerfan.service.UnderwritingService;
import cn.algerfan.util.AesUtil;
import cn.algerfan.util.CheckUtil;
import cn.algerfan.util.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 核保人实现类
 * </p>
 *
 * @author algerfan
 * @since 2019/4/17 08
 */
@Service
public class UnderwritingServiceImpl extends BaseDao<Underwriting> implements UnderwritingService {

    @Resource
    private UnderwritingMapper underwritingMapper;
    @Resource
    private AgentMapper agentMapper;

    @Override
    public Result insert(Underwriting underwriting, MultipartFile[] myFileNames) {
        Result result = new Result();
        if (underwriting.getName() == null || underwriting.getName().equals("") ||
                underwriting.getSex() == null || underwriting.getSex().equals("") ||
                underwriting.getBirthday() == null || underwriting.getBirthday().equals("") ||
                underwriting.getIntroduce() == null || underwriting.getIntroduce().equals("")) {
            result.setMsg("添加失败，信息不全！");
            result.setCode(-1);
            return result;
        }
        String data = "";
        int num = 0;
        if (myFileNames.length != 0) {
            for (MultipartFile myFileName : myFileNames) {
                String fileName = myFileName.getOriginalFilename();
                assert fileName != null;
                CheckUtil checkUtil = new CheckUtil();
                if(checkUtil.verify(fileName)) {
                    String realName;
                    String fileNameExtension = fileName.substring(fileName.indexOf("."));
                    // 生成实际存储的真实文件名
                    realName = UUID.randomUUID().toString() + fileNameExtension;
                    // 自定义的上传目录
                    Calendar cale = Calendar.getInstance();
                    int year = cale.get(Calendar.YEAR);
                    int month = cale.get(Calendar.MONTH) + 1;
                    int day = cale.get(Calendar.DATE);
                    String path = "/uploadData/"+ year + "/"+ month + "/" + day + "/" + underwriting.getName();
                    UploadUtil uploadUtil = new UploadUtil();
                    if (!uploadUtil.saveFile(myFileName, path,realName)) {
                        result.setMsg("文件上传失败 ");
                        result.setCode(-1);
                        return result;
                    }
                    if(num==0) {
                        data = path;
                    } else {
                        data = data + "," + path;
                    }
                    num++;
                } else {
                    result.setMsg("文件格式不正确");
                    result.setCode(-1);
                    return result;
                }
            }
        }
        underwriting.setData(data);
        underwritingMapper.insert(underwriting);
        result = new Result(ResultCodeEnum.SAVE);
        return result;
    }

    @Override
    public Result findUnderwriting(String openid) {
        String password = new AesUtil().AESEncode("lovewlgzs", openid);
        Agent agent = agentMapper.selectByOpenid(password);
        List<Underwriting> underwritingList =  underwritingMapper.selectByAgentId(agent.getAgentId());
        log.info("查询成功: "+underwritingList);
        return new Result(ResultCodeEnum.FIND, underwritingList);
    }

    @Override
    public PageInfo<Underwriting> select(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Underwriting> underwritingList = underwritingMapper.select(keyword);
        return new PageInfo<>(underwritingList);
    }

}
