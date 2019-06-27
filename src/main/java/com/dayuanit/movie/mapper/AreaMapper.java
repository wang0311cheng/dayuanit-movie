package com.dayuanit.movie.mapper;

import com.dayuanit.movie.entry.Area;
import com.dayuanit.movie.entry.Cinema;

import java.util.List;

public interface AreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);

    List<Area> listArea();
}