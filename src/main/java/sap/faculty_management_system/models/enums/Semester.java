package sap.faculty_management_system.models.enums;

public enum Semester {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    ;

    private int value;

    Semester(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
