package eu.thog92.thaumicthings;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import eu.thog92.thaumicthings.client.render.BlockExtraLifterRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXSparkle;

public class ClientProxy extends CommonProxy
{

    public static int extraLifter = -1;

    public void registerRenders()
    {
        extraLifter = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BlockExtraLifterRenderer());
    }


    public void sparkle(float x, float y, float z, float size, int color,
                        float gravity, double motionX, double motionY, double motionZ)
    {
        if ((getClientWorld() == null)
                || (getClientWorld().rand.nextInt(6) >= particleCount(2)))
            return;
        FXSparkle fx = new FXSparkle(getClientWorld(), x, y, z, size, color, 6);

        fx.noClip = true;
        fx.motionX = motionX;
        fx.motionY = motionY;
        fx.motionZ = motionZ;
        fx.setGravity(gravity);
        ParticleEngine.instance.addEffect(getClientWorld(), fx);
    }

    private WorldClient getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    public int particleCount(int base)
    {
        if (FMLClientHandler.instance().getClient().gameSettings.particleSetting == 2)
            return 0;
        return ((FMLClientHandler.instance().getClient().gameSettings.particleSetting == 1) ? base * 1
                : base * 2);
    }
}
