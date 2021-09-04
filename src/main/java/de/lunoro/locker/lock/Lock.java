package de.lunoro.locker.lock;

import lombok.Getter;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

import java.util.List;

public abstract class Lock {

    @Getter
    public Player owner;
    @Getter
    public Location blockLocation;
    @Getter
    public List<Player> trustedMembers;

    public void trust(Player player) {
        trustedMembers.add(player);
    }

    public Player searchForTrustedMember(Player player) {
        for (Player trustedMember : trustedMembers) {
            if (trustedMember.equals(player)) {
                return trustedMember;
            }
        }
        return null;
    }
}
