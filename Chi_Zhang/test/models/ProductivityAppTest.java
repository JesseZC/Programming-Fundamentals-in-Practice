package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductivityAppTest {

    private ProductivityApp productivityAppBelowBoundary, productivityAppOnBoundary, productivityAppAboveBoundary, productivityAppInvalidData;
    private Developer developerLego = new Developer("Lego", "www.lego.com");
    private Developer developerSphero = new Developer("Sphero", "www.sphero.com");

    // TODO Nothing!  This class is complete
    
    @BeforeEach
    void setUp() {
        //Validation: appSize(1-1000), appVersion(>=1.0), ageRating (0-18), appCost(>=0)
        productivityAppBelowBoundary = new ProductivityApp(developerLego, "WeDo", 1, 1.0, 0);
        productivityAppOnBoundary = new ProductivityApp(developerLego, "Spike", 1000, 2.0, 1.99);
        productivityAppAboveBoundary = new ProductivityApp(developerLego, "EV3", 1001, 3.5, 2.99);
        productivityAppInvalidData = new ProductivityApp(developerLego, "", -1, 0, -1.00);
    }

    @AfterEach
    void tearDown() {
        productivityAppBelowBoundary = productivityAppOnBoundary = productivityAppAboveBoundary = productivityAppInvalidData = null;
        developerLego = developerSphero = null;
    }

    @Nested
    class Getters {

        @Test
        void getDeveloper() {
            assertEquals(developerLego, productivityAppBelowBoundary.getDeveloper());
            assertEquals(developerLego, productivityAppOnBoundary.getDeveloper());
            assertEquals(developerLego, productivityAppAboveBoundary.getDeveloper());
            assertEquals(developerLego, productivityAppInvalidData.getDeveloper());
        }

        @Test
        void getAppName() {
            assertEquals("WeDo", productivityAppBelowBoundary.getAppName());
            assertEquals("Spike", productivityAppOnBoundary.getAppName());
            assertEquals("EV3", productivityAppAboveBoundary.getAppName());
            assertEquals("", productivityAppInvalidData.getAppName());
        }

        @Test
        void getAppSize() {
            assertEquals(1, productivityAppBelowBoundary.getAppSize());
            assertEquals(1000, productivityAppOnBoundary.getAppSize());
            assertEquals(0, productivityAppAboveBoundary.getAppSize());
            assertEquals(0, productivityAppInvalidData.getAppSize());
        }

        @Test
        void getAppVersion() {
            assertEquals(1.0, productivityAppBelowBoundary.getAppVersion());
            assertEquals(2.0, productivityAppOnBoundary.getAppVersion());
            assertEquals(3.5, productivityAppAboveBoundary.getAppVersion());
            assertEquals(1.0, productivityAppInvalidData.getAppVersion());
        }

        @Test
        void getAppCost() {
            assertEquals(0, productivityAppBelowBoundary.getAppCost());
            assertEquals(1.99, productivityAppOnBoundary.getAppCost());
            assertEquals(2.99, productivityAppAboveBoundary.getAppCost());
            assertEquals(0, productivityAppInvalidData.getAppCost());
        }
    }

    @Nested
    class Setters {

        @Test
        void setDeveloper() {
            //no validation in models
            assertEquals(developerLego, productivityAppBelowBoundary.getDeveloper());
            productivityAppBelowBoundary.setDeveloper(developerSphero);
            assertEquals(developerSphero, productivityAppBelowBoundary.getDeveloper());
        }

        @Test
        void setAppName() {
            //no validation in models
            assertEquals("WeDo", productivityAppBelowBoundary.getAppName());
            productivityAppBelowBoundary.setAppName("Mindstorms");
            assertEquals("Mindstorms", productivityAppBelowBoundary.getAppName());
        }

        @Test
        void setAppSize() {
            //Validation: appSize(1-1000)
            assertEquals(1, productivityAppBelowBoundary.getAppSize());

            productivityAppBelowBoundary.setAppSize(1000);
            assertEquals(1000, productivityAppBelowBoundary.getAppSize()); //update

            productivityAppBelowBoundary.setAppSize(1001);
            assertEquals(1000, productivityAppBelowBoundary.getAppSize()); //no update

            productivityAppBelowBoundary.setAppSize(2);
            assertEquals(2, productivityAppBelowBoundary.getAppSize()); //update

            productivityAppBelowBoundary.setAppSize(0);
            assertEquals(2, productivityAppBelowBoundary.getAppSize()); //no update
        }

        @Test
        void setAppVersion() {
            //Validation: appVersion(>=1.0)
            assertEquals(1.0, productivityAppBelowBoundary.getAppVersion());

            productivityAppBelowBoundary.setAppVersion(2.0);
            assertEquals(2.0, productivityAppBelowBoundary.getAppVersion()); //update

            productivityAppBelowBoundary.setAppVersion(0.0);
            assertEquals(2.0, productivityAppBelowBoundary.getAppVersion()); //no update

            productivityAppBelowBoundary.setAppVersion(1.0);
            assertEquals(1.0, productivityAppBelowBoundary.getAppVersion()); //update
        }

        @Test
        void setAppCost() {
            //Validation: appCost(>=0)
            assertEquals(0.0, productivityAppBelowBoundary.getAppCost());

            productivityAppBelowBoundary.setAppCost(1.0);
            assertEquals(1.0, productivityAppBelowBoundary.getAppCost()); //update

            productivityAppBelowBoundary.setAppCost(-1);
            assertEquals(1.0, productivityAppBelowBoundary.getAppCost()); //no update

            productivityAppBelowBoundary.setAppCost(0.0);
            assertEquals(0.0, productivityAppBelowBoundary.getAppCost()); //update
        }
    }

    @Nested
    class ObjectStateMethods {

        @Test
        void appSummaryReturnsCorrectString() {
            ProductivityApp productivityApp = setupProductivityAppWithRating(3, 4);
            String stringContents = productivityApp.appSummary();

            assertTrue(stringContents.contains(productivityApp.getAppName() + "(V" + productivityApp.getAppVersion()));
            // assertTrue(stringContents.contains(productivityApp.getDeveloper().toString()));
            assertTrue(stringContents.contains("€" + productivityApp.getAppCost()));
            assertTrue(stringContents.contains("Rating: " + productivityApp.calculateRating()));
        }

        @Test
        void toStringReturnsCorrectString() {
            ProductivityApp productivityApp = setupProductivityAppWithRating(3, 4);
            String stringContents = productivityApp.toString();

            assertTrue(stringContents.contains(productivityApp.getAppName()));
            // assertTrue(stringContents.contains("(Version " + productivityApp.getAppVersion()));
            assertTrue(stringContents.contains(productivityApp.getDeveloper().toString()));
            assertTrue(stringContents.contains(productivityApp.getAppSize() + "MB"));
            assertTrue(stringContents.contains("Cost: " + productivityApp.getAppCost()));
            assertTrue(stringContents.contains("Ratings (" + productivityApp.calculateRating()));

            //contains list of ratings too
            assertTrue(stringContents.contains("John Doe"));
            assertTrue(stringContents.contains("Very Good"));
            assertTrue(stringContents.contains("Jane Doe"));
            assertTrue(stringContents.contains("Excellent"));
        }

    }

    @Nested
    class RecommendedApp {

        @Test
        void appIsNotRecommendedWhenInAppCostLessThan3c() {
            ProductivityApp productivityApp = setupProductivityAppWithRating(3, 5);

            productivityApp.setAppCost(1);
            assertFalse(productivityApp.isRecommendedApp());
        }

        @Test
        void appIsNotRecommendedWhenRatingIsLessThan3() {
            ProductivityApp productivityApp = setupProductivityAppWithRating(1, 2);

            productivityApp.setAppCost(3);
            assertFalse(productivityApp.isRecommendedApp());
        }
    }

    ProductivityApp setupProductivityAppWithRating(int rating1, int rating2) {
        //setting all conditions to true
        ProductivityApp productivityApp = new ProductivityApp(developerLego, "WeDo", 1,
                1.0, 2.0);
        productivityApp.addRating(new Rating(rating1, "John Doe", "Very Good"));
        productivityApp.addRating(new Rating(rating2, "Jane Doe", "Excellent"));

        //verifying all conditions are true for a recommended productivity app]
        assertEquals(2, productivityApp.getRatings().size());  //two ratings are added
        assertEquals(2.0, productivityApp.getAppCost(), 0.01);
        assertEquals(((rating1 + rating2) / 2.0), productivityApp.calculateRating(), 0.01);

        return productivityApp;
    }
}
