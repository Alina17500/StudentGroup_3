package ru.vsu.cs.volobueva;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private final Scanner scanner;
    private final StudentGroups studentGroups;

    public ConsoleApp() {
        this.scanner = new Scanner(System.in);
        this.studentGroups = new StudentGroups();
    }

    public void start() {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить группу");
            System.out.println("2. Удалить группу");
            System.out.println("3. Редактировать группу");
            System.out.println("4. Добавить студента");
            System.out.println("5. Удалить студента");
            System.out.println("6. Редактировать информацию о студенте");
            System.out.println("7. Принять задачу у студента");
            System.out.println("8. Вывести информацию по группам");
            System.out.println("0. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addGroup();
                    break;
                case 2:
                    removeGroup();
                    break;
                case 3:
                    editGroup();
                    break;
                case 4:
                    addStudent();
                    break;
                case 5:
                    removeStudent();
                    break;
                case 6:
                    editStudent();
                    break;
                case 7:
                    passedTask();
                    break;
                case 8:
                    printGroupInfo();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private void addGroup() {
        System.out.println("Введите ID группы:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите название группы:");
        String name = scanner.nextLine();
        System.out.println("Введите номер группы:");
        int number = scanner.nextInt();
        scanner.nextLine();

        Group group = new Group(id, name, number);
        studentGroups.addGroup(group);
        System.out.println("Группа успешно добавлена.");
    }

    private void removeGroup() {
        System.out.println("Введите ID группы для удаления:");
        int id = scanner.nextInt();
        scanner.nextLine();

        studentGroups.deleteGroup(id);
        System.out.println("Группа успешно удалена.");
    }

    private void editGroup() {
        System.out.println("Введите ID группы для редактирования:");
        int id = scanner.nextInt();

        Group group = studentGroups.getGroupById(id);
        if (group != null) {
            System.out.println("Введите новое название группы:");
            String newName = scanner.next();
            group.setName(newName);
            System.out.println("Введите новый номер группы:");
            int newNumber = scanner.nextInt();
            group.setNumber(newNumber);


            studentGroups.editGroup(id, group);
            System.out.println("Информация о группе успешно обновлена.");
        } else {
            System.out.println("Группа с указанным ID не найдена.");
        }
    }

    private void addStudent() {
        System.out.println("Введите ID студента:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите полное имя студента:");
        String fullName = scanner.nextLine();
        System.out.println("Введите пол студента:");
        String gender = scanner.nextLine();
        System.out.println("Введите возраст студента:");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Введите ID группы, к которой принадлежит студент:");
        int groupId = scanner.nextInt();
        scanner.nextLine();

        Student student = new Student(id, fullName, gender, age, groupId, false, false, false);
        studentGroups.addStudent(student);
        System.out.println("Студент успешно добавлен.");
    }

    private void removeStudent() {
        System.out.println("Введите ID студента для удаления:");
        int id = scanner.nextInt();
        scanner.nextLine();

        studentGroups.deleteStudent(id);
        System.out.println("Студент успешно удален.");
    }

    private void editStudent() {
        System.out.println("Введите ID студента для редактирования:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Student student = studentGroups.getStudentId(id);
        if (student != null) {
            System.out.println("Введите новое полное имя студентаа:");
            String name = scanner.nextLine();
            student.setFullName(name);
            System.out.println("Введите новый пол студента:");
            String newGender = scanner.next();
            student.setGender(newGender);
            System.out.println("Введите новый возраст студента:");
            int newAge = scanner.nextInt();
            student.setAge(newAge);
            System.out.println("Введите новый ID группы, к которой принадлежит студент:");
            int newGroupId = scanner.nextInt();
            student.setGroupId(newGroupId);

            studentGroups.editStudent(id, student);
            System.out.println("Информация о студенте успешно обновлена.");
        } else {
            System.out.println("Студент с указанным ID не найден.");
        }
    }

    private void passedTask() {
        System.out.println("Введите ID студента:");
        int id = scanner.nextInt();
        scanner.nextLine();

        Student student = studentGroups.getStudentId(id);
        if (student != null) {
            System.out.println("Введите номер задачи студента:");
            int number = scanner.nextInt();
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
                default:
                    System.out.println("Некорректный выбор. Попробуйте снова.");
            }
            studentGroups.passedTask(id, student);
            System.out.println("Задача зачтена.");
            System.out.println("таск 1"+student.getTask1());
            System.out.println("таск 2"+student.getTask2());
            System.out.println("таск 3"+student.getTask3());
        } else {
            System.out.println("Студент с указанным ID не найден.");
        }
    }

    private void printGroupInfo() {
        List<Group> groups = studentGroups.getAllGroups();
        if (groups.isEmpty()) {
            System.out.println("На данный момент нет ни одной группы.");
        } else {
            System.out.println("Информация по группам:");
            for (Group group : groups) {
                System.out.println("ID: " + group.getId());
                System.out.println("Название: " + group.getName());
                System.out.println("Номер: " + group.getNumber());
                System.out.println("Студенты в группе:");

                List<Student> students = studentGroups.getStudentsByGroupId(group.getId());
                if (students.isEmpty()) {
                    System.out.println("   Нет студентов в этой группе.");
                } else {
                    for (Student student : students) {
                        System.out.println("   ID студента: " + student.getId());
                        System.out.println("   Полное имя: " + student.getFullName());
                        System.out.println("   Пол: " + student.getGender());
                        System.out.println("   Возраст: " + student.getAge());
                        System.out.println("   Задача 1: " + student.getTask1());
                        System.out.println("   Задача 2: " + student.getTask2());
                        System.out.println("   Задача 3: " + student.getTask3());
                        System.out.println("----------------------------------------");
                    }
                }
                System.out.println("----------------------------------------");
            }
        }
    }
}
