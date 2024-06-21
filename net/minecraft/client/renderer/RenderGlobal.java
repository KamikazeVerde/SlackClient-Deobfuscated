/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.gson.JsonSyntaxException;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Deque;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.ISound;
/*      */ import net.minecraft.client.audio.PositionedSoundRecord;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.EntityFX;
/*      */ import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
/*      */ import net.minecraft.client.renderer.chunk.CompiledChunk;
/*      */ import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.ListChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.chunk.VboChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.VisGraph;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelperImpl;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.shader.Framebuffer;
/*      */ import net.minecraft.client.shader.ShaderGroup;
/*      */ import net.minecraft.client.shader.ShaderLinkHelper;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemDye;
/*      */ import net.minecraft.item.ItemRecord;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.LongHashMap;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Matrix4f;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.util.Vector3d;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.IWorldAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.IChunkProvider;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.CustomSky;
/*      */ import net.optifine.DynamicLights;
/*      */ import net.optifine.Lagometer;
/*      */ import net.optifine.RandomEntities;
/*      */ import net.optifine.SmartAnimations;
/*      */ import net.optifine.model.BlockModelUtils;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.ChunkVisibility;
/*      */ import net.optifine.render.CloudRenderer;
/*      */ import net.optifine.render.RenderEnv;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.shaders.ShadersRender;
/*      */ import net.optifine.shaders.ShadowUtils;
/*      */ import net.optifine.util.ChunkUtils;
/*      */ import net.optifine.util.RenderChunkUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.util.vector.Matrix4f;
/*      */ import org.lwjgl.util.vector.Vector3f;
/*      */ import org.lwjgl.util.vector.Vector4f;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RenderGlobal
/*      */   implements IWorldAccess, IResourceManagerReloadListener
/*      */ {
/*  119 */   private static final Logger logger = LogManager.getLogger();
/*  120 */   private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
/*  121 */   private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
/*  122 */   private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
/*  123 */   private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
/*  124 */   private static final ResourceLocation locationForcefieldPng = new ResourceLocation("textures/misc/forcefield.png");
/*      */   
/*      */   public final Minecraft mc;
/*      */   
/*      */   private final TextureManager renderEngine;
/*      */   
/*      */   private final RenderManager renderManager;
/*      */   
/*      */   private WorldClient theWorld;
/*  133 */   private Set<RenderChunk> chunksToUpdate = Sets.newLinkedHashSet();
/*  134 */   private List<ContainerLocalRenderInformation> renderInfos = Lists.newArrayListWithCapacity(69696);
/*  135 */   private final Set<TileEntity> field_181024_n = Sets.newHashSet();
/*      */   
/*      */   private ViewFrustum viewFrustum;
/*      */   
/*  139 */   private int starGLCallList = -1;
/*      */ 
/*      */   
/*  142 */   private int glSkyList = -1;
/*      */ 
/*      */   
/*  145 */   private int glSkyList2 = -1;
/*      */   
/*      */   private VertexFormat vertexBufferFormat;
/*      */   
/*      */   private VertexBuffer starVBO;
/*      */   
/*      */   private VertexBuffer skyVBO;
/*      */   
/*      */   private VertexBuffer sky2VBO;
/*      */   private int cloudTickCounter;
/*  155 */   public final Map<Integer, DestroyBlockProgress> damagedBlocks = Maps.newHashMap();
/*  156 */   private final Map<BlockPos, ISound> mapSoundPositions = Maps.newHashMap();
/*  157 */   private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
/*      */   
/*      */   private Framebuffer entityOutlineFramebuffer;
/*      */   
/*      */   private ShaderGroup entityOutlineShader;
/*  162 */   private double frustumUpdatePosX = Double.MIN_VALUE;
/*  163 */   private double frustumUpdatePosY = Double.MIN_VALUE;
/*  164 */   private double frustumUpdatePosZ = Double.MIN_VALUE;
/*  165 */   private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
/*  166 */   private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
/*  167 */   private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
/*  168 */   private double lastViewEntityX = Double.MIN_VALUE;
/*  169 */   private double lastViewEntityY = Double.MIN_VALUE;
/*  170 */   private double lastViewEntityZ = Double.MIN_VALUE;
/*  171 */   private double lastViewEntityPitch = Double.MIN_VALUE;
/*  172 */   private double lastViewEntityYaw = Double.MIN_VALUE;
/*  173 */   private final ChunkRenderDispatcher renderDispatcher = new ChunkRenderDispatcher();
/*      */   private ChunkRenderContainer renderContainer;
/*  175 */   private int renderDistanceChunks = -1;
/*      */ 
/*      */   
/*  178 */   private int renderEntitiesStartupCounter = 2;
/*      */   
/*      */   private int countEntitiesTotal;
/*      */   
/*      */   private int countEntitiesRendered;
/*      */   
/*      */   private int countEntitiesHidden;
/*      */   
/*      */   private boolean debugFixTerrainFrustum = false;
/*      */   
/*      */   private ClippingHelper debugFixedClippingHelper;
/*      */   
/*  190 */   private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
/*  191 */   private final Vector3d debugTerrainFrustumPosition = new Vector3d();
/*      */   private boolean vboEnabled = false;
/*      */   IRenderChunkFactory renderChunkFactory;
/*      */   private double prevRenderSortX;
/*      */   private double prevRenderSortY;
/*      */   private double prevRenderSortZ;
/*      */   public boolean displayListEntitiesDirty = true;
/*      */   private CloudRenderer cloudRenderer;
/*      */   public Entity renderedEntity;
/*  200 */   public Set chunksToResortTransparency = new LinkedHashSet();
/*  201 */   public Set chunksToUpdateForced = new LinkedHashSet();
/*  202 */   private Deque visibilityDeque = new ArrayDeque();
/*  203 */   private List renderInfosEntities = new ArrayList(1024);
/*  204 */   private List renderInfosTileEntities = new ArrayList(1024);
/*  205 */   private List renderInfosNormal = new ArrayList(1024);
/*  206 */   private List renderInfosEntitiesNormal = new ArrayList(1024);
/*  207 */   private List renderInfosTileEntitiesNormal = new ArrayList(1024);
/*  208 */   private List renderInfosShadow = new ArrayList(1024);
/*  209 */   private List renderInfosEntitiesShadow = new ArrayList(1024);
/*  210 */   private List renderInfosTileEntitiesShadow = new ArrayList(1024);
/*  211 */   private int renderDistance = 0;
/*  212 */   private int renderDistanceSq = 0;
/*  213 */   private static final Set SET_ALL_FACINGS = Collections.unmodifiableSet(new HashSet(Arrays.asList((Object[])EnumFacing.VALUES)));
/*      */   private int countTileEntitiesRendered;
/*  215 */   private IChunkProvider worldChunkProvider = null;
/*  216 */   private LongHashMap worldChunkProviderMap = null;
/*  217 */   private int countLoadedChunksPrev = 0;
/*  218 */   private RenderEnv renderEnv = new RenderEnv(Blocks.air.getDefaultState(), new BlockPos(0, 0, 0));
/*      */   public boolean renderOverlayDamaged = false;
/*      */   public boolean renderOverlayEyes = false;
/*      */   private boolean firstWorldLoad = false;
/*  222 */   private static int renderEntitiesCounter = 0;
/*      */ 
/*      */   
/*      */   public RenderGlobal(Minecraft mcIn) {
/*  226 */     this.cloudRenderer = new CloudRenderer(mcIn);
/*  227 */     this.mc = mcIn;
/*  228 */     this.renderManager = mcIn.getRenderManager();
/*  229 */     this.renderEngine = mcIn.getTextureManager();
/*  230 */     this.renderEngine.bindTexture(locationForcefieldPng);
/*  231 */     GL11.glTexParameteri(3553, 10242, 10497);
/*  232 */     GL11.glTexParameteri(3553, 10243, 10497);
/*  233 */     GlStateManager.bindTexture(0);
/*  234 */     updateDestroyBlockIcons();
/*  235 */     this.vboEnabled = OpenGlHelper.useVbo();
/*      */     
/*  237 */     if (this.vboEnabled) {
/*      */       
/*  239 */       this.renderContainer = new VboRenderList();
/*  240 */       this.renderChunkFactory = (IRenderChunkFactory)new VboChunkFactory();
/*      */     }
/*      */     else {
/*      */       
/*  244 */       this.renderContainer = new RenderList();
/*  245 */       this.renderChunkFactory = (IRenderChunkFactory)new ListChunkFactory();
/*      */     } 
/*      */     
/*  248 */     this.vertexBufferFormat = new VertexFormat();
/*  249 */     this.vertexBufferFormat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
/*  250 */     generateStars();
/*  251 */     generateSky();
/*  252 */     generateSky2();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  257 */     updateDestroyBlockIcons();
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateDestroyBlockIcons() {
/*  262 */     TextureMap texturemap = this.mc.getTextureMapBlocks();
/*      */     
/*  264 */     for (int i = 0; i < this.destroyBlockIcons.length; i++)
/*      */     {
/*  266 */       this.destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void makeEntityOutlineShader() {
/*  275 */     if (OpenGlHelper.shadersSupported) {
/*      */       
/*  277 */       if (ShaderLinkHelper.getStaticShaderLinkHelper() == null)
/*      */       {
/*  279 */         ShaderLinkHelper.setNewStaticShaderLinkHelper();
/*      */       }
/*      */       
/*  282 */       ResourceLocation resourcelocation = new ResourceLocation("shaders/post/entity_outline.json");
/*      */ 
/*      */       
/*      */       try {
/*  286 */         this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocation);
/*  287 */         this.entityOutlineShader.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  288 */         this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
/*      */       }
/*  290 */       catch (IOException ioexception) {
/*      */         
/*  292 */         logger.warn("Failed to load shader: " + resourcelocation, ioexception);
/*  293 */         this.entityOutlineShader = null;
/*  294 */         this.entityOutlineFramebuffer = null;
/*      */       }
/*  296 */       catch (JsonSyntaxException jsonsyntaxexception) {
/*      */         
/*  298 */         logger.warn("Failed to load shader: " + resourcelocation, (Throwable)jsonsyntaxexception);
/*  299 */         this.entityOutlineShader = null;
/*  300 */         this.entityOutlineFramebuffer = null;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  305 */       this.entityOutlineShader = null;
/*  306 */       this.entityOutlineFramebuffer = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderEntityOutlineFramebuffer() {
/*  312 */     if (isRenderEntityOutlines()) {
/*      */       
/*  314 */       GlStateManager.enableBlend();
/*  315 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/*  316 */       this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.displayWidth, this.mc.displayHeight, false);
/*  317 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isRenderEntityOutlines() {
/*  323 */     return (!Config.isFastRender() && !Config.isShaders() && !Config.isAntialiasing()) ? ((this.entityOutlineFramebuffer != null && this.entityOutlineShader != null && this.mc.thePlayer != null && this.mc.thePlayer.isSpectator() && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown())) : false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateSky2() {
/*  328 */     Tessellator tessellator = Tessellator.getInstance();
/*  329 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/*  331 */     if (this.sky2VBO != null)
/*      */     {
/*  333 */       this.sky2VBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  336 */     if (this.glSkyList2 >= 0) {
/*      */       
/*  338 */       GLAllocation.deleteDisplayLists(this.glSkyList2);
/*  339 */       this.glSkyList2 = -1;
/*      */     } 
/*      */     
/*  342 */     if (this.vboEnabled) {
/*      */       
/*  344 */       this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
/*  345 */       renderSky(worldrenderer, -16.0F, true);
/*  346 */       worldrenderer.finishDrawing();
/*  347 */       worldrenderer.reset();
/*  348 */       this.sky2VBO.func_181722_a(worldrenderer.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  352 */       this.glSkyList2 = GLAllocation.generateDisplayLists(1);
/*  353 */       GL11.glNewList(this.glSkyList2, 4864);
/*  354 */       renderSky(worldrenderer, -16.0F, true);
/*  355 */       tessellator.draw();
/*  356 */       GL11.glEndList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateSky() {
/*  362 */     Tessellator tessellator = Tessellator.getInstance();
/*  363 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/*  365 */     if (this.skyVBO != null)
/*      */     {
/*  367 */       this.skyVBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  370 */     if (this.glSkyList >= 0) {
/*      */       
/*  372 */       GLAllocation.deleteDisplayLists(this.glSkyList);
/*  373 */       this.glSkyList = -1;
/*      */     } 
/*      */     
/*  376 */     if (this.vboEnabled) {
/*      */       
/*  378 */       this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
/*  379 */       renderSky(worldrenderer, 16.0F, false);
/*  380 */       worldrenderer.finishDrawing();
/*  381 */       worldrenderer.reset();
/*  382 */       this.skyVBO.func_181722_a(worldrenderer.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  386 */       this.glSkyList = GLAllocation.generateDisplayLists(1);
/*  387 */       GL11.glNewList(this.glSkyList, 4864);
/*  388 */       renderSky(worldrenderer, 16.0F, false);
/*  389 */       tessellator.draw();
/*  390 */       GL11.glEndList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderSky(WorldRenderer worldRendererIn, float p_174968_2_, boolean p_174968_3_) {
/*  396 */     int i = 64;
/*  397 */     int j = 6;
/*  398 */     worldRendererIn.begin(7, DefaultVertexFormats.field_181705_e);
/*  399 */     int k = (this.renderDistance / 64 + 1) * 64 + 64;
/*      */     
/*  401 */     for (int l = -k; l <= k; l += 64) {
/*      */       
/*  403 */       for (int i1 = -k; i1 <= k; i1 += 64) {
/*      */         
/*  405 */         float f = l;
/*  406 */         float f1 = (l + 64);
/*      */         
/*  408 */         if (p_174968_3_) {
/*      */           
/*  410 */           f1 = l;
/*  411 */           f = (l + 64);
/*      */         } 
/*      */         
/*  414 */         worldRendererIn.pos(f, p_174968_2_, i1).endVertex();
/*  415 */         worldRendererIn.pos(f1, p_174968_2_, i1).endVertex();
/*  416 */         worldRendererIn.pos(f1, p_174968_2_, (i1 + 64)).endVertex();
/*  417 */         worldRendererIn.pos(f, p_174968_2_, (i1 + 64)).endVertex();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateStars() {
/*  424 */     Tessellator tessellator = Tessellator.getInstance();
/*  425 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/*  427 */     if (this.starVBO != null)
/*      */     {
/*  429 */       this.starVBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  432 */     if (this.starGLCallList >= 0) {
/*      */       
/*  434 */       GLAllocation.deleteDisplayLists(this.starGLCallList);
/*  435 */       this.starGLCallList = -1;
/*      */     } 
/*      */     
/*  438 */     if (this.vboEnabled) {
/*      */       
/*  440 */       this.starVBO = new VertexBuffer(this.vertexBufferFormat);
/*  441 */       renderStars(worldrenderer);
/*  442 */       worldrenderer.finishDrawing();
/*  443 */       worldrenderer.reset();
/*  444 */       this.starVBO.func_181722_a(worldrenderer.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  448 */       this.starGLCallList = GLAllocation.generateDisplayLists(1);
/*  449 */       GlStateManager.pushMatrix();
/*  450 */       GL11.glNewList(this.starGLCallList, 4864);
/*  451 */       renderStars(worldrenderer);
/*  452 */       tessellator.draw();
/*  453 */       GL11.glEndList();
/*  454 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderStars(WorldRenderer worldRendererIn) {
/*  460 */     Random random = new Random(10842L);
/*  461 */     worldRendererIn.begin(7, DefaultVertexFormats.field_181705_e);
/*      */     
/*  463 */     for (int i = 0; i < 1500; i++) {
/*      */       
/*  465 */       double d0 = (random.nextFloat() * 2.0F - 1.0F);
/*  466 */       double d1 = (random.nextFloat() * 2.0F - 1.0F);
/*  467 */       double d2 = (random.nextFloat() * 2.0F - 1.0F);
/*  468 */       double d3 = (0.15F + random.nextFloat() * 0.1F);
/*  469 */       double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */       
/*  471 */       if (d4 < 1.0D && d4 > 0.01D) {
/*      */         
/*  473 */         d4 = 1.0D / Math.sqrt(d4);
/*  474 */         d0 *= d4;
/*  475 */         d1 *= d4;
/*  476 */         d2 *= d4;
/*  477 */         double d5 = d0 * 100.0D;
/*  478 */         double d6 = d1 * 100.0D;
/*  479 */         double d7 = d2 * 100.0D;
/*  480 */         double d8 = Math.atan2(d0, d2);
/*  481 */         double d9 = Math.sin(d8);
/*  482 */         double d10 = Math.cos(d8);
/*  483 */         double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
/*  484 */         double d12 = Math.sin(d11);
/*  485 */         double d13 = Math.cos(d11);
/*  486 */         double d14 = random.nextDouble() * Math.PI * 2.0D;
/*  487 */         double d15 = Math.sin(d14);
/*  488 */         double d16 = Math.cos(d14);
/*      */         
/*  490 */         for (int j = 0; j < 4; j++) {
/*      */           
/*  492 */           double d17 = 0.0D;
/*  493 */           double d18 = ((j & 0x2) - 1) * d3;
/*  494 */           double d19 = ((j + 1 & 0x2) - 1) * d3;
/*  495 */           double d20 = 0.0D;
/*  496 */           double d21 = d18 * d16 - d19 * d15;
/*  497 */           double d22 = d19 * d16 + d18 * d15;
/*  498 */           double d23 = d21 * d12 + 0.0D * d13;
/*  499 */           double d24 = 0.0D * d12 - d21 * d13;
/*  500 */           double d25 = d24 * d9 - d22 * d10;
/*  501 */           double d26 = d22 * d9 + d24 * d10;
/*  502 */           worldRendererIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldAndLoadRenderers(WorldClient worldClientIn) {
/*  513 */     if (this.theWorld != null)
/*      */     {
/*  515 */       this.theWorld.removeWorldAccess(this);
/*      */     }
/*      */     
/*  518 */     this.frustumUpdatePosX = Double.MIN_VALUE;
/*  519 */     this.frustumUpdatePosY = Double.MIN_VALUE;
/*  520 */     this.frustumUpdatePosZ = Double.MIN_VALUE;
/*  521 */     this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
/*  522 */     this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
/*  523 */     this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
/*  524 */     this.renderManager.set((World)worldClientIn);
/*  525 */     this.theWorld = worldClientIn;
/*      */     
/*  527 */     if (Config.isDynamicLights())
/*      */     {
/*  529 */       DynamicLights.clear();
/*      */     }
/*      */     
/*  532 */     ChunkVisibility.reset();
/*  533 */     this.worldChunkProvider = null;
/*  534 */     this.worldChunkProviderMap = null;
/*  535 */     this.renderEnv.reset(null, null);
/*  536 */     Shaders.checkWorldChanged((World)this.theWorld);
/*      */     
/*  538 */     if (worldClientIn != null) {
/*      */       
/*  540 */       worldClientIn.addWorldAccess(this);
/*  541 */       loadRenderers();
/*      */     }
/*      */     else {
/*      */       
/*  545 */       this.chunksToUpdate.clear();
/*  546 */       clearRenderInfos();
/*      */       
/*  548 */       if (this.viewFrustum != null)
/*      */       {
/*  550 */         this.viewFrustum.deleteGlResources();
/*      */       }
/*      */       
/*  553 */       this.viewFrustum = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadRenderers() {
/*  562 */     if (this.theWorld != null) {
/*      */       
/*  564 */       this.displayListEntitiesDirty = true;
/*  565 */       Blocks.leaves.setGraphicsLevel(Config.isTreesFancy());
/*  566 */       Blocks.leaves2.setGraphicsLevel(Config.isTreesFancy());
/*  567 */       BlockModelRenderer.updateAoLightValue();
/*      */       
/*  569 */       if (Config.isDynamicLights())
/*      */       {
/*  571 */         DynamicLights.clear();
/*      */       }
/*      */       
/*  574 */       SmartAnimations.update();
/*  575 */       this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
/*  576 */       this.renderDistance = this.renderDistanceChunks * 16;
/*  577 */       this.renderDistanceSq = this.renderDistance * this.renderDistance;
/*  578 */       boolean flag = this.vboEnabled;
/*  579 */       this.vboEnabled = OpenGlHelper.useVbo();
/*      */       
/*  581 */       if (flag && !this.vboEnabled) {
/*      */         
/*  583 */         this.renderContainer = new RenderList();
/*  584 */         this.renderChunkFactory = (IRenderChunkFactory)new ListChunkFactory();
/*      */       }
/*  586 */       else if (!flag && this.vboEnabled) {
/*      */         
/*  588 */         this.renderContainer = new VboRenderList();
/*  589 */         this.renderChunkFactory = (IRenderChunkFactory)new VboChunkFactory();
/*      */       } 
/*      */       
/*  592 */       generateStars();
/*  593 */       generateSky();
/*  594 */       generateSky2();
/*      */       
/*  596 */       if (this.viewFrustum != null)
/*      */       {
/*  598 */         this.viewFrustum.deleteGlResources();
/*      */       }
/*      */       
/*  601 */       stopChunkUpdates();
/*      */       
/*  603 */       synchronized (this.field_181024_n) {
/*      */         
/*  605 */         this.field_181024_n.clear();
/*      */       } 
/*      */       
/*  608 */       this.viewFrustum = new ViewFrustum((World)this.theWorld, this.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);
/*      */       
/*  610 */       if (this.theWorld != null) {
/*      */         
/*  612 */         Entity entity = this.mc.getRenderViewEntity();
/*      */         
/*  614 */         if (entity != null)
/*      */         {
/*  616 */           this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
/*      */         }
/*      */       } 
/*      */       
/*  620 */       this.renderEntitiesStartupCounter = 2;
/*      */     } 
/*      */     
/*  623 */     if (this.mc.thePlayer == null)
/*      */     {
/*  625 */       this.firstWorldLoad = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void stopChunkUpdates() {
/*  631 */     this.chunksToUpdate.clear();
/*  632 */     this.renderDispatcher.stopChunkUpdates();
/*      */   }
/*      */ 
/*      */   
/*      */   public void createBindEntityOutlineFbs(int p_72720_1_, int p_72720_2_) {
/*  637 */     if (OpenGlHelper.shadersSupported && this.entityOutlineShader != null)
/*      */     {
/*  639 */       this.entityOutlineShader.createBindFramebuffers(p_72720_1_, p_72720_2_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
/*      */     // Byte code:
/*      */     //   0: iconst_0
/*      */     //   1: istore #4
/*      */     //   3: getstatic net/optifine/reflect/Reflector.MinecraftForgeClient_getRenderPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   6: invokevirtual exists : ()Z
/*      */     //   9: ifeq -> 24
/*      */     //   12: getstatic net/optifine/reflect/Reflector.MinecraftForgeClient_getRenderPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   15: iconst_0
/*      */     //   16: anewarray java/lang/Object
/*      */     //   19: invokestatic callInt : (Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)I
/*      */     //   22: istore #4
/*      */     //   24: aload_0
/*      */     //   25: getfield renderEntitiesStartupCounter : I
/*      */     //   28: ifle -> 50
/*      */     //   31: iload #4
/*      */     //   33: ifle -> 37
/*      */     //   36: return
/*      */     //   37: aload_0
/*      */     //   38: dup
/*      */     //   39: getfield renderEntitiesStartupCounter : I
/*      */     //   42: iconst_1
/*      */     //   43: isub
/*      */     //   44: putfield renderEntitiesStartupCounter : I
/*      */     //   47: goto -> 2037
/*      */     //   50: aload_1
/*      */     //   51: getfield prevPosX : D
/*      */     //   54: aload_1
/*      */     //   55: getfield posX : D
/*      */     //   58: aload_1
/*      */     //   59: getfield prevPosX : D
/*      */     //   62: dsub
/*      */     //   63: fload_3
/*      */     //   64: f2d
/*      */     //   65: dmul
/*      */     //   66: dadd
/*      */     //   67: dstore #5
/*      */     //   69: aload_1
/*      */     //   70: getfield prevPosY : D
/*      */     //   73: aload_1
/*      */     //   74: getfield posY : D
/*      */     //   77: aload_1
/*      */     //   78: getfield prevPosY : D
/*      */     //   81: dsub
/*      */     //   82: fload_3
/*      */     //   83: f2d
/*      */     //   84: dmul
/*      */     //   85: dadd
/*      */     //   86: dstore #7
/*      */     //   88: aload_1
/*      */     //   89: getfield prevPosZ : D
/*      */     //   92: aload_1
/*      */     //   93: getfield posZ : D
/*      */     //   96: aload_1
/*      */     //   97: getfield prevPosZ : D
/*      */     //   100: dsub
/*      */     //   101: fload_3
/*      */     //   102: f2d
/*      */     //   103: dmul
/*      */     //   104: dadd
/*      */     //   105: dstore #9
/*      */     //   107: aload_0
/*      */     //   108: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   111: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   114: ldc 'prepare'
/*      */     //   116: invokevirtual startSection : (Ljava/lang/String;)V
/*      */     //   119: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   122: aload_0
/*      */     //   123: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   126: aload_0
/*      */     //   127: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   130: invokevirtual getTextureManager : ()Lnet/minecraft/client/renderer/texture/TextureManager;
/*      */     //   133: aload_0
/*      */     //   134: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   137: getfield MCfontRenderer : Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   140: aload_0
/*      */     //   141: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   144: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   147: fload_3
/*      */     //   148: invokevirtual cacheActiveRenderInfo : (Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;F)V
/*      */     //   151: aload_0
/*      */     //   152: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   155: aload_0
/*      */     //   156: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   159: aload_0
/*      */     //   160: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   163: getfield MCfontRenderer : Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   166: aload_0
/*      */     //   167: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   170: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   173: aload_0
/*      */     //   174: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   177: getfield pointedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   180: aload_0
/*      */     //   181: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   184: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   187: fload_3
/*      */     //   188: invokevirtual cacheActiveRenderInfo : (Lnet/minecraft/world/World;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/settings/GameSettings;F)V
/*      */     //   191: getstatic net/minecraft/client/renderer/RenderGlobal.renderEntitiesCounter : I
/*      */     //   194: iconst_1
/*      */     //   195: iadd
/*      */     //   196: putstatic net/minecraft/client/renderer/RenderGlobal.renderEntitiesCounter : I
/*      */     //   199: iload #4
/*      */     //   201: ifne -> 224
/*      */     //   204: aload_0
/*      */     //   205: iconst_0
/*      */     //   206: putfield countEntitiesTotal : I
/*      */     //   209: aload_0
/*      */     //   210: iconst_0
/*      */     //   211: putfield countEntitiesRendered : I
/*      */     //   214: aload_0
/*      */     //   215: iconst_0
/*      */     //   216: putfield countEntitiesHidden : I
/*      */     //   219: aload_0
/*      */     //   220: iconst_0
/*      */     //   221: putfield countTileEntitiesRendered : I
/*      */     //   224: aload_0
/*      */     //   225: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   228: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   231: astore #11
/*      */     //   233: aload #11
/*      */     //   235: getfield lastTickPosX : D
/*      */     //   238: aload #11
/*      */     //   240: getfield posX : D
/*      */     //   243: aload #11
/*      */     //   245: getfield lastTickPosX : D
/*      */     //   248: dsub
/*      */     //   249: fload_3
/*      */     //   250: f2d
/*      */     //   251: dmul
/*      */     //   252: dadd
/*      */     //   253: dstore #12
/*      */     //   255: aload #11
/*      */     //   257: getfield lastTickPosY : D
/*      */     //   260: aload #11
/*      */     //   262: getfield posY : D
/*      */     //   265: aload #11
/*      */     //   267: getfield lastTickPosY : D
/*      */     //   270: dsub
/*      */     //   271: fload_3
/*      */     //   272: f2d
/*      */     //   273: dmul
/*      */     //   274: dadd
/*      */     //   275: dstore #14
/*      */     //   277: aload #11
/*      */     //   279: getfield lastTickPosZ : D
/*      */     //   282: aload #11
/*      */     //   284: getfield posZ : D
/*      */     //   287: aload #11
/*      */     //   289: getfield lastTickPosZ : D
/*      */     //   292: dsub
/*      */     //   293: fload_3
/*      */     //   294: f2d
/*      */     //   295: dmul
/*      */     //   296: dadd
/*      */     //   297: dstore #16
/*      */     //   299: dload #12
/*      */     //   301: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerX : D
/*      */     //   304: dload #14
/*      */     //   306: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerY : D
/*      */     //   309: dload #16
/*      */     //   311: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerZ : D
/*      */     //   314: aload_0
/*      */     //   315: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   318: dload #12
/*      */     //   320: dload #14
/*      */     //   322: dload #16
/*      */     //   324: invokevirtual setRenderPosition : (DDD)V
/*      */     //   327: aload_0
/*      */     //   328: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   331: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   334: invokevirtual enableLightmap : ()V
/*      */     //   337: aload_0
/*      */     //   338: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   341: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   344: ldc_w 'global'
/*      */     //   347: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   350: aload_0
/*      */     //   351: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   354: invokevirtual getLoadedEntityList : ()Ljava/util/List;
/*      */     //   357: astore #18
/*      */     //   359: iload #4
/*      */     //   361: ifne -> 375
/*      */     //   364: aload_0
/*      */     //   365: aload #18
/*      */     //   367: invokeinterface size : ()I
/*      */     //   372: putfield countEntitiesTotal : I
/*      */     //   375: invokestatic isFogOff : ()Z
/*      */     //   378: ifeq -> 397
/*      */     //   381: aload_0
/*      */     //   382: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   385: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   388: getfield fogStandard : Z
/*      */     //   391: ifeq -> 397
/*      */     //   394: invokestatic disableFog : ()V
/*      */     //   397: getstatic net/optifine/reflect/Reflector.ForgeEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   400: invokevirtual exists : ()Z
/*      */     //   403: istore #19
/*      */     //   405: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   408: invokevirtual exists : ()Z
/*      */     //   411: istore #20
/*      */     //   413: iconst_0
/*      */     //   414: istore #21
/*      */     //   416: iload #21
/*      */     //   418: aload_0
/*      */     //   419: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   422: getfield weatherEffects : Ljava/util/List;
/*      */     //   425: invokeinterface size : ()I
/*      */     //   430: if_icmpge -> 521
/*      */     //   433: aload_0
/*      */     //   434: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   437: getfield weatherEffects : Ljava/util/List;
/*      */     //   440: iload #21
/*      */     //   442: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   447: checkcast net/minecraft/entity/Entity
/*      */     //   450: astore #22
/*      */     //   452: iload #19
/*      */     //   454: ifeq -> 480
/*      */     //   457: aload #22
/*      */     //   459: getstatic net/optifine/reflect/Reflector.ForgeEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   462: iconst_1
/*      */     //   463: anewarray java/lang/Object
/*      */     //   466: dup
/*      */     //   467: iconst_0
/*      */     //   468: iload #4
/*      */     //   470: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   473: aastore
/*      */     //   474: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   477: ifeq -> 515
/*      */     //   480: aload_0
/*      */     //   481: dup
/*      */     //   482: getfield countEntitiesRendered : I
/*      */     //   485: iconst_1
/*      */     //   486: iadd
/*      */     //   487: putfield countEntitiesRendered : I
/*      */     //   490: aload #22
/*      */     //   492: dload #5
/*      */     //   494: dload #7
/*      */     //   496: dload #9
/*      */     //   498: invokevirtual isInRangeToRender3d : (DDD)Z
/*      */     //   501: ifeq -> 515
/*      */     //   504: aload_0
/*      */     //   505: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   508: aload #22
/*      */     //   510: fload_3
/*      */     //   511: invokevirtual renderEntitySimple : (Lnet/minecraft/entity/Entity;F)Z
/*      */     //   514: pop
/*      */     //   515: iinc #21, 1
/*      */     //   518: goto -> 416
/*      */     //   521: aload_0
/*      */     //   522: invokevirtual isRenderEntityOutlines : ()Z
/*      */     //   525: ifeq -> 821
/*      */     //   528: sipush #519
/*      */     //   531: invokestatic depthFunc : (I)V
/*      */     //   534: invokestatic disableFog : ()V
/*      */     //   537: aload_0
/*      */     //   538: getfield entityOutlineFramebuffer : Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   541: invokevirtual framebufferClear : ()V
/*      */     //   544: aload_0
/*      */     //   545: getfield entityOutlineFramebuffer : Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   548: iconst_0
/*      */     //   549: invokevirtual bindFramebuffer : (Z)V
/*      */     //   552: aload_0
/*      */     //   553: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   556: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   559: ldc_w 'entityOutlines'
/*      */     //   562: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   565: invokestatic disableStandardItemLighting : ()V
/*      */     //   568: aload_0
/*      */     //   569: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   572: iconst_1
/*      */     //   573: invokevirtual setRenderOutlines : (Z)V
/*      */     //   576: iconst_0
/*      */     //   577: istore #21
/*      */     //   579: iload #21
/*      */     //   581: aload #18
/*      */     //   583: invokeinterface size : ()I
/*      */     //   588: if_icmpge -> 759
/*      */     //   591: aload #18
/*      */     //   593: iload #21
/*      */     //   595: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   600: checkcast net/minecraft/entity/Entity
/*      */     //   603: astore #22
/*      */     //   605: aload_0
/*      */     //   606: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   609: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   612: instanceof net/minecraft/entity/EntityLivingBase
/*      */     //   615: ifeq -> 638
/*      */     //   618: aload_0
/*      */     //   619: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   622: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   625: checkcast net/minecraft/entity/EntityLivingBase
/*      */     //   628: invokevirtual isPlayerSleeping : ()Z
/*      */     //   631: ifeq -> 638
/*      */     //   634: iconst_1
/*      */     //   635: goto -> 639
/*      */     //   638: iconst_0
/*      */     //   639: istore #23
/*      */     //   641: aload #22
/*      */     //   643: dload #5
/*      */     //   645: dload #7
/*      */     //   647: dload #9
/*      */     //   649: invokevirtual isInRangeToRender3d : (DDD)Z
/*      */     //   652: ifeq -> 704
/*      */     //   655: aload #22
/*      */     //   657: getfield ignoreFrustumCheck : Z
/*      */     //   660: ifne -> 692
/*      */     //   663: aload_2
/*      */     //   664: aload #22
/*      */     //   666: invokevirtual getEntityBoundingBox : ()Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   669: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/AxisAlignedBB;)Z
/*      */     //   674: ifne -> 692
/*      */     //   677: aload #22
/*      */     //   679: getfield riddenByEntity : Lnet/minecraft/entity/Entity;
/*      */     //   682: aload_0
/*      */     //   683: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   686: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   689: if_acmpne -> 704
/*      */     //   692: aload #22
/*      */     //   694: instanceof net/minecraft/entity/player/EntityPlayer
/*      */     //   697: ifeq -> 704
/*      */     //   700: iconst_1
/*      */     //   701: goto -> 705
/*      */     //   704: iconst_0
/*      */     //   705: istore #24
/*      */     //   707: aload #22
/*      */     //   709: aload_0
/*      */     //   710: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   713: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   716: if_acmpne -> 737
/*      */     //   719: aload_0
/*      */     //   720: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   723: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   726: getfield thirdPersonView : I
/*      */     //   729: ifne -> 737
/*      */     //   732: iload #23
/*      */     //   734: ifeq -> 753
/*      */     //   737: iload #24
/*      */     //   739: ifeq -> 753
/*      */     //   742: aload_0
/*      */     //   743: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   746: aload #22
/*      */     //   748: fload_3
/*      */     //   749: invokevirtual renderEntitySimple : (Lnet/minecraft/entity/Entity;F)Z
/*      */     //   752: pop
/*      */     //   753: iinc #21, 1
/*      */     //   756: goto -> 579
/*      */     //   759: aload_0
/*      */     //   760: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   763: iconst_0
/*      */     //   764: invokevirtual setRenderOutlines : (Z)V
/*      */     //   767: invokestatic enableStandardItemLighting : ()V
/*      */     //   770: iconst_0
/*      */     //   771: invokestatic depthMask : (Z)V
/*      */     //   774: aload_0
/*      */     //   775: getfield entityOutlineShader : Lnet/minecraft/client/shader/ShaderGroup;
/*      */     //   778: fload_3
/*      */     //   779: invokevirtual loadShaderGroup : (F)V
/*      */     //   782: invokestatic enableLighting : ()V
/*      */     //   785: iconst_1
/*      */     //   786: invokestatic depthMask : (Z)V
/*      */     //   789: aload_0
/*      */     //   790: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   793: invokevirtual getFramebuffer : ()Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   796: iconst_0
/*      */     //   797: invokevirtual bindFramebuffer : (Z)V
/*      */     //   800: invokestatic enableFog : ()V
/*      */     //   803: invokestatic enableBlend : ()V
/*      */     //   806: invokestatic enableColorMaterial : ()V
/*      */     //   809: sipush #515
/*      */     //   812: invokestatic depthFunc : (I)V
/*      */     //   815: invokestatic enableDepth : ()V
/*      */     //   818: invokestatic enableAlpha : ()V
/*      */     //   821: aload_0
/*      */     //   822: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   825: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   828: ldc_w 'entities'
/*      */     //   831: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   834: invokestatic isShaders : ()Z
/*      */     //   837: istore #21
/*      */     //   839: iload #21
/*      */     //   841: ifeq -> 847
/*      */     //   844: invokestatic beginEntities : ()V
/*      */     //   847: invokestatic updateItemRenderDistance : ()V
/*      */     //   850: aload_0
/*      */     //   851: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   854: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   857: getfield fancyGraphics : Z
/*      */     //   860: istore #22
/*      */     //   862: aload_0
/*      */     //   863: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   866: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   869: invokestatic isDroppedItemsFancy : ()Z
/*      */     //   872: putfield fancyGraphics : Z
/*      */     //   875: aload_0
/*      */     //   876: getfield renderInfosEntities : Ljava/util/List;
/*      */     //   879: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   884: astore #23
/*      */     //   886: aload #23
/*      */     //   888: invokeinterface hasNext : ()Z
/*      */     //   893: ifeq -> 1293
/*      */     //   896: aload #23
/*      */     //   898: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   903: astore #24
/*      */     //   905: aload #24
/*      */     //   907: checkcast net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation
/*      */     //   910: astore #25
/*      */     //   912: aload #25
/*      */     //   914: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   917: invokevirtual getChunk : ()Lnet/minecraft/world/chunk/Chunk;
/*      */     //   920: astore #26
/*      */     //   922: aload #26
/*      */     //   924: invokevirtual getEntityLists : ()[Lnet/minecraft/util/ClassInheritanceMultiMap;
/*      */     //   927: aload #25
/*      */     //   929: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   932: invokevirtual getPosition : ()Lnet/minecraft/util/BlockPos;
/*      */     //   935: invokevirtual getY : ()I
/*      */     //   938: bipush #16
/*      */     //   940: idiv
/*      */     //   941: aaload
/*      */     //   942: astore #27
/*      */     //   944: aload #27
/*      */     //   946: invokevirtual isEmpty : ()Z
/*      */     //   949: ifne -> 1290
/*      */     //   952: aload #27
/*      */     //   954: invokevirtual iterator : ()Ljava/util/Iterator;
/*      */     //   957: astore #28
/*      */     //   959: aload #28
/*      */     //   961: invokeinterface hasNext : ()Z
/*      */     //   966: ifne -> 972
/*      */     //   969: goto -> 886
/*      */     //   972: aload #28
/*      */     //   974: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   979: checkcast net/minecraft/entity/Entity
/*      */     //   982: astore #29
/*      */     //   984: iload #19
/*      */     //   986: ifeq -> 1012
/*      */     //   989: aload #29
/*      */     //   991: getstatic net/optifine/reflect/Reflector.ForgeEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   994: iconst_1
/*      */     //   995: anewarray java/lang/Object
/*      */     //   998: dup
/*      */     //   999: iconst_0
/*      */     //   1000: iload #4
/*      */     //   1002: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1005: aastore
/*      */     //   1006: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1009: ifeq -> 959
/*      */     //   1012: aload_0
/*      */     //   1013: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1016: aload #29
/*      */     //   1018: aload_2
/*      */     //   1019: dload #5
/*      */     //   1021: dload #7
/*      */     //   1023: dload #9
/*      */     //   1025: invokevirtual shouldRender : (Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z
/*      */     //   1028: ifne -> 1046
/*      */     //   1031: aload #29
/*      */     //   1033: getfield riddenByEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1036: aload_0
/*      */     //   1037: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1040: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1043: if_acmpne -> 1050
/*      */     //   1046: iconst_1
/*      */     //   1047: goto -> 1051
/*      */     //   1050: iconst_0
/*      */     //   1051: istore #30
/*      */     //   1053: iload #30
/*      */     //   1055: ifne -> 1061
/*      */     //   1058: goto -> 1212
/*      */     //   1061: aload_0
/*      */     //   1062: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1065: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   1068: instanceof net/minecraft/entity/EntityLivingBase
/*      */     //   1071: ifeq -> 1090
/*      */     //   1074: aload_0
/*      */     //   1075: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1078: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   1081: checkcast net/minecraft/entity/EntityLivingBase
/*      */     //   1084: invokevirtual isPlayerSleeping : ()Z
/*      */     //   1087: goto -> 1091
/*      */     //   1090: iconst_0
/*      */     //   1091: istore #31
/*      */     //   1093: aload #29
/*      */     //   1095: aload_0
/*      */     //   1096: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1099: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   1102: if_acmpne -> 1123
/*      */     //   1105: aload_0
/*      */     //   1106: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1109: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   1112: getfield thirdPersonView : I
/*      */     //   1115: ifne -> 1123
/*      */     //   1118: iload #31
/*      */     //   1120: ifeq -> 1209
/*      */     //   1123: aload #29
/*      */     //   1125: getfield posY : D
/*      */     //   1128: dconst_0
/*      */     //   1129: dcmpg
/*      */     //   1130: iflt -> 1164
/*      */     //   1133: aload #29
/*      */     //   1135: getfield posY : D
/*      */     //   1138: ldc2_w 256.0
/*      */     //   1141: dcmpl
/*      */     //   1142: ifge -> 1164
/*      */     //   1145: aload_0
/*      */     //   1146: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1149: new net/minecraft/util/BlockPos
/*      */     //   1152: dup
/*      */     //   1153: aload #29
/*      */     //   1155: invokespecial <init> : (Lnet/minecraft/entity/Entity;)V
/*      */     //   1158: invokevirtual isBlockLoaded : (Lnet/minecraft/util/BlockPos;)Z
/*      */     //   1161: ifeq -> 1209
/*      */     //   1164: aload_0
/*      */     //   1165: dup
/*      */     //   1166: getfield countEntitiesRendered : I
/*      */     //   1169: iconst_1
/*      */     //   1170: iadd
/*      */     //   1171: putfield countEntitiesRendered : I
/*      */     //   1174: aload_0
/*      */     //   1175: aload #29
/*      */     //   1177: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1180: iload #21
/*      */     //   1182: ifeq -> 1190
/*      */     //   1185: aload #29
/*      */     //   1187: invokestatic nextEntity : (Lnet/minecraft/entity/Entity;)V
/*      */     //   1190: aload_0
/*      */     //   1191: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1194: aload #29
/*      */     //   1196: fload_3
/*      */     //   1197: invokevirtual renderEntitySimple : (Lnet/minecraft/entity/Entity;F)Z
/*      */     //   1200: pop
/*      */     //   1201: aload_0
/*      */     //   1202: aconst_null
/*      */     //   1203: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1206: goto -> 1212
/*      */     //   1209: goto -> 959
/*      */     //   1212: iload #30
/*      */     //   1214: ifne -> 1287
/*      */     //   1217: aload #29
/*      */     //   1219: instanceof net/minecraft/entity/projectile/EntityWitherSkull
/*      */     //   1222: ifeq -> 1287
/*      */     //   1225: iload #19
/*      */     //   1227: ifeq -> 1253
/*      */     //   1230: aload #29
/*      */     //   1232: getstatic net/optifine/reflect/Reflector.ForgeEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1235: iconst_1
/*      */     //   1236: anewarray java/lang/Object
/*      */     //   1239: dup
/*      */     //   1240: iconst_0
/*      */     //   1241: iload #4
/*      */     //   1243: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1246: aastore
/*      */     //   1247: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1250: ifeq -> 1287
/*      */     //   1253: aload_0
/*      */     //   1254: aload #29
/*      */     //   1256: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1259: iload #21
/*      */     //   1261: ifeq -> 1269
/*      */     //   1264: aload #29
/*      */     //   1266: invokestatic nextEntity : (Lnet/minecraft/entity/Entity;)V
/*      */     //   1269: aload_0
/*      */     //   1270: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1273: invokevirtual getRenderManager : ()Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1276: aload #29
/*      */     //   1278: fload_3
/*      */     //   1279: invokevirtual renderWitherSkull : (Lnet/minecraft/entity/Entity;F)V
/*      */     //   1282: aload_0
/*      */     //   1283: aconst_null
/*      */     //   1284: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1287: goto -> 959
/*      */     //   1290: goto -> 886
/*      */     //   1293: aload_0
/*      */     //   1294: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1297: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   1300: iload #22
/*      */     //   1302: putfield fancyGraphics : Z
/*      */     //   1305: iload #21
/*      */     //   1307: ifeq -> 1316
/*      */     //   1310: invokestatic endEntities : ()V
/*      */     //   1313: invokestatic beginBlockEntities : ()V
/*      */     //   1316: aload_0
/*      */     //   1317: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1320: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   1323: ldc_w 'blockentities'
/*      */     //   1326: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   1329: invokestatic enableStandardItemLighting : ()V
/*      */     //   1332: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_hasFastRenderer : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1335: invokevirtual exists : ()Z
/*      */     //   1338: ifeq -> 1347
/*      */     //   1341: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1344: invokevirtual preDrawBatch : ()V
/*      */     //   1347: invokestatic updateTextRenderDistance : ()V
/*      */     //   1350: aload_0
/*      */     //   1351: getfield renderInfosTileEntities : Ljava/util/List;
/*      */     //   1354: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1359: astore #23
/*      */     //   1361: aload #23
/*      */     //   1363: invokeinterface hasNext : ()Z
/*      */     //   1368: ifeq -> 1550
/*      */     //   1371: aload #23
/*      */     //   1373: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1378: astore #24
/*      */     //   1380: aload #24
/*      */     //   1382: checkcast net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation
/*      */     //   1385: astore #25
/*      */     //   1387: aload #25
/*      */     //   1389: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   1392: invokevirtual getCompiledChunk : ()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
/*      */     //   1395: invokevirtual getTileEntities : ()Ljava/util/List;
/*      */     //   1398: astore #26
/*      */     //   1400: aload #26
/*      */     //   1402: invokeinterface isEmpty : ()Z
/*      */     //   1407: ifne -> 1547
/*      */     //   1410: aload #26
/*      */     //   1412: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1417: astore #27
/*      */     //   1419: aload #27
/*      */     //   1421: invokeinterface hasNext : ()Z
/*      */     //   1426: ifne -> 1432
/*      */     //   1429: goto -> 1361
/*      */     //   1432: aload #27
/*      */     //   1434: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1439: checkcast net/minecraft/tileentity/TileEntity
/*      */     //   1442: astore #28
/*      */     //   1444: iload #20
/*      */     //   1446: ifne -> 1452
/*      */     //   1449: goto -> 1514
/*      */     //   1452: aload #28
/*      */     //   1454: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1457: iconst_1
/*      */     //   1458: anewarray java/lang/Object
/*      */     //   1461: dup
/*      */     //   1462: iconst_0
/*      */     //   1463: iload #4
/*      */     //   1465: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1468: aastore
/*      */     //   1469: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1472: ifeq -> 1419
/*      */     //   1475: aload #28
/*      */     //   1477: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_getRenderBoundingBox : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1480: iconst_0
/*      */     //   1481: anewarray java/lang/Object
/*      */     //   1484: invokestatic call : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1487: checkcast net/minecraft/util/AxisAlignedBB
/*      */     //   1490: astore #29
/*      */     //   1492: aload #29
/*      */     //   1494: ifnull -> 1514
/*      */     //   1497: aload_2
/*      */     //   1498: aload #29
/*      */     //   1500: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/AxisAlignedBB;)Z
/*      */     //   1505: ifeq -> 1511
/*      */     //   1508: goto -> 1514
/*      */     //   1511: goto -> 1419
/*      */     //   1514: iload #21
/*      */     //   1516: ifeq -> 1524
/*      */     //   1519: aload #28
/*      */     //   1521: invokestatic nextBlockEntity : (Lnet/minecraft/tileentity/TileEntity;)V
/*      */     //   1524: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1527: aload #28
/*      */     //   1529: fload_3
/*      */     //   1530: iconst_m1
/*      */     //   1531: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   1534: aload_0
/*      */     //   1535: dup
/*      */     //   1536: getfield countTileEntitiesRendered : I
/*      */     //   1539: iconst_1
/*      */     //   1540: iadd
/*      */     //   1541: putfield countTileEntitiesRendered : I
/*      */     //   1544: goto -> 1419
/*      */     //   1547: goto -> 1361
/*      */     //   1550: aload_0
/*      */     //   1551: getfield field_181024_n : Ljava/util/Set;
/*      */     //   1554: dup
/*      */     //   1555: astore #23
/*      */     //   1557: monitorenter
/*      */     //   1558: aload_0
/*      */     //   1559: getfield field_181024_n : Ljava/util/Set;
/*      */     //   1562: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1567: astore #24
/*      */     //   1569: aload #24
/*      */     //   1571: invokeinterface hasNext : ()Z
/*      */     //   1576: ifeq -> 1642
/*      */     //   1579: aload #24
/*      */     //   1581: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1586: checkcast net/minecraft/tileentity/TileEntity
/*      */     //   1589: astore #25
/*      */     //   1591: iload #20
/*      */     //   1593: ifeq -> 1619
/*      */     //   1596: aload #25
/*      */     //   1598: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1601: iconst_1
/*      */     //   1602: anewarray java/lang/Object
/*      */     //   1605: dup
/*      */     //   1606: iconst_0
/*      */     //   1607: iload #4
/*      */     //   1609: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1612: aastore
/*      */     //   1613: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1616: ifeq -> 1639
/*      */     //   1619: iload #21
/*      */     //   1621: ifeq -> 1629
/*      */     //   1624: aload #25
/*      */     //   1626: invokestatic nextBlockEntity : (Lnet/minecraft/tileentity/TileEntity;)V
/*      */     //   1629: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1632: aload #25
/*      */     //   1634: fload_3
/*      */     //   1635: iconst_m1
/*      */     //   1636: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   1639: goto -> 1569
/*      */     //   1642: aload #23
/*      */     //   1644: monitorexit
/*      */     //   1645: goto -> 1656
/*      */     //   1648: astore #32
/*      */     //   1650: aload #23
/*      */     //   1652: monitorexit
/*      */     //   1653: aload #32
/*      */     //   1655: athrow
/*      */     //   1656: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_hasFastRenderer : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1659: invokevirtual exists : ()Z
/*      */     //   1662: ifeq -> 1673
/*      */     //   1665: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1668: iload #4
/*      */     //   1670: invokevirtual drawBatch : (I)V
/*      */     //   1673: aload_0
/*      */     //   1674: iconst_1
/*      */     //   1675: putfield renderOverlayDamaged : Z
/*      */     //   1678: aload_0
/*      */     //   1679: invokespecial preRenderDamagedBlocks : ()V
/*      */     //   1682: aload_0
/*      */     //   1683: getfield damagedBlocks : Ljava/util/Map;
/*      */     //   1686: invokeinterface values : ()Ljava/util/Collection;
/*      */     //   1691: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1696: astore #23
/*      */     //   1698: aload #23
/*      */     //   1700: invokeinterface hasNext : ()Z
/*      */     //   1705: ifeq -> 1992
/*      */     //   1708: aload #23
/*      */     //   1710: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1715: checkcast net/minecraft/client/renderer/DestroyBlockProgress
/*      */     //   1718: astore #24
/*      */     //   1720: aload #24
/*      */     //   1722: invokevirtual getPosition : ()Lnet/minecraft/util/BlockPos;
/*      */     //   1725: astore #25
/*      */     //   1727: aload_0
/*      */     //   1728: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1731: aload #25
/*      */     //   1733: invokevirtual getTileEntity : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1736: astore #26
/*      */     //   1738: aload #26
/*      */     //   1740: instanceof net/minecraft/tileentity/TileEntityChest
/*      */     //   1743: ifeq -> 1814
/*      */     //   1746: aload #26
/*      */     //   1748: checkcast net/minecraft/tileentity/TileEntityChest
/*      */     //   1751: astore #27
/*      */     //   1753: aload #27
/*      */     //   1755: getfield adjacentChestXNeg : Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   1758: ifnull -> 1785
/*      */     //   1761: aload #25
/*      */     //   1763: getstatic net/minecraft/util/EnumFacing.WEST : Lnet/minecraft/util/EnumFacing;
/*      */     //   1766: invokevirtual offset : (Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
/*      */     //   1769: astore #25
/*      */     //   1771: aload_0
/*      */     //   1772: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1775: aload #25
/*      */     //   1777: invokevirtual getTileEntity : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1780: astore #26
/*      */     //   1782: goto -> 1814
/*      */     //   1785: aload #27
/*      */     //   1787: getfield adjacentChestZNeg : Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   1790: ifnull -> 1814
/*      */     //   1793: aload #25
/*      */     //   1795: getstatic net/minecraft/util/EnumFacing.NORTH : Lnet/minecraft/util/EnumFacing;
/*      */     //   1798: invokevirtual offset : (Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
/*      */     //   1801: astore #25
/*      */     //   1803: aload_0
/*      */     //   1804: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1807: aload #25
/*      */     //   1809: invokevirtual getTileEntity : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1812: astore #26
/*      */     //   1814: aload_0
/*      */     //   1815: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1818: aload #25
/*      */     //   1820: invokevirtual getBlockState : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
/*      */     //   1823: invokeinterface getBlock : ()Lnet/minecraft/block/Block;
/*      */     //   1828: astore #27
/*      */     //   1830: iload #20
/*      */     //   1832: ifeq -> 1916
/*      */     //   1835: iconst_0
/*      */     //   1836: istore #28
/*      */     //   1838: aload #26
/*      */     //   1840: ifnull -> 1960
/*      */     //   1843: aload #26
/*      */     //   1845: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_shouldRenderInPass : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1848: iconst_1
/*      */     //   1849: anewarray java/lang/Object
/*      */     //   1852: dup
/*      */     //   1853: iconst_0
/*      */     //   1854: iload #4
/*      */     //   1856: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1859: aastore
/*      */     //   1860: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1863: ifeq -> 1960
/*      */     //   1866: aload #26
/*      */     //   1868: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_canRenderBreaking : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1871: iconst_0
/*      */     //   1872: anewarray java/lang/Object
/*      */     //   1875: invokestatic callBoolean : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1878: ifeq -> 1960
/*      */     //   1881: aload #26
/*      */     //   1883: getstatic net/optifine/reflect/Reflector.ForgeTileEntity_getRenderBoundingBox : Lnet/optifine/reflect/ReflectorMethod;
/*      */     //   1886: iconst_0
/*      */     //   1887: anewarray java/lang/Object
/*      */     //   1890: invokestatic call : (Ljava/lang/Object;Lnet/optifine/reflect/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1893: checkcast net/minecraft/util/AxisAlignedBB
/*      */     //   1896: astore #29
/*      */     //   1898: aload #29
/*      */     //   1900: ifnull -> 1913
/*      */     //   1903: aload_2
/*      */     //   1904: aload #29
/*      */     //   1906: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/AxisAlignedBB;)Z
/*      */     //   1911: istore #28
/*      */     //   1913: goto -> 1960
/*      */     //   1916: aload #26
/*      */     //   1918: ifnull -> 1957
/*      */     //   1921: aload #27
/*      */     //   1923: instanceof net/minecraft/block/BlockChest
/*      */     //   1926: ifne -> 1953
/*      */     //   1929: aload #27
/*      */     //   1931: instanceof net/minecraft/block/BlockEnderChest
/*      */     //   1934: ifne -> 1953
/*      */     //   1937: aload #27
/*      */     //   1939: instanceof net/minecraft/block/BlockSign
/*      */     //   1942: ifne -> 1953
/*      */     //   1945: aload #27
/*      */     //   1947: instanceof net/minecraft/block/BlockSkull
/*      */     //   1950: ifeq -> 1957
/*      */     //   1953: iconst_1
/*      */     //   1954: goto -> 1958
/*      */     //   1957: iconst_0
/*      */     //   1958: istore #28
/*      */     //   1960: iload #28
/*      */     //   1962: ifeq -> 1989
/*      */     //   1965: iload #21
/*      */     //   1967: ifeq -> 1975
/*      */     //   1970: aload #26
/*      */     //   1972: invokestatic nextBlockEntity : (Lnet/minecraft/tileentity/TileEntity;)V
/*      */     //   1975: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1978: aload #26
/*      */     //   1980: fload_3
/*      */     //   1981: aload #24
/*      */     //   1983: invokevirtual getPartialBlockDamage : ()I
/*      */     //   1986: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   1989: goto -> 1698
/*      */     //   1992: aload_0
/*      */     //   1993: invokespecial postRenderDamagedBlocks : ()V
/*      */     //   1996: aload_0
/*      */     //   1997: iconst_0
/*      */     //   1998: putfield renderOverlayDamaged : Z
/*      */     //   2001: iload #21
/*      */     //   2003: ifeq -> 2009
/*      */     //   2006: invokestatic endBlockEntities : ()V
/*      */     //   2009: getstatic net/minecraft/client/renderer/RenderGlobal.renderEntitiesCounter : I
/*      */     //   2012: iconst_1
/*      */     //   2013: isub
/*      */     //   2014: putstatic net/minecraft/client/renderer/RenderGlobal.renderEntitiesCounter : I
/*      */     //   2017: aload_0
/*      */     //   2018: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2021: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   2024: invokevirtual disableLightmap : ()V
/*      */     //   2027: aload_0
/*      */     //   2028: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2031: getfield mcProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   2034: invokevirtual endSection : ()V
/*      */     //   2037: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #645	-> 0
/*      */     //   #647	-> 3
/*      */     //   #649	-> 12
/*      */     //   #652	-> 24
/*      */     //   #654	-> 31
/*      */     //   #656	-> 36
/*      */     //   #659	-> 37
/*      */     //   #663	-> 50
/*      */     //   #664	-> 69
/*      */     //   #665	-> 88
/*      */     //   #666	-> 107
/*      */     //   #667	-> 119
/*      */     //   #668	-> 151
/*      */     //   #669	-> 191
/*      */     //   #671	-> 199
/*      */     //   #673	-> 204
/*      */     //   #674	-> 209
/*      */     //   #675	-> 214
/*      */     //   #676	-> 219
/*      */     //   #679	-> 224
/*      */     //   #680	-> 233
/*      */     //   #681	-> 255
/*      */     //   #682	-> 277
/*      */     //   #683	-> 299
/*      */     //   #684	-> 304
/*      */     //   #685	-> 309
/*      */     //   #686	-> 314
/*      */     //   #687	-> 327
/*      */     //   #688	-> 337
/*      */     //   #689	-> 350
/*      */     //   #691	-> 359
/*      */     //   #693	-> 364
/*      */     //   #696	-> 375
/*      */     //   #698	-> 394
/*      */     //   #701	-> 397
/*      */     //   #702	-> 405
/*      */     //   #704	-> 413
/*      */     //   #706	-> 433
/*      */     //   #708	-> 452
/*      */     //   #710	-> 480
/*      */     //   #712	-> 490
/*      */     //   #714	-> 504
/*      */     //   #704	-> 515
/*      */     //   #719	-> 521
/*      */     //   #721	-> 528
/*      */     //   #722	-> 534
/*      */     //   #723	-> 537
/*      */     //   #724	-> 544
/*      */     //   #725	-> 552
/*      */     //   #726	-> 565
/*      */     //   #727	-> 568
/*      */     //   #729	-> 576
/*      */     //   #731	-> 591
/*      */     //   #732	-> 605
/*      */     //   #733	-> 641
/*      */     //   #735	-> 707
/*      */     //   #737	-> 742
/*      */     //   #729	-> 753
/*      */     //   #741	-> 759
/*      */     //   #742	-> 767
/*      */     //   #743	-> 770
/*      */     //   #744	-> 774
/*      */     //   #745	-> 782
/*      */     //   #746	-> 785
/*      */     //   #747	-> 789
/*      */     //   #748	-> 800
/*      */     //   #749	-> 803
/*      */     //   #750	-> 806
/*      */     //   #751	-> 809
/*      */     //   #752	-> 815
/*      */     //   #753	-> 818
/*      */     //   #756	-> 821
/*      */     //   #757	-> 834
/*      */     //   #759	-> 839
/*      */     //   #761	-> 844
/*      */     //   #764	-> 847
/*      */     //   #765	-> 850
/*      */     //   #766	-> 862
/*      */     //   #769	-> 875
/*      */     //   #771	-> 905
/*      */     //   #772	-> 912
/*      */     //   #773	-> 922
/*      */     //   #775	-> 944
/*      */     //   #777	-> 952
/*      */     //   #786	-> 959
/*      */     //   #788	-> 969
/*      */     //   #791	-> 972
/*      */     //   #793	-> 984
/*      */     //   #795	-> 1012
/*      */     //   #797	-> 1053
/*      */     //   #799	-> 1058
/*      */     //   #802	-> 1061
/*      */     //   #804	-> 1093
/*      */     //   #806	-> 1164
/*      */     //   #807	-> 1174
/*      */     //   #809	-> 1180
/*      */     //   #811	-> 1185
/*      */     //   #814	-> 1190
/*      */     //   #815	-> 1201
/*      */     //   #816	-> 1206
/*      */     //   #818	-> 1209
/*      */     //   #821	-> 1212
/*      */     //   #823	-> 1253
/*      */     //   #825	-> 1259
/*      */     //   #827	-> 1264
/*      */     //   #830	-> 1269
/*      */     //   #831	-> 1282
/*      */     //   #833	-> 1287
/*      */     //   #835	-> 1290
/*      */     //   #837	-> 1293
/*      */     //   #839	-> 1305
/*      */     //   #841	-> 1310
/*      */     //   #842	-> 1313
/*      */     //   #845	-> 1316
/*      */     //   #846	-> 1329
/*      */     //   #848	-> 1332
/*      */     //   #850	-> 1341
/*      */     //   #853	-> 1347
/*      */     //   #856	-> 1350
/*      */     //   #858	-> 1380
/*      */     //   #859	-> 1387
/*      */     //   #861	-> 1400
/*      */     //   #863	-> 1410
/*      */     //   #871	-> 1419
/*      */     //   #873	-> 1429
/*      */     //   #876	-> 1432
/*      */     //   #878	-> 1444
/*      */     //   #880	-> 1449
/*      */     //   #883	-> 1452
/*      */     //   #885	-> 1475
/*      */     //   #887	-> 1492
/*      */     //   #889	-> 1508
/*      */     //   #891	-> 1511
/*      */     //   #894	-> 1514
/*      */     //   #896	-> 1519
/*      */     //   #899	-> 1524
/*      */     //   #900	-> 1534
/*      */     //   #901	-> 1544
/*      */     //   #903	-> 1547
/*      */     //   #905	-> 1550
/*      */     //   #907	-> 1558
/*      */     //   #909	-> 1591
/*      */     //   #911	-> 1619
/*      */     //   #913	-> 1624
/*      */     //   #916	-> 1629
/*      */     //   #918	-> 1639
/*      */     //   #919	-> 1642
/*      */     //   #921	-> 1656
/*      */     //   #923	-> 1665
/*      */     //   #926	-> 1673
/*      */     //   #927	-> 1678
/*      */     //   #929	-> 1682
/*      */     //   #931	-> 1720
/*      */     //   #932	-> 1727
/*      */     //   #934	-> 1738
/*      */     //   #936	-> 1746
/*      */     //   #938	-> 1753
/*      */     //   #940	-> 1761
/*      */     //   #941	-> 1771
/*      */     //   #943	-> 1785
/*      */     //   #945	-> 1793
/*      */     //   #946	-> 1803
/*      */     //   #950	-> 1814
/*      */     //   #953	-> 1830
/*      */     //   #955	-> 1835
/*      */     //   #957	-> 1838
/*      */     //   #959	-> 1881
/*      */     //   #961	-> 1898
/*      */     //   #963	-> 1903
/*      */     //   #965	-> 1913
/*      */     //   #969	-> 1916
/*      */     //   #972	-> 1960
/*      */     //   #974	-> 1965
/*      */     //   #976	-> 1970
/*      */     //   #979	-> 1975
/*      */     //   #981	-> 1989
/*      */     //   #983	-> 1992
/*      */     //   #984	-> 1996
/*      */     //   #986	-> 2001
/*      */     //   #988	-> 2006
/*      */     //   #991	-> 2009
/*      */     //   #992	-> 2017
/*      */     //   #993	-> 2027
/*      */     //   #995	-> 2037
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   452	63	22	entity1	Lnet/minecraft/entity/Entity;
/*      */     //   416	105	21	j	I
/*      */     //   605	148	22	entity3	Lnet/minecraft/entity/Entity;
/*      */     //   641	112	23	flag2	Z
/*      */     //   707	46	24	flag3	Z
/*      */     //   579	180	21	k	I
/*      */     //   1093	116	31	flag5	Z
/*      */     //   984	303	29	entity2	Lnet/minecraft/entity/Entity;
/*      */     //   1053	234	30	flag4	Z
/*      */     //   959	331	28	iterator	Ljava/util/Iterator;
/*      */     //   912	378	25	renderglobal$containerlocalrenderinformation	Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
/*      */     //   922	368	26	chunk	Lnet/minecraft/world/chunk/Chunk;
/*      */     //   944	346	27	classinheritancemultimap	Lnet/minecraft/util/ClassInheritanceMultiMap;
/*      */     //   905	385	24	e	Ljava/lang/Object;
/*      */     //   1492	19	29	axisalignedbb1	Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   1444	100	28	tileentity1	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1419	128	27	iterator1	Ljava/util/Iterator;
/*      */     //   1387	160	25	renderglobal$containerlocalrenderinformation1	Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
/*      */     //   1400	147	26	list1	Ljava/util/List;
/*      */     //   1380	167	24	e	Ljava/lang/Object;
/*      */     //   1591	48	25	tileentity	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1753	61	27	tileentitychest	Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   1898	15	29	axisalignedbb	Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   1838	78	28	flag8	Z
/*      */     //   1727	262	25	blockpos	Lnet/minecraft/util/BlockPos;
/*      */     //   1738	251	26	tileentity2	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1830	159	27	block	Lnet/minecraft/block/Block;
/*      */     //   1960	29	28	flag8	Z
/*      */     //   1720	269	24	destroyblockprogress	Lnet/minecraft/client/renderer/DestroyBlockProgress;
/*      */     //   69	1968	5	d0	D
/*      */     //   88	1949	7	d1	D
/*      */     //   107	1930	9	d2	D
/*      */     //   233	1804	11	entity	Lnet/minecraft/entity/Entity;
/*      */     //   255	1782	12	d3	D
/*      */     //   277	1760	14	d4	D
/*      */     //   299	1738	16	d5	D
/*      */     //   359	1678	18	list	Ljava/util/List;
/*      */     //   405	1632	19	flag	Z
/*      */     //   413	1624	20	flag1	Z
/*      */     //   839	1198	21	flag6	Z
/*      */     //   862	1175	22	flag7	Z
/*      */     //   0	2038	0	this	Lnet/minecraft/client/renderer/RenderGlobal;
/*      */     //   0	2038	1	renderViewEntity	Lnet/minecraft/entity/Entity;
/*      */     //   0	2038	2	camera	Lnet/minecraft/client/renderer/culling/ICamera;
/*      */     //   0	2038	3	partialTicks	F
/*      */     //   3	2035	4	i	I
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   944	346	27	classinheritancemultimap	Lnet/minecraft/util/ClassInheritanceMultiMap<Lnet/minecraft/entity/Entity;>;
/*      */     //   1400	147	26	list1	Ljava/util/List<Lnet/minecraft/tileentity/TileEntity;>;
/*      */     //   359	1678	18	list	Ljava/util/List<Lnet/minecraft/entity/Entity;>;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   1558	1645	1648	finally
/*      */     //   1648	1653	1648	finally
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugInfoRenders() {
/* 1002 */     int i = this.viewFrustum.renderChunks.length;
/* 1003 */     int j = 0;
/*      */     
/* 1005 */     for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
/*      */       
/* 1007 */       CompiledChunk compiledchunk = renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk;
/*      */       
/* 1009 */       if (compiledchunk != CompiledChunk.DUMMY && !compiledchunk.isEmpty())
/*      */       {
/* 1011 */         j++;
/*      */       }
/*      */     } 
/*      */     
/* 1015 */     return String.format("C: %d/%d %sD: %d, %s", new Object[] { Integer.valueOf(j), Integer.valueOf(i), this.mc.renderChunksMany ? "(s) " : "", Integer.valueOf(this.renderDistanceChunks), this.renderDispatcher.getDebugInfo() });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugInfoEntities() {
/* 1023 */     return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersionDebug();
/*      */   }
/*      */   
/*      */   public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
/*      */     Frustum frustum;
/* 1028 */     if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks)
/*      */     {
/* 1030 */       loadRenderers();
/*      */     }
/*      */     
/* 1033 */     this.theWorld.theProfiler.startSection("camera");
/* 1034 */     double d0 = viewEntity.posX - this.frustumUpdatePosX;
/* 1035 */     double d1 = viewEntity.posY - this.frustumUpdatePosY;
/* 1036 */     double d2 = viewEntity.posZ - this.frustumUpdatePosZ;
/*      */     
/* 1038 */     if (this.frustumUpdatePosChunkX != viewEntity.chunkCoordX || this.frustumUpdatePosChunkY != viewEntity.chunkCoordY || this.frustumUpdatePosChunkZ != viewEntity.chunkCoordZ || d0 * d0 + d1 * d1 + d2 * d2 > 16.0D) {
/*      */       
/* 1040 */       this.frustumUpdatePosX = viewEntity.posX;
/* 1041 */       this.frustumUpdatePosY = viewEntity.posY;
/* 1042 */       this.frustumUpdatePosZ = viewEntity.posZ;
/* 1043 */       this.frustumUpdatePosChunkX = viewEntity.chunkCoordX;
/* 1044 */       this.frustumUpdatePosChunkY = viewEntity.chunkCoordY;
/* 1045 */       this.frustumUpdatePosChunkZ = viewEntity.chunkCoordZ;
/* 1046 */       this.viewFrustum.updateChunkPositions(viewEntity.posX, viewEntity.posZ);
/*      */     } 
/*      */     
/* 1049 */     if (Config.isDynamicLights())
/*      */     {
/* 1051 */       DynamicLights.update(this);
/*      */     }
/*      */     
/* 1054 */     this.theWorld.theProfiler.endStartSection("renderlistcamera");
/* 1055 */     double d3 = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
/* 1056 */     double d4 = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
/* 1057 */     double d5 = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
/* 1058 */     this.renderContainer.initialize(d3, d4, d5);
/* 1059 */     this.theWorld.theProfiler.endStartSection("cull");
/*      */     
/* 1061 */     if (this.debugFixedClippingHelper != null) {
/*      */       
/* 1063 */       Frustum frustum1 = new Frustum(this.debugFixedClippingHelper);
/* 1064 */       frustum1.setPosition(this.debugTerrainFrustumPosition.field_181059_a, this.debugTerrainFrustumPosition.field_181060_b, this.debugTerrainFrustumPosition.field_181061_c);
/* 1065 */       frustum = frustum1;
/*      */     } 
/*      */     
/* 1068 */     this.mc.mcProfiler.endStartSection("culling");
/* 1069 */     BlockPos blockpos = new BlockPos(d3, d4 + viewEntity.getEyeHeight(), d5);
/* 1070 */     RenderChunk renderchunk = this.viewFrustum.getRenderChunk(blockpos);
/* 1071 */     new BlockPos(MathHelper.floor_double(d3 / 16.0D) * 16, MathHelper.floor_double(d4 / 16.0D) * 16, MathHelper.floor_double(d5 / 16.0D) * 16);
/* 1072 */     this.displayListEntitiesDirty = (this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || viewEntity.posX != this.lastViewEntityX || viewEntity.posY != this.lastViewEntityY || viewEntity.posZ != this.lastViewEntityZ || viewEntity.rotationPitch != this.lastViewEntityPitch || viewEntity.rotationYaw != this.lastViewEntityYaw);
/* 1073 */     this.lastViewEntityX = viewEntity.posX;
/* 1074 */     this.lastViewEntityY = viewEntity.posY;
/* 1075 */     this.lastViewEntityZ = viewEntity.posZ;
/* 1076 */     this.lastViewEntityPitch = viewEntity.rotationPitch;
/* 1077 */     this.lastViewEntityYaw = viewEntity.rotationYaw;
/* 1078 */     boolean flag = (this.debugFixedClippingHelper != null);
/* 1079 */     this.mc.mcProfiler.endStartSection("update");
/* 1080 */     Lagometer.timerVisibility.start();
/* 1081 */     int i = getCountLoadedChunks();
/*      */     
/* 1083 */     if (i != this.countLoadedChunksPrev) {
/*      */       
/* 1085 */       this.countLoadedChunksPrev = i;
/* 1086 */       this.displayListEntitiesDirty = true;
/*      */     } 
/*      */     
/* 1089 */     int j = 256;
/*      */     
/* 1091 */     if (!ChunkVisibility.isFinished())
/*      */     {
/* 1093 */       this.displayListEntitiesDirty = true;
/*      */     }
/*      */     
/* 1096 */     if (!flag && this.displayListEntitiesDirty && Config.isIntegratedServerRunning())
/*      */     {
/* 1098 */       j = ChunkVisibility.getMaxChunkY((World)this.theWorld, viewEntity, this.renderDistanceChunks);
/*      */     }
/*      */     
/* 1101 */     RenderChunk renderchunk1 = this.viewFrustum.getRenderChunk(new BlockPos(viewEntity.posX, viewEntity.posY, viewEntity.posZ));
/*      */     
/* 1103 */     if (Shaders.isShadowPass) {
/*      */       
/* 1105 */       this.renderInfos = this.renderInfosShadow;
/* 1106 */       this.renderInfosEntities = this.renderInfosEntitiesShadow;
/* 1107 */       this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;
/*      */       
/* 1109 */       if (!flag && this.displayListEntitiesDirty) {
/*      */         
/* 1111 */         clearRenderInfos();
/*      */         
/* 1113 */         if (renderchunk1 != null && renderchunk1.getPosition().getY() > j)
/*      */         {
/* 1115 */           this.renderInfosEntities.add(renderchunk1.getRenderInfo());
/*      */         }
/*      */         
/* 1118 */         Iterator<RenderChunk> iterator = ShadowUtils.makeShadowChunkIterator(this.theWorld, partialTicks, viewEntity, this.renderDistanceChunks, this.viewFrustum);
/*      */         
/* 1120 */         while (iterator.hasNext()) {
/*      */           
/* 1122 */           RenderChunk renderchunk2 = iterator.next();
/*      */           
/* 1124 */           if (renderchunk2 != null && renderchunk2.getPosition().getY() <= j)
/*      */           {
/* 1126 */             ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = renderchunk2.getRenderInfo();
/*      */             
/* 1128 */             if (!renderchunk2.compiledChunk.isEmpty() || renderchunk2.isNeedsUpdate())
/*      */             {
/* 1130 */               this.renderInfos.add(renderglobal$containerlocalrenderinformation);
/*      */             }
/*      */             
/* 1133 */             if (ChunkUtils.hasEntities(renderchunk2.getChunk()))
/*      */             {
/* 1135 */               this.renderInfosEntities.add(renderglobal$containerlocalrenderinformation);
/*      */             }
/*      */             
/* 1138 */             if (renderchunk2.getCompiledChunk().getTileEntities().size() > 0)
/*      */             {
/* 1140 */               this.renderInfosTileEntities.add(renderglobal$containerlocalrenderinformation);
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1148 */       this.renderInfos = this.renderInfosNormal;
/* 1149 */       this.renderInfosEntities = this.renderInfosEntitiesNormal;
/* 1150 */       this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
/*      */     } 
/*      */     
/* 1153 */     if (!flag && this.displayListEntitiesDirty && !Shaders.isShadowPass) {
/*      */       
/* 1155 */       this.displayListEntitiesDirty = false;
/* 1156 */       clearRenderInfos();
/* 1157 */       this.visibilityDeque.clear();
/* 1158 */       Deque<ContainerLocalRenderInformation> deque = this.visibilityDeque;
/* 1159 */       boolean flag1 = this.mc.renderChunksMany;
/*      */       
/* 1161 */       if (renderchunk != null && renderchunk.getPosition().getY() <= j) {
/*      */         
/* 1163 */         boolean flag2 = false;
/* 1164 */         ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation4 = new ContainerLocalRenderInformation(renderchunk, null, 0);
/* 1165 */         Set set1 = SET_ALL_FACINGS;
/*      */         
/* 1167 */         if (set1.size() == 1) {
/*      */           
/* 1169 */           Vector3f vector3f = getViewVector(viewEntity, partialTicks);
/* 1170 */           EnumFacing enumfacing2 = EnumFacing.getFacingFromVector(vector3f.x, vector3f.y, vector3f.z).getOpposite();
/* 1171 */           set1.remove(enumfacing2);
/*      */         } 
/*      */         
/* 1174 */         if (set1.isEmpty())
/*      */         {
/* 1176 */           flag2 = true;
/*      */         }
/*      */         
/* 1179 */         if (flag2 && !playerSpectator)
/*      */         {
/* 1181 */           this.renderInfos.add(renderglobal$containerlocalrenderinformation4);
/*      */         }
/*      */         else
/*      */         {
/* 1185 */           if (playerSpectator && this.theWorld.getBlockState(blockpos).getBlock().isOpaqueCube())
/*      */           {
/* 1187 */             flag1 = false;
/*      */           }
/*      */           
/* 1190 */           renderchunk.setFrameIndex(frameCount);
/* 1191 */           deque.add(renderglobal$containerlocalrenderinformation4);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1196 */         int j1 = (blockpos.getY() > 0) ? Math.min(j, 248) : 8;
/*      */         
/* 1198 */         if (renderchunk1 != null)
/*      */         {
/* 1200 */           this.renderInfosEntities.add(renderchunk1.getRenderInfo());
/*      */         }
/*      */         
/* 1203 */         for (int k = -this.renderDistanceChunks; k <= this.renderDistanceChunks; k++) {
/*      */           
/* 1205 */           for (int l = -this.renderDistanceChunks; l <= this.renderDistanceChunks; l++) {
/*      */             
/* 1207 */             RenderChunk renderchunk3 = this.viewFrustum.getRenderChunk(new BlockPos((k << 4) + 8, j1, (l << 4) + 8));
/*      */             
/* 1209 */             if (renderchunk3 != null && renderchunk3.isBoundingBoxInFrustum((ICamera)frustum, frameCount)) {
/*      */               
/* 1211 */               renderchunk3.setFrameIndex(frameCount);
/* 1212 */               ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 = renderchunk3.getRenderInfo();
/* 1213 */               renderglobal$containerlocalrenderinformation1.initialize(null, 0);
/* 1214 */               deque.add(renderglobal$containerlocalrenderinformation1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1220 */       this.mc.mcProfiler.startSection("iteration");
/* 1221 */       boolean flag3 = Config.isFogOn();
/*      */       
/* 1223 */       while (!deque.isEmpty()) {
/*      */         
/* 1225 */         ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation5 = deque.poll();
/* 1226 */         RenderChunk renderchunk6 = renderglobal$containerlocalrenderinformation5.renderChunk;
/* 1227 */         EnumFacing enumfacing1 = renderglobal$containerlocalrenderinformation5.facing;
/* 1228 */         CompiledChunk compiledchunk = renderchunk6.compiledChunk;
/*      */         
/* 1230 */         if (!compiledchunk.isEmpty() || renderchunk6.isNeedsUpdate())
/*      */         {
/* 1232 */           this.renderInfos.add(renderglobal$containerlocalrenderinformation5);
/*      */         }
/*      */         
/* 1235 */         if (ChunkUtils.hasEntities(renderchunk6.getChunk()))
/*      */         {
/* 1237 */           this.renderInfosEntities.add(renderglobal$containerlocalrenderinformation5);
/*      */         }
/*      */         
/* 1240 */         if (compiledchunk.getTileEntities().size() > 0)
/*      */         {
/* 1242 */           this.renderInfosTileEntities.add(renderglobal$containerlocalrenderinformation5);
/*      */         }
/*      */         
/* 1245 */         for (EnumFacing enumfacing : flag1 ? ChunkVisibility.getFacingsNotOpposite(renderglobal$containerlocalrenderinformation5.setFacing) : EnumFacing.VALUES) {
/*      */           
/* 1247 */           if (!flag1 || enumfacing1 == null || compiledchunk.isVisible(enumfacing1.getOpposite(), enumfacing)) {
/*      */             
/* 1249 */             RenderChunk renderchunk4 = getRenderChunkOffset(blockpos, renderchunk6, enumfacing, flag3, j);
/*      */             
/* 1251 */             if (renderchunk4 != null && renderchunk4.setFrameIndex(frameCount) && renderchunk4.isBoundingBoxInFrustum((ICamera)frustum, frameCount)) {
/*      */               
/* 1253 */               int i1 = renderglobal$containerlocalrenderinformation5.setFacing | 1 << enumfacing.ordinal();
/* 1254 */               ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation2 = renderchunk4.getRenderInfo();
/* 1255 */               renderglobal$containerlocalrenderinformation2.initialize(enumfacing, i1);
/* 1256 */               deque.add(renderglobal$containerlocalrenderinformation2);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1262 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1265 */     this.mc.mcProfiler.endStartSection("captureFrustum");
/*      */     
/* 1267 */     if (this.debugFixTerrainFrustum) {
/*      */       
/* 1269 */       fixTerrainFrustum(d3, d4, d5);
/* 1270 */       this.debugFixTerrainFrustum = false;
/*      */     } 
/*      */     
/* 1273 */     Lagometer.timerVisibility.end();
/*      */     
/* 1275 */     if (Shaders.isShadowPass) {
/*      */       
/* 1277 */       Shaders.mcProfilerEndSection();
/*      */     }
/*      */     else {
/*      */       
/* 1281 */       this.mc.mcProfiler.endStartSection("rebuildNear");
/* 1282 */       this.renderDispatcher.clearChunkUpdates();
/* 1283 */       Set<RenderChunk> set = this.chunksToUpdate;
/* 1284 */       this.chunksToUpdate = Sets.newLinkedHashSet();
/* 1285 */       Lagometer.timerChunkUpdate.start();
/*      */       
/* 1287 */       for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation3 : this.renderInfos) {
/*      */         
/* 1289 */         RenderChunk renderchunk5 = renderglobal$containerlocalrenderinformation3.renderChunk;
/*      */         
/* 1291 */         if (renderchunk5.isNeedsUpdate() || set.contains(renderchunk5)) {
/*      */           
/* 1293 */           this.displayListEntitiesDirty = true;
/* 1294 */           BlockPos blockpos1 = renderchunk5.getPosition();
/* 1295 */           boolean flag4 = (blockpos.distanceSq((blockpos1.getX() + 8), (blockpos1.getY() + 8), (blockpos1.getZ() + 8)) < 768.0D);
/*      */           
/* 1297 */           if (!flag4) {
/*      */             
/* 1299 */             this.chunksToUpdate.add(renderchunk5); continue;
/*      */           } 
/* 1301 */           if (!renderchunk5.isPlayerUpdate()) {
/*      */             
/* 1303 */             this.chunksToUpdateForced.add(renderchunk5);
/*      */             
/*      */             continue;
/*      */           } 
/* 1307 */           this.mc.mcProfiler.startSection("build near");
/* 1308 */           this.renderDispatcher.updateChunkNow(renderchunk5);
/* 1309 */           renderchunk5.setNeedsUpdate(false);
/* 1310 */           this.mc.mcProfiler.endSection();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1315 */       Lagometer.timerChunkUpdate.end();
/* 1316 */       this.chunksToUpdate.addAll(set);
/* 1317 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isPositionInRenderChunk(BlockPos pos, RenderChunk renderChunkIn) {
/* 1323 */     BlockPos blockpos = renderChunkIn.getPosition();
/* 1324 */     return (MathHelper.abs_int(pos.getX() - blockpos.getX()) > 16) ? false : ((MathHelper.abs_int(pos.getY() - blockpos.getY()) > 16) ? false : ((MathHelper.abs_int(pos.getZ() - blockpos.getZ()) <= 16)));
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<EnumFacing> getVisibleFacings(BlockPos pos) {
/* 1329 */     VisGraph visgraph = new VisGraph();
/* 1330 */     BlockPos blockpos = new BlockPos(pos.getX() >> 4 << 4, pos.getY() >> 4 << 4, pos.getZ() >> 4 << 4);
/* 1331 */     Chunk chunk = this.theWorld.getChunkFromBlockCoords(blockpos);
/*      */     
/* 1333 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos, blockpos.add(15, 15, 15))) {
/*      */       
/* 1335 */       if (chunk.getBlock((BlockPos)blockpos$mutableblockpos).isOpaqueCube())
/*      */       {
/* 1337 */         visgraph.func_178606_a((BlockPos)blockpos$mutableblockpos);
/*      */       }
/*      */     } 
/* 1340 */     return visgraph.func_178609_b(pos);
/*      */   }
/*      */ 
/*      */   
/*      */   private RenderChunk getRenderChunkOffset(BlockPos p_getRenderChunkOffset_1_, RenderChunk p_getRenderChunkOffset_2_, EnumFacing p_getRenderChunkOffset_3_, boolean p_getRenderChunkOffset_4_, int p_getRenderChunkOffset_5_) {
/* 1345 */     RenderChunk renderchunk = p_getRenderChunkOffset_2_.getRenderChunkNeighbour(p_getRenderChunkOffset_3_);
/*      */     
/* 1347 */     if (renderchunk == null)
/*      */     {
/* 1349 */       return null;
/*      */     }
/* 1351 */     if (renderchunk.getPosition().getY() > p_getRenderChunkOffset_5_)
/*      */     {
/* 1353 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1357 */     if (p_getRenderChunkOffset_4_) {
/*      */       
/* 1359 */       BlockPos blockpos = renderchunk.getPosition();
/* 1360 */       int i = p_getRenderChunkOffset_1_.getX() - blockpos.getX();
/* 1361 */       int j = p_getRenderChunkOffset_1_.getZ() - blockpos.getZ();
/* 1362 */       int k = i * i + j * j;
/*      */       
/* 1364 */       if (k > this.renderDistanceSq)
/*      */       {
/* 1366 */         return null;
/*      */       }
/*      */     } 
/*      */     
/* 1370 */     return renderchunk;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fixTerrainFrustum(double x, double y, double z) {
/* 1376 */     this.debugFixedClippingHelper = (ClippingHelper)new ClippingHelperImpl();
/* 1377 */     ((ClippingHelperImpl)this.debugFixedClippingHelper).init();
/* 1378 */     Matrix4f matrix4f = new Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
/* 1379 */     matrix4f.transpose();
/* 1380 */     Matrix4f matrix4f1 = new Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
/* 1381 */     matrix4f1.transpose();
/* 1382 */     Matrix4f matrix4f2 = new Matrix4f();
/* 1383 */     Matrix4f.mul((Matrix4f)matrix4f1, (Matrix4f)matrix4f, (Matrix4f)matrix4f2);
/* 1384 */     matrix4f2.invert();
/* 1385 */     this.debugTerrainFrustumPosition.field_181059_a = x;
/* 1386 */     this.debugTerrainFrustumPosition.field_181060_b = y;
/* 1387 */     this.debugTerrainFrustumPosition.field_181061_c = z;
/* 1388 */     this.debugTerrainMatrix[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
/* 1389 */     this.debugTerrainMatrix[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
/* 1390 */     this.debugTerrainMatrix[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
/* 1391 */     this.debugTerrainMatrix[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
/* 1392 */     this.debugTerrainMatrix[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
/* 1393 */     this.debugTerrainMatrix[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
/* 1394 */     this.debugTerrainMatrix[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1395 */     this.debugTerrainMatrix[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/* 1397 */     for (int i = 0; i < 8; i++) {
/*      */       
/* 1399 */       Matrix4f.transform((Matrix4f)matrix4f2, this.debugTerrainMatrix[i], this.debugTerrainMatrix[i]);
/* 1400 */       (this.debugTerrainMatrix[i]).x /= (this.debugTerrainMatrix[i]).w;
/* 1401 */       (this.debugTerrainMatrix[i]).y /= (this.debugTerrainMatrix[i]).w;
/* 1402 */       (this.debugTerrainMatrix[i]).z /= (this.debugTerrainMatrix[i]).w;
/* 1403 */       (this.debugTerrainMatrix[i]).w = 1.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vector3f getViewVector(Entity entityIn, double partialTicks) {
/* 1409 */     float f = (float)(entityIn.prevRotationPitch + (entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks);
/* 1410 */     float f1 = (float)(entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks);
/*      */     
/* 1412 */     if ((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 2)
/*      */     {
/* 1414 */       f += 180.0F;
/*      */     }
/*      */     
/* 1417 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/* 1418 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/* 1419 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/* 1420 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/* 1421 */     return new Vector3f(f3 * f4, f5, f2 * f4);
/*      */   }
/*      */ 
/*      */   
/*      */   public int renderBlockLayer(EnumWorldBlockLayer blockLayerIn, double partialTicks, int pass, Entity entityIn) {
/* 1426 */     RenderHelper.disableStandardItemLighting();
/*      */     
/* 1428 */     if (blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT && !Shaders.isShadowPass) {
/*      */       
/* 1430 */       this.mc.mcProfiler.startSection("translucent_sort");
/* 1431 */       double d0 = entityIn.posX - this.prevRenderSortX;
/* 1432 */       double d1 = entityIn.posY - this.prevRenderSortY;
/* 1433 */       double d2 = entityIn.posZ - this.prevRenderSortZ;
/*      */       
/* 1435 */       if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0D) {
/*      */         
/* 1437 */         this.prevRenderSortX = entityIn.posX;
/* 1438 */         this.prevRenderSortY = entityIn.posY;
/* 1439 */         this.prevRenderSortZ = entityIn.posZ;
/* 1440 */         int k = 0;
/* 1441 */         this.chunksToResortTransparency.clear();
/*      */         
/* 1443 */         for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
/*      */           
/* 1445 */           if (renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk.isLayerStarted(blockLayerIn) && k++ < 15)
/*      */           {
/* 1447 */             this.chunksToResortTransparency.add(renderglobal$containerlocalrenderinformation.renderChunk);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1452 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1455 */     this.mc.mcProfiler.startSection("filterempty");
/* 1456 */     int l = 0;
/* 1457 */     boolean flag = (blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT);
/* 1458 */     int i1 = flag ? (this.renderInfos.size() - 1) : 0;
/* 1459 */     int i = flag ? -1 : this.renderInfos.size();
/* 1460 */     int j1 = flag ? -1 : 1;
/*      */     int j;
/* 1462 */     for (j = i1; j != i; j += j1) {
/*      */       
/* 1464 */       RenderChunk renderchunk = ((ContainerLocalRenderInformation)this.renderInfos.get(j)).renderChunk;
/*      */       
/* 1466 */       if (!renderchunk.getCompiledChunk().isLayerEmpty(blockLayerIn)) {
/*      */         
/* 1468 */         l++;
/* 1469 */         this.renderContainer.addRenderChunk(renderchunk, blockLayerIn);
/*      */       } 
/*      */     } 
/*      */     
/* 1473 */     if (l == 0) {
/*      */       
/* 1475 */       this.mc.mcProfiler.endSection();
/* 1476 */       return l;
/*      */     } 
/*      */ 
/*      */     
/* 1480 */     if (Config.isFogOff() && this.mc.entityRenderer.fogStandard)
/*      */     {
/* 1482 */       GlStateManager.disableFog();
/*      */     }
/*      */     
/* 1485 */     this.mc.mcProfiler.endStartSection("render_" + blockLayerIn);
/* 1486 */     renderBlockLayer(blockLayerIn);
/* 1487 */     this.mc.mcProfiler.endSection();
/* 1488 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderBlockLayer(EnumWorldBlockLayer blockLayerIn) {
/* 1495 */     this.mc.entityRenderer.enableLightmap();
/*      */     
/* 1497 */     if (OpenGlHelper.useVbo()) {
/*      */       
/* 1499 */       GL11.glEnableClientState(32884);
/* 1500 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1501 */       GL11.glEnableClientState(32888);
/* 1502 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 1503 */       GL11.glEnableClientState(32888);
/* 1504 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1505 */       GL11.glEnableClientState(32886);
/*      */     } 
/*      */     
/* 1508 */     if (Config.isShaders())
/*      */     {
/* 1510 */       ShadersRender.preRenderChunkLayer(blockLayerIn);
/*      */     }
/*      */     
/* 1513 */     this.renderContainer.renderChunkLayer(blockLayerIn);
/*      */     
/* 1515 */     if (Config.isShaders())
/*      */     {
/* 1517 */       ShadersRender.postRenderChunkLayer(blockLayerIn);
/*      */     }
/*      */     
/* 1520 */     if (OpenGlHelper.useVbo())
/*      */     {
/* 1522 */       for (VertexFormatElement vertexformatelement : DefaultVertexFormats.BLOCK.getElements()) {
/*      */         
/* 1524 */         VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
/* 1525 */         int i = vertexformatelement.getIndex();
/*      */         
/* 1527 */         switch (vertexformatelement$enumusage) {
/*      */           
/*      */           case POSITION:
/* 1530 */             GL11.glDisableClientState(32884);
/*      */ 
/*      */           
/*      */           case UV:
/* 1534 */             OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i);
/* 1535 */             GL11.glDisableClientState(32888);
/* 1536 */             OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */ 
/*      */           
/*      */           case COLOR:
/* 1540 */             GL11.glDisableClientState(32886);
/* 1541 */             GlStateManager.resetColor();
/*      */         } 
/*      */       
/*      */       } 
/*      */     }
/* 1546 */     this.mc.entityRenderer.disableLightmap();
/*      */   }
/*      */ 
/*      */   
/*      */   private void cleanupDamagedBlocks(Iterator<DestroyBlockProgress> iteratorIn) {
/* 1551 */     while (iteratorIn.hasNext()) {
/*      */       
/* 1553 */       DestroyBlockProgress destroyblockprogress = iteratorIn.next();
/* 1554 */       int i = destroyblockprogress.getCreationCloudUpdateTick();
/*      */       
/* 1556 */       if (this.cloudTickCounter - i > 400)
/*      */       {
/* 1558 */         iteratorIn.remove();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateClouds() {
/* 1565 */     if (Config.isShaders() && Keyboard.isKeyDown(61) && Keyboard.isKeyDown(19)) {
/*      */       
/* 1567 */       Shaders.uninit();
/* 1568 */       Shaders.loadShaderPack();
/*      */     } 
/*      */     
/* 1571 */     this.cloudTickCounter++;
/*      */     
/* 1573 */     if (this.cloudTickCounter % 20 == 0)
/*      */     {
/* 1575 */       cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderSkyEnd() {
/* 1581 */     if (Config.isSkyEnabled()) {
/*      */       
/* 1583 */       GlStateManager.disableFog();
/* 1584 */       GlStateManager.disableAlpha();
/* 1585 */       GlStateManager.enableBlend();
/* 1586 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1587 */       RenderHelper.disableStandardItemLighting();
/* 1588 */       GlStateManager.depthMask(false);
/* 1589 */       this.renderEngine.bindTexture(locationEndSkyPng);
/* 1590 */       Tessellator tessellator = Tessellator.getInstance();
/* 1591 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */       
/* 1593 */       for (int i = 0; i < 6; i++) {
/*      */         
/* 1595 */         GlStateManager.pushMatrix();
/*      */         
/* 1597 */         if (i == 1)
/*      */         {
/* 1599 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1602 */         if (i == 2)
/*      */         {
/* 1604 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1607 */         if (i == 3)
/*      */         {
/* 1609 */           GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1612 */         if (i == 4)
/*      */         {
/* 1614 */           GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/*      */         }
/*      */         
/* 1617 */         if (i == 5)
/*      */         {
/* 1619 */           GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/*      */         }
/*      */         
/* 1622 */         worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 1623 */         int j = 40;
/* 1624 */         int k = 40;
/* 1625 */         int l = 40;
/*      */         
/* 1627 */         if (Config.isCustomColors()) {
/*      */           
/* 1629 */           Vec3 vec3 = new Vec3(j / 255.0D, k / 255.0D, l / 255.0D);
/* 1630 */           vec3 = CustomColors.getWorldSkyColor(vec3, (World)this.theWorld, this.mc.getRenderViewEntity(), 0.0F);
/* 1631 */           j = (int)(vec3.xCoord * 255.0D);
/* 1632 */           k = (int)(vec3.yCoord * 255.0D);
/* 1633 */           l = (int)(vec3.zCoord * 255.0D);
/*      */         } 
/*      */         
/* 1636 */         worldrenderer.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).func_181669_b(j, k, l, 255).endVertex();
/* 1637 */         worldrenderer.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).func_181669_b(j, k, l, 255).endVertex();
/* 1638 */         worldrenderer.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).func_181669_b(j, k, l, 255).endVertex();
/* 1639 */         worldrenderer.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).func_181669_b(j, k, l, 255).endVertex();
/* 1640 */         tessellator.draw();
/* 1641 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/* 1644 */       GlStateManager.depthMask(true);
/* 1645 */       GlStateManager.enableTexture2D();
/* 1646 */       GlStateManager.enableAlpha();
/* 1647 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderSky(float partialTicks, int pass) {
/* 1653 */     if (Reflector.ForgeWorldProvider_getSkyRenderer.exists()) {
/*      */       
/* 1655 */       WorldProvider worldprovider = this.mc.theWorld.provider;
/* 1656 */       Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0]);
/*      */       
/* 1658 */       if (object != null) {
/*      */         
/* 1660 */         Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.theWorld, this.mc });
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1665 */     if (this.mc.theWorld.provider.getDimensionId() == 1) {
/*      */       
/* 1667 */       renderSkyEnd();
/*      */     }
/* 1669 */     else if (this.mc.theWorld.provider.isSurfaceWorld()) {
/*      */       
/* 1671 */       GlStateManager.disableTexture2D();
/* 1672 */       boolean flag = Config.isShaders();
/*      */       
/* 1674 */       if (flag)
/*      */       {
/* 1676 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 1679 */       Vec3 vec3 = this.theWorld.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
/* 1680 */       vec3 = CustomColors.getSkyColor(vec3, (IBlockAccess)this.mc.theWorld, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 1682 */       if (flag)
/*      */       {
/* 1684 */         Shaders.setSkyColor(vec3);
/*      */       }
/*      */       
/* 1687 */       float f = (float)vec3.xCoord;
/* 1688 */       float f1 = (float)vec3.yCoord;
/* 1689 */       float f2 = (float)vec3.zCoord;
/*      */       
/* 1691 */       if (pass != 2) {
/*      */         
/* 1693 */         float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/* 1694 */         float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/* 1695 */         float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/* 1696 */         f = f3;
/* 1697 */         f1 = f4;
/* 1698 */         f2 = f5;
/*      */       } 
/*      */       
/* 1701 */       GlStateManager.color(f, f1, f2);
/* 1702 */       Tessellator tessellator = Tessellator.getInstance();
/* 1703 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1704 */       GlStateManager.depthMask(false);
/* 1705 */       GlStateManager.enableFog();
/*      */       
/* 1707 */       if (flag)
/*      */       {
/* 1709 */         Shaders.enableFog();
/*      */       }
/*      */       
/* 1712 */       GlStateManager.color(f, f1, f2);
/*      */       
/* 1714 */       if (flag)
/*      */       {
/* 1716 */         Shaders.preSkyList();
/*      */       }
/*      */       
/* 1719 */       if (Config.isSkyEnabled())
/*      */       {
/* 1721 */         if (this.vboEnabled) {
/*      */           
/* 1723 */           this.skyVBO.bindBuffer();
/* 1724 */           GL11.glEnableClientState(32884);
/* 1725 */           GL11.glVertexPointer(3, 5126, 12, 0L);
/* 1726 */           this.skyVBO.drawArrays(7);
/* 1727 */           this.skyVBO.unbindBuffer();
/* 1728 */           GL11.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1732 */           GlStateManager.callList(this.glSkyList);
/*      */         } 
/*      */       }
/*      */       
/* 1736 */       GlStateManager.disableFog();
/*      */       
/* 1738 */       if (flag)
/*      */       {
/* 1740 */         Shaders.disableFog();
/*      */       }
/*      */       
/* 1743 */       GlStateManager.disableAlpha();
/* 1744 */       GlStateManager.enableBlend();
/* 1745 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1746 */       RenderHelper.disableStandardItemLighting();
/* 1747 */       float[] afloat = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(partialTicks), partialTicks);
/*      */       
/* 1749 */       if (afloat != null && Config.isSunMoonEnabled()) {
/*      */         
/* 1751 */         GlStateManager.disableTexture2D();
/*      */         
/* 1753 */         if (flag)
/*      */         {
/* 1755 */           Shaders.disableTexture2D();
/*      */         }
/*      */         
/* 1758 */         GlStateManager.shadeModel(7425);
/* 1759 */         GlStateManager.pushMatrix();
/* 1760 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 1761 */         GlStateManager.rotate((MathHelper.sin(this.theWorld.getCelestialAngleRadians(partialTicks)) < 0.0F) ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
/* 1762 */         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 1763 */         float f6 = afloat[0];
/* 1764 */         float f7 = afloat[1];
/* 1765 */         float f8 = afloat[2];
/*      */         
/* 1767 */         if (pass != 2) {
/*      */           
/* 1769 */           float f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
/* 1770 */           float f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
/* 1771 */           float f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
/* 1772 */           f6 = f9;
/* 1773 */           f7 = f10;
/* 1774 */           f8 = f11;
/*      */         } 
/*      */         
/* 1777 */         worldrenderer.begin(6, DefaultVertexFormats.field_181706_f);
/* 1778 */         worldrenderer.pos(0.0D, 100.0D, 0.0D).func_181666_a(f6, f7, f8, afloat[3]).endVertex();
/* 1779 */         int j = 16;
/*      */         
/* 1781 */         for (int l = 0; l <= 16; l++) {
/*      */           
/* 1783 */           float f18 = l * 3.1415927F * 2.0F / 16.0F;
/* 1784 */           float f12 = MathHelper.sin(f18);
/* 1785 */           float f13 = MathHelper.cos(f18);
/* 1786 */           worldrenderer.pos((f12 * 120.0F), (f13 * 120.0F), (-f13 * 40.0F * afloat[3])).func_181666_a(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
/*      */         } 
/*      */         
/* 1789 */         tessellator.draw();
/* 1790 */         GlStateManager.popMatrix();
/* 1791 */         GlStateManager.shadeModel(7424);
/*      */       } 
/*      */       
/* 1794 */       GlStateManager.enableTexture2D();
/*      */       
/* 1796 */       if (flag)
/*      */       {
/* 1798 */         Shaders.enableTexture2D();
/*      */       }
/*      */       
/* 1801 */       GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 1802 */       GlStateManager.pushMatrix();
/* 1803 */       float f15 = 1.0F - this.theWorld.getRainStrength(partialTicks);
/* 1804 */       GlStateManager.color(1.0F, 1.0F, 1.0F, f15);
/* 1805 */       GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/* 1806 */       CustomSky.renderSky((World)this.theWorld, this.renderEngine, partialTicks);
/*      */       
/* 1808 */       if (flag)
/*      */       {
/* 1810 */         Shaders.preCelestialRotate();
/*      */       }
/*      */       
/* 1813 */       GlStateManager.rotate(this.theWorld.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
/*      */       
/* 1815 */       if (flag)
/*      */       {
/* 1817 */         Shaders.postCelestialRotate();
/*      */       }
/*      */       
/* 1820 */       float f16 = 30.0F;
/*      */       
/* 1822 */       if (Config.isSunTexture()) {
/*      */         
/* 1824 */         this.renderEngine.bindTexture(locationSunPng);
/* 1825 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1826 */         worldrenderer.pos(-f16, 100.0D, -f16).tex(0.0D, 0.0D).endVertex();
/* 1827 */         worldrenderer.pos(f16, 100.0D, -f16).tex(1.0D, 0.0D).endVertex();
/* 1828 */         worldrenderer.pos(f16, 100.0D, f16).tex(1.0D, 1.0D).endVertex();
/* 1829 */         worldrenderer.pos(-f16, 100.0D, f16).tex(0.0D, 1.0D).endVertex();
/* 1830 */         tessellator.draw();
/*      */       } 
/*      */       
/* 1833 */       f16 = 20.0F;
/*      */       
/* 1835 */       if (Config.isMoonTexture()) {
/*      */         
/* 1837 */         this.renderEngine.bindTexture(locationMoonPhasesPng);
/* 1838 */         int i = this.theWorld.getMoonPhase();
/* 1839 */         int k = i % 4;
/* 1840 */         int i1 = i / 4 % 2;
/* 1841 */         float f19 = (k + 0) / 4.0F;
/* 1842 */         float f21 = (i1 + 0) / 2.0F;
/* 1843 */         float f23 = (k + 1) / 4.0F;
/* 1844 */         float f14 = (i1 + 1) / 2.0F;
/* 1845 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1846 */         worldrenderer.pos(-f16, -100.0D, f16).tex(f23, f14).endVertex();
/* 1847 */         worldrenderer.pos(f16, -100.0D, f16).tex(f19, f14).endVertex();
/* 1848 */         worldrenderer.pos(f16, -100.0D, -f16).tex(f19, f21).endVertex();
/* 1849 */         worldrenderer.pos(-f16, -100.0D, -f16).tex(f23, f21).endVertex();
/* 1850 */         tessellator.draw();
/*      */       } 
/*      */       
/* 1853 */       GlStateManager.disableTexture2D();
/*      */       
/* 1855 */       if (flag)
/*      */       {
/* 1857 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 1860 */       float f17 = this.theWorld.getStarBrightness(partialTicks) * f15;
/*      */       
/* 1862 */       if (f17 > 0.0F && Config.isStarsEnabled() && !CustomSky.hasSkyLayers((World)this.theWorld)) {
/*      */         
/* 1864 */         GlStateManager.color(f17, f17, f17, f17);
/*      */         
/* 1866 */         if (this.vboEnabled) {
/*      */           
/* 1868 */           this.starVBO.bindBuffer();
/* 1869 */           GL11.glEnableClientState(32884);
/* 1870 */           GL11.glVertexPointer(3, 5126, 12, 0L);
/* 1871 */           this.starVBO.drawArrays(7);
/* 1872 */           this.starVBO.unbindBuffer();
/* 1873 */           GL11.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1877 */           GlStateManager.callList(this.starGLCallList);
/*      */         } 
/*      */       } 
/*      */       
/* 1881 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1882 */       GlStateManager.disableBlend();
/* 1883 */       GlStateManager.enableAlpha();
/* 1884 */       GlStateManager.enableFog();
/*      */       
/* 1886 */       if (flag)
/*      */       {
/* 1888 */         Shaders.enableFog();
/*      */       }
/*      */       
/* 1891 */       GlStateManager.popMatrix();
/* 1892 */       GlStateManager.disableTexture2D();
/*      */       
/* 1894 */       if (flag)
/*      */       {
/* 1896 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 1899 */       GlStateManager.color(0.0F, 0.0F, 0.0F);
/* 1900 */       double d0 = (this.mc.thePlayer.getPositionEyes(partialTicks)).yCoord - this.theWorld.getHorizon();
/*      */       
/* 1902 */       if (d0 < 0.0D) {
/*      */         
/* 1904 */         GlStateManager.pushMatrix();
/* 1905 */         GlStateManager.translate(0.0F, 12.0F, 0.0F);
/*      */         
/* 1907 */         if (this.vboEnabled) {
/*      */           
/* 1909 */           this.sky2VBO.bindBuffer();
/* 1910 */           GL11.glEnableClientState(32884);
/* 1911 */           GL11.glVertexPointer(3, 5126, 12, 0L);
/* 1912 */           this.sky2VBO.drawArrays(7);
/* 1913 */           this.sky2VBO.unbindBuffer();
/* 1914 */           GL11.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1918 */           GlStateManager.callList(this.glSkyList2);
/*      */         } 
/*      */         
/* 1921 */         GlStateManager.popMatrix();
/* 1922 */         float f20 = 1.0F;
/* 1923 */         float f22 = -((float)(d0 + 65.0D));
/* 1924 */         float f24 = -1.0F;
/* 1925 */         worldrenderer.begin(7, DefaultVertexFormats.field_181706_f);
/* 1926 */         worldrenderer.pos(-1.0D, f22, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1927 */         worldrenderer.pos(1.0D, f22, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1928 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1929 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1930 */         worldrenderer.pos(-1.0D, -1.0D, -1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1931 */         worldrenderer.pos(1.0D, -1.0D, -1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1932 */         worldrenderer.pos(1.0D, f22, -1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1933 */         worldrenderer.pos(-1.0D, f22, -1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1934 */         worldrenderer.pos(1.0D, -1.0D, -1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1935 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1936 */         worldrenderer.pos(1.0D, f22, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1937 */         worldrenderer.pos(1.0D, f22, -1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1938 */         worldrenderer.pos(-1.0D, f22, -1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1939 */         worldrenderer.pos(-1.0D, f22, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1940 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1941 */         worldrenderer.pos(-1.0D, -1.0D, -1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1942 */         worldrenderer.pos(-1.0D, -1.0D, -1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1943 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1944 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1945 */         worldrenderer.pos(1.0D, -1.0D, -1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 1946 */         tessellator.draw();
/*      */       } 
/*      */       
/* 1949 */       if (this.theWorld.provider.isSkyColored()) {
/*      */         
/* 1951 */         GlStateManager.color(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
/*      */       }
/*      */       else {
/*      */         
/* 1955 */         GlStateManager.color(f, f1, f2);
/*      */       } 
/*      */       
/* 1958 */       if (this.mc.gameSettings.renderDistanceChunks <= 4)
/*      */       {
/* 1960 */         GlStateManager.color(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
/*      */       }
/*      */       
/* 1963 */       GlStateManager.pushMatrix();
/* 1964 */       GlStateManager.translate(0.0F, -((float)(d0 - 16.0D)), 0.0F);
/*      */       
/* 1966 */       if (Config.isSkyEnabled())
/*      */       {
/* 1968 */         if (this.vboEnabled) {
/*      */           
/* 1970 */           this.sky2VBO.bindBuffer();
/* 1971 */           GlStateManager.glEnableClientState(32884);
/* 1972 */           GlStateManager.glVertexPointer(3, 5126, 12, 0);
/* 1973 */           this.sky2VBO.drawArrays(7);
/* 1974 */           this.sky2VBO.unbindBuffer();
/* 1975 */           GlStateManager.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1979 */           GlStateManager.callList(this.glSkyList2);
/*      */         } 
/*      */       }
/*      */       
/* 1983 */       GlStateManager.popMatrix();
/* 1984 */       GlStateManager.enableTexture2D();
/*      */       
/* 1986 */       if (flag)
/*      */       {
/* 1988 */         Shaders.enableTexture2D();
/*      */       }
/*      */       
/* 1991 */       GlStateManager.depthMask(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderClouds(float partialTicks, int pass) {
/* 1997 */     if (!Config.isCloudsOff()) {
/*      */       
/* 1999 */       if (Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
/*      */         
/* 2001 */         WorldProvider worldprovider = this.mc.theWorld.provider;
/* 2002 */         Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0]);
/*      */         
/* 2004 */         if (object != null) {
/*      */           
/* 2006 */           Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.theWorld, this.mc });
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/* 2011 */       if (this.mc.theWorld.provider.isSurfaceWorld()) {
/*      */         
/* 2013 */         if (Config.isShaders())
/*      */         {
/* 2015 */           Shaders.beginClouds();
/*      */         }
/*      */         
/* 2018 */         if (Config.isCloudsFancy()) {
/*      */           
/* 2020 */           renderCloudsFancy(partialTicks, pass);
/*      */         }
/*      */         else {
/*      */           
/* 2024 */           float f9 = partialTicks;
/* 2025 */           partialTicks = 0.0F;
/* 2026 */           GlStateManager.disableCull();
/* 2027 */           float f10 = (float)((this.mc.getRenderViewEntity()).lastTickPosY + ((this.mc.getRenderViewEntity()).posY - (this.mc.getRenderViewEntity()).lastTickPosY) * partialTicks);
/* 2028 */           int i = 32;
/* 2029 */           int j = 8;
/* 2030 */           Tessellator tessellator = Tessellator.getInstance();
/* 2031 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2032 */           this.renderEngine.bindTexture(locationCloudsPng);
/* 2033 */           GlStateManager.enableBlend();
/* 2034 */           GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2035 */           Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
/* 2036 */           float f = (float)vec3.xCoord;
/* 2037 */           float f1 = (float)vec3.yCoord;
/* 2038 */           float f2 = (float)vec3.zCoord;
/* 2039 */           this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, f9, vec3);
/*      */           
/* 2041 */           if (this.cloudRenderer.shouldUpdateGlList()) {
/*      */             
/* 2043 */             this.cloudRenderer.startUpdateGlList();
/*      */             
/* 2045 */             if (pass != 2) {
/*      */               
/* 2047 */               float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/* 2048 */               float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/* 2049 */               float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/* 2050 */               f = f3;
/* 2051 */               f1 = f4;
/* 2052 */               f2 = f5;
/*      */             } 
/*      */             
/* 2055 */             float f11 = 4.8828125E-4F;
/* 2056 */             double d2 = (this.cloudTickCounter + partialTicks);
/* 2057 */             double d0 = (this.mc.getRenderViewEntity()).prevPosX + ((this.mc.getRenderViewEntity()).posX - (this.mc.getRenderViewEntity()).prevPosX) * partialTicks + d2 * 0.029999999329447746D;
/* 2058 */             double d1 = (this.mc.getRenderViewEntity()).prevPosZ + ((this.mc.getRenderViewEntity()).posZ - (this.mc.getRenderViewEntity()).prevPosZ) * partialTicks;
/* 2059 */             int k = MathHelper.floor_double(d0 / 2048.0D);
/* 2060 */             int l = MathHelper.floor_double(d1 / 2048.0D);
/* 2061 */             d0 -= (k * 2048);
/* 2062 */             d1 -= (l * 2048);
/* 2063 */             float f6 = this.theWorld.provider.getCloudHeight() - f10 + 0.33F;
/* 2064 */             f6 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
/* 2065 */             float f7 = (float)(d0 * 4.8828125E-4D);
/* 2066 */             float f8 = (float)(d1 * 4.8828125E-4D);
/* 2067 */             worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/*      */             
/* 2069 */             for (int i1 = -256; i1 < 256; i1 += 32) {
/*      */               
/* 2071 */               for (int j1 = -256; j1 < 256; j1 += 32) {
/*      */                 
/* 2073 */                 worldrenderer.pos((i1 + 0), f6, (j1 + 32)).tex(((i1 + 0) * 4.8828125E-4F + f7), ((j1 + 32) * 4.8828125E-4F + f8)).func_181666_a(f, f1, f2, 0.8F).endVertex();
/* 2074 */                 worldrenderer.pos((i1 + 32), f6, (j1 + 32)).tex(((i1 + 32) * 4.8828125E-4F + f7), ((j1 + 32) * 4.8828125E-4F + f8)).func_181666_a(f, f1, f2, 0.8F).endVertex();
/* 2075 */                 worldrenderer.pos((i1 + 32), f6, (j1 + 0)).tex(((i1 + 32) * 4.8828125E-4F + f7), ((j1 + 0) * 4.8828125E-4F + f8)).func_181666_a(f, f1, f2, 0.8F).endVertex();
/* 2076 */                 worldrenderer.pos((i1 + 0), f6, (j1 + 0)).tex(((i1 + 0) * 4.8828125E-4F + f7), ((j1 + 0) * 4.8828125E-4F + f8)).func_181666_a(f, f1, f2, 0.8F).endVertex();
/*      */               } 
/*      */             } 
/*      */             
/* 2080 */             tessellator.draw();
/* 2081 */             this.cloudRenderer.endUpdateGlList();
/*      */           } 
/*      */           
/* 2084 */           this.cloudRenderer.renderGlList();
/* 2085 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2086 */           GlStateManager.disableBlend();
/* 2087 */           GlStateManager.enableCull();
/*      */         } 
/*      */         
/* 2090 */         if (Config.isShaders())
/*      */         {
/* 2092 */           Shaders.endClouds();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCloudFog(double x, double y, double z, float partialTicks) {
/* 2103 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderCloudsFancy(float partialTicks, int pass) {
/* 2108 */     partialTicks = 0.0F;
/* 2109 */     GlStateManager.disableCull();
/* 2110 */     float f = (float)((this.mc.getRenderViewEntity()).lastTickPosY + ((this.mc.getRenderViewEntity()).posY - (this.mc.getRenderViewEntity()).lastTickPosY) * partialTicks);
/* 2111 */     Tessellator tessellator = Tessellator.getInstance();
/* 2112 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2113 */     float f1 = 12.0F;
/* 2114 */     float f2 = 4.0F;
/* 2115 */     double d0 = (this.cloudTickCounter + partialTicks);
/* 2116 */     double d1 = ((this.mc.getRenderViewEntity()).prevPosX + ((this.mc.getRenderViewEntity()).posX - (this.mc.getRenderViewEntity()).prevPosX) * partialTicks + d0 * 0.029999999329447746D) / 12.0D;
/* 2117 */     double d2 = ((this.mc.getRenderViewEntity()).prevPosZ + ((this.mc.getRenderViewEntity()).posZ - (this.mc.getRenderViewEntity()).prevPosZ) * partialTicks) / 12.0D + 0.33000001311302185D;
/* 2118 */     float f3 = this.theWorld.provider.getCloudHeight() - f + 0.33F;
/* 2119 */     f3 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
/* 2120 */     int i = MathHelper.floor_double(d1 / 2048.0D);
/* 2121 */     int j = MathHelper.floor_double(d2 / 2048.0D);
/* 2122 */     d1 -= (i * 2048);
/* 2123 */     d2 -= (j * 2048);
/* 2124 */     this.renderEngine.bindTexture(locationCloudsPng);
/* 2125 */     GlStateManager.enableBlend();
/* 2126 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2127 */     Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
/* 2128 */     float f4 = (float)vec3.xCoord;
/* 2129 */     float f5 = (float)vec3.yCoord;
/* 2130 */     float f6 = (float)vec3.zCoord;
/* 2131 */     this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, partialTicks, vec3);
/*      */     
/* 2133 */     if (pass != 2) {
/*      */       
/* 2135 */       float f7 = (f4 * 30.0F + f5 * 59.0F + f6 * 11.0F) / 100.0F;
/* 2136 */       float f8 = (f4 * 30.0F + f5 * 70.0F) / 100.0F;
/* 2137 */       float f9 = (f4 * 30.0F + f6 * 70.0F) / 100.0F;
/* 2138 */       f4 = f7;
/* 2139 */       f5 = f8;
/* 2140 */       f6 = f9;
/*      */     } 
/*      */     
/* 2143 */     float f26 = f4 * 0.9F;
/* 2144 */     float f27 = f5 * 0.9F;
/* 2145 */     float f28 = f6 * 0.9F;
/* 2146 */     float f10 = f4 * 0.7F;
/* 2147 */     float f11 = f5 * 0.7F;
/* 2148 */     float f12 = f6 * 0.7F;
/* 2149 */     float f13 = f4 * 0.8F;
/* 2150 */     float f14 = f5 * 0.8F;
/* 2151 */     float f15 = f6 * 0.8F;
/* 2152 */     float f16 = 0.00390625F;
/* 2153 */     float f17 = MathHelper.floor_double(d1) * 0.00390625F;
/* 2154 */     float f18 = MathHelper.floor_double(d2) * 0.00390625F;
/* 2155 */     float f19 = (float)(d1 - MathHelper.floor_double(d1));
/* 2156 */     float f20 = (float)(d2 - MathHelper.floor_double(d2));
/* 2157 */     int k = 8;
/* 2158 */     int l = 4;
/* 2159 */     float f21 = 9.765625E-4F;
/* 2160 */     GlStateManager.scale(12.0F, 1.0F, 12.0F);
/*      */     
/* 2162 */     for (int i1 = 0; i1 < 2; i1++) {
/*      */       
/* 2164 */       if (i1 == 0) {
/*      */         
/* 2166 */         GlStateManager.colorMask(false, false, false, false);
/*      */       }
/*      */       else {
/*      */         
/* 2170 */         switch (pass) {
/*      */           
/*      */           case 0:
/* 2173 */             GlStateManager.colorMask(false, true, true, true);
/*      */             break;
/*      */           
/*      */           case 1:
/* 2177 */             GlStateManager.colorMask(true, false, false, true);
/*      */             break;
/*      */           
/*      */           case 2:
/* 2181 */             GlStateManager.colorMask(true, true, true, true);
/*      */             break;
/*      */         } 
/*      */       } 
/* 2185 */       this.cloudRenderer.renderGlList();
/*      */     } 
/*      */     
/* 2188 */     if (this.cloudRenderer.shouldUpdateGlList()) {
/*      */       
/* 2190 */       this.cloudRenderer.startUpdateGlList();
/*      */       
/* 2192 */       for (int l1 = -3; l1 <= 4; l1++) {
/*      */         
/* 2194 */         for (int j1 = -3; j1 <= 4; j1++) {
/*      */           
/* 2196 */           worldrenderer.begin(7, DefaultVertexFormats.field_181712_l);
/* 2197 */           float f22 = (l1 * 8);
/* 2198 */           float f23 = (j1 * 8);
/* 2199 */           float f24 = f22 - f19;
/* 2200 */           float f25 = f23 - f20;
/*      */           
/* 2202 */           if (f3 > -5.0F) {
/*      */             
/* 2204 */             worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).func_181666_a(f10, f11, f12, 0.8F).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 2205 */             worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).func_181666_a(f10, f11, f12, 0.8F).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 2206 */             worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).func_181666_a(f10, f11, f12, 0.8F).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/* 2207 */             worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).func_181666_a(f10, f11, f12, 0.8F).func_181663_c(0.0F, -1.0F, 0.0F).endVertex();
/*      */           } 
/*      */           
/* 2210 */           if (f3 <= 5.0F) {
/*      */             
/* 2212 */             worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 8.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).func_181666_a(f4, f5, f6, 0.8F).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 2213 */             worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 8.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).func_181666_a(f4, f5, f6, 0.8F).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 2214 */             worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).func_181666_a(f4, f5, f6, 0.8F).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/* 2215 */             worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).func_181666_a(f4, f5, f6, 0.8F).func_181663_c(0.0F, 1.0F, 0.0F).endVertex();
/*      */           } 
/*      */           
/* 2218 */           if (l1 > -1)
/*      */           {
/* 2220 */             for (int k1 = 0; k1 < 8; k1++) {
/*      */               
/* 2222 */               worldrenderer.pos((f24 + k1 + 0.0F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).func_181666_a(f26, f27, f28, 0.8F).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 2223 */               worldrenderer.pos((f24 + k1 + 0.0F), (f3 + 4.0F), (f25 + 8.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).func_181666_a(f26, f27, f28, 0.8F).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 2224 */               worldrenderer.pos((f24 + k1 + 0.0F), (f3 + 4.0F), (f25 + 0.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).func_181666_a(f26, f27, f28, 0.8F).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/* 2225 */               worldrenderer.pos((f24 + k1 + 0.0F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).func_181666_a(f26, f27, f28, 0.8F).func_181663_c(-1.0F, 0.0F, 0.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2229 */           if (l1 <= 1)
/*      */           {
/* 2231 */             for (int i2 = 0; i2 < 8; i2++) {
/*      */               
/* 2233 */               worldrenderer.pos((f24 + i2 + 1.0F - 9.765625E-4F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + i2 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).func_181666_a(f26, f27, f28, 0.8F).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 2234 */               worldrenderer.pos((f24 + i2 + 1.0F - 9.765625E-4F), (f3 + 4.0F), (f25 + 8.0F)).tex(((f22 + i2 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).func_181666_a(f26, f27, f28, 0.8F).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 2235 */               worldrenderer.pos((f24 + i2 + 1.0F - 9.765625E-4F), (f3 + 4.0F), (f25 + 0.0F)).tex(((f22 + i2 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).func_181666_a(f26, f27, f28, 0.8F).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/* 2236 */               worldrenderer.pos((f24 + i2 + 1.0F - 9.765625E-4F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + i2 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).func_181666_a(f26, f27, f28, 0.8F).func_181663_c(1.0F, 0.0F, 0.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2240 */           if (j1 > -1)
/*      */           {
/* 2242 */             for (int j2 = 0; j2 < 8; j2++) {
/*      */               
/* 2244 */               worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F), (f25 + j2 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + j2 + 0.5F) * 0.00390625F + f18)).func_181666_a(f13, f14, f15, 0.8F).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/* 2245 */               worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F), (f25 + j2 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + j2 + 0.5F) * 0.00390625F + f18)).func_181666_a(f13, f14, f15, 0.8F).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/* 2246 */               worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + j2 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + j2 + 0.5F) * 0.00390625F + f18)).func_181666_a(f13, f14, f15, 0.8F).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/* 2247 */               worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + j2 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + j2 + 0.5F) * 0.00390625F + f18)).func_181666_a(f13, f14, f15, 0.8F).func_181663_c(0.0F, 0.0F, -1.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2251 */           if (j1 <= 1)
/*      */           {
/* 2253 */             for (int k2 = 0; k2 < 8; k2++) {
/*      */               
/* 2255 */               worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F), (f25 + k2 + 1.0F - 9.765625E-4F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + k2 + 0.5F) * 0.00390625F + f18)).func_181666_a(f13, f14, f15, 0.8F).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/* 2256 */               worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F), (f25 + k2 + 1.0F - 9.765625E-4F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + k2 + 0.5F) * 0.00390625F + f18)).func_181666_a(f13, f14, f15, 0.8F).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/* 2257 */               worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + k2 + 1.0F - 9.765625E-4F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + k2 + 0.5F) * 0.00390625F + f18)).func_181666_a(f13, f14, f15, 0.8F).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/* 2258 */               worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + k2 + 1.0F - 9.765625E-4F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + k2 + 0.5F) * 0.00390625F + f18)).func_181666_a(f13, f14, f15, 0.8F).func_181663_c(0.0F, 0.0F, 1.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2262 */           tessellator.draw();
/*      */         } 
/*      */       } 
/*      */       
/* 2266 */       this.cloudRenderer.endUpdateGlList();
/*      */     } 
/*      */     
/* 2269 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2270 */     GlStateManager.disableBlend();
/* 2271 */     GlStateManager.enableCull();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateChunks(long finishTimeNano) {
/* 2276 */     finishTimeNano = (long)(finishTimeNano + 1.0E8D);
/* 2277 */     this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads(finishTimeNano);
/*      */     
/* 2279 */     if (this.chunksToUpdateForced.size() > 0) {
/*      */       
/* 2281 */       Iterator<RenderChunk> iterator = this.chunksToUpdateForced.iterator();
/*      */       
/* 2283 */       while (iterator.hasNext()) {
/*      */         
/* 2285 */         RenderChunk renderchunk = iterator.next();
/*      */         
/* 2287 */         if (!this.renderDispatcher.updateChunkLater(renderchunk)) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/* 2292 */         renderchunk.setNeedsUpdate(false);
/* 2293 */         iterator.remove();
/* 2294 */         this.chunksToUpdate.remove(renderchunk);
/* 2295 */         this.chunksToResortTransparency.remove(renderchunk);
/*      */       } 
/*      */     } 
/*      */     
/* 2299 */     if (this.chunksToResortTransparency.size() > 0) {
/*      */       
/* 2301 */       Iterator<RenderChunk> iterator2 = this.chunksToResortTransparency.iterator();
/*      */       
/* 2303 */       if (iterator2.hasNext()) {
/*      */         
/* 2305 */         RenderChunk renderchunk2 = iterator2.next();
/*      */         
/* 2307 */         if (this.renderDispatcher.updateTransparencyLater(renderchunk2))
/*      */         {
/* 2309 */           iterator2.remove();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2314 */     double d1 = 0.0D;
/* 2315 */     int i = Config.getUpdatesPerFrame();
/*      */     
/* 2317 */     if (!this.chunksToUpdate.isEmpty()) {
/*      */       
/* 2319 */       Iterator<RenderChunk> iterator1 = this.chunksToUpdate.iterator();
/*      */       
/* 2321 */       while (iterator1.hasNext()) {
/*      */         boolean flag1;
/* 2323 */         RenderChunk renderchunk1 = iterator1.next();
/* 2324 */         boolean flag = renderchunk1.isChunkRegionEmpty();
/*      */ 
/*      */         
/* 2327 */         if (flag) {
/*      */           
/* 2329 */           flag1 = this.renderDispatcher.updateChunkNow(renderchunk1);
/*      */         }
/*      */         else {
/*      */           
/* 2333 */           flag1 = this.renderDispatcher.updateChunkLater(renderchunk1);
/*      */         } 
/*      */         
/* 2336 */         if (!flag1) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/* 2341 */         renderchunk1.setNeedsUpdate(false);
/* 2342 */         iterator1.remove();
/*      */         
/* 2344 */         if (!flag) {
/*      */           
/* 2346 */           double d0 = 2.0D * RenderChunkUtils.getRelativeBufferSize(renderchunk1);
/* 2347 */           d1 += d0;
/*      */           
/* 2349 */           if (d1 > i) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderWorldBorder(Entity p_180449_1_, float partialTicks) {
/* 2360 */     Tessellator tessellator = Tessellator.getInstance();
/* 2361 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2362 */     WorldBorder worldborder = this.theWorld.getWorldBorder();
/* 2363 */     double d0 = (this.mc.gameSettings.renderDistanceChunks * 16);
/*      */     
/* 2365 */     if (p_180449_1_.posX >= worldborder.maxX() - d0 || p_180449_1_.posX <= worldborder.minX() + d0 || p_180449_1_.posZ >= worldborder.maxZ() - d0 || p_180449_1_.posZ <= worldborder.minZ() + d0) {
/*      */       
/* 2367 */       double d1 = 1.0D - worldborder.getClosestDistance(p_180449_1_) / d0;
/* 2368 */       d1 = Math.pow(d1, 4.0D);
/* 2369 */       double d2 = p_180449_1_.lastTickPosX + (p_180449_1_.posX - p_180449_1_.lastTickPosX) * partialTicks;
/* 2370 */       double d3 = p_180449_1_.lastTickPosY + (p_180449_1_.posY - p_180449_1_.lastTickPosY) * partialTicks;
/* 2371 */       double d4 = p_180449_1_.lastTickPosZ + (p_180449_1_.posZ - p_180449_1_.lastTickPosZ) * partialTicks;
/* 2372 */       GlStateManager.enableBlend();
/* 2373 */       GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 2374 */       this.renderEngine.bindTexture(locationForcefieldPng);
/* 2375 */       GlStateManager.depthMask(false);
/* 2376 */       GlStateManager.pushMatrix();
/* 2377 */       int i = worldborder.getStatus().getID();
/* 2378 */       float f = (i >> 16 & 0xFF) / 255.0F;
/* 2379 */       float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 2380 */       float f2 = (i & 0xFF) / 255.0F;
/* 2381 */       GlStateManager.color(f, f1, f2, (float)d1);
/* 2382 */       GlStateManager.doPolygonOffset(-3.0F, -3.0F);
/* 2383 */       GlStateManager.enablePolygonOffset();
/* 2384 */       GlStateManager.alphaFunc(516, 0.1F);
/* 2385 */       GlStateManager.enableAlpha();
/* 2386 */       GlStateManager.disableCull();
/* 2387 */       float f3 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F;
/* 2388 */       float f4 = 0.0F;
/* 2389 */       float f5 = 0.0F;
/* 2390 */       float f6 = 128.0F;
/* 2391 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 2392 */       worldrenderer.setTranslation(-d2, -d3, -d4);
/* 2393 */       double d5 = Math.max(MathHelper.floor_double(d4 - d0), worldborder.minZ());
/* 2394 */       double d6 = Math.min(MathHelper.ceiling_double_int(d4 + d0), worldborder.maxZ());
/*      */       
/* 2396 */       if (d2 > worldborder.maxX() - d0) {
/*      */         
/* 2398 */         float f7 = 0.0F;
/*      */         
/* 2400 */         for (double d7 = d5; d7 < d6; f7 += 0.5F) {
/*      */           
/* 2402 */           double d8 = Math.min(1.0D, d6 - d7);
/* 2403 */           float f8 = (float)d8 * 0.5F;
/* 2404 */           worldrenderer.pos(worldborder.maxX(), 256.0D, d7).tex((f3 + f7), (f3 + 0.0F)).endVertex();
/* 2405 */           worldrenderer.pos(worldborder.maxX(), 256.0D, d7 + d8).tex((f3 + f8 + f7), (f3 + 0.0F)).endVertex();
/* 2406 */           worldrenderer.pos(worldborder.maxX(), 0.0D, d7 + d8).tex((f3 + f8 + f7), (f3 + 128.0F)).endVertex();
/* 2407 */           worldrenderer.pos(worldborder.maxX(), 0.0D, d7).tex((f3 + f7), (f3 + 128.0F)).endVertex();
/* 2408 */           d7++;
/*      */         } 
/*      */       } 
/*      */       
/* 2412 */       if (d2 < worldborder.minX() + d0) {
/*      */         
/* 2414 */         float f9 = 0.0F;
/*      */         
/* 2416 */         for (double d9 = d5; d9 < d6; f9 += 0.5F) {
/*      */           
/* 2418 */           double d12 = Math.min(1.0D, d6 - d9);
/* 2419 */           float f12 = (float)d12 * 0.5F;
/* 2420 */           worldrenderer.pos(worldborder.minX(), 256.0D, d9).tex((f3 + f9), (f3 + 0.0F)).endVertex();
/* 2421 */           worldrenderer.pos(worldborder.minX(), 256.0D, d9 + d12).tex((f3 + f12 + f9), (f3 + 0.0F)).endVertex();
/* 2422 */           worldrenderer.pos(worldborder.minX(), 0.0D, d9 + d12).tex((f3 + f12 + f9), (f3 + 128.0F)).endVertex();
/* 2423 */           worldrenderer.pos(worldborder.minX(), 0.0D, d9).tex((f3 + f9), (f3 + 128.0F)).endVertex();
/* 2424 */           d9++;
/*      */         } 
/*      */       } 
/*      */       
/* 2428 */       d5 = Math.max(MathHelper.floor_double(d2 - d0), worldborder.minX());
/* 2429 */       d6 = Math.min(MathHelper.ceiling_double_int(d2 + d0), worldborder.maxX());
/*      */       
/* 2431 */       if (d4 > worldborder.maxZ() - d0) {
/*      */         
/* 2433 */         float f10 = 0.0F;
/*      */         
/* 2435 */         for (double d10 = d5; d10 < d6; f10 += 0.5F) {
/*      */           
/* 2437 */           double d13 = Math.min(1.0D, d6 - d10);
/* 2438 */           float f13 = (float)d13 * 0.5F;
/* 2439 */           worldrenderer.pos(d10, 256.0D, worldborder.maxZ()).tex((f3 + f10), (f3 + 0.0F)).endVertex();
/* 2440 */           worldrenderer.pos(d10 + d13, 256.0D, worldborder.maxZ()).tex((f3 + f13 + f10), (f3 + 0.0F)).endVertex();
/* 2441 */           worldrenderer.pos(d10 + d13, 0.0D, worldborder.maxZ()).tex((f3 + f13 + f10), (f3 + 128.0F)).endVertex();
/* 2442 */           worldrenderer.pos(d10, 0.0D, worldborder.maxZ()).tex((f3 + f10), (f3 + 128.0F)).endVertex();
/* 2443 */           d10++;
/*      */         } 
/*      */       } 
/*      */       
/* 2447 */       if (d4 < worldborder.minZ() + d0) {
/*      */         
/* 2449 */         float f11 = 0.0F;
/*      */         
/* 2451 */         for (double d11 = d5; d11 < d6; f11 += 0.5F) {
/*      */           
/* 2453 */           double d14 = Math.min(1.0D, d6 - d11);
/* 2454 */           float f14 = (float)d14 * 0.5F;
/* 2455 */           worldrenderer.pos(d11, 256.0D, worldborder.minZ()).tex((f3 + f11), (f3 + 0.0F)).endVertex();
/* 2456 */           worldrenderer.pos(d11 + d14, 256.0D, worldborder.minZ()).tex((f3 + f14 + f11), (f3 + 0.0F)).endVertex();
/* 2457 */           worldrenderer.pos(d11 + d14, 0.0D, worldborder.minZ()).tex((f3 + f14 + f11), (f3 + 128.0F)).endVertex();
/* 2458 */           worldrenderer.pos(d11, 0.0D, worldborder.minZ()).tex((f3 + f11), (f3 + 128.0F)).endVertex();
/* 2459 */           d11++;
/*      */         } 
/*      */       } 
/*      */       
/* 2463 */       tessellator.draw();
/* 2464 */       worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 2465 */       GlStateManager.enableCull();
/* 2466 */       GlStateManager.disableAlpha();
/* 2467 */       GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 2468 */       GlStateManager.disablePolygonOffset();
/* 2469 */       GlStateManager.enableAlpha();
/* 2470 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2471 */       GlStateManager.disableBlend();
/* 2472 */       GlStateManager.popMatrix();
/* 2473 */       GlStateManager.depthMask(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void preRenderDamagedBlocks() {
/* 2479 */     GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
/* 2480 */     GlStateManager.enableBlend();
/* 2481 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
/* 2482 */     GlStateManager.doPolygonOffset(-1.0F, -10.0F);
/* 2483 */     GlStateManager.enablePolygonOffset();
/* 2484 */     GlStateManager.alphaFunc(516, 0.1F);
/* 2485 */     GlStateManager.enableAlpha();
/* 2486 */     GlStateManager.pushMatrix();
/*      */     
/* 2488 */     if (Config.isShaders())
/*      */     {
/* 2490 */       ShadersRender.beginBlockDamage();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void postRenderDamagedBlocks() {
/* 2496 */     GlStateManager.disableAlpha();
/* 2497 */     GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 2498 */     GlStateManager.disablePolygonOffset();
/* 2499 */     GlStateManager.enableAlpha();
/* 2500 */     GlStateManager.depthMask(true);
/* 2501 */     GlStateManager.popMatrix();
/*      */     
/* 2503 */     if (Config.isShaders())
/*      */     {
/* 2505 */       ShadersRender.endBlockDamage();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawBlockDamageTexture(Tessellator tessellatorIn, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks) {
/* 2511 */     double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 2512 */     double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 2513 */     double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/*      */     
/* 2515 */     if (!this.damagedBlocks.isEmpty()) {
/*      */       
/* 2517 */       this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/* 2518 */       preRenderDamagedBlocks();
/* 2519 */       worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
/* 2520 */       worldRendererIn.setTranslation(-d0, -d1, -d2);
/* 2521 */       worldRendererIn.markDirty();
/* 2522 */       Iterator<DestroyBlockProgress> iterator = this.damagedBlocks.values().iterator();
/*      */       
/* 2524 */       while (iterator.hasNext()) {
/*      */         boolean flag;
/* 2526 */         DestroyBlockProgress destroyblockprogress = iterator.next();
/* 2527 */         BlockPos blockpos = destroyblockprogress.getPosition();
/* 2528 */         double d3 = blockpos.getX() - d0;
/* 2529 */         double d4 = blockpos.getY() - d1;
/* 2530 */         double d5 = blockpos.getZ() - d2;
/* 2531 */         Block block = this.theWorld.getBlockState(blockpos).getBlock();
/*      */ 
/*      */         
/* 2534 */         if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
/*      */           
/* 2536 */           boolean flag1 = (block instanceof net.minecraft.block.BlockChest || block instanceof net.minecraft.block.BlockEnderChest || block instanceof net.minecraft.block.BlockSign || block instanceof net.minecraft.block.BlockSkull);
/*      */           
/* 2538 */           if (!flag1) {
/*      */             
/* 2540 */             TileEntity tileentity = this.theWorld.getTileEntity(blockpos);
/*      */             
/* 2542 */             if (tileentity != null)
/*      */             {
/* 2544 */               flag1 = Reflector.callBoolean(tileentity, Reflector.ForgeTileEntity_canRenderBreaking, new Object[0]);
/*      */             }
/*      */           } 
/*      */           
/* 2548 */           flag = !flag1;
/*      */         }
/*      */         else {
/*      */           
/* 2552 */           flag = (!(block instanceof net.minecraft.block.BlockChest) && !(block instanceof net.minecraft.block.BlockEnderChest) && !(block instanceof net.minecraft.block.BlockSign) && !(block instanceof net.minecraft.block.BlockSkull));
/*      */         } 
/*      */         
/* 2555 */         if (flag) {
/*      */           
/* 2557 */           if (d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D) {
/*      */             
/* 2559 */             iterator.remove();
/*      */             
/*      */             continue;
/*      */           } 
/* 2563 */           IBlockState iblockstate = this.theWorld.getBlockState(blockpos);
/*      */           
/* 2565 */           if (iblockstate.getBlock().getMaterial() != Material.air) {
/*      */             
/* 2567 */             int i = destroyblockprogress.getPartialBlockDamage();
/* 2568 */             TextureAtlasSprite textureatlassprite = this.destroyBlockIcons[i];
/* 2569 */             BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
/* 2570 */             blockrendererdispatcher.renderBlockDamage(iblockstate, blockpos, textureatlassprite, (IBlockAccess)this.theWorld);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2576 */       tessellatorIn.draw();
/* 2577 */       worldRendererIn.setTranslation(0.0D, 0.0D, 0.0D);
/* 2578 */       postRenderDamagedBlocks();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int p_72731_3_, float partialTicks) {
/* 2587 */     if (p_72731_3_ == 0 && movingObjectPositionIn.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*      */       
/* 2589 */       GlStateManager.enableBlend();
/* 2590 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2591 */       GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
/* 2592 */       GL11.glLineWidth(2.0F);
/* 2593 */       GlStateManager.disableTexture2D();
/*      */       
/* 2595 */       if (Config.isShaders())
/*      */       {
/* 2597 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 2600 */       GlStateManager.depthMask(false);
/* 2601 */       float f = 0.002F;
/* 2602 */       BlockPos blockpos = movingObjectPositionIn.getBlockPos();
/* 2603 */       Block block = this.theWorld.getBlockState(blockpos).getBlock();
/*      */       
/* 2605 */       if (block.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(blockpos)) {
/*      */         
/* 2607 */         block.setBlockBoundsBasedOnState((IBlockAccess)this.theWorld, blockpos);
/* 2608 */         double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
/* 2609 */         double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
/* 2610 */         double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
/* 2611 */         AxisAlignedBB axisalignedbb = block.getSelectedBoundingBox((World)this.theWorld, blockpos);
/* 2612 */         Block.EnumOffsetType block$enumoffsettype = block.getOffsetType();
/*      */         
/* 2614 */         if (block$enumoffsettype != Block.EnumOffsetType.NONE)
/*      */         {
/* 2616 */           axisalignedbb = BlockModelUtils.getOffsetBoundingBox(axisalignedbb, block$enumoffsettype, blockpos);
/*      */         }
/*      */         
/* 2619 */         func_181561_a(axisalignedbb.expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-d0, -d1, -d2));
/*      */       } 
/*      */       
/* 2622 */       GlStateManager.depthMask(true);
/* 2623 */       GlStateManager.enableTexture2D();
/*      */       
/* 2625 */       if (Config.isShaders())
/*      */       {
/* 2627 */         Shaders.enableTexture2D();
/*      */       }
/*      */       
/* 2630 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void func_181561_a(AxisAlignedBB p_181561_0_) {
/* 2636 */     Tessellator tessellator = Tessellator.getInstance();
/* 2637 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2638 */     worldrenderer.begin(3, DefaultVertexFormats.field_181705_e);
/* 2639 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 2640 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 2641 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 2642 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 2643 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 2644 */     tessellator.draw();
/* 2645 */     worldrenderer.begin(3, DefaultVertexFormats.field_181705_e);
/* 2646 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 2647 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 2648 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 2649 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 2650 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 2651 */     tessellator.draw();
/* 2652 */     worldrenderer.begin(1, DefaultVertexFormats.field_181705_e);
/* 2653 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 2654 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 2655 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 2656 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 2657 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 2658 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 2659 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 2660 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 2661 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void func_181563_a(AxisAlignedBB p_181563_0_, int p_181563_1_, int p_181563_2_, int p_181563_3_, int p_181563_4_) {
/* 2666 */     Tessellator tessellator = Tessellator.getInstance();
/* 2667 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2668 */     worldrenderer.begin(3, DefaultVertexFormats.field_181706_f);
/* 2669 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2670 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2671 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2672 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2673 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2674 */     tessellator.draw();
/* 2675 */     worldrenderer.begin(3, DefaultVertexFormats.field_181706_f);
/* 2676 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2677 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2678 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2679 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2680 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2681 */     tessellator.draw();
/* 2682 */     worldrenderer.begin(1, DefaultVertexFormats.field_181706_f);
/* 2683 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2684 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2685 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2686 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.minZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2687 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2688 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2689 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2690 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.maxZ).func_181669_b(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2691 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void markBlocksForUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/* 2699 */     this.viewFrustum.markBlocksForUpdate(x1, y1, z1, x2, y2, z2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void markBlockForUpdate(BlockPos pos) {
/* 2704 */     int i = pos.getX();
/* 2705 */     int j = pos.getY();
/* 2706 */     int k = pos.getZ();
/* 2707 */     markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyLightSet(BlockPos pos) {
/* 2712 */     int i = pos.getX();
/* 2713 */     int j = pos.getY();
/* 2714 */     int k = pos.getZ();
/* 2715 */     markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/* 2724 */     markBlocksForUpdate(x1 - 1, y1 - 1, z1 - 1, x2 + 1, y2 + 1, z2 + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playRecord(String recordName, BlockPos blockPosIn) {
/* 2729 */     ISound isound = this.mapSoundPositions.get(blockPosIn);
/*      */     
/* 2731 */     if (isound != null) {
/*      */       
/* 2733 */       this.mc.getSoundHandler().stopSound(isound);
/* 2734 */       this.mapSoundPositions.remove(blockPosIn);
/*      */     } 
/*      */     
/* 2737 */     if (recordName != null) {
/*      */       
/* 2739 */       ItemRecord itemrecord = ItemRecord.getRecord(recordName);
/*      */       
/* 2741 */       if (itemrecord != null)
/*      */       {
/* 2743 */         this.mc.ingameGUI.setRecordPlayingMessage(itemrecord.getRecordNameLocal());
/*      */       }
/*      */       
/* 2746 */       PositionedSoundRecord positionedsoundrecord = PositionedSoundRecord.create(new ResourceLocation(recordName), blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ());
/* 2747 */       this.mapSoundPositions.put(blockPosIn, positionedsoundrecord);
/* 2748 */       this.mc.getSoundHandler().playSound((ISound)positionedsoundrecord);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume, float pitch) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnParticle(int particleID, boolean ignoreRange, final double xCoord, final double yCoord, final double zCoord, double xOffset, double yOffset, double zOffset, int... p_180442_15_) {
/*      */     try {
/* 2770 */       spawnEntityFX(particleID, ignoreRange, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_180442_15_);
/*      */     }
/* 2772 */     catch (Throwable throwable) {
/*      */       
/* 2774 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while adding particle");
/* 2775 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being added");
/* 2776 */       crashreportcategory.addCrashSection("ID", Integer.valueOf(particleID));
/*      */       
/* 2778 */       if (p_180442_15_ != null)
/*      */       {
/* 2780 */         crashreportcategory.addCrashSection("Parameters", p_180442_15_);
/*      */       }
/*      */       
/* 2783 */       crashreportcategory.addCrashSectionCallable("Position", new Callable<String>()
/*      */           {
/*      */             public String call() throws Exception
/*      */             {
/* 2787 */               return CrashReportCategory.getCoordinateInfo(xCoord, yCoord, zCoord);
/*      */             }
/*      */           });
/* 2790 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void spawnParticle(EnumParticleTypes particleIn, double p_174972_2_, double p_174972_4_, double p_174972_6_, double p_174972_8_, double p_174972_10_, double p_174972_12_, int... p_174972_14_) {
/* 2796 */     spawnParticle(particleIn.getParticleID(), particleIn.getShouldIgnoreRange(), p_174972_2_, p_174972_4_, p_174972_6_, p_174972_8_, p_174972_10_, p_174972_12_, p_174972_14_);
/*      */   }
/*      */ 
/*      */   
/*      */   private EntityFX spawnEntityFX(int p_174974_1_, boolean ignoreRange, double p_174974_3_, double p_174974_5_, double p_174974_7_, double p_174974_9_, double p_174974_11_, double p_174974_13_, int... p_174974_15_) {
/* 2801 */     if (this.mc != null && this.mc.getRenderViewEntity() != null && this.mc.effectRenderer != null) {
/*      */       
/* 2803 */       int i = this.mc.gameSettings.particleSetting;
/*      */       
/* 2805 */       if (i == 1 && this.theWorld.rand.nextInt(3) == 0)
/*      */       {
/* 2807 */         i = 2;
/*      */       }
/*      */       
/* 2810 */       double d0 = (this.mc.getRenderViewEntity()).posX - p_174974_3_;
/* 2811 */       double d1 = (this.mc.getRenderViewEntity()).posY - p_174974_5_;
/* 2812 */       double d2 = (this.mc.getRenderViewEntity()).posZ - p_174974_7_;
/*      */       
/* 2814 */       if (p_174974_1_ == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2816 */         return null;
/*      */       }
/* 2818 */       if (p_174974_1_ == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2820 */         return null;
/*      */       }
/* 2822 */       if (p_174974_1_ == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2824 */         return null;
/*      */       }
/* 2826 */       if (p_174974_1_ == EnumParticleTypes.SUSPENDED.getParticleID() && !Config.isWaterParticles())
/*      */       {
/* 2828 */         return null;
/*      */       }
/* 2830 */       if (p_174974_1_ == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID() && !Config.isVoidParticles())
/*      */       {
/* 2832 */         return null;
/*      */       }
/* 2834 */       if (p_174974_1_ == EnumParticleTypes.SMOKE_NORMAL.getParticleID() && !Config.isAnimatedSmoke())
/*      */       {
/* 2836 */         return null;
/*      */       }
/* 2838 */       if (p_174974_1_ == EnumParticleTypes.SMOKE_LARGE.getParticleID() && !Config.isAnimatedSmoke())
/*      */       {
/* 2840 */         return null;
/*      */       }
/* 2842 */       if (p_174974_1_ == EnumParticleTypes.SPELL_MOB.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2844 */         return null;
/*      */       }
/* 2846 */       if (p_174974_1_ == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2848 */         return null;
/*      */       }
/* 2850 */       if (p_174974_1_ == EnumParticleTypes.SPELL.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2852 */         return null;
/*      */       }
/* 2854 */       if (p_174974_1_ == EnumParticleTypes.SPELL_INSTANT.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2856 */         return null;
/*      */       }
/* 2858 */       if (p_174974_1_ == EnumParticleTypes.SPELL_WITCH.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2860 */         return null;
/*      */       }
/* 2862 */       if (p_174974_1_ == EnumParticleTypes.PORTAL.getParticleID() && !Config.isPortalParticles())
/*      */       {
/* 2864 */         return null;
/*      */       }
/* 2866 */       if (p_174974_1_ == EnumParticleTypes.FLAME.getParticleID() && !Config.isAnimatedFlame())
/*      */       {
/* 2868 */         return null;
/*      */       }
/* 2870 */       if (p_174974_1_ == EnumParticleTypes.REDSTONE.getParticleID() && !Config.isAnimatedRedstone())
/*      */       {
/* 2872 */         return null;
/*      */       }
/* 2874 */       if (p_174974_1_ == EnumParticleTypes.DRIP_WATER.getParticleID() && !Config.isDrippingWaterLava())
/*      */       {
/* 2876 */         return null;
/*      */       }
/* 2878 */       if (p_174974_1_ == EnumParticleTypes.DRIP_LAVA.getParticleID() && !Config.isDrippingWaterLava())
/*      */       {
/* 2880 */         return null;
/*      */       }
/* 2882 */       if (p_174974_1_ == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() && !Config.isFireworkParticles())
/*      */       {
/* 2884 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 2888 */       if (!ignoreRange) {
/*      */         
/* 2890 */         double d3 = 1024.0D;
/*      */         
/* 2892 */         if (p_174974_1_ == EnumParticleTypes.CRIT.getParticleID())
/*      */         {
/* 2894 */           d3 = 38416.0D;
/*      */         }
/*      */         
/* 2897 */         if (d0 * d0 + d1 * d1 + d2 * d2 > d3)
/*      */         {
/* 2899 */           return null;
/*      */         }
/*      */         
/* 2902 */         if (i > 1)
/*      */         {
/* 2904 */           return null;
/*      */         }
/*      */       } 
/*      */       
/* 2908 */       EntityFX entityfx = this.mc.effectRenderer.spawnEffectParticle(p_174974_1_, p_174974_3_, p_174974_5_, p_174974_7_, p_174974_9_, p_174974_11_, p_174974_13_, p_174974_15_);
/*      */       
/* 2910 */       if (p_174974_1_ == EnumParticleTypes.WATER_BUBBLE.getParticleID())
/*      */       {
/* 2912 */         CustomColors.updateWaterFX(entityfx, (IBlockAccess)this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_, this.renderEnv);
/*      */       }
/*      */       
/* 2915 */       if (p_174974_1_ == EnumParticleTypes.WATER_SPLASH.getParticleID())
/*      */       {
/* 2917 */         CustomColors.updateWaterFX(entityfx, (IBlockAccess)this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_, this.renderEnv);
/*      */       }
/*      */       
/* 2920 */       if (p_174974_1_ == EnumParticleTypes.WATER_DROP.getParticleID())
/*      */       {
/* 2922 */         CustomColors.updateWaterFX(entityfx, (IBlockAccess)this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_, this.renderEnv);
/*      */       }
/*      */       
/* 2925 */       if (p_174974_1_ == EnumParticleTypes.TOWN_AURA.getParticleID())
/*      */       {
/* 2927 */         CustomColors.updateMyceliumFX(entityfx);
/*      */       }
/*      */       
/* 2930 */       if (p_174974_1_ == EnumParticleTypes.PORTAL.getParticleID())
/*      */       {
/* 2932 */         CustomColors.updatePortalFX(entityfx);
/*      */       }
/*      */       
/* 2935 */       if (p_174974_1_ == EnumParticleTypes.REDSTONE.getParticleID())
/*      */       {
/* 2937 */         CustomColors.updateReddustFX(entityfx, (IBlockAccess)this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_);
/*      */       }
/*      */       
/* 2940 */       return entityfx;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2945 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityAdded(Entity entityIn) {
/* 2955 */     RandomEntities.entityLoaded(entityIn, (World)this.theWorld);
/*      */     
/* 2957 */     if (Config.isDynamicLights())
/*      */     {
/* 2959 */       DynamicLights.entityAdded(entityIn, this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityRemoved(Entity entityIn) {
/* 2969 */     RandomEntities.entityUnloaded(entityIn, (World)this.theWorld);
/*      */     
/* 2971 */     if (Config.isDynamicLights())
/*      */     {
/* 2973 */       DynamicLights.entityRemoved(entityIn, this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteAllDisplayLists() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {
/* 2986 */     switch (p_180440_1_) {
/*      */       
/*      */       case 1013:
/*      */       case 1018:
/* 2990 */         if (this.mc.getRenderViewEntity() != null) {
/*      */           
/* 2992 */           double d0 = p_180440_2_.getX() - (this.mc.getRenderViewEntity()).posX;
/* 2993 */           double d1 = p_180440_2_.getY() - (this.mc.getRenderViewEntity()).posY;
/* 2994 */           double d2 = p_180440_2_.getZ() - (this.mc.getRenderViewEntity()).posZ;
/* 2995 */           double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 2996 */           double d4 = (this.mc.getRenderViewEntity()).posX;
/* 2997 */           double d5 = (this.mc.getRenderViewEntity()).posY;
/* 2998 */           double d6 = (this.mc.getRenderViewEntity()).posZ;
/*      */           
/* 3000 */           if (d3 > 0.0D) {
/*      */             
/* 3002 */             d4 += d0 / d3 * 2.0D;
/* 3003 */             d5 += d1 / d3 * 2.0D;
/* 3004 */             d6 += d2 / d3 * 2.0D;
/*      */           } 
/*      */           
/* 3007 */           if (p_180440_1_ == 1013) {
/*      */             
/* 3009 */             this.theWorld.playSound(d4, d5, d6, "mob.wither.spawn", 1.0F, 1.0F, false);
/*      */             
/*      */             break;
/*      */           } 
/* 3013 */           this.theWorld.playSound(d4, d5, d6, "mob.enderdragon.end", 5.0F, 1.0F, false);
/*      */         }  break;
/*      */     }  } public void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int p_180439_4_) { int i, j; double d0, d1, d2; int i1; Block block; double d3, d4, d5; int k, j1;
/*      */     float f, f1, f2;
/*      */     EnumParticleTypes enumparticletypes;
/*      */     int k1;
/*      */     double d6, d8, d10;
/*      */     int l1;
/*      */     double d22;
/*      */     int l;
/* 3023 */     Random random = this.theWorld.rand;
/*      */     
/* 3025 */     switch (sfxType) {
/*      */       
/*      */       case 1000:
/* 3028 */         this.theWorld.playSoundAtPos(blockPosIn, "random.click", 1.0F, 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1001:
/* 3032 */         this.theWorld.playSoundAtPos(blockPosIn, "random.click", 1.0F, 1.2F, false);
/*      */         break;
/*      */       
/*      */       case 1002:
/* 3036 */         this.theWorld.playSoundAtPos(blockPosIn, "random.bow", 1.0F, 1.2F, false);
/*      */         break;
/*      */       
/*      */       case 1003:
/* 3040 */         this.theWorld.playSoundAtPos(blockPosIn, "random.door_open", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1004:
/* 3044 */         this.theWorld.playSoundAtPos(blockPosIn, "random.fizz", 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F, false);
/*      */         break;
/*      */       
/*      */       case 1005:
/* 3048 */         if (Item.getItemById(p_180439_4_) instanceof ItemRecord) {
/*      */           
/* 3050 */           this.theWorld.playRecord(blockPosIn, "records." + ((ItemRecord)Item.getItemById(p_180439_4_)).recordName);
/*      */           
/*      */           break;
/*      */         } 
/* 3054 */         this.theWorld.playRecord(blockPosIn, null);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1006:
/* 3060 */         this.theWorld.playSoundAtPos(blockPosIn, "random.door_close", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1007:
/* 3064 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.charge", 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1008:
/* 3068 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.fireball", 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1009:
/* 3072 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.fireball", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1010:
/* 3076 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.wood", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1011:
/* 3080 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.metal", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1012:
/* 3084 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.woodbreak", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1014:
/* 3088 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.wither.shoot", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1015:
/* 3092 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.bat.takeoff", 0.05F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1016:
/* 3096 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.infect", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1017:
/* 3100 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.unfect", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1020:
/* 3104 */         this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_break", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1021:
/* 3108 */         this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_use", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1022:
/* 3112 */         this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_land", 0.3F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 2000:
/* 3116 */         i = p_180439_4_ % 3 - 1;
/* 3117 */         j = p_180439_4_ / 3 % 3 - 1;
/* 3118 */         d0 = blockPosIn.getX() + i * 0.6D + 0.5D;
/* 3119 */         d1 = blockPosIn.getY() + 0.5D;
/* 3120 */         d2 = blockPosIn.getZ() + j * 0.6D + 0.5D;
/*      */         
/* 3122 */         for (i1 = 0; i1 < 10; i1++) {
/*      */           
/* 3124 */           double d15 = random.nextDouble() * 0.2D + 0.01D;
/* 3125 */           double d16 = d0 + i * 0.01D + (random.nextDouble() - 0.5D) * j * 0.5D;
/* 3126 */           double d17 = d1 + (random.nextDouble() - 0.5D) * 0.5D;
/* 3127 */           double d18 = d2 + j * 0.01D + (random.nextDouble() - 0.5D) * i * 0.5D;
/* 3128 */           double d19 = i * d15 + random.nextGaussian() * 0.01D;
/* 3129 */           double d20 = -0.03D + random.nextGaussian() * 0.01D;
/* 3130 */           double d21 = j * d15 + random.nextGaussian() * 0.01D;
/* 3131 */           spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d16, d17, d18, d19, d20, d21, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2001:
/* 3137 */         block = Block.getBlockById(p_180439_4_ & 0xFFF);
/*      */         
/* 3139 */         if (block.getMaterial() != Material.air)
/*      */         {
/* 3141 */           this.mc.getSoundHandler().playSound((ISound)new PositionedSoundRecord(new ResourceLocation(block.stepSound.getBreakSound()), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getFrequency() * 0.8F, blockPosIn.getX() + 0.5F, blockPosIn.getY() + 0.5F, blockPosIn.getZ() + 0.5F));
/*      */         }
/*      */         
/* 3144 */         this.mc.effectRenderer.addBlockDestroyEffects(blockPosIn, block.getStateFromMeta(p_180439_4_ >> 12 & 0xFF));
/*      */         break;
/*      */       
/*      */       case 2002:
/* 3148 */         d3 = blockPosIn.getX();
/* 3149 */         d4 = blockPosIn.getY();
/* 3150 */         d5 = blockPosIn.getZ();
/*      */         
/* 3152 */         for (k = 0; k < 8; k++) {
/*      */           
/* 3154 */           spawnParticle(EnumParticleTypes.ITEM_CRACK, d3, d4, d5, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D, new int[] { Item.getIdFromItem((Item)Items.potionitem), p_180439_4_ });
/*      */         } 
/*      */         
/* 3157 */         j1 = Items.potionitem.getColorFromDamage(p_180439_4_);
/* 3158 */         f = (j1 >> 16 & 0xFF) / 255.0F;
/* 3159 */         f1 = (j1 >> 8 & 0xFF) / 255.0F;
/* 3160 */         f2 = (j1 >> 0 & 0xFF) / 255.0F;
/* 3161 */         enumparticletypes = EnumParticleTypes.SPELL;
/*      */         
/* 3163 */         if (Items.potionitem.isEffectInstant(p_180439_4_))
/*      */         {
/* 3165 */           enumparticletypes = EnumParticleTypes.SPELL_INSTANT;
/*      */         }
/*      */         
/* 3168 */         for (k1 = 0; k1 < 100; k1++) {
/*      */           
/* 3170 */           double d7 = random.nextDouble() * 4.0D;
/* 3171 */           double d9 = random.nextDouble() * Math.PI * 2.0D;
/* 3172 */           double d11 = Math.cos(d9) * d7;
/* 3173 */           double d23 = 0.01D + random.nextDouble() * 0.5D;
/* 3174 */           double d24 = Math.sin(d9) * d7;
/* 3175 */           EntityFX entityfx = spawnEntityFX(enumparticletypes.getParticleID(), enumparticletypes.getShouldIgnoreRange(), d3 + d11 * 0.1D, d4 + 0.3D, d5 + d24 * 0.1D, d11, d23, d24, new int[0]);
/*      */           
/* 3177 */           if (entityfx != null) {
/*      */             
/* 3179 */             float f3 = 0.75F + random.nextFloat() * 0.25F;
/* 3180 */             entityfx.setRBGColorF(f * f3, f1 * f3, f2 * f3);
/* 3181 */             entityfx.multiplyVelocity((float)d7);
/*      */           } 
/*      */         } 
/*      */         
/* 3185 */         this.theWorld.playSoundAtPos(blockPosIn, "game.potion.smash", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 2003:
/* 3189 */         d6 = blockPosIn.getX() + 0.5D;
/* 3190 */         d8 = blockPosIn.getY();
/* 3191 */         d10 = blockPosIn.getZ() + 0.5D;
/*      */         
/* 3193 */         for (l1 = 0; l1 < 8; l1++) {
/*      */           
/* 3195 */           spawnParticle(EnumParticleTypes.ITEM_CRACK, d6, d8, d10, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D, new int[] { Item.getIdFromItem(Items.ender_eye) });
/*      */         } 
/*      */         
/* 3198 */         for (d22 = 0.0D; d22 < 6.283185307179586D; d22 += 0.15707963267948966D) {
/*      */           
/* 3200 */           spawnParticle(EnumParticleTypes.PORTAL, d6 + Math.cos(d22) * 5.0D, d8 - 0.4D, d10 + Math.sin(d22) * 5.0D, Math.cos(d22) * -5.0D, 0.0D, Math.sin(d22) * -5.0D, new int[0]);
/* 3201 */           spawnParticle(EnumParticleTypes.PORTAL, d6 + Math.cos(d22) * 5.0D, d8 - 0.4D, d10 + Math.sin(d22) * 5.0D, Math.cos(d22) * -7.0D, 0.0D, Math.sin(d22) * -7.0D, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2004:
/* 3207 */         for (l = 0; l < 20; l++) {
/*      */           
/* 3209 */           double d12 = blockPosIn.getX() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 3210 */           double d13 = blockPosIn.getY() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 3211 */           double d14 = blockPosIn.getZ() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 3212 */           this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d12, d13, d14, 0.0D, 0.0D, 0.0D, new int[0]);
/* 3213 */           this.theWorld.spawnParticle(EnumParticleTypes.FLAME, d12, d13, d14, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2005:
/* 3219 */         ItemDye.spawnBonemealParticles((World)this.theWorld, blockPosIn, p_180439_4_);
/*      */         break;
/*      */     }  }
/*      */ 
/*      */   
/*      */   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
/* 3225 */     if (progress >= 0 && progress < 10) {
/*      */       
/* 3227 */       DestroyBlockProgress destroyblockprogress = this.damagedBlocks.get(Integer.valueOf(breakerId));
/*      */       
/* 3229 */       if (destroyblockprogress == null || destroyblockprogress.getPosition().getX() != pos.getX() || destroyblockprogress.getPosition().getY() != pos.getY() || destroyblockprogress.getPosition().getZ() != pos.getZ()) {
/*      */         
/* 3231 */         destroyblockprogress = new DestroyBlockProgress(breakerId, pos);
/* 3232 */         this.damagedBlocks.put(Integer.valueOf(breakerId), destroyblockprogress);
/*      */       } 
/*      */       
/* 3235 */       destroyblockprogress.setPartialBlockDamage(progress);
/* 3236 */       destroyblockprogress.setCloudUpdateTick(this.cloudTickCounter);
/*      */     }
/*      */     else {
/*      */       
/* 3240 */       this.damagedBlocks.remove(Integer.valueOf(breakerId));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDisplayListEntitiesDirty() {
/* 3246 */     this.displayListEntitiesDirty = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasNoChunkUpdates() {
/* 3251 */     return (this.chunksToUpdate.isEmpty() && this.renderDispatcher.hasChunkUpdates());
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetClouds() {
/* 3256 */     this.cloudRenderer.reset();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountRenderers() {
/* 3261 */     return this.viewFrustum.renderChunks.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountActiveRenderers() {
/* 3266 */     return this.renderInfos.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountEntitiesRendered() {
/* 3271 */     return this.countEntitiesRendered;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountTileEntitiesRendered() {
/* 3276 */     return this.countTileEntitiesRendered;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountLoadedChunks() {
/* 3281 */     if (this.theWorld == null)
/*      */     {
/* 3283 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 3287 */     IChunkProvider ichunkprovider = this.theWorld.getChunkProvider();
/*      */     
/* 3289 */     if (ichunkprovider == null)
/*      */     {
/* 3291 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 3295 */     if (ichunkprovider != this.worldChunkProvider) {
/*      */       
/* 3297 */       this.worldChunkProvider = ichunkprovider;
/* 3298 */       this.worldChunkProviderMap = (LongHashMap)Reflector.getFieldValue(ichunkprovider, Reflector.ChunkProviderClient_chunkMapping);
/*      */     } 
/*      */     
/* 3301 */     return (this.worldChunkProviderMap == null) ? 0 : this.worldChunkProviderMap.getNumHashElements();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCountChunksToUpdate() {
/* 3308 */     return this.chunksToUpdate.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderChunk getRenderChunk(BlockPos p_getRenderChunk_1_) {
/* 3313 */     return this.viewFrustum.getRenderChunk(p_getRenderChunk_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldClient getWorld() {
/* 3318 */     return this.theWorld;
/*      */   }
/*      */ 
/*      */   
/*      */   private void clearRenderInfos() {
/* 3323 */     if (renderEntitiesCounter > 0) {
/*      */       
/* 3325 */       this.renderInfos = new ArrayList<>(this.renderInfos.size() + 16);
/* 3326 */       this.renderInfosEntities = new ArrayList(this.renderInfosEntities.size() + 16);
/* 3327 */       this.renderInfosTileEntities = new ArrayList(this.renderInfosTileEntities.size() + 16);
/*      */     }
/*      */     else {
/*      */       
/* 3331 */       this.renderInfos.clear();
/* 3332 */       this.renderInfosEntities.clear();
/* 3333 */       this.renderInfosTileEntities.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onPlayerPositionSet() {
/* 3339 */     if (this.firstWorldLoad) {
/*      */       
/* 3341 */       loadRenderers();
/* 3342 */       this.firstWorldLoad = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void pauseChunkUpdates() {
/* 3348 */     if (this.renderDispatcher != null)
/*      */     {
/* 3350 */       this.renderDispatcher.pauseChunkUpdates();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void resumeChunkUpdates() {
/* 3356 */     if (this.renderDispatcher != null)
/*      */     {
/* 3358 */       this.renderDispatcher.resumeChunkUpdates();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181023_a(Collection<TileEntity> p_181023_1_, Collection<TileEntity> p_181023_2_) {
/* 3364 */     synchronized (this.field_181024_n) {
/*      */       
/* 3366 */       this.field_181024_n.removeAll(p_181023_1_);
/* 3367 */       this.field_181024_n.addAll(p_181023_2_);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ContainerLocalRenderInformation
/*      */   {
/*      */     final RenderChunk renderChunk;
/*      */     EnumFacing facing;
/*      */     int setFacing;
/*      */     
/*      */     public ContainerLocalRenderInformation(RenderChunk p_i2_1_, EnumFacing p_i2_2_, int p_i2_3_) {
/* 3379 */       this.renderChunk = p_i2_1_;
/* 3380 */       this.facing = p_i2_2_;
/* 3381 */       this.setFacing = p_i2_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setFacingBit(byte p_setFacingBit_1_, EnumFacing p_setFacingBit_2_) {
/* 3386 */       this.setFacing = this.setFacing | p_setFacingBit_1_ | 1 << p_setFacingBit_2_.ordinal();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isFacingBit(EnumFacing p_isFacingBit_1_) {
/* 3391 */       return ((this.setFacing & 1 << p_isFacingBit_1_.ordinal()) > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     private void initialize(EnumFacing p_initialize_1_, int p_initialize_2_) {
/* 3396 */       this.facing = p_initialize_1_;
/* 3397 */       this.setFacing = p_initialize_2_;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\RenderGlobal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */