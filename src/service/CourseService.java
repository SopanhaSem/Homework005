
package service;

import exception.CourseNotFoundException;

public interface CourseService {
    void addAllCourses();

    void getAllCourses();

    void findCourseById() throws CourseNotFoundException;

    void findCourseByTitle() throws CourseNotFoundException;

    void removeCourseById() throws CourseNotFoundException;
}
