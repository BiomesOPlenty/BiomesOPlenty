package biomesoplenty.common.biome.overworld;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.Height;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.biome.BOPOverworldBiome;
import biomesoplenty.common.world.features.WorldGenBOPTallGrass;

public class BiomeGenSteppe extends BOPOverworldBiome
{
	private static final Height biomeHeight = new Height(0.6F, 1.3F);
	
	public BiomeGenSteppe(int biomeID) 
	{
		super(biomeID);

        this.setHeight(biomeHeight);
        this.setColor(13413215);
        this.setTemperatureRainfall(2.0F, 0.05F);
        
		this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 5, 2, 6));
        
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.deadBushPerChunk = 7;
		
        this.theBiomeDecorator.bopFeatures.tinyCactiPerChunk = 1;
        this.theBiomeDecorator.bopFeatures.bromeliadsPerChunk = 2;
        this.theBiomeDecorator.bopFeatures.sandSplatterPerChunk = 2;
        this.theBiomeDecorator.bopFeatures.gravelSplatterPerChunk = 6;
        this.theBiomeDecorator.bopFeatures.dirtSplatterPerChunk = 4;
        this.theBiomeDecorator.bopFeatures.generateQuicksand = true;
		
        this.theBiomeDecorator.bopFeatures.bopGrassPerChunk = 15;
        
        this.theBiomeDecorator.bopFeatures.weightedGrassGen.put(new WorldGenBOPTallGrass(BOPCBlocks.foliage, 1), 1D);
	}
	
	@Override
	public void decorate(World world, Random random, int chunkX, int chunkZ)
	{
		super.decorate(world, random, chunkX, chunkZ);
		int var5 = 12 + random.nextInt(6);

		for (int var6 = 0; var6 < var5; ++var6)
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(28) + 4;
			int z = chunkZ + random.nextInt(16);

			Block block = world.getBlock(x, y, z);

			if (block != null && block.isReplaceableOreGen(world, x, y, z, Blocks.stone))
			{
				world.setBlock(x, y, z, BOPCBlocks.gemOre, 2, 2);
			}
		}
	}
	
	@Override
    public int getBiomeGrassColor(int x, int y, int z)
    {
        double d0 = plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D);
        return d0 < -0.7D ? 12365199 : (d0 < -0.3 ? 12033916 : 11702123);
    }

	@Override
	public int getBiomeFoliageColor(int x, int y, int z)
	{
		double d0 = plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D);
		return d0 < -0.7D ? 12365199 : (d0 < -0.3 ? 12033916 : 11702123);
	}
}
