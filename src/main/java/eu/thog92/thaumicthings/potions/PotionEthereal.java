package eu.thog92.thaumicthings.potions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import eu.thog92.thaumicthings.ThaumicThings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.entities.ITaintedMob;

/**
 * Created by Thog92 on 15/03/2015.
 */
public class PotionEthereal extends Potion
{
    static final ResourceLocation potionMap = new ResourceLocation("thaumcraft", "textures/misc/potions.png");

    public PotionEthereal(int id)
    {
        super(id, false, 14742263);
        this.setIconIndex(3, 2);
        this.setPotionName("potion.ethereal");
        this.setEffectiveness(0.25D);
    }

    public boolean isBadEffect()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(potionMap);
        return super.getStatusIconIndex();
    }

    @Override
    public void performEffect(EntityLivingBase target, int par2)
    {
        if (target instanceof ITaintedMob)
        {
            target.attackEntityFrom(ThaumicThings.ETHEREAL, 1.0F);
        }
    }

    @Override
    public boolean isReady(int par1, int par2)
    {
        int k = 40 >> par2;
        return k > 0 ? par1 % k == 0 : true;
    }
}
