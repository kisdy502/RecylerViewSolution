package design.pattern.factory;

import design.pattern.factory.IMainboardFactory;
import design.pattern.factory.bean.Mainboard;
import design.pattern.factory.bean.Z97MainBoard;

/**
 * Created by SDT13411 on 2018/1/24.
 */

public class Z97MainboardFactory implements IMainboardFactory {
    @Override
    public Mainboard makeMainboard() {
        return new Z97MainBoard();
    }
}
