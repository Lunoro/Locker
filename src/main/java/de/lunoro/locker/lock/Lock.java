package de.lunoro.locker.lock;

import de.lunoro.locker.util.AdjoiningLockUtil;
import lombok.Getter;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.UUID;

@Getter
public class Lock {

    private final UUID owner;
    private final Location<World> blockLocation;
    private List<UUID> trustedMembers;

    public Lock(UUID owner, Location<World> blockLocation) {
        this.blockLocation = blockLocation;
        this.owner = owner;
    }

    public Lock(UUID owner, Location blockLocation, List<UUID> trustedMembers) {
        this.blockLocation = blockLocation;
        this.owner = owner;
        this.trustedMembers = trustedMembers;
    }

    public void trust(Player player) {
        trustedMembers.add(player.getUniqueId());
    }

    public void unlock() {
        Lock lockNextTo = AdjoiningLockUtil.getInstance().getAdjoiningLock(this);
        LockContainer lockContainer = LockContainer.getInstance();
        unlockVerticalAdjoiningLock(lockNextTo);
        if (lockNextTo.getBlockTypeOfLock().getName().contains("chest")) {
            lockContainer.delLock(lockNextTo.getBlockLocation());
        }
        lockContainer.delLock(this.getBlockLocation());
    }

    private void unlockVerticalAdjoiningLock(Lock lockNextTo) {
        if (lockNextTo == null) {
            lockNextTo = AdjoiningLockUtil.getInstance().getUpperOrUnderAdjoiningLock(this);
            if (lockNextTo.getBlockTypeOfLock().getName().contains("_door")) {
                LockContainer.getInstance().delLock(lockNextTo.getBlockLocation());
            }
        }
    }

    public boolean isPlayerTrusted(Player player) {
        if (owner.equals(player.getUniqueId())) {
            return true;
        }
        for (UUID trustedMember : trustedMembers) {
            if (trustedMember.equals(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public BlockType getBlockTypeOfLock() {
        return blockLocation.getBlockType();
    }
}
