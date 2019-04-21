package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Agent;
import cn.algerfan.domain.Underwriting;
import cn.algerfan.mapper.AgentMapper;
import cn.algerfan.mapper.UnderwritingMapper;
import cn.algerfan.service.UnderwritingService;
import cn.algerfan.util.AesUtil;
import cn.algerfan.util.CheckUtil;
import cn.algerfan.util.UploadUtil;
import cn.algerfan.util.openid.HttpRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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

    @Resource
    private UnderwritingMapper underwritingMapper;
    @Resource
    private AgentMapper agentMapper;

    @Override
    public Map<String,Object> insert(String formId, Underwriting underwriting, String encryptedData, String iv, String code) {
        Map<String,Object> map = new HashMap<>();
        if (formId ==null || formId.equals("") || underwriting.getName() == null || underwriting.getName().equals("") ||
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
        //小程序唯一标识   (在微信小程序管理后台获取)
        String APP_ID = "wx1633b8cd0a523508";
        //小程序的 app secret (在微信小程序管理后台获取)
        String APP_SECRET = "0765456e314c6cb199ce97b7bb949a43";
        //授权（必填）
        String grant_type = "authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        log.info("openid: "+openid);
        Agent check = agentMapper.check(new AesUtil().AESEncode("lovewlgzs", String.valueOf(openid)));
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
        //小程序唯一标识   (在微信小程序管理后台获取)
        String APP_ID = "wx1633b8cd0a523508";
        //小程序的 app secret (在微信小程序管理后台获取)
        String APP_SECRET = "0765456e314c6cb199ce97b7bb949a43";
        //授权（必填）
        String grant_type = "authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        log.info("openid: "+openid);
        Agent check = agentMapper.check(new AesUtil().AESEncode("lovewlgzs", String.valueOf(openid)));
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
                    UploadUtil uploadUtil = new UploadUtil();
                    if (!uploadUtil.saveFile(myFileName, path,realName)) {
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

    @Override
    public Map<String,Object> findUnderwriting(String encryptedData, String iv, String code) {
        Map<String,Object> map = new HashMap<>();
        //登录凭证不能为空
        if (code == null || encryptedData == null || iv ==null || code.length() == 0 || encryptedData.equals("") || iv.equals("")) {
            map.put("status", 0);
            map.put("msg", "code、encryptedData、iv 不能为空");
            return map;
        }

        //小程序唯一标识   (在微信小程序管理后台获取)
        String APP_ID = "wx1633b8cd0a523508";
        //小程序的 app secret (在微信小程序管理后台获取)
        String APP_SECRET = "0765456e314c6cb199ce97b7bb949a43";
        //授权（必填）
        String grant_type = "authorization_code";

        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        log.info("openid: "+openid);
        String password = new AesUtil().AESEncode("lovewlgzs", openid);
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
        return new PageInfo<>(underwritingList);
    }

}
