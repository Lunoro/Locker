package de.lunoro.locker.listeners;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import de.lunoro.locker.util.ValidLockBlockCheckUtil;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class BlockBreakListener {

    @Listener
    public void onBlockBreak(ChangeBlockEvent.Break event) {
        BlockSnapshot breakBlock = event.getTransactions().get(0).getFinal();
        Optional<Location<World>> optionalLocation = breakBlock.getLocation();
        if (!(optionalLocation.isPresent())) return;
        Location<World> location = optionalLocation.get();
        Lock lock = LockContainer.getInstance().get(location);
        if (ValidLockBlockCheckUtil.isValidLockBlock(breakBlock.getState().getType()))
            if (lock != null) {
                Sponge.getServer().getPlayer(lock.getOwner()).get().sendMessage(Text.of("Lock unlocked."));
                lock.unlock();
            }
    }
}
