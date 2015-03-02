package eu.thog92.thaumicthings.tileentity;

import eu.thog92.thaumicthings.blocks.BlockExtraLifter;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.Thaumcraft;

import java.util.List;

public class TileEntityExtraLifter extends TileThaumcraft implements IWandable
{
    public int rangeAbove;
    public boolean requiresUpdate;
    public boolean disabled;
    public float modifier = 1.0F;
    public boolean reverted;
    public boolean isShiftKeyPressed;
    private int counter;

    public TileEntityExtraLifter()
    {
        this.counter = 0;
        this.rangeAbove = 0;
        this.requiresUpdate = true;
        this.disabled = false;
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
        this.isShiftKeyPressed = Thaumcraft.proxy.isShiftKeyDown();

        this.counter += 1;
        if ((this.requiresUpdate) || (this.counter % 100 == 0))
        {
            this.disabled = isDisabled();

            this.requiresUpdate = false;

            int max = 10;
            int count = 1;
            if (blockMetadata == 0)
            {
                while ((this.worldObj.getBlock(this.xCoord, this.yCoord + count,
                        this.zCoord) instanceof BlockExtraLifter))
                {
                    ++count;
                    max += 10;
                }

                this.rangeAbove = 0;
                while ((this.rangeAbove < max)
                        && (!(this.worldObj.getBlock(this.xCoord, this.yCoord - 1
                        - this.rangeAbove, this.zCoord).isOpaqueCube())))
                {
                    this.rangeAbove += 1;
                }
            } else if (blockMetadata == 2)
            {
                while ((this.worldObj.getBlock(this.xCoord, this.yCoord,
                        this.zCoord + count) instanceof BlockExtraLifter))
                {
                    ++count;
                    max += 10;
                }


                this.rangeAbove = 0;
                while ((this.rangeAbove < max)
                        && (!(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord - 1 - this.rangeAbove).isOpaqueCube())))
                {
                    this.rangeAbove += 1;
                }
            } else if (blockMetadata == 4)
            {
                while ((this.worldObj.getBlock(this.xCoord + count, this.yCoord,
                        this.zCoord) instanceof BlockExtraLifter))
                {
                    ++count;
                    max += 10;
                }


                this.rangeAbove = 0;
                while ((this.rangeAbove < max)
                        && (!(this.worldObj.getBlock(this.xCoord - 1 - this.rangeAbove, this.yCoord, this.zCoord).isOpaqueCube())))
                {
                    this.rangeAbove += 1;
                }
            } else if (blockMetadata == 5)
            {
                while ((this.worldObj.getBlock(this.xCoord - count, this.yCoord,
                        this.zCoord) instanceof BlockExtraLifter))
                {
                    ++count;
                    max += 10;
                }


                this.rangeAbove = 0;
                while ((this.rangeAbove < max)
                        && (!(this.worldObj.getBlock(this.xCoord + 1 + this.rangeAbove, this.yCoord, this.zCoord).isOpaqueCube())))
                {
                    this.rangeAbove += 1;
                }

            } else
            {
                while ((this.worldObj.getBlock(this.xCoord, this.yCoord - count,
                        this.zCoord) instanceof BlockExtraLifter))
                {
                    ++count;
                    max += 10;
                }

                this.rangeAbove = 0;
                while ((this.rangeAbove < max)
                        && (!(this.worldObj.getBlock(this.xCoord, this.yCoord + 1
                        + this.rangeAbove, this.zCoord).isOpaqueCube())))
                {
                    this.rangeAbove += 1;
                }
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

                    // Disable damages
                    e.fallDistance = 0.0F;
                }
        }

    }

    private void manageDefaultOrientation(Entity e)
    {
        if (e instanceof EntityPlayer && isShiftKeyPressed)
        {
            if (e.motionY < 0.0D)
                e.motionY *= 0.8999999761581421D;
        } else if (e.motionY < 0.3499999940395355D)
            e.motionY += 0.1000000014901161D;
    }

    private void manageDownOrientation(Entity e)
    {
        if (e instanceof EntityPlayer && isShiftKeyPressed)
        {
            e.motionY += 0.1000000014901161D;
        } else if (e.motionY < 0.0D)
            e.motionY *= 0.8999999761581421D;
    }

    private double manageOrientation(Entity e, float modifier, double motion)
    {
        e.motionY = -e.fallDistance;
        if (e instanceof EntityPlayer && isShiftKeyPressed)
        {
            motion += modifier * 0.1000000014901161D;
        } else if ((modifier < 0 && motion > modifier * 0.3499999940395355D) || (modifier > 0 && motion < modifier * 0.3499999940395355))
            motion -= modifier * 0.1000000014901161D;

        return motion;
    }

    public boolean isDisabled()
    {
        return false;
    }

    @Override
    public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count)
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
    public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count)
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
}
