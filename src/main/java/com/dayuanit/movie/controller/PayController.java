package com.dayuanit.movie.controller;

import com.dayuanit.movie.dto.ResponseDTO;
import com.dayuanit.movie.entry.Film;
import com.dayuanit.movie.entry.Order;
import com.dayuanit.movie.handler.AliPayHandler;
import com.dayuanit.movie.service.FilmService;
import com.dayuanit.movie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PayController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private AliPayHandler aliPayHandler;

    @RequestMapping(value = "/pay/toPay/{filmId}/{cinemaId}/{sceneId}")
    public ResponseDTO toPay(@PathVariable int filmId,
                             @PathVariable int cinemaId,
                             @PathVariable int sceneId,
                             String seatInfo){
        // TODO 创建订单
        Order order = orderService.createOrder(filmId, cinemaId, sceneId, seatInfo);
        Film film = filmService.getFilm(filmId);
        String payForm = aliPayHandler.createPayForm(order, film);
        return ResponseDTO.success(payForm);
    }

    @RequestMapping(value = "/pay/alipayNotify")
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response){
//        System.out.println(">>>>>>>>/pay/alipayNotify");
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            aliPayHandler.processNotify(parameterMap);
            try(OutputStream os = response.getOutputStream()) {
                os.write("success".getBytes());
                os.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

