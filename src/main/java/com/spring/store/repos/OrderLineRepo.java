package com.spring.store.repos;

import com.spring.store.model.OrderLine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderLineRepo extends CrudRepository<OrderLine, Long> {
    List<OrderLine> findAllByBook_Id(Long id);
}
