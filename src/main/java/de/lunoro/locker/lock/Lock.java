package de.lunoro.locker.lock;

import de.lunoro.locker.util.AdjoiningLockUtil;
import lombok.Getter;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;

@Getter
public class Lock {

    private final UUID owner;
    private final Location<World> blockLocation;
    private List<UUID> trustedMembers;

    public Lock(UUID owner, Location<World> blockLocation) {
        this.blockLocation = blockLocation;
        this.owner = owner;
        this.trustedMembers = new ArrayList<>(Collections.singletonList(owner));
        System.out.println(this.getBlockLocation());
    }

    public Lock(UUID owner, Location blockLocation, List<UUID> trustedMembers) {
        this.owner = owner;
        this.blockLocation = blockLocation;
        this.trustedMembers = trustedMembers;
    }

    public void trust(Player player) {
        trustedMembers.add(player.getUniqueId());
    }

    public void unlockSingleBlock() {
        LockContainer.getInstance().delLock(this);
    }

    public void unlock() {
        unlockHorizontalAdjoiningLock();
        unlockVerticalAdjoiningLock();
        LockContainer.getInstance().delLock(this);
        System.out.println("Block unlocked");
    }

    private void unlockHorizontalAdjoiningLock() {
        Lock horizontalAdjoiningLock = AdjoiningLockUtil.getHorizontalAdjoiningLock(this);
        if (horizontalAdjoiningLock != null && horizontalAdjoiningLock.getBlockTypeOfLock().getName().contains("_door")) {
            LockContainer.getInstance().delLock(horizontalAdjoiningLock);
            System.out.println("Door unlocked");
        }
    }

    private void unlockVerticalAdjoiningLock() {
        Lock verticalAdjoiningLock = AdjoiningLockUtil.getVerticalAdjoiningLock(this);
        if (verticalAdjoiningLock != null && verticalAdjoiningLock.getBlockTypeOfLock().getName().contains("chest")) {
            LockContainer.getInstance().delLock(verticalAdjoiningLock);
            System.out.println("Double Chest unlocked");
        }
    }

    public boolean isPlayerTrusted(Player player) {
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
