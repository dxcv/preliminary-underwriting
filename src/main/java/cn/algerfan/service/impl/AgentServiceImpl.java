package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.domain.Company;
import cn.algerfan.domain.Result;
import cn.algerfan.enums.ResultCodeEnum;
import cn.algerfan.mapper.AgentMapper;
import cn.algerfan.domain.Agent;
import cn.algerfan.mapper.CompanyMapper;
import cn.algerfan.service.AgentService;
import cn.algerfan.util.AesUtil;
import cn.algerfan.util.openid.Aes;
import cn.algerfan.util.openid.Openid;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        Map<String, Object> map = new HashMap<>(10);
        if (employeeId == null || employeeId.length() == 0 || company==null || "".equals(company)) {
            map.put("status", 0);
            map.put("msg", "参数不完整");
            return map;
        }
        if(employeeId.length()!=10) {
            map.put("status", 0);
            map.put("msg", "对不起，您所录入的工号非长城人寿北京分公司工号，无法进行注册");
            return map;
        }
        List<Company> byEmployeeID = companyMapper.findByEmployeeID(employeeId.substring(0, 4));
        if(byEmployeeID.size()==0) {
            map.put("status",0);
            map.put("msg","对不起，您所录入的工号非长城人寿北京分公司工号，无法进行注册");
            return map;
        }

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
                Map<String, Object> userInfo = new HashMap<>(10);
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
                log.info("userInfo: "+userInfo);
                Agent check = agentMapper.selectByOpenid(AesUtil.aesEncrypt(String.valueOf(map1.get("openid")), "lovewlgzs5201314"));
                log.info("check: "+check);
                if(check!=null) {
                    if(!check.getEmployeeId().equals(employeeId) || !check.getCompany().equals(company)) {
                        map.put("status", 0);
                        map.put("msg","登录失败，公司或工号不正确");
                        return map;
                    } else {
                        map.put("status", 1);
                        map.put("msg","您已注册，直接登录");
                        return map;
                    }
                } else {
                    Agent agent1 = agentMapper.checkEmployeeID(employeeId);
                    if(agent1!=null) {
                        map.put("status",0);
                        map.put("msg","对不起，您所录入的工号已经使用，无法进行注册");
                        return map;
                    }
                    String openId = AesUtil.aesEncrypt(String.valueOf(userInfoJSON.get("openId")), "lovewlgzs5201314");
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

    @Override
    public Agent selectById(Integer agentId) {
        return agentMapper.selectByPrimaryKey(agentId);
    }

    @Override
    public Result delete(Integer agentId) {
        if(agentId == null || agentId == 0) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        if(agentMapper.deleteByPrimaryKey(agentId) == 0) {
            return new Result(ResultCodeEnum.UNDELETE);
        }
        return new Result(ResultCodeEnum.DELETE);
    }

    @Override
    public Result update(Integer agentId, Agent agent) {
        if(agentId == null || agentId == 0 || agent.getEmployeeId() ==null || "".equals(agent.getEmployeeId()) ||
                agent.getFirm() == null || "".equals(agent.getFirm())) {
            return new Result(ResultCodeEnum.SAVEFAIL);
        }
        Company company = companyMapper.selectByFirm(agent.getFirm());
        if(company == null) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        agent.setCompany(company.getCompany());
        agent.setAgentId(agentId);
        if(agentMapper.updateByPrimaryKeySelective(agent) == 0) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        return new Result(ResultCodeEnum.UPDATE);
    }

    @Override
    public PageInfo<Agent> select(String nickname, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(agentMapper.select(nickname));
    }

}
