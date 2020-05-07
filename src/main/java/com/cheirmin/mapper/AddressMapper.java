package com.cheirmin.mapper;

import com.cheirmin.pojo.Address;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Filename
 * @auther
 * @data 2019/12/21 12:16;
 * @Descripion
 * @Version 1.1.1
 * @Function
 * @History
 */

public interface AddressMapper extends Mapper<Address> {

    @Update("update  tb_address set is_defulet=0")
    int updateDefaultTo0();
}
