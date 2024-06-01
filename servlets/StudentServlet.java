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

public class StudentServlet extends HttpServlet {
    private final StudentGroups studentGroups;
    private DatabaseManager databaseManager;
    private final Student student;

    public StudentServlet(StudentGroups studentGroups, DatabaseManager databaseManager, Student student) {
        this.studentGroups = studentGroups;
        this.student = student;
        databaseManager = new DatabaseManager(() -> DriverManager.getConnection("jdbc:mysql://localhost:3306/studentsdb", "root", "delFin92!"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve all students and display them
        response.setContentType("text/html");
        response.getWriter().println("<h1>Students</h1>");
        List<Group> groups = studentGroups.getAllGroups();

        for (Group group : groups) {
            List<Student> students = studentGroups.getStudentsByGroupId(group.getId());
                for (Student student : students) {
                    response.getWriter().println("<p>ID: " + student.getId() + ", Name: " + student.getFullName()+ ", Gender: " + student.getGender() + ", Age: " + student.getAge() + ", Group: " + student.getGroupId() + "</p>");
                }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Create a new student
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        int age = Integer.parseInt(request.getParameter("age"));
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        boolean task1 = Boolean.parseBoolean(request.getParameter("Tas_1"));
        boolean task2 = Boolean.parseBoolean(request.getParameter("Tas_2"));
        boolean task3 = Boolean.parseBoolean(request.getParameter("Tas_3"));
        Student newStudent = new Student(id, name, gender, age, groupId, task1, task2, task3);
        studentGroups.addStudent(newStudent);
        response.sendRedirect("/students");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Delete a student
        int id = Integer.parseInt(request.getParameter("id"));
        studentGroups.deleteStudent(id);
        response.sendRedirect("/students");
    }
}
