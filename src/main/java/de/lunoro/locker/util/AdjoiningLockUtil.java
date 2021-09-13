package de.lunoro.locker.util;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class AdjoiningLockUtil {

    private static final AdjoiningLockUtil instance = new AdjoiningLockUtil();

    private AdjoiningLockUtil() {
    }

    private Location<World> lockLocation;

    public Lock getUpperOrUnderAdjoiningLock(Lock lock) {
        Location<World> adjoiningLockLocation = getUpperOrUnderAdjoiningLockLocation(lock);
        return LockContainer.getInstance().get(adjoiningLockLocation);
    }

    public Location<World> getUpperOrUnderAdjoiningLockLocation(Lock lock) {
        lockLocation = lock.getBlockLocation();
        for (int i = lockLocation.getBlockY() - 1; i <= lockLocation.getBlockY() + 1; i++) {
            System.out.println("In for loop");
            Location<World> location = new Location<>(lockLocation.getExtent(), lockLocation.getBlockX(), i, lockLocation.getBlockZ());
            System.out.println(location);
            if (location.getBlockType().equals(lock.getBlockTypeOfLock())) {
                if (lockLocation.equals(location)) continue;
                return location;
            }
        }
        return null;
    }

    // Checks the grid that way
    // 0 X 0
    // X C X
    // 0 X 0
    // C = chests; X = checked blocks; 0 = not checked blocks

    public Lock getAdjoiningLock(Lock lock) {
        return returnNextLockOnZAndXAxis(lock);
    }

    private Lock returnNextLockOnZAndXAxis(Lock lock) {
        lockLocation = lock.getBlockLocation();
        for (int i = lockLocation.getBlockZ() - 1; i <= lockLocation.getBlockZ() + 1; i++) {
            Location<World> location = new Location<>(lockLocation.getExtent(), lockLocation.getBlockX(), lockLocation.getBlockY(), i);
            Lock adjoiningLock = LockContainer.getInstance().get(location);
            if (adjoiningLock != null) {
                if (adjoiningLock.getBlockTypeOfLock().equals(lock.getBlockTypeOfLock())) {
                    if (lockLocation.equals(location)) continue;
                    return adjoiningLock;
                }
            }
        }
        return returnAdjoiningLockOnXAxis(lock);
    }

    private Lock returnAdjoiningLockOnXAxis(Lock lock) {
        lockLocation = lock.getBlockLocation();
        for (int i = lockLocation.getBlockX() - 1; i <= lockLocation.getBlockX() + 1; i++) {
            Location<World> location = new Location<>(lockLocation.getExtent(), i, lockLocation.getBlockY(), lockLocation.getBlockZ());
            Lock adjoiningLock = LockContainer.getInstance().get(location);
            if (adjoiningLock != null) {
                if (adjoiningLock.getBlockTypeOfLock().equals(lock.getBlockTypeOfLock())) {
                    if (lockLocation.equals(location)) continue;
                    return adjoiningLock;
                }
            }
        }
        return null;
    }

    public static AdjoiningLockUtil getInstance() {
        return instance;
    }
}