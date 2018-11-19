package AspriseOCRP;


import java.io.File;

public class Test {

    public static void main(String[] args) {
        File imageFile = new File("D:\\picture\\");
        //验证码保存地址
        String downAddress = "D:\\picture\\";
        //验证码下载地址

        String downURL = "http://localhost:8888/Internet/imgcode/dd925e723c74435abd2050072a7fbb41.jpg";

        OrcS aa = new OrcS();


      // aa.downloadPic(downURL,downAddress);


        if (imageFile.listFiles().length < 400) {
            for (int i = 1; i <= 1; i++) {
                aa.downloadPic(downURL, downAddress + i + ".gif");
                String result = aa.getCode(downAddress + "\\" + i + ".gif" );
                try {
                    Thread.sleep(10 + (i % 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        aa.delAllFile("D:\\picture\\");
        System.out.println("deleted");

    }

}
