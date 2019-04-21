package cn.algerfan.mapper;

import cn.algerfan.domain.Underwriting;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnderwritingMapper {
    int deleteByPrimaryKey(Integer underwritingId);

    int insert(Underwriting record);

    int insertSelective(Underwriting record);

    Underwriting selectByPrimaryKey(Integer underwritingId);

    int updateByPrimaryKeySelective(Underwriting record);

    int updateByPrimaryKey(Underwriting record);

    List<Underwriting> selectByAgentId(Integer agentId);

    List<Underwriting> select(String keyword);

    Underwriting selectByAgentIdAndName(@Param("agentId") Integer agentId, @Param("name") String name);
}