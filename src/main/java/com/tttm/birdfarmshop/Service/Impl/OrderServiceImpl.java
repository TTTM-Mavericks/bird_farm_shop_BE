package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.Enums.ERole;
import com.tttm.birdfarmshop.Enums.OrderStatus;
import com.tttm.birdfarmshop.Models.*;
import com.tttm.birdfarmshop.Repository.*;
import com.tttm.birdfarmshop.Service.OrderService;
import com.tttm.birdfarmshop.Utils.Request.OrderRequest;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import com.tttm.birdfarmshop.Utils.Response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ShipperRepository shipperRepository;
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private boolean isValidInformation(OrderRequest orderRequest)
    {
        return !orderRequest.getCustomerEmail().isBlank() && !orderRequest.getCustomerEmail().isEmpty() &&
                !orderRequest.getCustomerName().isBlank() && !orderRequest.getCustomerName().isEmpty() &&
                !orderRequest.getCustomerAddress().isBlank() && !orderRequest.getCustomerAddress().isEmpty() &&
                !orderRequest.getCustomerPhone().isBlank() && !orderRequest.getCustomerPhone().isEmpty() &&
                orderRequest.getListProduct().size() >= 1;

    }
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Optional<Customer> customer = customerRepository.findById(orderRequest.getCustomerID()); // // Check Product ID is existed or not
        if(customer.isEmpty() || !isValidInformation(orderRequest)) // Check validation fields
        {
            logger.info("Customer ID or Order Information is Invalid");
            return new OrderResponse();
        }

        float orderAmount = 0;
        List<String> listProductID = orderRequest.getListProduct();
        List<Product> productList = new ArrayList<>();
        for(String productID : listProductID)  // Check Product ID is existed or not
        {
            Optional<Product> product = productRepository.findById(productID);
            if (product.isPresent())
            {
                productList.add(product.get());
                orderAmount += product.get().getPrice();
            }
            else
            {
                logger.info("Product ID is not existed");
                return new OrderResponse();
            }
        }

        LocalDateTime currentTime = LocalDateTime.now(); // Get Current Time when Order Product
        Date orderDate = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());

        // Choose random shipper to take care the Order
        List<Shipper> shipperList = shipperRepository.findAll();
        Random random = new Random();
        int randomShipperIndex = random.nextInt(shipperList.size() - 1 - 0 + 1) + 0;
        Shipper shipper = shipperList.get(randomShipperIndex);

        orderRepository.save(
                new Order(
                        orderRequest.getCustomerPhone(),
                        orderRequest.getCustomerName(),
                        orderRequest.getCustomerEmail(),
                        orderRequest.getCustomerAddress(),
                        orderRequest.getNote(),
                        OrderStatus.PENDING,
                        orderAmount,
                        orderDate,
                        customer.get(),
                        shipper
                )
        );

        // Get the newest Order then Add Order to OrderDetail
        List<Order> orders = orderRepository.findAll();
        int size = orderRepository.findAll().size();
        Order newestOrder = orders.get(size - 1);

        productList.forEach(product ->
            orderDetailRepository.save(new OrderDetail(product, newestOrder))
        );

        return createOrderResponse(newestOrder, productList);
    }

    @Override
    public OrderResponse getOrderByOrderID(Integer OrderID) {
        return orderRepository.findById(OrderID)
                .map(order -> {
                    List<Product> productList = orderDetailRepository.findAllOrderDetailByOrderID(order.getOrderID())
                            .stream()
                            .map(OrderDetail::getProduct)
                            .collect(Collectors.toList());
                    return createOrderResponse(order, productList);
                })
                .orElse(new OrderResponse());
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return orderRepository.findAll()
                .stream()
                .map(order -> {
                    List<OrderDetail> orderDetailList = orderDetailRepository.findAllOrderDetailByOrderID(order.getOrderID());
                    List<Product> productList = orderDetailList
                            .stream()
                            .map(OrderDetail::getProduct)
                            .collect(Collectors.toList());
                    return createOrderResponse(order, productList);
                })
                .collect(Collectors.toList());
    }

    private OrderResponse createOrderResponse(Order newestOrder,  List<Product> productList)
    {
        return new OrderResponse(
                newestOrder.getOrderID(),
                newestOrder.getCustomer().getCustomerID(),
                newestOrder.getCustomerPhone(),
                newestOrder.getCustomerName(),
                newestOrder.getCustomerEmail(),
                newestOrder.getCustomerAddress(),
                newestOrder.getNote(),
                newestOrder.getAmount(),
                newestOrder.getOrderDate(),
                productList
        );
    }
}
