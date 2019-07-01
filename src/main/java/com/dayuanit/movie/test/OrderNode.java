package com.dayuanit.movie.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Setter
//@Getter
// 或者
@Data
// 构造方法
@AllArgsConstructor
public class OrderNode {

    private int orderId;

    private long deadTime;
}
