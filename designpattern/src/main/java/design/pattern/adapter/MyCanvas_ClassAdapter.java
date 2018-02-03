package design.pattern.adapter;

/**
 * 类适配器 继承无能修改的类，实现目标接口
 * Created by SDT13411 on 2018/1/25.
 */

public class MyCanvas_ClassAdapter extends Win32Canvas implements IPainter {
    @Override
    public void drawColor() {
            System.out.println("画颜色");
    }

}
