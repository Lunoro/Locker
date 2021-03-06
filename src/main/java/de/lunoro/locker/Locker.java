package de.lunoro.locker;

import com.google.inject.Inject;
import de.lunoro.locker.commands.TrustCommand;
import de.lunoro.locker.commands.UnlockCommand;
import de.lunoro.locker.config.Config;
import de.lunoro.locker.listeners.BlockBreakListener;
import de.lunoro.locker.listeners.BlockPlaceListener;
import de.lunoro.locker.listeners.BlockInteractListener;
import de.lunoro.locker.lock.LockContainer;
import de.lunoro.locker.sql.SQL;
import lombok.Getter;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.event.Listener;
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

    @Getter
    private LockContainer lockContainer;
    private Config config;
    private SQL sql;
    private boolean useSQL;

    private static Locker instance;

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    public Path getConfigDir() {
        return configDir;
    }

    @Listener
    public void onServerStart(GameStartingServerEvent event) {
        instance = this;
        initializeFiles();
        useSQL = Config.getInstance().getNode("useSql").getBoolean();
        sqlSetup();
        registerListeners();
        registerCommands();
    }

    private void sqlSetup() {
        if (useSQL) {
            sql = SQL.getInstance();
            sql.update("CREATE TABLE IF NOT EXISTS Locker (owner VARCHAR(64), worldName VARCHAR(64), blockX INT(64), blockY Int(64), blockZ INT(64), trustedMembers VARCHAR(64))");
        }
    }

    private void initializeFiles() {
        config = Config.getInstance();
        lockContainer = LockContainer.getInstance();
    }

    @Listener
    public void onServerStarted(GameStartedServerEvent event) {
        createDirectories();
        fileSetup();
    }

    private void fileSetup() {
        config.load();
        lockContainer.load();
    }

    @Listener
    public void onStoppedEvent(GameStoppedServerEvent event) {
        config.save();
        logger.info("Writing Data this might take a while.");
        logger.warn("---DONT SHUT THE SERVER DOWN!---");
        lockContainer.save();
        sqlDisconnect();
    }

    private void sqlDisconnect() {
        if (useSQL) {
            sql.disconnect();
        }
    }

    private void createDirectories() {
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Sponge.getEventManager().registerListeners(this, new BlockBreakListener());
    }

    public static Locker getInstance() {
        return instance;
    }
}
