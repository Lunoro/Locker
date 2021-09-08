package de.lunoro.locker.listeners;

import de.lunoro.locker.lock.Lock;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.text.Text;

public class BlockPlaceListener {

    @Listener
    public void onBlockPlace(ChangeBlockEvent.Place event) {
        BlockSnapshot block = event.getTransactions().get(0).getFinal();
        Player player = (Player) event.getSource();
        if (block.getState().getType().equals(BlockTypes.CHEST)) {
            Chest chest = (Chest) block.getState();
            new Lock(player.getUniqueId(), chest);
            // DEBUG
            player.sendMessage(Text.of("Chest placed!"));
        }
    }
}
