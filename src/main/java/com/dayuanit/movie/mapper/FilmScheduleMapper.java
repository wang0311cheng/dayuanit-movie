package com.dayuanit.movie.mapper;

import com.dayuanit.movie.entry.FilmSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilmScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FilmSchedule record);

    int insertSelective(FilmSchedule record);

    FilmSchedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FilmSchedule record);

    int updateByPrimaryKey(FilmSchedule record);

    List<FilmSchedule> listShowTime(@Param("cinemaCode") String cinemaCode, @Param("filmCode")String filmCode);
}