package eu.thog92.thaumicthings.client.render;

import eu.thog92.thaumicthings.ClientProxy;
import eu.thog92.thaumicthings.blocks.BlockMagic;
import eu.thog92.thaumicthings.tiles.TileEntityExtraLifter;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BlockExtraLifterRenderer extends BlockRenderingHandler
{

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return ClientProxy.extraLifter;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockMagic block, int modelId, RenderBlocks renderer)
    {
        GL11.glPushMatrix();

        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);

        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata == 2 || metadata == 3)
        {
            renderer.uvRotateBottom = 1;
            renderer.uvRotateTop = 1;
        } else if (metadata == 4)
        {
            renderer.uvRotateBottom = 3;
            renderer.uvRotateTop = 3;
        }

        renderer.renderStandardBlock(block, x, y, z);

        //TODO: TileEntity
        int brightness = 180;
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TileEntityExtraLifter)
        {
            TileEntityExtraLifter lifterTile = (TileEntityExtraLifter) tile;
            if (lifterTile.isDisabled())
                brightness = 0;
        }

        // Liquids render
        tessellator.setBrightness(brightness);
        if (metadata == 0)
        {
            tessellator.setColorOpaque_I(40960);

            renderer.renderFaceYNeg(block, (double) x, (double) ((float) y + 0.01F), (double) z, block.getTexture(3));

            tessellator.setColorOpaque_I(14488063);
            if (block.shouldSideBeRendered(world, x + 1, y, z, 6))
            {
                renderer.renderFaceXPos(block, (double) ((float) x - 0.01F), (double) y, (double) z, block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x - 1, y, z, 6))
            {
                renderer.renderFaceXNeg(block, (double) ((float) x + 0.01F), (double) y, (double) z, block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x, y, z + 1, 6))
            {
                renderer.renderFaceZPos(block, (double) x, (double) y, (double) ((float) z - 0.01F), block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x, y, z - 1, 6))
            {
                renderer.renderFaceZNeg(block, (double) x, (double) y, (double) ((float) z + 0.01F), block.getTexture(3));
            }
        } else if (metadata == 2)
        {
            tessellator.setColorOpaque_I(40960);

            if (block.shouldSideBeRendered(world, x, y, z - 1, 6))
            {
                renderer.renderFaceZNeg(block, (double) x, (double) y, (double) ((float) z + 0.01F), block.getTexture(3));
            }

            tessellator.setColorOpaque_I(14488063);
            if (block.shouldSideBeRendered(world, x + 1, y, z, 6))
            {
                renderer.renderFaceXPos(block, (double) ((float) x - 0.01F), (double) y, (double) z, block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x - 1, y, z, 6))
            {
                renderer.renderFaceXNeg(block, (double) ((float) x + 0.01F), (double) y, (double) z, block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x, y + 1, z, 6))
            {
                renderer.renderFaceYPos(block, (double) x, (double) ((float) y - 0.01F), (double) z, block.getTexture(3));
            }
            if (block.shouldSideBeRendered(world, x, y - 1, z, 6))
            {
                renderer.renderFaceYNeg(block, (double) x, (double) ((float) y + 0.01F), (double) z, block.getTexture(3));
            }

        } else if (metadata == 3)
        {
            tessellator.setColorOpaque_I(40960);

            if (block.shouldSideBeRendered(world, x, y, z + 1, 6))
            {
                renderer.renderFaceZPos(block, (double) x, (double) y, (double) ((float) z - 0.01F), block.getTexture(3));
            }


            tessellator.setColorOpaque_I(14488063);
            if (block.shouldSideBeRendered(world, x + 1, y, z, 6))
            {
                renderer.renderFaceXPos(block, (double) ((float) x - 0.01F), (double) y, (double) z, block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x - 1, y, z, 6))
            {
                renderer.renderFaceXNeg(block, (double) ((float) x + 0.01F), (double) y, (double) z, block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x, y + 1, z, 6))
            {
                renderer.renderFaceYPos(block, (double) x, (double) ((float) y - 0.01F), (double) z, block.getTexture(3));
            }
            if (block.shouldSideBeRendered(world, x, y - 1, z, 6))
            {
                renderer.renderFaceYNeg(block, (double) x, (double) ((float) y + 0.01F), (double) z, block.getTexture(3));
            }
        } else if (metadata == 4)
        {
            tessellator.setColorOpaque_I(40960);

            if (block.shouldSideBeRendered(world, x - 1, y, z, 6))
            {
                renderer.renderFaceXNeg(block, (double) ((float) x + 0.01F), (double) y, (double) z, block.getTexture(3));
            }


            tessellator.setColorOpaque_I(14488063);

            if (block.shouldSideBeRendered(world, x, y, z - 1, 6))
            {
                renderer.renderFaceZPos(block, (double) x, (double) y, (double) ((float) z - 0.01F), block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x, y + 1, z, 6))
            {
                renderer.renderFaceYPos(block, (double) x, (double) ((float) y - 0.01F), (double) z, block.getTexture(3));
            }
            if (block.shouldSideBeRendered(world, x, y - 1, z, 6))
            {
                renderer.renderFaceYNeg(block, (double) x, (double) ((float) y + 0.01F), (double) z, block.getTexture(3));
            }
            if (block.shouldSideBeRendered(world, x, y, z - 1, 6))
            {
                renderer.renderFaceZNeg(block, (double) x, (double) y, (double) ((float) z + 0.01F), block.getTexture(3));
            }

        } else if (metadata == 5)
        {
            tessellator.setColorOpaque_I(40960);

            if (block.shouldSideBeRendered(world, x + 1, y, z, 6))
            {
                renderer.renderFaceXPos(block, (double) ((float) x - 0.01F), (double) y, (double) z, block.getTexture(3));
            }


            tessellator.setColorOpaque_I(14488063);

            if (block.shouldSideBeRendered(world, x, y, z - 1, 6))
            {
                renderer.renderFaceZPos(block, (double) x, (double) y, (double) ((float) z - 0.01F), block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x, y + 1, z, 6))
            {
                renderer.renderFaceYPos(block, (double) x, (double) ((float) y - 0.01F), (double) z, block.getTexture(3));
            }
            if (block.shouldSideBeRendered(world, x, y - 1, z, 6))
            {
                renderer.renderFaceYNeg(block, (double) x, (double) ((float) y + 0.01F), (double) z, block.getTexture(3));
            }
            if (block.shouldSideBeRendered(world, x, y, z - 1, 6))
            {
                renderer.renderFaceZNeg(block, (double) x, (double) y, (double) ((float) z + 0.01F), block.getTexture(3));
            }
        } else
        {
            tessellator.setColorOpaque_I(40960);

            if (block.shouldSideBeRendered(world, x, y + 1, z, 6))
            {
                renderer.renderFaceYPos(block, (double) x, (double) ((float) y - 0.01F), (double) z, block.getTexture(3));
            }

            tessellator.setColorOpaque_I(14488063);
            if (block.shouldSideBeRendered(world, x + 1, y, z, 6))
            {
                renderer.renderFaceXPos(block, (double) ((float) x - 0.01F), (double) y, (double) z, block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x - 1, y, z, 6))
            {
                renderer.renderFaceXNeg(block, (double) ((float) x + 0.01F), (double) y, (double) z, block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x, y, z + 1, 6))
            {
                renderer.renderFaceZPos(block, (double) x, (double) y, (double) ((float) z - 0.01F), block.getTexture(3));
            }

            if (block.shouldSideBeRendered(world, x, y, z - 1, 6))
            {
                renderer.renderFaceZNeg(block, (double) x, (double) y, (double) ((float) z + 0.01F), block.getTexture(3));
            }
        }

        // Clean and restore default blockbounds
        renderer.clearOverrideBlockTexture();
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        renderer.uvRotateBottom = 0;
        renderer.uvRotateTop = 0;

        GL11.glPopMatrix();
        return true;
    }

    @Override
    public void renderInventoryBlock(BlockMagic block, int metadata, int modelId, RenderBlocks renderer)
    {
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderer.setRenderBoundsFromBlock(block);
        this.drawQuadsWithBlock(renderer, block, 1);
        Color color = new Color(40960); // Default Thaumcraft Color
        GL11.glColor3f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F);
        block.setBlockBounds(0.01F, 0.9F, 0.01F, 0.99F, 0.99F, 0.99F);
        renderer.setRenderBoundsFromBlock(block);
        drawAllCube(renderer, block, block.getTexture(3));
        color = new Color(14488063); // Default Thaumcraft Color
        GL11.glColor3f((float) color.getRed() / 255.0F, (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F);
        block.setBlockBounds(0.01F, 0.1F, 0.01F, 0.99F, 0.9F, 0.99F);
        renderer.setRenderBoundsFromBlock(block);
        drawAllCube(renderer, block, block.getTexture(3));

        // Restore Color
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }
}
