package sap.facultymanagementsystem.model.enums;

public enum AcademicYear {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4);

    private final int value;

    AcademicYear(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
