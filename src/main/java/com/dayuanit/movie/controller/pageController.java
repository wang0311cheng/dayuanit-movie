package com.dayuanit.movie.controller;

import com.dayuanit.movie.entry.Order;
import com.dayuanit.movie.entry.OrderInfo;
import com.dayuanit.movie.enums.OrderStatusEnums;
import com.dayuanit.movie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
public class pageController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/")
    public String index(){
        return "home";
    }

    @RequestMapping("/toFilmInfo/{filmId}")
    public String toFilmInfo(@PathVariable int filmId, HttpServletRequest request) {
//        request.setAttribute("filmId",filmId);
        return "filmInfo";
    }

    @RequestMapping("/toTicketInfo/{filmId}")
    public String toTicketInfo(@PathVariable int filmId, HttpServletRequest request) {
//        request.setAttribute("filmId",filmId);
        return "filmTicket";
    }

    @RequestMapping("/tochoseScene/{cinemaId}/{filmId}")
    public String tochoseScene(@PathVariable int filmId,
                               @PathVariable int cinemaId,HttpServletRequest request) {
        return "choseScene";
    }

    @RequestMapping("/toByTicket/{cinemaId}/{filmId}/{sceneId}")
    public String toByTicket(@PathVariable int filmId,
                             @PathVariable int cinemaId,
                             @PathVariable int sceneId) {
        return "choseTicket";
    }

    @RequestMapping("/toSettlement/{cinemaId}/{filmId}/{sceneId}")
    public String toSettlement(@PathVariable int filmId,
                             @PathVariable int cinemaId,
                             @PathVariable int sceneId,
                               String seatInfo,
                               HttpServletRequest request) {
        System.out.println(">>>>>>>>>" + seatInfo);
        request.setAttribute("seatInfo",seatInfo);
        return "settlement";
    }

    @RequestMapping("/toOrderInfo/{orderId}")
    public String toOrderInfo(@PathVariable int orderId, HttpServletRequest request) {
        Order order = orderService.getOrder(orderId);
        request.setAttribute("filmId",order.getFilmId());
        request.setAttribute("cinemaId",order.getCinemaId());
        request.setAttribute("sceneId",order.getFilmScheduleId());
        List<OrderInfo> orderInfos = orderService.listOrderInfo(orderId);

        StringBuilder sx = new StringBuilder();

        for (OrderInfo orderInfo : orderInfos){
            sx.append(orderInfo.getSeatRow())
                    .append("-")
                    .append(orderInfo.getSeatCol())
                    .append(",");
        }

        request.setAttribute("seatInfo",sx.toString().substring(0,sx.toString().length()-1));
        request.setAttribute("success",order.getStatus() == OrderStatusEnums.付款成功.getK());
        return "orderInfo";
    }

    @RequestMapping("/picture/{filmPic}")
    public void showPic(@PathVariable String filmPic, HttpServletResponse response){
        response.setContentType("image/png");
        try(FileInputStream fs = new FileInputStream("/tmp/dyCinema_spider/" + filmPic);
            OutputStream ous = response.getOutputStream()){
            byte[] buff = new byte[1024];
            int length = -1;
            while (-1 != (length=fs.read(buff))){
                ous.write(buff,0,length);
                ous.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
