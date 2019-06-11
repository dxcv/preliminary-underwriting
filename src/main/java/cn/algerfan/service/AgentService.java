package cn.algerfan.service;

import cn.algerfan.domain.Agent;
import cn.algerfan.domain.Result;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    /**
     * 核保人员可通过统计功能栏，
     * 输入时间及选择维度（预核保提交/预核保结论回复）后，下载录入时间段的所需代理人统计明细。
     * @param keyword
     * @param response
     * @return
     */
    void statistical(String keyword, HttpServletResponse response) throws IOException;
}
