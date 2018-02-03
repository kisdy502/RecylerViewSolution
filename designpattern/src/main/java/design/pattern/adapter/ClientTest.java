package design.pattern.adapter;

/**
 * Created by SDT13411 on 2018/1/25.
 */

public class ClientTest {

    public static void main(String[] args){

        MyCanvas_ClassAdapter painter=new MyCanvas_ClassAdapter();
        painter.drawColor();
        painter.drawBitmap();


        Win32Canvas win32Canvas1=new Win32Canvas();
        MyCanvas_ObjectAdapter painter1=new MyCanvas_ObjectAdapter(win32Canvas1);
        painter1.drawColor();
        painter1.drawBitmap();
    }
}
