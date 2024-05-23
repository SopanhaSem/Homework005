
package repository;

import exception.CourseNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Course;

public class CourseRepository {
    private static final List<Course> allCourseList = new ArrayList<>();

    public static void addCourse(Course course) {
        allCourseList.add(course);
    }

    public static List<Course> allCourses() {
        return new ArrayList<>(allCourseList);
    }

    public static void removeCourseById(int courseId) throws CourseNotFoundException {
        Iterator<Course> iterator = allCourseList.iterator();
        Course course;
        do {
            if (!iterator.hasNext()) {
                throw new CourseNotFoundException("Course not found with id: " + courseId);
            }
            course = iterator.next();
        } while(!course.getId().equals(courseId));
        iterator.remove();
    }
}
