package de.lunoro.locker.lock;

import com.google.common.reflect.TypeToken;
import lombok.NonNull;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.util.TypeTokens;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.UUID;

public class LocationSerializer implements TypeSerializer<Location> {

    @Override
    public Location<World> deserialize(@NonNull TypeToken<?> typeToken, @NonNull ConfigurationNode value) throws ObjectMappingException {
        String uuid = value.getNode("world").getValue(TypeTokens.STRING_TOKEN);
        TypeToken<Double> doubleTypeToken = TypeTokens.DOUBLE_TOKEN;
        double x = value.getNode("x").getValue(doubleTypeToken);
        double y = value.getNode("y").getValue(doubleTypeToken);
        double z = value.getNode("z").getValue(doubleTypeToken);
        assert uuid != null;
        if (Sponge.getServer().getWorld(uuid).isPresent()) {
            UUID worldUUID = UUID.fromString(uuid);
            World world = Sponge.getServer().getWorld(worldUUID).get();
            return new Location<World>(world, x, y, z);
        }
        return null;
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Location loc, ConfigurationNode value) throws ObjectMappingException {
        World world = (World) loc.getExtent();
        value.getNode("world").setValue(world.getUniqueId().toString());
        value.getNode("x").setValue(loc.getX());
        value.getNode("y").setValue(loc.getY());
        value.getNode("z").setValue(loc.getZ());
    }
}