package com.mb.ninjabank.backend.repositories;

import jakarta.persistence.EntityManager;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public class NaturalRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T,ID> implements NaturalRepository<T,ID> {

    private final EntityManager em;

    public NaturalRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    @Override
    public Optional<T> findBySimpleNaturalId(ID naturalId) {
        return em
                .unwrap(Session.class)
                .bySimpleNaturalId(this.getDomainClass())
                .loadOptional(naturalId);
    }

    @Override
    public Optional<T> findByNaturalId(Map<String, Object> naturalIds) {
        NaturalIdLoadAccess<T> naturalIdLoadAccess = em
                .unwrap(Session.class)
                .byNaturalId(this.getDomainClass());
        naturalIds.forEach(naturalIdLoadAccess::using);
        return naturalIdLoadAccess.loadOptional();
    }
}
