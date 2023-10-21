package com.tttm.birdfarmshop.Service.Impl;

import com.tttm.birdfarmshop.Enums.AccountStatus;
import com.tttm.birdfarmshop.Enums.OrderStatus;
import com.tttm.birdfarmshop.Enums.ProductStatus;
import com.tttm.birdfarmshop.Enums.VoucherStatus;
import com.tttm.birdfarmshop.Models.*;
import com.tttm.birdfarmshop.Repository.*;
import com.tttm.birdfarmshop.Service.OrderService;
import com.tttm.birdfarmshop.Utils.Request.OrderRequest;
import com.tttm.birdfarmshop.Utils.Response.MessageResponse;
import com.tttm.birdfarmshop.Utils.Response.OrderResponse;
import com.tttm.birdfarmshop.Utils.Response.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrderVoucherRepository orderVoucherRepository;
    private final CustomerRepository customerRepository;
    private final ShipperRepository shipperRepository;
    private final VoucherRepository voucherRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private Lock lock = new ReentrantLock();
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private boolean isValidInformation(OrderRequest orderRequest)
    {
        return !orderRequest.getCustomerEmail().isBlank() && !orderRequest.getCustomerEmail().isEmpty() &&
                !orderRequest.getCustomerName().isBlank() && !orderRequest.getCustomerName().isEmpty() &&
                !orderRequest.getCustomerAddress().isBlank() && !orderRequest.getCustomerAddress().isEmpty() &&
                !orderRequest.getCustomerPhone().isBlank() && !orderRequest.getCustomerPhone().isEmpty() &&
                orderRequest.getListProduct().size() >= 1;

    }

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        try
        {
            lock.lock();
            Optional<Customer> customer = customerRepository.findById(orderRequest.getCustomerID()); // // Check Product ID is existed or not
            Optional<User> user =  userRepository.findById(orderRequest.getCustomerID());
            if(customer.isEmpty() || !isValidInformation(orderRequest) || user.get().getAccountStatus() == AccountStatus.INACTIVE) // Check validation fields
            {
                logger.info("Customer ID, Order Information is Invalid or Your Account is banned");
                return new OrderResponse();
            }

            float orderAmount = 0;
            List<String> listProductID = orderRequest.getListProduct();
            List<Product> productList = new ArrayList<>();
            for(String productID : listProductID)  // Check Product ID is existed or not
            {
                Optional<Product> productOptional = productRepository.findById(productID);
                if (productOptional.isEmpty()) {
                    logger.info("Product ID is not existed");
                    return new OrderResponse();
                }
                Product product = productOptional.get();
                if (product.getProductStatus() == ProductStatus.UNAVAILABLE || product.getQuantity() <= 0) {
                    logger.info("Product ID is Unavailable or out of stock");
                    return new OrderResponse();
                }
                productList.add(product);
                orderAmount += product.getPrice();
            }

            // Apply Voucher to the Order
            List<Voucher> voucherList = new ArrayList<>();
            List<String> voucherStringList = orderRequest.getVoucherList();
            for(String voucherID : voucherStringList)
            {
                Optional<Voucher> voucher = voucherRepository.findById(Integer.parseInt(voucherID));
                if(voucher.isEmpty() || voucher.get().getVoucherStatus().equals(VoucherStatus.UNAVAILABLE))
                {
                    logger.info("Voucher ID is not existed to apply voucher");
                    return new OrderResponse();
                }
                else // Apply Voucher to Order
                {
                    orderAmount -= voucher.get().getValue();
                    voucherList.add(voucher.get());
                }
            }

            LocalDateTime currentTime = LocalDateTime.now(); // Get Current Time when Order Product
            Date orderDate = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());

            // Choose random shipper to take care the Order
            List<Shipper> shipperList = shipperRepository.findAll();
            Random random = new Random();
            int randomShipperIndex = random.nextInt(shipperList.size());
            Shipper shipper = shipperList.get(randomShipperIndex);

            orderRepository.save(
                    Order.builder()
                            .customerPhone(orderRequest.getCustomerPhone())
                            .customerName(orderRequest.getCustomerName())
                            .customerEmail(orderRequest.getCustomerEmail())
                            .customerAddress(orderRequest.getCustomerAddress())
                            .note(orderRequest.getNote())
                            .status(OrderStatus.PENDING)
                            .amount(orderAmount)
                            .orderDate(orderDate)
                            .customer(customer.get())
                            .shipper(shipper)
                            .build()
            );

            // Get the newest Order then Add Order to OrderDetail
            List<Order> orders = orderRepository.findAll();
            int size = orderRepository.findAll().size();
            Order newestOrder = orders.get(size - 1);

            productList.forEach(product ->
                    {
                        product.setQuantity(product.getQuantity() - 1);
                        product.setProductStatus(ProductStatus.UNAVAILABLE);
                        productRepository.save(product);
                        orderDetailRepository.save(new OrderDetail(product, newestOrder));
                    }
            );

            voucherList.forEach(voucher -> {
                orderVoucherRepository.save(new OrderVoucher(newestOrder, voucher));
            });

            return createOrderResponse(newestOrder, productList);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
        return new OrderResponse();
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

    private OrderResponse createOrderResponse(Order newestOrder, List<Product> productList)
    {
        return OrderResponse.builder()
                .orderID(newestOrder.getOrderID())
                .customerID(newestOrder.getCustomer().getCustomerID())
                .customerPhone(newestOrder.getCustomerPhone())
                .customerName(newestOrder.getCustomerName())
                .customerEmail(newestOrder.getCustomerEmail())
                .customerAddress(newestOrder.getCustomerAddress())
                .note(newestOrder.getNote())
                .orderAmount(newestOrder.getAmount())
                .orderDate(newestOrder.getOrderDate())
                .productList(
                        productList.stream()
                            .map(this::mapperedToProductResponse)
                            .collect(Collectors.toList())
                )
                .build();
    }

    private ProductResponse mapperedToProductResponse(Product product)
    {
        return ProductResponse.builder()
                .productID(product.getProductID())
                .productName(product.getProductName())
                .price(product.getPrice())
                .description(product.getDescription())
                .typeOfProduct(product.getTypeOfProduct())
                .images(
                        imageRepository.findImageByProductID(product.getProductID())
                                .stream()
                                .map(Image::getImageUrl)
                                .collect(Collectors.toList())
                )
                .feedback(product.getFeedback())
                .productStatus(product.getProductStatus())
                .rating(product.getRating())
                .quantity(product.getQuantity())
                .build();
    }

    @Transactional
    @Override
    public MessageResponse DeleteOrder(Integer OrderID) {
        Optional<Order> order = orderRepository.findById(OrderID);

        if(order.isEmpty())
        {
            return new MessageResponse("Fail");
        }
        Customer customer = order.get().getCustomer();
        User user = userRepository.findById(customer.getCustomerID()).get();

        if(user.getAccountStatus() == AccountStatus.INACTIVE)
        {
            return new MessageResponse("Fail");
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findAllOrderDetailByOrderID(OrderID);
        if(orderDetailList.size() == 0)
        {
            return new MessageResponse("Fail");
        }

        List<Product> productList = orderDetailList
                    .stream()
                    .map(OrderDetail::getProduct)
                    .collect(Collectors.toList());

        // Update the status of Product
        productList.forEach(
                product -> {
                    product.setProductStatus(ProductStatus.AVAILABLE);
                    productRepository.save(product);
                }
        );

        // Delete OrderVoucher by Order ID
        orderVoucherRepository.deleteOrderVoucherByOrderID(OrderID);

        // Delete OrderDetail by Order ID
        orderDetailRepository.deleteOrderDetailByOrderID(OrderID);

        // Delete Order By OrderID
        orderRepository.deleteOrderByOrderID(OrderID);

        // Check if Customer Cancel Order exceed specific times

        int numberCancleOrder = customer.getNumberCancleOrder();
        String message = "Success";
        if(numberCancleOrder + 1 == 3)
        {
            message = "Cancel more than 3 times. Your account will be banned";
        }
        else if(numberCancleOrder + 1 > 3)
        {
            message = "Your account has been Banned";
            user.setAccountStatus(AccountStatus.INACTIVE);
            userRepository.save(user);
        }
        customer.setNumberCancleOrder(numberCancleOrder + 1);
        customerRepository.save(customer);

        return new MessageResponse(message);
    }
}
