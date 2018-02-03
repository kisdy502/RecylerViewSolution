package design.pattern.adapter;

/**
 * 对象适配器
 * 感觉和代理模式很有点像，不过代理模式的Win32Canvas对象是在
 * MyCanvas_ObjectAdapter 构造函数时new出来的，而不是将一个已经new好的
 * Win32Canvas对象作为参数传递进来
 * Created by SDT13411 on 2018/1/25.
 */

public class MyCanvas_ObjectAdapter implements IPainter {

    private Win32Canvas win32Canvas;

    public MyCanvas_ObjectAdapter(Win32Canvas win32Canvas) {
        this.win32Canvas = win32Canvas;
    }

    @Override
    public void drawColor() {
        System.out.println("画颜色");
    }

    public void drawBitmap() {
        win32Canvas.drawBitmap();
    }

}
