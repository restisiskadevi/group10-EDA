package com.room10.inventoryrepository.repository;

import com.room10.inventoryrepository.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    Item findBySkuCode(String skuCode);

}
