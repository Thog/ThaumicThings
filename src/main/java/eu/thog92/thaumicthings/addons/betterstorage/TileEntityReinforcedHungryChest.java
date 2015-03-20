package eu.thog92.thaumicthings.addons.betterstorage;

import net.mcft.copy.betterstorage.tile.entity.TileEntityReinforcedChest;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Thog92 on 20/03/2015.
 */
public class TileEntityReinforcedHungryChest extends TileEntityReinforcedChest
{
    @Override
    protected String getConnectableName()
    {
        return "reinforcedHungryChest";
    }
}
