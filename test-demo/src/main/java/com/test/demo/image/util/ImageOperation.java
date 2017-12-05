package com.test.demo.image.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class ImageOperation {
    
    public static final String diskPath = "D:/TEMP/IMAGE/";     // 图片临时文件路径  
    
    public static final Map<String, Color> colorMap = new HashMap<String,Color>();  
    public static final Map<String, String> tipsMap = new HashMap<String,String>();  
    static{  
        colorMap.put("00", Color.BLACK);  
        colorMap.put("01", Color.RED);  
        colorMap.put("02", Color.YELLOW);  
        colorMap.put("03", Color.BLUE);  
        tipsMap.put("00", "选择验证码");  
        tipsMap.put("01", "选择红色验证码");  
        tipsMap.put("02", "选择黄色验证码");  
        tipsMap.put("03", "选择蓝色验证码");  
    }  
      
      
    public static void main(String[] args) throws Exception {  
        long startTime = System.nanoTime();
        createFolder(diskPath);  
        String imageBase64 = "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";  
        String yzmImagePath = encode64Image(imageBase64);
        String tipsImagePath = generateImageTips(tipsMap.get("01"),colorMap.get("01"));  
        String composeImagePath = composeImage(tipsImagePath, yzmImagePath);
        deleteFile(new String[]{yzmImagePath,tipsImagePath});  
        System.out.println("共消耗"+((System.nanoTime()-startTime)/1000000000)+"秒");  
    }  
      
    // 生成文件路径  
    public static boolean createFolder(String folderPath){  
        if(StringUtils.isBlank(folderPath)){  
            return false;  
        }  
        File file = new File(folderPath);  
        if(!file.exists()){  
            file.mkdirs();  
        }  
        return true;  
    }  
      
      
      
    // 解码base64图片，并保存  
    public static String encode64Image(String imageBase64){  
          
        if(StringUtils.isBlank(imageBase64)){  
            return null;  
        }  
        String fileName = getRandomString(15);  
        byte[] decode = Base64.decodeBase64(imageBase64);  
        int length = decode.length;  
        for(int i=0;i<length;++i){  
            if(decode[i] < 0){  
                decode[i] += 256;  
            }  
        }  
        OutputStream out = null;  
        try {  
            out = new FileOutputStream(new File(diskPath+fileName));  
            try {  
                out.write(decode);  
                out.flush();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return diskPath+fileName;  
          
    }  
      
    // 生成提示文字图片  
    public static String generateImageTips(String tips,Color color) throws Exception{  
        int width = 90;       
        int height = 35;       
        String fileName = getRandomString(15);  
        File file = new File(diskPath+fileName);       
               
        Font font = new Font("Serif", Font.BOLD, 10);       
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);       
        Graphics2D g2 = (Graphics2D)bi.getGraphics();       
        g2.setBackground(Color.WHITE);       
        g2.clearRect(0, 0, width, height);       
        g2.setPaint(color);       
        FontRenderContext context = g2.getFontRenderContext();       
        Rectangle2D bounds = font.getStringBounds(tips, context);  
        double x = (width - bounds.getWidth()) / 2;       
        double y = (height - bounds.getHeight()) / 2;       
        double ascent = -bounds.getY();       
        double baseY = y + ascent;       
               
        g2.drawString(tips, (int)x, (int)baseY);       
        ImageIO.write(bi, "png", file);    
          
        return diskPath+fileName;  
    }  
      
    // 合成图片  
    public static String composeImage(String img1Path,String img2Path){  
        try {      
            //第一张图片        
            File  fileOne  =  new  File(img1Path);        
            BufferedImage  imageOne = ImageIO.read(fileOne);        
            int  width  =  imageOne.getWidth();      
            int  height  =  imageOne.getHeight();         
            int[]  imageArrayOne  =  new  int[width*height];        
            imageArrayOne  =  imageOne.getRGB(0,0,width,height,imageArrayOne,0,width);        
           
            //第二张图片     
            File  fileTwo  =  new  File(img2Path);        
            BufferedImage  imageTwo  =  ImageIO.read(fileTwo);     
            int width2 = imageTwo.getWidth();    
            int height2 = imageTwo.getHeight();    
            int[]   ImageArrayTwo  =  new  int[width2*height2];        
            ImageArrayTwo  =  imageTwo.getRGB(0,0,width,height,ImageArrayTwo,0,width);           
           
            //新图片     
            BufferedImage  imageNew  =  new  BufferedImage(width,height*2,BufferedImage.TYPE_INT_RGB);             
            imageNew.setRGB(0,0,width,height,imageArrayOne,0,width);        
            imageNew.setRGB(0,height,width,height,ImageArrayTwo,0,width);  
               
            String fileName = getRandomString(15);  
              
            File  outFile  =  new  File(diskPath+fileName+".png");        
            ImageIO.write(imageNew,  "png",  outFile);  
              
            return diskPath+fileName+".png";  
        } catch (Exception e) {      
            e.printStackTrace();      
        }     
        return null;  
    }  
      
    // 删除无用的图片  
    public static boolean deleteFile(String[] paths){  
        if(paths == null || paths.length == 0){  
            return false;  
        }  
        File file = null;  
        for(String p:paths){  
            file = new File(p);  
            file.deleteOnExit();  
        }  
        return true;  
    }  
      
    // 生成图片临时文件名  
    public static String getRandomString(int length) { //length表示生成字符串的长度  
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";  
        Random random = new Random();   
        StringBuffer sb = new StringBuffer();     
        for (int i = 0; i < length; i++) {     
            int number = random.nextInt(base.length());     
            sb.append(base.charAt(number));     
        }     
        return sb.toString();    
     }     

}

