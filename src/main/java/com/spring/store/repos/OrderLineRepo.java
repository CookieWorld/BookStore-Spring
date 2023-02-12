package com.spring.store.repos;

import com.spring.store.entity.OrderLine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderLineRepo extends CrudRepository<OrderLine, Long> {
    @Query(value = "SELECT * FROM order_line WHERE order_id = :id", nativeQuery = true)
    List<OrderLine> selectOrderLinesByOrderId(Long id);
}
