package com.springpractice.dao.converter;

import com.springpractice.dao.enums.UserAuthGradeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserAuthGradeConverter implements AttributeConverter<UserAuthGradeEnum, String> {

    @Override
    public String convertToDatabaseColumn(UserAuthGradeEnum userAuthGradeEnum) {
        if (userAuthGradeEnum == null) {
            return null;
        }
        return userAuthGradeEnum.getGrade();
    }

    @Override
    public UserAuthGradeEnum convertToEntityAttribute(String grade) {
        if (grade == null) {
            return null;
        }
        return UserAuthGradeEnum.fromGrade(grade);
    }
}
