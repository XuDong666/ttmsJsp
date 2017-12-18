package ttms.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ttms.idao.FactoryDao;
import ttms.model.UserModel;
import ttms.idao.FactoryDao;
import ttms.dao.UserDao;

/**
 * Created by xudong on 17-12-14.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        login(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDao userDao = (UserDao) FactoryDao.createUserDao();
        UserModel user = userDao.getUserByNameAndPwd(username, password);

        if (user == null) {
            request.getSession().setAttribute("msg", "pwd_error");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            request.getSession().setAttribute("user", user);
            if (user.getType() == 1) {
                response.sendRedirect(request.getContextPath() + "/admin/index.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/common/index.jsp");
            }
        }
    }

}
