package design.pattern.factory;

import design.pattern.factory.bean.B85Mainboard;
import design.pattern.factory.bean.Mainboard;
import design.pattern.factory.bean.X99Mainboard;
import design.pattern.factory.bean.Z97MainBoard;

/**
 * 普通工厂
 * Created by SDT13411 on 2018/1/24.
 */

public class NormalFactory {

    public Mainboard makeMainboard(String model) {
        if ("X99".equalsIgnoreCase(model)) {
            return new X99Mainboard();
        } else if ("Z97".equalsIgnoreCase(model)) {
            return new Z97MainBoard();
        } else if ("B85".equalsIgnoreCase(model)) {
            return new B85Mainboard();
        }else{
            throw new NullPointerException("UNKOWN MODEL");
        }

    }
}
