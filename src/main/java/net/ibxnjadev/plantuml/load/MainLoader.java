package net.ibxnjadev.plantuml.load;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import net.ibxnjadev.plantuml.command.BasicMessageCreateListener;
import net.ibxnjadev.plantuml.command.CommandMap;
import net.ibxnjadev.plantuml.command.CommandMapImpl;
import net.ibxnjadev.plantuml.command.impl.DiagramCommand;

public class MainLoader {

    private DiscordClient client;
    private final CommandMap commandMap = new CommandMapImpl();
    private final BasicMessageCreateListener basicMessageCreateListener = new BasicMessageCreateListener(commandMap);

    private final String token;


    public MainLoader(String token) {
        this.token = token;
    }

    /**
     * Start the server for run the discord bot
     * making the authentication and configure other
     * parameters
      */

    public void load() {
        initBot();

        commandMap.register(";p",
                new DiagramCommand());

        handleCommands();
    }

    /**
     * Generate the authentication with services discord
     * for enable the bot
     */

    private void initBot() {
        client = DiscordClient.create(token);
    }

    private void handleCommands() {
        client.withGateway(client ->
                        client.on(MessageCreateEvent.class, basicMessageCreateListener::apply))
                .block();
    }

}
