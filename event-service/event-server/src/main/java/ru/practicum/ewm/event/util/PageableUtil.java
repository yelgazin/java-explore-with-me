package ru.practicum.ewm.event.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class PageableUtil {

    public static Pageable of(long from, int size) {
        return PageRequest.of((int) (from / size), size);
    }
}
