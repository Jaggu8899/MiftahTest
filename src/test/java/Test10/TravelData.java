package Test10;

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
        public String inclusions;
        public String roomName;
        public String roomBasePrice;
        public String roomMaxOccupancy;
        public String roomMaxAdults;
        public String roomMaxChildren;
        public String roomMaxInfants;
        public String roomView;
        public String roomAmenities;
        public String mapEmbedUrl;
        public String direction;
        public String termsAndConditions;
        public String agePolicy;
        public String cancellationPolicy;
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
            this.inclusions = "";
            this.roomName = "";
            this.roomBasePrice = "";
            this.roomMaxOccupancy = "";
            this.roomMaxAdults = "";
            this.roomMaxChildren = "";
            this.roomMaxInfants = "";
            this.roomView = "";
            this.roomAmenities = "";
            this.mapEmbedUrl = "";
            this.direction = "";
            this.termsAndConditions = "";
            this.agePolicy = "";
            this.cancellationPolicy = "";
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
        s.inclusions = "Breakfast, Wifi";
        s.roomName = "Sea View Suite";
        s.roomBasePrice = "500";
        s.roomMaxOccupancy = "4";
        s.roomMaxAdults = "2";
        s.roomMaxChildren = "2";
        s.roomMaxInfants = "1";
        s.roomView = "Ocean Front";
        s.roomAmenities = "Mini Bar, Balcony";
        s.mapEmbedUrl = "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1107029.2201364434!2d54.568041327437584!3d25.0745656650172!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3e5f43496ad9c645%3A0xbde66e5084295162!2sDubai%20-%20United%20Arab%20Emirates!5e1!3m2!1sen!2sin!4v1769755543337!5m2!1sen!2sin\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>";
        s.direction = "Main road.";
        s.termsAndConditions = "ID required.";
        s.agePolicy = "Children free.";
        s.cancellationPolicy = "72h refund.";
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
        c.inclusions = base.inclusions;
        c.roomName = base.roomName;
        c.roomBasePrice = base.roomBasePrice;
        c.roomMaxOccupancy = base.roomMaxOccupancy;
        c.roomMaxAdults = base.roomMaxAdults;
        c.roomMaxChildren = base.roomMaxChildren;
        c.roomMaxInfants = base.roomMaxInfants;
        c.roomView = base.roomView;
        c.roomAmenities = base.roomAmenities;
        c.mapEmbedUrl = base.mapEmbedUrl;
        c.direction = base.direction;
        c.termsAndConditions = base.termsAndConditions;
        c.agePolicy = base.agePolicy;
        c.cancellationPolicy = base.cancellationPolicy;
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
