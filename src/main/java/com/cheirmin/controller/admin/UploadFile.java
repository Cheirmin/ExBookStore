package com.cheirmin.controller.admin;

import com.cheirmin.controller.enums.UploadFileTypeEnum;
import org.apache.commons.io.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Message:文件上传控制器
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:10
 * @Version 1.0
 */

@Controller
@RequestMapping("/admin")
public class UploadFile {

    @RequestMapping("upload")
    @ResponseBody
    public ResponseEntity<List<String>> upload(@RequestParam("file") MultipartFile file) throws Exception {
        List<String> url=new ArrayList<>();
//        设置文件要存的路径
        String aStatic = this.getClass().getClassLoader().getResource("static").getFile();
          String path=aStatic+File.separator+"load"+File.separator;
//        路径可能会乱码，利用乱码解析器
            String decode = URLDecoder.decode(aStatic, "UTF-8");
//           获取文件的尾缀
            String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
//            重新设置文件的名字
             Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmss");
            Random random=new Random();
            int i = random.nextInt(10000);
            String format = sdf.format(date);
            String imgname="";
            if ("jpg".equals(substring.toLowerCase())){
                imgname=format+i+".jpg";
            }else if ("png".equals(substring.toLowerCase())){
                imgname=format+i+".png";
            }else if ("jpeg".equals(substring.toLowerCase())){
                imgname=format+i+".jpeg";
            }else if ("gif".equals(substring.toLowerCase())){
                imgname=format+i+".gif";
            }else {
                url.add("图片格式错误");
                return ResponseEntity.status(404).body(url);
            }

//            解析文件的内容
//        通过获取文件输入流
        InputStream inputStream = file.getInputStream();
//            利用图片 Io流读取图片
        BufferedImage image= ImageIO.read(inputStream);
        if (image==null){
            url.add("图片有误！");
            return ResponseEntity.status(404).body(url);
        }

//判断文件是否存在，不存在就创建文件
        if (!new File(path,imgname).getParentFile().exists()){
            new File(path,imgname).getParentFile().mkdirs();
        }

//        写入文件
        file.transferTo(new File(path,imgname));
        url.add("/load/"+imgname);
        return ResponseEntity.ok(url);
    }


    @RequestMapping("/file")
    @ResponseBody
    public ResponseEntity<String> upExccle(@RequestParam("file") MultipartFile file, HttpServletRequest request){


        String type=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1,file.getOriginalFilename().length());
        String fileName = null;
        //*******
        UploadFileTypeEnum uploadFileTypeEnum = UploadFileTypeEnum.getFileEnumByType(type);
        if (uploadFileTypeEnum == UploadFileTypeEnum.ERROR_TYPE) {
            //格式错误则不允许上传，直接返回错误提示
            return ResponseEntity.status(500).body("格式错误");
        } else {
            //生成文件名称通用方法
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmss");
            Random rd=new Random();
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(sdf.format(new Date())).append(rd.nextInt(100)).append(".").append(type);
            fileName=stringBuilder.toString();
        }
        try {
            String aStatic = URLDecoder.decode(this.getClass().getClassLoader().getResource("static").getFile(), "utf-8");
            String dir=aStatic+File.separator+"upload"+File.separator;; //将虚拟路径转化为实际路径并在定位的子目录当中
            //
            FileUtils.writeByteArrayToFile(new File(dir, fileName), file.getBytes());
            return ResponseEntity.ok("/upload/"+fileName);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("文件上传失败");
        }

    }

}
