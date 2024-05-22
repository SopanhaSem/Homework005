//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package service;

import exception.CourseNotFoundException;
import exception.InstructorValidatorException;
import exception.RequirementValidatorException;
import exception.TitleValidatorException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import model.Course;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import repository.CourseRepository;

public class CourseServiceImp implements CourseService {
    public void addAllCourses() {
        Scanner scanner = new Scanner(System.in);
        String[] title = null;
        String[] instructorName = null;
        String[] requirement = null;

        while(true) {
            try {
                String requirementInput;
                if (title == null) {
                    System.out.println("=".repeat(30));
                    System.out.print("[+] Insert course title: ");
                    requirementInput = scanner.nextLine();
                    if (requirementInput.isEmpty() || requirementInput.matches(".*\\d.*")) {
                        throw new TitleValidatorException("[!] Invalid title. Please enter a valid text without numbers.");
                    }

                    title = new String[]{requirementInput};
                }

                if (instructorName == null) {
                    System.out.print("[+] Insert course instructor name: ");
                    requirementInput = scanner.nextLine();
                    if (requirementInput.isEmpty() || requirementInput.matches(".*\\d.*")) {
                        throw new InstructorValidatorException("[!] Invalid instructor name. Please enter a valid text without numbers.");
                    }

                    instructorName = new String[]{requirementInput};
                }

                if (requirement == null) {
                    System.out.print("[+] Insert course requirement: ");
                    requirementInput = scanner.nextLine();
                    if (requirementInput.isEmpty() || requirementInput.matches(".*\\d.*")) {
                        throw new RequirementValidatorException("[!] Invalid requirement. Please enter a valid text without numbers.");
                    }

                    requirement = new String[]{requirementInput};
                }

                Course course = new Course((new Random()).nextInt(100), title, instructorName, requirement, LocalDate.of(2024, 3, 3).toString());
                CourseRepository.addCourse(course);
                System.out.println("[+] Course added successfully!");
                return;
            } catch (InstructorValidatorException | RequirementValidatorException | TitleValidatorException var6) {
                System.out.println(var6.getMessage());
            }
        }
    }

    public void getAllCourses() {
        List<Course> courses = CourseRepository.allCourses();
        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);
        table.addCell("ID");
        table.addCell("TITLE");
        table.addCell("INSTRUCTOR");
        table.addCell("REQUIREMENT");
        table.addCell("START DATE");
        table.setColumnWidth(0, 10, 30);
        table.setColumnWidth(1, 25, 60);
        table.setColumnWidth(2, 25, 60);
        table.setColumnWidth(3, 30, 60);
        table.setColumnWidth(4, 15, 15);
        if (courses.isEmpty()) {
            table.addCell("N/A", 5);
            table.addCell("No courses available.", 5);
        } else {
            Iterator var3 = courses.iterator();

            while(var3.hasNext()) {
                Course course = (Course)var3.next();
                table.addCell(course.getId().toString());
                table.addCell(String.join(", ", course.getTitle()));
                table.addCell(String.join(", ", course.getInstructorName()));
                table.addCell(String.join(", ", course.getRequirement()));
                table.addCell(course.getStartDate());
            }
        }

        System.out.println(table.render());
    }

    public void findCourseById() throws CourseNotFoundException {
        List<Course> courses = CourseRepository.allCourses();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("[+] Input Id For Search: ");

            try {
                int fId = scanner.nextInt();
                boolean found = false;
                Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);
                table.addCell("ID");
                table.addCell("TITLE");
                table.addCell("INSTRUCTOR");
                table.addCell("REQUIREMENT");
                table.addCell("START DATE");
                table.setColumnWidth(0, 10, 30);
                table.setColumnWidth(1, 25, 60);
                table.setColumnWidth(2, 25, 60);
                table.setColumnWidth(3, 30, 60);
                table.setColumnWidth(4, 15, 15);
                Iterator var6 = courses.iterator();

                while(var6.hasNext()) {
                    Course course = (Course)var6.next();
                    if (course.getId().equals(fId)) {
                        table.addCell(course.getId().toString());
                        table.addCell(String.join(", ", course.getTitle()));
                        table.addCell(String.join(", ", course.getInstructorName()));
                        table.addCell(String.join(", ", course.getRequirement()));
                        table.addCell(course.getStartDate());
                        System.out.println(table.render());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    throw new CourseNotFoundException("[!] Course not found with id: " + fId);
                }

                return;
            } catch (InputMismatchException var8) {
                System.out.println("[!] Invalid input. Please enter a valid integer ID.");
                scanner.nextLine();
            }
        }
    }

    public void findCourseByTitle() throws CourseNotFoundException {
        List<Course> courses = CourseRepository.allCourses();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            while(true) {
                System.out.print("[+] Input Title For Search: ");
                String fTitle = scanner.nextLine().trim();
                if (fTitle.isEmpty()) {
                    System.out.println("[!] Title cannot be empty. Please enter a valid title.");
                } else {
                    if (!fTitle.matches(".*\\d.*")) {
                        List<Course> filteredCourses = courses.stream().filter((coursex) -> {
                            return Arrays.stream(coursex.getTitle()).anyMatch((title) -> {
                                return title.equalsIgnoreCase(fTitle);
                            });
                        }).toList();
                        if (filteredCourses.isEmpty()) {
                            throw new CourseNotFoundException("[!] Course not found with title: " + fTitle);
                        }

                        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);
                        table.addCell("ID");
                        table.addCell("TITLE");
                        table.addCell("INSTRUCTOR");
                        table.addCell("REQUIREMENT");
                        table.addCell("START DATE");
                        table.setColumnWidth(0, 10, 30);
                        table.setColumnWidth(1, 25, 60);
                        table.setColumnWidth(2, 25, 60);
                        table.setColumnWidth(3, 30, 60);
                        table.setColumnWidth(4, 15, 15);
                        Iterator var6 = filteredCourses.iterator();

                        while(var6.hasNext()) {
                            Course course = (Course)var6.next();
                            table.addCell(course.getId().toString());
                            table.addCell(String.join(", ", course.getTitle()));
                            table.addCell(String.join(", ", course.getInstructorName()));
                            table.addCell(String.join(", ", course.getRequirement()));
                            table.addCell(course.getStartDate());
                        }

                        System.out.println(table.render());
                        return;
                    }

                    System.out.println("[!] Invalid title. Please enter a valid text without numbers.");
                }
            }
        }
    }

    public void removeCourseById() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("[+] Input ID of the course to delete: ");
            if (!scanner.hasNextInt()) {
                System.out.println("[!] Invalid input. Please enter a valid course ID (a number).");
                scanner.next();
                if (!this.promptRetry(scanner)) {
                    break;
                }
            } else {
                int courseId = scanner.nextInt();

                try {
                    CourseRepository.removeCourseById(courseId);
                    System.out.println("[-] Course with ID " + courseId + " has been successfully removed.");
                    break;
                } catch (CourseNotFoundException var4) {
                    System.out.println(var4.getMessage());
                    if (!this.promptRetry(scanner)) {
                        break;
                    }
                }
            }
        }

    }

    private boolean promptRetry(Scanner scanner) {
        System.out.print("[!] Do you want to try again? (yes/no): ");
        String response = scanner.next();
        return response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y");
    }
}
