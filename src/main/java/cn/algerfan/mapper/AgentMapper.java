package cn.algerfan.mapper;

import cn.algerfan.domain.Agent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AgentMapper {

    void insert(Agent record);

    Agent check(@Param("employeeId") String employeeId, @Param("company") String company);

    int deleteByPrimaryKey(Integer agentId);

    int insertSelective(Agent record);

    Agent selectByPrimaryKey(Integer agentId);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);

    Agent selectByOpenid(String password);

}