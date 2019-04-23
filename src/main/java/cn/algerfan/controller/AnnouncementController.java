package cn.algerfan.controller;

import cn.algerfan.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 *  公告Controller
 * </p>
 *
 * @author algerfan
 * @since 2019/4/23 10
 */
@Controller
@RequestMapping("/announcement")
@Api(value = "公告——小程序部分", tags = "公告——小程序部分")
public class AnnouncementController extends BaseController {

    /**
     * 公告栏查询公告——小程序部分
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "公告栏查询公告——小程序部分", notes = "公告栏查询公告",
            httpMethod = "GET")
    public Map<String, Object> selectAnnouncement() {
        return announcementService.selectAnnouncement();
    }

}
