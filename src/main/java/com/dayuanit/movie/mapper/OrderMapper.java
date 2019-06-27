package com.dayuanit.movie.mapper;

import com.dayuanit.movie.entry.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updateStatues(@Param("orderId") Integer orderId,@Param("oldStatue") Integer oldStatue,@Param("newStatue") Integer newStatue);

    List<Order> listOrder(int status);

    List<Order> listOrder4Cancel(@Param("orderStatus") Integer orderStatus, @Param("deadTime") Date deadTime);
}