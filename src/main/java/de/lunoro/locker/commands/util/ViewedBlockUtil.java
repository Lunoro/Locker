package de.lunoro.locker.commands.util;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class ViewedBlockUtil {

    public static Location<World> getViewedBlockLocation(Player player) {
        BlockRay<World> blockRay = BlockRay.from(player).select(BlockRay.blockTypeFilter(BlockTypes.CHEST)).distanceLimit(5).build();
        Optional<BlockRayHit<World>> hitOptional = blockRay.end();
        if (hitOptional.isPresent()) {
            Location<World> location = hitOptional.get().getLocation();
            System.out.println(location.getBlock().getName());
            System.out.println(location);
            return location;
        }
        return null;
    }
}