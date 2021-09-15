package de.lunoro.locker.config;

import de.lunoro.locker.Locker;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

    private static Config instance;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode rootNode;
    private final Path path;
    private final Locker locker;

    private Config() {
        locker = Locker.getInstance();
        path = Paths.get(locker.getConfigDir().toString() + "\\config.conf");
        loader = HoconConfigurationLoader.builder().setPath(path).build();
        initializeNode();
    }

    private void initializeNode() {
        try {
            rootNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (!Files.exists(path)) {
                Sponge.getAssetManager().getAsset(locker, "config.conf").get().copyToFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            loader.save(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CommentedConfigurationNode getNode(String... nodePath) {
        return rootNode.getNode((Object) nodePath);
    }

    public static Config getInstance() {
        if (instance == null) {
            return new Config();
        }
        return instance;
    }
}
