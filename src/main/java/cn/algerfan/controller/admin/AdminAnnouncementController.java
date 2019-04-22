package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Announcement;
import cn.algerfan.domain.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 *  后台公告管理
 * </p>
 *
 * @author algerfan
 * @since 2019/4/22 20
 */
@Controller
@RequestMapping("/admin/announcement")
public class AdminAnnouncementController extends BaseController {

    @RequestMapping(value = "select",method = RequestMethod.GET)
    public ModelAndView select(Model model) {
        List<Announcement> announcements = announcementService.select();
        model.addAttribute("msg", "查询成功");
        model.addAttribute("announcements", announcements);
        log.info("查询成功："+announcements);
        return new ModelAndView("announcement/announcement");
    }

    /**
     * 跳转更新
     * @param announcementId
     * @param model
     */
    @RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
    public ModelAndView toUpdate(Integer announcementId, Model model) {
        if(announcementId == null || announcementId == 0) {
            model.addAttribute("msg", "查询失败");
            return new ModelAndView("redirect:/admin/announcement/select");
        }
        Announcement announcement = announcementService.toUpdate(announcementId);
        if(announcement==null) {
            model.addAttribute("msg", "查询失败");
            return new ModelAndView("redirect:/admin/announcement/select");
        }
        model.addAttribute("查询成功");
        log.info("查询成功："+announcement);
        return new ModelAndView("announcement/updateAnnouncement");
    }

    /**
     * 更新公告
     * @param announcementId
     * @param announcement
     * @return Result
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Result update(Integer announcementId, Announcement announcement) {
        return announcementService.update(announcementId,announcement);
    }

}
