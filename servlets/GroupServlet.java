package servlets;

import ru.vsu.cs.volobueva.DatabaseManager;
import ru.vsu.cs.volobueva.Group;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;

public class GroupServlet extends HttpServlet {
    private DatabaseManager databaseManager;

    public GroupServlet(DatabaseManager databaseManager) {
        databaseManager = new DatabaseManager(() -> DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb", "root", "delFin92!"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve all groups and display them
        response.setContentType("text/html");
        response.getWriter().println("<h1>Groups</h1>");
        for (Group group : databaseManager.getAllGroups()) {
            response.getWriter().println("<p>ID: " + group.getId() + ", Name: " + group.getName() + ", Number: " + group.getNumber() + "</p>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Create a new group
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int number = Integer.parseInt(request.getParameter("number"));
        Group newGroup = new Group(id, name, number);
        databaseManager.addGroup(newGroup);
        response.sendRedirect("/groups");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Delete a group
        int id = Integer.parseInt(request.getParameter("id"));
        databaseManager.deleteGroup(id);
        response.sendRedirect("/groups");
    }
}
