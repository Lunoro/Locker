package de.lunoro.locker.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Lock {

    public UUID owner;
    public Location blockLocation;
    public List<UUID> trustedMembers;

    public void trust(Player player) {
        trustedMembers.add(player.getUniqueId());
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
