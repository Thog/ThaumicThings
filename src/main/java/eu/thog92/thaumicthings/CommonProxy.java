package eu.thog92.thaumicthings;

import cpw.mods.fml.common.registry.GameRegistry;
import eu.thog92.thaumicthings.blocks.BlockExtraLifter;
import eu.thog92.thaumicthings.tileentity.TileEntityExtraLifter;
import net.minecraft.block.Block;

public class CommonProxy
{

    public Block extraLifter;

    public void registerRenders()
    {

    }

    public void initContents()
    {
        extraLifter = new BlockExtraLifter();
    }

    public void sparkle(float x, float y, float z, float size, int color, double motionX, double motionY, double motionZ)
    {

    }

    public void initTiles()
    {
        GameRegistry.registerTileEntity(TileEntityExtraLifter.class, "TileEntityExtraLifter");
    }
}
