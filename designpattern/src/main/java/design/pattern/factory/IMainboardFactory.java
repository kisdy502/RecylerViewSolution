package design.pattern.factory;

import design.pattern.factory.bean.GraphicsCard;
import design.pattern.factory.bean.Mainboard;
import design.pattern.factory.bean.Monitor;

/**
 * Created by SDT13411 on 2018/1/24.
 */

public interface IMainboardFactory {
    Mainboard makeMainboard();
}
