package de.lunoro.locker.lock.sql;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.sql.SQL;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LockSQLSerializer {

    private SQL sql = SQL.getInstance();

    public Lock deserialize(ResultSet resultSet) {
        try {
            UUID owner = UUID.fromString(resultSet.getString(1));
            String worldName = resultSet.getString(2);
            int blockX = resultSet.getInt(3);
            int blockY = resultSet.getInt(4);
            int blockZ = resultSet.getInt(5);
            List<UUID> trustedMembers = convertStringToList(resultSet.getString(6));
            Optional<World> optionalWorld = Sponge.getServer().getWorld(worldName);
            if (optionalWorld.isPresent()) {
                World world = optionalWorld.get();
                Location<World> lockLocation = new Location<World>(world, blockX, blockY, blockZ);
                return new Lock(owner, lockLocation, trustedMembers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<UUID> convertStringToList(String uuids) {
        List<UUID> list = new ArrayList<>();
        for (String s : uuids.split(",")) {
            UUID newUUID = UUID.fromString(s);
            list.add(newUUID);
        }
        return list;
    }

    public void serialize(Lock lock) {
        // TODO: 16.09.2021 table
        sql.update("INSERT INTO Locker (owner, worldName, blockX, blockY, blockZ ,trustedMembers) VALUES ('"
                + lock.getOwner().toString() +
                "','" + lock.getBlockLocation().getExtent().getName() +
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
                returningString.append(uuid.toString()).append(",");
            }
            return returningString.toString();
        }
        return " ";
    }
}
