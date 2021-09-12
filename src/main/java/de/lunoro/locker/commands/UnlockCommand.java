package de.lunoro.locker.commands;

import de.lunoro.locker.util.ValidLockBlockCheckUtil;
import de.lunoro.locker.util.ViewedBlockUtil;
import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class UnlockCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return null;
        Player player = (Player) src;
        Location<World> viewedBlockLocation = ViewedBlockUtil.getViewedBlockLocation(player);
        BlockType viewedBlockType = viewedBlockLocation.getBlock().getType();
        player.sendMessage(Text.of(viewedBlockType.getName()));
        if (ValidLockBlockCheckUtil.isValidLockBlock(viewedBlockType)) {
            Lock lock = LockContainer.getInstance().get(viewedBlockLocation);
            if (lock == null) return CommandResult.empty();
            if (lock.getOwner().equals(player.getUniqueId())) {
                unlockChestNextToIfExists(lock);
                unlockChest(lock);
                player.sendMessage(Text.of("Chest unlocked"));
            }
        }
        return CommandResult.success();
    }

    private void unlockChest(Lock lock) {
        LockContainer.getInstance().delLock(lock.getBlockLocation());
    }

    private void unlockChestNextToIfExists(Lock lock) {
        System.out.println("TRY TO UNLOCK NEXT CHEST");
        Lock lockNextTo = LockContainer.getInstance().getLockNextTo(lock);
        if (lockNextTo == null) return;
        System.out.println(lockNextTo.getBlockTypeOfLock().getName());
        if (lockNextTo.getBlockTypeOfLock().getName().contains("chest")) {
            LockContainer.getInstance().delLock(lockNextTo.getBlockLocation());
            System.out.println("Near Chest Unlocked");
        }
    }
}