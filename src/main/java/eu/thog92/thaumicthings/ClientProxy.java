package eu.thog92.thaumicthings;

import cpw.mods.fml.client.registry.RenderingRegistry;
import eu.thog92.thaumicthings.client.render.BlockExtraLifterRenderer;

public class ClientProxy extends CommonProxy
{

    public static int extraLifter = -1;

    public void registerRenders()
    {
        extraLifter = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BlockExtraLifterRenderer());
    }
}
