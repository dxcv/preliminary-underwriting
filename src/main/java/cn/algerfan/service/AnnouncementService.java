package cn.algerfan.service;

import cn.algerfan.domain.Announcement;
import cn.algerfan.domain.Result;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  后台公告管理
 * </p>
 *
 * @author algerfan
 * @since 2019/4/22 20
 */
public interface AnnouncementService {
    /**
     * 后台查询
     * @return
     */
    List<Announcement> select();

    /**
     * 后台添加
     * @param announcement
     * @return
     */
    Result insert(Announcement announcement);

    /**
     * 后台删除
     * @param announcementId
     * @return
     */
    Result delete(Integer announcementId);

    /**
     * 根据id查询
     * @param announcementId
     * @return
     */
    Announcement findById(Integer announcementId);

    /**
     * 后台更新
     * @param announcementId
     * @param announcement
     * @return
     */
    Result update(Integer announcementId, Announcement announcement);

    /**
     * 小程序查询
     * @return
     */
    Map<String, Object> selectAnnouncement();

}
