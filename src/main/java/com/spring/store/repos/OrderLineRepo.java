package com.spring.store.repos;

import com.spring.store.model.OrderLine;
import org.springframework.data.repository.CrudRepository;

public interface OrderLineRepo extends CrudRepository<OrderLine, Long> {
}
