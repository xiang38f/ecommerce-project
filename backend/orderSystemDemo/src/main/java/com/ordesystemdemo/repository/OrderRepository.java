
package com.ordesystemdemo.repository;

import com.ordesystemdemo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {

    Optional<Order> findById( String orderId);

    @Transactional
    @Procedure(procedureName = "create_order")
    String createOrder(
            @Param("p_member_id") String memberId,
            @Param("p_total_price") BigDecimal totalPrice,
            @Param("p_pay_status") Integer payStatus
    );

}
