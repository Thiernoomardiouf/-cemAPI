package com.cybersourcecem.repositories;

import com.cybersourcecem.entities.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
@RepositoryRestResource
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
}
