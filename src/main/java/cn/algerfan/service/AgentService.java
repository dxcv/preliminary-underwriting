package cn.algerfan.service;

import cn.algerfan.domain.Agent;

import java.util.Map;

/**
 * <p>
 *  代理人服务类
 * </p>
 *
 * @author algerfan
 * @since 2019-04-13
 */
public interface AgentService {

    Map<String, Object> register(String employeeId, String company, String encryptedData, String iv, String code);

    Agent selectById(Integer agentId);
}
