package eu.thog92.thaumicthings.client.render;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

/**
 * Desc...
 * Created by Thog the 26/12/2015
 */
public class ColoredSparkleFX extends EntityFX
{
    private final int multiplier;
    private final int particle;
    private final int blendmode;

    public ColoredSparkleFX(World world, double d, double d1, double d2, float f, float red, float green, float blue, int m) {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        this.particle = 16;
        this.blendmode = 1;

        red = red == 0.0F ? 1.0F : red;
        this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
        this.particleGravity = 0.0F;
        this.motionX = this.motionY = this.motionZ = 0.0D;
        this.particleScale *= f;
        this.particleMaxAge = 3 * m;
        this.multiplier = m;
        this.noClip = false;
        this.setSize(0.01F, 0.01F);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
    }

    public ColoredSparkleFX(World world, double x, double y, double z, float f, int type, int m) {
        this(world, x, y, z, f, 0.0F, 0.0F, 0.0F, m);
    }
}
