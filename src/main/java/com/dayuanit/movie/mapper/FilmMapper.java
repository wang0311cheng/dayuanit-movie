package com.dayuanit.movie.mapper;

import com.dayuanit.movie.entry.Film;
import com.dayuanit.movie.entry.FilmInfo;

import java.util.List;

public interface FilmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Film record);

    int insertSelective(Film record);

    Film selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Film record);

    int updateByPrimaryKeyWithBLOBs(Film record);

    int updateByPrimaryKey(Film record);

    List<Film> listFilm();

    List<Film> listCinemaFilm(String cinemaCode);

}