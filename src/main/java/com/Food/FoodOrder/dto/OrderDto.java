package com.Food.FoodOrder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Adedayo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    
    
    private String phoneNumber;
    
    private long quantity;
    
    private String foodName;

}
