package net.darkhax.anvilrepairing.neoforge;

import net.darkhax.anvilrepairing.common.impl.AnvilRepairing;
import net.neoforged.fml.common.Mod;

@Mod(AnvilRepairing.MOD_ID)
public class NeoForgeMod {

    public NeoForgeMod() {
        AnvilRepairing.LOG.debug("Initializing AnvilRepairing");
    }
}