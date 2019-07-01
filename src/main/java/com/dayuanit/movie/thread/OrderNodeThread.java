package com.dayuanit.movie.thread;

import com.dayuanit.movie.service.OrderService;
import com.dayuanit.movie.test.HeapSort;
import com.dayuanit.movie.test.OrderNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderNodeThread implements InitializingBean {

    Logger log = LoggerFactory.getLogger(OrderNodeThread.class);

    @Autowired
    HeapSort<OrderNode> nodeHeapSort;
    @Autowired
    OrderService orderService;

    @Override
    public void afterPropertiesSet() {

        System.out.println("优先级队列线程启动。。。");

//        new Thread(() -> {}).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    OrderNode orderNode = nodeHeapSort.peek();
                    if (null == orderNode){
                        System.out.println("orderNode is null...");
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        continue;
                    }

                    int ttl = (int)(orderNode.getDeadTime() - System.currentTimeMillis());
                    if (ttl <= 0 ){
                        System.out.println("订单过期取消" + orderNode.getOrderId());
                        // {} 里面的值就是orderNode.getOrderId()填充的值
                        log.info("订单{}过期取消",orderNode.getOrderId());
                        nodeHeapSort.remove();
                        try {
                            orderService.processCancelOrder(orderService.getOrder(orderNode.getOrderId()));

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    // 如果还没有过期，那么就让线程睡，睡到过期这个时间差，那么等他醒来正好订单过期
                    if (ttl > 0){
                        try {
                            Thread.sleep(ttl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },"OrderNodeThread...").start();
    }
}


