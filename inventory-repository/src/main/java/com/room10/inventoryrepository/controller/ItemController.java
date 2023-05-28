package com.room10.inventoryrepository.controller;

import com.room10.inventoryrepository.entity.Item;
import com.room10.inventoryrepository.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping("item")
@RestController
public class ItemController {

    @Autowired
    ItemRepository repository;

    @PutMapping("/item-taken")
    @Transactional
    public ResponseEntity<Boolean> itemTaken(@RequestParam String skuCode, @RequestParam Integer stock){
        Item item = repository.findBySkuCode(skuCode);
        if(Objects.isNull(item)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Integer currentStock = item.getStock();
        if(currentStock < stock){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        item.setStock(currentStock - stock);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
