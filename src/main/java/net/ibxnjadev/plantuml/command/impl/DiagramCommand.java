package net.ibxnjadev.plantuml.command.impl;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import net.ibxnjadev.plantuml.command.CommandExecutor;
import reactor.core.publisher.Mono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiagramCommand implements CommandExecutor  {

    private static final Pattern PATTERN_BACKTICKS = Pattern.compile("`(.*?)`");

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
                args);
        Matcher matcher = PATTERN_BACKTICKS.matcher(messageAll);

        if (!matcher.find()) {
            return channel.flatMap(c -> c.createMessage("The argument should contain the plantuml code between `"));
        }
        String plantUmlCode = matcher.group(0);
        System.out.println(plantUmlCode);

        return channel.flatMap(c -> c.createMessage("Diagram Created"));
    }

}
