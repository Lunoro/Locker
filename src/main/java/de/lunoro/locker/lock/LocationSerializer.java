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
        System.out.println("DESERIALIZE LOCATION");
        System.out.println("------START------");
        String worldName = value.getNode("world").getValue(TypeTokens.STRING_TOKEN);
        System.out.println("LOCATION WORLDUUID: " + worldName);
        TypeToken<Double> doubleTypeToken = TypeTokens.DOUBLE_TOKEN;
        double x = value.getNode("x").getValue(doubleTypeToken);
        System.out.println("LOCATION X: " + x);
        double y = value.getNode("y").getValue(doubleTypeToken);
        System.out.println("LOCATION Y: " + y);
        double z = value.getNode("z").getValue(doubleTypeToken);
        System.out.println("LOCATION Z: " + z);
        assert worldName != null;
        if (Sponge.getServer().getWorld(worldName).isPresent()) {
            System.out.println("LOCATION WORLDUUID: " + worldName);
            World world = Sponge.getServer().getWorld(worldName).get();
            System.out.println("LOCATION WORLD: " + world);
            Location<World> location = new Location<>(world, x, y, z);
            System.out.println("LOCATION LOCATION: " + location);
            System.out.println("------END------");
            return location;
        }
        return null;
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Location loc, ConfigurationNode value) throws ObjectMappingException {
        System.out.println("SERIALIZE LOCATION");
        System.out.println("------START------");
        World world = (World) loc.getExtent();
        System.out.println("LOCATION: " + world.getName());
        value.getNode("world").setValue(world.getName());
        System.out.println("LOCATION: " + world.getUniqueId());
        value.getNode("x").setValue(loc.getX());
        value.getNode("y").setValue(loc.getY());
        value.getNode("z").setValue(loc.getZ());
        System.out.println("------END------");
    }
}