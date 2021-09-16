package de.lunoro.locker.lock;

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
        mySQL.update("INSERT INTO Locker (owner, xLocation, yLocation, zLocation ,trustedMembers) VALUES ('"
                + lock.getOwner().toString() + "','" + lock.getBlockLocation().getBlockX() + "','" + lock.getBlockLocation().getBlockY() + "','" + lock.getBlockLocation().getBlockZ() + "','" + convertListToString(lock.getTrustedMembers()) + "')");
        System.out.println();
    }

    private String convertListToString(List<UUID> list) {
        if (list.isEmpty()) {
            StringBuilder returningString = new StringBuilder();
            for (UUID uuid : list) {
                returningString.append(", ").append(uuid.toString());
            }
            return returningString.toString();
        }
        return " ";
    }
}
