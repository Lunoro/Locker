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
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (optPlayer.isPresent()) {
            Player player = optPlayer.get();
            if (ValidLockBlockCheckUtil.isValidLockBlock(block.getState().getType())) {
                Lock newLock = new Lock(player.getUniqueId(), block.getLocation().get());
                LockContainer.getInstance().addLock(newLock);
                player.sendMessage(Text.of("Locked block placed."));
                System.out.println(newLock.getBlockLocation());
                if (newLock.getBlockTypeOfLock().getName().contains("_door")) {
                    System.out.println("Door detected!");
                    createDoor(newLock, player);
                }
            }
        }
    }

    private void createDoor(Lock lock, Player player) {
        Location<World> otherDoorHalfLocation = AdjoiningLockUtil.getInstance().getUpperOrUnderAdjoiningLockLocation(lock);
        System.out.println(otherDoorHalfLocation);
        LockContainer.getInstance().addLock(new Lock(player.getUniqueId(), otherDoorHalfLocation));
        System.out.println(LockContainer.getInstance().get(otherDoorHalfLocation).getOwner());
    }
}
