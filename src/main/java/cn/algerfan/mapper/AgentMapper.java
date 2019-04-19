package cn.algerfan.mapper;

import cn.algerfan.domain.Agent;
import org.apache.ibatis.annotations.Param;

public interface AgentMapper {
    int deleteByPrimaryKey(Integer agentId);

    void insert(Agent record);

    int insertSelective(Agent record);

    Agent selectByPrimaryKey(Integer agentId);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);

    Agent selectByOpenid(String password);

    Agent check(@Param("employeeId") String employeeId,@Param("company") String company);
}