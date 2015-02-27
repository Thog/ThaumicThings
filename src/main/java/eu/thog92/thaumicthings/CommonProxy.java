package eu.thog92.thaumicthings;

import eu.thog92.thaumicthings.blocks.BlockExtraLifter;
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

    public void sparkle(float x, float y, float z, float size, int color,
            float gravity, double motionX, double motionY, double motionZ)
    {
        
    }
}
