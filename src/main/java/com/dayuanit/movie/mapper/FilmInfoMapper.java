package com.dayuanit.movie.mapper;

import com.dayuanit.movie.entry.FilmInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilmInfoMapper {

    int insert(FilmInfo record);

    int insertSelective(FilmInfo record);

    List<FilmInfo> listFilmInfo(@Param("filmCode") String filmCode, @Param("actorType") Integer actorType);
}