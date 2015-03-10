package eu.thog92.thaumicthings.tiles;

import eu.thog92.thaumicthings.blocks.BlockExtraLifter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.wands.IWandable;

import java.util.List;

public class TileEntityExtraLifter extends TileThaumcraft implements IWandable
{
    private int rangeAbove;
    private boolean requiresUpdate;
    private boolean wasDisabled;
    private float modifier = 1.0F;
    private boolean reverted;
    private int counter;

    public TileEntityExtraLifter()
    {
        this.counter = 0;
        this.rangeAbove = 0;
        this.requiresUpdate = true;
        this.wasDisabled = false;
    }

    public TileEntityExtraLifter(int metadata)
    {
        this();
        this.blockMetadata = metadata;

    }

    public boolean canUpdate()
    {
        return true;
    }

    public void updateEntity()
    {
        super.updateEntity();

        this.counter += 1;
        if ((this.requiresUpdate) || (this.counter % 100 == 0))
        {
            this.wasDisabled = isDisabled();

            this.requiresUpdate = false;

            int max = 10;
            int count = 1;


            while ((this.getNeighborBlock(count) instanceof BlockExtraLifter))
            {
                TileEntityExtraLifter tile = (TileEntityExtraLifter) this.getNeighborTile(count);
                if (tile.isDisabled())
                    this.wasDisabled = true;

                ++count;
                max += 10;
            }


            this.rangeAbove = 0;
            while ((this.rangeAbove < max) && (!(this.getNeighborBlock(-1 - this.rangeAbove).isOpaqueCube())))
            {
                this.rangeAbove += 1;
            }
        }


        if ((this.rangeAbove > 0) && (!(isDisabled())))
        {
            AxisAlignedBB aabb;

            if (blockMetadata == 0)
            {
                aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord - this.rangeAbove, this.zCoord, this.xCoord + 1, this.yCoord, this.zCoord + 1);
            } else if (blockMetadata == 2)
            {
                aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord - this.rangeAbove, this.xCoord + 1, this.yCoord + 1, this.zCoord);
            } else if (blockMetadata == 3)
            {
                aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + this.rangeAbove);
            } else if (blockMetadata == 4)
            {
                aabb = AxisAlignedBB.getBoundingBox(this.xCoord - this.rangeAbove, this.yCoord, this.zCoord, this.xCoord, this.yCoord + 1, this.zCoord + 1);
            } else if (blockMetadata == 5)
            {
                aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + this.rangeAbove, this.yCoord + 1, this.zCoord + 1);
            } else
            {
                aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord + 1,
                        this.zCoord, this.xCoord + 1, this.yCoord + 1
                                + this.rangeAbove, this.zCoord + 1);
            }

            List<Entity> targets = this.worldObj.getEntitiesWithinAABB(Entity.class, aabb);
            if (targets.size() > 0)
                for (Entity e : targets)
                {
                    if (blockMetadata == 0)
                    {
                        if (!this.reverted)
                            this.manageDownOrientation(e);
                        else
                            this.manageDefaultOrientation(e);
                    } else if (blockMetadata == 2)
                    {
                        e.motionZ = this.manageOrientation(e, modifier * 1.0F, e.motionZ);
                    } else if (blockMetadata == 3)
                    {
                        e.motionZ = this.manageOrientation(e, modifier * -1.0F, e.motionZ);
                    } else if (blockMetadata == 4)
                    {
                        e.motionX = this.manageOrientation(e, modifier * 1.0F, e.motionX);
                    } else if (blockMetadata == 5)
                    {
                        e.motionX = this.manageOrientation(e, modifier * -1.0F, e.motionX);
                    } else
                    {
                        if (!this.reverted)
                            this.manageDefaultOrientation(e);
                        else
                            this.manageDownOrientation(e);
                    }

                    if ((e instanceof EntityLivingBase) && ((EntityLivingBase) e).isJumping)
                        ((EntityLivingBase) e).jump();

                    // Disable damages
                    e.fallDistance = 0.0F;
                }
        }

    }

    private void manageDefaultOrientation(Entity e)
    {
        if (e.isSneaking())
        {
            if (e.motionY < 0.0D)
                e.motionY *= 0.8999999761581421D;
        } else if (e.motionY < 0.3499999940395355D)
            e.motionY += 0.1000000014901161D;
    }

    private void manageDownOrientation(Entity e)
    {
        if (e.isSneaking())
        {
            e.motionY += 0.1000000014901161D;
        } else if (e.motionY < 0.0D)
            e.motionY *= 0.8999999761581421D;
    }

    private double manageOrientation(Entity e, float modifier, double motion)
    {
        e.motionY = 0;

        if (e.isSneaking())
        {
            motion += modifier * 0.1000000014901161D;
        } else if ((modifier < 0 && motion > modifier * 0.3499999940395355D) || (modifier > 0 && motion < modifier * 0.3499999940395355))
            motion -= modifier * 0.1000000014901161D;

        return motion;
    }

    public boolean isDisabled()
    {
        return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) || this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord + 1, this.zCoord);
    }

    @Override
    public void onUsingWandTick(ItemStack wandStack, EntityPlayer player, int count)
    {

    }

    @Override
    public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player)
    {
        return wandstack;
    }

    @Override
    public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md)
    {
        if (player.isSneaking())
        {
            int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
            world.setBlockMetadataWithNotify(x, y, z, l, 2);
        } else
        {
            this.setInvert(!reverted);
        }
        return 0;
    }

    @Override
    public void onWandStoppedUsing(ItemStack wandStack, World world, EntityPlayer player, int count)
    {

    }

    @Override
    public void readCustomNBT(NBTTagCompound nbttagcompound)
    {
        this.reverted = nbttagcompound.getBoolean("reverted");
        this.updateModifier();
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setBoolean("reverted", this.reverted);
        this.updateModifier();
    }

    public void setInvert(boolean inverted)
    {
        this.reverted = inverted;
        this.updateModifier();
    }

    public void updateModifier()
    {
        if (this.reverted)
            this.modifier = -1.0F;
        else
            this.modifier = 1.0F;
    }

    public float getModifier()
    {
        return modifier;
    }

    public boolean lastDisabledStatus()
    {
        return wasDisabled;
    }

    public Block getNeighborBlock(int count)
    {
        switch (blockMetadata)
        {
            case 0:
                return worldObj.getBlock(xCoord, yCoord + count, zCoord);
            case 2:
                return this.worldObj.getBlock(this.xCoord, this.yCoord,
                        this.zCoord + count);
            case 3:
                return this.worldObj.getBlock(this.xCoord, this.yCoord,
                        this.zCoord - count);
            case 4:
                return this.worldObj.getBlock(this.xCoord + count, this.yCoord,
                        this.zCoord);
            case 5:
                return this.worldObj.getBlock(this.xCoord - count, this.yCoord,
                        this.zCoord);
            default:
                return worldObj.getBlock(xCoord, yCoord - count, zCoord);
        }
    }

    public TileEntity getNeighborTile(int count)
    {
        switch (blockMetadata)
        {
            case 0:
                return worldObj.getTileEntity(xCoord, yCoord + count, zCoord);
            case 2:
                return this.worldObj.getTileEntity(this.xCoord, this.yCoord,
                        this.zCoord + count);
            case 3:
                return this.worldObj.getTileEntity(this.xCoord, this.yCoord,
                        this.zCoord + count);
            case 4:
                return this.worldObj.getTileEntity(this.xCoord + count, this.yCoord,
                        this.zCoord);
            case 5:
                return this.worldObj.getTileEntity(this.xCoord - count, this.yCoord,
                        this.zCoord);
            default:
                return worldObj.getTileEntity(xCoord, yCoord - count, zCoord);
        }
    }

    public boolean isReverted()
    {
        return reverted;
    }

    public void updateNeeded()
    {
        this.requiresUpdate = true;
    }
}
