package com.dayuanit.movie.controller;


import com.alibaba.fastjson.JSON;
import com.dayuanit.movie.dto.ResponseDTO;
import com.dayuanit.movie.entry.*;
import com.dayuanit.movie.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FilmController extends BaseController {

    @Autowired
    FilmService filmService;

    @RequestMapping("/film/listFilm")
    public ResponseDTO listFilm(){
        List<Film> films = filmService.filmList();
        return ResponseDTO.success(films);
    }

    @RequestMapping("/film/{filmId}")
    public ResponseDTO loadFilmInfo(@PathVariable int filmId){
        Film film = filmService.loadFilm(filmId);
        return ResponseDTO.success(film);
    }

    @RequestMapping("/film/actor/{filmId}")
    public ResponseDTO loadActorInfo(@PathVariable int filmId){
        Map<String, List<FilmInfo>> map = filmService.loadFilmAActor(filmId);
        return ResponseDTO.success(map);
    }

    @RequestMapping("/cinema/area")
    public ResponseDTO loadArea(){
        List<Area> areas = filmService.listArea();
        return ResponseDTO.success(areas);
    }

    @RequestMapping("/cinema/listFilm4Cinema/{filmId}/{areaCode}")
    public ResponseDTO listFilm4Cinema(@PathVariable int filmId,@PathVariable String areaCode){
        List<Cinema> cinemas = filmService.listFilm4Cinema(filmId,areaCode);
        return ResponseDTO.success(cinemas);
    }

    @RequestMapping("/cinema/listCinema/{cinemaId}")
    public ResponseDTO listCinema(@PathVariable int cinemaId){
        Cinema cinemas = filmService.loadCinema(cinemaId);
        return ResponseDTO.success(cinemas);
    }

    @RequestMapping("/cinema/listCinemaFilm/{cinemaId}")
    public ResponseDTO listCinemaFilm(@PathVariable int cinemaId){
        List<Film> films = filmService.listCinemaFile(cinemaId);
        return ResponseDTO.success(films);
    }

    @RequestMapping(value = "/film/listShowTime/{cinemaId}/{filmId}", method = RequestMethod.GET)
    public ResponseDTO listShowTime(@PathVariable int cinemaId, @PathVariable int filmId) {
        return ResponseDTO.success(filmService.listShowTime(cinemaId, filmId));
    }

    @RequestMapping(value = "/film/loadScene/{sceneId}")
    public ResponseDTO loadScene(@PathVariable int sceneId){
        FilmSchedule filmSchedule = filmService.loadScene(sceneId);
        return ResponseDTO.success(filmSchedule);
    }

    @RequestMapping(value = "/film/loadAlreadyBuyTicket/{sceneId}")
    public ResponseDTO loadAlreadyBuyTicket(@PathVariable int sceneId){
        List<OrderInfo> orderInfos = filmService.listAlreadyBuyTicket(sceneId);
        return ResponseDTO.success(orderInfos);
    }
}
