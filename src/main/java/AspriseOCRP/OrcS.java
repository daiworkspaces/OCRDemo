package AspriseOCRP;

import com.asprise.ocr.Ocr;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OrcS {


    //下载验证码
    public void downloadPic(String picUrl, String imgAddress) {

        try {
            URL url = new URL(picUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //需要设置头信息，否则会被识别为机器而获取不到验证码图片
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36");
            conn.connect();

            int result = -1;
            byte[] buf = new byte[1024];
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            FileOutputStream fos = new FileOutputStream(imgAddress);
            while ((result = bis.read(buf)) != -1) {
                fos.write(buf);
            }
            fos.flush();
            fos.close();
            bis.close();
            System.out.println("图片下载成功");

        } catch (MalformedURLException e) {
            System.out.println("图片读取失败");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println();
            e.printStackTrace();
        }

    }








    //图形验证码识别
    public  String getCode(String imageUrl) {

        Ocr.setUp(); // one time setup
        Ocr ocr = new Ocr(); // create a new OCR engine
        ocr.startEngine("eng",Ocr.SPEED_FASTEST); // English
        String s = ocr.recognize(new File[]{new File(imageUrl)}, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT);
        System.out.println("Result: "+s);
// ocr more images here ...
        ocr.stopEngine();
        return s;
    }

    //文件夹图片删除

    public  void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
//param path 文件夹完整绝对路径
    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

}
