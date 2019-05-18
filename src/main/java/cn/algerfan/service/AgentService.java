package cn.algerfan.service;

import cn.algerfan.domain.Agent;
import cn.algerfan.domain.Result;
import com.github.pagehelper.PageInfo;

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

    /**
     * 注册或登录代理人
     * @param employeeId
     * @param company
     * @param encryptedData
     * @param iv
     * @param code
     * @return
     */
    Map<String, Object> register(String employeeId, String company, String encryptedData, String iv, String code);

    /**
     * 后台通过id查询
     * @param agentId
     * @return
     */
    Agent selectById(Integer agentId);

    /**
     * 后台通过id删除
     * @param agentId
     * @return
     */
    Result delete(Integer agentId);

    /**
     * 后台更新
     * @param agentId
     * @param agent
     * @return
     */
    Result update(Integer agentId, Agent agent);

    /**
     * 后台查询
     * @param nickname
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Agent> select(String nickname, int pageNum, int pageSize);
}
