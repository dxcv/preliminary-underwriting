package cn.algerfan.service;

import cn.algerfan.domain.Announcement;
import cn.algerfan.domain.Result;

import java.util.List;

/**
 * <p>
 *  后台公告管理
 * </p>
 *
 * @author algerfan
 * @since 2019/4/22 20
 */
public interface AnnouncementService {
    List<Announcement> select();

    Announcement toUpdate(Integer announcementId);

    Result update(Integer announcementId, Announcement announcement);
}
