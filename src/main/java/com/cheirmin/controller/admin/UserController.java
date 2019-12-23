package com.cheirmin.controller.admin;

import com.cheirmin.controller.vo.UserVO;
import com.cheirmin.pojo.User;
import com.cheirmin.service.UserService;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.Result;
import com.cheirmin.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:10
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/users")
    public String personalPage(HttpServletRequest request,
                               HttpSession httpSession) {

        return "admin/exbookstore_user";
    }



    @RequestMapping("/users/list")
    @ResponseBody
    public Result<List<User>> getlist(@RequestParam Map<String, Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }

        PageQueryUtil pageUtil = new PageQueryUtil(params);


        return ResultGenerator.genSuccessResult(userService.getCategorisPage(pageUtil));
    }


    @RequestMapping("/users/lock/{lockStatus}")
    @ResponseBody
    public Result setlock(@PathVariable("lockStatus") Integer lockStatus,@RequestBody List<Integer> ids){
        if (lockStatus==null || ids==null) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        System.out.println(lockStatus+" "+ids);

     return userService.setlock(lockStatus,ids);
    }

}
