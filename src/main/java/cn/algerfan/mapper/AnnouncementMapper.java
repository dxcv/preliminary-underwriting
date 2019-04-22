package cn.algerfan.mapper;

import cn.algerfan.domain.Announcement;

import java.util.List;

public interface AnnouncementMapper {
    int deleteByPrimaryKey(Integer announcementId);

    int insert(Announcement record);

    int insertSelective(Announcement record);

    Announcement selectByPrimaryKey(Integer announcementId);

    int updateByPrimaryKeySelective(Announcement record);

    int updateByPrimaryKey(Announcement record);

    List<Announcement> select();

    Announcement selectByContent(String content);
}