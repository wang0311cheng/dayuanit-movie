package com.dayuanit.movie.mapper;

import com.dayuanit.movie.entry.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    List<OrderInfo> listOrderInfoByOrderId(int orderId);

    List<OrderInfo> listAlreadyBuyTicket(int scenId);

    OrderInfo loadAlreadyBuyTicket(@Param("scenId") int scenId,
                                   @Param("row") int row,
                                   @Param("col") int col);
}