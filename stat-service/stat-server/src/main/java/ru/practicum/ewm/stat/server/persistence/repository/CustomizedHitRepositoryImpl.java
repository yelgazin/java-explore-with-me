package ru.practicum.ewm.stat.server.persistence.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stat.server.persistence.dto.StatProjection;
import ru.practicum.ewm.stat.server.persistence.entity.HitEntity;
import ru.practicum.ewm.stat.server.persistence.entity.QHitEntity;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomizedHitRepositoryImpl implements CustomizedHitRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CustomizedHitRepositoryImpl(JpaContext context) {
        EntityManager entityManager = context.getEntityManagerByManagedType(HitEntity.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<StatProjection> getStats(LocalDateTime start, LocalDateTime end, Collection<String> endpointUris, boolean unique) {

        QHitEntity hitEntity = QHitEntity.hitEntity;
        List<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(hitEntity.timestamp.goe(start));
        conditions.add(hitEntity.timestamp.loe(end));

        if (!endpointUris.isEmpty()) {
            conditions.add(makeEndpointCondition(endpointUris));
        }

        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        NumberExpression<Long> countUniqueIpExpression = unique ? hitEntity.ip.countDistinct() : hitEntity.ip.count();

        JPAQuery<StatProjection> query = queryFactory
                .select(Projections.bean(StatProjection.class,
                        hitEntity.endpoint.app,
                        hitEntity.endpoint.uri,
                        countUniqueIpExpression.as("hits")))
                .from(hitEntity)
                .where(finalCondition)
                .groupBy(hitEntity.endpoint.id, hitEntity.endpoint.app, hitEntity.endpoint.uri)
                .orderBy(hitEntity.endpoint.id.asc());

        return query.fetch();
    }

    private BooleanExpression makeEndpointCondition(Collection<String> endpointUris) {
        return QHitEntity.hitEntity.endpoint.uri.in(endpointUris);
    }
}
