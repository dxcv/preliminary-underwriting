package cn.algerfan.mapper;

import cn.algerfan.domain.Agent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代理人
 * @author AlgerFan
 */
@Mapper
public interface AgentMapper {

    /**
     * 注册代理人
     * @param record
     */
    void insert(Agent record);

    /**
     * 根据openid查询
     * @param openid
     * @return
     */
    Agent selectByOpenid(@Param(value="openid") String openid);

    /**
     * 根据id删除
     * @param agentId
     * @return
     */
    int deleteByPrimaryKey(Integer agentId);

    /**
     * 选择性添加
     * @param record
     * @return
     */
    int insertSelective(Agent record);

    /**
     * 根据id查询
     * @param agentId
     * @return
     */
    Agent selectByPrimaryKey(Integer agentId);

    /**
     * 选择性更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Agent record);

    /**
     * 全部更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Agent record);

    /**
     * 根据昵称模糊查询
     * @param nickname
     * @return
     */
    List<Agent> select(@Param(value="nickname") String nickname);

    /**
     * 根据id批量查询
     * @param list
     * @return
     */
    List<Agent> selectByAgentIds(@Param(value="list") List<Integer> list);
}