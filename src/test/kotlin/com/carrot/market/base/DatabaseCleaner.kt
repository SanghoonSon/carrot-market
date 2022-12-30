package com.carrot.market.base

import com.google.common.base.CaseFormat
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Table
import jakarta.persistence.metamodel.EntityType
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@ActiveProfiles("test")
class DatabaseCleaner : InitializingBean {

    @PersistenceContext
    private lateinit var em: EntityManager

    private var tableNames: List<String> = mutableListOf()

    override fun afterPropertiesSet() {
        tableNames = em.metamodel.entities.stream()
            .filter { e -> e.javaType.getAnnotation(Entity::class.java) != null }
            .map { e -> getTableName(e) }
            .collect(Collectors.toList())
    }

    private fun getTableName(e: EntityType<*>): String {
        return if (e.javaType.isAnnotationPresent(Table::class.java)) {
            e.javaType.getAnnotation(Table::class.java).name
        } else CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.name)
    }

    @Transactional
    fun execute() {
        em.flush()
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        for (tableName in tableNames) {
            em.createNativeQuery("TRUNCATE TABLE $tableName RESTART IDENTITY").executeUpdate()
        }
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}
