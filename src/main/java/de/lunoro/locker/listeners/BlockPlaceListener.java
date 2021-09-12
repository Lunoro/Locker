package de.lunoro.locker.listeners;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import de.lunoro.locker.util.ValidLockBlockCheckUtil;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class BlockPlaceListener {

    @Listener
    public void onBlockPlace(ChangeBlockEvent.Place event) {
        BlockSnapshot block = event.getTransactions().get(0).getFinal();
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (optPlayer.isPresent()) {
            Player player = optPlayer.get();
            System.out.println(block.getState().getType().getName());
            if (ValidLockBlockCheckUtil.isValidLockBlock(block.getState().getType())) {
                LockContainer.getInstance().addLock(new Lock(player.getUniqueId(), block.getLocation().get()));
                player.sendMessage(Text.of("Locked block placed!"));
            }
        }
    }
}
