package net.biryeongtrain.monster_cannibal;

import com.google.common.collect.ImmutableSet;
import io.icker.factions.api.persistents.Faction;
import net.minecraft.state.property.Property;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FactionProperty extends Property <Faction>{
    private final ImmutableSet<Faction> values = ImmutableSet.of(true, false);
    @Override
    public Collection<Faction> getValues() {
        return List.of();
    }

    @Override
    public String name(Faction value) {
        return "";
    }

    @Override
    public Optional<Faction> parse(String name) {
        return Optional.empty();
    }
}
