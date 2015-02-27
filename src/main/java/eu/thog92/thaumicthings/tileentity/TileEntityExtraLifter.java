package eu.thog92.thaumicthings.tileentity;

import eu.thog92.thaumicthings.blocks.BlockExtraLifter;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.common.Thaumcraft;

import java.util.List;

/**
 * Created by thog on 2/27/15.
 */
public class TileEntityExtraLifter extends TileThaumcraft
{
    private int counter;
    public int rangeAbove;
    public boolean requiresUpdate;
    public boolean disabled;

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
        int maxX = 0, maxY = 0, maxZ = 0;

        this.counter += 1;
        if ((this.requiresUpdate) || (this.counter % 100 == 0))
        {
            this.disabled = isDisabled();

            this.requiresUpdate = false;

            int max = 10;
            int count = 1;
            if(blockMetadata == 0)
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
            }
            else
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
            //System.out.println("HERE");
            AxisAlignedBB aabb;

            if(blockMetadata == 0)
            {
                aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord - this.rangeAbove , this.zCoord, this.xCoord + 1, this.yCoord, this.zCoord + 1);
            }
            else if(blockMetadata == 2)
            {
                //System.out.println(zCoord);
                aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord - this.rangeAbove, this.xCoord + 1, this.yCoord + 1, this.zCoord);
            }
            else if (blockMetadata == 3)
            {
                aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + this.rangeAbove);
            }
            else
            {
                aabb = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord + 1,
                        this.zCoord, this.xCoord + 1, this.yCoord + 1
                                + this.rangeAbove, this.zCoord + 1);
            }

            List<Entity> targets = this.worldObj.getEntitiesWithinAABB(Entity.class, aabb);
            
            if (targets.size() > 0)
                System.out.println(targets);
                for (Entity e : targets)
                {
                    if(blockMetadata == 0)
                    {
                        this.manageDownOrientation(e);
                    }
                    else if(blockMetadata == 2)
                    {
                        this.manageNorthOrientation(e);
                    }
                    else if(blockMetadata == 3)
                    {
                        e.motionY = -e.fallDistance;
                        if (Thaumcraft.proxy.isShiftKeyDown())
                        {
                            e.motionZ -= 0.1000000014901161D;
                        } else if (e.motionZ < 0.3499999940395355D)
                            e.motionZ += 0.1000000014901161D;
                    }
                    else
                    {
                        this.manageDefaultOrientation(e);
                    }

                    // Disable damages
                    e.fallDistance = 0.0F;
                }
        }

    }

    public void manageDefaultOrientation(Entity e)
    {
        if (Thaumcraft.proxy.isShiftKeyDown())
        {
            if (e.motionY < 0.0D)
                e.motionY *= 0.8999999761581421D;
        } else if (e.motionY < 0.3499999940395355D)
            e.motionY += 0.1000000014901161D;
    }

    public void manageDownOrientation(Entity e)
    {
        if (Thaumcraft.proxy.isShiftKeyDown())
        {
            e.motionY += 0.1000000014901161D;
        } else if (e.motionY < 0.0D)
            e.motionY *= 0.8999999761581421D;
    }
    
    public void manageNorthOrientation(Entity e)
    {
        e.motionY = -e.fallDistance;
        if (Thaumcraft.proxy.isShiftKeyDown())
        {
            e.motionZ += 0.1000000014901161D;
        } else if (e.motionZ > -0.3499999940395355D)
            e.motionZ -= 0.1000000014901161D;
    }

    public boolean isDisabled()
    {
        return false;
    }
}
