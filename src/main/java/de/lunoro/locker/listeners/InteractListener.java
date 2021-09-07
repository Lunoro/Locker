package de.lunoro.locker.listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;

public class InteractListener {

    @Listener
    public void onInteract(InteractBlockEvent event) {
        if (!(event.getSource() instanceof Player)) return;

    }
}
