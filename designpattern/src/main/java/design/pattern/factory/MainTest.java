package design.pattern.factory;

import design.pattern.factory.bean.Mainboard;

/**
 * Created by SDT13411 on 2018/1/24.
 */

public class MainTest {

    public static void main(String[] args) {
        //测试普通工厂
        System.out.println("---普通工厂---");
        Mainboard x99mainboard = new NormalFactory().makeMainboard("X99");
        System.out.println("主板型号:" + x99mainboard.getModel());

        Mainboard z97mainboard = new NormalFactory().makeMainboard("Z97");
        System.out.println("主板型号:" + z97mainboard.getModel());

        System.out.println("---抽象工厂---");

        IMainboardFactory x99MainboardFactory = new X99MainboardFactory();
        Mainboard x99Mainboard = x99MainboardFactory.makeMainboard();
        System.out.println("主板型号:" + x99Mainboard.getModel());


        IMainboardFactory z97MainboardFactory = new Z97MainboardFactory();
        Mainboard z97Mainboard = z97MainboardFactory.makeMainboard();
        System.out.println("主板型号:" + z97Mainboard.getModel());


        //假设添加了一款主板B85,怎么办
        //普通工厂 修改NormalFactory类

        //抽象工厂怎么办
        //添加一个类 B85MainboardFactory工厂类

        //所以说添加一个产品族很容易
        //Z97，X99,B85 一系列主板称为产品族


        //主板，显示器，显卡则称为产品等级结构

        //如果要扩展创建新的主板，抽象工厂很容易

        //如果要扩展创建一个显卡产品系列，抽象工厂就要修改很多地方了


        // 在抽象工厂中，一个工厂如果想生产主板，又生产显卡，就要改很多地方了


    }
}
