package com.example.transaction.infrastructure.adapter.jpa.respository;

import com.example.transaction.domain.model.entity.Sale;
import com.example.transaction.domain.port.repository.SaleRepository;
import com.example.transaction.infrastructure.adapter.entity.SaleEntity;
import com.example.transaction.infrastructure.adapter.jpa.SaleSpringJpaAdapterRepository;
import com.example.transaction.infrastructure.adapter.mapper.SaleDboMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@AllArgsConstructor
public class SaleH2Repository implements SaleRepository {
    private final SaleSpringJpaAdapterRepository saleSpringJpaAdapterRepository;
    private final SaleDboMapper saleDboMapper;

    @Override
    public List<Sale> create(List<Sale> request) {
        List<SaleEntity> salesToSave = request.stream().map(saleDboMapper::toDatabase).toList();

        return saleSpringJpaAdapterRepository.saveAll(salesToSave).stream()
                .map(saleDboMapper::toDomain)
                .toList();
    }
}
