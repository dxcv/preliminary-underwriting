package cn.algerfan.service.impl;

import cn.algerfan.domain.Announcement;
import cn.algerfan.domain.Result;
import cn.algerfan.enums.ResultCodeEnum;
import cn.algerfan.mapper.AnnouncementMapper;
import cn.algerfan.service.AnnouncementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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
@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Resource
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> select() {
        return announcementMapper.select();
    }

    @Override
    public Announcement findById(Integer announcementId) {
        return announcementMapper.selectByPrimaryKey(announcementId);
    }

    @Override
    public Result update(Integer announcementId, Announcement announcement) {
        if(announcementId == null || announcementId == 0 ||
                announcement.getType() == null || "".equals(announcement.getType()) ||
                announcement.getContent() == null || "".equals(announcement.getContent())) {
            return new Result(ResultCodeEnum.SAVEFAIL);
        }
        announcement.setAnnouncementId(announcementId);
        announcement.setDate(new Date());
        if(announcementMapper.selectByContent(announcement.getContent()) !=null) {
            return new Result(-1,"修改失败，该公告已存在");
        }
        if(announcementMapper.updateByPrimaryKey(announcement) == 0) {
            return new Result(ResultCodeEnum.UNUPDATE);
        }
        return new Result(ResultCodeEnum.UPDATE);
    }

    @Override
    public Map<String, Object> selectAnnouncement() {
        Map<String, Object> map = new HashMap<>(10);
        List<Announcement> select = announcementMapper.select();
        map.put("status",1);
        map.put("msg","查询成功");
        map.put("list",select);
        return map;
    }

}
