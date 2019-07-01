package com.dayuanit.movie.config;

import com.dayuanit.movie.test.HeapSort;
import com.dayuanit.movie.test.OrderNode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

@Configuration
public class BeanConfig {

    @Bean
    public HeapSort<OrderNode> nodeHeapSort(){

        Comparator<OrderNode> comparator = (n1,n2) -> (int)(n1.getDeadTime() - n2.getDeadTime());
        HeapSort<OrderNode> objectHeapSort = new HeapSort<>(comparator);
        return objectHeapSort;
    }
}
