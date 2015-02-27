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
}
