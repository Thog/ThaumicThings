package eu.thog92.thaumicthings.addons.betterstorage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.lib.utils.InventoryUtils;

public class BlockReinforcedHungryChest extends BlockContainer
{
    private BlockContainer originalChest;

    protected BlockReinforcedHungryChest(BlockContainer chest)
    {
        super(chest.getMaterial());
        this.originalChest = chest;
        this.setHardness(this.originalChest.getHarvestLevel(0));
        this.setResistance(this.originalChest.getExplosionResistance(null) * 5);
        this.setStepSound(this.originalChest.stepSound);
        this.setBlockBounds((float) originalChest.getBlockBoundsMinX(), (float) originalChest.getBlockBoundsMinY(), (float) originalChest.getBlockBoundsMinZ(), (float) originalChest.getBlockBoundsMaxX(), (float) originalChest.getBlockBoundsMaxY(), (float) originalChest.getBlockBoundsMaxZ());
        this.setHarvestLevel(this.originalChest.getHarvestTool(0), this.originalChest.getHarvestLevel(0));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return originalChest.createNewTileEntity(world, metadata);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = this.originalChest.getIcon(0, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
        return originalChest.getRenderType();
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {

        originalChest.setBlockBoundsBasedOnState(world, x, y, z);

        //TODO: Find a better way to do that
        this.setBlockBounds((float) originalChest.getBlockBoundsMinX(), (float) originalChest.getBlockBoundsMinY(), (float) originalChest.getBlockBoundsMinZ(), (float) originalChest.getBlockBoundsMaxX(), (float) originalChest.getBlockBoundsMaxY(), (float) originalChest.getBlockBoundsMaxZ());
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity != null && !world.isRemote)
        {
            if (entity instanceof EntityItem && !entity.isDead)
            {
                ItemStack leftovers = InventoryUtils.placeItemStackIntoInventory(((EntityItem) entity).getEntityItem(), (IInventory) tileEntity, 1, true);
                if (leftovers == null || leftovers.stackSize != ((EntityItem) entity).getEntityItem().stackSize)
                {
                    world.playSoundAtEntity(entity, "random.eat", 0.25F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
                    world.addBlockEvent(x, y, z, tileEntity.getBlockType(), 2, 2);
                }

                if (leftovers != null)
                {
                    ((EntityItem) entity).setEntityItemStack(leftovers);
                } else
                {
                    entity.setDead();
                }

                tileEntity.markDirty();
            }
        }
    }
}
