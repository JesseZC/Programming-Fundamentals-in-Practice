package models;

import java.util.Objects;

public class GameApp extends App{
    private boolean isMutiplayer = false;

    public GameApp(Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMutiplayer) {
        super(developer, appName, appSize, appVersion, appCost);
        this.isMutiplayer = isMutiplayer;
    }

    @Override
    public String appSummary() {
        return super.appSummary();
    }

    /**
     * 是否是多人游戏
     * @return
     */
    public boolean isMutiplayer(){
        return this.isMutiplayer;
    }

    @Override
    public boolean isRecommendedApp() {
        return this.isMutiplayer && calculateRating() >=4.0?true:false;
    }

    public void setMutiplayer(boolean mutiplayer) {
        isMutiplayer = mutiplayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameApp)) return false;
        if (!super.equals(o)) return false;
        GameApp gameApp = (GameApp) o;
        return isMutiplayer() == gameApp.isMutiplayer();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isMutiplayer());
    }

    @Override
    public String toString() {
        return "GameApp{" +
                "isMutiplayer=" + isMutiplayer +
                "} " + super.toString();
    }
}
