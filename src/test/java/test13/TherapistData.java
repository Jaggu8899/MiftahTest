package test13;

import java.util.ArrayList;
import java.util.List;

public class TherapistData {

    public static class TherapistScenario {
        public String scenarioName;
        public String name;
        public String dialCode;
        public String phone;
        public String secDialCode;
        public String secPhone;
        public String languages;
        public String description;
        public String experience; // Experience Details
        public String count; // Sessions Available
        public String currency;
        public String leadTime; // Lead Time (Mins)
        public String basePrice;
        public String pricePerSession;
        public String pricePerHour;
        public String bespokePrice;
        public String licenseNumber;
        public String therapyMethods;
        public String capacity;
        public boolean homeVisits;
        public String telehealthPlatform;
        public String locationType;
        public String serviceRadius;
        public String cities;
        public String cancellationPolicy;
        public String termsAndConditions;

        public TherapistScenario(String scenarioName) {
            this.scenarioName = scenarioName;
            this.name = "";
            this.dialCode = "";
            this.phone = "";
            this.secDialCode = "";
            this.secPhone = "";
            this.languages = "";
            this.description = "";
            this.experience = "";
            this.count = "";
            this.currency = "AED";
            this.leadTime = "";
            this.basePrice = "";
            this.pricePerSession = "";
            this.pricePerHour = "";
            this.bespokePrice = "";
            this.licenseNumber = "";
            this.therapyMethods = "";
            this.capacity = "";
            this.homeVisits = true;
            this.telehealthPlatform = "";
            this.locationType = "REMOTE";
            this.serviceRadius = "";
            this.cities = "";
            this.cancellationPolicy = "";
            this.termsAndConditions = "";
        }
    }

    private static TherapistScenario getPositiveBase() {
        TherapistScenario s = new TherapistScenario("Positive Base Case");
        s.name = "Dr. John Doe";
        s.dialCode = "+91";
        s.phone = "9876543210";
        s.secDialCode = "+91";
        s.secPhone = "9876543211";
        s.languages = "Arabic";
        s.description = "Detailed description of therapy services provided by a professional.";
        s.experience = "10 years of clinical experience in mental health.";
        s.count = "500";
        s.currency = "AED";
        s.leadTime = "30";
        s.basePrice = "120";
        s.pricePerSession = "100";
        s.pricePerHour = "80";
        s.bespokePrice = "200";
        s.licenseNumber = "LIC123456";
        s.therapyMethods = "CBT, DBT";
        s.capacity = "5";
        s.homeVisits = true;
        s.telehealthPlatform = "Zoom";
        s.locationType = "REMOTE";
        s.serviceRadius = "15";
        s.cities = "Dubai, Sharjah";
        s.cancellationPolicy = "Cancellations allowed up to 24 hours prior.";
        s.termsAndConditions = "Agreement to standard service terms.";
        return s;
    }

    private static TherapistScenario copy(TherapistScenario base, String newName) {
        TherapistScenario c = new TherapistScenario(newName);
        c.name = base.name;
        c.dialCode = base.dialCode;
        c.phone = base.phone;
        c.secDialCode = base.secDialCode;
        c.secPhone = base.secPhone;
        c.languages = base.languages;
        c.description = base.description;
        c.experience = base.experience;
        c.count = base.count;
        c.currency = base.currency;
        c.leadTime = base.leadTime;
        c.basePrice = base.basePrice;
        c.pricePerSession = base.pricePerSession;
        c.pricePerHour = base.pricePerHour;
        c.bespokePrice = base.bespokePrice;
        c.licenseNumber = base.licenseNumber;
        c.therapyMethods = base.therapyMethods;
        c.capacity = base.capacity;
        c.homeVisits = base.homeVisits;
        c.telehealthPlatform = base.telehealthPlatform;
        c.locationType = base.locationType;
        c.serviceRadius = base.serviceRadius;
        c.cities = base.cities;
        c.cancellationPolicy = base.cancellationPolicy;
        c.termsAndConditions = base.termsAndConditions;
        return c;
    }

    public static List<TherapistScenario> getScenarios() {
        List<TherapistScenario> scenarios = new ArrayList<>();
        TherapistScenario base = getPositiveBase();

        // 1. Positive Scenario
        scenarios.add(base);

        // --- EXPLICIT NEGATIVE TESTING (One-to-One Objectives) ---

        // 1. Name
        String[] nTitles = { "", " ", "12345", "@#$%^", "A", "A".repeat(256), "<script>alert(1)</script>" };
        String[] nTitlesObjs = { " Titles Empty", " Titles Space", " Titles Numeric", " Titles Special",
                " Titles Short",
                " Titles TooLong", " Titles Script" };
        for (int i = 0; i < nTitles.length; i++) {
            TherapistScenario s = copy(base, "Neg Titles:" + nTitlesObjs[i]);
            s.name = nTitles[i];
            scenarios.add(s);
        }

        // 2. Phone Number
        String[] nPhones = { "", "123", "ABC", "987654321123", "phone12", "98@76#432", " " };
        String[] nPhoneObjs = { " phone Empty", " phone Short", " phone Alpha", " phone TooLong", " phone Mixed",
                " phone Special", " phone Space" };
        for (int i = 0; i < nPhones.length; i++) {
            TherapistScenario s = copy(base, "Neg Phone:" + nPhoneObjs[i]);
            s.phone = nPhones[i];
            scenarios.add(s);
        }

        // 3. Alternative Phone
        String[] nAltPhones = { "123", "ABC" };
        String[] nAltPhoneObjs = { " altPhone NumericShort", " altPhone AlphaChars" };
        for (int i = 0; i < nAltPhones.length; i++) {
            TherapistScenario s = copy(base, "Neg AltPhone:" + nAltPhoneObjs[i]);
            s.secPhone = nAltPhones[i];
            scenarios.add(s);
        }

        // 4. Description
        String[] nDescs = { "", "Short", "@@@@####", "<script>alert(1)</script>" };
        String[] nDescObjs = { " desc Empty", " desc ShortText", " desc Special", " desc ScriptTag" };
        for (int i = 0; i < nDescs.length; i++) {
            TherapistScenario s = copy(base, "Neg Desc:" + nDescObjs[i]);
            s.description = nDescs[i];
            scenarios.add(s);
        }

        // 5. Sessions Available (Count)
        String[] nCounts = { "", " ", "0", "-5", "ABC", "@#$", "99999" };
        String[] nCountObjs = { " count Empty", " count Space", " count Zero", " count Negative", " count Alpha",
                " count Special", " count LimitExceeded" };
        for (int i = 0; i < nCounts.length; i++) {
            TherapistScenario s = copy(base, "Neg Count:" + nCountObjs[i]);
            s.count = nCounts[i];
            scenarios.add(s);
        }

        // 6. Lead Time
        String[] nLeads = { "", "-1", "ABC", "0.5", "@#%", "007" };
        String[] nLeadObjs = { " lead Empty", " lead Negative", " lead Alpha", " lead Float", " lead Special",
                " lead LeadingZeros" };
        for (int i = 0; i < nLeads.length; i++) {
            TherapistScenario s = copy(base, "Neg Lead:" + nLeadObjs[i]);
            s.leadTime = nLeads[i];
            scenarios.add(s);
        }

        // 7. Base Price
        String[] nBasePrices = { "", "0", "-10", "100abc", "@#$%", "000500" };
        String[] nBasePriceObjs = { " basePrice Empty", " basePrice Zero", " basePrice Negative",
                " basePrice AlphaMixed", " basePrice Special", " basePrice LeadingZeros" };
        for (int i = 0; i < nBasePrices.length; i++) {
            TherapistScenario s = copy(base, "Neg BasePrice:" + nBasePriceObjs[i]);
            s.basePrice = nBasePrices[i];
            scenarios.add(s);
        }

        // 8. Price Per Session
        String[] nSessPrices = { "", "0", "-1", "ABC" };
        String[] nSessPriceObjs = { " priceSess Empty", " priceSess Zero", " priceSess Negative", " priceSess Alpha" };
        for (int i = 0; i < nSessPrices.length; i++) {
            TherapistScenario s = copy(base, "Neg PriceSess:" + nSessPriceObjs[i]);
            s.pricePerSession = nSessPrices[i];
            scenarios.add(s);
        }

        // 9. Price Per Hour
        String[] nHourPrices = { "", "0", "-1", "NaN" };
        String[] nHourPriceObjs = { " priceHr Empty", " priceHr Zero", " priceHr Negative", " priceHr NaN" };
        for (int i = 0; i < nHourPrices.length; i++) {
            TherapistScenario s = copy(base, "Neg PriceHr:" + nHourPriceObjs[i]);
            s.pricePerHour = nHourPrices[i];
            scenarios.add(s);
        }

        // 10. Bespoke Price
        String[] nBespPrices = { "", "0", "-1" };
        String[] nBespPriceObjs = { " bespoke Empty", " bespoke Zero", " bespoke Negative" };
        for (int i = 0; i < nBespPrices.length; i++) {
            TherapistScenario s = copy(base, "Neg Bespoke:" + nBespPriceObjs[i]);
            s.bespokePrice = nBespPrices[i];
            scenarios.add(s);
        }

        // 11. License Number
        String[] nLicNums = { "", " ", "!!!", "A".repeat(5) }; // Assuming min length or specificity
        String[] nLicObjs = { " license Empty", " license Space", " license Special", " license TooShort" };
        for (int i = 0; i < nLicNums.length; i++) {
            TherapistScenario s = copy(base, "Neg License:" + nLicObjs[i]);
            s.licenseNumber = nLicNums[i];
            scenarios.add(s);
        }

        // 12. Therapy Methods
        String[] nMethods = { "", "!!!" };
        String[] nMethodObjs = { " methods Empty", " methods Special" };
        for (int i = 0; i < nMethods.length; i++) {
            TherapistScenario s = copy(base, "Neg Methods:" + nMethodObjs[i]);
            s.therapyMethods = nMethods[i];
            scenarios.add(s);
        }

        // 13. Experience
        String[] nExps = { "", "None", "-5" };
        String[] nExpObjs = { " exp Empty", " exp InvalidText", " exp Negative" };
        for (int i = 0; i < nExps.length; i++) {
            TherapistScenario s = copy(base, "Neg Exp:" + nExpObjs[i]);
            s.experience = nExps[i];
            scenarios.add(s);
        }

        // 14. Capacity
        String[] nCaps = { "", "0", "-1", "ABC" };
        String[] nCapObjs = { " capacity Empty", " capacity Zero", " capacity Negative", " capacity Alpha" };
        for (int i = 0; i < nCaps.length; i++) {
            TherapistScenario s = copy(base, "Neg Capacity:" + nCapObjs[i]);
            s.capacity = nCaps[i];
            scenarios.add(s);
        }

        // 15. Service Radius
        String[] nRadii = { "", "-1", "ABC", "1000" };
        String[] nRadiiObjs = { " radius Empty", " radius Negative", " radius Alpha", " radius TooLarge" };
        for (int i = 0; i < nRadii.length; i++) {
            TherapistScenario s = copy(base, "Neg Radius:" + nRadiiObjs[i]);
            s.serviceRadius = nRadii[i];
            scenarios.add(s);
        }

        return scenarios;
    }
}
