package de.lunoro.locker;

import com.google.inject.Inject;
import de.lunoro.locker.commands.TestCommand;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

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

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .executor(new TestCommand()).build(), "test");
    }
}
