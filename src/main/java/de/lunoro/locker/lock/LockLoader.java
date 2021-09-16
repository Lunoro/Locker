package de.lunoro.locker.lock;

import com.google.common.reflect.TypeToken;
import de.lunoro.locker.sql.SQL;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import org.spongepowered.api.world.Location;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LockLoader {

    private final ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode node;

    public LockLoader(Path path) {
        this.loader = HoconConfigurationLoader.builder().setPath(path).build();
        registerSerializer();
    }

    LockSQLSerializer lockSQLSerializer = new LockSQLSerializer();

    public List<Lock> load() {
        List<Lock> list = new ArrayList<>();
        for (Object obj : node.getChildrenMap().keySet()) {
            try {
                Lock lock = node.getNode(obj.toString()).getValue(TypeToken.of(Lock.class));
                list.add(lock);
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void save(Lock lock) {
        try {
            node.getNode(UUID.randomUUID()).setValue(TypeToken.of(Lock.class), lock);
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        save();
    }

    private void registerSerializer() {
        TypeSerializerCollection serializerCollection = TypeSerializerCollection.defaults().newChild();
        serializerCollection.register(TypeToken.of(Location.class), new LocationSerializer());
        serializerCollection.register(TypeToken.of(Lock.class), new LockSerializer());
        ConfigurationOptions options = ConfigurationOptions.defaults()
                .withSerializers(serializerCollection);
        try {
            this.node = loader.load(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void save() {
        try {
            loader.save(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        node.setValue("");
    }
}
