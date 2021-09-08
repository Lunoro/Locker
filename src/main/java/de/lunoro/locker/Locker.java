package de.lunoro.locker;

import com.google.inject.Inject;
import de.lunoro.locker.listeners.BlockPlaceListener;
import de.lunoro.locker.listeners.BlockInteractListener;
import de.lunoro.locker.lock.LockContainer;
import lombok.Getter;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;

@Plugin(
        id = "locker",
        name = "Locker",
        version = "1.0-SNAPSHOT",
        description = "a sponge plugin for locking chests and other things.",
        authors = {
                "Lunoro"
        }
)
public class Locker {

    private LockContainer lockContainer;

    @Getter
    private static Locker instance;

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    @Getter
    private Path configDir;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        lockContainer = LockContainer.getInstance();
        instance = this;
        registerListeners();
        lockContainer.load();
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent event) {
        lockContainer.save();
    }

    private void registerListeners() {
        Sponge.getEventManager().registerListeners(this, new BlockPlaceListener());
        Sponge.getEventManager().registerListeners(this, new BlockInteractListener());
    }
}
