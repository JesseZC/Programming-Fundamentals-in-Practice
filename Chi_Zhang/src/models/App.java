package models;

import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class App {
    private Developer developer;

    private String appName = "No app name";

    private double appSize = 0;

    private double appVersion = 0;

    private double appCost = 0;

    private List<Rating> ratings = new ArrayList<>();

    public App(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        this.developer = developer;
        this.appName = appName;
        if (Utilities.validRangeExclIncl(appSize, 0, Double.MAX_VALUE)) {
            this.appSize = appSize;
        } else {
            this.appSize = 0;
        }
        if (Utilities.validRangeExclIncl(appVersion, 0, Double.MAX_VALUE)) {
            this.appVersion = appVersion;
        } else {
            this.appVersion = 0;
        }
        if (Utilities.validRangeExclIncl(appCost, 0, Double.MAX_VALUE)) {
            this.appCost = appCost;
        } else {
            this.appCost = 0;
        }
    }

    public boolean addRating(Rating rating) {
        return ratings.add(rating);
    }

    /**
     * app摘要
     *
     * @return
     */
    public String appSummary() {
        return this.appName
                + "(V" + this.appVersion
                + ")by"
                + this.developer.getDeveloperName()
                + ",€"
                + this.appCost
                + ".Rating: "
                + this.calculateRating();
    }

    /**
     * 计算评分
     *
     * @return
     */
    public double calculateRating() {
        double result = 0;
        int count = 0;
        for (Rating rating : ratings) {
            if (rating.getNumberOfStars() != 0) {
                count++;
                result += rating.getNumberOfStars();
            }
        }

        return count != 0 ? result / count : 0;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public String getAppName() {
        return appName;
    }

    public double getAppSize() {
        return appSize;
    }

    public double getAppVersion() {
        return appVersion;
    }

    public double getAppCost() {
        return appCost;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    /**
     * 是否是推荐应用程序
     *
     * @return
     */
    public abstract boolean isRecommendedApp();

    /**
     * 返回评分的文本形式
     *
     * @return
     */
    public String listRatings() {
        int size = this.ratings.size();
        if (size == 0) {
            return "No ratings added yet";
        } else {
            return this.ratings.toString();
        }
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppSize(double appSize) {
        this.appSize = appSize;
    }

    public void setAppVersion(double appVersion) {
        this.appVersion = appVersion;
    }

    public void setAppCost(double appCost) {
        this.appCost = appCost;
    }

    @Override
    public String toString() {
        return "App{" +
                "developer=" + developer +
                ", appName='" + appName + '\'' +
                ", appSize=" + appSize + "MB" +
                ", appVersion=" + appVersion +
                ", appCost=" + appCost +
                '}';
    }
}
