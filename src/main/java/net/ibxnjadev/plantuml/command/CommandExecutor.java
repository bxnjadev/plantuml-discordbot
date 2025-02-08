package net.ibxnjadev.plantuml.command;

import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public interface CommandExecutor {

    /**
     * This method is executed when a user interact
     * with a command from this bot
     * @param args array that contains all arguments
     * @param message the message
     * @return a action that represent the response that execute the user
     */

    Mono<? extends Object> execute(String[] args,
                                   Message message);

}
