package org.example;

import org.example.dao.CourseDao;
import org.example.dao.GradeDao;
import org.example.dao.StudentDao;
import org.example.exception.DAOException;
import org.example.model.Course;
import org.example.model.Grade;
import org.example.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final StudentDao studentDao = new StudentDao();
    private static final CourseDao courseDao = new CourseDao();
    private static final GradeDao gradeDao = new GradeDao();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addStudent();
                case "2" -> listOfAllStudents();
                case "3" -> updateStudent();
                case "4" -> deleteStudentById();
                case "5" -> listOfAllCourses();
                case "6" -> transferStudentById();
                case "7" -> addCourse();
                case "8" -> updateCourseById();
                case "9" -> deleteCourseById();
                case "10" -> listOfStudentGrades();
                case "11" -> addStudentGrade();
                case "0" -> running = false;
                default -> System.out.println("Unknown command, try again..");
            }
        }
    }

    private static void printMenu() {
        System.out.println("""

                    Main menu:
                1. Add student
                2. Show all students
                3. Update info of student
                4. Delete student
                5. Show all courses
                6. Transfer student to another course
                7. Add course
                8. Update info of course
                9. Delete course
                10. Show student's grades
                11. Add student's grade
                0. Exit
                Choose option:""");
    }

    private static void addStudent() {
        System.out.print("Full name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            studentDao.saveStudent(new Student(name, email));
            System.out.println("Student added");
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listOfAllStudents() {
        try {
            studentDao.findAllStudents().forEach(System.out::println);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateStudent() {
        System.out.print("Student's ID to update his info: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            Optional<Student> found = studentDao.findStudentById(id);
            if (found.isEmpty()) {
                System.out.println("Student not found");
                return;
            }

            Student student = found.get();
            System.out.print("New full name (current name: " + student.getFullName() + "): ");
            student.setFullName(scanner.nextLine());
            System.out.print("New email (current email: " + student.getEmail() + "): ");
            student.setEmail(scanner.nextLine());

            studentDao.updateStudent(student);
            System.out.println("Student updated");
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteStudentById() {
        System.out.print("Student's ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            studentDao.deleteStudentById(id);
            System.out.println("Student deleted");
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listOfAllCourses() {
        try {
            courseDao.findAllCourses().forEach(System.out::println);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void transferStudentById() {
        System.out.print("Student's ID: ");
        Long studentId = Long.parseLong(scanner.nextLine());
        System.out.print("From old course (ID): ");
        Long oldCourseId = Long.parseLong(scanner.nextLine());
        System.out.print("To new course (ID): ");
        Long newCourseId = Long.parseLong(scanner.nextLine());
        System.out.print("Grade: ");
        Short score = Short.parseShort(scanner.nextLine());

        try {
            gradeDao.transferStudentToCourse(studentId, oldCourseId, newCourseId, score);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addCourse() {
        System.out.print("Course name: ");
        String title = scanner.nextLine();

        try {
            courseDao.saveCourse(new Course(title));
            System.out.println("Course added");
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateCourseById() {
        System.out.print("Course's ID to update info: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            Optional<Course> found = courseDao.findCourseById(id);
            if (found.isEmpty()) {
                System.out.println("Course not found");
                return;
            }

            Course course = found.get();
            System.out.print("New name (current name: " + course.getTitle() + "): ");
            course.setTitle(scanner.nextLine());

            courseDao.updateCourse(course);
            System.out.println("Course updated");
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteCourseById() {
        System.out.print("Course's ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());

        try {
            courseDao.deleteById(id);
            System.out.println("Course deleted");
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listOfStudentGrades() {
        System.out.print("Student's ID: ");
        Long studentId = Long.parseLong(scanner.nextLine());

        try {
            List<Grade> grades = gradeDao.findGradesByStudentId(studentId);
            if (grades.isEmpty()) {
                System.out.println("This student has no grades");
            } else {
                grades.forEach(System.out::println);
            }
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addStudentGrade() {
        System.out.print("Student's ID: ");
        Long studentId = Long.parseLong(scanner.nextLine());
        System.out.print("Course ID: ");
        Long courseId = Long.parseLong(scanner.nextLine());
        System.out.print("Grade: ");
        Short score = Short.parseShort(scanner.nextLine());

        try {
            gradeDao.saveStudentGrade(new Grade(studentId, courseId, score));
            System.out.println("Grade added");
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }
}