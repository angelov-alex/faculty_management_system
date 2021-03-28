package sap.faculty_management_system.response;

public class DelistResponse {
    private boolean isDeleted;
    private String message;

    public DelistResponse() {
    }

    public DelistResponse(boolean isDeleted, String message) {
        this.isDeleted = isDeleted;
        this.message = message;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
