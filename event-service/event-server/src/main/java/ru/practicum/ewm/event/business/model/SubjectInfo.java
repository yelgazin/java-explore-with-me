package ru.practicum.ewm.event.business.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectInfo {

    Long userId;
    SubjectRole role;

    public static SubjectInfo of(Long userId, SubjectRole role) {
        return new SubjectInfo(userId, role);
    }

    public static SubjectInfo of(SubjectRole role) {
        return new SubjectInfo(null, role);
    }

    public boolean isAdmin() {
        return role == SubjectRole.ADMIN;
    }

    public boolean isUser() {
        return role == SubjectRole.USER;
    }
}
