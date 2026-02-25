package RecommendationCreation;

import java.util.ArrayList;
import java.util.List;

public class TravelData {

    public static class TravelScenario {
        public String scenarioName;
        public String title;
        public String stars;
        public String city;
        public String location;
        public String leadTime;
        public String basePrice;
        public String description;
        public String travelDescription;
        public String contactPerson;
        public String contactPhone;
        public String contactEmail;
        public String hospitalityGroup;
        public String checkInTime;
        public String checkOutTime;
        public String tags;
        public String facilities;
        public String travelIncludes;
        // Room Information (matches actual form)
        public String roomType;
        public String roomPrice;
        public String roomMaxGuests;
        public String roomCount;
        public String roomSqFtArea;
        public String roomBeds;
        public String roomView;
        public String roomAmenities;
        // Additional Information
        public String mapEmbedUrl;
        public String direction;
        public String termsAndConditions;
        public String agePolicy;
        // Cancellation Policy (multiple rules: Minutes, Hours, Days)
        public String policyName;
        public String[] timeBefores;
        public String[] timeUnits;
        public String[] refundPercentages;
        // Meta
        public String metaData;

        public TravelScenario(String scenarioName) {
            this.scenarioName = scenarioName;
            this.title = "";
            this.stars = "";
            this.city = "";
            this.location = "";
            this.leadTime = "";
            this.basePrice = "";
            this.description = "";
            this.travelDescription = "";
            this.contactPerson = "";
            this.contactPhone = "";
            this.contactEmail = "";
            this.hospitalityGroup = "";
            this.checkInTime = "";
            this.checkOutTime = "";
            this.tags = "";
            this.facilities = "";
            this.travelIncludes = "";
            this.roomType = "";
            this.roomPrice = "";
            this.roomMaxGuests = "";
            this.roomCount = "";
            this.roomSqFtArea = "";
            this.roomBeds = "";
            this.roomView = "";
            this.roomAmenities = "";
            this.mapEmbedUrl = "";
            this.direction = "";
            this.termsAndConditions = "";
            this.agePolicy = "";
            this.policyName = "";
            this.timeBefores = new String[] {};
            this.timeUnits = new String[] {};
            this.refundPercentages = new String[] {};
            this.metaData = "";
        }
    }

    private static TravelScenario getPositiveBase() {
        TravelScenario s = new TravelScenario("Base Positive");
        s.title = "Luxury Dubai Resort Experience";
        s.stars = "5";
        s.city = "Dubai";
        s.location = "Palm Jumeirah";
        s.leadTime = "2";
        s.basePrice = "1500";
        s.description = "A premium stay in the heart of Dubai.";
        s.travelDescription = "Includes private airport transfers and breakfast.";
        s.contactPerson = "John Doe";
        s.contactPhone = "9876543210";
        s.contactEmail = "john@example.com";
        s.hospitalityGroup = "Marriott International";
        s.checkInTime = "11:00";
        s.checkOutTime = "14:00";
        s.tags = "luxury, dubai";
        s.facilities = "Pool, Gym, Spa";
        s.travelIncludes = "Breakfast, Airport pickup";
        // Room Information
        s.roomType = "Deluxe Room";
        s.roomPrice = "500";
        s.roomMaxGuests = "4";
        s.roomCount = "2";
        s.roomSqFtArea = "450";
        s.roomBeds = "2";
        s.roomView = "Sea view";
        s.roomAmenities = "Mini Bar, Balcony, WiFi";
        // Additional Information
        s.mapEmbedUrl = "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1107029.2201364434!2d54.568041327437584!3d25.0745656650172!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3e5f43496ad9c645%3A0xbde66e5084295162!2sDubai%20-%20United%20Arab%20Emirates!5e1!3m2!1sen!2sin!4v1769755543337!5m2!1sen!2sin\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>";
        s.direction = "Main road.";
        s.termsAndConditions = "ID required.";
        s.agePolicy = "Children free.";
        // Cancellation Policy (3 rules: Minutes, Hours, Days)
        s.policyName = "Flexible Travel Policy";
        s.timeBefores = new String[] { "3", "2", "1" };
        s.timeUnits = new String[] { "Minutes", "Hours", "Days" };
        s.refundPercentages = new String[] { "20", "40", "100" };
        // Meta
        s.metaData = "beachfront, luxury";
        return s;
    }

    private static TravelScenario copy(TravelScenario base, String newName) {
        TravelScenario c = new TravelScenario(newName);
        c.title = base.title;
        c.stars = base.stars;
        c.city = base.city;
        c.location = base.location;
        c.leadTime = base.leadTime;
        c.basePrice = base.basePrice;
        c.description = base.description;
        c.travelDescription = base.travelDescription;
        c.contactPerson = base.contactPerson;
        c.contactPhone = base.contactPhone;
        c.contactEmail = base.contactEmail;
        c.hospitalityGroup = base.hospitalityGroup;
        c.checkInTime = base.checkInTime;
        c.checkOutTime = base.checkOutTime;
        c.tags = base.tags;
        c.facilities = base.facilities;
        c.travelIncludes = base.travelIncludes;
        c.roomType = base.roomType;
        c.roomPrice = base.roomPrice;
        c.roomMaxGuests = base.roomMaxGuests;
        c.roomCount = base.roomCount;
        c.roomSqFtArea = base.roomSqFtArea;
        c.roomBeds = base.roomBeds;
        c.roomView = base.roomView;
        c.roomAmenities = base.roomAmenities;
        c.mapEmbedUrl = base.mapEmbedUrl;
        c.direction = base.direction;
        c.termsAndConditions = base.termsAndConditions;
        c.agePolicy = base.agePolicy;
        c.policyName = base.policyName;
        c.timeBefores = base.timeBefores;
        c.timeUnits = base.timeUnits;
        c.refundPercentages = base.refundPercentages;
        c.metaData = base.metaData;
        return c;
    }

    public static List<TravelScenario> getScenarios() {
        List<TravelScenario> scenarios = new ArrayList<>();
        TravelScenario base = getPositiveBase();

        // 1. FIRST: Positive Scenario
        scenarios.add(copy(base, "Positive Case: Valid Data"));

        // 2. Systematic Negative: TITLE FIELD
        String[] negTitles = { "", "<script>alert('XSS')</script>", "A".repeat(500) };
        for (String val : negTitles) {
            TravelScenario s = copy(base,
                    "Neg Title: " + (val.isEmpty() ? "Empty" : val.length() > 20 ? "Long Text" : val));
            s.title = val;
            scenarios.add(s);
        }

        // 3. Systematic Negative: STARS FIELD
        String[] negStars = { "", "-1", "ABC", "10" }; // Boundary: max is usually 5
        for (String val : negStars) {
            TravelScenario s = copy(base, "Neg Stars: " + (val.isEmpty() ? "Empty" : val));
            s.stars = val;
            scenarios.add(s);
        }

        // 4. Systematic Negative: CITY FIELD
        String[] negCities = { "", "@#$%^" };
        for (String val : negCities) {
            TravelScenario s = copy(base, "Neg City: " + (val.isEmpty() ? "Empty" : "Special Chars"));
            s.city = val;
            scenarios.add(s);
        }

        // 5. Systematic Negative: LEAD TIME FIELD
        String[] negLeadTimes = { "", "-5", "Invalid" };
        for (String val : negLeadTimes) {
            TravelScenario s = copy(base, "Neg LeadTime: " + (val.isEmpty() ? "Empty" : val));
            s.leadTime = val;
            scenarios.add(s);
        }

        // 6. Systematic Negative: BASE PRICE FIELD
        String[] negPrices = { "", "-100", "Free" };
        for (String val : negPrices) {
            TravelScenario s = copy(base, "Neg Price: " + (val.isEmpty() ? "Empty" : val));
            s.basePrice = val;
            scenarios.add(s);
        }

        // 7. Systematic Negative: CONTACT EMAIL FIELD
        String[] negEmails = { "", "invalid-email", "test@@domain.com" };
        for (String val : negEmails) {
            TravelScenario s = copy(base, "Neg Email: " + (val.isEmpty() ? "Empty" : val));
            s.contactEmail = val;
            scenarios.add(s);
        }

        return scenarios;
    }
}
