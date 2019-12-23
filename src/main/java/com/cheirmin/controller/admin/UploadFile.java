package com.cheirmin.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.annotation.MultipartConfig;
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

@Controller()
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
}
