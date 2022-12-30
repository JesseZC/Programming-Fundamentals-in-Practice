package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.Collator;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

import static java.lang.Math.random;
import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI implements ISerializer{
    private List<App> apps = new ArrayList<>();

    //TODO refer to the spec and add in the required methods here (make note of which methods are given to you first!)

    //---------------------
    // Method to simulate ratings (using the RatingUtility).
    // This will be called from the Driver (see skeleton code)
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED method as you start working through this class
    //---------------------
    public void simulateRatings() {
        for (App app : apps) {
            app.addRating(generateRandomRating());
        }
    }

    //---------------------
    // Validation methods
    //---------------------
    // TODO UNCOMMENT THIS COMPlETED method as you start working through this class
    //---------------------
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }

    //---------------------
    // Persistence methods
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED CODE block as you start working through this class
    //---------------------
    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (List<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName() {
        return "apps.xml";
    }

    public boolean addApp(App app) {
        return this.apps.add(app);
    }

    public App deleteAppByIndex(int index) {
        if (Utilities.isValidIndex(this.apps, index)) {
            return apps.remove(index);
        }
        return null;
    }

    public App getAppByIndex(int index) {
        if (this.isValidIndex(index)) {
            return apps.get(index);
        }
        return null;
    }

    public App getAppByName(String name) {
        if (this.isValidAppName(name)) {
            for (App app : apps) {
                if (app.getAppName().equalsIgnoreCase(name)) {
                    return app;
                }
            }
        }
        return null;
    }

    /**
     * 验证apps列表中是否有app名称为name
     * @param name
     * @return
     */
    private boolean isValidAppName(String name) {
        if(name == null){
            return false;
        }

        for (App app : apps) {
            if(app.getAppName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public String listAllApps() {
        // 如果apps长度为0 则返回no apps
        if (apps.size() == 0) {
            return "No apps";
        } else {
            // 拼接字符串
            StringBuilder stringBuilder = new StringBuilder("");
            for (int i = 0; i < apps.size(); i++) {
                // 拼接应用信息
                stringBuilder.append(i + ":[" + apps.get(i).toString() + "]\n");
            }
            return stringBuilder.toString();
        }
    }

    public String listSummaryOfAllApps() {
        if (apps.size() == 0) {
            return "No apps";
        } else {
            StringBuilder stringBuilder = new StringBuilder("");
            for (int i = 0; i < apps.size(); i++) {
                // 拼接应用的摘要信息
                stringBuilder.append(i + ":[" + apps.get(i).appSummary().toString() + "]\n");
            }
            return stringBuilder.toString();
        }
    }

    public String listAllGameApps() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < apps.size(); i++) {
            // 判断该应用是否是游戏类应用
            if (apps.get(i) instanceof GameApp) {
                stringBuilder.append(i + ":[" + apps.get(i).toString() + "]\n");
            }
        }

        // 如果拼接的字符串的长度为0，则说明没有游戏类应用
        if (stringBuilder.length() == 0) {
            return "No Game apps";
        }
        return stringBuilder.toString();
    }

    public String listAllEducationApps() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < apps.size(); i++) {
            // 判断该应用是否是教育类应用
            if (apps.get(i) instanceof EducationApp) {
                stringBuilder.append(i + ":[" + apps.get(i).toString() + "]\n");
            }
        }

        // 如果拼接的字符串的长度为0，则说明没有教育类应用
        if (stringBuilder.length() == 0) {
            return "No Education apps";
        }
        return stringBuilder.toString();
    }

    public String listAllProductivityApps() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < apps.size(); i++) {
            // 判断该应用是否是生产力应用
            if (apps.get(i) instanceof EducationApp) {
                stringBuilder.append(i + ":[" + apps.get(i).toString() + "]\n");
            }
        }

        // 如果拼接的字符串的长度为0，则说明没有生产力应用
        if (stringBuilder.length() == 0) {
            return "No Productivity apps";
        }
        return stringBuilder.toString();
    }

    public String listAllAppsByName(String name) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getAppName().equalsIgnoreCase(name)) {
                stringBuilder.append(i + ":[" + apps.get(i).toString() + "]\n");
            }
        }

        if (stringBuilder.length() == 0) {
            return "No apps from name \"" + name + "\" exists";
        }
        return stringBuilder.toString();
    }

    public String listAllAppsAboveOrEqualAGibvenStarRating(int rating) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).calculateRating()>=rating) {
                stringBuilder.append(i + ":[" + apps.get(i).toString() + "]\n");
            }
        }

        if (stringBuilder.length() == 0) {
            return "No recommended apps";
        }
        return stringBuilder.toString();
    }

    public String listAllRecommendedApps(){
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).isRecommendedApp()) {
                stringBuilder.append(i + ":[" + apps.get(i).toString() + "]\n");
            }
        }

        if (stringBuilder.length() == 0) {
            return "No recommended apps";
        }
        return stringBuilder.toString();
    }

    public String listAllAppsByChosenDeveloper(Developer developer){
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getDeveloper().equals(developer)) {
                stringBuilder.append(i + ":[" + apps.get(i).toString() + "]\n");
            }
        }

        if (stringBuilder.length() == 0) {
            return "No apps for developer:"+developer;
        }
        return stringBuilder.toString();
    }

    public int numberOfAppsByChosenDeveloper(Developer developer){
        int count = 0;
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getDeveloper().equals(developer)) {
                count++;
            }
        }

        return count;
    }

    public App randomApp(){
        if (apps.size() == 0){
            return null;
        }

        int randomNumber = new Random().nextInt(apps.size());
        return apps.get(randomNumber);
    }

    public void sortAppsByNameAscending(){
        // 冒泡排序
        for (int i = 0; i < apps.size(); i++) {
            for (int j = i+1; j < apps.size(); j++) {
                if(apps.get(i).getAppName().compareTo(apps.get(j).getAppName()) < 0){
                    this.swapApps(apps,i,j);
                }
            }
        }
    }

    private void swapApps(List<App> apps,int i,int j){
        App tmp = apps.get(i);
        apps.set(i,apps.get(j));
        apps.set(j,tmp);
    }


    public int numberOfApps() {
        return apps.size();
    }
}
