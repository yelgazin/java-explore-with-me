package ru.practicum.ewm.event.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageFormatter {
    public static String format(String format, Object... params) {
        return org.slf4j.helpers.MessageFormatter.arrayFormat(format, params).getMessage();
    }
}
