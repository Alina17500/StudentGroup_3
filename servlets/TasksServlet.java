package servlets;

import ru.vsu.cs.volobueva.DatabaseManager;
import ru.vsu.cs.volobueva.Group;
import ru.vsu.cs.volobueva.Student;
import ru.vsu.cs.volobueva.StudentGroups;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.List;

public class TasksServlet extends HttpServlet {
    private final StudentGroups studentGroups;
    private DatabaseManager databaseManager;
    private final Student student;

    public TasksServlet(StudentGroups studentGroups, DatabaseManager databaseManager, Student student) {
        this.studentGroups = studentGroups;
        this.student = student;
        databaseManager = new DatabaseManager(() -> DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb", "root", "delFin92!"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve all students and display them
        response.setContentType("text/html");
        response.getWriter().println("<h1>Students and him tasks</h1>");
        List<Group> groups = studentGroups.getAllGroups();

        for (Group group : groups) {
            List<Student> students = studentGroups.getStudentsByGroupId(group.getId());
            for (Student student : students) {
                response.getWriter().println("<p>ID: " + student.getId() + ", Name: " + student.getFullName()+ ", Gender: " + student.getGender() + ", Age: " + student.getAge() + ", Group: " + student.getGroupId() + "</p>");
            }
        }
    }

    protected void editTasks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>Assignment tasks</h1>");

        int id = Integer.parseInt(request.getParameter("id"));
        Student student = studentGroups.getStudentId(id);
        int number = Integer.parseInt(request.getParameter("number"));
        switch (number) {
            case 1:
                student.setTask1(true);
                break;
            case 2:
                student.setTask2(true);
                break;
            case 3:
                student.setTask3(true);
                break;
            }
        studentGroups.passedTask(id, student);
        response.sendRedirect("/students");
    }
}

