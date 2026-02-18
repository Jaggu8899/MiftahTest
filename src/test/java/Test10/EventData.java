package Test10;

import java.util.ArrayList;
import java.util.List;

public class EventData {

    public static class EventScenario {
        public String scenarioName;
        public String title;
        public String stars;
        public String description;
        public String location;
        public String city;
        public String contactEmail;
        public String contactPhone;
        public String contactPerson;
        public String mapEmbedUrl;
        public String directions;
        public String termsAndConditions;
        public String agePolicy;
        public String cancellationPolicy;
        public String leadTime;
        public String metaData;
        public String experienceIncludes;
        public String eventDescription;
        public String eventDate; // DD-MM-YYYY
        public String startTime; // HH:mm
        public String ticketName;
        public String category;
        public String doorsOpen; // HH:mm
        public String totalCapacity;
        public String lastBookingDate; // DDMMYYYYHHmm (raw for robust entry)
        public String ticketTier;
        public String ticketPrice;
        public String ticketQuantity;
        public String ticketTierDescription;

        public EventScenario(String scenarioName) {
            this.scenarioName = scenarioName;
            this.title = "";
            this.stars = "";
            this.description = "";
            this.location = "";
            this.city = "";
            this.contactEmail = "";
            this.contactPhone = "";
            this.contactPerson = "";
            this.mapEmbedUrl = "";
            this.directions = "";
            this.termsAndConditions = "";
            this.agePolicy = "";
            this.cancellationPolicy = "";
            this.leadTime = "";
            this.metaData = "";
            this.experienceIncludes = "";
            this.eventDescription = "";
            this.eventDate = "";
            this.startTime = "";
            this.ticketName = "";
            this.category = "";
            this.doorsOpen = "";
            this.totalCapacity = "";
            this.lastBookingDate = "";
            this.ticketTier = "";
            this.ticketPrice = "";
            this.ticketQuantity = "";
            this.ticketTierDescription = "";
        }
    }

    private static EventScenario getPositiveBase() {
        EventScenario s = new EventScenario("Base Positive");
        s.title = "Dubai Tech Summit 2026";
        s.stars = "5";
        s.description = "The biggest tech event in the Middle East.";
        s.location = "Dubai World Trade Centre";
        s.city = "Dubai";
        s.contactEmail = "events@miftah.ai";
        s.contactPhone = "9876543211";
        s.contactPerson = "Ahmed Khan";
        s.mapEmbedUrl = "<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3609.11234567!2d55.2891!3d25.2285!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m2!1m1!2zMjXCsDEzJzQyLjYiTiA1N8KwMTcnMjAuOCJF!5e0!3m2!1sen!2sin!4v123456789\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>";
        s.directions = "Take the Metro to World Trade Centre Station.";
        s.termsAndConditions = "Tickets are non-transferable.";
        s.agePolicy = "18+ for main sessions.";
        s.cancellationPolicy = "Partial refund up to 30 days before.";
        s.leadTime = "48";
        s.metaData = "tech, dubai, innovation, 2026";
        s.experienceIncludes = "Networking Lounge, Workshop Access, Lunch Buffet";
        s.eventDescription = "A 3-day deep dive into AI and Robotics.";
        s.eventDate = "15-05-2026";
        s.startTime = "09:00";
        s.ticketName = "All-Access Delegate Pass";
        s.category = "Conference";
        s.doorsOpen = "08:30";
        s.totalCapacity = "500";
        s.lastBookingDate = "100520261800"; // 10-05-2026 18:00
        s.ticketTier = "Early Bird";
        s.ticketPrice = "1200";
        s.ticketQuantity = "500";
        s.ticketTierDescription = "Enjoy early bird discounts on full passes.";
        return s;
    } 

    private static EventScenario copy(EventScenario base, String newName) {
        EventScenario c = new EventScenario(newName);
        c.title = base.title;
        c.stars = base.stars;
        c.description = base.description;
        c.location = base.location;
        c.city = base.city;
        c.contactEmail = base.contactEmail;
        c.contactPhone = base.contactPhone;
        c.contactPerson = base.contactPerson;
        c.mapEmbedUrl = base.mapEmbedUrl;
        c.directions = base.directions;
        c.termsAndConditions = base.termsAndConditions;
        c.agePolicy = base.agePolicy;
        c.cancellationPolicy = base.cancellationPolicy;
        c.leadTime = base.leadTime;
        c.metaData = base.metaData;
        c.experienceIncludes = base.experienceIncludes;
        c.eventDescription = base.eventDescription;
        c.eventDate = base.eventDate;
        c.startTime = base.startTime;
        c.ticketName = base.ticketName;
        c.category = base.category;
        c.doorsOpen = base.doorsOpen;
        c.totalCapacity = base.totalCapacity;
        c.lastBookingDate = base.lastBookingDate;
        c.ticketTier = base.ticketTier;
        c.ticketPrice = base.ticketPrice;
        c.ticketQuantity = base.ticketQuantity;
        c.ticketTierDescription = base.ticketTierDescription;
        return c;
    }

    public static List<EventScenario> getScenarios() {
        List<EventScenario> scenarios = new ArrayList<>();
        EventScenario base = getPositiveBase();

        // 1. FIRST: Positive Scenario
        scenarios.add(copy(base, "Positive Case: Valid Event"));

        // 2. Systematic Negative: TITLE FIELD
        String[] negTitles = { "", "Event " + "A".repeat(250) };
        for (String val : negTitles) {
            EventScenario s = copy(base, "Neg Title: " + (val.isEmpty() ? "Empty" : "Too Long"));
            s.title = val;
            scenarios.add(s);
        }

        // 3. Systematic Negative: STARS FIELD
        String[] negStars = { "", "-1", "ABC", "10" };
        for (String val : negStars) {
            EventScenario s = copy(base, "Neg Stars: " + (val.isEmpty() ? "Empty" : val));
            s.stars = val;
            scenarios.add(s);
        }

        // 4. Systematic Negative: EMAIL FIELD
        String[] negEmails = { "", "invalid-email", "test@missingdot" };
        for (String val : negEmails) {
            EventScenario s = copy(base, "Neg Email: " + (val.isEmpty() ? "Empty" : "Invalid"));
            s.contactEmail = val;
            scenarios.add(s);
        }

        // 5. Systematic Negative: PRICE FIELD
        String[] negPrices = { "", "-500", "Free" };
        for (String val : negPrices) {
            EventScenario s = copy(base, "Neg Price: " + (val.isEmpty() ? "Empty" : val));
            s.ticketPrice = val;
            scenarios.add(s);
        }

        // 6. Systematic Negative: QUANTITY / CAPACITY
        String[] negNums = { "", "-10", "Many" };
        for (String val : negNums) {
            EventScenario s = copy(base, "Neg Quantity: " + (val.isEmpty() ? "Empty" : val));
            s.ticketQuantity = val;
            scenarios.add(s);

            EventScenario s2 = copy(base, "Neg Capacity: " + (val.isEmpty() ? "Empty" : val));
            s2.totalCapacity = val;
            scenarios.add(s2);
        }

        // 7. Systematic Negative: LEAD TIME
        String[] negLeads = { "", "Invalid", "-2" };
        for (String val : negLeads) {
            EventScenario s = copy(base, "Neg LeadTime: " + (val.isEmpty() ? "Empty" : val));
            s.leadTime = val;
            scenarios.add(s);
        }

        return scenarios;
    }
}
