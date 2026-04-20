package net.darkhax.anvilrepairing.fabric;

import net.darkhax.anvilrepairing.common.impl.AnvilRepairing;
import net.fabricmc.api.ModInitializer;

public class AnvilRepairingFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        AnvilRepairing.LOG.debug("Initializing AnvilRepairing");
    }
}