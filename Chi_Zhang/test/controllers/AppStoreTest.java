package controllers;

import models.Developer;
import models.ProductivityApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppStoreTest {

    private AppStoreAPI appStoreAPI;
    private DeveloperAPI developerAPI;

    @BeforeEach
    void setUp() {
        developerAPI = new DeveloperAPI();
        try {
            developerAPI.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        appStoreAPI = new AppStoreAPI();
        try {
            appStoreAPI.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addApp() {
        Developer rose = developerAPI.getDeveloperByName("rose");
        ProductivityApp productivityApp = new ProductivityApp(rose, "roseApp2", 1.0, 1.1, 1.2);
        System.out.println(appStoreAPI.addApp(productivityApp));
    }

    @Test
    void deleteAppByIndex() {
        System.out.println(appStoreAPI.deleteAppByIndex(0).toString());
    }

    @Test
    void getAppByIndex() {
        System.out.println(appStoreAPI.getAppByIndex(0).toString());
    }

    @Test
    void getAppByName() {
        System.out.println(appStoreAPI.getAppByName("roseApp").toString());
    }

    @Test
    void listAllApps() {
        System.out.println(appStoreAPI.listAllApps());
    }

    @Test
    void listSummaryOfAllApps() {
        System.out.println(appStoreAPI.listSummaryOfAllApps());
    }

    @Test
    void listAllGameApps() {
        System.out.println(appStoreAPI.listAllGameApps());
    }

    @Test
    void listAllEducationApps() {
        System.out.println(appStoreAPI.listAllEducationApps());
    }

    @Test
    void listAllProductivityApps() {
        System.out.println(appStoreAPI.listAllProductivityApps());
    }

    @Test
    void listAllAppsByName() {
        System.out.println(appStoreAPI.listAllAppsByName("roseApp"));
    }

    @Test
    void listAllAppsAboveOrEqualAGibvenStarRating() {
        System.out.println(appStoreAPI.listAllAppsAboveOrEqualAGibvenStarRating(3));
    }

    @Test
    void listAllRecommendedApps() {
        System.out.println(appStoreAPI.listAllRecommendedApps());
    }

    @Test
    void listAllAppsByChosenDeveloper() {
        Developer rose = developerAPI.getDeveloperByName("rose");
        System.out.println(appStoreAPI.listAllAppsByChosenDeveloper(rose).toString());
    }

    @Test
    void numberOfAppsByChosenDeveloper() {
        Developer rose = developerAPI.getDeveloperByName("rose");
        System.out.println(appStoreAPI.numberOfAppsByChosenDeveloper(rose));
    }

    @Test
    void randomApp() {
        System.out.println(appStoreAPI.randomApp().toString());
    }

    @Test
    void sortAppsByNameAscending() {
        appStoreAPI.sortAppsByNameAscending();
        System.out.println(appStoreAPI.listAllApps());
    }

    @Test
    void numberOfApps() {
        System.out.println(appStoreAPI.numberOfApps());
    }


    @AfterEach
    void tearDown() {
    }

}
