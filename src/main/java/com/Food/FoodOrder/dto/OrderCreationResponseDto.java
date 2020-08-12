
package com.Food.FoodOrder.dto;

import com.food.FoodModel.Order.EnumType.DeliveryType;
import com.food.FoodModel.Order.EnumType.PaymentType;
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
public class OrderCreationResponseDto {
    
    private String orderNumber;
    private long quantity;
    private double amount;  
    PaymentType paymentType;
    DeliveryType deliveryType;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerAddress;
    private String customerEmail;
    private String dateOrdered;

   
}