package com.dayuanit.movie.mapper;

import com.dayuanit.movie.entry.Cinema;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CinemaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cinema record);

    int insertSelective(Cinema record);

    Cinema selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cinema record);

    int updateByPrimaryKey(Cinema record);

    List<Cinema> listCinemaFilm(@Param("filmCode") String filmCode, @Param("areaCode") String areaCode);

}