package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Agent;
import cn.algerfan.domain.Underwriting;
import cn.algerfan.mapper.AgentMapper;
import cn.algerfan.mapper.UnderwritingMapper;
import cn.algerfan.service.UnderwritingService;
import cn.algerfan.util.AesUtil;
import cn.algerfan.util.CheckUtil;
import cn.algerfan.util.openid.Openid;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

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
    @Value("${filePath}")
    private String FILE_PATH;

    @Resource
    private UnderwritingMapper underwritingMapper;
    @Resource
    private AgentMapper agentMapper;

    @Override
    public Map<String,Object> insert(String formId, Underwriting underwriting, String encryptedData, String iv, String code) {
        log.info("encryptedData: "+encryptedData+"  iv: "+iv+"  code: "+code);
        log.info("underwriting: "+underwriting);
        Map<String,Object> map = new HashMap<>();
        //formId ==null || formId.equals("") ||
        if (underwriting.getName() == null || underwriting.getName().equals("") ||
                underwriting.getSex() == null || underwriting.getSex().equals("") ||
                underwriting.getBirthday() == null ||
                underwriting.getIntroduce() == null || underwriting.getIntroduce().equals("")) {
            map.put("status",0);
            map.put("msg","添加失败，信息不全！");
            return map;
        }
        //用户凭证不能为空
        if (code == null || encryptedData == null || iv ==null || code.length() == 0 || encryptedData.equals("") || iv.equals("")) {
            map.put("status", 0);
            map.put("msg", "code、encryptedData、iv 不能为空");
            return map;
        }
        Map<String, Object> map1 = Openid.session_key(code);

        Agent check = agentMapper.check(new AesUtil().AESEncode("lovewlgzs", String.valueOf(map1.get("openid"))));
        if(check==null) {
            map.put("status", 0);
            map.put("msg","添加失败，该代理人不存在");
            return map;
        }
        Underwriting underwriting1 = underwritingMapper.selectByAgentIdAndName(check.getAgentId(),underwriting.getName());
        if(underwriting1 != null) {
            underwriting.setUpdateTime(new Date());
            underwriting.setAgentId(check.getAgentId());
            underwriting.setUnderwritingId(underwriting1.getUnderwritingId());
            underwritingMapper.updateByPrimaryKeySelective(underwriting);
            map.put("status", 1);
            map.put("msg","该核保人已存在，已更新资料");
            return map;
        }
        underwriting.setSubmitTime(new Date());
        underwriting.setAgentId(check.getAgentId());
        underwritingMapper.insert(underwriting);
        map.put("status",1);
        map.put("msg","添加成功");
        return map;
    }

    @Override
    public Map<String, Object> upload(String name, MultipartFile[] multipartFiles, String encryptedData, String iv, String code) {
        Map<String,Object> map = new HashMap<>();
        //用户凭证不能为空
        if (code == null || encryptedData == null || iv ==null || code.length() == 0 || encryptedData.equals("") || iv.equals("")) {
            map.put("status", 0);
            map.put("msg", "code、encryptedData、iv 不能为空");
            return map;
        }
        Map<String, Object> map1 = Openid.session_key(code);
        Agent check = agentMapper.check(new AesUtil().AESEncode("lovewlgzs", String.valueOf(map1.get("openid"))));
        if(check==null) {
            map.put("status", 0);
            map.put("msg","添加失败，该代理人不存在");
            return map;
        }
        Underwriting underwriting = underwritingMapper.selectByAgentIdAndName(check.getAgentId(),name);
        if(underwriting == null) {
            map.put("status", 0);
            map.put("msg","添加失败，该核保人不存在");
            return map;
        }
        String data = "";
        int num = 0;
        if (multipartFiles.length != 0) {
            for (MultipartFile myFileName : multipartFiles) {
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
                    if (!saveFile(myFileName, path,realName)) {
                        map.put("status",0);
                        map.put("msg","文件上传失败");
                        return map;
                    }
                    if(num==0) {
                        data = path;
                    } else {
                        data = data + "," + path;
                    }
                    num++;
                } else {
                    map.put("status",0);
                    map.put("msg","文件格式不正确");
                    return map;
                }
            }
        }
        underwriting.setData(data);
        underwritingMapper.updateByPrimaryKey(underwriting);
        map.put("status",1);
        map.put("msg","添加成功");
        return map;
    }

    //文件写入
    private boolean saveFile(MultipartFile file, String path, String realName) {
        try {
            File fileDr = new File(FILE_PATH + path);
            if(!fileDr.exists()&&!fileDr.isDirectory()) {
                boolean mkdirs = fileDr.mkdirs();
                if(!mkdirs) return false;
            }
            if (!file.isEmpty()) {
                File saveFile = new File(FILE_PATH + path + "/" +realName);
                FileOutputStream outputStream = new FileOutputStream(saveFile);
                BufferedOutputStream out = new BufferedOutputStream(outputStream);
                out.write(file.getBytes());
                out.flush();
                out.close();
                outputStream.close();
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //删除文件
    public boolean deleteFile(String url) {
        File file = new File("." + url);
        if (file.exists() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    @Override
    public Map<String,Object> findUnderwriting(String encryptedData, String iv, String code) {
        Map<String,Object> map = new HashMap<>();
        //登录凭证不能为空
        if (code == null || encryptedData == null || iv ==null || code.length() == 0 || encryptedData.equals("") || iv.equals("")) {
            map.put("status", 0);
            map.put("msg", "code、encryptedData、iv 不能为空");
            return map;
        }
        Map<String, Object> map1 = Openid.session_key(code);

        String password = new AesUtil().AESEncode("lovewlgzs", String.valueOf(map1.get("openid")));
        Agent agent = agentMapper.selectByOpenid(password);
        if(agent==null) {
            map.put("status",0);
            map.put("msg","添加失败，非长城人寿北京分公司代理人");
            return map;
        }
        List<Underwriting> underwritingList =  underwritingMapper.selectByAgentId(agent.getAgentId());
        log.info("查询成功: "+underwritingList);
        map.put("status",1);
        map.put("msg","查询成功");
        map.put("list",underwritingList);
        return map;
    }

    @Override
    public PageInfo<Underwriting> select(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Underwriting> underwritingList = underwritingMapper.select(keyword);
        List<Underwriting> underwritings = new ArrayList<>();
        for (Underwriting underwriting : underwritingList) {
            if (underwriting.getConclusion() == null) {
                underwritings.add(underwriting);
            }
        }
        return new PageInfo<>(underwritings);
    }

    @Override
    public Underwriting selectById(Integer underwritingId) {
        return underwritingMapper.selectByPrimaryKey(underwritingId);
    }

    @Override
    public PageInfo<Underwriting> selectHistory(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Underwriting> underwritingList = underwritingMapper.select(keyword);
        List<Underwriting> underwritings = new ArrayList<>();
        for (Underwriting underwriting : underwritingList) {
            if (underwriting.getConclusion() != null) {
                underwritings.add(underwriting);
            }
        }
        return new PageInfo<>(underwritings);
    }

}
