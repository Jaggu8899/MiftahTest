package RecommendationCreation;

import java.util.ArrayList;
import java.util.List;

public class CuratedData {

    public static class CuratedScenario {
        public String scenarioName;
        public String title;
        public String stars;
        public String durationHours;
        public String generalDescription;
        public String currency;
        public String location;
        public String city;
        public String contactEmail;
        public String contactPhone;
        public String contactPerson;
        public String mapEmbedUrl;
        public String directions;
        public String termsAndConditions;
        public String agePolicy;
        public String metaData;
        public String detailedDescription;
        public String date;
        public String minAge;
        public String durationMinutes;
        public String dressCode;
        public String startTime;
        public String totalGuests;
        public String slot2Time;
        public String slot2Guests;
        public String importantNotes;
        public String cancellationPolicy;
        public String experienceIncludes;
        public String basePrice;
        public String itemTitle;
        public String itemDescription;
        public String durationMaxMinutes;
        public String policyName;
        public String[] refundRuleTimes;
        public String[] refundRuleUnits;
        public String[] refundRulePercentages;
        public String provider;

        public CuratedScenario(String scenarioName) {
            this.scenarioName = scenarioName;
            this.title = "";
            this.stars = "";
            this.durationHours = "";
            this.generalDescription = "";
            this.currency = "AED";
            this.location = "";
            this.city = "";
            this.contactEmail = "";
            this.contactPhone = "";
            this.contactPerson = "";
            this.mapEmbedUrl = "";
            this.directions = "";
            this.termsAndConditions = "";
            this.agePolicy = "";
            this.metaData = "";
            this.detailedDescription = "";
            this.date = "";
            this.minAge = "";
            this.durationMinutes = "";
            this.dressCode = "";
            this.startTime = "";
            this.totalGuests = "";
            this.slot2Time = "";
            this.slot2Guests = "";
            this.importantNotes = "";
            this.cancellationPolicy = "";
            this.experienceIncludes = "";
            this.basePrice = "";
            this.itemTitle = "";
            this.itemDescription = "";
            this.durationMaxMinutes = "";
            this.policyName = "";
            this.refundRuleTimes = new String[] {};
            this.refundRuleUnits = new String[] {};
            this.refundRulePercentages = new String[] {};
            this.provider = "Jaggu";
        }
    }

    private static CuratedScenario getPositiveBase() {
        CuratedScenario s = new CuratedScenario("Base Positive");

        // Sequential field entry: Title to Description (in order of form entry)
        // 1. Title (First field to be entered)
        s.title = "Luxury Desert Safari";

        // 2. Description (General Description)
        s.generalDescription = "An exclusive desert experience.";

        // 3. Location
        s.location = "Dubai Desert Conservation Reserve";

        // 4. City
        s.city = "Dubai";

        // 5. Rating (Stars)
        s.stars = "5";

        // 6. Lead Time (Hours)
        s.durationHours = "6";

        // 7. Price (Base Price)
        s.basePrice = "450";

        // 8. Currency
        s.currency = "AED";

        // 9. Contact Email
        s.contactEmail = "safari@example.com";

        // 10. Contact Phone
        s.contactPhone = "9876543210";

        // 11. Contact Person
        s.contactPerson = "Ahmed Ali";

        // 12. Map Embed URL
        s.mapEmbedUrl = "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1107029.2201364434!2d54.568041327437584!3d25.0745656650172!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3e5f43496ad9c645%3A0xbde66e5084295162!2sDubai%20-%20United%20Arab%20Emirates!5e1!3m2!1sen!2sin!4v1769148737410!5m2!1sen!2sin\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>";

        // 13. Directions
        s.directions = "Take E66 towards desert.";

        // 14. Terms & Conditions
        s.termsAndConditions = "Refundable up to 24h.";

        // 15. Age Policy
        s.agePolicy = "Ages 6+";

        // 16. Meta Data
        s.metaData = "desert, safari, luxury";

        // 17. Experience Description (Detailed Description)
        s.detailedDescription = "Join us for a sunset dinner and dune bashing.";

        // 18. Date
        s.date = "20-02-2026";

        // 19. Min Age
        s.minAge = "6";

        // 20. Duration (Minutes)
        s.durationMinutes = "360";

        // 21. Dress Code
        s.dressCode = "Casual and sunglasses";

        // 22. Start Time
        s.startTime = "15:00";

        // 23. Total Guests
        s.totalGuests = "20";

        // 24. Slot 2 Time
        s.slot2Time = "17:00";

        // 25. Slot 2 Guests
        s.slot2Guests = "10";

        // 26. Important Notes
        s.importantNotes = "Wear comfortable clothes.";

        // 27. Cancellation Policy
        s.cancellationPolicy = "Full refund if cancelled before 48h.";

        // 28. Experience Includes
        s.experienceIncludes = "Dinner, Transport, Dune Bashing";

        // 29. Item Title (Description field ending section)
        s.itemTitle = "VIP Seating";

        // 30. Item Description (Final Description field)
        s.itemDescription = "Front row seats for the fire show.";

        // --- NEW FIELDS FOUND IN FORM ---
        // 31. Duration Max (Minutes)
        s.durationMaxMinutes = "480";

        // 32. Policy Name
        s.policyName = "Flexible Cancellation";

        // 33-35. Refund Rules (Minutes, Hours, Days)
        s.refundRuleTimes = new String[] { "3", "2", "1" };
        s.refundRuleUnits = new String[] { "Minutes", "Hours", "Days" };
        s.refundRulePercentages = new String[] { "20", "40", "100" };

        // 36. Assign Provider
        s.provider = "Jaggu";

        return s;
    }

    private static CuratedScenario copy(CuratedScenario base, String newName) {
        CuratedScenario c = new CuratedScenario(newName);

        // Copy all fields in sequential order (Title to Description)
        // Sequential fields: Title → Item Description (30 fields total)
        c.title = base.title;
        c.generalDescription = base.generalDescription;
        c.location = base.location;
        c.city = base.city;
        c.stars = base.stars;
        c.durationHours = base.durationHours;
        c.basePrice = base.basePrice;
        c.currency = base.currency;
        c.contactEmail = base.contactEmail;
        c.contactPhone = base.contactPhone;
        c.contactPerson = base.contactPerson;
        c.mapEmbedUrl = base.mapEmbedUrl;
        c.directions = base.directions;
        c.termsAndConditions = base.termsAndConditions;
        c.agePolicy = base.agePolicy;
        c.metaData = base.metaData;
        c.detailedDescription = base.detailedDescription;
        c.date = base.date;
        c.minAge = base.minAge;
        c.durationMinutes = base.durationMinutes;
        c.dressCode = base.dressCode;
        c.startTime = base.startTime;
        c.totalGuests = base.totalGuests;
        c.slot2Time = base.slot2Time;
        c.slot2Guests = base.slot2Guests;
        c.importantNotes = base.importantNotes;
        c.cancellationPolicy = base.cancellationPolicy;
        c.experienceIncludes = base.experienceIncludes;
        c.itemTitle = base.itemTitle;
        c.itemDescription = base.itemDescription;
        c.durationMaxMinutes = base.durationMaxMinutes;
        c.policyName = base.policyName;
        c.refundRuleTimes = base.refundRuleTimes;
        c.refundRuleUnits = base.refundRuleUnits;
        c.refundRulePercentages = base.refundRulePercentages;
        c.provider = base.provider;

        return c;
    }

    public static List<CuratedScenario> getScenarios() {
        List<CuratedScenario> scenarios = new ArrayList<>();
        CuratedScenario base = getPositiveBase();

        // 1. FIRST: Positive Scenario
        scenarios.add(copy(base, "Positive Case: Valid Data"));
        /*
         * // 2. Systematic Negative: TITLE FIELD
         * String[] negTitles = { "", "A".repeat(501) }; // Empty and Long text
         * for (String val : negTitles) {
         * CuratedScenario s = copy(base, "Neg Title: " + (val.isEmpty() ? "Empty" :
         * "Long Text"));
         * s.title = val;
         * scenarios.add(s);
         * }
         * 
         * // 3. Systematic Negative: STARS FIELD
         * String[] negStars = { "", "-1", "ABC", "10" };
         * for (String val : negStars) {
         * CuratedScenario s = copy(base, "Neg Stars: " + (val.isEmpty() ? "Empty" :
         * val));
         * s.stars = val;
         * scenarios.add(s);
         * }
         * 
         * // 4. Systematic Negative: DURATION HOURS FIELD
         * String[] negDurations = { "", "0", "-5", "Invalid" };
         * for (String val : negDurations) {
         * CuratedScenario s = copy(base, "Neg Duration: " + (val.isEmpty() ? "Empty" :
         * val));
         * s.durationHours = val;
         * scenarios.add(s);
         * }
         * 
         * // 5. Systematic Negative: CITY/LOCATION FIELD
         * String[] negLocations = { "" };
         * for (String val : negLocations) {
         * CuratedScenario s = copy(base, "Neg Location: Empty");
         * s.location = val;
         * s.city = val;
         * scenarios.add(s);
         * }
         * 
         * // 6. Systematic Negative: CONTACT EMAIL FIELD
         * String[] negEmails = { "", "invalid-email", "test@@domain.com" };
         * for (String val : negEmails) {
         * CuratedScenario s = copy(base, "Neg Email: " + (val.isEmpty() ? "Empty" :
         * val));
         * s.contactEmail = val;
         * scenarios.add(s);
         * }
         * 
         * // 7. Systematic Negative: BASE PRICE FIELD
         * String[] negPrices = { "", "-100", "Free" };
         * for (String val : negPrices) {
         * CuratedScenario s = copy(base, "Neg Price: " + (val.isEmpty() ? "Empty" :
         * val));
         * s.basePrice = val;
         * scenarios.add(s);
         * }
         */

        return scenarios;
    }
}
