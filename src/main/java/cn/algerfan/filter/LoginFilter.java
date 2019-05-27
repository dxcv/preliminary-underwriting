package cn.algerfan.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 过滤器
 * @author AlgerFan
 */
public class LoginFilter implements Filter {
    /**
     *  日志
     */
    protected Logger log;

    public LoginFilter() {
        log = Logger.getLogger(this.getClass());
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String returnUrl = "/administrator/toLogin";
            if (session != null && session.getAttribute("user") != null) {
                filterChain.doFilter(request, response);
            } else {
                servletRequest.setCharacterEncoding("UTF-8");
                // 转码
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("<script language=\"javascript\">" + "alert(\"没有访问权限，请重新登录！\");"
                        + "if(window.opener==null){window.top.location.href=\"" + returnUrl
                        + "\";}else{window.opener.top.location.href=\"" + returnUrl + "\";window.close();}</script>");
                response.getWriter().close();
        }
    }

    @Override
    public void destroy() {
        log.info("过滤器销毁");
    }

}
