package de.lunoro.locker.util;

import org.spongepowered.api.block.BlockType;

public class ValidLockBlockCheckUtil {

    public static boolean isValidLockBlock(BlockType blockType) {
        // I use #contains("_door") here because the blocktype trapdoor contains "door" in his name too.
        return blockType.getName().contains("chest") || blockType.getName().contains("_door") || blockType.getName().contains("fence_gate");
    }
}
