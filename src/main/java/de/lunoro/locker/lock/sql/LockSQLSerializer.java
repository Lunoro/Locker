package de.lunoro.locker.lock.sql;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.sql.SQL;

import java.util.List;
import java.util.UUID;

public class LockSQLSerializer {

    private SQL mySQL = SQL.getInstance();

    public Lock deserialize() {
        return null;
    }

    public void serialize(Lock lock) {
        // TODO: 16.09.2021 table
        mySQL.update("INSERT INTO Locker (owner, worldUuid, blockX, blockY, blockZ ,trustedMembers) VALUES ('"
                + lock.getOwner().toString() +
                "','" + lock.getBlockLocation().getExtent().getUniqueId().toString() +
                "','" + lock.getBlockLocation().getBlockX() +
                "','" + lock.getBlockLocation().getBlockY() +
                "','" + lock.getBlockLocation().getBlockZ() +
                "','" + convertListToString(lock.getTrustedMembers()) +
                "')");
    }

    private String convertListToString(List<UUID> list) {
        if (!(list.isEmpty())) {
            StringBuilder returningString = new StringBuilder();
            for (UUID uuid : list) {
                returningString.append(", ").append(uuid.toString());
            }
            return returningString.toString();
        }
        return " ";
    }
}