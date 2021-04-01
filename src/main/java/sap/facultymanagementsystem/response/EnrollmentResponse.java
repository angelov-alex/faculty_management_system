package sap.facultymanagementsystem.response;

public class EnrollmentResponse {
    private boolean isEnrolled;
    private String message;

    public EnrollmentResponse() {
    }

    public EnrollmentResponse(boolean isEnrolled, String message) {
        this.isEnrolled = isEnrolled;
        this.message = message;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(boolean enrolled) {
        isEnrolled = enrolled;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
