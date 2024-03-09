package ru.practicum.ewm.event.persistence.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.NotImplementedException;
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

import static ru.practicum.ewm.event.persistence.entity.QEventEntity.eventEntity;

@Component
public class CustomizedEventRepositoryImpl implements CustomizedEventRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CustomizedEventRepositoryImpl(JpaContext context) {
        EntityManager entityManager = context.getEntityManagerByManagedType(EventEntity.class);
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
}
