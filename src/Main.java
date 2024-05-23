
import exception.CourseNotFoundException;
import service.CourseService;
import service.CourseServiceImp;
import view.View;

public class Main {
    private static final CourseService courseService = new CourseServiceImp();
    public static void main(String[] args) {
        while(true) {
            try {
                switch (View.menu()) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        courseService.addAllCourses();
                        break;
                    case 2:
                        courseService.getAllCourses();
                        break;
                    case 3:
                        try {
                            courseService.findCourseById();
                        } catch (CourseNotFoundException var5) {
                            System.out.println(var5.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            courseService.findCourseByTitle();
                        } catch (CourseNotFoundException var4) {
                            System.out.println(var4.getMessage());
                        }
                        break;
                    case 5:
                        try {
                            courseService.removeCourseById();
                        } catch (CourseNotFoundException var3) {
                            System.out.println(var3.getMessage());
                        }
                        break;
                    default:
                        System.out.println("[!] Invalid option. Please try again.");
                }
            } catch (Exception var6) {
                System.out.println("[!] Please Input Valid Option");
            }
        }
    }
}
