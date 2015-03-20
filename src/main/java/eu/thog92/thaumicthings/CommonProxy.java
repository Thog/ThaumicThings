package eu.thog92.thaumicthings;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import eu.thog92.thaumicthings.addons.betterstorage.AddonBetterStorage;
import eu.thog92.thaumicthings.blocks.BlockExtraLifter;
import eu.thog92.thaumicthings.entities.EntityBottleEthereal;
import eu.thog92.thaumicthings.items.ItemBottleEthereal;
import eu.thog92.thaumicthings.potions.PotionEthereal;
import eu.thog92.thaumicthings.tiles.TileEntityExtraLifter;
import eu.thog92.thaumicthings.utils.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.common.entities.ai.combat.AIAttackOnCollide;
import thaumcraft.common.entities.monster.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommonProxy
{

    public static Block extraLifter;
    public static Item bottleEthereal;
    public static Potion ethereal;
    private Configuration config;

    public void registerRenders()
    {

    }

    public void initContents()
    {
        ThaumicThings.log.trace("Init Mod Contents...");
        extraLifter = new BlockExtraLifter();
        bottleEthereal = new ItemBottleEthereal().setUnlocalizedName("bottleEthereal");
        GameRegistry.registerItem(bottleEthereal, bottleEthereal.getUnlocalizedName());


        //POTIONS
        int potionOffset = Potion.potionTypes.length;

        int potionCount = 1;
        if (potionOffset < (128 - potionCount))
        {
            ThaumicThings.log.trace("Extending Potion.potionTypes array to " + (potionOffset + potionCount));
            ;
            // Extend potions array
            Potion[] potionTypes = new Potion[potionOffset + potionCount];
            System.arraycopy(Potion.potionTypes, 0, potionTypes, 0, potionOffset);
            ReflectionHelper.setPrivateFinalWithValue(Potion.class, null, potionTypes, new String[]{"potionTypes", "field_76425_a", "a"});
        }

        ethereal = new PotionEthereal(getNextPotionID(potionOffset++));

        MinecraftForge.EVENT_BUS.register(this);

        if (Loader.isModLoaded("betterstorage"))
            AddonBetterStorage.initRenders();

    }

    public void sparkle(float x, float y, float z, float size, int color, double motionX, double motionY, double motionZ)
    {

    }

    public void initTiles()
    {
        GameRegistry.registerTileEntity(TileEntityExtraLifter.class, "TileEntityExtraLifter");
    }

    public void initEntities()
    {
        ThaumicThings.log.trace("Init Entities...");
        EntityRegistry.registerModEntity(EntityBottleEthereal.class, "BottleEthereal", 0, ThaumicThings.instance, 64, 20, true);
    }

    public void onBottleBreak(Item item, World world, double x, double y, double z)
    {

    }


    public void loadConfiguration(File file)
    {
        ThaumicThings.log.trace("Loading Configuration...");
        config = new Configuration(file);
        config.addCustomCategoryComment("", "");
    }

    @SubscribeEvent
    public void onDead(LivingDeathEvent event)
    {
        if (!event.entityLiving.worldObj.isRemote)
        {

            if (event.entityLiving instanceof ITaintedMob)
            {
                // Ethereal
                if (event.entityLiving.isPotionActive(ethereal))
                {
                    this.spawnOriginalLivingEntity(event.entityLiving);
                } else if (event.entityLiving.isPotionActive(PotionFluxTaint.instance))
                {
                    this.spawnTaintLivingEntity(event.entityLiving);
                }

            }
            // Tain Villager on death
            else if ((event.entityLiving instanceof EntityVillager && event.source.getEntity() != null && (event.source.getEntity() instanceof EntityTaintVillager)) || (event.entityLiving instanceof EntityZombie && ((EntityZombie) event.entityLiving).isVillager()))
            {
                EntityTaintVillager tainVillager = new EntityTaintVillager(event.entityLiving.worldObj);
                if (tainVillager != null)
                {
                    tainVillager.setLocationAndAngles(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, event.entityLiving.rotationYaw, 0.0F);
                    event.entityLiving.worldObj.spawnEntityInWorld(tainVillager);
                    event.entityLiving.setDead();
                }
            }
        }

    }

    private void spawnTaintLivingEntity(EntityLivingBase entityLiving)
    {
        Entity toSpawn = null;

        //TODO: Investigate why that is not working
        if ((entityLiving instanceof EntityZombie && ((EntityZombie) entityLiving).isVillager()))
        {
            toSpawn = new EntityTaintVillager(entityLiving.worldObj);
        }
        //More are coming...

        if (toSpawn != null)
        {
            toSpawn.setLocationAndAngles(entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.rotationYaw, 0.0F);
            entityLiving.worldObj.spawnEntityInWorld(toSpawn);
            entityLiving.setDead();
        }
    }

    private void spawnOriginalLivingEntity(EntityLivingBase entityLiving)
    {
        Entity toSpawn = null;
        if (entityLiving instanceof EntityTaintPig)
            toSpawn = new EntityPig(entityLiving.worldObj);
        else if (entityLiving instanceof EntityTaintCow)
            toSpawn = new EntityCow(entityLiving.worldObj);
        else if (entityLiving instanceof EntityTaintSheep)
            toSpawn = new EntitySheep(entityLiving.worldObj);
        else if (entityLiving instanceof EntityTaintChicken)
            toSpawn = new EntityChicken(entityLiving.worldObj);
        else if (entityLiving instanceof EntityTaintSpider)
            toSpawn = new EntitySpider(entityLiving.worldObj);
        else if (entityLiving instanceof EntityTaintCreeper)
            toSpawn = new EntityCreeper(entityLiving.worldObj);
        else if (entityLiving instanceof EntityTaintVillager)
            toSpawn = new EntityVillager(entityLiving.worldObj);
        else if (entityLiving instanceof EntityThaumicSlime)
        {
            EntitySlime slime = new EntitySlime(entityLiving.worldObj);

            // Restore slime size
            try
            {
                Method sizeMethod = cpw.mods.fml.relauncher.ReflectionHelper.findMethod(EntitySlime.class, slime, new String[]{"a", "func_70799_a", "setSlimeSize"}, int.class);
                sizeMethod.setAccessible(true);
                sizeMethod.invoke(slime, (int) (1.0F + Math.min(entityLiving.getMaxHealth() / 10.0F, 6.0F)));
            } catch (IllegalAccessException e)
            {

            } catch (InvocationTargetException e)
            {

            }

            toSpawn = slime;
        }


        if (toSpawn != null)
        {
            toSpawn.setLocationAndAngles(entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.rotationYaw, 0.0F);
            entityLiving.worldObj.spawnEntityInWorld(toSpawn);
            entityLiving.setDead();
        }
    }

    @SubscribeEvent
    public void onSpawn(LivingSpawnEvent event)
    {
        this.onSpawn(event.entityLiving);
    }

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityLivingBase)
        {
            this.onSpawn((EntityLivingBase) event.entity);
        }
    }

    public void onSpawn(EntityLivingBase entityLiving)
    {
        // Attack Villager and Zombies
        if (entityLiving instanceof EntityTaintVillager)
        {
            EntityTaintVillager taintVillager = ((EntityTaintVillager) entityLiving);
            taintVillager.tasks.addTask(11, new AIAttackOnCollide(taintVillager, EntityVillager.class, 1.0D, false));
            taintVillager.tasks.addTask(12, new AIAttackOnCollide(taintVillager, EntityZombie.class, 1.0D, false));

            taintVillager.targetTasks.addTask(3, new EntityAINearestAttackableTarget(taintVillager, EntityVillager.class, 0, false));
            taintVillager.targetTasks.addTask(3, new EntityAINearestAttackableTarget(taintVillager, EntityZombie.class, 0, false));

        }
    }

    private int getNextPotionID(int start)
    {
        if (Potion.potionTypes != null && start > 0 && start < Potion.potionTypes.length && Potion.potionTypes[start] == null)
        {
            return start;
        } else
        {
            ++start;
            if (start < 128)
            {
                start = getNextPotionID(start);
            } else
            {
                start = -1;
            }

            return start;
        }
    }
}
