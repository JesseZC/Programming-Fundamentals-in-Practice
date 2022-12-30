package models;

public class ProductivityApp extends App{

    public ProductivityApp(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        super(developer, appName, appSize, appVersion, appCost);
    }

    @Override
    public boolean isRecommendedApp() {
        return getAppCost()>=1.99 && calculateRating() >=3.0?true:false;
    }

    @Override
    public String toString() {
        return "ProcuctivityApp{} " + super.toString();
    }
}
