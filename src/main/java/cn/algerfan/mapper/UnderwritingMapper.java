package cn.algerfan.mapper;

import cn.algerfan.domain.Underwriting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnderwritingMapper {
    int deleteByPrimaryKey(Integer underwritingId);

    int insert(Underwriting record);

    int insertSelective(Underwriting record);

    Underwriting selectByPrimaryKey(Integer underwritingId);

    void updateByPrimaryKeySelective(Underwriting record);

    void updateByPrimaryKey(Underwriting record);

    List<Underwriting> selectByAgentId(@Param(value="agentId") Integer agentId);

    List<Underwriting> select(@Param(value="keyword") String keyword);

    List<Underwriting> selectAll();

    Underwriting selectByAgentIdAndName(@Param("agentId") Integer agentId, @Param("name") String name);

}