package de.lunoro.locker.listeners;

import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.carrier.Chest;
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
            player.sendMessage(Text.of("Test"));
            if (block.getState().getType().equals(BlockTypes.CHEST)) {
                LockContainer.getInstance().addLock(new Lock(player.getUniqueId(), block.getLocation().get()));
                // DEBUG
                player.sendMessage(Text.of("Chest placed!"));
                System.out.println(LockContainer.getInstance().getLockList());
            }
        }
    }
}
