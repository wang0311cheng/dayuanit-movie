package com.dayuanit.movie.task;

import com.dayuanit.movie.entry.Order;
import com.dayuanit.movie.enums.OrderStatusEnums;
import com.dayuanit.movie.handler.AliPayHandler;
import com.dayuanit.movie.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlipayQueryTask extends BaseTask {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AliPayHandler aliPayHandler;

    @Scheduled(cron = "0/10 * * * * *")
    public void doTask() {
        System.out.println("AlipayQueryTask ...");
        processQuery();
    }

    public void processQuery(){
        List<Order> orders = orderMapper.listOrder(OrderStatusEnums.待付款.getK());
        for (Order order : orders){
            try {
                aliPayHandler.queryOrder(order);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
