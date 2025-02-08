package net.ibxnjadev.plantuml.command.impl;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateFields;
import discord4j.core.spec.MessageCreateSpec;
import net.ibxnjadev.plantuml.command.CommandExecutor;
import net.ibxnjadev.plantuml.diagram.PlantumlDiagramCreator;
import net.ibxnjadev.plantuml.util.PlantumlArrays;
import reactor.core.publisher.Mono;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiagramCommand implements CommandExecutor  {

    private static final String DELIMITER = "```";

    private final PlantumlDiagramCreator plantumlDiagramCreator;
    public DiagramCommand(PlantumlDiagramCreator plantumlDiagramCreator){
        this.plantumlDiagramCreator = plantumlDiagramCreator;
    }

    @Override
    public Mono<? extends Object> execute(String[] args,
                                          Message message) {
        Mono<MessageChannel> channel = message.getChannel();
        String name = args[0];

        if (!name.equalsIgnoreCase("d") &&
                !name.equalsIgnoreCase("diagram")) {
            return channel.flatMap(c -> c.createMessage("Command not found, try with : \n ;p d | diagram << PlantUML Code >> "));
        }

        String messageAll = String.join(" ",
                        PlantumlArrays.slice(
                                args, String.class, 1, args.length - 1
                        )).trim();

        System.out.println(messageAll);
        if(!(messageAll.startsWith(DELIMITER) &&
        messageAll.endsWith(DELIMITER))) {
            return channel.flatMap(c -> c.createMessage("The argument should contain the plantuml code between `"));
        }

        String plantUmlCode = messageAll.
                substring(3, messageAll.length() - 3).trim();

        try (OutputStream outputStream = new FileOutputStream("diagram.png")) {
            plantumlDiagramCreator.output(plantUmlCode, outputStream);

            File diagramFile = new File("diagram.png");
            return channel.flatMap(c -> {
                try {
                    return c.createMessage(MessageCreateSpec
                            .builder()
                            .addFile(
                                    MessageCreateFields.File.of("diagram.png", new FileInputStream(diagramFile))
                            ).build()
                    );
                } catch (FileNotFoundException e) {
                    return Mono.error(new RuntimeException(e));
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
