package com.Food.FoodOrder.service.impl;

//import com.Food.FoodOrder.utility.AppData;
import com.Food.FoodOrder.dto.OrderDto;
import com.Food.FoodOrder.dto.OrderCreationResponseDto;
import com.Food.FoodOrder.dto.ServerResponse;
import com.food.FoodModel.Order.EnumType.DeliveryType;
import com.food.FoodModel.Order.EnumType.PaymentType;
import com.Food.FoodOrder.mail.EmailService;
import com.Food.FoodOrder.mail.Mail;
import com.food.FoodModel.Order.Order;
import com.Food.FoodOrder.repository.OrderRepository;
import com.Food.FoodOrder.response.DetailResponse;
import com.Food.FoodOrder.service.OrderService;
import com.Food.FoodOrder.utility.ServerResponseType;
import com.food.FoodModel.Customer.Customer;
import com.food.FoodModel.Food.FoodItem;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Transactional
@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
        
        
        @Autowired
        EmailService emailService;
        
        @Autowired
        RestTemplate restTemplate;
        
       @Bean
       RestTemplate getRestTemplate(RestTemplateBuilder builder){
          return builder.build();
        }

        
	
	 @Override
	public Order findById(Integer orderId) {
		
		Optional<Order> order = orderRepository.findById(orderId);
                        if (order.isPresent())
                             return order.get();
                        else
                          return new Order();
	}

   
    @Override
    public ServerResponse createOrder(OrderDto request, PaymentType paymentType, DeliveryType deliveryType) {
      
        ServerResponse response = new ServerResponse();
        Order order = null;
        
        String phoneNumber = request.getPhoneNumber() != null ? request.getPhoneNumber() : request.getPhoneNumber();
        String foodName = request.getFoodName() != null ? request.getFoodName() : request.getFoodName();
	long quantity = request.getQuantity() != 0 ? request.getQuantity() : request.getQuantity();
                
               
               //Validations
                if (phoneNumber == null || phoneNumber.isEmpty()) {
                   response.setData("");
                   response.setMessage("Please enter phone Number");
                   response.setSuccess(false);
                   response.setStatus(ServerResponseType.FAILED);

            return response;
		}
                
                  if (quantity == 0) {
                   response.setData("");
                   response.setMessage("Please enter Quantity");
                   response.setSuccess(false);
                   response.setStatus(ServerResponseType.FAILED);

            return response;
		}
                  
                   if (foodName == null || foodName.isEmpty()) {
                   response.setData("");
                   response.setMessage("please enter food name");
                   response.setSuccess(false);
                   response.setStatus(ServerResponseType.FAILED);

            return response;
		}
                  
                  if (paymentType == null || paymentType.toString().isEmpty()) {
			response.setData("");
                        response.setMessage("Please select payment method");
                        response.setSuccess(false);
                        response.setStatus(ServerResponseType.FAILED);

               return response;
		}
                
                if (deliveryType == null || deliveryType.toString().isEmpty()) {
			response.setData("");
                        response.setMessage("Please select delivery Type");
                        response.setSuccess(false);
                        response.setStatus(ServerResponseType.FAILED);

            return response;
		}
                
                
                try {
			
		//Customer customer = customerRepository.findByPhoneNumber(request.getPhoneNumber());
			
			if (order == null) {
				response.setData("");
                                response.setMessage("Customer not found");
                                response.setSuccess(false);
                                response.setStatus(ServerResponseType.FAILED);

                return response;
                
                     }
			order = new Order();
                        
			//generating order number
			String orderNumber = "MEAL" + System.currentTimeMillis();
              			
                        order.setOrderNumber(orderNumber);
			//order.setCustomer(customer);
			order.setDeliveryType(deliveryType);
			order.setPaymentType(paymentType);
			order.setQuantity(request.getQuantity());
			order.setOrderDate(new Date());
                        
           
                        
            Mail mail = new Mail();
            mail.setTo("adminDayo@gmail.com");
            mail.setFrom("dayoanifannu@gmail.com");
            mail.setSubject("Food Order");

            Map<String, Object> model = new HashMap<String, Object>();
             
            model.put("title", "Dear " + "Customer");
            model.put("quantity", request.getQuantity());
            model.put("delivery", order.getDeliveryType());
            model.put("paymentType", order.getPaymentType());
            model.put("amount", order.getAmount());
            //model.put("address", order.getCustomer().getAddress());
            //model.put("phone", order.getCustomer().getPhoneNumber());
	    model.put("orderDate", order.getOrderDate().toLocaleString());
            model.put("content", "Order: <b>" + orderNumber + "</b> has been placed by a customer, Kindly ensure that this order is fulfilled");
            mail.setModel(model);
            mail.setTemplate("order_template.ftl");  
            emailService.sendSimpleMessage(mail);
            
            model.put("content", "Hi, you placed an order to buy food from our platform, below are your order details.");
            //model.put("title", "Dear " + order.getCustomer().getFirstName());
            mail.setModel(model);
            //mail.setTo(user.getEmail());
            emailService.sendSimpleMessage(mail);
            
                        orderRepository.save(order);
                        
                      
                        //converting Date to string
            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.getDefault());
             Date parsedDate = sdf.parse(String.valueOf(order.getOrderDate()));
             SimpleDateFormat print = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
                        
               //Server Response On creating Order 
                OrderCreationResponseDto dto = new OrderCreationResponseDto();
                dto.setQuantity(request.getQuantity());
                //dto.setCustomerFirstName(customer.getFirstName());
                //dto.setCustomerLastName(customer.getLastName());
                //dto.setCustomerEmail(user.getEmail());
                //dto.setCustomerPhone(customer.getPhoneNumber());
                //dto.setCustomerAddress(customer.getAddress());
                dto.setPaymentType(paymentType);
                dto.setDeliveryType(deliveryType);
                dto.setDateOrdered(print.format(parsedDate));
                dto.setAmount(order.getAmount());

                response.setData(dto);
                response.setMessage("Customer order successfully placed");
                response.setSuccess(true);
                response.setStatus(ServerResponseType.OK);
                        
                }catch(Exception e) {
			e.printStackTrace();
		response.setData("");
	        response.setMessage("Order was not placed");
	        response.setSuccess(false);
	        response.setStatus(ServerResponseType.INTERNAL_SERVER_ERROR);
	        
	        return response;			
		}
		
	return response;        
    }
    


    @Override
    public ServerResponse getOrderByDeliveryType(DeliveryType deliveryType) {
        ServerResponse response = new ServerResponse();
		
   	    	
   		  if (deliveryType == null || deliveryType.toString().isEmpty()) {
   	       response.setData("");
               response.setMessage("Please select delivery type");
               response.setSuccess(false);
               response.setStatus(ServerResponseType.FAILED);
               return response;
   		}
           
           try {
   			//Ensuring orders with selected delivery type exist
        	   List<Order> delivery = orderRepository.getOrderByDeliveryType(deliveryType);
 
        	   if (delivery.size() < 1) {
   		    response.setData("");
   	            response.setMessage("Order Not found for selected delivery type");
   	            response.setSuccess(false);
   	            response.setStatus(ServerResponseType.OK);
   	            return response;
   			}
   			//Orders with selected delivery type fetched and displayed
   	        response.setData(delivery);
                response.setMessage("Order available for selected delivery method");
                response.setSuccess(true);
                response.setStatus(ServerResponseType.OK);
   			
        	   
           }catch(Exception e) {
        	   response.setData("");
               response.setMessage("Something went wrong");
               response.setSuccess(false);
               response.setStatus(ServerResponseType.INTERNAL_SERVER_ERROR);
               e.printStackTrace();
               return response; 
           }
           
		return response;
	}

    
   @Override
    public ServerResponse getOrderByPaymentType(PaymentType paymentType) {
      ServerResponse response = new ServerResponse();
		
   	    	
   		  if (paymentType == null || paymentType.toString().isEmpty()) {
   	       response.setData("");
               response.setMessage("Please select payment type");
               response.setSuccess(false);
               response.setStatus(ServerResponseType.FAILED);
               return response;
   		}
           
           try {
   			//Ensuring orders with selected food item exist
        	   List<Order> payment = orderRepository.getOrderByPaymentType(paymentType);
 
        	   if (payment.size() < 1) {
   		    response.setData("");
   	            response.setMessage("Order Not found for selected payment type");
   	            response.setSuccess(false);
   	            response.setStatus(ServerResponseType.OK);
   	            return response;
   			}
   			//Orders with selected food item fetched and displayed
   	        response.setData(payment);
                response.setMessage("Order available for selected payment type");
                response.setSuccess(true);
                response.setStatus(ServerResponseType.OK);
   			
        	   
           }catch(Exception e) {
        	response.setData("");
               response.setMessage("Something went wrong");
               response.setSuccess(false);
               response.setStatus(ServerResponseType.INTERNAL_SERVER_ERROR);
               e.printStackTrace();
               return response; 
           }
           
		return response;
	}
    

    @Override
    public ServerResponse getAllOrders() {
        ServerResponse response = new ServerResponse();
		
		try {
			List<Order> order = orderRepository.findAll();
			
			if (order.size() < 1) {
				response.setData("");
				response.setMessage("Order list is empty");
				response.setSuccess(false);
				response.setStatus(ServerResponseType.NO_CONTENT);
				
				return response;
			}
			
			response.setData(order);
			response.setMessage("Data fetched successfully");
			response.setSuccess(true);
			response.setStatus(ServerResponseType.OK);
		} catch (Exception e){
			
			response.setData("");
			response.setMessage("Failed to fetch data");
			response.setSuccess(false);
			response.setStatus(ServerResponseType.FAILED);
			return response;
		}
		
		return response;
	}

  
       /**
	 * Fetching order through order number
	 */
	@Override
	public ServerResponse getOrderByOrderNumber(String orderNumber) {

		ServerResponse response =  new ServerResponse();
		
		//Validating to ensure that order code is inputed
		if(orderNumber == null || orderNumber.isEmpty()) {
                    
                    response.setData("");
                    response.setMessage("Please provide order number");
                    response.setSuccess(false);
                    response.setStatus(ServerResponseType.FAILED);
                    return response;
		}
		
		
		try {
			//Confirming inputed order number exists
			Order orders = orderRepository.findOrderByOrderNumber(orderNumber);
			
		     if (orders == null) {
			response.setData("");
                        response.setMessage("Order with order number " + orderNumber + " does not exist");
                        response.setSuccess(false);
                        response.setStatus(ServerResponseType.OK);
                        return response;
			}
			
			
			//Server response if order with number exists 
			OrderCreationResponseDto ORdto = new OrderCreationResponseDto();
			ORdto.setOrderNumber(orders.getOrderNumber());
			ORdto.setPaymentType(orders.getPaymentType());
			ORdto.setDeliveryType(orders.getDeliveryType());
			ORdto.setQuantity(orders.getQuantity());
			//ORdto.setCustomerFirstName(orders.getCustomer().getFirstName());
			//ORdto.setCustomerLastName(orders.getCustomer().getLastName());
			//ORdto.setCustomerEmail(user.getEmail());
			//ORdto.setCustomerPhone(orders.getCustomer().getPhoneNumber());
			//ORdto.setCustomerAddress(orders.getCustomer().getAddress());
			//ORdto.setOrderDate(print.format(parsedDate));
			ORdto.setAmount(orders.getAmount());
			
			response.setData(ORdto);
                        response.setMessage("Oder printed successfully");
                        response.setSuccess(true);
                        response.setStatus(ServerResponseType.OK);
			
			
		}catch(Exception e) {
			response.setData("");
            response.setMessage("Something went wrong");
            response.setSuccess(false);
            response.setStatus(ServerResponseType.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            return response;
		}
		
		return response;
	}

    @Override
    public DetailResponse findDetailResponse(int orderId) {
        Order order = findById(orderId);
        Customer customer = getCustomer(order.getCustomerId());
        FoodItem foodItem = getFoodItem(order.getFoodId());
        
        return new DetailResponse(order, customer, foodItem);
    }

    private Customer getCustomer(int customerId){

        Customer customer=restTemplate.getForObject("http://localhost:9292/customers/"+customerId,Customer.class);
        return customer;

    }

    private FoodItem getFoodItem(int foodId){

       return restTemplate.getForObject("http://localhost:9393/foods/"+foodId,FoodItem.class);


    }
       
}
    

    
		
	
	

