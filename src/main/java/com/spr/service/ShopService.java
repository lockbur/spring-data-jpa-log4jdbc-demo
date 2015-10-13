package com.spr.service;

import java.util.List;

import com.spr.exception.ShopNotFound;
import com.spr.model.Shop;
import com.spr.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopService {

    public Shop create(Shop shop);

    public Shop delete(int id) throws ShopNotFound;

    public List<Shop> findAll();

    public Shop update(Shop shop) throws ShopNotFound;

    public Shop findById(int id);

    public Page<Shop> findByName(String name, Pageable pageable);

    public Page<Shop> findByStatus(Status status, Pageable pageable);

}
