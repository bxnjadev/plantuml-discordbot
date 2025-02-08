package net.ibxnjadev.plantuml.load;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import net.ibxnjadev.plantuml.command.BasicMessageCreateListener;
import net.ibxnjadev.plantuml.command.CommandMap;
import net.ibxnjadev.plantuml.command.CommandMapImpl;
import net.ibxnjadev.plantuml.command.impl.DiagramCommand;
import net.ibxnjadev.plantuml.diagram.PlantumlDiagramCreator;
import net.ibxnjadev.plantuml.diagram.PlantumlDiagramCreatorImpl;
import net.ibxnjadev.plantuml.util.PlantumlFiles;

import java.io.*;

public class MainLoader {

    private static final String INITIAL_CONTENT_FILE_TOKEN = "token: \"\"";
    private static final String FILE_NAME = "token.yml";

    private DiscordClient client;
    private final CommandMap commandMap = new CommandMapImpl();
    private final PlantumlDiagramCreator plantumlDiagramCreator = new PlantumlDiagramCreatorImpl();
    private final BasicMessageCreateListener basicMessageCreateListener = new BasicMessageCreateListener(commandMap);

    /**
     * The token discord bot
     */

    private  String token;

    public MainLoader() {}

    /**
     * Start the server for run the discord bot
     * making the authentication and configure other
     * parameters
      */

    public void load() {
        loadConfiguration();
        initBot();

        commandMap.register(";p",
                new DiagramCommand(plantumlDiagramCreator));

        handleCommands();
    }

    /**
     * Create and load the configuration bot file
     * for start the discord bot
     */

    private void loadConfiguration() {

        try {
            boolean response = PlantumlFiles
                    .createFileIfNotExists(FILE_NAME, INITIAL_CONTENT_FILE_TOKEN);
            if(response) {
                System.out.println("Creating the initial file : " + FILE_NAME);
                System.out.println("Please, configure this file for start the bot");
                System.exit(-1);
            }
        } catch (IOException e) {
            System.out.println("Error creating the file : " + FILE_NAME);
            throw new RuntimeException(e);
        }

        String token = "";
        try {
            token = readToken(FILE_NAME);
            if(token == null || token.isBlank()){
                System.out.println("Error loading the token, please check the file name, exit the program");
                System.exit(-1);
            }
            this.token = token;
        } catch (IOException e) {
            System.out.println("Error loading the token, please check the file name, exit the program");
            throw new RuntimeException(e);
        }

    }

    /**
     * Generate the authentication with services discord
     * for enable the bot
     */

    private void initBot() {
        client = DiscordClient.create(token);
    }

    /**
     * Read a file for get the registered token
     * @return the token obtained
     * @throws IOException this exception is throw if there is an error reading the file
     */

    private String readToken(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(fileName)
        );

        String line = reader.readLine();
        if(!line.startsWith("token")) {
            return null;
        }
        String token = line.substring(
                line.indexOf(':') + 1,
                line.length() - 1
        ).replace("\"","")
                        .trim();
        return token;
    }

    private void handleCommands() {
        client.withGateway(client ->
                        client.on(MessageCreateEvent.class, basicMessageCreateListener::apply))
                .block();
    }

}
