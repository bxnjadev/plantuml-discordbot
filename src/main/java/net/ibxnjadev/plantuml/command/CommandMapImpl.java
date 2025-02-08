package net.ibxnjadev.plantuml.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandMapImpl implements CommandMap {

    private final Map<String, CommandExecutor> commands = new HashMap<>();

    @Override
    public void register(String name, CommandExecutor executor) {
        name = name.toLowerCase();
        System.out.println("Put = " + name);
        commands.put(name, executor);
    }

    @Override
    public void unregister(String name) {
        commands.remove(name);
    }

    @Override
    public CommandExecutor getExecutor(String name) {
        return commands.get(name);
    }

    @Override
    public Collection<CommandExecutor> all() {
        return commands.values();
    }
}
