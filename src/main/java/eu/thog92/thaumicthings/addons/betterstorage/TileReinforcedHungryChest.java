package eu.thog92.thaumicthings.addons.betterstorage;

import cpw.mods.fml.common.registry.GameRegistry;
import net.mcft.copy.betterstorage.tile.TileReinforcedChest;
import net.mcft.copy.betterstorage.tile.entity.TileEntityReinforcedChest;
import net.mcft.copy.betterstorage.utils.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.utils.InventoryUtils;

public class TileReinforcedHungryChest extends TileReinforcedChest
{
    public TileReinforcedHungryChest(Material material)
    {
        super(material);
        this.setCreativeTab(Thaumcraft.tabTC);
    }

    public TileReinforcedHungryChest()
    {
        this(Material.wood);
    }

    @Override
    public Class<? extends ItemBlock> getItemClass()
    {
        return ItemHungryChest.class;
    }

    public String getTileName()
    {
        return "reinforcedChest";
    }

    @Override
    protected void registerBlock()
    {
        Class<? extends Item> itemClass = getItemClass();

        if (itemClass != null)
        {
            GameRegistry.registerBlock(this, (Class<? extends ItemBlock>) itemClass, super.getTileName());
        } else
        {
            GameRegistry.registerBlock(this, null, super.getTileName());
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        TileEntityReinforcedChest tileEntity = WorldUtils.get(world, x, y, z, TileEntityReinforcedChest.class);

        if (tileEntity != null && !world.isRemote)
        {
            if (entity instanceof EntityItem && !entity.isDead)
            {
                ItemStack stack = InventoryUtils.placeItemStackIntoInventory(((EntityItem) entity).getEntityItem(), tileEntity, 1, true);
                if (stack == null || stack.stackSize != ((EntityItem) entity).getEntityItem().stackSize)
                {
                    world.playSoundAtEntity(entity, "random.eat", 0.25F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
                    tileEntity.lidAngle = 1.0F;
                }

                if (stack != null)
                {
                    ((EntityItem) entity).setEntityItemStack(stack);
                } else
                {
                    entity.setDead();
                }

                tileEntity.markDirty();
            }
        }
    }
}
