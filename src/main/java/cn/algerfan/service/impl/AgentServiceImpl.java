package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Company;
import cn.algerfan.mapper.AgentMapper;
import cn.algerfan.domain.Agent;
import cn.algerfan.mapper.CompanyMapper;
import cn.algerfan.service.AgentService;
import cn.algerfan.util.AesUtil;
import cn.algerfan.util.openid.Aes;
import cn.algerfan.util.openid.HttpRequest;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  代理人服务实现类
 * </p>
 *
 * @author algerfan
 * @since 2019-04-13
 */
@Service
public class AgentServiceImpl extends BaseDao<Agent> implements AgentService {
    @Resource
    private AgentMapper agentMapper;
    @Resource
    private CompanyMapper companyMapper;

    @Override
    public Map<String, Object> register(String employeeId, String company, String encryptedData, String iv, String code) {
        log.info("employeeId："+employeeId+"    company："+company+"   encryptedData: "+encryptedData+"  iv: "+iv+"  code: "+code);
        Map<String, Object> map = new HashMap<>();
        if (employeeId == null || employeeId.length() == 0) {
            map.put("status", 0);
            map.put("msg", "employeeId 不能为空");
            return map;
        }
        Company byEmployeeID = companyMapper.findByEmployeeID(employeeId.substring(0, 4));
        if(byEmployeeID==null) {
            map.put("status",0);
            map.put("msg","对不起，您所录入的工号非长城人寿北京分公司工号，无法进行注册");
            return map;
        }
        if (company == null || company.length() == 0) {
            map.put("status", 0);
            map.put("msg", "company 不能为空");
            return map;
        }
        Agent check = agentMapper.check(employeeId,company);
        log.info("check: "+check);
        if(check!=null) {
            map.put("status", 0);
            map.put("msg","注册失败，该代理人已存在");
            return map;
        }
        AesUtil aesUtil = new AesUtil();
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

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = Aes.decrypt(encryptedData, session_key, iv);
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");
                log.info("解密成功");

                JSONObject userInfoJSON = JSONObject.fromObject(result);
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
                log.info("userInfo: "+userInfo);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        String openId = aesUtil.AESDncode("lovewlgzs", String.valueOf(map.get("openId")));
        Agent agent = new Agent(String.valueOf(map.get("nickName")), String.valueOf(map.get("avatarUrl")),
                openId, employeeId, company, byEmployeeID.getFirm());
        agentMapper.insert(agent);
        return map;
    }

}
