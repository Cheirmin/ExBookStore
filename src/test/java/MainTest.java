import com.cheirmin.pojo.Book;
import com.cheirmin.util.IsbnUtil;
import org.junit.Test;

import java.util.Scanner;


/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2020/3/2 23:03
 * @Version 1.0
 */
public class MainTest {
    @Test
    public void test01(){
        System.out.println(123);
    }

    @Test
    public void test02(){

    }

    @Test
    public void test03(){
        Book bookInfoByISBN = IsbnUtil.getBookInfoByISBN("9787500083528");

        System.out.println(bookInfoByISBN);
    }
}
