package com.cheirmin.mapper;

import com.cheirmin.pojo.IndexCarousel;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface IndexCarouselMapper extends Mapper<IndexCarousel>, IdListMapper<IndexCarousel,Long> {

  }
