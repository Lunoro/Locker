package de.lunoro.locker.lock;

import lombok.Getter;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

public class LockedChest extends Lock {

    @Getter
    private Chest chest;

    public LockedChest(Player owner, Location blockLocation) {
        this.owner = owner;
        if (blockLocation.getBlock() instanceof Chest) {
            this.chest = (Chest) blockLocation.getBlock();
        }
    }
}
