package ru.practicum.ewm.event.business.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Polygon;
import ru.practicum.ewm.event.persistence.entity.EventEntity;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventSearchParameters {

    /**
     * Текст для поиска в содержимом аннотации и подробном описании события.
     */
    String text;

    /**
     * Список идентификаторов пользователей, чьи события нужно найти.
     */
    Collection<Long> usersIds;

    /**
     * Список идентификаторов категорий в которых будет вестись поиск.
     */
    Collection<Long> categoriesIds;

    /**
     * Список состояний в которых находятся искомые события.
     */
    Collection<EventEntity.EventState> states;

    /**
     * Признак платного события.
     */
    Boolean paid;

    /**
     * Дата и время не раньше которых должно произойти событие.
     */
    LocalDateTime rangeStart;

    /**
     * Дата и время не позже которых должно произойти событие.
     */
    LocalDateTime rangeEnd;

    /**
     * Только события у которых не исчерпан лимит запросов на участие.
     */
    boolean onlyAvailable;

    /**
     * Поиск событий внутри полигона.
     */
    Polygon<G2D> polygon;

    /**
     * Список идентификаторов локаций в которых будет вестись поиск.
     */
    Collection<Long> locationsIds;
}
