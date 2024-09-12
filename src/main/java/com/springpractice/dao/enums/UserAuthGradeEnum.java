package com.springpractice.dao.enums;

public enum UserAuthGradeEnum {
    NORMAL("normal"),
    JUNIOR("junior"),
    SENIOR("senior");

    private final String grade;

    UserAuthGradeEnum(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public static UserAuthGradeEnum fromGrade(String grade) {
        for (UserAuthGradeEnum userAuthGradeEnum : UserAuthGradeEnum.values()) {
            if (userAuthGradeEnum.getGrade().equals(grade)) {
                return userAuthGradeEnum;
            }
        }
        throw new IllegalArgumentException("Unknown grade: " + grade);
    }


}
