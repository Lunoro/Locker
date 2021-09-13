package de.lunoro.locker.util;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class ViewedBlockUtil {

    public static Location<World> getViewedBlockLocation(Player player) {
        BlockRay<World> blockRay = BlockRay.from(player).select(BlockRay.blockTypeFilter(
                BlockTypes.CHEST,
                BlockTypes.ENDER_CHEST,
                BlockTypes.TRAPPED_CHEST,
                BlockTypes.FENCE_GATE,
                BlockTypes.ACACIA_FENCE_GATE,
                BlockTypes.BIRCH_FENCE_GATE,
                BlockTypes.JUNGLE_FENCE_GATE,
                BlockTypes.SPRUCE_FENCE_GATE,
                BlockTypes.DARK_OAK_FENCE_GATE,
                BlockTypes.WOODEN_DOOR,
                BlockTypes.DARK_OAK_DOOR,
                BlockTypes.ACACIA_DOOR,
                BlockTypes.BIRCH_DOOR,
                BlockTypes.JUNGLE_DOOR,
                BlockTypes.SPRUCE_DOOR
        )).distanceLimit(5).build();
        Optional<BlockRayHit<World>> hitOptional = blockRay.end();
        if (hitOptional.isPresent()) {
            Location<World> location = hitOptional.get().getLocation();
            System.out.println("LOCATION HERE" + location);
            return location;
        }
        return null;
    }
}