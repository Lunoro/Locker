package de.lunoro.locker.listeners;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import de.lunoro.locker.util.ValidLockBlockCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.item.inventory.Inventory;
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
        Optional<Location<World>> optionalLocation = targetBlock.getLocation();
        if (ValidLockBlockCheckUtil.isValidLockBlock(targetBlock.getState().getType())) {
            if (!(optionalLocation.isPresent())) return;
            Location<World> targetBlockLocation = optionalLocation.get();
            Lock lockedBlock = LockContainer.getInstance().get(targetBlockLocation);
            if (lockedBlock == null) return;
            if (ValidLockBlockCheckUtil.isValidLockBlock(targetBlock.getState().getType())) {
                if (!lockedBlock.isPlayerTrusted(player)) {
                    player.sendMessage(Text.of("Chest is locked!"));
                    event.setCancelled(true);
                }
            }
        }
    }
}
