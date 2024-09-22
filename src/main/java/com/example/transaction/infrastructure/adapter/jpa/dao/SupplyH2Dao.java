package com.example.transaction.infrastructure.adapter.jpa.dao;
import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.domain.port.dao.SupplyDao;
import com.example.transaction.infrastructure.adapter.entity.SupplyEntity;
import com.example.transaction.infrastructure.adapter.jpa.SupplySpringJpaAdapterRepository;
import com.example.transaction.infrastructure.adapter.mapper.SupplyDboMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class SupplyH2Dao implements SupplyDao {

    private final SupplySpringJpaAdapterRepository supplySpringJpaAdapterRepository;
    private final SupplyDboMapper supplyDboMapper;

    @Override
    public Supply getSupply(Long id) {
        Optional<SupplyEntity> optionalArticle = supplySpringJpaAdapterRepository.findById(id);
        return optionalArticle.map(supplyDboMapper::toDomain).orElse(null);
    }

    @Override
    public boolean dateExist(Timestamp email) {
        return false;
    }

    @Override
    public boolean idExist(Long id) {
        return supplySpringJpaAdapterRepository.existsById(id);
    }
}
