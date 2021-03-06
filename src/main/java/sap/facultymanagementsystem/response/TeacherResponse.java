package sap.facultymanagementsystem.response;

public class TeacherResponse {
    private boolean isCreated;
    private String message;

    public TeacherResponse() {
    }

    public TeacherResponse(boolean isCreated, String message) {
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
