package de.lunoro.locker;

import com.google.inject.Inject;
import de.lunoro.locker.commands.TrustCommand;
import de.lunoro.locker.commands.UnlockCommand;
import de.lunoro.locker.listeners.BlockPlaceListener;
import de.lunoro.locker.listeners.BlockInteractListener;
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
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.nio.file.Files;
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
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        instance = this;
        lockContainer = LockContainer.getInstance();
        lockContainer.load();
        registerListeners();
        registerCommands();
    }

    @Listener
    public void onServerStop(GameStoppedServerEvent event) {
        lockContainer.save();
    }

    private void registerCommands() {
        Sponge.getCommandManager().register(this,
                CommandSpec.builder()
                        .executor(new TrustCommand())
                        .build(), "trust");
        Sponge.getCommandManager().register(this,
                CommandSpec.builder()
                        .executor(new UnlockCommand())
                        .description(Text.of(""))
                        .build(), "unlock");

    }

    private void registerListeners() {
        Sponge.getEventManager().registerListeners(this, new BlockPlaceListener());
        Sponge.getEventManager().registerListeners(this, new BlockInteractListener());
    }
}
