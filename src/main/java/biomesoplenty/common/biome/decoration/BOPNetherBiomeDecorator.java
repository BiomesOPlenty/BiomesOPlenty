package biomesoplenty.common.biome.decoration;

import biomesoplenty.api.biome.BOPBiome;
import biomesoplenty.api.biome.BOPBiomeDecorator;
import biomesoplenty.common.world.generation.IBOPWorldGenerator;
import biomesoplenty.common.world.generation.WorldGenFieldAssociation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.*;

public class BOPNetherBiomeDecorator extends BOPBiomeDecorator<NetherBiomeFeatures> {
    public BOPNetherBiomeDecorator() {
        super(NetherBiomeFeatures.class);
    }

    @Override
    protected void genDecorations(BiomeGenBase biome) {
        BOPBiome bopBiome = (BOPBiome) biome;

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));

        int i;
        int x;
        int y;
        int z;
        int perChunk = this.treesPerChunk;

        if (this.randomGenerator.nextInt(10) == 0) {
            ++perChunk;
        }

        boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, TREE);

        for (i = 0; doGen && i < perChunk; ++i) {
            x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            y = this.nextInt(128);
            WorldGenAbstractTree worldgenabstracttree = biome.func_150567_a(this.randomGenerator);
            worldgenabstracttree.setScale(1.0D, 1.0D, 1.0D);

            if (worldgenabstracttree.generate(this.currentWorld, this.randomGenerator, x, y, z)) {
                worldgenabstracttree.func_150524_b(this.currentWorld, this.randomGenerator, x, y, z);
            }
        }

        for (String featureName : bopFeatures.getFeatureNames()) {
            if (featureName.equals("bopFlowersPerChunk")) {
                if (!TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, FLOWERS)) continue;
            } else if (featureName.equals("bopGrassPerChunk")) {
                if (!TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, GRASS)) continue;
            }

            WorldGenFieldAssociation.WorldFeature worldFeature = WorldGenFieldAssociation.getAssociatedFeature(featureName);

            if (worldFeature != null) {
                IBOPWorldGenerator worldGenerator = worldFeature.getBOPWorldGenerator();

                if (worldGenerator != null) {
                    worldGenerator.setupGeneration(currentWorld, randomGenerator, bopBiome, featureName, chunk_X, chunk_Z);
                }
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
    }
}
