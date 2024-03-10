package ru.practicum.ewm.event.persistence.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.spatial.MultiPolygonExpression;
import org.apache.commons.lang3.NotImplementedException;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static ru.practicum.ewm.event.persistence.entity.QEventEntity.eventEntity;
import static ru.practicum.ewm.event.persistence.entity.QLocationEntity.locationEntity;

@Component
public class CustomizedEventRepositoryImpl implements CustomizedEventRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CustomizedEventRepositoryImpl(JpaContext jpaContext) {
        EntityManager entityManager = jpaContext.getEntityManagerByManagedType(EventEntity.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<EventEntity> findEvents(EventSearchParameters searchParameters,
                                        EventSortBy sortBy,
                                        Pageable pageable) {

        BooleanExpression conditions = prepareConditions(searchParameters);
        OrderSpecifier<?>[] sortOrder = prepareSortOrder(sortBy);

        return queryFactory.selectFrom(eventEntity)
                .where(conditions)
                .orderBy(sortOrder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private OrderSpecifier<?>[] prepareSortOrder(EventSortBy sortBy) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        if (sortBy != null) {
            switch (sortBy) {
                case EVENT_DATE:
                    orders.add(eventEntity.date.desc());
                    break;
                case VIEWS:
                    orders.add(eventEntity.views.desc());
                default:
                    throw new NotImplementedException();
            }
        }
        orders.add(eventEntity.id.asc());

        return orders.toArray(new OrderSpecifier<?>[0]);
    }

    private BooleanExpression prepareConditions(EventSearchParameters searchParameters) {

        List<BooleanExpression> conditions = new ArrayList<>();

        String text = searchParameters.getText();
        if (text != null && !text.isBlank()) {
            conditions.add(makeSearchTextCondition(text));
        }

        Collection<Long> userIds = searchParameters.getUsersIds();
        if (userIds != null && !userIds.isEmpty()) {
            conditions.add(eventEntity.initiator.id.in(userIds));
        }

        Collection<Long> categoriesIds = searchParameters.getCategoriesIds();
        if (categoriesIds != null && !categoriesIds.isEmpty()) {
            conditions.add(eventEntity.category.id.in(categoriesIds));
        }

        Collection<EventEntity.EventState> states = searchParameters.getStates();
        if (states != null && !states.isEmpty()) {
            conditions.add(eventEntity.state.in(states));
        }

        Boolean paid = searchParameters.getPaid();
        if (paid != null) {
            conditions.add(eventEntity.paid.eq(paid));
        }

        LocalDateTime rangeStart = searchParameters.getRangeStart();
        if (rangeStart != null) {
            conditions.add(eventEntity.date.goe(rangeStart));
        }

        LocalDateTime rangeEnd = searchParameters.getRangeEnd();
        if (rangeEnd != null) {
            conditions.add(eventEntity.date.loe(rangeEnd));
        }

        Collection<Long> locationIds = searchParameters.getLocationsIds();
        if (!Objects.isNull(locationIds)) {
            conditions.add(makeLocationsIdsCondition(locationIds));
        }

        Polygon<G2D> polygon = searchParameters.getPolygon();
        if (!Objects.isNull(polygon)) {
            conditions.add(eventEntity.location.intersects(polygon));
        }

        boolean onlyAvailable = searchParameters.isOnlyAvailable();
        if (onlyAvailable) {
            conditions.add(makeOnlyAvailableCondition());
        }

        return conditions.stream()
                .reduce(BooleanExpression::and)
                .orElseGet(() -> Expressions.asBoolean(true).isTrue());
    }

    private BooleanExpression makeSearchTextCondition(String text) {
        return eventEntity.annotation.containsIgnoreCase(text)
                .or(eventEntity.description.containsIgnoreCase(text));
    }

    private BooleanExpression makeOnlyAvailableCondition() {
        return eventEntity.participantLimit.eq(0)
                .or(eventEntity.participantLimit.lt(eventEntity.participationRequests.size()));
    }

    private BooleanExpression makeLocationsIdsCondition(Collection<Long> locationIds) {

        List<BooleanExpression> expressions = new ArrayList<>();

        locationIds.forEach(locationId -> {
            BooleanExpression booleanExpression = queryFactory.selectFrom(locationEntity)
                    .where(locationEntity.id.eq(locationId)
                            .and(locationEntity.polygon.contains(eventEntity.location)))
                    .exists();
            expressions.add(booleanExpression);
        });

        return expressions.stream()
                .reduce(BooleanExpression::or)
                .orElseThrow(IllegalArgumentException::new);
    }
}
