package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.*;
import utils.ScannerInput;
import utils.Utilities;

public class Driver {

    //TODO Some skeleton code has been given to you.
    //     Familiarise yourself with the skeleton code...run the menu and then review the skeleton code.
    //     Then start working through the spec.

    private DeveloperAPI developerAPI = new DeveloperAPI();
    private AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                //case 2 -> //  run the App Store Menu and the associated methods (your design here)
                case 2 -> runAppMenu();
                //case 3 -> // TODO run the Reports Menu and the associated methods (your design here)
                case  3 -> runReportsMenu();
                case 4 -> searchAppsBySpecificCriteria();
                //case 5 -> // TODO Sort Apps by Name
                //case 6 -> // TODO print the recommended apps
                //case 7 -> // TODO print the random app of the day
                case 8 -> simulateRatings();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private void exitApp() {
        saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //--------------------------------------------------
    //  Developer Management - Menu Items
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runDeveloperMenu() {
        int option = developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addDeveloper();
                case 2 -> System.out.println(developerAPI.listDevelopers());
                case 3 -> updateDeveloper();
                case 4 -> deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(developerAPI.listDevelopers());
        Developer developer = readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite))
                System.out.println("Developer Website Updated");
            else
                System.out.println("Developer Website NOT Updated");
        } else
            System.out.println("Developer name is NOT valid");
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            return developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }

    //--------------------------------------------------
    //  App Management - Menu Items
    //--------------------------------------------------
    private int appMenu() {
        System.out.println("""
                 -------App Menu-------
                |   1) Add an app      |
                |   2) List app        |
                |   3) Update app      |
                |   4) Delete app      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runAppMenu() {
        int option = appMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addApp();
                case 2 -> System.out.println(appStoreAPI.listAllApps());
                case 3 -> updateApp();
                case 4 -> deleteApp();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appMenu();
        }
    }

    private int appTypeMenu() {
        // app类型
        System.out.println("""
                 ------App Type Menu------
                |   1) Game app            |
                |   2) Education app       |
                |   3) Productivity app    |
                |   0) RETURN to App menu  |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void addApp() {
        int option = appTypeMenu();
        App app = null;

        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String appName = ScannerInput.validNextLine("Please enter the app name: ");
        double appSize = ScannerInput.validNextDouble("Please enter the app size: ");
        double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
        double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");

        if (option == 1) {
            char isMutipalyer;
            // 输入是否为多人游戏,如果输入错误，则重新输入
            while (true) {
                isMutipalyer = ScannerInput.validNextChar("Please enter the isMutipalyer: ");
                if (isMutipalyer == 'Y' || isMutipalyer == 'y' || isMutipalyer == 'n' || isMutipalyer == 'N') {
                    break;
                } else {
                    continue;
                }
            }
            // 将字符转换成boolean
            boolean yNtoBoolean = Utilities.YNtoBoolean(isMutipalyer);

            app = new GameApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, yNtoBoolean);
        } else if (option == 2) {
            int level = 0;
            while (true) {
                level = ScannerInput.validNextInt("Please enter the level: ");
                if (Utilities.validRange(level, 1, 10)) {
                    break;
                } else {
                    continue;
                }
            }
            app = new EducationApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, level);
        } else if (option == 3) {
            app = new ProductivityApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost);
        } else {
            System.out.println("Error");
            return;
        }

        // 添加app
        if (appStoreAPI.addApp(app)) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }

    }

    private void updateApp() {
        System.out.println(appStoreAPI.listAllApps());
        // 根据app名称获取app
        App app = readValidAppByName();
        if (app != null) {
            String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
            String appName = ScannerInput.validNextLine("Please enter the app name: ");
            double appSize = ScannerInput.validNextDouble("Please enter the app size: ");
            double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
            double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");

            app.setDeveloper(developerAPI.getDeveloperByName(developerName));
            app.setAppName(appName);
            app.setAppSize(appSize);
            app.setAppVersion(appVersion);
            app.setAppCost(appCost);

            System.out.println("App Updated");
        } else{
            System.out.println("app name is NOT valid");
        }
    }

    private void deleteApp() {
        System.out.println(appStoreAPI.listAllApps());
        int index = ScannerInput.validNextInt("Please enter the app index: ");
        App app = appStoreAPI.deleteAppByIndex(index);
        if (app != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private App readValidAppByName() {
        String appName = ScannerInput.validNextLine("Please enter the app name: ");
        return appStoreAPI.getAppByName(appName);
    }

    //--------------------------------------------------
    //  Reports Management - Menu Items
    //--------------------------------------------------
    private int reportsMenu() {
        System.out.println("""
                 -------Reports Menu-------
                |   1) Apps Overview         |
                |   2) Developers Overview   |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runReportsMenu() {
        int option = reportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> System.out.println(appStoreAPI.listAllApps());
                case 2 -> System.out.println(developerAPI.listDevelopers());
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = reportsMenu();
        }
    }

    //--------------------------------------------------
    // TODO UNCOMMENT THIS CODE as you start working through this class
    //--------------------------------------------------
    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search apps by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            // TODO Search methods below
            // case 1 -> searchAppsByName();
            // case 2 -> searchAppsByDeveloper(readValidDeveloperByName());
            // case 3 -> searchAppsEqualOrAboveAStarRating();
            // default -> System.out.println("Invalid option");
        }
    }

    //--------------------------------------------------
    // TODO UNCOMMENT THIS COMPLETED CODE as you start working through this class
    //--------------------------------------------------
    private void simulateRatings() {
        // simulate random ratings for all apps (to give data for recommended apps and reports etc).
        if (appStoreAPI.numberOfApps() > 0) {
           System.out.println("Simulating ratings...");
           appStoreAPI.simulateRatings();
           System.out.println(appStoreAPI.listSummaryOfAllApps());
        } else {
           System.out.println("No apps");
        }
    }

    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------

    private void saveAllData() {
        try {
            // TODO try-catch to save the developers to XML file
            developerAPI.save();
            // TODO try-catch to save the apps in the store to XML file
            appStoreAPI.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllData() {
        try {
            // TODO try-catch to load the developers from XML file
            developerAPI.load();
            // TODO try-catch to load the apps in the store from XML file
            appStoreAPI.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
