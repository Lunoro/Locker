package de.lunoro.locker.listeners;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import de.lunoro.locker.util.ValidLockBlockCheckUtil;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class BlockInteractListener {

    @Listener
    public void onInteract(InteractBlockEvent.Secondary event) {
        if (!(event.getSource() instanceof Player)) return;
        Player player = (Player) event.getSource();
        BlockSnapshot targetBlock = event.getTargetBlock();
        Optional<Location<World>> locOpt = targetBlock.getLocation();
        if (ValidLockBlockCheckUtil.isValidLockBlock(targetBlock.getState().getType())) {
            if (!(locOpt.isPresent())) return;
            Location<World> targetBlockLocation = locOpt.get();
            System.out.println(targetBlockLocation.getBlock().getName());
            Lock lockedBlock = LockContainer.getInstance().get(targetBlockLocation);
            if (lockedBlock == null) return;
            if (ValidLockBlockCheckUtil.isValidLockBlock(targetBlock.getState().getType())) {
                System.out.println(Sponge.getServer().getPlayer(lockedBlock.getOwner()));
                if (!lockedBlock.isPlayerTrusted(player)) {
                    player.sendMessage(Text.of("Chest is locked!"));
                    event.setCancelled(true);

                }
            }
        }
    }
}
