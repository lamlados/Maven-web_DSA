package com.web.servlet;

import com.dao.UserDao;
import com.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);

//        Map<String, String[]> map = req.getParameterMap();
//        try {
//            BeanUtils.populate(loginUser, map);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        UserDao dao = new UserDao();
        User user = dao.login(loginUser);
        String checkCode = req.getParameter("checkCode");
        HttpSession session = req.getSession();
        String cSession = (String) session.getAttribute("cSession");
        session.removeAttribute("cSession");
        if (cSession != null && cSession.equalsIgnoreCase(checkCode)) {
            if (user == null) {
                req.setAttribute("login_error", "用户名或密码错误");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            } else {
                session.setAttribute("user", username);
                resp.sendRedirect(req.getContextPath() + "/success.jsp");
            }
        } else {
            req.setAttribute("cc_error", "验证码错误");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
