package sap.faculty_management_system.request;

public class EnrollmentRequest {
    private String courseId;
    private String studentId;

    public EnrollmentRequest() {
    }

    public EnrollmentRequest(String courseId, String studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
