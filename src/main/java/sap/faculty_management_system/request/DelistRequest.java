package sap.faculty_management_system.request;

public class DelistRequest {
    private final String courseId;
    private final String studentId;

    public DelistRequest(String courseId, String studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getStudentId() {
        return studentId;
    }
}
