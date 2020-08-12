package com.Food.FoodOrder.response;

import com.food.FoodModel.Order.Order;
import lombok.Data;

/**
 *
 * @author Adedayo
 */
@Data
public class SimpleResponse implements Response {
    
    Order order;
    
   public SimpleResponse(Order order){
       this.order = order;
   } 
}
