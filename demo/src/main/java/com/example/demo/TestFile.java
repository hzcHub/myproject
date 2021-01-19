package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TestFile {


    private static final int copySize = 100;
    public static void main(String[] args) {

        File file1 = new File("C:\\Users\\Administrator\\Desktop\\a.txt");
        File file2 = new File("C:\\Users\\Administrator\\Desktop\\b.txt");

        if(!file1.exists()){
            System.out.println("文件不存在");
            return;
        }

        try {
            RandomAccessFile r = new RandomAccessFile(file1,"r");
            RandomAccessFile rw = new RandomAccessFile(file2,"rw");

            long length = r.length();
            rw.setLength(length);
            int bord = (int) (length/copySize);
            bord = length%copySize == 0 ? bord:bord+1 ;

            for (int i = 0; i < bord; i++) {
                new MyThread(file1,file2,i*copySize*1024).start();

            }

            r.seek(0);
            rw.seek(0);

            byte[] by = new byte[1024];
            int len = 0;
            int maxSize = 0;
            while ((len = r.read(by)) != -1 && maxSize<copySize){
                rw.write(by,0,len);
                System.out.println("by = " + new String(by,"utf-8"));

                maxSize ++ ;
            }

            r.close();
            rw.close();
            Thread.sleep(3000);

            System.out.println("maxSize = " + maxSize);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}


class MyThread extends Thread{
    private RandomAccessFile rafR;
    private RandomAccessFile rafRW;

    private long startPoint;

    public MyThread(File fr,File frw,long startPoint){

        try {
            this.rafR = new RandomAccessFile(fr,"r");
            this.rafRW = new RandomAccessFile(fr,"rw");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.startPoint = startPoint;
    }


    @Override
    public void run() {
        try {
            this.rafR.seek(startPoint);
            this.rafR.seek(startPoint);
            byte[] by = new byte[1024];
            int len = 0 ;
            int maxSize = 0 ;
            while ((len = rafR.read(by))!=-1 && maxSize < 100){

                rafRW.write(by,0,len);
                maxSize ++;
            }



        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                rafR.close();
                rafRW.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
