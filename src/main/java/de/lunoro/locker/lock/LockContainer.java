package de.lunoro.locker.lock;

import de.lunoro.locker.Locker;
import lombok.Getter;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
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
        Lock lock = get(location);
        lockList.remove(lock);
        System.out.println("LOCK WAS REMOVED FROM LIST");
    }

    public Lock get(Location<World> location) {
        for (Lock lock : lockList) {
            if (lock.getBlockLocation().equals(location)) {
                return lock;
            }
        }
        return null;
    }

    // Checks the grid that way
    // 0 X 0
    // X C X
    // 0 X 0
    // C = chests; X = checked blocks; 0 = not checked blocks

    public Lock getLockNextTo(Lock lock) {
        return returnNextLockOnZAndXAxis(lock);
    }

    private Lock returnNextLockOnZAndXAxis(Lock lock) {
        Location<World> lockLocation = lock.getBlockLocation();
        for (int i = lockLocation.getBlockZ() - 1; i <= lockLocation.getBlockZ() + 1; i++) {
            System.out.println("SEARCH FOR Z: " + i);
            System.out.println(i);
            Location<World> location = new Location<World>(lockLocation.getExtent(), lockLocation.getBlockX(), lockLocation.getBlockY(), i);
            Lock adjoiningLock = get(location);
            if (adjoiningLock != null) {
                if (adjoiningLock.getBlockTypeOfLock().equals(lock.getBlockTypeOfLock())) {
                    if (lockLocation.equals(location)) continue;
                    System.out.println("RETURN BLOCK");
                    return adjoiningLock;
                }
            }
        }
        return returnNextLockOnXAxis(lock);
    }

    private Lock returnNextLockOnXAxis(Lock lock) {
        Location<World> lockLocation = lock.getBlockLocation();
        for (int i = lockLocation.getBlockX() - 1; i <= lockLocation.getBlockX() + 1; i++) {
            System.out.println("SEARCH FOR X: " + i);
            Location<World> location = new Location<World>(lockLocation.getExtent(), i, lockLocation.getBlockY(), lockLocation.getBlockZ());
            Lock adjoiningLock = get(location);
            if (adjoiningLock != null) {
                if (adjoiningLock.getBlockTypeOfLock().equals(lock.getBlockTypeOfLock())) {
                    if (lockLocation.equals(location)) continue;
                    System.out.println("RETURN BLOCK");
                    return adjoiningLock;
                }
            }
        }
        return null;
    }
}