package net.darkhax.anvilrepairing.common.impl;

import net.darkhax.pricklemc.common.api.annotations.RangedFloat;
import net.darkhax.pricklemc.common.api.annotations.Value;

public class Config {

    @Value(comment = "The percent chance that the anvil will be repaired when using a repair item. 1=100%, 0.55=55%, etc.")
    @RangedFloat(min = 0, max = 1f)
    public float repair_chance = 1f;

    @Value(comment = "Should the player be given an advancement when repairing the anvil?")
    public boolean grant_advancement = true;
}