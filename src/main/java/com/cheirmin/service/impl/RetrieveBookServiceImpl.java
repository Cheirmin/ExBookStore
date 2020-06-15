package com.cheirmin.service.impl;

import com.cheirmin.mapper.RetrieveBookMapper;
import com.cheirmin.pojo.RetrieveBook;
import com.cheirmin.service.RetrieveBookService;
import com.cheirmin.vo.ShoppingCartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: cheirmin/xiaoming.chen
 * @since: 2020/5/12 16:25
 * @history: 1.2020/5/12 created by cheirmin/xiaoming.chen
 */
@Service
public class RetrieveBookServiceImpl implements RetrieveBookService {
    @Autowired
    private RetrieveBookMapper retrieveBookMapper;

    @Override
    public Long addBook(RetrieveBook retrieveBook) {
        Example example = new Example(RetrieveBook.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bookIsbn",retrieveBook.getBookIsbn());

        RetrieveBook retrieveBook2 = retrieveBookMapper.selectOneByExample(example);

        //查询是否已经有了该ISBN
        if (retrieveBook2 !=null) {
            return retrieveBook2.getBookId();
        }else {
            int insert = retrieveBookMapper.insert(retrieveBook);
            if(insert >0){
                return retrieveBookMapper.selectOneByExample(example).getBookId();
            }else {
                return null;
            }
        }

    }

    @Override
    public List<ShoppingCartItemVO> getMyCartItems(Long userId) {
        return null;
    }
}
