package RecommendationCreation;

import java.util.ArrayList;
import java.util.List;

public class DiningData {

    public static class DiningScenario {
        public String scenarioName;
        // Basic Information
        public String title;
        public String description;
        public String location;
        public String city;
        public String rating;
        public String leadTime;
        public String price;
        // Contact Details
        public String contactEmail;
        public String contactPhone;
        public String contactPerson;
        // Dining Details
        public String diningDescription;
        public String restaurantGroup;
        public String diningIncludes;
        public String features;
        public String metaData;
        public String dressCode;
        public String cuisineType;
        // Operating Hours
        public String openingTime;
        public String closingTime;
        // Time Slots (multiple: Breakfast, Lunch, Dinner)
        public String[] mealTypes;
        public String[] slotTimes;
        public String[] slotGuests;
        // Policies
        public String reservationPolicy;
        public String policyName;
        public String[] timeBefores;
        public String[] timeUnits;
        public String[] refundPercentages;
        public String agePolicy;
        // Additional Information
        public String mapEmbed;
        public String direction;
        public String termsAndConditions;
        // Options
        public boolean kidFriendly;
        public boolean petFriendly;

        public DiningScenario(String scenarioName) {
            this.scenarioName = scenarioName;
            this.title = "";
            this.description = "";
            this.location = "";
            this.city = "";
            this.rating = "";
            this.leadTime = "";
            this.price = "";
            this.contactEmail = "";
            this.contactPhone = "";
            this.contactPerson = "";
            this.diningDescription = "";
            this.restaurantGroup = "";
            this.diningIncludes = "";
            this.features = "";
            this.metaData = "";
            this.dressCode = "";
            this.cuisineType = "";
            this.openingTime = "";
            this.closingTime = "";
            this.mealTypes = new String[] {};
            this.slotTimes = new String[] {};
            this.slotGuests = new String[] {};
            this.reservationPolicy = "";
            this.policyName = "";
            this.timeBefores = new String[] {};
            this.timeUnits = new String[] {};
            this.refundPercentages = new String[] {};
            this.agePolicy = "";
            this.mapEmbed = "";
            this.direction = "";
            this.termsAndConditions = "";
            this.kidFriendly = false;
            this.petFriendly = false;
        }
    }

    private static DiningScenario getPositiveBase() {
        DiningScenario s = new DiningScenario("Base Positive");
        // Basic Information
        s.title = "La Maison Fine Dining";
        s.description = "An exquisite fine dining experience in the heart of Dubai.";
        s.location = "Downtown Dubai, Sheikh Zayed Road";
        s.city = "Dubai";
        s.rating = "5";
        s.leadTime = "2";
        s.price = "350";
        // Contact Details
        s.contactEmail = "reservations@lamaison.com";
        s.contactPhone = "9876543210";
        s.contactPerson = "Ahmed Hassan";
        // Dining Details
        s.diningDescription = "Premium multi-course dining with seasonal menus crafted by Michelin-starred chefs.";
        s.restaurantGroup = "Emaar Hospitality Group";
        s.diningIncludes = "Appetizer, Main course, Dessert";
        s.features = "live music, outdoor seating, valet parking";
        s.metaData = "fine dining, luxury, rooftop";
        s.dressCode = "Smart Casual";
        s.cuisineType = "Mediterranean";
        // Operating Hours
        s.openingTime = "12:00";
        s.closingTime = "23:00";
        // Time Slots (Breakfast, Lunch, Dinner)
        s.mealTypes = new String[] { "breakfast", "lunch", "dinner" };
        s.slotTimes = new String[] { "08:00", "12:30", "19:00" };
        s.slotGuests = new String[] { "2", "4", "6" };
        // Policies
        s.reservationPolicy = "Reservations required 24 hours in advance. Walk-ins subject to availability.";
        s.policyName = "Standard";
        s.timeBefores = new String[] { "3", "2", "1" };
        s.timeUnits = new String[] { "Minutes", "Hours", "Days" };
        s.refundPercentages = new String[] { "20", "40", "100" };
        s.agePolicy = "Children under 12 must be accompanied by an adult.";
        // Additional Information
        s.mapEmbed = "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1107029.2201364434!2d54.568041327437584!3d25.0745656650172!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3e5f43496ad9c645%3A0xbde66e5084295162!2sDubai%20-%20United%20Arab%20Emirates!5e1!3m2!1sen!2sin!4v1769755543337!5m2!1sen!2sin\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>";
        s.direction = "Located on the 60th floor of Address Downtown. Valet parking available at main entrance.";
        s.termsAndConditions = "Smart casual dress code enforced. No outside food or beverages allowed.";
        // Options
        s.kidFriendly = true;
        s.petFriendly = false;
        return s;
    }

    private static DiningScenario copy(DiningScenario base, String newName) {
        DiningScenario c = new DiningScenario(newName);
        c.title = base.title;
        c.description = base.description;
        c.location = base.location;
        c.city = base.city;
        c.rating = base.rating;
        c.leadTime = base.leadTime;
        c.price = base.price;
        c.contactEmail = base.contactEmail;
        c.contactPhone = base.contactPhone;
        c.contactPerson = base.contactPerson;
        c.diningDescription = base.diningDescription;
        c.restaurantGroup = base.restaurantGroup;
        c.diningIncludes = base.diningIncludes;
        c.features = base.features;
        c.metaData = base.metaData;
        c.dressCode = base.dressCode;
        c.cuisineType = base.cuisineType;
        c.openingTime = base.openingTime;
        c.closingTime = base.closingTime;
        c.mealTypes = base.mealTypes;
        c.slotTimes = base.slotTimes;
        c.slotGuests = base.slotGuests;
        c.reservationPolicy = base.reservationPolicy;
        c.policyName = base.policyName;
        c.timeBefores = base.timeBefores;
        c.timeUnits = base.timeUnits;
        c.refundPercentages = base.refundPercentages;
        c.agePolicy = base.agePolicy;
        c.mapEmbed = base.mapEmbed;
        c.direction = base.direction;
        c.termsAndConditions = base.termsAndConditions;
        c.kidFriendly = base.kidFriendly;
        c.petFriendly = base.petFriendly;
        return c;
    }

    public static List<DiningScenario> getScenarios() {
        List<DiningScenario> scenarios = new ArrayList<>();
        DiningScenario base = getPositiveBase();

        // 1. Positive Scenario
        scenarios.add(copy(base, "Positive Case: Valid Data"));

        // 2. Negative: Title
        String[] negTitles = { "", "<script>alert('XSS')</script>", "A".repeat(500) };
        for (String val : negTitles) {
            DiningScenario s = copy(base,
                    "Neg Title: " + (val.isEmpty() ? "Empty" : val.length() > 20 ? "Long Text" : val));
            s.title = val;
            scenarios.add(s);
        }

        // 3. Negative: Rating
        String[] negRatings = { "", "-1", "ABC", "10" };
        for (String val : negRatings) {
            DiningScenario s = copy(base, "Neg Rating: " + (val.isEmpty() ? "Empty" : val));
            s.rating = val;
            scenarios.add(s);
        }

        // 4. Negative: City
        String[] negCities = { "", "@#$%^" };
        for (String val : negCities) {
            DiningScenario s = copy(base, "Neg City: " + (val.isEmpty() ? "Empty" : "Special Chars"));
            s.city = val;
            scenarios.add(s);
        }

        // 5. Negative: Price
        String[] negPrices = { "", "-100", "Free" };
        for (String val : negPrices) {
            DiningScenario s = copy(base, "Neg Price: " + (val.isEmpty() ? "Empty" : val));
            s.price = val;
            scenarios.add(s);
        }

        // 6. Negative: Contact Email
        String[] negEmails = { "", "invalid-email", "test@@domain.com" };
        for (String val : negEmails) {
            DiningScenario s = copy(base, "Neg Email: " + (val.isEmpty() ? "Empty" : val));
            s.contactEmail = val;
            scenarios.add(s);
        }

        return scenarios;
    }
}
