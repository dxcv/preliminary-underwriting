package cn.algerfan.controller.admin;

import cn.algerfan.base.BaseController;
import cn.algerfan.domain.Underwriting;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 *
 * </p>
 *
 * @author algerfan
 * @since 2019/4/18 09
 */
@Controller
@RequestMapping("/admin")
public class AdminUnderwritingController extends BaseController {

    @RequestMapping(value = "select", method = RequestMethod.GET)
    public ModelAndView select(String keyword, Model model, @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                               @RequestParam(name = "pageSize", defaultValue = "16") int pageSize) {
        PageInfo<Underwriting> select = underwritingService.select(keyword, pageNum, pageSize);
        model.addAttribute("list", select.getList());
        model.addAttribute("pages",select.getPages());
        model.addAttribute("pageNum",select.getPageNum());
        return new ModelAndView("");
    }
}
