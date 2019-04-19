package cn.algerfan.base;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.algerfan.service.AgentService;
import cn.algerfan.service.CompanyService;
import cn.algerfan.service.UnderwritingService;
import cn.algerfan.service.UserService;
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
    protected HttpServletRequest request;// 获得request
    @Resource
    protected HttpServletResponse response;//获得response
    @Resource
    protected HttpSession session; // 获得session

}
