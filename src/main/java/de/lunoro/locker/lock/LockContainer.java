package de.lunoro.locker.lock;

import de.lunoro.locker.Locker;
import de.lunoro.locker.config.Config;
import de.lunoro.locker.lock.sql.LockSQLLoader;
import de.lunoro.locker.sql.SQL;
import lombok.Getter;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.nio.file.Paths;
import java.util.List;

public class LockContainer {

    private static LockContainer instance;
    @Getter
    private List<Lock> lockList;
    private LockLoader lockLoader;
    private LockSQLLoader lockSQLLoader;
    private final boolean sqlEnabled;

    private LockContainer() {
        sqlEnabled = Config.getInstance().getNode("useSql").getBoolean();
        if (sqlEnabled) {
            lockSQLLoader = LockSQLLoader.getInstance();
        } else {
            lockLoader = new LockLoader(Paths.get(Locker.getInstance().getConfigDir() + "\\lock.conf"));
        }
    }

    public void load() {
        System.out.println(sqlEnabled);
        if (sqlEnabled) {
            lockList = lockSQLLoader.load();
        } else {
            lockList = lockLoader.load();
        }
    }

    public void save() {
        if (sqlEnabled) {
            lockSQLLoader.clear();
            for (Lock lock : lockList) {
                lockSQLLoader.save(lock);
            }
        } else {
            lockLoader.clear();
            for (Lock lock : lockList) {
                lockLoader.save(lock);
            }
        }
    }

    public void addLock(Lock lock) {
        lockList.add(lock);
    }

    public void delLock(Location<World> location) {
        Lock lock = get(location);
        lockList.remove(lock);
    }

    public void delLock(Lock lock) {
        lockList.remove(lock);
    }

    public Lock get(Location<World> location) {
        for (Lock lock : lockList) {
            if (lock.getBlockLocation().equals(location)) {
                return lock;
            }
        }
        return null;
    }

    public static LockContainer getInstance() {
        if (instance == null) {
            instance = new LockContainer();
        }
        return instance;
    }
}