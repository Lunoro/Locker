package de.lunoro.locker.commands;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import de.lunoro.locker.sql.SQL;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import sun.misc.UUDecoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SQLCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        // TODO: 16.09.2021 remove this | pure dbug command
        SQL.getInstance().update("TRUNCATE locker");
        for (Lock lock : LockContainer.getInstance().getLockList()) {
            if (lock != null) {
                System.out.println("Upload to database");
                SQL.getInstance().update("INSERT INTO Locker (owner, worldName, blockX, blockY, blockZ ,trustedMembers) VALUES ('"
                        + lock.getOwner().toString() +
                        "','" + lock.getBlockLocation().getExtent().getName() +
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
                returningString.append(uuid.toString()).append(",");
            }
            return returningString.toString();
        }
        return " ";
    }

    private List<UUID> convertStringToList(String uuids) {
        List<UUID> list = new ArrayList<>();
        for (String s : uuids.split(",")) {
            UUID newUUID = UUID.fromString(s);
            list.add(newUUID);
        }
        return list;
    }
}
