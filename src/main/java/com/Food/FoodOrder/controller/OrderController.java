package com.Food.FoodOrder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Food.FoodOrder.dto.ServerResponse;
import com.Food.FoodOrder.dto.OrderDto;
import com.Food.FoodOrder.response.Response;
import com.Food.FoodOrder.response.SimpleResponse;
import com.Food.FoodOrder.utility.ServerResponseType;
import com.Food.FoodOrder.service.OrderService;
import com.food.FoodModel.Order.EnumType.DeliveryType;
import com.food.FoodModel.Order.EnumType.PaymentType;
import com.food.FoodModel.Order.Order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/*
 * Developer's  account endpoint manager 
 */
@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/json")
@Api(tags = "Customer Management", description = "Endpoint")
public class OrderController {
	
	
	@Autowired
	OrderService orderService;
	
	private HttpHeaders responseHeaders = new HttpHeaders();
	
	
	@ApiOperation(value = "Account creation", response = ServerResponse.class)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createCustomer(@RequestBody OrderDto request){
		
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = orderService.createOrder(request, PaymentType.CARD_PAY, DeliveryType.PICKUP);
		 
		} catch (Exception e) {
			response.setData("An error occured" + e.getMessage());
                        response.setMessage("An error occured");
                        response.setSuccess(false);
                        response.setStatus(ServerResponseType.FAILED);
            
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));
	}
	
	
	
	@ApiOperation(value = "List all order", response = ServerResponse.class)
       @RequestMapping(value = "/all", method = RequestMethod.GET)
       @ResponseBody
       public ResponseEntity<?> getAllOrders(@RequestHeader("Authorization")  String authorization){
		
		ServerResponse response = new ServerResponse();
		
		try {
			
			response = orderService.getAllOrders();
		
		} catch (Exception e) {
			response.setData("User account verification error" + e.getMessage());
			response.setMessage("User account verification error");
	        response.setSuccess(false);
            response.setStatus(ServerResponseType.FAILED);
		}
		
		return new ResponseEntity<ServerResponse>(response, responseHeaders, ServerResponse.getStatus(response.getStatus()));

	}
       
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response getOrder(@PathVariable(value = "id") Integer orderId, @RequestParam(required = false) String type){ 
           
           if (type == null){         
             return new SimpleResponse(orderService.findById(orderId));
           }else{
               return orderService.findDetailResponse(orderId);
           }
       }
       
       /*@RequestMapping(value = "/{id}", method = RequestMethod.GET)
       public Order getOrder(@PathVariable(value = "id") Integer orderId){        
            	Order order = orderService.findById(orderId);
                log.info("Food Item Available");
			return order;
       } */     

}
