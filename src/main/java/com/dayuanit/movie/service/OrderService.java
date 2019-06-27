package com.dayuanit.movie.service;

import com.dayuanit.movie.entry.*;
import com.dayuanit.movie.enums.OrderStatusEnums;
import com.dayuanit.movie.exception.BizException;
import com.dayuanit.movie.mapper.*;
import com.dayuanit.movie.util.MoneyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private FilmMapper filmMapper;
    @Autowired
    private CinemaMapper cinemaMapper;
    @Autowired
    private FilmScheduleMapper filmScheduleMapper;
    @Autowired
    private RedisService redisService;

    public Order createOrder(int filmId,int cinemaId,int sceneId,String seatInfo){
        Film film = filmMapper.selectByPrimaryKey(filmId);
        Cinema cinema = cinemaMapper.selectByPrimaryKey(cinemaId);
        FilmSchedule filmSchedule = filmScheduleMapper.selectByPrimaryKey(sceneId);
        System.out.println(">>>>>>film="+film);
        System.out.println(">>>>>>cinema="+cinema);
        System.out.println(">>>>>>>filmSchene="+ filmSchedule );
        if (null == film || null == cinema || null == filmSchedule || StringUtils.isBlank(seatInfo)){
            throw new BizException("存在参数错误");
        }
        String[] seats = seatInfo.split(",");

        String amount = MoneyUtils.mul(String.valueOf(seats.length), filmSchedule.getPrice());

        Order order = new Order();
        order.setAmount(amount);
        order.setCinemaId(cinemaId);
        order.setFilmId(filmId);
        order.setFilmScheduleId(sceneId);
        order.setStatus(0);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        orderMapper.insert(order);

        for (String seat : seats){
            String[] seatRowCol = seat.split("-");
            String row = seatRowCol[0];
            String col = seatRowCol[1];

            // 防止并发,设置缓存，这个key存在30秒钟，只要我这个key设置成功，那么说明没有人跟我竞争，
            // 而且这个key会在redis中存放30秒钟，那么这5秒钟不会有人进来，在这个30秒钟之内我再去数据库中查，如果有，就抛异常，没有则继续执行
            boolean flag = redisService.cacheSeatInfo(sceneId, Integer.valueOf(row), Integer.valueOf(col));
            if (!flag){
                throw new BizException("座位号已被选中，请重新选择>>");
            }

            // 查询数据库
            OrderInfo orderInfo1 = orderInfoMapper.loadAlreadyBuyTicket(sceneId, Integer.valueOf(row), Integer.valueOf(col));
            if (null != orderInfo1){
                throw new BizException("座位号已被选中，请重新选择");
            }

            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(order.getId());
            orderInfo.setPrice(filmSchedule.getPrice());
            orderInfo.setSeatCol(Integer.valueOf(col));
            orderInfo.setSeatRow(Integer.valueOf(row));
            orderInfoMapper.insert(orderInfo);
        }

        return order;
    }

    public void processAlipayNotify(int orderId, String totalAmount){
        Order order = orderMapper.selectByPrimaryKey(orderId);

        if (null == order){
            return;
        }
        if (order.getStatus() != OrderStatusEnums.待付款.getK()){
            return;
        }
        if (order.getStatus() == OrderStatusEnums.取消.getK()){
            // 退款操作
            return;
        }
        if (Double.parseDouble(order.getAmount()) != Double.parseDouble(totalAmount)){
            return;
        }

        System.out.println(">>>>>>> " + OrderStatusEnums.付款成功.getK());


        // 改变订单状态
        int row = orderMapper.updateStatues(orderId, order.getStatus(), OrderStatusEnums.付款成功.getK());
        if (1 != row){
            throw new BizException("异步处理失败");
        }
    }

    public Order getOrder(int orderId){
        Order order = orderMapper.selectByPrimaryKey(orderId);
        return order;
    }

    public List<OrderInfo> listOrderInfo(int orderId){
        List<OrderInfo> orderInfos = orderInfoMapper.listOrderInfoByOrderId(orderId);
        return orderInfos;
    }

    public void processQueryOrder(Order order, String amount){
        //校验金额
        if (Double.parseDouble(order.getAmount()) != Double.parseDouble(amount)) {
            throw new BizException("订单金额不相等");
        }
        int row = orderMapper.updateStatues(order.getId(), order.getStatus(), OrderStatusEnums.付款成功.getK());
        if (1 != row){
            throw new BizException("异步处理失败");
        }
    }

    public void processCancelOrder(Order order){

        int row = orderMapper.updateStatues(order.getId(), order.getStatus(), OrderStatusEnums.取消.getK());

        if (1 != row){
            throw new BizException("订单取消失败");
        }
    }
}
