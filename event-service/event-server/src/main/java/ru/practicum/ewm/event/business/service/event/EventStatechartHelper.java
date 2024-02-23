package ru.practicum.ewm.event.business.service.event;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.event.business.model.EventStateAction;
import ru.practicum.ewm.event.business.model.SubjectRole;
import ru.practicum.ewm.event.persistence.entity.EventEntity.EventState;

import java.util.Map;
import java.util.Optional;

import static ru.practicum.ewm.event.business.model.EventStateAction.*;
import static ru.practicum.ewm.event.business.model.SubjectRole.ADMIN;
import static ru.practicum.ewm.event.business.model.SubjectRole.USER;
import static ru.practicum.ewm.event.persistence.entity.EventEntity.EventState.*;

@UtilityClass
public class EventStatechartHelper {

    private static final Map<SubjectRole, Map<EventStateAction, Map<EventState, EventState>>> eventStateGraph
            = Map.of(
            ADMIN, Map.of(
                    PUBLISH_EVENT, Map.of(PENDING, PUBLISHED),
                    REJECT_EVENT, Map.of(PENDING, CANCELED)
            ),
            USER, Map.of(
                    SEND_TO_REVIEW, Map.of(CANCELED, PENDING),
                    CANCEL_REVIEW, Map.of(PENDING, CANCELED)
            )
    );

    public static Optional<EventState> next(SubjectRole role, EventStateAction action, EventState from) {
        Map<EventStateAction, Map<EventState, EventState>> actionMap = eventStateGraph.get(role);
        if (actionMap == null) {
            return Optional.empty();
        }

        Map<EventState, EventState> eventMap = actionMap.get(action);
        if (eventMap == null) {
            return Optional.empty();
        }

        EventState nextState = eventMap.get(from);
        if (nextState == null) {
            return Optional.empty();
        }
        return Optional.of(nextState);
    }
}
