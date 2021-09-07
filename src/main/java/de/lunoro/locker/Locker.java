package de.lunoro.locker;

import com.google.inject.Inject;
import de.lunoro.locker.commands.TestCommand;
import de.lunoro.locker.lock.Lock;
import de.lunoro.locker.lock.LockContainer;
import lombok.Getter;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.awt.*;
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
        instance = this;
        lockContainer = LockContainer.getInstance();
        lockContainer.load();
        logger.info(Color.RED + "loaded");
    }

    @Listener
    public void onServerStart(GameStoppedServerEvent event) {
        lockContainer.save();
    }
}
