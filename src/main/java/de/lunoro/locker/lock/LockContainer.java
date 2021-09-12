package de.lunoro.locker.lock;

import de.lunoro.locker.Locker;
import lombok.Getter;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.nio.file.Paths;
import java.util.List;

public class LockContainer {

    @Getter
    private static final LockContainer instance = new LockContainer();
    private List<Lock> lockList;
    private final LockLoader lockLoader;

    private LockContainer() {
        lockLoader = new LockLoader(Paths.get(Locker.getInstance().getConfigDir() + "\\lock.conf"));
    }

    public void load() {
        lockList = lockLoader.load();
    }

    public void save() {
        lockLoader.clear();
        for (Lock lock : lockList) {
            lockLoader.save(lock);
        }
    }

    public void addLock(Lock lock) {
        lockList.add(lock);
    }

    public void delLock(Location<World> location) {
        Lock removeLock = get(location);
        lockList.remove(removeLock);
    }

    public Lock get(Location<World> location) {
        for (Lock lock : lockList) {
            if (lock.getBlockLocation().equals(location)) {
                return lock;
            }
        }
        return null;
    }

    public List<Lock> getLockList() {
        return lockList;
    }
}
