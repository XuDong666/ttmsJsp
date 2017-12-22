package ttms.filter;

import ttms.idao.User;
import ttms.model.UserModel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "Privilege", urlPatterns = "/admin/*")
public class Privilege implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 1、获得当前请求访问资源路径
        String path = request.getRequestURI().substring(request.getContextPath().length());
        System.out.println("拦截了" + request.getRequestURI());
        UserModel user = (UserModel) request.getSession().getAttribute("userlogin");
        if (user == null) {
            System.out.println("没登录");
            request.setAttribute("msg", "您还没有登陆！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        } else {
//            if (path.startsWith("/user")) { // user 身份
////                if (user.getRole() == 0 || user.getRole() == 1) {
////                    System.out.println("登录用户界面");
////                    chain.doFilter(request, response);// 权限满足
////                    return;
////                } else {
////                    throw new RuntimeException("对不起您的权限不足！");
////
////                }
////            }
            if (path.startsWith("/admin")) { // 管理员身份
                if (user.getType()== 1) {
                    System.out.println(">>>>权限满足<<<<");
                    chain.doFilter(request, response);// 权限满足
                    return;
                } else {
                    throw new RuntimeException("对不起您的权限不足！");
                }
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}