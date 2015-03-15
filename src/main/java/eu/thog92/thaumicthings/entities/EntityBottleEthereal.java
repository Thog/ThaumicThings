package eu.thog92.thaumicthings.entities;

import eu.thog92.thaumicthings.CommonProxy;
import eu.thog92.thaumicthings.ThaumicThings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

import java.util.Iterator;
import java.util.List;

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

            List entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(5.0D, 5.0D, 5.0D));
            if (entities.size() > 0)
            {
                Iterator iterator = entities.iterator();

                while (iterator.hasNext())
                {
                    Object y = iterator.next();
                    EntityLivingBase entity = (EntityLivingBase) y;
                    if (entity instanceof ITaintedMob && !entity.isEntityUndead())
                        entity.addPotionEffect(new PotionEffect(CommonProxy.ethereal.getId(), 1200, 0, false));
                }
            }

            int entityPosX = (int) this.posX;
            int entityPosY = (int) this.posY;
            int entityPosZ = (int) this.posZ;

            for (int i = 0; i < 10; ++i)
            {
                int x = (int) ((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                int z = (int) ((this.rand.nextFloat() - this.rand.nextFloat()) * 5.0F);
                if ((this.worldObj.getBiomeGenForCoords(x + entityPosX, z + entityPosZ).biomeID == Config.biomeTaintID || this.worldObj.getBiomeGenForCoords(x + entityPosX, z + entityPosZ).biomeID == Config.biomeEerieID || this.worldObj.getBiomeGenForCoords(x + entityPosX, z + entityPosZ).biomeID == Config.biomeMagicalForestID))
                {
                    BiomeGenBase[] biomesForGeneration = null;
                    biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, x + entityPosX, z + entityPosZ, 1, 1);
                    if (biomesForGeneration != null && biomesForGeneration[0] != null)
                    {
                        BiomeGenBase biome = biomesForGeneration[0];
                        if (biome.biomeID == ThaumcraftWorldGenerator.biomeTaint.biomeID)
                        {
                            biome = BiomeGenBase.plains;
                        }

                        Utils.setBiomeAt(this.worldObj, x + entityPosX, z + entityPosZ, biome);
                        worldObj.markBlockRangeForRenderUpdate(x + entityPosX, entityPosY - 1, z + entityPosZ, x + entityPosX + 16, entityPosY, z + entityPosZ + 16);
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
