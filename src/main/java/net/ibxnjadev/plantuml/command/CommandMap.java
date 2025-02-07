package net.ibxnjadev.plantuml.command;

import java.util.Collection;

/**
 * Represent a collection of commands
 * for be executed
 */

public interface CommandMap {

    /**
     * Register a new command from a name
     * and interface that is executed
     * @param name the name command
     * @param executor the interface executor
     */

    void register(String name,
                  CommandExecutor executor);

    /**
     * Unregister a name from a name
     * @param name the name command
     */

    void unregister(String name);

    /**
     * Get the command executor from the
     * command name
     * @param name the command name
     * @return the command executor obtained, this
     * value can be null
     */

    CommandExecutor getExecutor(String name);

    /**
     * Provide all commands registered
     * @return all commands registered
     */

    Collection<CommandExecutor> all();

}
