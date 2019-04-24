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
import cn.algerfan.util.openid.Openid;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
        List<Company> byEmployeeID = companyMapper.findByEmployeeID(employeeId.substring(0, 4));
        if(byEmployeeID.size()==0) {
            map.put("status",0);
            map.put("msg","对不起，您所录入的工号非长城人寿北京分公司工号，无法进行注册");
            return map;
        }
        if (company == null || company.length() == 0) {
            map.put("status", 0);
            map.put("msg", "company 不能为空");
            return map;
        }

        AesUtil aesUtil = new AesUtil();
        //登录凭证不能为空
        if (code == null || encryptedData == null || iv ==null || code.length() == 0 || encryptedData.equals("") || iv.equals("")) {
            map.put("status", 0);
            map.put("msg", "code、encryptedData、iv 不能为空");
            return map;
        }
        Map<String, Object> map1 = Openid.session_key(code);

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = Aes.decrypt(encryptedData, String.valueOf(map1.get("session_key")), iv);
            if (null != result && result.length() > 0) {
                log.info("解密成功");
                JSONObject userInfoJSON = JSONObject.fromObject(result);
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
                log.info("userInfo: "+userInfo);
                Agent check = agentMapper.check(aesUtil.AESEncode("lovewlgzs", String.valueOf(map1.get("openid"))));
                log.info("check: "+check);
                if(check!=null) {
                    if(!check.getEmployeeId().equals(employeeId) || !check.getCompany().equals(company)) {
                        map.put("status", 0);
                        map.put("msg","登录失败，工号或者公司不正确");
                        return map;
                    } else {
                        map.put("status", 1);
                        map.put("msg","该代理人已注册，直接登录");
                        return map;
                    }
                } else {
                    String openId = aesUtil.AESEncode("lovewlgzs", String.valueOf(userInfoJSON.get("openId")));
                    Agent agent = new Agent(String.valueOf(userInfoJSON.get("nickName")), String.valueOf(userInfoJSON.get("avatarUrl")),
                            openId, employeeId, company, byEmployeeID.get(0).getFirm());
                    agentMapper.insert(agent);
                    map.put("status", 1);
                    map.put("msg", "注册成功");
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
    }

}
