package de.lunoro.locker.lock;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.util.TypeTokens;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.UUID;

public class LockSerializer implements TypeSerializer<Lock> {

    @Override
    public Lock deserialize(@NonNull TypeToken type, @NonNull ConfigurationNode node) throws ObjectMappingException {
        UUID owner = node.getNode("owner").getValue(TypeTokens.UUID_TOKEN);
        Location<World> location = node.getNode("location").getValue(TypeToken.of(Location.class));
        List<UUID> trustedMembers = node.getNode("trustedMembers").getList(TypeTokens.UUID_TOKEN);
        return new Lock(owner, location, trustedMembers);
    }

    @Override
    public void serialize(@NonNull TypeToken<?> type, @Nullable Lock lock, @NonNull ConfigurationNode node) throws ObjectMappingException {
        node.getNode("owner").setValue(TypeTokens.UUID_TOKEN, lock.getOwner());
        System.out.println(lock.getBlockLocation());
        node.getNode("location").setValue(TypeToken.of(Location.class), lock.getBlockLocation());
        node.getNode("trustedMembers").setValue(new TypeToken<List<UUID>>() {
        }, lock.getTrustedMembers());
    }
}
