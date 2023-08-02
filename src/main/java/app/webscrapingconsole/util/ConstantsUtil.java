package app.webscrapingconsole.util;

import java.util.Map;

public class ConstantsUtil {
    public static final String MAIN_ATTR = "data-testid";
    public static final String JOB_ITEM = "job-list-item";
    public static final String JOB_LINK = "job-title-link";
    public static final String JOB_TAG = "tag";
    public static final String COMPANY_LOGO = "image";
    public static final String COMPANY_LINK = "sc-beqWaB dqlQzp";
    public static final String JOB_DESCRIPTION = "careerPage";
    public static final String POSTED_DATE = "added";
    public static final String JOB_FILTER = "filter_id";
    public static final String FUNCTIONS_CLASS = "sc-beqWaB sc-gueYoa dmdAKU MYFxR";
    public static final Map<String, String> jobFunctionMap = Map.ofEntries(
            Map.entry("Accounting & Finance",
                    "eyJqb2JfZnVuY3Rpb25zIjpbIkFjY291bnRpbmcgJiBGaW5hbmNlIl19"),
            Map.entry("Administration", "eyJqb2JfZnVuY3Rpb25zIjpbIkFkbWluaXN0cmF0aW9uIl19"),
            Map.entry("Customer Service", "eyJqb2JfZnVuY3Rpb25zIjpbIkN1c3RvbWVyIFNlcnZpY2UiXX0%3D"),
            Map.entry("Data Science", "eyJqb2JfZnVuY3Rpb25zIjpbIkRhdGEgU2NpZW5jZSJdfQ%3D%3D"),
            Map.entry("Design", "eyJqb2JfZnVuY3Rpb25zIjpbIkRlc2lnbiJdfQ%3D%3D"),
            Map.entry("IT", "eyJqb2JfZnVuY3Rpb25zIjpbIklUIl19"),
            Map.entry("Legal", "eyJqb2JfZnVuY3Rpb25zIjpbIkxlZ2FsIl19"),
            Map.entry("Marketing & Communications",
                    "eyJqb2JfZnVuY3Rpb25zIjpbIk1hcmtldGluZyAmIENvbW11bmljYXRpb25zIl19"),
            Map.entry("Operations", "eyJqb2JfZnVuY3Rpb25zIjpbIk9wZXJhdGlvbnMiXX0%3D"),
            Map.entry("Other Engineering", "eyJqb2JfZnVuY3Rpb25zIjpbIk90aGVyIEVuZ2luZWVyaW5nIl19"),
            Map.entry("People & HR", "eyJqb2JfZnVuY3Rpb25zIjpbIlBlb3BsZSAmIEhSIl19"),
            Map.entry("Product", "eyJqb2JfZnVuY3Rpb25zIjpbIlByb2R1Y3QiXX0%3D"),
            Map.entry("Quality Assurance", "eyJqb2JfZnVuY3Rpb25zIjpbIlF1YWxpdHkgQXNzdXJhbmNlIl19"),
            Map.entry("Sales & Business Development",
                    "eyJqb2JfZnVuY3Rpb25zIjpbIlNhbGVzICYgQnVzaW5lc3MgRGV2ZWxvcG1lbnQiXX0%3D"),
            Map.entry("Software Engineering",
                    "eyJqb2JfZnVuY3Rpb25zIjpbIlNvZnR3YXJlIEVuZ2luZWVyaW5nIl19"));
    public static final String CONSOLE_DIALOG = "Enter the job function from the list above "
            + "to scrape and, after a comma, specify number of jobs, a multiple of 20, "
            + "you'd like to receive or 'quit' to exit:";

    private ConstantsUtil() {
    }
}
