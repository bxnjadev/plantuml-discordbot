package net.ibxnjadev.plantuml;

import net.ibxnjadev.plantuml.load.MainLoader;

public class Main {

    private static final String DISCORD_BOT_TOKEN = "";

    public static void main(String[] args) {
        MainLoader mainLoader = new MainLoader(DISCORD_BOT_TOKEN);
        mainLoader.load();
    }

}