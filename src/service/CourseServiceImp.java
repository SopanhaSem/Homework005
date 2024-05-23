package service;

import exception.CourseNotFoundException;
import exception.InstructorValidatorException;
import exception.RequirementValidatorException;
import exception.TitleValidatorException;

import java.util.*;
import model.Course;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import repository.CourseRepository;

public class CourseServiceImp implements CourseService {

    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void addAllCourses() {
        String[] title = null;
        String[] instructorName = null;
        String[] requirement = null;
        Date startDate = new Date();

        while (true) {
            try {
                if (title == null) {
                    title = new String[]{getInput("Insert course title", new TitleValidatorException("[!] Invalid title. Please enter a valid text without numbers."))};
                }

                if (instructorName == null) {
                    instructorName = new String[]{getInput("Insert course instructor name", new InstructorValidatorException("[!] Invalid instructor name. Please enter a valid text without numbers."))};
                }

                if (requirement == null) {
                    requirement = new String[]{getInput("Insert course requirement", new RequirementValidatorException("[!] Invalid requirement. Please enter a valid text without numbers."))};
                }

                Course course = new Course(new Random().nextInt(100), title, instructorName, requirement, startDate.toString());
                CourseRepository.addCourse(course);
                System.out.println("[+] Course added successfully!");
                return;

            } catch (TitleValidatorException | InstructorValidatorException | RequirementValidatorException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getInput(String prompt, Exception exception) throws Exception {
        System.out.print("[+] " + prompt + ": ");
        String input = scanner.nextLine();
        if (input.isEmpty() || input.matches(".*\\d.*")) {
            throw exception;
        }
        return input;
    }

    @Override
    public void getAllCourses() {
        List<Course> courses = CourseRepository.allCourses();
        Table table = createTable();
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

        if (courses.isEmpty()) {
            table.addCell("N/A", cellStyle);
            table.addCell("No courses available.", cellStyle);
        } else {
            for (Course course : courses) {
                addCourseToTable(table, course, cellStyle);
            }
        }

        System.out.println(table.render());
    }

    @Override
    public void findCourseById() throws CourseNotFoundException {
        List<Course> courses = CourseRepository.allCourses();

        System.out.print("[+] Input Id For Search: ");
        try {
            int fId = scanner.nextInt();
            Course course = courses.stream()
                    .filter(c -> c.getId() == fId)
                    .findFirst()
                    .orElseThrow(() -> new CourseNotFoundException("[!] Course not found with id: " + fId));

            Table table = createTable();
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
            addCourseToTable(table, course, cellStyle);
            System.out.println(table.render());

        } catch (InputMismatchException e) {
            System.out.println("[!] Invalid input. Please enter a valid integer ID.");
            scanner.nextLine();
        }
    }

    @Override
    public void findCourseByTitle() throws CourseNotFoundException {
        List<Course> courses = CourseRepository.allCourses();

        System.out.print("[+] Input Title For Search: ");
        String fTitle = scanner.nextLine().trim();

        if (fTitle.isEmpty()) {
            System.out.println("[!] Title cannot be empty. Please enter a valid title.");
        } else if (fTitle.matches(".*\\d.*")) {
            System.out.println("[!] Invalid title. Please enter a valid text without numbers.");
        } else {
            List<Course> filteredCourses = courses.stream()
                    .filter(course -> Arrays.stream(course.getTitle())
                            .anyMatch(title -> title.equalsIgnoreCase(fTitle)))
                    .toList();

            if (filteredCourses.isEmpty()) {
                throw new CourseNotFoundException("[!] Course not found with title: " + fTitle);
            }

            Table table = createTable();
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
            for (Course course : filteredCourses) {
                addCourseToTable(table, course, cellStyle);
            }
            System.out.println(table.render());
        }
    }

    @Override
    public void removeCourseById() {
        while (true) {
            System.out.print("[+] Input ID of the course to delete: ");
            if (!scanner.hasNextInt()) {
                System.out.println("[!] Invalid input. Please enter a valid course ID (a number).");
                scanner.next();
                if (!promptRetry()) {
                    break;
                }
            } else {
                int courseId = scanner.nextInt();
                try {
                    CourseRepository.removeCourseById(courseId);
                    System.out.println("[-] Course with ID " + courseId + " has been successfully removed.");
                    break;
                } catch (CourseNotFoundException e) {
                    System.out.println(e.getMessage());
                    if (!promptRetry()) {
                        break;
                    }
                }
            }
        }
    }

    private boolean promptRetry() {
        System.out.print("[!] Do you want to try again? (yes/no): ");
        String response = scanner.next();
        return response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y");
    }

    private Table createTable() {
        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        table.addCell("ID", cellStyle);
        table.addCell("TITLE", cellStyle);
        table.addCell("INSTRUCTOR", cellStyle);
        table.addCell("REQUIREMENT", cellStyle);
        table.addCell("START DATE", cellStyle);
        table.setColumnWidth(0, 10, 30);
        table.setColumnWidth(1, 25, 60);
        table.setColumnWidth(2, 25, 60);
        table.setColumnWidth(3, 30, 60);
        table.setColumnWidth(4, 25, 60);
        return table;
    }

    private void addCourseToTable(Table table, Course course, CellStyle cellStyle) {
        table.addCell(course.getId().toString(), cellStyle);
        table.addCell(wrapWithBrackets(course.getTitle()), cellStyle);
        table.addCell(wrapWithBrackets(course.getInstructorName()), cellStyle);
        table.addCell(wrapWithBrackets(course.getRequirement()), cellStyle);
        table.addCell(course.getStartDate(), cellStyle);
    }

    private String wrapWithBrackets(String[] array) {
        return Arrays.stream(array)
                .map(s -> "[" + s + "]")
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }
}
