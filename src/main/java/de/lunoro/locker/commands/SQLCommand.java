package de.lunoro.locker.commands;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import de.lunoro.locker.sql.SQL;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import sun.misc.UUDecoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        // TODO: 16.09.2021 remove this | pure dbug command
        List<UUID> uuids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            uuids.add(UUID.randomUUID());
        }
        for (Lock lock : LockContainer.getInstance().getLockList()) {
            SQL.getInstance().update("TRUNCATE locker");
            if (lock != null) {
                System.out.println(lock.getOwner().toString());
                System.out.println(lock.getBlockLocation());
                System.out.println(lock.getBlockLocation().getExtent());
                System.out.println(lock.getBlockLocation().getExtent().getUniqueId());
                SQL.getInstance().update("INSERT INTO Locker (owner, worldUuid, blockX, blockY, blockZ ,trustedMembers) VALUES ('"
                        + lock.getOwner().toString() +
                        "','" + lock.getBlockLocation().getExtent().getUniqueId() +
                        "','" + lock.getBlockLocation().getBlockX() +
                        "','" + lock.getBlockLocation().getBlockY() +
                        "','" + lock.getBlockLocation().getBlockZ() +
                        "','" + convertListToString(lock.getTrustedMembers()) +
                        "')");
            }
        }
        return CommandResult.success();
    }

    private String convertListToString(List<UUID> list) {
        if (!(list.isEmpty())) {
            StringBuilder returningString = new StringBuilder();
            for (UUID uuid : list) {
                returningString.append(uuid.toString()).append(", ");
            }
            return returningString.toString();
        }
        return " ";
    }
}
