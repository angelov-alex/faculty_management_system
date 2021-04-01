package sap.facultymanagementsystem.request;

public class CourseRequest {

    private String name;
    private String courseLeaderId;
    private String creditId;

    public CourseRequest() {
    }

    public CourseRequest(String name, String courseLeaderId, String creditId) {
        this.name = name;
        this.courseLeaderId = courseLeaderId;
        this.creditId = creditId;
    }

    public String getName() {
        return name;
    }

    public String getCourseLeaderId() {
        return courseLeaderId;
    }

    public String getCreditId() {
        return creditId;
    }

    @Override
    public String toString() {
        return " CourseRequest{" +
                "name='" + name + '\'' +
                ", courseLeaderId='" + courseLeaderId + '\'' +
                ", creditId='" + creditId + '\'' +
                '}';
    }
}
