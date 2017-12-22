package ttms.servlet;
/**
 * Created by xudong on 17-12-19.
 */

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import ttms.dao.EmployeeDao;
import ttms.idao.FactoryDao;
import ttms.model.UserModel;
import ttms.model.EmployeeModel;

@WebServlet(name = "EmployeeServlet", urlPatterns = "/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch (method) {
            case "getEmployee":
                getEmployee(request, response);
            case "getAllEmployee":
                getAllEmployee(request, response);
            case "searchByPage":
                searchBypage(request, response);
            case "delete":
                delete(request, response);
            case "searvhById":
                searchById(request,response);
            case "update":
                update(request,response);
            case "searchByName":
                searchByName(request,response);
            case "add":
                add(request,response);
        }


    }

    private void getEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        String emp_no = user.getEmp_no();
        EmployeeDao employeeDao = (EmployeeDao) FactoryDao.createEmployeeDao();
        EmployeeModel employee = employeeDao.getEmployeeByEmp_no(emp_no);
        if (employee == null) {
            request.getSession().setAttribute("msg", "emp_error");
        } else {
            request.getSession().setAttribute("employee", employee);
            if (user.getType() == 1) {
                request.getRequestDispatcher(request.getContextPath() + "/admin/index.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher(request.getContextPath() + "/common/index.jsp").forward(request, response);
            }
        }

    }

    private ArrayList<EmployeeModel> getAllEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeDao employeeDao = (EmployeeDao) FactoryDao.createEmployeeDao();
        ArrayList<EmployeeModel> list = employeeDao.getAllEmploye();
        request.setAttribute("allEmployee", list);
        request.setAttribute("isemployee", 1);
        request.getRequestDispatcher(request.getContextPath() + "/admin/index.jsp").forward(request, response);
        return list;
    }

    private void searchBypage(HttpServletRequest request, HttpServletResponse response) {
        int currentPage = 1; // 当前页默认为第一页
        String strpage = request.getParameter("currentPage"); // 获取前台传入当前页
        if (strpage != null && !strpage.equals("")) {
            currentPage = Integer.parseInt(strpage) < 1 ? 1 : Integer.parseInt(strpage); // 将字符串转换成整型
        }
        String emp_name = request.getParameter("emp_name");
        EmployeeDao dao = (EmployeeDao) FactoryDao.createEmployeeDao();
        // 从UserDAO中获取所有用户信息
        ArrayList<EmployeeModel> list = dao.findEmployeeByPage(currentPage, emp_name);
        // 从UserDAO中获取总记录数
        int allCount = dao.getAllCount();
        // 从UserDAO中获取总页数
        int allPageCount = dao.getAllPageCount();
        // 从UserDAO中获取当前页
        currentPage = dao.getCurrentPage();

        // 存入request中
        request.setAttribute("allEmployee", list);
        request.setAttribute("allCount", allCount);
        request.setAttribute("allPageCount", allPageCount);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("search_emp_name", emp_name);

        try {
            request.setAttribute("isUser", "0");
            request.setAttribute("isEmployee", "1");
            request.getRequestDispatcher(request.getContextPath() + "/admin/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void delete(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        int emp_id = Integer.parseInt(request.getParameter("emp_id"));
        if (emp_id > 0) {
            EmployeeDao dao = (EmployeeDao) FactoryDao.createEmployeeDao();
            result = dao.delete(emp_id);
            if (result)
                request.setAttribute("result", "删除成功!");
            else
                request.setAttribute("result", "删除失败!");
            // 不分页时删除调用全查
//            search(request, response);
            // 分页时删除调用分页全查:使用分页index1.jsp时，把这里注释打开
            searchBypage(request, response);
        }
    }
    private void searchById(HttpServletRequest request, HttpServletResponse response)
    {
        int emp_id = Integer.parseInt(request.getParameter("emp_id"));
        if (emp_id > 0)
        {
            EmployeeDao dao = (EmployeeDao) FactoryDao.createEmployeeDao();
            EmployeeModel emp = dao.findEmployeeById(emp_id);
            request.setAttribute("employee", emp);

            request.setAttribute("updateEmployeeStatus","1");
            try
            {
                request.getRequestDispatcher(request.getContextPath()+"/admin/index.jsp").forward(request, response);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private void update(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        int emp_id = Integer.parseInt(request.getParameter("emp_id"));
        String emp_no = request.getParameter("emp_no");
        String emp_name = request.getParameter("emp_name");
        String emp_tel_num = request.getParameter("emp_tel_num");
        String emp_addr = request.getParameter("emp_addr");
        String emp_email = request.getParameter("emp_email");
        EmployeeModel employee = new EmployeeModel();
        employee.setEmp_id(emp_id);
        employee.setEmp_no(emp_no);
        employee.setEmp_name(emp_name);
        employee.setEmp_tel_num(emp_tel_num);
        employee.setEmp_addr(emp_addr);
        employee.setEmp_email(emp_email);
        EmployeeDao dao = (EmployeeDao) FactoryDao.createEmployeeDao();
        boolean result = dao.update(employee);
        try {
            if (result)
                request.setAttribute("result", "更新成功!");
            else
                request.setAttribute("result", "更新失败!");
            request.setAttribute("employee", employee);
            request.setAttribute("updateEmployeeStatus","");
            request.getRequestDispatcher(request.getContextPath()+"/admin/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchByName(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String emp_name=request.getParameter("emp_name_s");
        ArrayList<EmployeeModel> list=null;
        EmployeeDao dao = (EmployeeDao)FactoryDao.createEmployeeDao();
        list=dao.findEmployeeByName(emp_name);
        // 从UserDAO中获取总记录数
        int allCount = dao.getAllCount();
        // 从UserDAO中获取总页数
        int allPageCount = dao.getAllPageCount();
        // 从UserDAO中获取当前页
        int currentPage = 1;
        request.setAttribute("allEmployee",list);
        request.setAttribute("allCount", allCount);
        request.setAttribute("allPageCount", allPageCount);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("search_emp_name", emp_name);
        request.setAttribute("isEmployee","1");
        request.getRequestDispatcher(request.getContextPath()+"/admin/index.jsp").forward(request,response);

    }

    private void add(HttpServletRequest request, HttpServletResponse response)
    {
        String emp_no = request.getParameter("emp_no_add");
        String emp_name = request.getParameter("emp_name_add");
        String emp_tel_num = request.getParameter("emp_tel_num_add");
        String emp_addr = request.getParameter("emp_addr_add");
        String emp_email = request.getParameter("emp_email_add");
        EmployeeModel employee = new EmployeeModel();
        employee.setEmp_no(emp_no);
        employee.setEmp_name(emp_name);
        employee.setEmp_tel_num(emp_tel_num);
        employee.setEmp_addr(emp_addr);
        employee.setEmp_email(emp_email);
        EmployeeDao dao = (EmployeeDao) FactoryDao.createEmployeeDao();
        boolean result = dao.insert(employee);
        try
        {
            if (result)
                request.setAttribute("result", "添加成功!");

            else
                request.setAttribute("result", "添加失败!");
            request.getRequestDispatcher(request.getContextPath()+"/admin/index.jsp").forward(request, response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
