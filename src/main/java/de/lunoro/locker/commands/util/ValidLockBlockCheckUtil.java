package de.lunoro.locker.commands.util;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;

public class ValidLockBlockCheckUtil {

    public static boolean isValidLockBlock(BlockType blockType) {
        return blockType.equals(BlockTypes.CHEST) || blockType.equals(BlockTypes.WOODEN_DOOR) || blockType.equals(BlockTypes.FENCE_GATE);
    }
}
