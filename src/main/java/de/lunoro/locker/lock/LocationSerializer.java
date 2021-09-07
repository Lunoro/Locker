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

public class LocationSerializer implements TypeSerializer<Location> {

    @Override
    public Location<World> deserialize(@NonNull TypeToken<?> typeToken, @NonNull ConfigurationNode value) throws ObjectMappingException {
        String name = value.getNode("world").getValue(TypeToken.of(String.class));
        TypeToken<Double> doubleTypeToken = TypeTokens.DOUBLE_TOKEN;
        double x = value.getNode("x").getValue(doubleTypeToken);
        double y = value.getNode("y").getValue(doubleTypeToken);
        double z = value.getNode("z").getValue(doubleTypeToken);
        assert name != null;
        if (!Sponge.getServer().getWorld(name).isPresent()) {
            System.out.println("This world does not exist");
            return null;
        }
        World w = Sponge.getServer().getWorld(name).get();
        return w.getLocation(x, y, z);
    }


    @Override
    public void serialize(TypeToken<?> typeToken, Location loc, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("world").setValue(Sponge.getServer().getWorld(loc.createSnapshot().getWorldUniqueId()).get().getName());
        value.getNode("x").setValue(loc.getX());
        value.getNode("y").setValue(loc.getY());
        value.getNode("z").setValue(loc.getZ());
    }
}