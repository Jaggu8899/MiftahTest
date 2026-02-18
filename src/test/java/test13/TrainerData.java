package test13;

import java.util.ArrayList;
import java.util.List;

public class TrainerData {

    public static class TrainerScenario {
        public String scenarioName;
        public String serviceTitle;
        public String dialCode;
        public String phoneNumber;
        public String secDialCode;
        public String alternativePhoneNumber;
        public String languages;
        public String description;
        public String sessionsAvailable;
        public String currency;
        public String leadTime;
        public String basePrice;
        public String pricePerSession;
        public String pricePerHour;
        public String bespokePrice;
        public String trainingTypes;
        public String experienceDetails;
        public String certificationDetails;
        public String sessionDuration;
        public String levelFocus;
        public boolean equipmentProvided;
        public String locationType;
        public String serviceRadius;
        public String cities;
        public String cancellationPolicy;
        public String termsAndConditions;

        public TrainerScenario(String scenarioName) {
            this.scenarioName = scenarioName;
            this.serviceTitle = "";
            this.dialCode = "";
            this.phoneNumber = "";
            this.secDialCode = "";
            this.alternativePhoneNumber = "";
            this.languages = "";
            this.description = "";
            this.sessionsAvailable = "";
            this.currency = "AED";
            this.leadTime = "";
            this.basePrice = "";
            this.pricePerSession = "";
            this.pricePerHour = "";
            this.bespokePrice = "";
            this.trainingTypes = "";
            this.experienceDetails = "";
            this.certificationDetails = "";
            this.sessionDuration = "";
            this.levelFocus = "";
            this.equipmentProvided = true;
            this.locationType = "REMOTE";
            this.serviceRadius = "";
            this.cities = "";
            this.cancellationPolicy = "";
            this.termsAndConditions = "";
        }
    }

    private static TrainerScenario getPositiveBase() {
        TrainerScenario s = new TrainerScenario("Positive Base Case");
        s.serviceTitle = "Pro Trainer John";
        s.dialCode = "+91";
        s.phoneNumber = "9876543211";
        s.secDialCode = "+91";
        s.alternativePhoneNumber = "9876543212";
        s.languages = "Arabic";
        s.description = "Professional trainer with over 10 years of experience in fitness and wellness.";
        s.sessionsAvailable = "5";
        s.currency = "AED";
        s.leadTime = "3";
        s.basePrice = "100";
        s.pricePerSession = "75";
        s.pricePerHour = "50";
        s.bespokePrice = "200";
        s.trainingTypes = "Online, Offline";
        s.experienceDetails = "Extensive experience in weight loss and muscle building programs.";
        s.certificationDetails = "Certified ACE Personal Trainer, First Aid Certified.";
        s.sessionDuration = "60";
        s.levelFocus = "Beginner, Intermediate, Advanced";
        s.equipmentProvided = true;
        s.locationType = "REMOTE";
        s.serviceRadius = "10";
        s.cities = "Dubai, Sharjah";
        s.cancellationPolicy = "Cancellation is allowed up to 24 hours before the session.";
        s.termsAndConditions = "Users must follow all guidelines and agree to the service terms.";
        return s;
    }

    private static TrainerScenario copy(TrainerScenario base, String newName) {
        TrainerScenario c = new TrainerScenario(newName);
        c.serviceTitle = base.serviceTitle;
        c.dialCode = base.dialCode;
        c.phoneNumber = base.phoneNumber;
        c.secDialCode = base.secDialCode;
        c.alternativePhoneNumber = base.alternativePhoneNumber;
        c.languages = base.languages;
        c.description = base.description;
        c.sessionsAvailable = base.sessionsAvailable;
        c.currency = base.currency;
        c.leadTime = base.leadTime;
        c.basePrice = base.basePrice;
        c.pricePerSession = base.pricePerSession;
        c.pricePerHour = base.pricePerHour;
        c.bespokePrice = base.bespokePrice;
        c.trainingTypes = base.trainingTypes;
        c.experienceDetails = base.experienceDetails;
        c.certificationDetails = base.certificationDetails;
        c.sessionDuration = base.sessionDuration;
        c.levelFocus = base.levelFocus;
        c.equipmentProvided = base.equipmentProvided;
        c.locationType = base.locationType;
        c.serviceRadius = base.serviceRadius;
        c.cities = base.cities;
        c.cancellationPolicy = base.cancellationPolicy;
        c.termsAndConditions = base.termsAndConditions;
        return c;
    }

    public static List<TrainerScenario> getScenarios() {
        List<TrainerScenario> scenarios = new ArrayList<>();
        TrainerScenario base = getPositiveBase();

        // 1. Positive Scenario
        scenarios.add(base);

        
          // --- EXPLICIT NEGATIVE TESTING (One-to-One Objectives) ---
         
         // 1. Service Title (Name)
          String[] nTitles = { "https://google.com", "C:\\test\\file.txt",
          "<script>alert(1)</script>", "12345", "@#$%^",
          "@", "A", " ", "", "A".repeat(256) };
          String[] nTitleObjectives = { " title URL", " title FilePath", " title Script", " title Numeric",
          " title SpecialChars", " title AtSymbol", " title Short",
          " title Space", " title Empty", " title TooLong" };
          for (int i = 0; i < nTitles.length; i++) {
          scenarios.add(copy(base, "Neg Name: " + nTitleObjectives[i]));
          scenarios.get(scenarios.size() - 1).serviceTitle = nTitles[i];
          }
          
          // 2. Phone Number
          String[] nPhones = { "", "123", "ABC", "987654321123", "phone123",
          "98@76#432", " " };
          String[] nPhoneObjectives = { " phone Empty", " phone Short", " phone Alpha", " phone TooLong", " phone Mixed",
          " phone Special", " phone Space" };
          for (int i = 0; i < nPhones.length; i++) {
          scenarios.add(copy(base, "Neg Phone: " + nPhoneObjectives[i]));
          scenarios.get(scenarios.size() - 1).phoneNumber = nPhones[i];
          }
          
          // 3. Alternative Phone Number
          String[] nAltPhones = { "123", "ABC" };
          String[] nAltPhoneObjectives = { " altPhone NumericShort", " altPhone AlphaChars" };
          for (int i = 0; i < nAltPhones.length; i++) {
          scenarios.add(copy(base, "Neg AltPhone: " + nAltPhoneObjectives[i]));
          scenarios.get(scenarios.size() - 1).alternativePhoneNumber = nAltPhones[i];
          }
           /* 
          // 4. Description
          String[] nDescs = { "", "Short", "@@@@####", "<script>alert(1)</script>" };
          String[] nDescObjectives = { " description Empty", " description ShortText", " description SpecialChars",
          " description ScriptTag" };
          for (int i = 0; i < nDescs.length; i++) {
          scenarios.add(copy(base, "Neg Desc: " + nDescObjectives[i]));
          scenarios.get(scenarios.size() - 1).description = nDescs[i];
          }
          */
          // 5. years of experience (Count)
          String[] nSessions = { "@#$", "", " ", "0", "-1", "ABC", "100" };
          String[] nSessionObjectives = { " yearsexperience SpecialChars", " yearsexperience Empty", " yearsexperience Space",
          " yearsexperience ZeroValue", " yearsexperience Negative", " yearsexperience AlphaChars",
          " yearsexperience LimitExceeded" };
          for (int i = 0; i < nSessions.length; i++) {
          scenarios.add(copy(base, "Neg Count: " + nSessionObjectives[i]));
          scenarios.get(scenarios.size() - 1).sessionsAvailable = nSessions[i];
          }
        
          // 6. Lead Time
          String[] nLeads = { "", "-1", "ABC", "abc", "@#%", "3days", "1.5", "999",
          "007" };
          String[] nLeadObjectives = { " leadTime Empty", " leadTime Negative", " leadTime UpperAlpha", " leadTime LowerAlpha",
          " leadTime SpecialChars", " leadTime MixedAlpha",
          " leadTime FloatValue", " leadTime TooLarge", " leadTime LeadingZeros" };
          for (int i = 0; i < nLeads.length; i++) {
          scenarios.add(copy(base, "Neg LeadTime: " + nLeadObjectives[i]));
          scenarios.get(scenarios.size() - 1).leadTime = nLeads[i];
          }
          
          // 7. Base Price
          String[] nBasePrices = { "", "0", "-1", "100abc", "@#$%", "000500" };
          String[] nBasePriceObjectives = { " basePrice Empty", " basePrice Zero", " basePrice Negative", " basePrice AlphaMixed",
          " basePrice Special", " basePrice LeadingZeros" };
          for (int i = 0; i < nBasePrices.length; i++) {
          scenarios.add(copy(base, "Neg BasePrice: " + nBasePriceObjectives[i]));
          scenarios.get(scenarios.size() - 1).basePrice = nBasePrices[i];
          }
          
          // 8. Price per Session
         String[] nSessPrices = { "", "0", "-1", "ABC" };
          String[] nSessPriceObjectives = { " pricePerSession Empty", " pricePerSession Zero", " pricePerSession Negative", " pricePerSession AlphaChars"
         };
          for (int i = 0; i < nSessPrices.length; i++) {
          scenarios.add(copy(base, "Neg PriceSess: " + nSessPriceObjectives[i]));
          scenarios.get(scenarios.size() - 1).pricePerSession = nSessPrices[i];
          }
          
          // 9. Price per Hour
          String[] nHourPrices = { "", "0", "-1", "NaN" };
          String[] nHourPriceObjectives = { " pricePerHour Empty", " pricePerHour Zero", " pricePerHour Negative", " pricePerHour NotANumber"
          };
          for (int i = 0; i < nHourPrices.length; i++) {
          scenarios.add(copy(base, "Neg PriceHr: " + nHourPriceObjectives[i]));
          scenarios.get(scenarios.size() - 1).pricePerHour = nHourPrices[i];
         }
          
          // 10. Bespoke Price
          String[] nBespPrices = { "", "0", "-1" };
          String[] nBespPriceObjectives = { " bespokePrice Empty", "bespokePrice Zero", "bespokePrice Negative" };
          for (int i = 0; i < nBespPrices.length; i++) {
          scenarios.add(copy(base, "Neg BespokePrice: " + nBespPriceObjectives[i]));
          scenarios.get(scenarios.size() - 1).bespokePrice = nBespPrices[i];
          }
           /* 
          // 11. Experience Details
          String[] nExps = { "", "None", "-5" };
          String[] nExpObjectives = { " experienceDetails Empty", " experienceDetails InvalidText", " experienceDetails NegativeValue" };
          for (int i = 0; i < nExps.length; i++) {
          scenarios.add(copy(base, "Neg Exp: " + nExpObjectives[i]));
          scenarios.get(scenarios.size() - 1).experienceDetails = nExps[i];
          }
          
          // 12. Certification Details
          String[] nCerts = { "", "!!!" };
          String[] nCertObjectives = { " certificationDetails Empty", " certificationDetails SpecialChars" };
          for (int i = 0; i < nCerts.length; i++) {
          scenarios.add(copy(base, "Neg Certs: " + nCertObjectives[i]));
          scenarios.get(scenarios.size() - 1).certificationDetails = nCerts[i];
         }
          */
          // 13. Session Duration
          String[] nDurations = { "", "0", "-10" };
          String[] nDurationObjectives = { " sessionDuration Empty", " sessionDuration Zero", " sessionDuration Negative" };
          for (int i = 0; i < nDurations.length; i++) {
          scenarios.add(copy(base, "Neg Duration: " + nDurationObjectives[i]));
          scenarios.get(scenarios.size() - 1).sessionDuration = nDurations[i];
          }
          
          // 14. Service Radius
          String[] nRadii = { "", "-1", "ABC" };
          String[] nRadiiObjectives = { " serviceRadius Empty", " serviceRadius Negative", " serviceRadius AlphaChars" };
         for (int i = 0; i < nRadii.length; i++) {
          scenarios.add(copy(base, "Neg Radius: " + nRadiiObjectives[i]));
          scenarios.get(scenarios.size() - 1).serviceRadius = nRadii[i];
          }
         
          
        return scenarios;
    }
}
