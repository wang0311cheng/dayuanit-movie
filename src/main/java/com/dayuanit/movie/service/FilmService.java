package com.dayuanit.movie.service;


import com.dayuanit.movie.entry.*;
import com.dayuanit.movie.exception.BizException;
import com.dayuanit.movie.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilmService {

    @Value("${film.picture.host}")
    private String picUrl;

    @Autowired
    private FilmMapper filmMapper;
    @Autowired
    private FilmInfoMapper filmInfoMapper;
    @Autowired
    private CinemaMapper cinemaMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private FilmScheduleMapper filmScheduleMapper;
    @Autowired
    private OrderInfoMapper orderInfoMapper;



    public List<Film> filmList(){
        List<Film> films = filmMapper.listFilm();
        return films;
    }

    public Film loadFilm(int filmId){
        Film film = filmMapper.selectByPrimaryKey(filmId);
//        film.setFilmPic("http://127.0.0.1:8080/picture" + film.getFilmPic());
        return film;
    }

    public Map<String,List<FilmInfo>> loadFilmAActor(int filmId){
        Film film = filmMapper.selectByPrimaryKey(filmId);
//        List<FilmInfo> filmInfos = filmInfoMapper.listFilmInfo(filmId, actorTypr);
        Map<String,List<FilmInfo>> map = new HashMap<>();
        map.put("dy",filmInfoMapper.listFilmInfo(film.getFilmCode(),0));
        map.put("yy",filmInfoMapper.listFilmInfo(film.getFilmCode(),1));
        return map;
    }

    public List<Area> listArea(){
        return areaMapper.listArea();
    }

    public List<Cinema> listFilm4Cinema(int filmId,String areaCode){
        Film film = filmMapper.selectByPrimaryKey(filmId);
        if (null == film) {
            throw new BizException("电影不存在");
        }
        List<Cinema> cinemas = cinemaMapper.listCinemaFilm(film.getFilmCode(),areaCode);
        return cinemas;
    }

    public Cinema loadCinema(int cinemaId){
        Cinema cinema = cinemaMapper.selectByPrimaryKey(cinemaId);
        cinema.setPicName(picUrl + cinema.getPicName());
        return cinema;
    }

    public List<Film> listCinemaFile(int cinemaId){
        Cinema cinema = cinemaMapper.selectByPrimaryKey(cinemaId);
        List<Film> films = filmMapper.listCinemaFilm(cinema.getCinemaCode());
        for (Film film : films){
            film.setFilmPic(picUrl + film.getFilmPic());
        }
        return films;
    }

    public List<FilmSchedule> listShowTime(int cinemaId,int filmId){

        Cinema cinema = cinemaMapper.selectByPrimaryKey(cinemaId);
        Film film = filmMapper.selectByPrimaryKey(filmId);
        if (null == cinema||film ==null){
            throw new BizException("影片已下架或者不存在");
        }
        List<FilmSchedule> filmSchedules = filmScheduleMapper.listShowTime(cinema.getCinemaCode(), film.getFilmCode());
        return filmSchedules;
    }

    public FilmSchedule loadScene(int sceneId){
        FilmSchedule filmSchedule = filmScheduleMapper.selectByPrimaryKey(sceneId);
        if (null==filmSchedule){
            throw new BizException("场次不正确");
        }
        return filmSchedule;
    }

    public Film getFilm(int filmId){
        return filmMapper.selectByPrimaryKey(filmId);
    }

    public List<OrderInfo> listAlreadyBuyTicket(int sceneId){
        List<OrderInfo> orderInfos = orderInfoMapper.listAlreadyBuyTicket(sceneId);
        return orderInfos;
    }
}
