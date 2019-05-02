package servlet;

import dao.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", value = "/registration")
public class RegistrationServlet extends HttpServlet {

    private static final UserDao userDao = new UserDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User newUser = new User(login, password);
        if (userDao.isUserExistsInDatabase(newUser)) {
            request.setAttribute("userExists", true);
            request.getRequestDispatcher("registration.jsp").forward(request, response);
        } else {
            newUser = new User(login, password);
            userDao.addUserToDatabase(newUser);
            request.getSession().setAttribute("user", newUser);
            request.getRequestDispatcher("show_list.jsp").forward(request, response);
        }
    }
}
