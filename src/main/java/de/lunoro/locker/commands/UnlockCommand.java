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
        if (!(src instanceof Player)) return CommandResult.empty();
        Player player = (Player) src;
        Location<World> viewedBlockLocation = ViewedBlockUtil.getViewedBlockLocation(player);
        BlockType viewedBlockType = viewedBlockLocation.getBlock().getType();
        if (ValidLockBlockCheckUtil.isValidLockBlock(viewedBlockType)) {
            Lock lock = LockContainer.getInstance().get(viewedBlockLocation);
            if (lock == null) return CommandResult.empty();
            if (lock.getOwner().equals(player.getUniqueId())) {
                lock.unlock();
                player.sendMessage(Text.of("Chest unlocked"));
            }
        }
        return CommandResult.success();
    }
}