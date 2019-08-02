package cn.algerfan.mapper;

import cn.algerfan.domain.Zip;

import java.util.List;

public interface ZipMapper {
    /**
     * 通过id删除
     * @param zipId
     * @return
     */
    int deleteByPrimaryKey(Integer zipId);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(Zip record);

    /**
     * 选择性插入
     * @param record
     * @return
     */
    int insertSelective(Zip record);

    /**
     * 根据id查询
     * @param zipId
     * @return
     */
    Zip selectByPrimaryKey(Integer zipId);

    /**
     * 选择性更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Zip record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Zip record);

    /**
     * 查询所有月份
     * @return
     */
    List<Zip> selectMonth();

    /**
     * 通过月份查询
     * @param month
     * @return
     */
    Zip selectByMonth(String month);
}