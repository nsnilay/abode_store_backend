package com.cart.cart.repository;

import com.cart.cart.entity.HistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<HistoryEntity, String> {

    List<HistoryEntity> getByEmailId(String emailId);
    HistoryEntity save(HistoryEntity historyEntityEntity);

}
