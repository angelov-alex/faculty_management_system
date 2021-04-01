package sap.facultymanagementsystem.model.enums;

public enum Rank {
    PROFESSOR("Professor"),
    DOCENT("Docent"),
    ASSISTANT("Assistant"),
    CHIEF_ASSISTANT("Chief assistant");

    private final String value;

    Rank(String value) {
        this.value = value;

    }

    public String getValue() {
        return value;
    }
}
