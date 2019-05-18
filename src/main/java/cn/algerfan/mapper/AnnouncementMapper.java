package cn.algerfan.mapper;

import cn.algerfan.domain.Announcement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公告
 * @author AlgerFan
 */
public interface AnnouncementMapper {
    /**
     * 根据id删除
     * @param announcementId
     * @return
     */
    int deleteByPrimaryKey(Integer announcementId);

    /**
     * 添加
     * @param record
     * @return
     */
    int insert(Announcement record);

    /**
     * 选择添加
     * @param record
     * @return
     */
    int insertSelective(Announcement record);

    /**
     * 通过id查询
     * @param announcementId
     * @return
     */
    Announcement selectByPrimaryKey(Integer announcementId);

    /**
     * 选择更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Announcement record);

    /**
     * 全部更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Announcement record);

    /**
     * 查询
     * @return
     */
    List<Announcement> select();

    /**
     * 根据内容查询
     * @param content
     * @return
     */
    Announcement selectByContent(@Param(value="content") String content);
}