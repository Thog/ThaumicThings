package eu.thog92.thaumicthings.entities;

import eu.thog92.thaumicthings.ThaumicThings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

/**
 * Created by Thog92 on 10/03/2015.
 */
public class EntityBottleEthereal extends EntityThrowable
{
    public EntityBottleEthereal(World world)
    {
        super(world);
    }

    public EntityBottleEthereal(World world, EntityLivingBase player)
    {
        super(world, player);
    }

    protected float getGravityVelocity()
    {
        return 0.05F;
    }

    protected float func_70182_d()
    {
        return 0.5F;
    }

    protected float func_70183_g()
    {
        return -20.0F;
    }

    @Override
    protected void onImpact(MovingObjectPosition movingObjectPosition)
    {
        if (!worldObj.isRemote)
        {
            //TODO: Make this work!
            int entityPosX = (int)this.posX;
            int entityPosY = (int)this.posY;
            int entityPosZ = (int)this.posZ;

            for(int a1 = 0; a1 < 10; ++a1) {
                int x = entityPosX + (int)((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                int z = entityPosZ + (int)((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                if((this.worldObj.getBiomeGenForCoords(x + entityPosX, z + entityPosZ).biomeID == Config.biomeTaintID || this.worldObj.getBiomeGenForCoords(x + entityPosX, z + entityPosZ).biomeID == Config.biomeEerieID || this.worldObj.getBiomeGenForCoords(x + entityPosX, z + entityPosZ).biomeID == Config.biomeMagicalForestID)) {
                    BiomeGenBase[] biomesForGeneration = null;
                    biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, x + entityPosX, z + entityPosZ, 1, 1);
                    if(biomesForGeneration != null && biomesForGeneration[0] != null) {
                        BiomeGenBase biome = biomesForGeneration[0];
                        if(biome.biomeID == ThaumcraftWorldGenerator.biomeTaint.biomeID) {
                            biome = BiomeGenBase.plains;
                        }

                        Utils.setBiomeAt(this.worldObj, x + entityPosX, z + entityPosZ, biome);
                    }
                }
            }
            this.setDead();
        } else
        {
            ThaumicThings.proxy.onBottleBreak(ThaumicThings.proxy.bottleEthereal, worldObj, posX, posY, posZ);
        }
    }
}
