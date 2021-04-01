package sap.facultymanagementsystem.request;

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

    @Override
    public String toString() {
        return " DelistRequest{" +
                "courseId='" + courseId + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
