package com.example.transaction.infrastructure.adapter.jpa;
import com.example.transaction.infrastructure.adapter.entity.SupplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface SupplySpringJpaAdapterRepository extends JpaRepository<SupplyEntity, Long>, JpaSpecificationExecutor<SupplyEntity> {
    Optional<SupplyEntity> findById(Long id);
}