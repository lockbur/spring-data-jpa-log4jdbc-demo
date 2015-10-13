package com.spr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spr.model.Shop;
import com.spr.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *  
 * @author lcockbur@aboutdata.me
 *
 */
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    public Page<Shop> findByName(String name, Pageable pageable);

    public Page<Shop> findByNameLike(String name, Pageable pageable);

    public Page<Shop> findByStatus(Status status, Pageable pageable);
}
