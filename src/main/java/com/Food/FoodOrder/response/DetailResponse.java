/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Food.FoodOrder.response;

import com.food.FoodModel.Customer.Customer;
import com.food.FoodModel.Food.FoodItem;
import com.food.FoodModel.Order.Order;
import lombok.Data;

/**
 *
 * @author Adedayo
 */
@Data
public class DetailResponse implements Response {
    
    Order order;
    
    Customer customer;
    
    FoodItem food;
    
    public DetailResponse(Order order, Customer customer, FoodItem food){
            this.order = order;
            this.customer  = customer;
            this.food = food;
            
    }
                    
}
