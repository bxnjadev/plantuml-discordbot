package net.ibxnjadev.plantuml.command;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import net.ibxnjadev.plantuml.util.PlantumlArrays;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

public class BasicMessageCreateListener implements Function<MessageCreateEvent, Publisher<? extends Object>> {

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
        Optional<User> authorOptional = message.getAuthor();

        if (authorOptional.isPresent()
                && authorOptional.get().isBot()) {
            return Mono.empty();
        }

        CommandExecutor executor = commandMap.getExecutor(nameCommand);
        args = PlantumlArrays.slice(args, String.class, 1, args.length - 1);

        return executor.execute(
                args,
                message
        );
    }

}
