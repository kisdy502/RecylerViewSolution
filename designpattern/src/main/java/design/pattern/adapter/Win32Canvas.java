package design.pattern.adapter;

/**
 * 第三方，系统提供的类，可以画图片
 * 我们无法修改这个类
 * Created by SDT13411 on 2018/1/25.
 */

public class Win32Canvas {

    public void drawBitmap(){
        System.out.println("画一幅图片");
    }

}
