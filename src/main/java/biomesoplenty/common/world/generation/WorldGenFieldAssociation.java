package biomesoplenty.common.world.generation;

import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraft.world.gen.feature.WorldGenerator;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.world.features.WorldGenBOPBigMushroom;
import biomesoplenty.common.world.features.WorldGenBOPBlob;
import biomesoplenty.common.world.features.WorldGenBOPCoral;
import biomesoplenty.common.world.features.WorldGenBOPDoubleFlora;
import biomesoplenty.common.world.features.WorldGenBOPFlora;
import biomesoplenty.common.world.features.WorldGenBOPLily;
import biomesoplenty.common.world.features.WorldGenBOPUndergroundDecoration;
import biomesoplenty.common.world.features.WorldGenCobwebNest;
import biomesoplenty.common.world.features.WorldGenGrassSplatter;
import biomesoplenty.common.world.features.WorldGenKelp;
import biomesoplenty.common.world.features.WorldGenLavaSpout;
import biomesoplenty.common.world.features.WorldGenLog;
import biomesoplenty.common.world.features.WorldGenLongVine;
import biomesoplenty.common.world.features.WorldGenRiverCane;
import biomesoplenty.common.world.features.WorldGenSandstoneSpike;
import biomesoplenty.common.world.features.WorldGenSplatter;
import biomesoplenty.common.world.features.WorldGenSplotches;
import biomesoplenty.common.world.features.WorldGenWasteland;
import biomesoplenty.common.world.features.WorldGenWasteland2;
import biomesoplenty.common.world.features.WorldGenWasteland3;
import biomesoplenty.common.world.features.WorldGenWasteland4;
import biomesoplenty.common.world.features.WorldGenWaterReeds;
import biomesoplenty.common.world.features.WorldGenWaterside;
import biomesoplenty.common.world.features.WorldGenXericSplatter;
import biomesoplenty.common.world.features.managers.WorldGenBOPFlowerManager;
import biomesoplenty.common.world.features.managers.WorldGenBOPGrassManager;
import biomesoplenty.common.world.features.nether.WorldGenBoneSpine;
import biomesoplenty.common.world.features.nether.WorldGenGrave;
import biomesoplenty.common.world.features.nether.WorldGenLakesNether;
import biomesoplenty.common.world.features.nether.WorldGenWaspHive;
import biomesoplenty.common.world.forcedgenerators.LakesForcedGenerator;
import biomesoplenty.common.world.forcedgenerators.MelonForcedGenerator;
import biomesoplenty.common.world.forcedgenerators.SpringForcedGenerator;

public class WorldGenFieldAssociation
{
    public static HashMap<String, WorldFeature> featureMap = new HashMap<String, WorldFeature>();

    public static void init()
    {
        associateFeatures();
        associateFeaturesForced();
    }

    private static void associateFeatures()
    {
        associateFeature("generateQuicksand", new WorldGenSplotches(BOPCBlocks.mud, 1, 24, Blocks.grass, Blocks.dirt, Blocks.sand));
        associateFeature("generateCanyon", new WorldGenSplotches(BOPCBlocks.rocks, 0, 48, Blocks.stone));
        associateFeature("generateStoneInGrass", new WorldGenSplotches(Blocks.stone, 0, 32, Blocks.grass));
        associateFeature("generateStoneInGrass2", new WorldGenSplotches(Blocks.stone, 0, 48, Blocks.grass, Blocks.dirt));
        associateFeature("generateGrass", new WorldGenSplotches(Blocks.grass, 0, 48, BOPCBlocks.rocks));
        associateFeature("generateSand", new WorldGenSplotches(Blocks.sand, 0, 32, BOPCBlocks.rocks));
        associateFeature("generateQuagmire", new WorldGenSplotches(Blocks.grass, 0, 48, BOPCBlocks.mud));
        associateFeature("generateAsh", new WorldGenSplotches(BOPCBlocks.ash, 0, 32, BOPCBlocks.ashStone, Blocks.netherrack));
        associateFeature("generateMycelium", new WorldGenSplotches(Blocks.mycelium, 0, 32, Blocks.grass));
        associateFeature("generateSponge", new WorldGenSplotches(Blocks.sponge, 0, 24, Blocks.dirt, Blocks.sand, Blocks.gravel));
        
        associateFeature("mudPerChunk", new WorldGenWaterside(BOPCBlocks.mud, 7, Blocks.dirt, Blocks.grass));
        associateFeature("gravelPerChunk", new WorldGenWaterside(Blocks.gravel, 7, Blocks.dirt, Blocks.grass));
        associateFeature("riverCanePerChunk", new WorldGenRiverCane());
        associateFeature("shrubsPerChunk", new WorldGenBOPFlora(BOPCBlocks.foliage, 9));
        associateFeature("bushesPerChunk", new WorldGenBOPFlora(BOPCBlocks.foliage, 4));
        associateFeature("cloverPatchesPerChunk", new WorldGenBOPFlora(BOPCBlocks.foliage, 13, 128));
        associateFeature("leafPilesPerChunk", new WorldGenBOPFlora(BOPCBlocks.foliage, 14, 256));
        associateFeature("deadLeafPilesPerChunk", new WorldGenBOPFlora(BOPCBlocks.foliage, 15, 256));
        associateFeature("lavenderPerChunk", new WorldGenBOPFlora(BOPCBlocks.flowers2, 3));
        associateFeature("thornsPerChunk", new WorldGenBOPFlora(BOPCBlocks.plants, 5));
        associateFeature("stalagmitesPerChunk", new WorldGenBOPUndergroundDecoration(BOPCBlocks.stoneFormations, 0));
        associateFeature("stalactitesPerChunk", new WorldGenBOPUndergroundDecoration(BOPCBlocks.stoneFormations, 1));
        associateFeature("desertSproutsPerChunk", new WorldGenBOPFlora(BOPCBlocks.plants, 2));
        associateFeature("desertGrassPerChunk", new WorldGenBOPFlora(BOPCBlocks.plants, 3));
        associateFeature("bromeliadsPerChunk", new WorldGenBOPFlora(BOPCBlocks.flowers, 12));
        associateFeature("waterReedsPerChunk", new WorldGenWaterReeds());
        associateFeature("wildCarrotsPerChunk", new WorldGenBOPFlora(BOPCBlocks.plants, 11));
        associateFeature("poisonIvyPerChunk", new WorldGenBOPFlora(BOPCBlocks.foliage, 7));
        associateFeature("berryBushesPerChunk", new WorldGenBOPFlora(BOPCBlocks.foliage, 8));
        associateFeature("wildRicePerChunk", new WorldGenBOPFlora(BOPCBlocks.plants, 6));
        associateFeature("portobellosPerChunk", new WorldGenBOPFlora(BOPCBlocks.mushrooms, 1));
        associateFeature("koruPerChunk", new WorldGenBOPFlora(BOPCBlocks.foliage, 12));
        associateFeature("toadstoolsPerChunk", new WorldGenBOPFlora(BOPCBlocks.mushrooms, 0));
        associateFeature("blueMilksPerChunk", new WorldGenBOPFlora(BOPCBlocks.mushrooms, 2));
        associateFeature("cattailsPerChunk", new WorldGenBOPFlora(BOPCBlocks.plants, 7));
        associateFeature("highCattailsPerChunk", new WorldGenBOPDoubleFlora(BOPCBlocks.plants, BOPCBlocks.plants, 10, 9));
        associateFeature("flaxPerChunk", new WorldGenBOPDoubleFlora(BOPCBlocks.foliage, BOPCBlocks.foliage, 3, 6, 24));
        associateFeature("bopLilyPerChunk", new WorldGenBOPLily(BOPCBlocks.lilyBop, 0, 128));
        associateFeature("algaePerChunk", new WorldGenBOPFlora(BOPCBlocks.foliage, 0, 256));
        associateFeature("sproutsPerChunk", new WorldGenBOPFlora(BOPCBlocks.foliage, 5));
        associateFeature("tinyCactiPerChunk", new WorldGenBOPFlora(BOPCBlocks.plants, 12));
        associateFeature("oasesPerChunk", new WorldGenWaterside(Blocks.grass, 7, Blocks.sand, Blocks.hardened_clay));
        associateFeature("minersDelightPerChunk", new WorldGenBOPUndergroundDecoration(BOPCBlocks.flowers2, 6));
        associateFeature("rootsPerChunk", new WorldGenBOPFlora(BOPCBlocks.plants, 15));
        associateFeature("grassSplatterPerChunk", new WorldGenGrassSplatter());
        associateFeature("xericSplatterPerChunk", new WorldGenXericSplatter());
        associateFeature("rockpilesPerChunk", new WorldGenBOPBlob(Blocks.cobblestone, 0));
        associateFeature("wastelandRockPilesPerChunk", new WorldGenBOPBlob(BOPCBlocks.driedDirt, 0));
        associateFeature("logsPerChunk", new WorldGenLog());
        associateFeature("lavaSpoutsPerChunk", new WorldGenLavaSpout());
        associateFeature("cobwebsPerChunk", new WorldGenBOPFlora(Blocks.web, 0));
        associateFeature("cobwebNestsPerChunk", new WorldGenCobwebNest());
        associateFeature("wasteland1PerChunk", new WorldGenWasteland());
        associateFeature("wasteland2PerChunk", new WorldGenWasteland2());
        associateFeature("wasteland3PerChunk", new WorldGenWasteland3());
        associateFeature("wasteland4PerChunk", new WorldGenWasteland4());
        associateFeature("smolderingGrassPerChunk", new WorldGenSplatter(BOPCBlocks.bopGrass, 1, BOPCBlocks.ash, Blocks.netherrack));
        associateFeature("sandSplatterPerChunk", new WorldGenSplatter(Blocks.sand, Blocks.grass));
        associateFeature("gravelSplatterPerChunk", new WorldGenSplatter(Blocks.gravel, Blocks.grass));
        associateFeature("redSandSplatterPerChunk", new WorldGenSplatter(Blocks.sand, 1, BOPCBlocks.hardSand));
        associateFeature("dirtSplatterPerChunk", new WorldGenSplatter(Blocks.dirt, 1, Blocks.grass));
        associateFeature("sandstoneSpikesPerChunk", new WorldGenSandstoneSpike());
        associateFeature("glowshroomsPerChunk", new WorldGenBOPFlora(BOPCBlocks.mushrooms, 3));
        associateFeature("bopBigMushroomsPerChunk", new WorldGenBOPBigMushroom(Blocks.dirt, Blocks.grass, Blocks.mycelium, BOPCBlocks.overgrownNetherrack));
        
        //Ocean Features
        associateFeature("seaweedPerChunk", new WorldGenBOPCoral(BOPCBlocks.coral2, 8, 256));
        associateFeature("coralPerChunk", new WorldGenBOPCoral(BOPCBlocks.coral1, -1));
        associateFeature("kelpPerChunk", new WorldGenKelp(4, 8));
        associateFeature("kelpThickPerChunk", new WorldGenKelp(4, 8));
        associateFeature("shortKelpPerChunk", new WorldGenKelp(2, 4));
        
        //Nether Features
        associateFeature("waspHivesPerChunk", new WorldGenWaspHive());
        associateFeature("boneSpinesUpPerChunk", new WorldGenBoneSpine(false));
        associateFeature("boneSpinesDownPerChunk", new WorldGenBoneSpine(true));
        associateFeature("netherLavaLakesPerChunk", new WorldGenLakesNether());
        associateFeature("netherVinesPerChunk", new WorldGenLongVine(BOPCBlocks.ivy, 15, 45));
        associateFeature("netherrackSplatterPerChunk", new WorldGenSplatter(Blocks.netherrack, BOPCBlocks.overgrownNetherrack));
        associateFeature("gravesPerChunk", new WorldGenGrave());

        associateFeature("bopFlowersPerChunk", new WorldGenBOPFlowerManager());
        associateFeature("bopGrassPerChunk", new WorldGenBOPGrassManager());
    }
    
    private static void associateFeaturesForced()
    {
        associateFeatureForced("waterSpringsPerChunk", new WorldGenLiquids(Blocks.flowing_water), SpringForcedGenerator.class);
        associateFeatureForced("lavaSpringsPerChunk", new WorldGenLiquids(Blocks.flowing_lava), SpringForcedGenerator.class);
        //associateFeatureForced("bloodSpringsPerChunk", new WorldGenLiquids(BOPCBlocks.blood), SpringForcedGenerator.class);

        associateFeatureForced("waterLakesPerChunk", new WorldGenLakes(Blocks.water), LakesForcedGenerator.class);
        associateFeatureForced("lavaLakesPerChunk", new WorldGenLakes(Blocks.lava), LakesForcedGenerator.class);
        associateFeatureForced("poisonLakesPerChunk", new WorldGenLakes(BOPCBlocks.poison), LakesForcedGenerator.class);
        associateFeatureForced("bloodLakesPerChunk", new WorldGenLakes(BOPCBlocks.blood), LakesForcedGenerator.class);

        associateFeatureForced("generateMelons", new WorldGenMelon(), MelonForcedGenerator.class);
    }

    public static void associateFeature(String name, WorldFeature feature)
    {
        featureMap.put(name, feature);
    }

    public static void associateFeature(String name, WorldGenerator generator)
    {
        featureMap.put(name, new WorldFeature(generator));
    }

    public static void associateFeatureForced(String name, WorldGenerator generator, Class<? extends ForcedWorldFeatureBOP> forcedFeature)
    {
        associateFeature(name, new WorldFeature(generator, forcedFeature));
    }

    public static WorldFeature getAssociatedFeature(String name)
    {
        return featureMap.get(name);
    }

    public static class WorldFeature
    {
        private WorldGenerator worldGenerator;
        private Class<? extends ForcedWorldFeatureBOP> forcedFeature;

        protected WorldFeature(WorldGenerator worldGenerator, Class<? extends ForcedWorldFeatureBOP> forcedFeature)
        {
            this.worldGenerator = worldGenerator;
            this.forcedFeature = forcedFeature;
        }

        protected WorldFeature(WorldGenerator worldGenerator)
        {
            this(worldGenerator, null);
        }

        public IBOPWorldGenerator getBOPWorldGenerator()
        {
            if (this.worldGenerator instanceof IBOPWorldGenerator)
            {
                return (IBOPWorldGenerator)this.worldGenerator;
            }
            else if (forcedFeature != null)
            {
                try
                {
                   return (IBOPWorldGenerator)forcedFeature.getConstructor(WorldGenerator.class).newInstance(worldGenerator);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }
}