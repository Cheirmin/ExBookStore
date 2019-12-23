package com.cheirmin.dao;

import com.cheirmin.pojo.Address;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Filename
 * @auther 吴星辰;
 * @data 2019/12/21 12:16;
 * @Descripion
 * @Version 1.1.1
 * @Function
 * @History
 */
public interface AddressMapper extends Mapper<Address> {

    @Update("update  address set is_defulet=0")
    int updateDefaultTo0();
}