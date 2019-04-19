package cn.algerfan.service.impl;

import cn.algerfan.base.BaseDao;
import cn.algerfan.mapper.AgentMapper;
import cn.algerfan.domain.Agent;
import cn.algerfan.service.AgentService;
import cn.algerfan.util.AesUtil;
import cn.algerfan.util.openid.Openid;
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

    @Override
    public Map<String, Object> register(String employeeId, String company, String encryptedData, String iv, String code) {
        log.info("employeeId："+employeeId+"    company："+company);
        Map<String, Object> map = new HashMap<>();
        if (employeeId == null || employeeId.length() == 0) {
            map.put("status", 0);
            map.put("msg", "employeeId 不能为空");
            return map;
        }
        if (company == null || company.length() == 0) {
            map.put("status", 0);
            map.put("msg", "company 不能为空");
            return map;
        }
        Agent check = agentMapper.check(employeeId,company);
        if(check!=null) {
            map.put("status", 0);
            map.put("msg","注册失败，该代理人已存在");
            return map;
        }
        AesUtil aesUtil = new AesUtil();
        Map<String, Object> register = new Openid().register(encryptedData, iv, code);
        String openId = aesUtil.AESDncode("lovewlgzs", String.valueOf(register.get("openId")));
        Agent agent = new Agent();
        agent.setOpenid(openId);
        agent.setEmployeeId(employeeId);
        agent.setCompany(company);
        agentMapper.insert(agent);
        return new Openid().register(encryptedData, iv, code);
    }
}
