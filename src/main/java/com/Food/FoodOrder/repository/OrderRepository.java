
package com.Food.FoodOrder.repository;

import com.food.FoodModel.Order.EnumType.DeliveryType;
import com.food.FoodModel.Order.EnumType.PaymentType;
import com.food.FoodModel.Order.Order;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Adedayo
 */

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    
        public List<Order> findAll();

        public Order findById(int orderId);

        public Order findOrderByOrderNumber(String OrderNumber);
	  	
	List<Order> getOrderByDeliveryType(DeliveryType deliveryType);
	 	
	List<Order> getOrderByPaymentType(PaymentType paymentType);
}
