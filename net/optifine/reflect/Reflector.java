/*      */ package net.optifine.reflect;
/*      */ 
/*      */ import com.google.common.base.Optional;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Map;
/*      */ import javax.vecmath.Matrix4f;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.GuiEnchantment;
/*      */ import net.minecraft.client.gui.GuiHopper;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.inventory.GuiBeacon;
/*      */ import net.minecraft.client.gui.inventory.GuiBrewingStand;
/*      */ import net.minecraft.client.gui.inventory.GuiChest;
/*      */ import net.minecraft.client.gui.inventory.GuiFurnace;
/*      */ import net.minecraft.client.model.ModelBanner;
/*      */ import net.minecraft.client.model.ModelBase;
/*      */ import net.minecraft.client.model.ModelBat;
/*      */ import net.minecraft.client.model.ModelBlaze;
/*      */ import net.minecraft.client.model.ModelBook;
/*      */ import net.minecraft.client.model.ModelChest;
/*      */ import net.minecraft.client.model.ModelDragon;
/*      */ import net.minecraft.client.model.ModelEnderCrystal;
/*      */ import net.minecraft.client.model.ModelEnderMite;
/*      */ import net.minecraft.client.model.ModelGhast;
/*      */ import net.minecraft.client.model.ModelGuardian;
/*      */ import net.minecraft.client.model.ModelHorse;
/*      */ import net.minecraft.client.model.ModelHumanoidHead;
/*      */ import net.minecraft.client.model.ModelLeashKnot;
/*      */ import net.minecraft.client.model.ModelMagmaCube;
/*      */ import net.minecraft.client.model.ModelOcelot;
/*      */ import net.minecraft.client.model.ModelRabbit;
/*      */ import net.minecraft.client.model.ModelRenderer;
/*      */ import net.minecraft.client.model.ModelSign;
/*      */ import net.minecraft.client.model.ModelSilverfish;
/*      */ import net.minecraft.client.model.ModelSkeletonHead;
/*      */ import net.minecraft.client.model.ModelSlime;
/*      */ import net.minecraft.client.model.ModelSquid;
/*      */ import net.minecraft.client.model.ModelWitch;
/*      */ import net.minecraft.client.model.ModelWither;
/*      */ import net.minecraft.client.model.ModelWolf;
/*      */ import net.minecraft.client.multiplayer.ChunkProviderClient;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.block.model.ModelBlock;
/*      */ import net.minecraft.client.renderer.entity.RenderBoat;
/*      */ import net.minecraft.client.renderer.entity.RenderLeashKnot;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.entity.RenderMinecart;
/*      */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*      */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*      */ import net.minecraft.client.renderer.tileentity.RenderItemFrame;
/*      */ import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.resources.model.ModelRotation;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EnumCreatureType;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.passive.EntityVillager;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityBeacon;
/*      */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*      */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*      */ import net.minecraft.tileentity.TileEntityFurnace;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.LongHashMap;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.ChunkCache;
/*      */ import net.minecraft.world.ChunkCoordIntPair;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.IWorldNameable;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraftforge.common.property.IUnlistedProperty;
/*      */ 
/*      */ 
/*      */ public class Reflector
/*      */ {
/*  101 */   private static boolean logForge = logEntry("*** Reflector Forge ***");
/*  102 */   public static ReflectorClass BetterFoliageClient = new ReflectorClass("mods.betterfoliage.client.BetterFoliageClient");
/*  103 */   public static ReflectorClass BlamingTransformer = new ReflectorClass("net.minecraftforge.fml.common.asm.transformers.BlamingTransformer");
/*  104 */   public static ReflectorMethod BlamingTransformer_onCrash = new ReflectorMethod(BlamingTransformer, "onCrash");
/*  105 */   public static ReflectorClass ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
/*  106 */   public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(ChunkWatchEvent_UnWatch, new Class[] { ChunkCoordIntPair.class, EntityPlayerMP.class });
/*  107 */   public static ReflectorClass CoreModManager = new ReflectorClass("net.minecraftforge.fml.relauncher.CoreModManager");
/*  108 */   public static ReflectorMethod CoreModManager_onCrash = new ReflectorMethod(CoreModManager, "onCrash");
/*  109 */   public static ReflectorClass DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
/*  110 */   public static ReflectorMethod DimensionManager_createProviderFor = new ReflectorMethod(DimensionManager, "createProviderFor");
/*  111 */   public static ReflectorMethod DimensionManager_getStaticDimensionIDs = new ReflectorMethod(DimensionManager, "getStaticDimensionIDs");
/*  112 */   public static ReflectorClass DrawScreenEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
/*  113 */   public static ReflectorConstructor DrawScreenEvent_Pre_Constructor = new ReflectorConstructor(DrawScreenEvent_Pre, new Class[] { GuiScreen.class, int.class, int.class, float.class });
/*  114 */   public static ReflectorClass DrawScreenEvent_Post = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
/*  115 */   public static ReflectorConstructor DrawScreenEvent_Post_Constructor = new ReflectorConstructor(DrawScreenEvent_Post, new Class[] { GuiScreen.class, int.class, int.class, float.class });
/*  116 */   public static ReflectorClass EntityViewRenderEvent_CameraSetup = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup");
/*  117 */   public static ReflectorConstructor EntityViewRenderEvent_CameraSetup_Constructor = new ReflectorConstructor(EntityViewRenderEvent_CameraSetup, new Class[] { EntityRenderer.class, Entity.class, Block.class, double.class, float.class, float.class, float.class });
/*  118 */   public static ReflectorField EntityViewRenderEvent_CameraSetup_yaw = new ReflectorField(EntityViewRenderEvent_CameraSetup, "yaw");
/*  119 */   public static ReflectorField EntityViewRenderEvent_CameraSetup_pitch = new ReflectorField(EntityViewRenderEvent_CameraSetup, "pitch");
/*  120 */   public static ReflectorField EntityViewRenderEvent_CameraSetup_roll = new ReflectorField(EntityViewRenderEvent_CameraSetup, "roll");
/*  121 */   public static ReflectorClass EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
/*  122 */   public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(EntityViewRenderEvent_FogColors, new Class[] { EntityRenderer.class, Entity.class, Block.class, double.class, float.class, float.class, float.class });
/*  123 */   public static ReflectorField EntityViewRenderEvent_FogColors_red = new ReflectorField(EntityViewRenderEvent_FogColors, "red");
/*  124 */   public static ReflectorField EntityViewRenderEvent_FogColors_green = new ReflectorField(EntityViewRenderEvent_FogColors, "green");
/*  125 */   public static ReflectorField EntityViewRenderEvent_FogColors_blue = new ReflectorField(EntityViewRenderEvent_FogColors, "blue");
/*  126 */   public static ReflectorClass Event = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event");
/*  127 */   public static ReflectorMethod Event_isCanceled = new ReflectorMethod(Event, "isCanceled");
/*  128 */   public static ReflectorClass EventBus = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
/*  129 */   public static ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post");
/*  130 */   public static ReflectorClass Event_Result = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
/*  131 */   public static ReflectorField Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
/*  132 */   public static ReflectorField Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
/*  133 */   public static ReflectorField Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
/*  134 */   public static ReflectorClass ExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.ExtendedBlockState");
/*  135 */   public static ReflectorConstructor ExtendedBlockState_Constructor = new ReflectorConstructor(ExtendedBlockState, new Class[] { Block.class, IProperty[].class, IUnlistedProperty[].class });
/*  136 */   public static ReflectorClass FMLClientHandler = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
/*  137 */   public static ReflectorMethod FMLClientHandler_instance = new ReflectorMethod(FMLClientHandler, "instance");
/*  138 */   public static ReflectorMethod FMLClientHandler_handleLoadingScreen = new ReflectorMethod(FMLClientHandler, "handleLoadingScreen");
/*  139 */   public static ReflectorMethod FMLClientHandler_isLoading = new ReflectorMethod(FMLClientHandler, "isLoading");
/*  140 */   public static ReflectorMethod FMLClientHandler_trackBrokenTexture = new ReflectorMethod(FMLClientHandler, "trackBrokenTexture");
/*  141 */   public static ReflectorMethod FMLClientHandler_trackMissingTexture = new ReflectorMethod(FMLClientHandler, "trackMissingTexture");
/*  142 */   public static ReflectorClass FMLCommonHandler = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
/*  143 */   public static ReflectorMethod FMLCommonHandler_callFuture = new ReflectorMethod(FMLCommonHandler, "callFuture");
/*  144 */   public static ReflectorMethod FMLCommonHandler_enhanceCrashReport = new ReflectorMethod(FMLCommonHandler, "enhanceCrashReport");
/*  145 */   public static ReflectorMethod FMLCommonHandler_getBrandings = new ReflectorMethod(FMLCommonHandler, "getBrandings");
/*  146 */   public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(FMLCommonHandler, "handleServerAboutToStart");
/*  147 */   public static ReflectorMethod FMLCommonHandler_handleServerStarting = new ReflectorMethod(FMLCommonHandler, "handleServerStarting");
/*  148 */   public static ReflectorMethod FMLCommonHandler_instance = new ReflectorMethod(FMLCommonHandler, "instance");
/*  149 */   public static ReflectorClass ForgeBiome = new ReflectorClass(BiomeGenBase.class);
/*  150 */   public static ReflectorMethod ForgeBiome_getWaterColorMultiplier = new ReflectorMethod(ForgeBiome, "getWaterColorMultiplier");
/*  151 */   public static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
/*  152 */   public static ReflectorMethod ForgeBlock_addDestroyEffects = new ReflectorMethod(ForgeBlock, "addDestroyEffects");
/*  153 */   public static ReflectorMethod ForgeBlock_addHitEffects = new ReflectorMethod(ForgeBlock, "addHitEffects");
/*  154 */   public static ReflectorMethod ForgeBlock_canCreatureSpawn = new ReflectorMethod(ForgeBlock, "canCreatureSpawn");
/*  155 */   public static ReflectorMethod ForgeBlock_canRenderInLayer = new ReflectorMethod(ForgeBlock, "canRenderInLayer", new Class[] { EnumWorldBlockLayer.class });
/*  156 */   public static ReflectorMethod ForgeBlock_doesSideBlockRendering = new ReflectorMethod(ForgeBlock, "doesSideBlockRendering");
/*  157 */   public static ReflectorMethod ForgeBlock_getBedDirection = new ReflectorMethod(ForgeBlock, "getBedDirection");
/*  158 */   public static ReflectorMethod ForgeBlock_getExtendedState = new ReflectorMethod(ForgeBlock, "getExtendedState");
/*  159 */   public static ReflectorMethod ForgeBlock_getLightOpacity = new ReflectorMethod(ForgeBlock, "getLightOpacity", new Class[] { IBlockAccess.class, BlockPos.class });
/*  160 */   public static ReflectorMethod ForgeBlock_getLightValue = new ReflectorMethod(ForgeBlock, "getLightValue", new Class[] { IBlockAccess.class, BlockPos.class });
/*  161 */   public static ReflectorMethod ForgeBlock_hasTileEntity = new ReflectorMethod(ForgeBlock, "hasTileEntity", new Class[] { IBlockState.class });
/*  162 */   public static ReflectorMethod ForgeBlock_isAir = new ReflectorMethod(ForgeBlock, "isAir");
/*  163 */   public static ReflectorMethod ForgeBlock_isBed = new ReflectorMethod(ForgeBlock, "isBed");
/*  164 */   public static ReflectorMethod ForgeBlock_isBedFoot = new ReflectorMethod(ForgeBlock, "isBedFoot");
/*  165 */   public static ReflectorMethod ForgeBlock_isSideSolid = new ReflectorMethod(ForgeBlock, "isSideSolid");
/*  166 */   public static ReflectorClass ForgeChunkCache = new ReflectorClass(ChunkCache.class);
/*  167 */   public static ReflectorMethod ForgeChunkCache_isSideSolid = new ReflectorMethod(ForgeChunkCache, "isSideSolid");
/*  168 */   public static ReflectorClass ForgeEntity = new ReflectorClass(Entity.class);
/*  169 */   public static ReflectorMethod ForgeEntity_canRiderInteract = new ReflectorMethod(ForgeEntity, "canRiderInteract");
/*  170 */   public static ReflectorField ForgeEntity_captureDrops = new ReflectorField(ForgeEntity, "captureDrops");
/*  171 */   public static ReflectorField ForgeEntity_capturedDrops = new ReflectorField(ForgeEntity, "capturedDrops");
/*  172 */   public static ReflectorMethod ForgeEntity_shouldRenderInPass = new ReflectorMethod(ForgeEntity, "shouldRenderInPass");
/*  173 */   public static ReflectorMethod ForgeEntity_shouldRiderSit = new ReflectorMethod(ForgeEntity, "shouldRiderSit");
/*  174 */   public static ReflectorClass ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
/*  175 */   public static ReflectorMethod ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
/*  176 */   public static ReflectorMethod ForgeEventFactory_canEntitySpawn = new ReflectorMethod(ForgeEventFactory, "canEntitySpawn");
/*  177 */   public static ReflectorMethod ForgeEventFactory_doSpecialSpawn = new ReflectorMethod(ForgeEventFactory, "doSpecialSpawn", new Class[] { EntityLiving.class, World.class, float.class, float.class, float.class });
/*  178 */   public static ReflectorMethod ForgeEventFactory_getMaxSpawnPackSize = new ReflectorMethod(ForgeEventFactory, "getMaxSpawnPackSize");
/*  179 */   public static ReflectorMethod ForgeEventFactory_renderBlockOverlay = new ReflectorMethod(ForgeEventFactory, "renderBlockOverlay");
/*  180 */   public static ReflectorMethod ForgeEventFactory_renderFireOverlay = new ReflectorMethod(ForgeEventFactory, "renderFireOverlay");
/*  181 */   public static ReflectorMethod ForgeEventFactory_renderWaterOverlay = new ReflectorMethod(ForgeEventFactory, "renderWaterOverlay");
/*  182 */   public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
/*  183 */   public static ReflectorMethod ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
/*  184 */   public static ReflectorMethod ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
/*  185 */   public static ReflectorMethod ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
/*  186 */   public static ReflectorMethod ForgeHooks_onLivingFall = new ReflectorMethod(ForgeHooks, "onLivingFall");
/*  187 */   public static ReflectorMethod ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
/*  188 */   public static ReflectorMethod ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
/*  189 */   public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
/*  190 */   public static ReflectorMethod ForgeHooks_onLivingUpdate = new ReflectorMethod(ForgeHooks, "onLivingUpdate");
/*  191 */   public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
/*  192 */   public static ReflectorMethod ForgeHooksClient_applyTransform = new ReflectorMethod(ForgeHooksClient, "applyTransform", new Class[] { Matrix4f.class, Optional.class });
/*  193 */   public static ReflectorMethod ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast");
/*  194 */   public static ReflectorMethod ForgeHooksClient_drawScreen = new ReflectorMethod(ForgeHooksClient, "drawScreen");
/*  195 */   public static ReflectorMethod ForgeHooksClient_fillNormal = new ReflectorMethod(ForgeHooksClient, "fillNormal");
/*  196 */   public static ReflectorMethod ForgeHooksClient_handleCameraTransforms = new ReflectorMethod(ForgeHooksClient, "handleCameraTransforms");
/*  197 */   public static ReflectorMethod ForgeHooksClient_getArmorModel = new ReflectorMethod(ForgeHooksClient, "getArmorModel");
/*  198 */   public static ReflectorMethod ForgeHooksClient_getArmorTexture = new ReflectorMethod(ForgeHooksClient, "getArmorTexture");
/*  199 */   public static ReflectorMethod ForgeHooksClient_getFogDensity = new ReflectorMethod(ForgeHooksClient, "getFogDensity");
/*  200 */   public static ReflectorMethod ForgeHooksClient_getFOVModifier = new ReflectorMethod(ForgeHooksClient, "getFOVModifier");
/*  201 */   public static ReflectorMethod ForgeHooksClient_getMatrix = new ReflectorMethod(ForgeHooksClient, "getMatrix", new Class[] { ModelRotation.class });
/*  202 */   public static ReflectorMethod ForgeHooksClient_getOffsetFOV = new ReflectorMethod(ForgeHooksClient, "getOffsetFOV");
/*  203 */   public static ReflectorMethod ForgeHooksClient_loadEntityShader = new ReflectorMethod(ForgeHooksClient, "loadEntityShader");
/*  204 */   public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
/*  205 */   public static ReflectorMethod ForgeHooksClient_onFogRender = new ReflectorMethod(ForgeHooksClient, "onFogRender");
/*  206 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
/*  207 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
/*  208 */   public static ReflectorMethod ForgeHooksClient_orientBedCamera = new ReflectorMethod(ForgeHooksClient, "orientBedCamera");
/*  209 */   public static ReflectorMethod ForgeHooksClient_putQuadColor = new ReflectorMethod(ForgeHooksClient, "putQuadColor");
/*  210 */   public static ReflectorMethod ForgeHooksClient_renderFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderFirstPersonHand");
/*  211 */   public static ReflectorMethod ForgeHooksClient_renderMainMenu = new ReflectorMethod(ForgeHooksClient, "renderMainMenu");
/*  212 */   public static ReflectorMethod ForgeHooksClient_setRenderLayer = new ReflectorMethod(ForgeHooksClient, "setRenderLayer");
/*  213 */   public static ReflectorMethod ForgeHooksClient_setRenderPass = new ReflectorMethod(ForgeHooksClient, "setRenderPass");
/*  214 */   public static ReflectorMethod ForgeHooksClient_transform = new ReflectorMethod(ForgeHooksClient, "transform");
/*  215 */   public static ReflectorClass ForgeItem = new ReflectorClass(Item.class);
/*  216 */   public static ReflectorField ForgeItem_delegate = new ReflectorField(ForgeItem, "delegate");
/*  217 */   public static ReflectorMethod ForgeItem_getDurabilityForDisplay = new ReflectorMethod(ForgeItem, "getDurabilityForDisplay");
/*  218 */   public static ReflectorMethod ForgeItem_getModel = new ReflectorMethod(ForgeItem, "getModel");
/*  219 */   public static ReflectorMethod ForgeItem_onEntitySwing = new ReflectorMethod(ForgeItem, "onEntitySwing");
/*  220 */   public static ReflectorMethod ForgeItem_shouldCauseReequipAnimation = new ReflectorMethod(ForgeItem, "shouldCauseReequipAnimation");
/*  221 */   public static ReflectorMethod ForgeItem_showDurabilityBar = new ReflectorMethod(ForgeItem, "showDurabilityBar");
/*  222 */   public static ReflectorClass ForgeModContainer = new ReflectorClass("net.minecraftforge.common.ForgeModContainer");
/*  223 */   public static ReflectorField ForgeModContainer_forgeLightPipelineEnabled = new ReflectorField(ForgeModContainer, "forgeLightPipelineEnabled");
/*  224 */   public static ReflectorClass ForgePotionEffect = new ReflectorClass(PotionEffect.class);
/*  225 */   public static ReflectorMethod ForgePotionEffect_isCurativeItem = new ReflectorMethod(ForgePotionEffect, "isCurativeItem");
/*  226 */   public static ReflectorClass ForgeTileEntity = new ReflectorClass(TileEntity.class);
/*  227 */   public static ReflectorMethod ForgeTileEntity_canRenderBreaking = new ReflectorMethod(ForgeTileEntity, "canRenderBreaking");
/*  228 */   public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(ForgeTileEntity, "getRenderBoundingBox");
/*  229 */   public static ReflectorMethod ForgeTileEntity_hasFastRenderer = new ReflectorMethod(ForgeTileEntity, "hasFastRenderer");
/*  230 */   public static ReflectorMethod ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(ForgeTileEntity, "shouldRenderInPass");
/*  231 */   public static ReflectorClass ForgeVertexFormatElementEnumUseage = new ReflectorClass(VertexFormatElement.EnumUsage.class);
/*  232 */   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_preDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "preDraw");
/*  233 */   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_postDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "postDraw");
/*  234 */   public static ReflectorClass ForgeWorld = new ReflectorClass(World.class);
/*  235 */   public static ReflectorMethod ForgeWorld_countEntities = new ReflectorMethod(ForgeWorld, "countEntities", new Class[] { EnumCreatureType.class, boolean.class });
/*  236 */   public static ReflectorMethod ForgeWorld_getPerWorldStorage = new ReflectorMethod(ForgeWorld, "getPerWorldStorage");
/*  237 */   public static ReflectorClass ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
/*  238 */   public static ReflectorMethod ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(ForgeWorldProvider, "getCloudRenderer");
/*  239 */   public static ReflectorMethod ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(ForgeWorldProvider, "getSkyRenderer");
/*  240 */   public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(ForgeWorldProvider, "getWeatherRenderer");
/*  241 */   public static ReflectorMethod ForgeWorldProvider_getSaveFolder = new ReflectorMethod(ForgeWorldProvider, "getSaveFolder");
/*  242 */   public static ReflectorClass GuiModList = new ReflectorClass("net.minecraftforge.fml.client.GuiModList");
/*  243 */   public static ReflectorConstructor GuiModList_Constructor = new ReflectorConstructor(GuiModList, new Class[] { GuiScreen.class });
/*  244 */   public static ReflectorClass IColoredBakedQuad = new ReflectorClass("net.minecraftforge.client.model.IColoredBakedQuad");
/*  245 */   public static ReflectorClass IExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.IExtendedBlockState");
/*  246 */   public static ReflectorMethod IExtendedBlockState_getClean = new ReflectorMethod(IExtendedBlockState, "getClean");
/*  247 */   public static ReflectorClass IModel = new ReflectorClass("net.minecraftforge.client.model.IModel");
/*  248 */   public static ReflectorMethod IModel_getTextures = new ReflectorMethod(IModel, "getTextures");
/*  249 */   public static ReflectorClass IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
/*  250 */   public static ReflectorMethod IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
/*  251 */   public static ReflectorClass ItemModelMesherForge = new ReflectorClass("net.minecraftforge.client.ItemModelMesherForge");
/*  252 */   public static ReflectorConstructor ItemModelMesherForge_Constructor = new ReflectorConstructor(ItemModelMesherForge, new Class[] { ModelManager.class });
/*  253 */   public static ReflectorClass Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
/*  254 */   public static ReflectorField Launch_blackboard = new ReflectorField(Launch, "blackboard");
/*  255 */   public static ReflectorClass LightUtil = new ReflectorClass("net.minecraftforge.client.model.pipeline.LightUtil");
/*  256 */   public static ReflectorField LightUtil_itemConsumer = new ReflectorField(LightUtil, "itemConsumer");
/*  257 */   public static ReflectorMethod LightUtil_putBakedQuad = new ReflectorMethod(LightUtil, "putBakedQuad");
/*  258 */   public static ReflectorMethod LightUtil_renderQuadColor = new ReflectorMethod(LightUtil, "renderQuadColor");
/*  259 */   public static ReflectorField LightUtil_tessellator = new ReflectorField(LightUtil, "tessellator");
/*  260 */   public static ReflectorClass Loader = new ReflectorClass("net.minecraftforge.fml.common.Loader");
/*  261 */   public static ReflectorMethod Loader_getActiveModList = new ReflectorMethod(Loader, "getActiveModList");
/*  262 */   public static ReflectorMethod Loader_instance = new ReflectorMethod(Loader, "instance");
/*  263 */   public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
/*  264 */   public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
/*  265 */   public static ReflectorClass MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
/*  266 */   public static ReflectorMethod MinecraftForgeClient_getRenderPass = new ReflectorMethod(MinecraftForgeClient, "getRenderPass");
/*  267 */   public static ReflectorMethod MinecraftForgeClient_onRebuildChunk = new ReflectorMethod(MinecraftForgeClient, "onRebuildChunk");
/*  268 */   public static ReflectorClass ModContainer = new ReflectorClass("net.minecraftforge.fml.common.ModContainer");
/*  269 */   public static ReflectorMethod ModContainer_getModId = new ReflectorMethod(ModContainer, "getModId");
/*  270 */   public static ReflectorClass ModelLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader");
/*  271 */   public static ReflectorField ModelLoader_stateModels = new ReflectorField(ModelLoader, "stateModels");
/*  272 */   public static ReflectorMethod ModelLoader_onRegisterItems = new ReflectorMethod(ModelLoader, "onRegisterItems");
/*  273 */   public static ReflectorMethod ModelLoader_getInventoryVariant = new ReflectorMethod(ModelLoader, "getInventoryVariant");
/*  274 */   public static ReflectorField ModelLoader_textures = new ReflectorField(ModelLoader, "textures", true);
/*  275 */   public static ReflectorClass ModelLoader_VanillaLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader$VanillaLoader", true);
/*  276 */   public static ReflectorField ModelLoader_VanillaLoader_INSTANCE = new ReflectorField(ModelLoader_VanillaLoader, "instance", true);
/*  277 */   public static ReflectorMethod ModelLoader_VanillaLoader_loadModel = new ReflectorMethod(ModelLoader_VanillaLoader, "loadModel", null, true);
/*  278 */   public static ReflectorClass RenderBlockOverlayEvent_OverlayType = new ReflectorClass("net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType");
/*  279 */   public static ReflectorField RenderBlockOverlayEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockOverlayEvent_OverlayType, "BLOCK");
/*  280 */   public static ReflectorClass RenderingRegistry = new ReflectorClass("net.minecraftforge.fml.client.registry.RenderingRegistry");
/*  281 */   public static ReflectorMethod RenderingRegistry_loadEntityRenderers = new ReflectorMethod(RenderingRegistry, "loadEntityRenderers", new Class[] { RenderManager.class, Map.class });
/*  282 */   public static ReflectorClass RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
/*  283 */   public static ReflectorConstructor RenderItemInFrameEvent_Constructor = new ReflectorConstructor(RenderItemInFrameEvent, new Class[] { EntityItemFrame.class, RenderItemFrame.class });
/*  284 */   public static ReflectorClass RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
/*  285 */   public static ReflectorConstructor RenderLivingEvent_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Pre, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/*  286 */   public static ReflectorClass RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
/*  287 */   public static ReflectorConstructor RenderLivingEvent_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Post, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/*  288 */   public static ReflectorClass RenderLivingEvent_Specials_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre");
/*  289 */   public static ReflectorConstructor RenderLivingEvent_Specials_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Pre, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/*  290 */   public static ReflectorClass RenderLivingEvent_Specials_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Post");
/*  291 */   public static ReflectorConstructor RenderLivingEvent_Specials_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Post, new Class[] { EntityLivingBase.class, RendererLivingEntity.class, double.class, double.class, double.class });
/*  292 */   public static ReflectorClass SplashScreen = new ReflectorClass("net.minecraftforge.fml.client.SplashProgress");
/*  293 */   public static ReflectorClass WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
/*  294 */   public static ReflectorConstructor WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[] { World.class });
/*  295 */   private static boolean logVanilla = logEntry("*** Reflector Vanilla ***");
/*  296 */   public static ReflectorClass ChunkProviderClient = new ReflectorClass(ChunkProviderClient.class);
/*  297 */   public static ReflectorField ChunkProviderClient_chunkMapping = new ReflectorField(ChunkProviderClient, LongHashMap.class);
/*  298 */   public static ReflectorClass EntityVillager = new ReflectorClass(EntityVillager.class);
/*  299 */   public static ReflectorField EntityVillager_careerId = new ReflectorField(new FieldLocatorTypes(EntityVillager.class, new Class[0], int.class, new Class[] { int.class, boolean.class, boolean.class, InventoryBasic.class }, "EntityVillager.careerId"));
/*  300 */   public static ReflectorField EntityVillager_careerLevel = new ReflectorField(new FieldLocatorTypes(EntityVillager.class, new Class[] { int.class }, int.class, new Class[] { boolean.class, boolean.class, InventoryBasic.class }, "EntityVillager.careerLevel"));
/*  301 */   public static ReflectorClass GuiBeacon = new ReflectorClass(GuiBeacon.class);
/*  302 */   public static ReflectorField GuiBeacon_tileBeacon = new ReflectorField(GuiBeacon, IInventory.class);
/*  303 */   public static ReflectorClass GuiBrewingStand = new ReflectorClass(GuiBrewingStand.class);
/*  304 */   public static ReflectorField GuiBrewingStand_tileBrewingStand = new ReflectorField(GuiBrewingStand, IInventory.class);
/*  305 */   public static ReflectorClass GuiChest = new ReflectorClass(GuiChest.class);
/*  306 */   public static ReflectorField GuiChest_lowerChestInventory = new ReflectorField(GuiChest, IInventory.class, 1);
/*  307 */   public static ReflectorClass GuiEnchantment = new ReflectorClass(GuiEnchantment.class);
/*  308 */   public static ReflectorField GuiEnchantment_nameable = new ReflectorField(GuiEnchantment, IWorldNameable.class);
/*  309 */   public static ReflectorClass GuiFurnace = new ReflectorClass(GuiFurnace.class);
/*  310 */   public static ReflectorField GuiFurnace_tileFurnace = new ReflectorField(GuiFurnace, IInventory.class);
/*  311 */   public static ReflectorClass GuiHopper = new ReflectorClass(GuiHopper.class);
/*  312 */   public static ReflectorField GuiHopper_hopperInventory = new ReflectorField(GuiHopper, IInventory.class, 1);
/*  313 */   public static ReflectorClass GuiMainMenu = new ReflectorClass(GuiMainMenu.class);
/*  314 */   public static ReflectorField GuiMainMenu_splashText = new ReflectorField(GuiMainMenu, String.class);
/*  315 */   public static ReflectorClass Minecraft = new ReflectorClass(Minecraft.class);
/*  316 */   public static ReflectorField Minecraft_defaultResourcePack = new ReflectorField(Minecraft, DefaultResourcePack.class);
/*  317 */   public static ReflectorClass ModelHumanoidHead = new ReflectorClass(ModelHumanoidHead.class);
/*  318 */   public static ReflectorField ModelHumanoidHead_head = new ReflectorField(ModelHumanoidHead, ModelRenderer.class);
/*  319 */   public static ReflectorClass ModelBat = new ReflectorClass(ModelBat.class);
/*  320 */   public static ReflectorFields ModelBat_ModelRenderers = new ReflectorFields(ModelBat, ModelRenderer.class, 6);
/*  321 */   public static ReflectorClass ModelBlaze = new ReflectorClass(ModelBlaze.class);
/*  322 */   public static ReflectorField ModelBlaze_blazeHead = new ReflectorField(ModelBlaze, ModelRenderer.class);
/*  323 */   public static ReflectorField ModelBlaze_blazeSticks = new ReflectorField(ModelBlaze, ModelRenderer[].class);
/*  324 */   public static ReflectorClass ModelBlock = new ReflectorClass(ModelBlock.class);
/*  325 */   public static ReflectorField ModelBlock_parentLocation = new ReflectorField(ModelBlock, ResourceLocation.class);
/*  326 */   public static ReflectorField ModelBlock_textures = new ReflectorField(ModelBlock, Map.class);
/*  327 */   public static ReflectorClass ModelDragon = new ReflectorClass(ModelDragon.class);
/*  328 */   public static ReflectorFields ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, ModelRenderer.class, 12);
/*  329 */   public static ReflectorClass ModelEnderCrystal = new ReflectorClass(ModelEnderCrystal.class);
/*  330 */   public static ReflectorFields ModelEnderCrystal_ModelRenderers = new ReflectorFields(ModelEnderCrystal, ModelRenderer.class, 3);
/*  331 */   public static ReflectorClass RenderEnderCrystal = new ReflectorClass(RenderEnderCrystal.class);
/*  332 */   public static ReflectorField RenderEnderCrystal_modelEnderCrystal = new ReflectorField(RenderEnderCrystal, ModelBase.class, 0);
/*  333 */   public static ReflectorClass ModelEnderMite = new ReflectorClass(ModelEnderMite.class);
/*  334 */   public static ReflectorField ModelEnderMite_bodyParts = new ReflectorField(ModelEnderMite, ModelRenderer[].class);
/*  335 */   public static ReflectorClass ModelGhast = new ReflectorClass(ModelGhast.class);
/*  336 */   public static ReflectorField ModelGhast_body = new ReflectorField(ModelGhast, ModelRenderer.class);
/*  337 */   public static ReflectorField ModelGhast_tentacles = new ReflectorField(ModelGhast, ModelRenderer[].class);
/*  338 */   public static ReflectorClass ModelGuardian = new ReflectorClass(ModelGuardian.class);
/*  339 */   public static ReflectorField ModelGuardian_body = new ReflectorField(ModelGuardian, ModelRenderer.class, 0);
/*  340 */   public static ReflectorField ModelGuardian_eye = new ReflectorField(ModelGuardian, ModelRenderer.class, 1);
/*  341 */   public static ReflectorField ModelGuardian_spines = new ReflectorField(ModelGuardian, ModelRenderer[].class, 0);
/*  342 */   public static ReflectorField ModelGuardian_tail = new ReflectorField(ModelGuardian, ModelRenderer[].class, 1);
/*  343 */   public static ReflectorClass ModelHorse = new ReflectorClass(ModelHorse.class);
/*  344 */   public static ReflectorFields ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, ModelRenderer.class, 39);
/*  345 */   public static ReflectorClass RenderLeashKnot = new ReflectorClass(RenderLeashKnot.class);
/*  346 */   public static ReflectorField RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, ModelLeashKnot.class);
/*  347 */   public static ReflectorClass ModelMagmaCube = new ReflectorClass(ModelMagmaCube.class);
/*  348 */   public static ReflectorField ModelMagmaCube_core = new ReflectorField(ModelMagmaCube, ModelRenderer.class);
/*  349 */   public static ReflectorField ModelMagmaCube_segments = new ReflectorField(ModelMagmaCube, ModelRenderer[].class);
/*  350 */   public static ReflectorClass ModelOcelot = new ReflectorClass(ModelOcelot.class);
/*  351 */   public static ReflectorFields ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, ModelRenderer.class, 8);
/*  352 */   public static ReflectorClass ModelRabbit = new ReflectorClass(ModelRabbit.class);
/*  353 */   public static ReflectorFields ModelRabbit_renderers = new ReflectorFields(ModelRabbit, ModelRenderer.class, 12);
/*  354 */   public static ReflectorClass ModelSilverfish = new ReflectorClass(ModelSilverfish.class);
/*  355 */   public static ReflectorField ModelSilverfish_bodyParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 0);
/*  356 */   public static ReflectorField ModelSilverfish_wingParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 1);
/*  357 */   public static ReflectorClass ModelSlime = new ReflectorClass(ModelSlime.class);
/*  358 */   public static ReflectorFields ModelSlime_ModelRenderers = new ReflectorFields(ModelSlime, ModelRenderer.class, 4);
/*  359 */   public static ReflectorClass ModelSquid = new ReflectorClass(ModelSquid.class);
/*  360 */   public static ReflectorField ModelSquid_body = new ReflectorField(ModelSquid, ModelRenderer.class);
/*  361 */   public static ReflectorField ModelSquid_tentacles = new ReflectorField(ModelSquid, ModelRenderer[].class);
/*  362 */   public static ReflectorClass ModelWitch = new ReflectorClass(ModelWitch.class);
/*  363 */   public static ReflectorField ModelWitch_mole = new ReflectorField(ModelWitch, ModelRenderer.class, 0);
/*  364 */   public static ReflectorField ModelWitch_hat = new ReflectorField(ModelWitch, ModelRenderer.class, 1);
/*  365 */   public static ReflectorClass ModelWither = new ReflectorClass(ModelWither.class);
/*  366 */   public static ReflectorField ModelWither_bodyParts = new ReflectorField(ModelWither, ModelRenderer[].class, 0);
/*  367 */   public static ReflectorField ModelWither_heads = new ReflectorField(ModelWither, ModelRenderer[].class, 1);
/*  368 */   public static ReflectorClass ModelWolf = new ReflectorClass(ModelWolf.class);
/*  369 */   public static ReflectorField ModelWolf_tail = new ReflectorField(ModelWolf, ModelRenderer.class, 6);
/*  370 */   public static ReflectorField ModelWolf_mane = new ReflectorField(ModelWolf, ModelRenderer.class, 7);
/*  371 */   public static ReflectorClass OptiFineClassTransformer = new ReflectorClass("optifine.OptiFineClassTransformer");
/*  372 */   public static ReflectorField OptiFineClassTransformer_instance = new ReflectorField(OptiFineClassTransformer, "instance");
/*  373 */   public static ReflectorMethod OptiFineClassTransformer_getOptiFineResource = new ReflectorMethod(OptiFineClassTransformer, "getOptiFineResource");
/*  374 */   public static ReflectorClass RenderBoat = new ReflectorClass(RenderBoat.class);
/*  375 */   public static ReflectorField RenderBoat_modelBoat = new ReflectorField(RenderBoat, ModelBase.class);
/*  376 */   public static ReflectorClass RenderMinecart = new ReflectorClass(RenderMinecart.class);
/*  377 */   public static ReflectorField RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, ModelBase.class);
/*  378 */   public static ReflectorClass RenderWitherSkull = new ReflectorClass(RenderWitherSkull.class);
/*  379 */   public static ReflectorField RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, ModelSkeletonHead.class);
/*  380 */   public static ReflectorClass TileEntityBannerRenderer = new ReflectorClass(TileEntityBannerRenderer.class);
/*  381 */   public static ReflectorField TileEntityBannerRenderer_bannerModel = new ReflectorField(TileEntityBannerRenderer, ModelBanner.class);
/*  382 */   public static ReflectorClass TileEntityBeacon = new ReflectorClass(TileEntityBeacon.class);
/*  383 */   public static ReflectorField TileEntityBeacon_customName = new ReflectorField(TileEntityBeacon, String.class);
/*  384 */   public static ReflectorClass TileEntityBrewingStand = new ReflectorClass(TileEntityBrewingStand.class);
/*  385 */   public static ReflectorField TileEntityBrewingStand_customName = new ReflectorField(TileEntityBrewingStand, String.class);
/*  386 */   public static ReflectorClass TileEntityChestRenderer = new ReflectorClass(TileEntityChestRenderer.class);
/*  387 */   public static ReflectorField TileEntityChestRenderer_simpleChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 0);
/*  388 */   public static ReflectorField TileEntityChestRenderer_largeChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 1);
/*  389 */   public static ReflectorClass TileEntityEnchantmentTable = new ReflectorClass(TileEntityEnchantmentTable.class);
/*  390 */   public static ReflectorField TileEntityEnchantmentTable_customName = new ReflectorField(TileEntityEnchantmentTable, String.class);
/*  391 */   public static ReflectorClass TileEntityEnchantmentTableRenderer = new ReflectorClass(TileEntityEnchantmentTableRenderer.class);
/*  392 */   public static ReflectorField TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, ModelBook.class);
/*  393 */   public static ReflectorClass TileEntityEnderChestRenderer = new ReflectorClass(TileEntityEnderChestRenderer.class);
/*  394 */   public static ReflectorField TileEntityEnderChestRenderer_modelChest = new ReflectorField(TileEntityEnderChestRenderer, ModelChest.class);
/*  395 */   public static ReflectorClass TileEntityFurnace = new ReflectorClass(TileEntityFurnace.class);
/*  396 */   public static ReflectorField TileEntityFurnace_customName = new ReflectorField(TileEntityFurnace, String.class);
/*  397 */   public static ReflectorClass TileEntitySignRenderer = new ReflectorClass(TileEntitySignRenderer.class);
/*  398 */   public static ReflectorField TileEntitySignRenderer_model = new ReflectorField(TileEntitySignRenderer, ModelSign.class);
/*  399 */   public static ReflectorClass TileEntitySkullRenderer = new ReflectorClass(TileEntitySkullRenderer.class);
/*  400 */   public static ReflectorField TileEntitySkullRenderer_skeletonHead = new ReflectorField(TileEntitySkullRenderer, ModelSkeletonHead.class, 0);
/*  401 */   public static ReflectorField TileEntitySkullRenderer_humanoidHead = new ReflectorField(TileEntitySkullRenderer, ModelSkeletonHead.class, 1);
/*      */ 
/*      */ 
/*      */   
/*      */   public static void callVoid(ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  407 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  409 */       if (method == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  414 */       method.invoke(null, params);
/*      */     }
/*  416 */     catch (Throwable throwable) {
/*      */       
/*  418 */       handleException(throwable, null, refMethod, params);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean callBoolean(ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  426 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  428 */       if (method == null)
/*      */       {
/*  430 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  434 */       Boolean obool = (Boolean)method.invoke(null, params);
/*  435 */       return obool.booleanValue();
/*      */     
/*      */     }
/*  438 */     catch (Throwable throwable) {
/*      */       
/*  440 */       handleException(throwable, null, refMethod, params);
/*  441 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int callInt(ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  449 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  451 */       if (method == null)
/*      */       {
/*  453 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*  457 */       Integer integer = (Integer)method.invoke(null, params);
/*  458 */       return integer.intValue();
/*      */     
/*      */     }
/*  461 */     catch (Throwable throwable) {
/*      */       
/*  463 */       handleException(throwable, null, refMethod, params);
/*  464 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float callFloat(ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  472 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  474 */       if (method == null)
/*      */       {
/*  476 */         return 0.0F;
/*      */       }
/*      */ 
/*      */       
/*  480 */       Float f = (Float)method.invoke(null, params);
/*  481 */       return f.floatValue();
/*      */     
/*      */     }
/*  484 */     catch (Throwable throwable) {
/*      */       
/*  486 */       handleException(throwable, null, refMethod, params);
/*  487 */       return 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static double callDouble(ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  495 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  497 */       if (method == null)
/*      */       {
/*  499 */         return 0.0D;
/*      */       }
/*      */ 
/*      */       
/*  503 */       Double d0 = (Double)method.invoke(null, params);
/*  504 */       return d0.doubleValue();
/*      */     
/*      */     }
/*  507 */     catch (Throwable throwable) {
/*      */       
/*  509 */       handleException(throwable, null, refMethod, params);
/*  510 */       return 0.0D;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String callString(ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  518 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  520 */       if (method == null)
/*      */       {
/*  522 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  526 */       String s = (String)method.invoke(null, params);
/*  527 */       return s;
/*      */     
/*      */     }
/*  530 */     catch (Throwable throwable) {
/*      */       
/*  532 */       handleException(throwable, null, refMethod, params);
/*  533 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object call(ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  541 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  543 */       if (method == null)
/*      */       {
/*  545 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  549 */       Object object = method.invoke(null, params);
/*  550 */       return object;
/*      */     
/*      */     }
/*  553 */     catch (Throwable throwable) {
/*      */       
/*  555 */       handleException(throwable, null, refMethod, params);
/*  556 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void callVoid(Object obj, ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  564 */       if (obj == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  569 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  571 */       if (method == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  576 */       method.invoke(obj, params);
/*      */     }
/*  578 */     catch (Throwable throwable) {
/*      */       
/*  580 */       handleException(throwable, obj, refMethod, params);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean callBoolean(Object obj, ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  588 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  590 */       if (method == null)
/*      */       {
/*  592 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  596 */       Boolean obool = (Boolean)method.invoke(obj, params);
/*  597 */       return obool.booleanValue();
/*      */     
/*      */     }
/*  600 */     catch (Throwable throwable) {
/*      */       
/*  602 */       handleException(throwable, obj, refMethod, params);
/*  603 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int callInt(Object obj, ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  611 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  613 */       if (method == null)
/*      */       {
/*  615 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*  619 */       Integer integer = (Integer)method.invoke(obj, params);
/*  620 */       return integer.intValue();
/*      */     
/*      */     }
/*  623 */     catch (Throwable throwable) {
/*      */       
/*  625 */       handleException(throwable, obj, refMethod, params);
/*  626 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float callFloat(Object obj, ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  634 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  636 */       if (method == null)
/*      */       {
/*  638 */         return 0.0F;
/*      */       }
/*      */ 
/*      */       
/*  642 */       Float f = (Float)method.invoke(obj, params);
/*  643 */       return f.floatValue();
/*      */     
/*      */     }
/*  646 */     catch (Throwable throwable) {
/*      */       
/*  648 */       handleException(throwable, obj, refMethod, params);
/*  649 */       return 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static double callDouble(Object obj, ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  657 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  659 */       if (method == null)
/*      */       {
/*  661 */         return 0.0D;
/*      */       }
/*      */ 
/*      */       
/*  665 */       Double d0 = (Double)method.invoke(obj, params);
/*  666 */       return d0.doubleValue();
/*      */     
/*      */     }
/*  669 */     catch (Throwable throwable) {
/*      */       
/*  671 */       handleException(throwable, obj, refMethod, params);
/*  672 */       return 0.0D;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String callString(Object obj, ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  680 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  682 */       if (method == null)
/*      */       {
/*  684 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  688 */       String s = (String)method.invoke(obj, params);
/*  689 */       return s;
/*      */     
/*      */     }
/*  692 */     catch (Throwable throwable) {
/*      */       
/*  694 */       handleException(throwable, obj, refMethod, params);
/*  695 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object call(Object obj, ReflectorMethod refMethod, Object... params) {
/*      */     try {
/*  703 */       Method method = refMethod.getTargetMethod();
/*      */       
/*  705 */       if (method == null)
/*      */       {
/*  707 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  711 */       Object object = method.invoke(obj, params);
/*  712 */       return object;
/*      */     
/*      */     }
/*  715 */     catch (Throwable throwable) {
/*      */       
/*  717 */       handleException(throwable, obj, refMethod, params);
/*  718 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object getFieldValue(ReflectorField refField) {
/*  724 */     return getFieldValue((Object)null, refField);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object getFieldValue(Object obj, ReflectorField refField) {
/*      */     try {
/*  731 */       Field field = refField.getTargetField();
/*      */       
/*  733 */       if (field == null)
/*      */       {
/*  735 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  739 */       Object object = field.get(obj);
/*  740 */       return object;
/*      */     
/*      */     }
/*  743 */     catch (Throwable throwable) {
/*      */       
/*  745 */       throwable.printStackTrace();
/*  746 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getFieldValueBoolean(ReflectorField refField, boolean def) {
/*      */     try {
/*  754 */       Field field = refField.getTargetField();
/*      */       
/*  756 */       if (field == null)
/*      */       {
/*  758 */         return def;
/*      */       }
/*      */ 
/*      */       
/*  762 */       boolean flag = field.getBoolean(null);
/*  763 */       return flag;
/*      */     
/*      */     }
/*  766 */     catch (Throwable throwable) {
/*      */       
/*  768 */       throwable.printStackTrace();
/*  769 */       return def;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean getFieldValueBoolean(Object obj, ReflectorField refField, boolean def) {
/*      */     try {
/*  777 */       Field field = refField.getTargetField();
/*      */       
/*  779 */       if (field == null)
/*      */       {
/*  781 */         return def;
/*      */       }
/*      */ 
/*      */       
/*  785 */       boolean flag = field.getBoolean(obj);
/*  786 */       return flag;
/*      */     
/*      */     }
/*  789 */     catch (Throwable throwable) {
/*      */       
/*  791 */       throwable.printStackTrace();
/*  792 */       return def;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object getFieldValue(ReflectorFields refFields, int index) {
/*  798 */     ReflectorField reflectorfield = refFields.getReflectorField(index);
/*  799 */     return (reflectorfield == null) ? null : getFieldValue(reflectorfield);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object getFieldValue(Object obj, ReflectorFields refFields, int index) {
/*  804 */     ReflectorField reflectorfield = refFields.getReflectorField(index);
/*  805 */     return (reflectorfield == null) ? null : getFieldValue(obj, reflectorfield);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float getFieldValueFloat(Object obj, ReflectorField refField, float def) {
/*      */     try {
/*  812 */       Field field = refField.getTargetField();
/*      */       
/*  814 */       if (field == null)
/*      */       {
/*  816 */         return def;
/*      */       }
/*      */ 
/*      */       
/*  820 */       float f = field.getFloat(obj);
/*  821 */       return f;
/*      */     
/*      */     }
/*  824 */     catch (Throwable throwable) {
/*      */       
/*  826 */       throwable.printStackTrace();
/*  827 */       return def;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getFieldValueInt(Object obj, ReflectorField refField, int def) {
/*      */     try {
/*  835 */       Field field = refField.getTargetField();
/*      */       
/*  837 */       if (field == null)
/*      */       {
/*  839 */         return def;
/*      */       }
/*      */ 
/*      */       
/*  843 */       int i = field.getInt(obj);
/*  844 */       return i;
/*      */     
/*      */     }
/*  847 */     catch (Throwable throwable) {
/*      */       
/*  849 */       throwable.printStackTrace();
/*  850 */       return def;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFieldValueLong(Object obj, ReflectorField refField, long def) {
/*      */     try {
/*  858 */       Field field = refField.getTargetField();
/*      */       
/*  860 */       if (field == null)
/*      */       {
/*  862 */         return def;
/*      */       }
/*      */ 
/*      */       
/*  866 */       long i = field.getLong(obj);
/*  867 */       return i;
/*      */     
/*      */     }
/*  870 */     catch (Throwable throwable) {
/*      */       
/*  872 */       throwable.printStackTrace();
/*  873 */       return def;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean setFieldValue(ReflectorField refField, Object value) {
/*  879 */     return setFieldValue(null, refField, value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean setFieldValue(Object obj, ReflectorField refField, Object value) {
/*      */     try {
/*  886 */       Field field = refField.getTargetField();
/*      */       
/*  888 */       if (field == null)
/*      */       {
/*  890 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  894 */       field.set(obj, value);
/*  895 */       return true;
/*      */     
/*      */     }
/*  898 */     catch (Throwable throwable) {
/*      */       
/*  900 */       throwable.printStackTrace();
/*  901 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean setFieldValueInt(ReflectorField refField, int value) {
/*  907 */     return setFieldValueInt(null, refField, value);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean setFieldValueInt(Object obj, ReflectorField refField, int value) {
/*      */     try {
/*  914 */       Field field = refField.getTargetField();
/*      */       
/*  916 */       if (field == null)
/*      */       {
/*  918 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  922 */       field.setInt(obj, value);
/*  923 */       return true;
/*      */     
/*      */     }
/*  926 */     catch (Throwable throwable) {
/*      */       
/*  928 */       throwable.printStackTrace();
/*  929 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean postForgeBusEvent(ReflectorConstructor constr, Object... params) {
/*  935 */     Object object = newInstance(constr, params);
/*  936 */     return (object == null) ? false : postForgeBusEvent(object);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean postForgeBusEvent(Object event) {
/*  941 */     if (event == null)
/*      */     {
/*  943 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  947 */     Object object = getFieldValue(MinecraftForge_EVENT_BUS);
/*      */     
/*  949 */     if (object == null)
/*      */     {
/*  951 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  955 */     Object object1 = call(object, EventBus_post, new Object[] { event });
/*      */     
/*  957 */     if (!(object1 instanceof Boolean))
/*      */     {
/*  959 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  963 */     Boolean obool = (Boolean)object1;
/*  964 */     return obool.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object newInstance(ReflectorConstructor constr, Object... params) {
/*  972 */     Constructor constructor = constr.getTargetConstructor();
/*      */     
/*  974 */     if (constructor == null)
/*      */     {
/*  976 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  982 */       Object object = constructor.newInstance(params);
/*  983 */       return object;
/*      */     }
/*  985 */     catch (Throwable throwable) {
/*      */       
/*  987 */       handleException(throwable, constr, params);
/*  988 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean matchesTypes(Class[] pTypes, Class[] cTypes) {
/*  995 */     if (pTypes.length != cTypes.length)
/*      */     {
/*  997 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1001 */     for (int i = 0; i < cTypes.length; i++) {
/*      */       
/* 1003 */       Class oclass = pTypes[i];
/* 1004 */       Class oclass1 = cTypes[i];
/*      */       
/* 1006 */       if (oclass != oclass1)
/*      */       {
/* 1008 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1012 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void dbgCall(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params, Object retVal) {
/* 1018 */     String s = refMethod.getTargetMethod().getDeclaringClass().getName();
/* 1019 */     String s1 = refMethod.getTargetMethod().getName();
/* 1020 */     String s2 = "";
/*      */     
/* 1022 */     if (isStatic)
/*      */     {
/* 1024 */       s2 = " static";
/*      */     }
/*      */     
/* 1027 */     Config.dbg(callType + s2 + " " + s + "." + s1 + "(" + Config.arrayToString(params) + ") => " + retVal);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void dbgCallVoid(boolean isStatic, String callType, ReflectorMethod refMethod, Object[] params) {
/* 1032 */     String s = refMethod.getTargetMethod().getDeclaringClass().getName();
/* 1033 */     String s1 = refMethod.getTargetMethod().getName();
/* 1034 */     String s2 = "";
/*      */     
/* 1036 */     if (isStatic)
/*      */     {
/* 1038 */       s2 = " static";
/*      */     }
/*      */     
/* 1041 */     Config.dbg(callType + s2 + " " + s + "." + s1 + "(" + Config.arrayToString(params) + ")");
/*      */   }
/*      */ 
/*      */   
/*      */   private static void dbgFieldValue(boolean isStatic, String accessType, ReflectorField refField, Object val) {
/* 1046 */     String s = refField.getTargetField().getDeclaringClass().getName();
/* 1047 */     String s1 = refField.getTargetField().getName();
/* 1048 */     String s2 = "";
/*      */     
/* 1050 */     if (isStatic)
/*      */     {
/* 1052 */       s2 = " static";
/*      */     }
/*      */     
/* 1055 */     Config.dbg(accessType + s2 + " " + s + "." + s1 + " => " + val);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void handleException(Throwable e, Object obj, ReflectorMethod refMethod, Object[] params) {
/* 1060 */     if (e instanceof java.lang.reflect.InvocationTargetException) {
/*      */       
/* 1062 */       Throwable throwable = e.getCause();
/*      */       
/* 1064 */       if (throwable instanceof RuntimeException) {
/*      */         
/* 1066 */         RuntimeException runtimeexception = (RuntimeException)throwable;
/* 1067 */         throw runtimeexception;
/*      */       } 
/*      */ 
/*      */       
/* 1071 */       e.printStackTrace();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1076 */       if (e instanceof IllegalArgumentException) {
/*      */         
/* 1078 */         Config.warn("*** IllegalArgumentException ***");
/* 1079 */         Config.warn("Method: " + refMethod.getTargetMethod());
/* 1080 */         Config.warn("Object: " + obj);
/* 1081 */         Config.warn("Parameter classes: " + Config.arrayToString(getClasses(params)));
/* 1082 */         Config.warn("Parameters: " + Config.arrayToString(params));
/*      */       } 
/*      */       
/* 1085 */       Config.warn("*** Exception outside of method ***");
/* 1086 */       Config.warn("Method deactivated: " + refMethod.getTargetMethod());
/* 1087 */       refMethod.deactivate();
/* 1088 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void handleException(Throwable e, ReflectorConstructor refConstr, Object[] params) {
/* 1094 */     if (e instanceof java.lang.reflect.InvocationTargetException) {
/*      */       
/* 1096 */       e.printStackTrace();
/*      */     }
/*      */     else {
/*      */       
/* 1100 */       if (e instanceof IllegalArgumentException) {
/*      */         
/* 1102 */         Config.warn("*** IllegalArgumentException ***");
/* 1103 */         Config.warn("Constructor: " + refConstr.getTargetConstructor());
/* 1104 */         Config.warn("Parameter classes: " + Config.arrayToString(getClasses(params)));
/* 1105 */         Config.warn("Parameters: " + Config.arrayToString(params));
/*      */       } 
/*      */       
/* 1108 */       Config.warn("*** Exception outside of constructor ***");
/* 1109 */       Config.warn("Constructor deactivated: " + refConstr.getTargetConstructor());
/* 1110 */       refConstr.deactivate();
/* 1111 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Object[] getClasses(Object[] objs) {
/* 1117 */     if (objs == null)
/*      */     {
/* 1119 */       return (Object[])new Class[0];
/*      */     }
/*      */ 
/*      */     
/* 1123 */     Class[] aclass = new Class[objs.length];
/*      */     
/* 1125 */     for (int i = 0; i < aclass.length; i++) {
/*      */       
/* 1127 */       Object object = objs[i];
/*      */       
/* 1129 */       if (object != null)
/*      */       {
/* 1131 */         aclass[i] = object.getClass();
/*      */       }
/*      */     } 
/*      */     
/* 1135 */     return (Object[])aclass;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ReflectorField[] getReflectorFields(ReflectorClass parentClass, Class fieldType, int count) {
/* 1141 */     ReflectorField[] areflectorfield = new ReflectorField[count];
/*      */     
/* 1143 */     for (int i = 0; i < areflectorfield.length; i++)
/*      */     {
/* 1145 */       areflectorfield[i] = new ReflectorField(parentClass, fieldType, i);
/*      */     }
/*      */     
/* 1148 */     return areflectorfield;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean logEntry(String str) {
/* 1153 */     Config.dbg(str);
/* 1154 */     return true;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\reflect\Reflector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */