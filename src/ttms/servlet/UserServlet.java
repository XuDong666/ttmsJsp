package ttms.servlet;

import ttms.dao.EmployeeDao;
import ttms.dao.UserDao;
import ttms.idao.FactoryDao;
import ttms.idao.User;
import ttms.model.EmployeeModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import ttms.idao.FactoryDao;
import ttms.dao.UserDao;
import ttms.model.UserModel;

/**
 * Created by xudong on 17-12-21.
 */
@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch (method) {
            case "searchByPage":
                searchBypage(request, response);
            case "searchByNo":
                searchByNo(request, response);
            case "delete":
                delete(request, response);
            case "add":
                add(request, response);
            case "searchByno":
                searchByno(request,response);


        }
    }

    private void searchBypage(HttpServletRequest request, HttpServletResponse response) {
        int currentPage = 1; // 当前页默认为第一页
        String strpage = request.getParameter("currentPage"); // 获取前台传入当前页
        if (strpage != null && !strpage.equals("")) {
            currentPage = Integer.parseInt(strpage) < 1 ? 1 : Integer.parseInt(strpage); // 将字符串转换成整型
        }
        String emp_name = request.getParameter("emp_name");
        UserDao dao = (UserDao) FactoryDao.createUserDao();
        // 从UserDAO中获取所有用户信息
        ArrayList<UserModel> list = dao.findUserByPage(currentPage);
        // 从UserDAO中获取总记录数
        int allCount = dao.getAllCount();
        // 从UserDAO中获取总页数
        int allPageCount = dao.getAllPageCount();
        // 从UserDAO中获取当前页
        currentPage = dao.getCurrentPage();

        // 存入request中
        request.setAttribute("allUser", list);
        request.setAttribute("allCount", allCount);
        request.setAttribute("allPageCount", allPageCount);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("search_emp_name", emp_name);

        try {
            request.setAttribute("isEmployee", "0");
            request.setAttribute("isUser", "1");
            request.getRequestDispatcher(request.getContextPath() + "/admin/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchByNo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emp_name = request.getParameter("emp_no_u");
        ArrayList<UserModel> list = null;
        UserDao dao = (UserDao) FactoryDao.createUserDao();
        list = dao.findUserByNo(emp_name);
        // 从UserDAO中获取总记录数
        int allCount = dao.getAllCount();
        // 从UserDAO中获取总页数
        int allPageCount = dao.getAllPageCount();
        // 从UserDAO中获取当前页
        int currentPage = 1;
        System.out.print("\n" + currentPage + "\n" + allCount + "\n" + allPageCount);
        request.setAttribute("allUser", list);
        request.setAttribute("allCount", allCount);
        request.setAttribute("allPageCount", allPageCount);
        request.setAttribute("currentPage", currentPage);

        request.setAttribute("isUser", "1");
        request.setAttribute("isEmployee", "0");
        request.getRequestDispatcher(request.getContextPath() + "/admin/index.jsp").forward(request, response);

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        String emp_no = request.getParameter("emp_no");

        UserDao dao = (UserDao) FactoryDao.createUserDao();
        result = dao.delete(emp_no);
        if (result)
            request.setAttribute("result", "删除成功!");
        else
            request.setAttribute("result", "删除失败!");
        // 不分页时删除调用全查
//            search(request, response);
        // 分页时删除调用分页全查:使用分页index1.jsp时，把这里注释打开
        searchBypage(request, response);

    }

    private void add(HttpServletRequest request, HttpServletResponse response) {

        String emp_name = request.getParameter("user_name_add");
        String emp_pwd = request.getParameter("user_pwd_add");
        String emp_type = request.getParameter("type");
        System.out.println(emp_name);
        EmployeeDao Edao = (EmployeeDao) FactoryDao.createEmployeeDao();
        EmployeeModel employee = Edao.findEmployeeByName1(emp_name);
        String emp_no = employee.getEmp_no();
        UserModel user = new UserModel();
        user.setEmp_no(emp_no);
        user.setEmp_pass(emp_pwd);
        user.setType(Integer.parseInt(emp_type));

        UserDao dao = (UserDao) FactoryDao.createUserDao();
        boolean result = dao.insert(user);
        try {
            if (result)
                request.setAttribute("result", "添加成功!");

            else
                request.setAttribute("result", "添加失败!");
            request.getRequestDispatcher(request.getContextPath() + "/admin/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchByno(HttpServletRequest request, HttpServletResponse response) {
        String emp_no = request.getParameter("emp_no");
        UserDao dao = (UserDao) FactoryDao.createUserDao();
        ArrayList<UserModel> user=dao.findUserByNo(emp_no);
        request.setAttribute("user", user.get(0));

        request.setAttribute("updateUserStatus", "1");
        try {
            request.getRequestDispatcher(request.getContextPath() + "/admin/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
