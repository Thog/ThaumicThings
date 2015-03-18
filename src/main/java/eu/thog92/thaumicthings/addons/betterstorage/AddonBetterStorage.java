package eu.thog92.thaumicthings.addons.betterstorage;

import net.minecraft.block.Block;

public class AddonBetterStorage
{
    public static Block hungryReinforcedChest;

    public static void preInit()
    {
        hungryReinforcedChest = new TileReinforcedHungryChest();
    }
}
