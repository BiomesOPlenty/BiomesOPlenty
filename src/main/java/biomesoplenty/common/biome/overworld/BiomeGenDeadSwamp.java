package biomesoplenty.common.biome.overworld;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.client.fog.IBiomeFog;
import biomesoplenty.common.biome.BOPOverworldBiome;
import biomesoplenty.common.configuration.BOPConfigurationMisc;
import biomesoplenty.common.world.features.WorldGenBOPDoubleFlora;
import biomesoplenty.common.world.features.WorldGenBOPTallGrass;
import biomesoplenty.common.world.features.trees.WorldGenDeadTree;
import biomesoplenty.common.configuration.BOPConfigurationTerrainGen;
// BOPConfigurationTerrainGen.heightrootmod - BOPConfigurationTerrainGen.heightvarmod

public class BiomeGenDeadSwamp extends BOPOverworldBiome implements IBiomeFog
{
    //private static final Height biomeHeight = new Height(0.0F, 0.1F);
  	private static final Height biomeHeight = new Height(0.0F*BOPConfigurationTerrainGen.heightrootmod, 0.1F*BOPConfigurationTerrainGen.heightvarmod);
  
	public BiomeGenDeadSwamp(int id)
	{
		super(id);
		
        //TODO: setHeight()
        this.setHeight(biomeHeight);
        //TODO: setColor()
        this.setColor(9154376);
        this.setTemperatureRainfall(0.8F, 0.9F);

		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		
		this.waterColorMultiplier = 10661201;

		this.theBiomeDecorator.treesPerChunk = 1;
		this.theBiomeDecorator.grassPerChunk = 5;
		this.theBiomeDecorator.flowersPerChunk = -999;
		this.theBiomeDecorator.reedsPerChunk = -999;
		this.theBiomeDecorator.sandPerChunk = -999;
		this.theBiomeDecorator.sandPerChunk2 = -999;

        this.theBiomeDecorator.bopFeatures.mudPerChunk = 3;
        this.theBiomeDecorator.bopFeatures.riverCanePerChunk = 2;
        this.theBiomeDecorator.bopFeatures.waterReedsPerChunk = 4;
        this.theBiomeDecorator.bopFeatures.koruPerChunk = 1;
        this.theBiomeDecorator.bopFeatures.seaweedPerChunk = 5;

        this.theBiomeDecorator.bopFeatures.bopGrassPerChunk = 25;

        this.theBiomeDecorator.bopFeatures.weightedGrassGen.put(new WorldGenBOPTallGrass(Blocks.tallgrass, 1), 0.5D);
        this.theBiomeDecorator.bopFeatures.weightedGrassGen.put(new WorldGenBOPTallGrass(BOPCBlocks.foliage, 2), 1D);
        this.theBiomeDecorator.bopFeatures.weightedGrassGen.put(new WorldGenBOPTallGrass(BOPCBlocks.foliage, 10), 0.5D);
        this.theBiomeDecorator.bopFeatures.weightedGrassGen.put(new WorldGenBOPTallGrass(BOPCBlocks.foliage, 11), 0.5D);
        this.theBiomeDecorator.bopFeatures.weightedGrassGen.put(new WorldGenBOPDoubleFlora(2), 0.25D);
	}

    @Override
    //TODO:                     getRandomWorldGenForTrees()
    public WorldGenAbstractTree func_150567_a(Random random)
    {
        return new WorldGenDeadTree();
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
            
            //TODO:             getBlock()
            Block block = world.getBlock(x, y, z);

            if (block != null && block.isReplaceableOreGen(world, x, y, z, Blocks.stone))
            {
                //TODO: setBlock()
                world.setBlock(x, y, z, BOPCBlocks.gemOre, 10, 2);
            }
        }
    }

	@Override
    //TODO:     getBiomeGrassColor()
    public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_)
    {
		return 6713420;
	}

	@Override
    //TODO:     getBiomeFoliageColor()
    public int getBiomeFoliageColor(int x, int y, int z)
    {
		return 6713420;
	}
	
    @Override
    public int getSkyColorByTemp(float par1)
    {
        if (BOPConfigurationMisc.skyColors) return 6451816;
        else return super.getSkyColorByTemp(par1);
    }
	
    @Override
	public int getFogColour(int x, int y, int z)
	{
		return 9219993;
	}
	
    @Override
    public float getFogDensity(int x, int y, int z)
    {
        // TODO Auto-generated method stub
        return 0.6F;
    }
}
