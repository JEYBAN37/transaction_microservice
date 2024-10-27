package com.example.transaction.infrastructure.adapter.jpa;


import com.example.transaction.infrastructure.adapter.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleSpringJpaAdapterRepository extends JpaRepository<SaleEntity, Long> ,
        JpaSpecificationExecutor<SaleEntity> {}
