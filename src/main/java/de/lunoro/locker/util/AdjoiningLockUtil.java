package de.lunoro.locker.util;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class AdjoiningLockUtil {

    private AdjoiningLockUtil() {
    }

    // Checks the grid that way
    // Y-Axis
    // | 0 X 0
    // | 0 B 0
    // | 0 X 0
    // L _ _ _ X-Axis
    // B = locked block; X = checked blocks; 0 = not checked blocks

    public static Lock getUpperOrUnderAdjoiningLock(Lock lock) {
        Location<World> adjoiningLockLocation = getUpperOrUnderAdjoiningLockLocation(lock);
        return LockContainer.getInstance().get(adjoiningLockLocation);
    }

    public static Location<World> getUpperOrUnderAdjoiningLockLocation(Lock lock) {
        Location<World> lockLocation = lock.getBlockLocation();
        for (int i = lockLocation.getBlockY() - 1; i <= lockLocation.getBlockY() + 1; i++) {
            Location<World> location = new Location<>(lockLocation.getExtent(), lockLocation.getBlockX(), i, lockLocation.getBlockZ());
            if (location.getBlockType().equals(lock.getBlockTypeOfLock())) {
                if (lockLocation.equals(location)) continue;
                return location;
            }
        }
        return null;
    }

    // Checks the grid that way
    // Z-Axis
    // | 0 X 0
    // | X B X
    // | 0 X 0
    // L _ _ _ X-Axis
    // B = locked block; X = checked blocks; 0 = not checked blocks

    public static Lock getAdjoiningLock(Lock lock) {
        return returnNextLockOnZAndXAxis(lock);
    }

    private static Lock returnNextLockOnZAndXAxis(Lock lock) {
        Location<World> lockLocation = lock.getBlockLocation();
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

    private static Lock returnAdjoiningLockOnXAxis(Lock lock) {
        Location<World> lockLocation = lock.getBlockLocation();
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
}