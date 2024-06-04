package com.numarics.repository;

import com.numarics.enums.Category;
import com.numarics.model.ProductEntity;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    boolean existsByNameAndCategoryAndPrice(String name, Category category, BigDecimal price);

    @Query("SELECT p FROM ProductEntity p WHERE " +
            "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:description IS NULL OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
            "(:category IS NULL OR LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%')))")
    Page<ProductEntity> searchProducts(
            @Param("name") String name,
            @Param("description") String description,
            @Param("category") String category,
            Pageable pageable);
}
