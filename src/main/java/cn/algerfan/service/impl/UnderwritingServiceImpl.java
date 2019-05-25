package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Agent;
import cn.algerfan.domain.Result;
import cn.algerfan.domain.Underwriting;
import cn.algerfan.mapper.AgentMapper;
import cn.algerfan.mapper.UnderwritingMapper;
import cn.algerfan.service.UnderwritingService;
import cn.algerfan.util.AesUtil;
import cn.algerfan.util.CheckUtil;
import cn.algerfan.util.openid.HttpRequest;
import cn.algerfan.util.openid.Openid;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
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
    private String filePath;

    @Resource
    private UnderwritingMapper underwritingMapper;
    @Resource
    private AgentMapper agentMapper;

    @Override
    public Map<String,Object> insert(String formId, Underwriting underwriting, String encryptedData, String iv, String code) {
        log.info("encryptedData: "+encryptedData+"  iv: "+iv+"  code: "+code);
        log.info("underwriting: "+underwriting);
        Map<String,Object> map = new HashMap<>(10);
        //formId ==null || formId.equals("") ||
        if (underwriting.getName() == null || "".equals(underwriting.getName()) ||
                underwriting.getSex() == null || "".equals(underwriting.getSex()) ||
                underwriting.getBirthday() == null ||
                underwriting.getIntroduce() == null || "".equals(underwriting.getIntroduce())) {
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

        Agent check = agentMapper.selectByOpenid(new AesUtil().AESEncode("lovewlgzs", String.valueOf(map1.get("openid"))));
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
        Agent check = agentMapper.selectByOpenid(new AesUtil().AESEncode("lovewlgzs", String.valueOf(map1.get("openid"))));
        if(check==null) {
            map.put("status", 0);
            map.put("msg","添加失败，该代理人不存在");
            return map;
        }
        log.info(check.getAgentId()+"------------"+name);
        try {
            name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("乱码解决后用户名：" + name);
        Underwriting underwriting = underwritingMapper.selectByAgentIdAndName(check.getAgentId(),name);
        log.info(underwriting);
        if(underwriting == null) {
            map.put("status", 0);
            map.put("msg","添加失败，该核保人不存在");
            return map;
        }
        StringBuilder data = new StringBuilder();
        int num = 0;
        if (multipartFiles.length != 0) {
            for (int i = 0; i < multipartFiles.length-1; i++) {
                String fileName = multipartFiles[i].getOriginalFilename();
                CheckUtil checkUtil = new CheckUtil();
                if(fileName != null && checkUtil.verify(fileName)) {
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
                    if (!saveFile(multipartFiles[i], path,realName)) {
                        map.put("status",0);
                        map.put("msg","文件上传失败");
                        return map;
                    }
                    if(num==0) {
                        data.append(path).append("/").append(realName);
                    } else {
                        data.append(data).append(",").append(path).append("/").append(realName);
                    }
                    num++;
                } else {
                    map.put("status",0);
                    map.put("msg","文件格式不正确");
                    return map;
                }
            }
        }
        underwriting.setData(String.valueOf(data));
        underwritingMapper.updateByPrimaryKey(underwriting);
        map.put("status",1);
        map.put("msg","添加成功");
        return map;
    }

    /**
     * 文件写入
     * @param file
     * @param path
     * @param realName
     */
    private boolean saveFile(MultipartFile file, String path, String realName) {
        try {
            File fileDr = new File(filePath + path);
            if(!fileDr.exists()&&!fileDr.isDirectory()) {
                boolean mkdirs = fileDr.mkdirs();
                if(!mkdirs) {
                    return false;
                }
            }
            if (!file.isEmpty()) {
                File saveFile = new File(filePath + path + "/" +realName);
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

    /**
     * 删除文件
     */
    /*public boolean deleteFile(String url) {
        File file = new File("." + url);
        if (file.exists() && file.exists()) {
            return file.delete();
        }
        return false;
    }*/

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

    @Override
    public PageInfo<Underwriting> selectByDate(String keyword, int pageNum, int pageSize) {
        //2017-05-06 至 2018-05-24
        String first = keyword.substring(0,10);
        String last = keyword.substring(13);
        PageHelper.startPage(pageNum, pageSize);
        List<Underwriting> underwritingList = underwritingMapper.selectAll();
        List<Underwriting> underwritings = new ArrayList<>();
        for (Underwriting underwriting : underwritingList) {
            //处理核保人的提交时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(underwriting.getSubmitTime());
            if (underwriting.getConclusion() != null && first.compareTo(dateString)<0 && last.compareTo(dateString)>0) {
                underwritings.add(underwriting);
            }
        }
        return new PageInfo<>(underwritings);
    }

    @Override
    public Result send(Integer underwritingId, String auditResult, String note) {
        if(underwritingId==null ||underwritingId==0 || auditResult==null || "".equals(auditResult) ||
                note == null || "".equals(note)) {
            return new Result(0,"发送失败，信息不完整！");
        }
        Underwriting underwriting = underwritingMapper.selectByPrimaryKey(underwritingId);
        if(underwriting.getConclusion() != null) {
            return new Result(0,"发送失败，该核保人已经发送过通知");
        }
        Agent agent = agentMapper.selectByPrimaryKey(underwriting.getAgentId());
        if(agent == null) {
            return new Result(0,"发送失败，该代理人不存在");
        }
        String openid = new AesUtil().AESDncode("lovewlgzs", agent.getOpenid());
        JSONObject jsonObject = new JSONObject();
        //代理人openid
        jsonObject.put("touser", openid);
        //消息模版id
        jsonObject.put("template_id", "1BTR4gR2KAcGX_LSXRS-7InGCIpezSEc3d5Te1qFT5k");
        //表单id
        jsonObject.put("form_id", underwriting.getFormId());
        String templateContent = "{'keyword1':{'value':'测试'},'keyword2':{'value':'" + auditResult +
                "'},'keyword3':{'value':'" + note + "'}}";
        jsonObject.put("data", templateContent);
        jsonObject.put("emphasis_keyword","keyword2.DATA");
        String string = HttpRequest.sendPost(jsonObject);
        log.info(string);
        String status = "ok";
        if(string.contains(status)) {
            underwriting.setConclusion("审核结果："+ auditResult+ "，备注：" +note);
            underwriting.setUpdateTime(new Date());
            underwritingMapper.updateByPrimaryKeySelective(underwriting);
        }
        return new Result(1,"发送成功！");
    }

}
