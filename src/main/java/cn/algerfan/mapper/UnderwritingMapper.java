package cn.algerfan.mapper;

import cn.algerfan.domain.Underwriting;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 核保人
 * @author AlgerFan
 */
public interface UnderwritingMapper {
    /**
     * 根据id删除
     * @param underwritingId
     * @return
     */
    int deleteByPrimaryKey(Integer underwritingId);

    /**
     * 添加核保人
     * @param record
     * @return
     */
    int insert(Underwriting record);

    /**
     * 选择添加
     * @param record
     * @return
     */
    int insertSelective(Underwriting record);

    /**
     * 根据id查询
     * @param underwritingId
     * @return
     */
    Underwriting selectByPrimaryKey(Integer underwritingId);

    /**
     * 选择更新
     * @param record
     */
    void updateByPrimaryKeySelective(Underwriting record);

    /**
     * 全部更新
     * @param record
     */
    void updateByPrimaryKey(Underwriting record);

    /**
     * 根据agentId查询
     * @param agentId
     * @return
     */
    List<Underwriting> selectByAgentId(@Param(value="agentId") Integer agentId);

    /**
     * 根据姓名模糊查询
     * @param keyword
     * @return
     */
    List<Underwriting> select(@Param(value="keyword") String keyword);

    /**
     * 查询全部
     * @return
     */
    List<Underwriting> selectAll();

    /**
     * 根据agentId、姓名查询
     * @param agentId
     * @param name
     * @return
     */
    Underwriting selectByAgentIdAndName(@Param("agentId") Integer agentId, @Param("name") String name);

}