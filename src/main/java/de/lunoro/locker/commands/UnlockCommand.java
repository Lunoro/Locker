package de.lunoro.locker.commands;

import de.lunoro.locker.lock.LockContainer;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class UnlockCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) return null;
        Player player = (Player) src;
        BlockRay<World> blockRay = BlockRay.from(player).whilst(BlockRay.notAirFilter()).build();
        Location<World> lookedBlockLocation = blockRay.end().get().getLocation();
        if (lookedBlockLocation.getBlock().getType().equals(BlockTypes.CHEST)) {
            LockContainer.getInstance().delLock(lookedBlockLocation);
        }
        return CommandResult.success();
    }
}