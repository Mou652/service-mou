package com.mou.selenium;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class TestSetup {
    
    //定义 OpenCV 动态链接库的引用路径，修改成实际路径
    // private final static String OPENCV_DLL_PATH = "/Users/mou/Downloads/OpenCV_4_1_1/build/java/x64/opencv_java411.dll";
    //
    // static {
    //     //加载动态链接库
    //     System.load(OPENCV_DLL_PATH);
    // }

    public static void main(String[] args) {
        Mat mat = Imgcodecs.imread("/Users/mou/Downloads/test.jpg");
        ImageViewer imageViewer = new ImageViewer(mat);
        imageViewer.imshow();
    }
    
}