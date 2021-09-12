package de.lunoro.locker.util;

import org.spongepowered.api.block.BlockType;

public class ValidLockBlockCheckUtil {

    public static boolean isValidLockBlock(BlockType blockType) {
        // I use #contains("_DOOR") here because the blocktype trapdoor contains "DOOR" in his string too.
        return blockType.getName().contains("CHEST") || blockType.getName().contains("_DOOR") || blockType.getName().contains("FENCE_GATE");
    }
}
