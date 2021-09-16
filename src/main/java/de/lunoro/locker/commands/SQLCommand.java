package de.lunoro.locker.commands;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import de.lunoro.locker.sql.SQL;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import java.util.List;

public class SQLCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        // TODO: 16.09.2021 remove this 
        List<Lock> lockList = LockContainer.getInstance().getLockList();
        for (Lock lock : lockList) {
            SQL.getInstance().update("INSERT INTO Locker (owner) VALUES ('" + lock.getOwner().toString() + "')");
        }
        return CommandResult.success();
    }
}
