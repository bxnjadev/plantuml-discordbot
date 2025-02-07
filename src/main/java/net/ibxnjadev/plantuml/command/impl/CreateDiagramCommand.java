package net.ibxnjadev.plantuml.command.impl;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import net.ibxnjadev.plantuml.command.CommandExecutor;
import reactor.core.publisher.Mono;

public class CreateDiagramCommand implements CommandExecutor  {

    @Override
    public Mono<? extends Object> execute(String[] args,
                                          Guild guild,
                                          Member member,
                                          Message message) {

        String name = args[0];
        if(!name.equalsIgnoreCase("d") &&
                !name.equalsIgnoreCase("diagram")) {
            return Mono.empty();
        }
        Mono<MessageChannel> channel = message.getChannel();

        return channel.flatMap(c -> c.createMessage("Creating Diagram..."));
    }

}
