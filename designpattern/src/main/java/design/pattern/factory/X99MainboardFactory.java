package design.pattern.factory;

import design.pattern.factory.bean.GraphicsCard;
import design.pattern.factory.bean.Mainboard;
import design.pattern.factory.bean.X99Mainboard;

/**
 * Created by SDT13411 on 2018/1/24.
 */

public class X99MainboardFactory implements IMainboardFactory {

    @Override
    public Mainboard makeMainboard() {
        return new X99Mainboard();
    }
}
