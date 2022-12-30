package models;

import utils.Utilities;

import java.util.Objects;

public class EducationApp extends App{
    /**
     * max:10,min:1
     */
    private int level = 0;

    public EducationApp(Developer developer, String appName, double appSize, double appVersion, double appCost, int level) {
        super(developer, appName, appSize, appVersion, appCost);
        // 验证level是否合法
        if(Utilities.validRange(level,1,10)){
            this.level = level;
        }
    }

    @Override
    public String appSummary() {
        return super.appSummary();
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean isRecommendedApp() {
        return getAppCost()>=0.99 && calculateRating() >=3.5 && this.level >=3?true:false;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "EducationApp{" +
                "level=" + level +
                "} " + super.toString();
    }
}
