package com.ordesystemdemo.repository;

import com.ordesystemdemo.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {


    List<OrderDetail> findByOrderId(String orderId);

    @Transactional(readOnly = true)
    @Query(value = "CALL get_order_details(:orderId)", nativeQuery = true)
    List<OrderDetail> getOrderDetailsByProcedure(@Param("orderId") String orderId);

    @Transactional
    @Procedure(procedureName = "create_order_detail")
    void createOrderDetail(
            @Param("p_order_id") String orderId,
            @Param("p_product_id") String productId,
            @Param("p_quantity") Integer quantity,
            @Param("p_stand_price") BigDecimal standPrice,
            @Param("p_item_price") BigDecimal itemPrice
    );


}
