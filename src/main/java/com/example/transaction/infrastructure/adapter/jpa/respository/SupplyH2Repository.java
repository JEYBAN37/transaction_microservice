package com.example.transaction.infrastructure.adapter.jpa.respository;
import com.example.transaction.domain.model.entity.Supply;
import com.example.transaction.domain.port.repository.SupplyRepository;
import com.example.transaction.infrastructure.adapter.entity.SupplyEntity;
import com.example.transaction.infrastructure.adapter.jpa.SupplySpringJpaAdapterRepository;
import com.example.transaction.infrastructure.adapter.mapper.SupplyDboMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@AllArgsConstructor
public class SupplyH2Repository implements SupplyRepository {
    private final SupplySpringJpaAdapterRepository supplySpringJpaAdapterRepository;
    private final SupplyDboMapper supplyDboMapper;

    @Override
    public Supply create(Supply request) {
        SupplyEntity userToSave = supplyDboMapper.toDatabase(request);
        userToSave.setDate(request.getDate());
        SupplyEntity userSaved = supplySpringJpaAdapterRepository.save(userToSave);
        return supplyDboMapper.toDomain(userSaved);
    }

    @Override
    public Supply update(Supply request) {
        SupplyEntity userToUpdate = supplyDboMapper.toDatabase(request);
        SupplyEntity userUpdate = supplySpringJpaAdapterRepository.save(userToUpdate);
        return supplyDboMapper.toDomain(userUpdate);
    }
}
