package com.cheirmin.dao;

import com.cheirmin.pojo.IndexCarousel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.idlist.IdListProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IndexCarouselMapper extends Mapper<IndexCarousel>, IdListMapper<IndexCarousel,Long> {

  }