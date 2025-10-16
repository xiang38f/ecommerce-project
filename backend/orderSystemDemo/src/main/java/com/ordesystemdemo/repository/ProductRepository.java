package com.ordesystemdemo.repository;

import com.ordesystemdemo.entity.OrderDetail;
import com.ordesystemdemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    @Procedure(procedureName = "get_available_products")
    List<Product> getAvailableProducts();

    @Procedure(procedureName = "add_new_product")
    void addNewProduct(String productName, Double price, Integer quantity);

    @Transactional
    @Procedure(procedureName = "update_product_stock")
    void updateProductStock(
            @Param("p_product_id") String productId,
            @Param("p_quantity") Integer quantity
    );

}
