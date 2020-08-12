package com.Food.FoodOrder.service;

import com.Food.FoodOrder.dto.OrderDto;
import com.Food.FoodOrder.dto.ServerResponse;
import com.Food.FoodOrder.response.DetailResponse;
import com.food.FoodModel.Order.EnumType.DeliveryType;
import com.food.FoodModel.Order.EnumType.PaymentType;
import com.food.FoodModel.Order.Order;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Adedayo
 */

@Service
public interface OrderService {
    
     
	public Order findById(Integer orderId);
	
	public ServerResponse createOrder(OrderDto request, PaymentType paymentType,DeliveryType deliveryType);
	
        public ServerResponse getOrderByOrderNumber(String orderNumber);
        
	public ServerResponse getOrderByPaymentType(PaymentType paymentType);
	
	public ServerResponse getOrderByDeliveryType(DeliveryType deliveryType);
	
	public ServerResponse getAllOrders();
        
        DetailResponse findDetailResponse(int id);
}
