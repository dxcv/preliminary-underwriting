package cn.algerfan.mapper;

import cn.algerfan.domain.User;

import java.util.List;

/**
 * 管理员
 * @author AlgerFan
 */
public interface UserMapper {
    /**
     * 根据id删除
     * @param userId
     * @return
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * 添加
     * @param record
     * @return
     */
    int insert(User record);

    /**
     * 选择添加
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    User selectByPrimaryKey(Integer userId);

    /**
     * 选择更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 全部更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);

    /**
     * 查询全部
     * @return
     */
    List<User> getAllUser();
}