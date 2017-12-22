package ttms.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ttms.dao.EmployeeDao;
import ttms.idao.FactoryDao;
import ttms.model.EmployeeModel;
import ttms.model.UserModel;
import ttms.idao.FactoryDao;
import ttms.dao.UserDao;
import java.util.ArrayList;

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
        EmployeeDao dao= (EmployeeDao) FactoryDao.createEmployeeDao();
        ArrayList<EmployeeModel> list=dao.getAllEmploye();

        if (user == null) {
            request.getSession().setAttribute("msg", "pwd_error");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            request.getSession().setAttribute("allEmployee",list);
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/EmployeeServlet?method=getEmployee");
        }
    }

}
