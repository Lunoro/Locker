package de.lunoro.locker.lock;

import de.lunoro.locker.util.AdjoiningLockUtil;
import lombok.Getter;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
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
    }

    public Lock(UUID owner, Location blockLocation, List<UUID> trustedMembers) {
        this.owner = owner;
        this.blockLocation = blockLocation;
        this.trustedMembers = trustedMembers;
    }

    public void trust(Player player) {
        trustedMembers.add(player.getUniqueId());
    }

    public void unlockSingleBlockOrDoor() {
        unlockHorizontalAdjoiningLock();
        LockContainer.getInstance().delLock(this);
    }

    public void unlock() {
        unlockHorizontalAdjoiningLock();
        unlockVerticalAdjoiningLock();
        LockContainer.getInstance().delLock(this);
    }

    private void unlockHorizontalAdjoiningLock() {
        Lock verticalAdjoiningLock = AdjoiningLockUtil.getVerticalAdjoiningLock(this);
        if (verticalAdjoiningLock != null && verticalAdjoiningLock.getBlockTypeOfLock().getName().contains("_door")) {
            LockContainer.getInstance().delLock(verticalAdjoiningLock);
        }
    }

    private void unlockVerticalAdjoiningLock() {
        Lock horizontalAdjoiningLock = AdjoiningLockUtil.getHorizontalAdjoiningLock(this);
        if (horizontalAdjoiningLock != null && horizontalAdjoiningLock.getBlockTypeOfLock().getName().contains("chest")) {
            Optional<TileEntity> optionalTile = horizontalAdjoiningLock.blockLocation.getTileEntity();
            if (!(optionalTile.isPresent())) return;
            Chest chest = (Chest) optionalTile.get();
            Optional<Inventory> optionalInventory = chest.getDoubleChestInventory();
            if (!(optionalInventory.isPresent())) return;
            Inventory doubleChestInventory = optionalInventory.get();
            if (doubleChestInventory.capacity() != 54) return;
            LockContainer.getInstance().delLock(horizontalAdjoiningLock);
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
