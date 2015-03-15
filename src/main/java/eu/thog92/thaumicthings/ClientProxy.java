package eu.thog92.thaumicthings;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import eu.thog92.thaumicthings.client.render.BlockExtraLifterRenderer;
import eu.thog92.thaumicthings.entities.EntityBottleEthereal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXSparkle;

public class ClientProxy extends CommonProxy
{

    public static int extraLifter = -1;

    public void registerRenders()
    {
        extraLifter = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BlockExtraLifterRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityBottleEthereal.class, new RenderSnowball(this.bottleEthereal));
    }


    public void sparkle(float x, float y, float z, float size, int color, double motionX, double motionY, double motionZ)
    {
        if ((getClientWorld() == null)
                || (getClientWorld().rand.nextInt(6) >= particleCount(2)))
            return;
        FXSparkle fx = new FXSparkle(getClientWorld(), x, y, z, size, color, 6);

        fx.noClip = true;
        fx.motionX = motionX;
        fx.motionY += motionY;
        fx.motionZ = motionZ;
        ParticleEngine.instance.addEffect(getClientWorld(), fx);
    }

    private WorldClient getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    private int particleCount(int base)
    {
        if (FMLClientHandler.instance().getClient().gameSettings.particleSetting == 2)
            return 0;
        return ((FMLClientHandler.instance().getClient().gameSettings.particleSetting == 1) ? base : base * 2);
    }

    public void onBottleBreak(Item item, World world, double x, double y, double z)
    {
        String s = "iconcrack_" + Item.getIdFromItem(item) + "_" + 0;

        for (int k1 = 0; k1 < 8; ++k1)
        {
            Minecraft.getMinecraft().renderGlobal.spawnParticle(s, x, y, z, world.rand.nextGaussian() * 0.15D, world.rand.nextDouble() * 0.2D, world.rand.nextGaussian() * 0.15D);
        }

        world.playSound(x, y, z, "game.potion.smash", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F, false);
    }
}
