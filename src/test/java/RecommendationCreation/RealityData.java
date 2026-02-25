package RecommendationCreation;

import java.util.ArrayList;
import java.util.List;

public class RealityData {

    public static class RealityScenario {
        public String scenarioName;
        // Basic Information
        public String title;
        public String city;
        public String location;
        public String zoneName;
        public String rating;
        public String makaniNumber;
        public String leadTime;
        public String description;
        // Property Details
        public String propertyType; // top-level Property Type dropdown
        public String realtyDescription;
        // Buying Options (Option 1)
        public String bedrooms;
        public String bathrooms;
        public String areaSqFt;
        public String dldNumber;
        public String dldPermitNumber;
        public String propertyRefNo;
        public String optionPropertyType; // Property Type dropdown inside buying option
        public boolean furnished;
        public String views;
        // Features & Metadata
        public String features;
        public String metadata;
        public String realtyIncludes;
        // Contact Information
        public String contactEmail;
        public String contactPhone;
        public String contactPerson;
        // Location & Directions
        public String mapEmbed;
        public String directions;
        public String videoUrl;
        // Terms
        public String termsAndConditions;

        public RealityScenario(String scenarioName) {
            this.scenarioName = scenarioName;
            this.title = "";
            this.city = "";
            this.location = "";
            this.zoneName = "";
            this.rating = "";
            this.makaniNumber = "";
            this.leadTime = "";
            this.description = "";
            this.propertyType = "";
            this.realtyDescription = "";
            this.bedrooms = "";
            this.bathrooms = "";
            this.areaSqFt = "";
            this.dldNumber = "";
            this.dldPermitNumber = "";
            this.propertyRefNo = "";
            this.optionPropertyType = "";
            this.furnished = false;
            this.views = "";
            this.features = "";
            this.metadata = "";
            this.realtyIncludes = "";
            this.contactEmail = "";
            this.contactPhone = "";
            this.contactPerson = "";
            this.mapEmbed = "";
            this.directions = "";
            this.videoUrl = "";
            this.termsAndConditions = "";
        }
    }

    private static RealityScenario getPositiveBase() {
        RealityScenario s = new RealityScenario("Base Positive");
        // Basic Information
        s.title = "Luxury Penthouse in Dubai Marina";
        s.city = "Dubai";
        s.location = "Dubai Marina";
        s.zoneName = "Dubai Marina";
        s.rating = "5";
        s.makaniNumber = "PROP-DXB-2024-001";
        s.leadTime = "2";
        s.description = "Stunning luxury penthouse offering panoramic views of the marina and Arabian Gulf.";
        // Property Details
        s.propertyType = "Apartment";
        s.realtyDescription = "A premium 3-bedroom penthouse with private pool, smart home automation, and 24/7 concierge service located in the heart of Dubai Marina.";
        // Buying Options
        s.bedrooms = "3";
        s.bathrooms = "4";
        s.areaSqFt = "3500";
        s.dldNumber = "12345";
        s.dldPermitNumber = "DLD-PERMIT-1234567";
        s.propertyRefNo = "REF-MAR-2024-001";
        s.optionPropertyType = "Apartment";
        s.furnished = true;
        s.views = "sea, city skyline";
        // Features & Metadata
        s.features = "private pool, gym, concierge";
        s.metadata = "waterfront, luxury, marina view";
        s.realtyIncludes = "Parking, Swimming Pool, Gym";
        // Contact Information
        s.contactEmail = "sales@dubaimarina-realty.com";
        s.contactPhone = "9876543210";
        s.contactPerson = "Omar Al Rashid";
        // Location & Directions
        s.mapEmbed = "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1107029.2201364434!2d54.568041327437584!3d25.0745656650172!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3e5f43496ad9c645%3A0xbde66e5084295162!2sDubai%20-%20United%20Arab%20Emirates!5e1!3m2!1sen!2sin!4v1769755543337!5m2!1sen!2sin\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\"></iframe>";
        s.directions = "Located in Cluster A, Tower 5, Floor 50. Take the main elevator from the lobby.";
        s.videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        // Terms
        s.termsAndConditions = "All buyers must complete KYC verification. 10% deposit required at booking. Transfer fees apply as per DLD regulations.";
        return s;
    }

    private static RealityScenario copy(RealityScenario base, String newName) {
        RealityScenario c = new RealityScenario(newName);
        c.title = base.title;
        c.city = base.city;
        c.location = base.location;
        c.zoneName = base.zoneName;
        c.rating = base.rating;
        c.makaniNumber = base.makaniNumber;
        c.leadTime = base.leadTime;
        c.description = base.description;
        c.propertyType = base.propertyType;
        c.realtyDescription = base.realtyDescription;
        c.bedrooms = base.bedrooms;
        c.bathrooms = base.bathrooms;
        c.areaSqFt = base.areaSqFt;
        c.dldNumber = base.dldNumber;
        c.dldPermitNumber = base.dldPermitNumber;
        c.propertyRefNo = base.propertyRefNo;
        c.optionPropertyType = base.optionPropertyType;
        c.furnished = base.furnished;
        c.views = base.views;
        c.features = base.features;
        c.metadata = base.metadata;
        c.realtyIncludes = base.realtyIncludes;
        c.contactEmail = base.contactEmail;
        c.contactPhone = base.contactPhone;
        c.contactPerson = base.contactPerson;
        c.mapEmbed = base.mapEmbed;
        c.directions = base.directions;
        c.videoUrl = base.videoUrl;
        c.termsAndConditions = base.termsAndConditions;
        return c;
    }

    public static List<RealityScenario> getScenarios() {
        List<RealityScenario> scenarios = new ArrayList<>();
        RealityScenario base = getPositiveBase();

        // 1. Positive Scenario
        scenarios.add(copy(base, "Positive Case: Valid Data"));

        // 2. Negative: Title
        String[] negTitles = { "", "<script>alert('XSS')</script>", "A".repeat(500) };
        for (String val : negTitles) {
            RealityScenario s = copy(base,
                    "Neg Title: " + (val.isEmpty() ? "Empty" : val.length() > 20 ? "Long Text" : val));
            s.title = val;
            scenarios.add(s);
        }

        // 3. Negative: Rating
        String[] negRatings = { "", "-1", "ABC", "10" };
        for (String val : negRatings) {
            RealityScenario s = copy(base, "Neg Rating: " + (val.isEmpty() ? "Empty" : val));
            s.rating = val;
            scenarios.add(s);
        }

        // 4. Negative: Zone Name
        String[] negZones = { "", "@#$%^" };
        for (String val : negZones) {
            RealityScenario s = copy(base, "Neg Zone: " + (val.isEmpty() ? "Empty" : "Special Chars"));
            s.zoneName = val;
            scenarios.add(s);
        }

        // 5. Negative: DLD Number
        String[] negDld = { "", "-100", "ABC" };
        for (String val : negDld) {
            RealityScenario s = copy(base, "Neg DLD: " + (val.isEmpty() ? "Empty" : val));
            s.dldNumber = val;
            scenarios.add(s);
        }

        // 6. Negative: Contact Email
        String[] negEmails = { "", "invalid-email", "test@@domain.com" };
        for (String val : negEmails) {
            RealityScenario s = copy(base, "Neg Email: " + (val.isEmpty() ? "Empty" : val));
            s.contactEmail = val;
            scenarios.add(s);
        }

        return scenarios;
    }
}
