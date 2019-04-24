package cn.algerfan.base;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.algerfan.service.*;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController implements Serializable {

    /**
     * 在父类中实现序列化，简化基本controller代码
     */
    private static final long serialVersionUID = 1L;

    /**
     *  日志
     */
    protected Logger log;
    public BaseController() {
        log = Logger.getLogger(this.getClass());
    }

    /**
     * 服务接口
     */
    @Resource
    protected UserService userService;
    @Resource
    protected AgentService agentService;
    @Resource
    protected UnderwritingService underwritingService;
    @Resource
    protected CompanyService companyService;
    @Resource
    protected PropertiesService propertiesService;
    @Resource
    protected AnnouncementService announcementService;
    @Resource
    protected HttpServletRequest request;// 获得request
    @Resource
    protected HttpServletResponse response;//获得response
    @Resource
    protected HttpSession session; // 获得session

}
