package net.ibxnjadev.plantuml.command.impl;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import net.ibxnjadev.plantuml.command.CommandExecutor;
import reactor.core.publisher.Mono;

public class DiagramCommand implements CommandExecutor  {

    @Override
    public Mono<? extends Object> execute(String[] args,
                                          Message message) {
        System.out.println("HOLAAAAA");
        String name = args[0];
        if(!name.equalsIgnoreCase("d") &&
                !name.equalsIgnoreCase("diagram")) {
            System.out.println("3");
            return Mono.empty();
        }
        Mono<MessageChannel> channel = message.getChannel();

        System.out.println("4");
        return channel.flatMap(c -> c.createMessage("Creating Diagram..."));
    }

}
