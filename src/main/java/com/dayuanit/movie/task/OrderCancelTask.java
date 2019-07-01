package com.dayuanit.movie.task;

import com.dayuanit.movie.entry.Order;
import com.dayuanit.movie.enums.OrderStatusEnums;
import com.dayuanit.movie.mapper.OrderMapper;
import com.dayuanit.movie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class OrderCancelTask extends BaseTask {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0/15 * * * * *")
    public void doTask() {
//        processCancel();
    }

    public void processCancel(){

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,-2);
        List<Order> orders = orderMapper.listOrder4Cancel(OrderStatusEnums.待付款.getK(),cal.getTime());

        for (Order order : orders){

            try {
                orderService.processCancelOrder(order);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
