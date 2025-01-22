package com.gsb.gsbecommercebackend.service.orders;

import com.gsb.gsbecommercebackend.dao.orders.OrderAnalyticsDAO;
import com.gsb.gsbecommercebackend.dao.orders.OrderDAO;
import com.gsb.gsbecommercebackend.model.ordersClass.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderAnalyticsService {

    @Autowired
    private OrderAnalyticsDAO orderAnalyticsDAO;

    public List<Map<String, Object>> getDailyOrderAmount(){
        return orderAnalyticsDAO.getDailyOrderAmount();
    }
}
