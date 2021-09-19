package de.lunoro.locker.listeners;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import de.lunoro.locker.util.AdjoiningLockUtil;
import de.lunoro.locker.util.ValidLockBlockCheckUtil;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class BlockPlaceListener {

    @Listener
    public void onBlockPlace(ChangeBlockEvent.Place event) {
        BlockSnapshot block = event.getTransactions().get(0).getFinal();
        Optional<Player> optionalPlayer = event.getCause().first(Player.class);
        System.out.println(LockContainer.getInstance().getLockList());
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            if (ValidLockBlockCheckUtil.isValidLockBlock(block.getState().getType())) {
                Lock newLock = new Lock(player.getUniqueId(), block.getLocation().get());
                LockContainer.getInstance()
                        .addLock(newLock);
                player.sendMessage(Text.of("Locked block placed."));
                if (newLock.getBlockTypeOfLock().getName().contains("_door")) {
                    createDoor(newLock, player);
                }
            }
        }
    }

    private void createDoor(Lock lock, Player player) {
        Location<World> otherDoorHalfLocation = AdjoiningLockUtil.getUpperOrUnderAdjoiningLockLocation(lock);
        LockContainer.getInstance().addLock(new Lock(player.getUniqueId(), otherDoorHalfLocation));
    }
}
