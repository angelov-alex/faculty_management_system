package sap.faculty_management_system.response;

public class CourseResponse {
    private boolean isCreated;
    private String message;

    public CourseResponse() {
    }

    public CourseResponse(boolean isCreated, String message) {
        this.isCreated = isCreated;
        this.message = message;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean created) {
        isCreated = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
