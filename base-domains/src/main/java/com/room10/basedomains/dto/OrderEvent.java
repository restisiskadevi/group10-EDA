package com.room10.basedomains.dto;

import com.room10.basedomains.dto.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent implements Serializable {
    private String message;
    private String status;
    private Order order;
}
