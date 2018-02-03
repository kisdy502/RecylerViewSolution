package design.pattern.factory.bean;

/**
 * Created by SDT13411 on 2018/1/24.
 */

public class GTX1060GraphicsCard implements GraphicsCard {

    @Override
    public String getModel() {
        return "GTX1060";
    }

    @Override
    public int getBitWide() {
        return 192;
    }
}
