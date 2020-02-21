package com.web.hackaton.enumerated;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PositionName {
    A_B_TESTER("A/B tester"),
    APPLICATION_ANALYST("Application analyst"),
    BUSINESS_ANALYST("Business analyst"),
    COMPUTER_OPERATOR("Computer operator"),
    COMPUTER_REPAIR_TECHNICIAN("Computer repair technician"),
    COMPUTER_SCIENTIST("Computer scientist"),
    COMPUTER_ANALYST("Computer analyst"),
    CHIEF_INFORMATION_OFFICER("Chief Information Officer"),
    DATA_ENTRY_CLERK("Data entry clerk"),
    DATABASE_ADMINISTRATOR("Database administrator"),
    DATA_ANALYST("Data analyst"),
    DATA_DESIGNER("Data designer"),
    DATA_SCIENTIST("Data scientist"),
    HARDWARE_ENGINEER("Hardware engineer"),
    INFORMATION_SYSTEMS_TECHNICIAN("Information systems technician"),
    IT_ASSISTANT("IT assistant"),
    NETWORK_ANALYST("Network analyst"),
    NETWORK_ADMINISTRATOR("Network administrator"),
    PROGRAMMER("Programmer"),
    PRODUCT_MANAGER("Product manager"),
    RAPID_PROTOTYPER("Rapid prototyper"),
    SCRUM_MASTER("Scrum master"),
    SOFTWARE_ANALYST("Software analyst"),
    SOFTWARE_ARCHITECT("Software architect"),
    SOFTWARE_DESIGN("Software design"),
    SOFTWARE_ENGINEER("Software engineer"),
    SOFTWARE_PROJECT_MANAGER("Software project manager"),
    SOFTWARE_QUALITY_ANALYST("Software quality analyst"),
    SOFTWARE_TEST_ENGINEER_TESTER("Software test engineer (Tester)"),
    SOLUTION_ARCHITECT("Solution architect"),
    SUPPORT_TECHNICIAN_HELP_DESK("Support technician (Help Desk)"),
    SYSTEM_ADMINISTRATOR("System administrator"),
    SYSTEMS_ANALYST("Systems analyst"),
    USER_EXPERIENCE_DESIGNER("User experience designer"),
    USER_INTERACTION_DESIGNER("User interaction designer"),
    USER_RESEARCHER("User researcher"),
    VISUAL_DESIGNER("Visual designer"),
    WEB_DEVELOPER("Web developer"),
    WEBSITE_ADMINISTRATOR("Website administrator");

    public String caption;

    public String getCaption() {
        return caption;
    }
}

