package net.ibxnjadev.plantuml;

import net.ibxnjadev.plantuml.load.MainLoader;
import net.ibxnjadev.plantuml.util.PlantumlArrays;

public class Main {

    private static final String DISCORD_BOT_TOKEN = "MTMzNzQ0Njc3MTUxNjc2ODM2OQ.GTb-Wa.3A08odhg9TAEDxq9l4W1t-3E8EWrFCcTK8VEbs";

    public static void main(String[] args) {
        MainLoader mainLoader = new MainLoader(DISCORD_BOT_TOKEN);
        mainLoader.load();
    }

}