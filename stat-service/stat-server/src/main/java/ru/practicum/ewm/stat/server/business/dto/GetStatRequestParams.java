package ru.practicum.ewm.stat.server.business.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetStatRequestParams {

    LocalDateTime start;
    LocalDateTime end;
    Collection<String> uris;
    boolean unique;

    public static GetStatRequestParams of (LocalDateTime start, LocalDateTime end, Collection<String> uris,
                                           boolean unique) {
        GetStatRequestParams request = new GetStatRequestParams();
        request.setStart(start);
        request.setEnd(end);
        request.setUris(uris);
        request.setUnique(unique);
        return request;
    }
}
