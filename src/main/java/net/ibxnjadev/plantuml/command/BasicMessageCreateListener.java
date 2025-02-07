package net.ibxnjadev.plantuml.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class BasicMessageCreateListener implements Function<MessageCreateEvent, Publisher<? extends Object>> {

    private final String PREFIX_COMMAND = ";p";

    private final CommandMap commandMap;
    public BasicMessageCreateListener(CommandMap commandMap){
        this.commandMap = commandMap;
    }

    @Override
    public Mono<? extends Object> apply(MessageCreateEvent event) {
        Message message = event.getMessage();
        String rawMessage = message.getContent();
        String[] args = rawMessage.split(" ");
        String nameCommand = args[0];

        if(!nameCommand.equalsIgnoreCase(PREFIX_COMMAND)){
            return Mono.empty();
        }

        CommandExecutor executor = commandMap.getExecutor(nameCommand);


        return executor.execute(
                args,
                event.getGuild()
                        .block(),
                event.getMember().get(),
                message
        );
    }

}
