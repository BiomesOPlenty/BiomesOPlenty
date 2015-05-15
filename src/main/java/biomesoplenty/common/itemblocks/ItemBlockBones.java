package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockBones extends ItemBlock
{
	private static final String[] types = new String[] {"bones_small", "bones_medium", "bones_large", "bones_small_side_1", "bones_small_side_2", "bones_medium_side_1", "bones_medium_side_2"};

	public ItemBlockBones(Block block)
	{
		super(block);
		
		this.setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta)
	{
		return meta & 15;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) 
	{
		int meta = itemstack.getItemDamage();
		
		if (meta < 0 || meta >= types.length) 
		{
			meta = 0;
		}

		return super.getUnlocalizedName() + "." + types[meta];
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitVecX, float hitVecY, float hitVecZ)
	{
		Block block = world.getBlock(x, y, z);

		if (block == Blocks.snow && (world.getBlockMetadata(x, y, z) & 7) < 1)
		{
			side = 1;
		}
		else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z))
		{
			if (side == 0)
			{
				--y;
			}

			if (side == 1)
			{
				++y;
			}

			if (side == 2)
			{
				--z;
			}

			if (side == 3)
			{
				++z;
			}

			if (side == 4)
			{
				--x;
			}

			if (side == 5)
			{
				++x;
			}
		}

		if (itemstack.stackSize == 0)
			return false;
		else if (!player.canPlayerEdit(x, y, z, side, itemstack))
			return false;
		else if (y == 255 && field_150939_a.getMaterial().isSolid())
			return false;
		else if (world.canPlaceEntityOnSide(field_150939_a, x, y, z, false, side, player, itemstack))
		{
			int itemMeta = this.getMetadata(itemstack.getItemDamage());

			if (side == 2 || side == 3)
			{
				if (itemstack.getItemDamage() == 0) {
					itemMeta = 3;
				} else if (itemstack.getItemDamage() == 1) {
					itemMeta = 5;
				}
			}

			if (side == 4 || side == 5)
			{
				if (itemstack.getItemDamage() == 0) {
					itemMeta = 4;
				} else if (itemstack.getItemDamage() == 1) {
					itemMeta = 6;
				}
			}

			int blockMeta = field_150939_a.onBlockPlaced(world, x, y, z, side, hitVecX, hitVecY, hitVecZ, itemMeta);

			if (placeBlockAt(itemstack, player, world, x, y, z, side, hitVecX, hitVecY, hitVecZ, blockMeta))
			{
				world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, field_150939_a.stepSound.func_150496_b(), (field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, field_150939_a.stepSound.getPitch() * 0.8F);
				--itemstack.stackSize;
			}

			return true;
		} else
			return false;
	}
}
