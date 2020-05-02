package com.cheirmin.service.impl;

import com.cheirmin.common.Constants;
import com.cheirmin.common.ServiceResultEnum;
import com.cheirmin.vo.ShoppingCartItemVO;
import com.cheirmin.dao.BookMapper;
import com.cheirmin.dao.ShoppingCartItemMapper;
import com.cheirmin.pojo.Book;
import com.cheirmin.pojo.ShoppingCartItem;
import com.cheirmin.pojo.User;
import com.cheirmin.service.ShoppingCartService;
import com.cheirmin.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public String saveCartItem(ShoppingCartItem shoppingCartItem){
        Example example = new Example(ShoppingCartItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",shoppingCartItem.getUserId());
        criteria.andEqualTo("bookId",shoppingCartItem.getBookId());
        ShoppingCartItem cartItem = shoppingCartItemMapper.selectOneByExample(example);

        if (cartItem != null){
            //如果本商品有了
            cartItem.setBookCount(shoppingCartItem.getBookCount());
            return updateCartItem(cartItem);
        }
        Book book = bookMapper.selectByPrimaryKey(shoppingCartItem.getBookId());
        if (book == null){
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = shoppingCartItemMapper.selectCountByExample(example);
        //如果超出单个物品购买数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER){
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //保存数据
        if (shoppingCartItemMapper.insertSelective(shoppingCartItem) > 0){
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCartItem(ShoppingCartItem shoppingCartItem) {
        ShoppingCartItem cartItem = shoppingCartItemMapper.selectByPrimaryKey(shoppingCartItem.getCartItemId());
        if (cartItem == null){
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        //超出最大数量
        if (shoppingCartItem.getBookCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER){
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        cartItem.setBookCount(shoppingCartItem.getBookCount());
        cartItem.setUpdateTime(new Date());

        //保存记录
        if (shoppingCartItemMapper.updateByPrimaryKeySelective(cartItem) > 0){
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public ShoppingCartItem getCartItemByCartItemId(Long cartItemId) {
        return shoppingCartItemMapper.selectByPrimaryKey(cartItemId);
    }

    @Override
    public Boolean deleteByCartItemId(Long cartItemId) {
        return shoppingCartItemMapper.deleteByPrimaryKey(cartItemId) > 0;
    }

    @Override
    public List<ShoppingCartItemVO> getMyShoppingCartItems(Long userId) {
        List<ShoppingCartItemVO> shoppingCartItemVOS = new ArrayList<>();
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        List<ShoppingCartItem> cartItems = shoppingCartItemMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(cartItems)){
            //查询书籍信息并做数据转换
            List<Long> booksIds = cartItems.stream().map(ShoppingCartItem::getBookId).collect(Collectors.toList());
            Example example1 = new Example(Book.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andIn("bookId",booksIds);
            List<Book> books = bookMapper.selectByExample(example1);
            Map<Long,Book> bookMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(books)){
                bookMap = books.stream().collect(Collectors.toMap(Book::getBookId, Function.identity(),(entity1,entity2) -> entity1));
            }
            for (ShoppingCartItem shoppingCartItem : cartItems){
                ShoppingCartItemVO shoppingCartItemVO = new ShoppingCartItemVO();
                BeanUtil.copyProperties(shoppingCartItem,shoppingCartItemVO);
                if (bookMap.containsKey(shoppingCartItem.getBookId())){
                    Book bookTemp = bookMap.get(shoppingCartItem.getBookId());
                    shoppingCartItemVO.setBookCoverImg(bookTemp.getBookCoverImg());
                    String booksName = bookTemp.getBookName();
                    //字符串过长导致文字超出的问题
                    if (booksName.length() > 28){
                        booksName = booksName.substring(0,28) + "...";
                    }
                    shoppingCartItemVO.setBookName(booksName);
                    shoppingCartItemVO.setSellingPrice(bookTemp.getSellingPrice());
                    shoppingCartItemVOS.add(shoppingCartItemVO);
                }
            }
        }
        return shoppingCartItemVOS;
    }

//    @Autowired
//    private ShoppingCartItemMapper shoppingCartItemMapper;

//    @Override
//    public String putCart(Model model,Integer bookCount, Long bookId, HttpSession session){
//        HashMap<String ,Object> map = new HashMap<>();
//        map.put("userId",getUserId(session));
//        map.put("bookId",3);
//        map.put("bookCount",3);
//        List<Map<String, Object>> list = shoppingCartItemMapper.isPutCart(map);
//        if (list.size() > 0){
//            shoppingCartItemMapper.updateCart(map);
//        }else {
//            shoppingCartItemMapper.putCart(map);
//        }
//        return "forward:/shop-cart/selectcart";
//    }
//
//    @Override
//    public String selectCart(Model model,HttpSession session){
//        List<Map<String, Object>> list = shoppingCartItemMapper.selectCart(getUserId(session));
//        long totalPrice = 0;
//        for (Map<String ,Object> map : list){
//            totalPrice = totalPrice + (Long) map.get("totalprice");
//        }
//        model.addAttribute("totalPrice",totalPrice);
//        model.addAttribute("cartlist",list);
//        System.out.println(list.toString());
//        return "store/cart";
//    }
//
//    @Override
//    public String deleteBooks(Long bookId, HttpSession session){
//        HashMap<String ,Object> map = new HashMap<>();
//        map.put("userId",getUserId(session));
//        map.put("bookId",bookId);
//        shoppingCartItemMapper.deleteBooks(map);
//        return "forward:/shop-cart/selectcart";
//    }
//
//    @Override
//    public String clear(HttpSession session) {
//        shoppingCartItemMapper.clear(getUserId(session));
//        return "/shop-cart/selectcart";
//    }
//
//    @Override
//    public String orderConfirm(Model model,HttpSession session) {
//            List<Map<String, Object>> list = shoppingCartItemMapper.selectCart(getUserId(session));
//            long totalPrice = 0;
//            for (Map<String ,Object> map : list){
//                totalPrice = totalPrice + (Long) map.get("totalprice");
//            }
//            model.addAttribute("totalPrice",totalPrice);
//            model.addAttribute("cartlist",list);
//            System.out.println(list);
//            return "/shop-cart/settle";
//    }
//
//    public static Long getUserId(HttpSession session){
//        UserVO userVO = (UserVO) session.getAttribute(Constants.USER_SESSION_KEY);
//        return userVO.getUserId();
//    }




}
