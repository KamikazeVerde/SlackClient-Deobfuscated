/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import cc.slack.Slack;
/*      */ import cc.slack.events.impl.render.RenderEvent;
/*      */ import cc.slack.features.modules.impl.ghost.AimAssist;
/*      */ import cc.slack.features.modules.impl.ghost.Reach;
/*      */ import cc.slack.features.modules.impl.other.Tweaks;
/*      */ import cc.slack.utils.render.FreeLookUtil;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import com.google.gson.JsonSyntaxException;
/*      */ import java.io.IOException;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockBed;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.AbstractClientPlayer;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.MapItemRenderer;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.EffectRenderer;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelperImpl;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.shader.ShaderGroup;
/*      */ import net.minecraft.client.shader.ShaderLinkHelper;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.passive.EntityAnimal;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MouseFilter;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.GlErrors;
/*      */ import net.optifine.Lagometer;
/*      */ import net.optifine.RandomEntities;
/*      */ import net.optifine.gui.GuiChatOF;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.reflect.ReflectorForge;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.shaders.ShadersRender;
/*      */ import net.optifine.util.TextureUtils;
/*      */ import net.optifine.util.TimedEvent;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.glu.Project;
/*      */ 
/*      */ public class EntityRenderer
/*      */   implements IResourceManagerReloadListener
/*      */ {
/*  103 */   private static final Logger logger = LogManager.getLogger();
/*  104 */   private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
/*  105 */   private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
/*      */   
/*      */   public static boolean anaglyphEnable;
/*      */   
/*      */   public static int anaglyphField;
/*      */   
/*      */   private Minecraft mc;
/*      */   
/*      */   private final IResourceManager resourceManager;
/*  114 */   private Random random = new Random();
/*      */   
/*      */   private float farPlaneDistance;
/*      */   
/*      */   public ItemRenderer itemRenderer;
/*      */   
/*      */   private final MapItemRenderer theMapItemRenderer;
/*      */   
/*      */   private int rendererUpdateCount;
/*      */   private Entity pointedEntity;
/*  124 */   private MouseFilter mouseFilterXAxis = new MouseFilter();
/*  125 */   private MouseFilter mouseFilterYAxis = new MouseFilter();
/*  126 */   private float thirdPersonDistance = 4.0F;
/*      */ 
/*      */   
/*  129 */   private float thirdPersonDistanceTemp = 4.0F;
/*      */ 
/*      */   
/*      */   private float smoothCamYaw;
/*      */ 
/*      */   
/*      */   private float smoothCamPitch;
/*      */ 
/*      */   
/*      */   private float smoothCamFilterX;
/*      */ 
/*      */   
/*      */   private float smoothCamFilterY;
/*      */ 
/*      */   
/*      */   private float smoothCamPartialTicks;
/*      */   
/*      */   private float fovModifierHand;
/*      */   
/*      */   private float fovModifierHandPrev;
/*      */   
/*      */   private float bossColorModifier;
/*      */   
/*      */   private float bossColorModifierPrev;
/*      */   
/*      */   private boolean cloudFog;
/*      */   
/*      */   private boolean renderHand = true;
/*      */   
/*      */   private boolean drawBlockOutline = true;
/*      */   
/*  160 */   private long prevFrameTime = Minecraft.getSystemTime();
/*      */ 
/*      */   
/*      */   private long renderEndNanoTime;
/*      */ 
/*      */   
/*      */   private final DynamicTexture lightmapTexture;
/*      */ 
/*      */   
/*      */   private final int[] lightmapColors;
/*      */ 
/*      */   
/*      */   private final ResourceLocation locationLightMap;
/*      */ 
/*      */   
/*      */   private boolean lightmapUpdateNeeded;
/*      */ 
/*      */   
/*      */   private float torchFlickerX;
/*      */ 
/*      */   
/*      */   private float torchFlickerDX;
/*      */ 
/*      */   
/*      */   private int rainSoundCounter;
/*      */ 
/*      */   
/*  187 */   private float[] rainXCoords = new float[1024];
/*  188 */   private float[] rainYCoords = new float[1024];
/*      */ 
/*      */   
/*  191 */   private FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
/*      */   
/*      */   public float fogColorRed;
/*      */   
/*      */   public float fogColorGreen;
/*      */   
/*      */   public float fogColorBlue;
/*      */   
/*      */   private float fogColor2;
/*      */   private float fogColor1;
/*  201 */   private int debugViewDirection = 0;
/*      */   private boolean debugView = false;
/*  203 */   private double cameraZoom = 1.0D;
/*      */   private double cameraYaw;
/*      */   private double cameraPitch;
/*      */   private ShaderGroup theShaderGroup;
/*  207 */   private static final ResourceLocation[] shaderResourceLocations = new ResourceLocation[] { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json") };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  233 */   public static final int shaderCount = shaderResourceLocations.length;
/*      */   private int shaderIndex;
/*      */   private boolean useShader;
/*      */   public int frameCount;
/*      */   private boolean initialized = false;
/*  238 */   private World updatedWorld = null;
/*      */   private boolean showDebugInfo = false;
/*      */   public boolean fogStandard = false;
/*  241 */   private float clipDistance = 128.0F;
/*  242 */   private long lastServerTime = 0L;
/*  243 */   private int lastServerTicks = 0;
/*  244 */   private int serverWaitTime = 0;
/*  245 */   private int serverWaitTimeCurrent = 0;
/*  246 */   private float avgServerTimeDiff = 0.0F;
/*  247 */   private float avgServerTickDiff = 0.0F;
/*  248 */   private ShaderGroup[] fxaaShaders = new ShaderGroup[10];
/*      */   
/*      */   private boolean loadVisibleChunks = false;
/*      */   
/*      */   public EntityRenderer(Minecraft mcIn, IResourceManager resourceManagerIn) {
/*  253 */     this.shaderIndex = shaderCount;
/*  254 */     this.useShader = false;
/*  255 */     this.frameCount = 0;
/*  256 */     this.mc = mcIn;
/*  257 */     this.resourceManager = resourceManagerIn;
/*  258 */     this.itemRenderer = mcIn.getItemRenderer();
/*  259 */     this.theMapItemRenderer = new MapItemRenderer(mcIn.getTextureManager());
/*  260 */     this.lightmapTexture = new DynamicTexture(16, 16);
/*  261 */     this.locationLightMap = mcIn.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
/*  262 */     this.lightmapColors = this.lightmapTexture.getTextureData();
/*  263 */     this.theShaderGroup = null;
/*      */     
/*  265 */     for (int i = 0; i < 32; i++) {
/*      */       
/*  267 */       for (int j = 0; j < 32; j++) {
/*      */         
/*  269 */         float f = (j - 16);
/*  270 */         float f1 = (i - 16);
/*  271 */         float f2 = MathHelper.sqrt_float(f * f + f1 * f1);
/*  272 */         this.rainXCoords[i << 5 | j] = -f1 / f2;
/*  273 */         this.rainYCoords[i << 5 | j] = f / f2;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isShaderActive() {
/*  280 */     return (OpenGlHelper.shadersSupported && this.theShaderGroup != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181022_b() {
/*  285 */     if (this.theShaderGroup != null)
/*      */     {
/*  287 */       this.theShaderGroup.deleteShaderGroup();
/*      */     }
/*      */     
/*  290 */     this.theShaderGroup = null;
/*  291 */     this.shaderIndex = shaderCount;
/*      */   }
/*      */ 
/*      */   
/*      */   public void switchUseShader() {
/*  296 */     this.useShader = !this.useShader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadEntityShader(Entity entityIn) {
/*  304 */     if (OpenGlHelper.shadersSupported) {
/*      */       
/*  306 */       if (this.theShaderGroup != null)
/*      */       {
/*  308 */         this.theShaderGroup.deleteShaderGroup();
/*      */       }
/*      */       
/*  311 */       this.theShaderGroup = null;
/*      */       
/*  313 */       if (entityIn instanceof net.minecraft.entity.monster.EntityCreeper) {
/*      */         
/*  315 */         loadShader(new ResourceLocation("shaders/post/creeper.json"));
/*      */       }
/*  317 */       else if (entityIn instanceof net.minecraft.entity.monster.EntitySpider) {
/*      */         
/*  319 */         loadShader(new ResourceLocation("shaders/post/spider.json"));
/*      */       }
/*  321 */       else if (entityIn instanceof net.minecraft.entity.monster.EntityEnderman) {
/*      */         
/*  323 */         loadShader(new ResourceLocation("shaders/post/invert.json"));
/*      */       }
/*  325 */       else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
/*      */         
/*  327 */         Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, new Object[] { entityIn, this });
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void activateNextShader() {
/*  334 */     if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*      */       
/*  336 */       if (this.theShaderGroup != null)
/*      */       {
/*  338 */         this.theShaderGroup.deleteShaderGroup();
/*      */       }
/*      */       
/*  341 */       this.shaderIndex = (this.shaderIndex + 1) % (shaderResourceLocations.length + 1);
/*      */       
/*  343 */       if (this.shaderIndex != shaderCount) {
/*      */         
/*  345 */         loadShader(shaderResourceLocations[this.shaderIndex]);
/*      */       }
/*      */       else {
/*      */         
/*  349 */         this.theShaderGroup = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadShader(ResourceLocation resourceLocationIn) {
/*  356 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*      */       
/*      */       try {
/*      */         
/*  360 */         this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocationIn);
/*  361 */         this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  362 */         this.useShader = true;
/*      */       }
/*  364 */       catch (IOException ioexception) {
/*      */         
/*  366 */         logger.warn("Failed to load shader: " + resourceLocationIn, ioexception);
/*  367 */         this.shaderIndex = shaderCount;
/*  368 */         this.useShader = false;
/*      */       }
/*  370 */       catch (JsonSyntaxException jsonsyntaxexception) {
/*      */         
/*  372 */         logger.warn("Failed to load shader: " + resourceLocationIn, (Throwable)jsonsyntaxexception);
/*  373 */         this.shaderIndex = shaderCount;
/*  374 */         this.useShader = false;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  381 */     if (this.theShaderGroup != null)
/*      */     {
/*  383 */       this.theShaderGroup.deleteShaderGroup();
/*      */     }
/*      */     
/*  386 */     this.theShaderGroup = null;
/*      */     
/*  388 */     if (this.shaderIndex != shaderCount) {
/*      */       
/*  390 */       loadShader(shaderResourceLocations[this.shaderIndex]);
/*      */     }
/*      */     else {
/*      */       
/*  394 */       loadEntityShader(this.mc.getRenderViewEntity());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRenderer() {
/*  403 */     if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null)
/*      */     {
/*  405 */       ShaderLinkHelper.setNewStaticShaderLinkHelper();
/*      */     }
/*      */     
/*  408 */     updateFovModifierHand();
/*  409 */     updateTorchFlicker();
/*  410 */     this.fogColor2 = this.fogColor1;
/*  411 */     this.thirdPersonDistanceTemp = this.thirdPersonDistance;
/*      */     
/*  413 */     if (this.mc.gameSettings.smoothCamera) {
/*      */       
/*  415 */       float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/*  416 */       float f1 = f * f * f * 8.0F;
/*  417 */       this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05F * f1);
/*  418 */       this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05F * f1);
/*  419 */       this.smoothCamPartialTicks = 0.0F;
/*  420 */       this.smoothCamYaw = 0.0F;
/*  421 */       this.smoothCamPitch = 0.0F;
/*      */     }
/*      */     else {
/*      */       
/*  425 */       this.smoothCamFilterX = 0.0F;
/*  426 */       this.smoothCamFilterY = 0.0F;
/*  427 */       this.mouseFilterXAxis.reset();
/*  428 */       this.mouseFilterYAxis.reset();
/*      */     } 
/*      */     
/*  431 */     if (this.mc.getRenderViewEntity() == null)
/*      */     {
/*  433 */       this.mc.setRenderViewEntity((Entity)this.mc.thePlayer);
/*      */     }
/*      */     
/*  436 */     Entity entity = this.mc.getRenderViewEntity();
/*  437 */     double d2 = entity.posX;
/*  438 */     double d0 = entity.posY + entity.getEyeHeight();
/*  439 */     double d1 = entity.posZ;
/*  440 */     float f2 = this.mc.theWorld.getLightBrightness(new BlockPos(d2, d0, d1));
/*  441 */     float f3 = this.mc.gameSettings.renderDistanceChunks / 16.0F;
/*  442 */     f3 = MathHelper.clamp_float(f3, 0.0F, 1.0F);
/*  443 */     float f4 = f2 * (1.0F - f3) + f3;
/*  444 */     this.fogColor1 += (f4 - this.fogColor1) * 0.1F;
/*  445 */     this.rendererUpdateCount++;
/*  446 */     this.itemRenderer.updateEquippedItem();
/*  447 */     addRainParticles();
/*  448 */     this.bossColorModifierPrev = this.bossColorModifier;
/*      */     
/*  450 */     if (BossStatus.hasColorModifier) {
/*      */       
/*  452 */       this.bossColorModifier += 0.05F;
/*      */       
/*  454 */       if (this.bossColorModifier > 1.0F)
/*      */       {
/*  456 */         this.bossColorModifier = 1.0F;
/*      */       }
/*      */       
/*  459 */       BossStatus.hasColorModifier = false;
/*      */     }
/*  461 */     else if (this.bossColorModifier > 0.0F) {
/*      */       
/*  463 */       this.bossColorModifier -= 0.0125F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ShaderGroup getShaderGroup() {
/*  469 */     return this.theShaderGroup;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateShaderGroupSize(int width, int height) {
/*  474 */     if (OpenGlHelper.shadersSupported) {
/*      */       
/*  476 */       if (this.theShaderGroup != null)
/*      */       {
/*  478 */         this.theShaderGroup.createBindFramebuffers(width, height);
/*      */       }
/*      */       
/*  481 */       this.mc.renderGlobal.createBindEntityOutlineFbs(width, height);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getMouseOver(float partialTicks) {
/*  490 */     Entity entity = this.mc.getRenderViewEntity();
/*      */     
/*  492 */     if (entity != null && this.mc.theWorld != null) {
/*      */       
/*  494 */       this.mc.mcProfiler.startSection("pick");
/*  495 */       this.mc.pointedEntity = null;
/*  496 */       double d0 = this.mc.playerController.getBlockReachDistance();
/*  497 */       this.mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
/*  498 */       double d1 = d0;
/*  499 */       Vec3 vec3 = entity.getPositionEyes(partialTicks);
/*  500 */       boolean flag = false;
/*      */       
/*  502 */       if (this.mc.playerController.extendedReach()) {
/*      */         
/*  504 */         d0 = 6.0D;
/*  505 */         d1 = 6.0D;
/*      */       }
/*  507 */       else if (d0 > 3.0D) {
/*      */         
/*  509 */         flag = true;
/*      */       } 
/*      */       
/*  512 */       if (this.mc.objectMouseOver != null)
/*      */       {
/*  514 */         d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
/*      */       }
/*      */       
/*  517 */       Vec3 vec31 = entity.getLook(partialTicks);
/*  518 */       Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
/*  519 */       this.pointedEntity = null;
/*  520 */       Vec3 vec33 = null;
/*  521 */       float f = 1.0F;
/*  522 */       List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity, entity
/*      */           
/*  524 */           .getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f, f, f), 
/*  525 */           Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
/*      */             {
/*      */               public boolean apply(Entity p_apply_1_)
/*      */               {
/*  529 */                 return p_apply_1_.canBeCollidedWith();
/*      */               }
/*      */             }));
/*  532 */       double d2 = d1;
/*      */       
/*  534 */       for (int j = 0; j < list.size(); j++) {
/*      */         
/*  536 */         Entity entity1 = list.get(j);
/*  537 */         float f1 = entity1.getCollisionBorderSize();
/*  538 */         AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
/*  539 */         MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
/*      */         
/*  541 */         if (axisalignedbb.isVecInside(vec3)) {
/*      */           
/*  543 */           if (d2 >= 0.0D)
/*      */           {
/*  545 */             this.pointedEntity = entity1;
/*  546 */             vec33 = (movingobjectposition == null) ? vec3 : movingobjectposition.hitVec;
/*  547 */             d2 = 0.0D;
/*      */           }
/*      */         
/*  550 */         } else if (movingobjectposition != null) {
/*      */           
/*  552 */           double d3 = vec3.distanceTo(movingobjectposition.hitVec);
/*      */           
/*  554 */           if (d3 < d2 || d2 == 0.0D) {
/*      */             
/*  556 */             boolean flag1 = false;
/*      */             
/*  558 */             if (Reflector.ForgeEntity_canRiderInteract.exists())
/*      */             {
/*  560 */               flag1 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
/*      */             }
/*      */             
/*  563 */             if (!flag1 && entity1 == entity.ridingEntity) {
/*      */               
/*  565 */               if (d2 == 0.0D)
/*      */               {
/*  567 */                 this.pointedEntity = entity1;
/*  568 */                 vec33 = movingobjectposition.hitVec;
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/*  573 */               this.pointedEntity = entity1;
/*  574 */               vec33 = movingobjectposition.hitVec;
/*  575 */               d2 = d3;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  581 */       if (this.pointedEntity != null && flag) {
/*  582 */         double combatReach = 3.0D;
/*  583 */         if (((Reach)Slack.getInstance().getModuleManager().getInstance(Reach.class)).isToggle()) {
/*  584 */           combatReach = ((Reach)Slack.getInstance().getModuleManager().getInstance(Reach.class)).combatReach;
/*      */         }
/*  586 */         if (vec3.distanceTo(vec33) > combatReach) {
/*  587 */           this.pointedEntity = null;
/*  588 */           this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
/*      */         } 
/*      */       } 
/*      */       
/*  592 */       if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null)) {
/*      */         
/*  594 */         this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, vec33);
/*      */         
/*  596 */         if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof net.minecraft.entity.item.EntityItemFrame)
/*      */         {
/*  598 */           this.mc.pointedEntity = this.pointedEntity;
/*      */         }
/*      */       } 
/*      */       
/*  602 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateFovModifierHand() {
/*  611 */     float f = 1.0F;
/*      */     
/*  613 */     if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayer) {
/*      */       
/*  615 */       AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)this.mc.getRenderViewEntity();
/*  616 */       f = abstractclientplayer.getFovModifier();
/*      */     } 
/*      */     
/*  619 */     this.fovModifierHandPrev = this.fovModifierHand;
/*  620 */     this.fovModifierHand += (f - this.fovModifierHand) * 0.5F;
/*      */     
/*  622 */     if (this.fovModifierHand > 1.5F)
/*      */     {
/*  624 */       this.fovModifierHand = 1.5F;
/*      */     }
/*      */     
/*  627 */     if (this.fovModifierHand < 0.1F)
/*      */     {
/*  629 */       this.fovModifierHand = 0.1F;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float getFOVModifier(float partialTicks, boolean p_78481_2_) {
/*  638 */     if (this.debugView)
/*      */     {
/*  640 */       return 90.0F;
/*      */     }
/*      */ 
/*      */     
/*  644 */     Entity entity = this.mc.getRenderViewEntity();
/*  645 */     float f = 70.0F;
/*      */     
/*  647 */     if (p_78481_2_) {
/*      */       
/*  649 */       f = this.mc.gameSettings.fovSetting;
/*      */       
/*  651 */       if (Config.isDynamicFov())
/*      */       {
/*  653 */         f *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * partialTicks;
/*      */       }
/*      */     } 
/*      */     
/*  657 */     boolean flag = false;
/*      */     
/*  659 */     if (this.mc.currentScreen == null) {
/*      */       
/*  661 */       GameSettings gamesettings = this.mc.gameSettings;
/*  662 */       flag = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
/*      */     } 
/*      */     
/*  665 */     if (flag) {
/*      */       
/*  667 */       if (!Config.zoomMode) {
/*      */         
/*  669 */         Config.zoomMode = true;
/*  670 */         this.mc.gameSettings.smoothCamera = true;
/*  671 */         this.mc.renderGlobal.displayListEntitiesDirty = true;
/*      */       } 
/*      */       
/*  674 */       if (Config.zoomMode)
/*      */       {
/*  676 */         f /= 4.0F;
/*      */       }
/*      */     }
/*  679 */     else if (Config.zoomMode) {
/*      */       
/*  681 */       Config.zoomMode = false;
/*  682 */       this.mc.gameSettings.smoothCamera = false;
/*  683 */       this.mouseFilterXAxis = new MouseFilter();
/*  684 */       this.mouseFilterYAxis = new MouseFilter();
/*  685 */       this.mc.renderGlobal.displayListEntitiesDirty = true;
/*      */     } 
/*      */     
/*  688 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0F) {
/*      */       
/*  690 */       float f1 = ((EntityLivingBase)entity).deathTime + partialTicks;
/*  691 */       f /= (1.0F - 500.0F / (f1 + 500.0F)) * 2.0F + 1.0F;
/*      */     } 
/*      */     
/*  694 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*      */     
/*  696 */     if (block.getMaterial() == Material.water)
/*      */     {
/*  698 */       f = f * 60.0F / 70.0F;
/*      */     }
/*      */     
/*  701 */     return Reflector.ForgeHooksClient_getFOVModifier.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getFOVModifier, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(f) }) : f;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void hurtCameraEffect(float partialTicks) {
/*  707 */     if (this.mc.getRenderViewEntity() instanceof EntityLivingBase && !((Boolean)((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).nohurtcam.getValue()).booleanValue()) {
/*      */       
/*  709 */       EntityLivingBase entitylivingbase = (EntityLivingBase)this.mc.getRenderViewEntity();
/*  710 */       float f = entitylivingbase.hurtTime - partialTicks;
/*      */       
/*  712 */       if (entitylivingbase.getHealth() <= 0.0F) {
/*      */         
/*  714 */         float f1 = entitylivingbase.deathTime + partialTicks;
/*  715 */         GlStateManager.rotate(40.0F - 8000.0F / (f1 + 200.0F), 0.0F, 0.0F, 1.0F);
/*      */       } 
/*      */       
/*  718 */       if (f < 0.0F) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  723 */       f /= entitylivingbase.maxHurtTime;
/*  724 */       f = MathHelper.sin(f * f * f * f * 3.1415927F);
/*  725 */       float f2 = entitylivingbase.attackedAtYaw;
/*  726 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  727 */       GlStateManager.rotate(-f * 14.0F, 0.0F, 0.0F, 1.0F);
/*  728 */       GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupViewBobbing(float partialTicks) {
/*  737 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*      */       
/*  739 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  740 */       float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
/*  741 */       float f1 = -(entityplayer.distanceWalkedModified + f * partialTicks);
/*  742 */       float f2 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
/*  743 */       float f3 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
/*  744 */       GlStateManager.translate(MathHelper.sin(f1 * 3.1415927F) * f2 * 0.5F, -Math.abs(MathHelper.cos(f1 * 3.1415927F) * f2), 0.0F);
/*  745 */       GlStateManager.rotate(MathHelper.sin(f1 * 3.1415927F) * f2 * 3.0F, 0.0F, 0.0F, 1.0F);
/*  746 */       GlStateManager.rotate(Math.abs(MathHelper.cos(f1 * 3.1415927F - 0.2F) * f2) * 5.0F, 1.0F, 0.0F, 0.0F);
/*  747 */       GlStateManager.rotate(f3, 1.0F, 0.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void orientCamera(float partialTicks) {
/*  756 */     Entity entity = this.mc.getRenderViewEntity();
/*  757 */     float f = entity.getEyeHeight();
/*  758 */     double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  759 */     double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  760 */     double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*      */     
/*  762 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping()) {
/*      */       
/*  764 */       f = (float)(f + 1.0D);
/*  765 */       GlStateManager.translate(0.0F, 0.3F, 0.0F);
/*      */       
/*  767 */       if (!this.mc.gameSettings.debugCamEnable)
/*      */       {
/*  769 */         BlockPos blockpos = new BlockPos(entity);
/*  770 */         IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/*  771 */         Block block = iblockstate.getBlock();
/*      */         
/*  773 */         if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
/*      */           
/*  775 */           Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, new Object[] { this.mc.theWorld, blockpos, iblockstate, entity });
/*      */         }
/*  777 */         else if (block == Blocks.bed) {
/*      */           
/*  779 */           int j = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/*  780 */           GlStateManager.rotate((j * 90), 0.0F, 1.0F, 0.0F);
/*      */         } 
/*      */         
/*  783 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
/*  784 */         GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
/*      */       }
/*      */     
/*  787 */     } else if (this.mc.gameSettings.thirdPersonView > 0) {
/*      */       
/*  789 */       double d3 = (this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * partialTicks);
/*      */       
/*  791 */       if (this.mc.gameSettings.debugCamEnable)
/*      */       {
/*  793 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*      */       }
/*      */       else
/*      */       {
/*  797 */         float f1 = entity.rotationYaw;
/*  798 */         float f2 = entity.rotationPitch;
/*      */         
/*  800 */         if (this.mc.gameSettings.thirdPersonView == 2)
/*      */         {
/*  802 */           f2 += 180.0F;
/*      */         }
/*      */         
/*  805 */         double d4 = (-MathHelper.sin(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F)) * d3;
/*  806 */         double d5 = (MathHelper.cos(f1 / 180.0F * 3.1415927F) * MathHelper.cos(f2 / 180.0F * 3.1415927F)) * d3;
/*  807 */         double d6 = -MathHelper.sin(f2 / 180.0F * 3.1415927F) * d3;
/*      */         
/*  809 */         for (int i = 0; i < 8; i++) {
/*      */           
/*  811 */           float f3 = ((i & 0x1) * 2 - 1);
/*  812 */           float f4 = ((i >> 1 & 0x1) * 2 - 1);
/*  813 */           float f5 = ((i >> 2 & 0x1) * 2 - 1);
/*  814 */           f3 *= 0.1F;
/*  815 */           f4 *= 0.1F;
/*  816 */           f5 *= 0.1F;
/*  817 */           MovingObjectPosition movingobjectposition = this.mc.theWorld.rayTraceBlocks(new Vec3(d0 + f3, d1 + f4, d2 + f5), new Vec3(d0 - d4 + f3 + f5, d1 - d6 + f4, d2 - d5 + f5));
/*      */           
/*  819 */           if (movingobjectposition != null) {
/*      */             
/*  821 */             double d7 = movingobjectposition.hitVec.distanceTo(new Vec3(d0, d1, d2));
/*      */             
/*  823 */             if (d7 < d3)
/*      */             {
/*  825 */               d3 = d7;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  830 */         if (this.mc.gameSettings.thirdPersonView == 2)
/*      */         {
/*  832 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */         }
/*      */         
/*  835 */         GlStateManager.rotate(entity.rotationPitch - f2, 1.0F, 0.0F, 0.0F);
/*  836 */         GlStateManager.rotate(entity.rotationYaw - f1, 0.0F, 1.0F, 0.0F);
/*  837 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*  838 */         GlStateManager.rotate(f1 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  839 */         GlStateManager.rotate(f2 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  844 */       GlStateManager.translate(0.0F, 0.0F, -0.1F);
/*      */     } 
/*      */     
/*  847 */     if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists()) {
/*      */       
/*  849 */       if (!this.mc.gameSettings.debugCamEnable)
/*      */       {
/*  851 */         float f6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F;
/*  852 */         float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
/*  853 */         float f8 = 0.0F;
/*      */         
/*  855 */         if (entity instanceof EntityAnimal) {
/*      */           
/*  857 */           EntityAnimal entityanimal1 = (EntityAnimal)entity;
/*  858 */           f6 = entityanimal1.prevRotationYawHead + (entityanimal1.rotationYawHead - entityanimal1.prevRotationYawHead) * partialTicks + 180.0F;
/*      */         } 
/*      */         
/*  861 */         Block block1 = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*  862 */         Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_CameraSetup_Constructor, new Object[] { this, entity, block1, Float.valueOf(partialTicks), Float.valueOf(f6), Float.valueOf(f7), Float.valueOf(f8) });
/*  863 */         Reflector.postForgeBusEvent(object);
/*  864 */         f8 = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_roll, f8);
/*  865 */         f7 = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_pitch, f7);
/*  866 */         f6 = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_yaw, f6);
/*  867 */         GlStateManager.rotate(f8, 0.0F, 0.0F, 1.0F);
/*  868 */         GlStateManager.rotate(f7, 1.0F, 0.0F, 0.0F);
/*  869 */         GlStateManager.rotate(f6, 0.0F, 1.0F, 0.0F);
/*      */       }
/*      */     
/*  872 */     } else if (!this.mc.gameSettings.debugCamEnable) {
/*      */       
/*  874 */       GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
/*      */       
/*  876 */       if (entity instanceof EntityAnimal) {
/*      */         
/*  878 */         EntityAnimal entityanimal = (EntityAnimal)entity;
/*  879 */         GlStateManager.rotate(entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */       }
/*      */       else {
/*      */         
/*  883 */         GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
/*      */       } 
/*      */     } 
/*      */     
/*  887 */     GlStateManager.translate(0.0F, -f, 0.0F);
/*  888 */     d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  889 */     d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  890 */     d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*  891 */     this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d1, d2, partialTicks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupCameraTransform(float partialTicks, int pass) {
/*  899 */     this.farPlaneDistance = (this.mc.gameSettings.renderDistanceChunks * 16);
/*      */     
/*  901 */     if (Config.isFogFancy())
/*      */     {
/*  903 */       this.farPlaneDistance *= 0.95F;
/*      */     }
/*      */     
/*  906 */     if (Config.isFogFast())
/*      */     {
/*  908 */       this.farPlaneDistance *= 0.83F;
/*      */     }
/*      */     
/*  911 */     GlStateManager.matrixMode(5889);
/*  912 */     GlStateManager.loadIdentity();
/*  913 */     float f = 0.07F;
/*      */     
/*  915 */     if (this.mc.gameSettings.anaglyph)
/*      */     {
/*  917 */       GlStateManager.translate(-(pass * 2 - 1) * f, 0.0F, 0.0F);
/*      */     }
/*      */     
/*  920 */     this.clipDistance = this.farPlaneDistance * 2.0F;
/*      */     
/*  922 */     if (this.clipDistance < 173.0F)
/*      */     {
/*  924 */       this.clipDistance = 173.0F;
/*      */     }
/*      */     
/*  927 */     if (this.cameraZoom != 1.0D) {
/*      */       
/*  929 */       GlStateManager.translate((float)this.cameraYaw, (float)-this.cameraPitch, 0.0F);
/*  930 */       GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0D);
/*      */     } 
/*      */     
/*  933 */     Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/*  934 */     GlStateManager.matrixMode(5888);
/*  935 */     GlStateManager.loadIdentity();
/*      */     
/*  937 */     if (this.mc.gameSettings.anaglyph)
/*      */     {
/*  939 */       GlStateManager.translate((pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*      */     }
/*      */     
/*  942 */     hurtCameraEffect(partialTicks);
/*      */     
/*  944 */     if (this.mc.gameSettings.viewBobbing)
/*      */     {
/*  946 */       setupViewBobbing(partialTicks);
/*      */     }
/*      */     
/*  949 */     float f1 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;
/*      */     
/*  951 */     if (f1 > 0.0F) {
/*      */       
/*  953 */       int i = 20;
/*      */       
/*  955 */       if (this.mc.thePlayer.isPotionActive(Potion.confusion))
/*      */       {
/*  957 */         i = 7;
/*      */       }
/*      */       
/*  960 */       float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
/*  961 */       f2 *= f2;
/*  962 */       GlStateManager.rotate((this.rendererUpdateCount + partialTicks) * i, 0.0F, 1.0F, 1.0F);
/*  963 */       GlStateManager.scale(1.0F / f2, 1.0F, 1.0F);
/*  964 */       GlStateManager.rotate(-(this.rendererUpdateCount + partialTicks) * i, 0.0F, 1.0F, 1.0F);
/*      */     } 
/*      */     
/*  967 */     orientCamera(partialTicks);
/*      */     
/*  969 */     if (this.debugView)
/*      */     {
/*  971 */       switch (this.debugViewDirection) {
/*      */         
/*      */         case 0:
/*  974 */           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 1:
/*  978 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 2:
/*  982 */           GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 3:
/*  986 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 4:
/*  990 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderHand(float partialTicks, int xOffset) {
/* 1002 */     renderHand(partialTicks, xOffset, true, true, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderHand(float p_renderHand_1_, int p_renderHand_2_, boolean p_renderHand_3_, boolean p_renderHand_4_, boolean p_renderHand_5_) {
/* 1007 */     if (!this.debugView) {
/*      */       
/* 1009 */       GlStateManager.matrixMode(5889);
/* 1010 */       GlStateManager.loadIdentity();
/* 1011 */       float f = 0.07F;
/*      */       
/* 1013 */       if (this.mc.gameSettings.anaglyph)
/*      */       {
/* 1015 */         GlStateManager.translate(-(p_renderHand_2_ * 2 - 1) * f, 0.0F, 0.0F);
/*      */       }
/*      */       
/* 1018 */       if (Config.isShaders())
/*      */       {
/* 1020 */         Shaders.applyHandDepth();
/*      */       }
/*      */       
/* 1023 */       Project.gluPerspective(getFOVModifier(p_renderHand_1_, false), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
/* 1024 */       GlStateManager.matrixMode(5888);
/* 1025 */       GlStateManager.loadIdentity();
/*      */       
/* 1027 */       if (this.mc.gameSettings.anaglyph)
/*      */       {
/* 1029 */         GlStateManager.translate((p_renderHand_2_ * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*      */       }
/*      */       
/* 1032 */       boolean flag = false;
/*      */       
/* 1034 */       if (p_renderHand_3_) {
/*      */         
/* 1036 */         GlStateManager.pushMatrix();
/* 1037 */         hurtCameraEffect(p_renderHand_1_);
/*      */         
/* 1039 */         if (this.mc.gameSettings.viewBobbing)
/*      */         {
/* 1041 */           setupViewBobbing(p_renderHand_1_);
/*      */         }
/*      */         
/* 1044 */         flag = (this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping());
/* 1045 */         boolean flag1 = !ReflectorForge.renderFirstPersonHand(this.mc.renderGlobal, p_renderHand_1_, p_renderHand_2_);
/*      */         
/* 1047 */         if (flag1 && this.mc.gameSettings.thirdPersonView == 0 && !flag && !this.mc.gameSettings.hideGUI && !this.mc.playerController.isSpectator()) {
/*      */           
/* 1049 */           enableLightmap();
/*      */           
/* 1051 */           if (Config.isShaders()) {
/*      */             
/* 1053 */             ShadersRender.renderItemFP(this.itemRenderer, p_renderHand_1_, p_renderHand_5_);
/*      */           }
/*      */           else {
/*      */             
/* 1057 */             this.itemRenderer.renderItemInFirstPerson(p_renderHand_1_);
/*      */           } 
/*      */           
/* 1060 */           disableLightmap();
/*      */         } 
/*      */         
/* 1063 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/* 1066 */       if (!p_renderHand_4_) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1071 */       disableLightmap();
/*      */       
/* 1073 */       if (this.mc.gameSettings.thirdPersonView == 0 && !flag) {
/*      */         
/* 1075 */         this.itemRenderer.renderOverlays(p_renderHand_1_);
/* 1076 */         hurtCameraEffect(p_renderHand_1_);
/*      */       } 
/*      */       
/* 1079 */       if (this.mc.gameSettings.viewBobbing)
/*      */       {
/* 1081 */         setupViewBobbing(p_renderHand_1_);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void disableLightmap() {
/* 1088 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 1089 */     GlStateManager.disableTexture2D();
/* 1090 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */     
/* 1092 */     if (Config.isShaders())
/*      */     {
/* 1094 */       Shaders.disableLightmap();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void enableLightmap() {
/* 1100 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 1101 */     GlStateManager.matrixMode(5890);
/* 1102 */     GlStateManager.loadIdentity();
/* 1103 */     float f = 0.00390625F;
/* 1104 */     GlStateManager.scale(f, f, f);
/* 1105 */     GlStateManager.translate(8.0F, 8.0F, 8.0F);
/* 1106 */     GlStateManager.matrixMode(5888);
/* 1107 */     this.mc.getTextureManager().bindTexture(this.locationLightMap);
/* 1108 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 1109 */     GL11.glTexParameteri(3553, 10240, 9729);
/* 1110 */     GL11.glTexParameteri(3553, 10242, 33071);
/* 1111 */     GL11.glTexParameteri(3553, 10243, 33071);
/* 1112 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1113 */     GlStateManager.enableTexture2D();
/* 1114 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */     
/* 1116 */     if (Config.isShaders())
/*      */     {
/* 1118 */       Shaders.enableLightmap();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateTorchFlicker() {
/* 1127 */     this.torchFlickerDX = (float)(this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
/* 1128 */     this.torchFlickerDX = (float)(this.torchFlickerDX * 0.9D);
/* 1129 */     this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0F;
/* 1130 */     this.lightmapUpdateNeeded = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateLightmap(float partialTicks) {
/* 1135 */     if (this.lightmapUpdateNeeded) {
/*      */       
/* 1137 */       this.mc.mcProfiler.startSection("lightTex");
/* 1138 */       WorldClient worldClient = this.mc.theWorld;
/*      */       
/* 1140 */       if (worldClient != null) {
/*      */         
/* 1142 */         if (Config.isCustomColors() && CustomColors.updateLightmap((World)worldClient, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision), partialTicks)) {
/*      */           
/* 1144 */           this.lightmapTexture.updateDynamicTexture();
/* 1145 */           this.lightmapUpdateNeeded = false;
/* 1146 */           this.mc.mcProfiler.endSection();
/*      */           
/*      */           return;
/*      */         } 
/* 1150 */         float f = worldClient.getSunBrightness(1.0F);
/* 1151 */         float f1 = f * 0.95F + 0.05F;
/*      */         
/* 1153 */         for (int i = 0; i < 256; i++) {
/*      */           
/* 1155 */           float f2 = ((World)worldClient).provider.getLightBrightnessTable()[i / 16] * f1;
/* 1156 */           float f3 = ((World)worldClient).provider.getLightBrightnessTable()[i % 16] * (this.torchFlickerX * 0.1F + 1.5F);
/*      */           
/* 1158 */           if (worldClient.getLastLightningBolt() > 0)
/*      */           {
/* 1160 */             f2 = ((World)worldClient).provider.getLightBrightnessTable()[i / 16];
/*      */           }
/*      */           
/* 1163 */           float f4 = f2 * (f * 0.65F + 0.35F);
/* 1164 */           float f5 = f2 * (f * 0.65F + 0.35F);
/* 1165 */           float f6 = f3 * ((f3 * 0.6F + 0.4F) * 0.6F + 0.4F);
/* 1166 */           float f7 = f3 * (f3 * f3 * 0.6F + 0.4F);
/* 1167 */           float f8 = f4 + f3;
/* 1168 */           float f9 = f5 + f6;
/* 1169 */           float f10 = f2 + f7;
/* 1170 */           f8 = f8 * 0.96F + 0.03F;
/* 1171 */           f9 = f9 * 0.96F + 0.03F;
/* 1172 */           f10 = f10 * 0.96F + 0.03F;
/*      */           
/* 1174 */           if (this.bossColorModifier > 0.0F) {
/*      */             
/* 1176 */             float f11 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
/* 1177 */             f8 = f8 * (1.0F - f11) + f8 * 0.7F * f11;
/* 1178 */             f9 = f9 * (1.0F - f11) + f9 * 0.6F * f11;
/* 1179 */             f10 = f10 * (1.0F - f11) + f10 * 0.6F * f11;
/*      */           } 
/*      */           
/* 1182 */           if (((World)worldClient).provider.getDimensionId() == 1) {
/*      */             
/* 1184 */             f8 = 0.22F + f3 * 0.75F;
/* 1185 */             f9 = 0.28F + f6 * 0.75F;
/* 1186 */             f10 = 0.25F + f7 * 0.75F;
/*      */           } 
/*      */           
/* 1189 */           if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
/*      */             
/* 1191 */             float f15 = getNightVisionBrightness((EntityLivingBase)this.mc.thePlayer, partialTicks);
/* 1192 */             float f12 = 1.0F / f8;
/*      */             
/* 1194 */             if (f12 > 1.0F / f9)
/*      */             {
/* 1196 */               f12 = 1.0F / f9;
/*      */             }
/*      */             
/* 1199 */             if (f12 > 1.0F / f10)
/*      */             {
/* 1201 */               f12 = 1.0F / f10;
/*      */             }
/*      */             
/* 1204 */             f8 = f8 * (1.0F - f15) + f8 * f12 * f15;
/* 1205 */             f9 = f9 * (1.0F - f15) + f9 * f12 * f15;
/* 1206 */             f10 = f10 * (1.0F - f15) + f10 * f12 * f15;
/*      */           } 
/*      */           
/* 1209 */           if (f8 > 1.0F)
/*      */           {
/* 1211 */             f8 = 1.0F;
/*      */           }
/*      */           
/* 1214 */           if (f9 > 1.0F)
/*      */           {
/* 1216 */             f9 = 1.0F;
/*      */           }
/*      */           
/* 1219 */           if (f10 > 1.0F)
/*      */           {
/* 1221 */             f10 = 1.0F;
/*      */           }
/*      */           
/* 1224 */           float f16 = this.mc.gameSettings.gammaSetting;
/* 1225 */           float f17 = 1.0F - f8;
/* 1226 */           float f13 = 1.0F - f9;
/* 1227 */           float f14 = 1.0F - f10;
/* 1228 */           f17 = 1.0F - f17 * f17 * f17 * f17;
/* 1229 */           f13 = 1.0F - f13 * f13 * f13 * f13;
/* 1230 */           f14 = 1.0F - f14 * f14 * f14 * f14;
/* 1231 */           f8 = f8 * (1.0F - f16) + f17 * f16;
/* 1232 */           f9 = f9 * (1.0F - f16) + f13 * f16;
/* 1233 */           f10 = f10 * (1.0F - f16) + f14 * f16;
/* 1234 */           f8 = f8 * 0.96F + 0.03F;
/* 1235 */           f9 = f9 * 0.96F + 0.03F;
/* 1236 */           f10 = f10 * 0.96F + 0.03F;
/*      */           
/* 1238 */           if (f8 > 1.0F)
/*      */           {
/* 1240 */             f8 = 1.0F;
/*      */           }
/*      */           
/* 1243 */           if (f9 > 1.0F)
/*      */           {
/* 1245 */             f9 = 1.0F;
/*      */           }
/*      */           
/* 1248 */           if (f10 > 1.0F)
/*      */           {
/* 1250 */             f10 = 1.0F;
/*      */           }
/*      */           
/* 1253 */           if (f8 < 0.0F)
/*      */           {
/* 1255 */             f8 = 0.0F;
/*      */           }
/*      */           
/* 1258 */           if (f9 < 0.0F)
/*      */           {
/* 1260 */             f9 = 0.0F;
/*      */           }
/*      */           
/* 1263 */           if (f10 < 0.0F)
/*      */           {
/* 1265 */             f10 = 0.0F;
/*      */           }
/*      */           
/* 1268 */           int j = 255;
/* 1269 */           int k = (int)(f8 * 255.0F);
/* 1270 */           int l = (int)(f9 * 255.0F);
/* 1271 */           int i1 = (int)(f10 * 255.0F);
/* 1272 */           this.lightmapColors[i] = j << 24 | k << 16 | l << 8 | i1;
/*      */         } 
/*      */         
/* 1275 */         this.lightmapTexture.updateDynamicTexture();
/* 1276 */         this.lightmapUpdateNeeded = false;
/* 1277 */         this.mc.mcProfiler.endSection();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getNightVisionBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks) {
/* 1284 */     int i = entitylivingbaseIn.getActivePotionEffect(Potion.nightVision).getDuration();
/* 1285 */     return (i > 200) ? 1.0F : (0.7F + MathHelper.sin((i - partialTicks) * 3.1415927F * 0.2F) * 0.3F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181560_a(float p_181560_1_, long p_181560_2_) {
/* 1290 */     Config.renderPartialTicks = p_181560_1_;
/* 1291 */     frameInit();
/* 1292 */     boolean flag = Display.isActive();
/*      */     
/* 1294 */     if (!flag && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
/*      */       
/* 1296 */       if (Minecraft.getSystemTime() - this.prevFrameTime > 500L)
/*      */       {
/* 1298 */         this.mc.displayInGameMenu();
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1303 */       this.prevFrameTime = Minecraft.getSystemTime();
/*      */     } 
/*      */     
/* 1306 */     this.mc.mcProfiler.startSection("mouse");
/*      */     
/* 1308 */     if (flag && Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
/*      */       
/* 1310 */       Mouse.setGrabbed(false);
/* 1311 */       Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
/* 1312 */       Mouse.setGrabbed(true);
/*      */     } 
/*      */     
/* 1315 */     if (this.mc.inGameHasFocus && flag) {
/*      */       
/* 1317 */       this.mc.mouseHelper.mouseXYChange();
/* 1318 */       float f = ((AimAssist)Slack.getInstance().getModuleManager().getInstance(AimAssist.class)).getSens() * 0.6F + 0.2F;
/* 1319 */       float f1 = f * f * f * 8.0F;
/* 1320 */       float f2 = this.mc.mouseHelper.deltaX * f1;
/* 1321 */       float f3 = this.mc.mouseHelper.deltaY * f1;
/* 1322 */       int i = 1;
/*      */       
/* 1324 */       if (this.mc.gameSettings.invertMouse)
/*      */       {
/* 1326 */         i = -1;
/*      */       }
/*      */       
/* 1329 */       if (this.mc.gameSettings.smoothCamera) {
/*      */         
/* 1331 */         this.smoothCamYaw += f2;
/* 1332 */         this.smoothCamPitch += f3;
/* 1333 */         float f4 = p_181560_1_ - this.smoothCamPartialTicks;
/* 1334 */         this.smoothCamPartialTicks = p_181560_1_;
/* 1335 */         f2 = this.smoothCamFilterX * f4;
/* 1336 */         f3 = this.smoothCamFilterY * f4;
/* 1337 */         if (FreeLookUtil.freelooking) {
/* 1338 */           FreeLookUtil.overrideMouse(f2, f3 * i);
/*      */         } else {
/* 1340 */           this.mc.thePlayer.setAngles(f2, f3 * i);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1345 */         if (FreeLookUtil.freelooking) {
/* 1346 */           FreeLookUtil.overrideMouse(f2, f3 * i);
/*      */         } else {
/* 1348 */           this.mc.thePlayer.setAngles(f2, f3 * i);
/*      */         } 
/* 1350 */         this.smoothCamYaw = 0.0F;
/* 1351 */         this.smoothCamPitch = 0.0F;
/*      */       } 
/*      */     } 
/*      */     
/* 1355 */     this.mc.mcProfiler.endSection();
/*      */     
/* 1357 */     if (!this.mc.skipRenderWorld) {
/*      */       
/* 1359 */       anaglyphEnable = this.mc.gameSettings.anaglyph;
/* 1360 */       final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1361 */       int i1 = scaledresolution.getScaledWidth();
/* 1362 */       int j1 = scaledresolution.getScaledHeight();
/* 1363 */       final int k1 = Mouse.getX() * i1 / this.mc.displayWidth;
/* 1364 */       final int l1 = j1 - Mouse.getY() * j1 / this.mc.displayHeight - 1;
/* 1365 */       int i2 = this.mc.gameSettings.limitFramerate;
/*      */       
/* 1367 */       if (this.mc.theWorld != null) {
/*      */         
/* 1369 */         this.mc.mcProfiler.startSection("level");
/* 1370 */         int j = Math.min(Minecraft.getDebugFPS(), i2);
/* 1371 */         j = Math.max(j, 60);
/* 1372 */         long k = System.nanoTime() - p_181560_2_;
/* 1373 */         long l = Math.max((1000000000 / j / 4) - k, 0L);
/* 1374 */         renderWorld(p_181560_1_, System.nanoTime() + l);
/*      */         
/* 1376 */         if (OpenGlHelper.shadersSupported) {
/*      */           
/* 1378 */           this.mc.renderGlobal.renderEntityOutlineFramebuffer();
/*      */           
/* 1380 */           if (this.theShaderGroup != null && this.useShader) {
/*      */             
/* 1382 */             GlStateManager.matrixMode(5890);
/* 1383 */             GlStateManager.pushMatrix();
/* 1384 */             GlStateManager.loadIdentity();
/* 1385 */             this.theShaderGroup.loadShaderGroup(p_181560_1_);
/* 1386 */             GlStateManager.popMatrix();
/*      */           } 
/*      */           
/* 1389 */           this.mc.getFramebuffer().bindFramebuffer(true);
/*      */         } 
/*      */         
/* 1392 */         this.renderEndNanoTime = System.nanoTime();
/* 1393 */         this.mc.mcProfiler.endStartSection("gui");
/*      */         
/* 1395 */         if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
/*      */           
/* 1397 */           GlStateManager.alphaFunc(516, 0.1F);
/* 1398 */           this.mc.ingameGUI.renderGameOverlay(p_181560_1_);
/*      */           
/* 1400 */           if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo)
/*      */           {
/* 1402 */             Config.drawFps();
/*      */           }
/*      */           
/* 1405 */           if (this.mc.gameSettings.showDebugInfo)
/*      */           {
/* 1407 */             Lagometer.showLagometer(scaledresolution);
/*      */           }
/*      */         } 
/*      */         
/* 1411 */         this.mc.mcProfiler.endSection();
/*      */       }
/*      */       else {
/*      */         
/* 1415 */         GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 1416 */         GlStateManager.matrixMode(5889);
/* 1417 */         GlStateManager.loadIdentity();
/* 1418 */         GlStateManager.matrixMode(5888);
/* 1419 */         GlStateManager.loadIdentity();
/* 1420 */         setupOverlayRendering();
/* 1421 */         this.renderEndNanoTime = System.nanoTime();
/* 1422 */         TileEntityRendererDispatcher.instance.renderEngine = this.mc.getTextureManager();
/* 1423 */         TileEntityRendererDispatcher.instance.fontRenderer = this.mc.MCfontRenderer;
/*      */       } 
/*      */       
/* 1426 */       if (this.mc.currentScreen != null) {
/*      */         
/* 1428 */         GlStateManager.clear(256);
/*      */ 
/*      */         
/*      */         try {
/* 1432 */           if (Reflector.ForgeHooksClient_drawScreen.exists())
/*      */           {
/* 1434 */             Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, new Object[] { this.mc.currentScreen, Integer.valueOf(k1), Integer.valueOf(l1), Float.valueOf(p_181560_1_) });
/*      */           }
/*      */           else
/*      */           {
/* 1438 */             this.mc.currentScreen.drawScreen(k1, l1, p_181560_1_);
/*      */           }
/*      */         
/* 1441 */         } catch (Throwable throwable) {
/*      */           
/* 1443 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering screen");
/* 1444 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Screen render details");
/* 1445 */           crashreportcategory.addCrashSectionCallable("Screen name", new Callable<String>()
/*      */               {
/*      */                 public String call() throws Exception
/*      */                 {
/* 1449 */                   return EntityRenderer.this.mc.currentScreen.getClass().getCanonicalName();
/*      */                 }
/*      */               });
/* 1452 */           crashreportcategory.addCrashSectionCallable("Mouse location", new Callable<String>()
/*      */               {
/*      */                 public String call() throws Exception
/*      */                 {
/* 1456 */                   return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[] { Integer.valueOf(this.val$k1), Integer.valueOf(this.val$l1), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY()) });
/*      */                 }
/*      */               });
/* 1459 */           crashreportcategory.addCrashSectionCallable("Screen size", new Callable<String>()
/*      */               {
/*      */                 public String call() throws Exception
/*      */                 {
/* 1463 */                   return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[] { Integer.valueOf(this.val$scaledresolution.getScaledWidth()), Integer.valueOf(this.val$scaledresolution.getScaledHeight()), Integer.valueOf((EntityRenderer.access$000(this.this$0)).displayWidth), Integer.valueOf((EntityRenderer.access$000(this.this$0)).displayHeight), Integer.valueOf(this.val$scaledresolution.getScaleFactor()) });
/*      */                 }
/*      */               });
/* 1466 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1471 */     frameFinish();
/* 1472 */     waitForServerThread();
/* 1473 */     Lagometer.updateLagometer();
/*      */     
/* 1475 */     if (this.mc.gameSettings.ofProfiler)
/*      */     {
/* 1477 */       this.mc.gameSettings.showDebugProfilerChart = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isDrawBlockOutline() {
/* 1482 */     if (!this.drawBlockOutline)
/*      */     {
/* 1484 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1488 */     Entity entity = this.mc.getRenderViewEntity();
/* 1489 */     boolean flag = (entity instanceof EntityPlayer && !this.mc.gameSettings.hideGUI);
/*      */     
/* 1491 */     if (flag && !((EntityPlayer)entity).capabilities.allowEdit) {
/*      */       
/* 1493 */       ItemStack itemstack = ((EntityPlayer)entity).getCurrentEquippedItem();
/*      */       
/* 1495 */       if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*      */         
/* 1497 */         BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/* 1498 */         IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/* 1499 */         Block block = iblockstate.getBlock();
/*      */         
/* 1501 */         if (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR) {
/*      */           
/* 1503 */           flag = (ReflectorForge.blockHasTileEntity(iblockstate) && this.mc.theWorld.getTileEntity(blockpos) instanceof net.minecraft.inventory.IInventory);
/*      */         }
/*      */         else {
/*      */           
/* 1507 */           flag = (itemstack != null && (itemstack.canDestroy(block) || itemstack.canPlaceOn(block)));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1512 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderWorldDirections(float partialTicks) {
/* 1518 */     if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
/*      */       
/* 1520 */       Entity entity = this.mc.getRenderViewEntity();
/* 1521 */       GlStateManager.enableBlend();
/* 1522 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1523 */       GL11.glLineWidth(1.0F);
/* 1524 */       GlStateManager.disableTexture2D();
/* 1525 */       GlStateManager.depthMask(false);
/* 1526 */       GlStateManager.pushMatrix();
/* 1527 */       GlStateManager.matrixMode(5888);
/* 1528 */       GlStateManager.loadIdentity();
/* 1529 */       orientCamera(partialTicks);
/* 1530 */       GlStateManager.translate(0.0F, entity.getEyeHeight(), 0.0F);
/* 1531 */       RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.005D, 1.0E-4D, 1.0E-4D), 255, 0, 0, 255);
/* 1532 */       RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 1.0E-4D, 0.005D), 0, 0, 255, 255);
/* 1533 */       RenderGlobal.func_181563_a(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0E-4D, 0.0033D, 1.0E-4D), 0, 255, 0, 255);
/* 1534 */       GlStateManager.popMatrix();
/* 1535 */       GlStateManager.depthMask(true);
/* 1536 */       GlStateManager.enableTexture2D();
/* 1537 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderWorld(float partialTicks, long finishTimeNano) {
/* 1543 */     updateLightmap(partialTicks);
/*      */     
/* 1545 */     if (this.mc.getRenderViewEntity() == null)
/*      */     {
/* 1547 */       this.mc.setRenderViewEntity((Entity)this.mc.thePlayer);
/*      */     }
/*      */     
/* 1550 */     getMouseOver(partialTicks);
/*      */     
/* 1552 */     if (Config.isShaders())
/*      */     {
/* 1554 */       Shaders.beginRender(this.mc, partialTicks, finishTimeNano);
/*      */     }
/*      */     
/* 1557 */     GlStateManager.enableDepth();
/* 1558 */     GlStateManager.enableAlpha();
/* 1559 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1560 */     this.mc.mcProfiler.startSection("center");
/*      */     
/* 1562 */     if (this.mc.gameSettings.anaglyph) {
/*      */       
/* 1564 */       anaglyphField = 0;
/* 1565 */       GlStateManager.colorMask(false, true, true, false);
/* 1566 */       renderWorldPass(0, partialTicks, finishTimeNano);
/* 1567 */       anaglyphField = 1;
/* 1568 */       GlStateManager.colorMask(true, false, false, false);
/* 1569 */       renderWorldPass(1, partialTicks, finishTimeNano);
/* 1570 */       GlStateManager.colorMask(true, true, true, false);
/*      */     }
/*      */     else {
/*      */       
/* 1574 */       renderWorldPass(2, partialTicks, finishTimeNano);
/*      */     } 
/*      */     
/* 1577 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderWorldPass(int pass, float partialTicks, long finishTimeNano) {
/* 1582 */     boolean flag = Config.isShaders();
/*      */     
/* 1584 */     if (flag)
/*      */     {
/* 1586 */       Shaders.beginRenderPass(pass, partialTicks, finishTimeNano);
/*      */     }
/*      */     
/* 1589 */     RenderGlobal renderglobal = this.mc.renderGlobal;
/* 1590 */     EffectRenderer effectrenderer = this.mc.effectRenderer;
/* 1591 */     boolean flag1 = isDrawBlockOutline();
/* 1592 */     GlStateManager.enableCull();
/* 1593 */     this.mc.mcProfiler.endStartSection("clear");
/*      */     
/* 1595 */     if (flag) {
/*      */       
/* 1597 */       Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/*      */     }
/*      */     else {
/*      */       
/* 1601 */       GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/*      */     } 
/*      */     
/* 1604 */     updateFogColor(partialTicks);
/* 1605 */     GlStateManager.clear(16640);
/*      */     
/* 1607 */     if (flag)
/*      */     {
/* 1609 */       Shaders.clearRenderBuffer();
/*      */     }
/*      */     
/* 1612 */     this.mc.mcProfiler.endStartSection("camera");
/* 1613 */     setupCameraTransform(partialTicks, pass);
/*      */     
/* 1615 */     if (flag)
/*      */     {
/* 1617 */       Shaders.setCamera(partialTicks);
/*      */     }
/*      */     
/* 1620 */     ActiveRenderInfo.updateRenderInfo((EntityPlayer)this.mc.thePlayer, (this.mc.gameSettings.thirdPersonView == 2));
/* 1621 */     this.mc.mcProfiler.endStartSection("frustum");
/* 1622 */     ClippingHelper clippinghelper = ClippingHelperImpl.getInstance();
/* 1623 */     this.mc.mcProfiler.endStartSection("culling");
/* 1624 */     clippinghelper.disabled = (Config.isShaders() && !Shaders.isFrustumCulling());
/* 1625 */     Frustum frustum = new Frustum(clippinghelper);
/* 1626 */     Entity entity = this.mc.getRenderViewEntity();
/* 1627 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 1628 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 1629 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/*      */     
/* 1631 */     if (flag) {
/*      */       
/* 1633 */       ShadersRender.setFrustrumPosition((ICamera)frustum, d0, d1, d2);
/*      */     }
/*      */     else {
/*      */       
/* 1637 */       frustum.setPosition(d0, d1, d2);
/*      */     } 
/*      */     
/* 1640 */     if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
/*      */       
/* 1642 */       setupFog(-1, partialTicks);
/* 1643 */       this.mc.mcProfiler.endStartSection("sky");
/* 1644 */       GlStateManager.matrixMode(5889);
/* 1645 */       GlStateManager.loadIdentity();
/* 1646 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1647 */       GlStateManager.matrixMode(5888);
/*      */       
/* 1649 */       if (flag)
/*      */       {
/* 1651 */         Shaders.beginSky();
/*      */       }
/*      */       
/* 1654 */       renderglobal.renderSky(partialTicks, pass);
/*      */       
/* 1656 */       if (flag)
/*      */       {
/* 1658 */         Shaders.endSky();
/*      */       }
/*      */       
/* 1661 */       GlStateManager.matrixMode(5889);
/* 1662 */       GlStateManager.loadIdentity();
/* 1663 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1664 */       GlStateManager.matrixMode(5888);
/*      */     }
/*      */     else {
/*      */       
/* 1668 */       GlStateManager.disableBlend();
/*      */     } 
/*      */     
/* 1671 */     setupFog(0, partialTicks);
/* 1672 */     GlStateManager.shadeModel(7425);
/*      */     
/* 1674 */     if (entity.posY + entity.getEyeHeight() < 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F))
/*      */     {
/* 1676 */       renderCloudsCheck(renderglobal, partialTicks, pass);
/*      */     }
/*      */     
/* 1679 */     this.mc.mcProfiler.endStartSection("prepareterrain");
/* 1680 */     setupFog(0, partialTicks);
/* 1681 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1682 */     RenderHelper.disableStandardItemLighting();
/* 1683 */     this.mc.mcProfiler.endStartSection("terrain_setup");
/* 1684 */     checkLoadVisibleChunks(entity, partialTicks, (ICamera)frustum, this.mc.thePlayer.isSpectator());
/*      */     
/* 1686 */     if (flag) {
/*      */       
/* 1688 */       ShadersRender.setupTerrain(renderglobal, entity, partialTicks, (ICamera)frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
/*      */     }
/*      */     else {
/*      */       
/* 1692 */       renderglobal.setupTerrain(entity, partialTicks, (ICamera)frustum, this.frameCount++, this.mc.thePlayer.isSpectator());
/*      */     } 
/*      */     
/* 1695 */     if (pass == 0 || pass == 2) {
/*      */       
/* 1697 */       this.mc.mcProfiler.endStartSection("updatechunks");
/* 1698 */       Lagometer.timerChunkUpload.start();
/* 1699 */       this.mc.renderGlobal.updateChunks(finishTimeNano);
/* 1700 */       Lagometer.timerChunkUpload.end();
/*      */     } 
/*      */     
/* 1703 */     this.mc.mcProfiler.endStartSection("terrain");
/* 1704 */     Lagometer.timerTerrain.start();
/*      */     
/* 1706 */     if (this.mc.gameSettings.ofSmoothFps && pass > 0) {
/*      */       
/* 1708 */       this.mc.mcProfiler.endStartSection("finish");
/* 1709 */       GL11.glFinish();
/* 1710 */       this.mc.mcProfiler.endStartSection("terrain");
/*      */     } 
/*      */     
/* 1713 */     GlStateManager.matrixMode(5888);
/* 1714 */     GlStateManager.pushMatrix();
/* 1715 */     GlStateManager.disableAlpha();
/*      */     
/* 1717 */     if (flag)
/*      */     {
/* 1719 */       ShadersRender.beginTerrainSolid();
/*      */     }
/*      */     
/* 1722 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, partialTicks, pass, entity);
/* 1723 */     GlStateManager.enableAlpha();
/*      */     
/* 1725 */     if (flag)
/*      */     {
/* 1727 */       ShadersRender.beginTerrainCutoutMipped();
/*      */     }
/*      */     
/* 1730 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, (this.mc.gameSettings.mipmapLevels > 0));
/* 1731 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, pass, entity);
/* 1732 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 1733 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/*      */     
/* 1735 */     if (flag)
/*      */     {
/* 1737 */       ShadersRender.beginTerrainCutout();
/*      */     }
/*      */     
/* 1740 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, partialTicks, pass, entity);
/* 1741 */     this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/*      */     
/* 1743 */     if (flag)
/*      */     {
/* 1745 */       ShadersRender.endTerrain();
/*      */     }
/*      */     
/* 1748 */     Lagometer.timerTerrain.end();
/* 1749 */     GlStateManager.shadeModel(7424);
/* 1750 */     GlStateManager.alphaFunc(516, 0.1F);
/*      */     
/* 1752 */     if (!this.debugView) {
/*      */       
/* 1754 */       GlStateManager.matrixMode(5888);
/* 1755 */       GlStateManager.popMatrix();
/* 1756 */       GlStateManager.pushMatrix();
/* 1757 */       RenderHelper.enableStandardItemLighting();
/* 1758 */       this.mc.mcProfiler.endStartSection("entities");
/*      */       
/* 1760 */       if (Reflector.ForgeHooksClient_setRenderPass.exists())
/*      */       {
/* 1762 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*      */       }
/*      */       
/* 1765 */       renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/*      */       
/* 1767 */       if (Reflector.ForgeHooksClient_setRenderPass.exists())
/*      */       {
/* 1769 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/*      */       }
/*      */       
/* 1772 */       RenderHelper.disableStandardItemLighting();
/* 1773 */       disableLightmap();
/* 1774 */       GlStateManager.matrixMode(5888);
/* 1775 */       GlStateManager.popMatrix();
/* 1776 */       GlStateManager.pushMatrix();
/*      */       
/* 1778 */       if (this.mc.objectMouseOver != null && entity.isInsideOfMaterial(Material.water) && flag1) {
/*      */         
/* 1780 */         EntityPlayer entityplayer = (EntityPlayer)entity;
/* 1781 */         GlStateManager.disableAlpha();
/* 1782 */         this.mc.mcProfiler.endStartSection("outline");
/* 1783 */         renderglobal.drawSelectionBox(entityplayer, this.mc.objectMouseOver, 0, partialTicks);
/* 1784 */         GlStateManager.enableAlpha();
/*      */       } 
/*      */     } 
/*      */     
/* 1788 */     GlStateManager.matrixMode(5888);
/* 1789 */     GlStateManager.popMatrix();
/*      */     
/* 1791 */     if (flag1 && this.mc.objectMouseOver != null && !entity.isInsideOfMaterial(Material.water)) {
/*      */       
/* 1793 */       EntityPlayer entityplayer1 = (EntityPlayer)entity;
/* 1794 */       GlStateManager.disableAlpha();
/* 1795 */       this.mc.mcProfiler.endStartSection("outline");
/*      */       
/* 1797 */       if ((!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, new Object[] { renderglobal, entityplayer1, this.mc.objectMouseOver, Integer.valueOf(0), entityplayer1.getHeldItem(), Float.valueOf(partialTicks) })) && !this.mc.gameSettings.hideGUI)
/*      */       {
/* 1799 */         renderglobal.drawSelectionBox(entityplayer1, this.mc.objectMouseOver, 0, partialTicks);
/*      */       }
/* 1801 */       GlStateManager.enableAlpha();
/*      */     } 
/*      */     
/* 1804 */     if (!renderglobal.damagedBlocks.isEmpty()) {
/*      */       
/* 1806 */       this.mc.mcProfiler.endStartSection("destroyProgress");
/* 1807 */       GlStateManager.enableBlend();
/* 1808 */       GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 1809 */       this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/* 1810 */       renderglobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), entity, partialTicks);
/* 1811 */       this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 1812 */       GlStateManager.disableBlend();
/*      */     } 
/*      */     
/* 1815 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1816 */     GlStateManager.disableBlend();
/*      */     
/* 1818 */     if (!this.debugView) {
/*      */       
/* 1820 */       enableLightmap();
/* 1821 */       this.mc.mcProfiler.endStartSection("litParticles");
/*      */       
/* 1823 */       if (flag)
/*      */       {
/* 1825 */         Shaders.beginLitParticles();
/*      */       }
/*      */       
/* 1828 */       effectrenderer.renderLitParticles(entity, partialTicks);
/* 1829 */       RenderHelper.disableStandardItemLighting();
/* 1830 */       setupFog(0, partialTicks);
/* 1831 */       this.mc.mcProfiler.endStartSection("particles");
/*      */       
/* 1833 */       if (flag)
/*      */       {
/* 1835 */         Shaders.beginParticles();
/*      */       }
/*      */       
/* 1838 */       effectrenderer.renderParticles(entity, partialTicks);
/*      */       
/* 1840 */       if (flag)
/*      */       {
/* 1842 */         Shaders.endParticles();
/*      */       }
/*      */       
/* 1845 */       disableLightmap();
/*      */     } 
/*      */     
/* 1848 */     GlStateManager.depthMask(false);
/*      */     
/* 1850 */     if (Config.isShaders())
/*      */     {
/* 1852 */       GlStateManager.depthMask(Shaders.isRainDepth());
/*      */     }
/*      */     
/* 1855 */     GlStateManager.enableCull();
/* 1856 */     this.mc.mcProfiler.endStartSection("weather");
/*      */     
/* 1858 */     if (flag)
/*      */     {
/* 1860 */       Shaders.beginWeather();
/*      */     }
/*      */     
/* 1863 */     renderRainSnow(partialTicks);
/*      */     
/* 1865 */     if (flag)
/*      */     {
/* 1867 */       Shaders.endWeather();
/*      */     }
/*      */     
/* 1870 */     GlStateManager.depthMask(true);
/* 1871 */     renderglobal.renderWorldBorder(entity, partialTicks);
/*      */     
/* 1873 */     if (flag) {
/*      */       
/* 1875 */       ShadersRender.renderHand0(this, partialTicks, pass);
/* 1876 */       Shaders.preWater();
/*      */     } 
/*      */     
/* 1879 */     GlStateManager.disableBlend();
/* 1880 */     GlStateManager.enableCull();
/* 1881 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1882 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1883 */     setupFog(0, partialTicks);
/* 1884 */     GlStateManager.enableBlend();
/* 1885 */     GlStateManager.depthMask(false);
/* 1886 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1887 */     GlStateManager.shadeModel(7425);
/* 1888 */     this.mc.mcProfiler.endStartSection("translucent");
/*      */     
/* 1890 */     if (flag)
/*      */     {
/* 1892 */       Shaders.beginWater();
/*      */     }
/*      */     
/* 1895 */     renderglobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, pass, entity);
/*      */     
/* 1897 */     if (flag)
/*      */     {
/* 1899 */       Shaders.endWater();
/*      */     }
/*      */     
/* 1902 */     if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView) {
/*      */       
/* 1904 */       RenderHelper.enableStandardItemLighting();
/* 1905 */       this.mc.mcProfiler.endStartSection("entities");
/* 1906 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 1907 */       this.mc.renderGlobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 1908 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1909 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 1910 */       RenderHelper.disableStandardItemLighting();
/*      */     } 
/*      */     
/* 1913 */     GlStateManager.shadeModel(7424);
/* 1914 */     GlStateManager.depthMask(true);
/* 1915 */     GlStateManager.enableCull();
/* 1916 */     GlStateManager.disableBlend();
/* 1917 */     GlStateManager.disableFog();
/*      */     
/* 1919 */     if (entity.posY + entity.getEyeHeight() >= 128.0D + (this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
/*      */       
/* 1921 */       this.mc.mcProfiler.endStartSection("aboveClouds");
/* 1922 */       renderCloudsCheck(renderglobal, partialTicks, pass);
/*      */     } 
/*      */     
/* 1925 */     if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
/*      */       
/* 1927 */       this.mc.mcProfiler.endStartSection("forge_render_last");
/* 1928 */       Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, new Object[] { renderglobal, Float.valueOf(partialTicks) });
/*      */     } 
/*      */     
/* 1931 */     this.mc.mcProfiler.endStartSection("hand");
/*      */     
/* 1933 */     (new RenderEvent(RenderEvent.State.RENDER_3D, partialTicks)).call();
/*      */     
/* 1935 */     if (this.renderHand && !Shaders.isShadowPass) {
/*      */       
/* 1937 */       if (flag) {
/*      */         
/* 1939 */         ShadersRender.renderHand1(this, partialTicks, pass);
/* 1940 */         Shaders.renderCompositeFinal();
/*      */       } 
/*      */       
/* 1943 */       GlStateManager.clear(256);
/*      */       
/* 1945 */       if (flag) {
/*      */         
/* 1947 */         ShadersRender.renderFPOverlay(this, partialTicks, pass);
/*      */       }
/*      */       else {
/*      */         
/* 1951 */         renderHand(partialTicks, pass);
/*      */       } 
/*      */       
/* 1954 */       renderWorldDirections(partialTicks);
/*      */     } 
/*      */     
/* 1957 */     if (flag)
/*      */     {
/* 1959 */       Shaders.endRender();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderCloudsCheck(RenderGlobal renderGlobalIn, float partialTicks, int pass) {
/* 1965 */     if (this.mc.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff() && Shaders.shouldRenderClouds(this.mc.gameSettings)) {
/*      */       
/* 1967 */       this.mc.mcProfiler.endStartSection("clouds");
/* 1968 */       GlStateManager.matrixMode(5889);
/* 1969 */       GlStateManager.loadIdentity();
/* 1970 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance * 4.0F);
/* 1971 */       GlStateManager.matrixMode(5888);
/* 1972 */       GlStateManager.pushMatrix();
/* 1973 */       setupFog(0, partialTicks);
/* 1974 */       renderGlobalIn.renderClouds(partialTicks, pass);
/* 1975 */       GlStateManager.disableFog();
/* 1976 */       GlStateManager.popMatrix();
/* 1977 */       GlStateManager.matrixMode(5889);
/* 1978 */       GlStateManager.loadIdentity();
/* 1979 */       Project.gluPerspective(getFOVModifier(partialTicks, true), this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1980 */       GlStateManager.matrixMode(5888);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void addRainParticles() {
/* 1986 */     float f = this.mc.theWorld.getRainStrength(1.0F);
/*      */     
/* 1988 */     if (!Config.isRainFancy())
/*      */     {
/* 1990 */       f /= 2.0F;
/*      */     }
/*      */     
/* 1993 */     if (f != 0.0F && Config.isRainSplash()) {
/*      */       
/* 1995 */       this.random.setSeed(this.rendererUpdateCount * 312987231L);
/* 1996 */       Entity entity = this.mc.getRenderViewEntity();
/* 1997 */       WorldClient worldClient = this.mc.theWorld;
/* 1998 */       BlockPos blockpos = new BlockPos(entity);
/* 1999 */       int i = 10;
/* 2000 */       double d0 = 0.0D;
/* 2001 */       double d1 = 0.0D;
/* 2002 */       double d2 = 0.0D;
/* 2003 */       int j = 0;
/* 2004 */       int k = (int)(100.0F * f * f);
/*      */       
/* 2006 */       if (this.mc.gameSettings.particleSetting == 1) {
/*      */         
/* 2008 */         k >>= 1;
/*      */       }
/* 2010 */       else if (this.mc.gameSettings.particleSetting == 2) {
/*      */         
/* 2012 */         k = 0;
/*      */       } 
/*      */       
/* 2015 */       for (int l = 0; l < k; l++) {
/*      */         
/* 2017 */         BlockPos blockpos1 = worldClient.getPrecipitationHeight(blockpos.add(this.random.nextInt(i) - this.random.nextInt(i), 0, this.random.nextInt(i) - this.random.nextInt(i)));
/* 2018 */         BiomeGenBase biomegenbase = worldClient.getBiomeGenForCoords(blockpos1);
/* 2019 */         BlockPos blockpos2 = blockpos1.down();
/* 2020 */         Block block = worldClient.getBlockState(blockpos2).getBlock();
/*      */         
/* 2022 */         if (blockpos1.getY() <= blockpos.getY() + i && blockpos1.getY() >= blockpos.getY() - i && biomegenbase.canSpawnLightningBolt() && biomegenbase.getFloatTemperature(blockpos1) >= 0.15F) {
/*      */           
/* 2024 */           double d3 = this.random.nextDouble();
/* 2025 */           double d4 = this.random.nextDouble();
/*      */           
/* 2027 */           if (block.getMaterial() == Material.lava) {
/*      */             
/* 2029 */             this.mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockpos1.getX() + d3, (blockpos1.getY() + 0.1F) - block.getBlockBoundsMinY(), blockpos1.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */           }
/* 2031 */           else if (block.getMaterial() != Material.air) {
/*      */             
/* 2033 */             block.setBlockBoundsBasedOnState((IBlockAccess)worldClient, blockpos2);
/* 2034 */             j++;
/*      */             
/* 2036 */             if (this.random.nextInt(j) == 0) {
/*      */               
/* 2038 */               d0 = blockpos2.getX() + d3;
/* 2039 */               d1 = (blockpos2.getY() + 0.1F) + block.getBlockBoundsMaxY() - 1.0D;
/* 2040 */               d2 = blockpos2.getZ() + d4;
/*      */             } 
/*      */             
/* 2043 */             this.mc.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, blockpos2.getX() + d3, (blockpos2.getY() + 0.1F) + block.getBlockBoundsMaxY(), blockpos2.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2048 */       if (j > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
/*      */         
/* 2050 */         this.rainSoundCounter = 0;
/*      */         
/* 2052 */         if (d1 > (blockpos.getY() + 1) && worldClient.getPrecipitationHeight(blockpos).getY() > MathHelper.floor_float(blockpos.getY())) {
/*      */           
/* 2054 */           this.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.1F, 0.5F, false);
/*      */         }
/*      */         else {
/*      */           
/* 2058 */           this.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.2F, 1.0F, false);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderRainSnow(float partialTicks) {
/* 2069 */     if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
/*      */       
/* 2071 */       WorldProvider worldprovider = this.mc.theWorld.provider;
/* 2072 */       Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);
/*      */       
/* 2074 */       if (object != null) {
/*      */         
/* 2076 */         Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.mc.theWorld, this.mc });
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 2081 */     float f5 = this.mc.theWorld.getRainStrength(partialTicks);
/*      */     
/* 2083 */     if (f5 > 0.0F) {
/*      */       
/* 2085 */       if (Config.isRainOff()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 2090 */       enableLightmap();
/* 2091 */       Entity entity = this.mc.getRenderViewEntity();
/* 2092 */       WorldClient worldClient = this.mc.theWorld;
/* 2093 */       int i = MathHelper.floor_double(entity.posX);
/* 2094 */       int j = MathHelper.floor_double(entity.posY);
/* 2095 */       int k = MathHelper.floor_double(entity.posZ);
/* 2096 */       Tessellator tessellator = Tessellator.getInstance();
/* 2097 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2098 */       GlStateManager.disableCull();
/* 2099 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 2100 */       GlStateManager.enableBlend();
/* 2101 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2102 */       GlStateManager.alphaFunc(516, 0.1F);
/* 2103 */       double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 2104 */       double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 2105 */       double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 2106 */       int l = MathHelper.floor_double(d1);
/* 2107 */       int i1 = 5;
/*      */       
/* 2109 */       if (Config.isRainFancy())
/*      */       {
/* 2111 */         i1 = 10;
/*      */       }
/*      */       
/* 2114 */       int j1 = -1;
/* 2115 */       float f = this.rendererUpdateCount + partialTicks;
/* 2116 */       worldrenderer.setTranslation(-d0, -d1, -d2);
/* 2117 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2118 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 2120 */       for (int k1 = k - i1; k1 <= k + i1; k1++) {
/*      */         
/* 2122 */         for (int l1 = i - i1; l1 <= i + i1; l1++) {
/*      */           
/* 2124 */           int i2 = (k1 - k + 16) * 32 + l1 - i + 16;
/* 2125 */           double d3 = this.rainXCoords[i2] * 0.5D;
/* 2126 */           double d4 = this.rainYCoords[i2] * 0.5D;
/* 2127 */           blockpos$mutableblockpos.func_181079_c(l1, 0, k1);
/* 2128 */           BiomeGenBase biomegenbase = worldClient.getBiomeGenForCoords((BlockPos)blockpos$mutableblockpos);
/*      */           
/* 2130 */           if (biomegenbase.canSpawnLightningBolt() || biomegenbase.getEnableSnow()) {
/*      */             
/* 2132 */             int j2 = worldClient.getPrecipitationHeight((BlockPos)blockpos$mutableblockpos).getY();
/* 2133 */             int k2 = j - i1;
/* 2134 */             int l2 = j + i1;
/*      */             
/* 2136 */             if (k2 < j2)
/*      */             {
/* 2138 */               k2 = j2;
/*      */             }
/*      */             
/* 2141 */             if (l2 < j2)
/*      */             {
/* 2143 */               l2 = j2;
/*      */             }
/*      */             
/* 2146 */             int i3 = j2;
/*      */             
/* 2148 */             if (j2 < l)
/*      */             {
/* 2150 */               i3 = l;
/*      */             }
/*      */             
/* 2153 */             if (k2 != l2) {
/*      */               
/* 2155 */               this.random.setSeed((l1 * l1 * 3121 + l1 * 45238971 ^ k1 * k1 * 418711 + k1 * 13761));
/* 2156 */               blockpos$mutableblockpos.func_181079_c(l1, k2, k1);
/* 2157 */               float f1 = biomegenbase.getFloatTemperature((BlockPos)blockpos$mutableblockpos);
/*      */               
/* 2159 */               if (worldClient.getWorldChunkManager().getTemperatureAtHeight(f1, j2) >= 0.15F) {
/*      */                 
/* 2161 */                 if (j1 != 0) {
/*      */                   
/* 2163 */                   if (j1 >= 0)
/*      */                   {
/* 2165 */                     tessellator.draw();
/*      */                   }
/*      */                   
/* 2168 */                   j1 = 0;
/* 2169 */                   this.mc.getTextureManager().bindTexture(locationRainPng);
/* 2170 */                   worldrenderer.begin(7, DefaultVertexFormats.field_181704_d);
/*      */                 } 
/*      */                 
/* 2173 */                 double d5 = ((this.rendererUpdateCount + l1 * l1 * 3121 + l1 * 45238971 + k1 * k1 * 418711 + k1 * 13761 & 0x1F) + partialTicks) / 32.0D * (3.0D + this.random.nextDouble());
/* 2174 */                 double d6 = (l1 + 0.5F) - entity.posX;
/* 2175 */                 double d7 = (k1 + 0.5F) - entity.posZ;
/* 2176 */                 float f2 = MathHelper.sqrt_double(d6 * d6 + d7 * d7) / i1;
/* 2177 */                 float f3 = ((1.0F - f2 * f2) * 0.5F + 0.5F) * f5;
/* 2178 */                 blockpos$mutableblockpos.func_181079_c(l1, i3, k1);
/* 2179 */                 int j3 = worldClient.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0);
/* 2180 */                 int k3 = j3 >> 16 & 0xFFFF;
/* 2181 */                 int l3 = j3 & 0xFFFF;
/* 2182 */                 worldrenderer.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).tex(0.0D, k2 * 0.25D + d5).func_181666_a(1.0F, 1.0F, 1.0F, f3).func_181671_a(k3, l3).endVertex();
/* 2183 */                 worldrenderer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D, k2 * 0.25D + d5).func_181666_a(1.0F, 1.0F, 1.0F, f3).func_181671_a(k3, l3).endVertex();
/* 2184 */                 worldrenderer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D, l2 * 0.25D + d5).func_181666_a(1.0F, 1.0F, 1.0F, f3).func_181671_a(k3, l3).endVertex();
/* 2185 */                 worldrenderer.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).tex(0.0D, l2 * 0.25D + d5).func_181666_a(1.0F, 1.0F, 1.0F, f3).func_181671_a(k3, l3).endVertex();
/*      */               }
/*      */               else {
/*      */                 
/* 2189 */                 if (j1 != 1) {
/*      */                   
/* 2191 */                   if (j1 >= 0)
/*      */                   {
/* 2193 */                     tessellator.draw();
/*      */                   }
/*      */                   
/* 2196 */                   j1 = 1;
/* 2197 */                   this.mc.getTextureManager().bindTexture(locationSnowPng);
/* 2198 */                   worldrenderer.begin(7, DefaultVertexFormats.field_181704_d);
/*      */                 } 
/*      */                 
/* 2201 */                 double d8 = (((this.rendererUpdateCount & 0x1FF) + partialTicks) / 512.0F);
/* 2202 */                 double d9 = this.random.nextDouble() + f * 0.01D * (float)this.random.nextGaussian();
/* 2203 */                 double d10 = this.random.nextDouble() + (f * (float)this.random.nextGaussian()) * 0.001D;
/* 2204 */                 double d11 = (l1 + 0.5F) - entity.posX;
/* 2205 */                 double d12 = (k1 + 0.5F) - entity.posZ;
/* 2206 */                 float f6 = MathHelper.sqrt_double(d11 * d11 + d12 * d12) / i1;
/* 2207 */                 float f4 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * f5;
/* 2208 */                 blockpos$mutableblockpos.func_181079_c(l1, i3, k1);
/* 2209 */                 int i4 = (worldClient.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
/* 2210 */                 int j4 = i4 >> 16 & 0xFFFF;
/* 2211 */                 int k4 = i4 & 0xFFFF;
/* 2212 */                 worldrenderer.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).tex(0.0D + d9, k2 * 0.25D + d8 + d10).func_181666_a(1.0F, 1.0F, 1.0F, f4).func_181671_a(j4, k4).endVertex();
/* 2213 */                 worldrenderer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D + d9, k2 * 0.25D + d8 + d10).func_181666_a(1.0F, 1.0F, 1.0F, f4).func_181671_a(j4, k4).endVertex();
/* 2214 */                 worldrenderer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D + d9, l2 * 0.25D + d8 + d10).func_181666_a(1.0F, 1.0F, 1.0F, f4).func_181671_a(j4, k4).endVertex();
/* 2215 */                 worldrenderer.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).tex(0.0D + d9, l2 * 0.25D + d8 + d10).func_181666_a(1.0F, 1.0F, 1.0F, f4).func_181671_a(j4, k4).endVertex();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2222 */       if (j1 >= 0)
/*      */       {
/* 2224 */         tessellator.draw();
/*      */       }
/*      */       
/* 2227 */       worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 2228 */       GlStateManager.enableCull();
/* 2229 */       GlStateManager.disableBlend();
/* 2230 */       GlStateManager.alphaFunc(516, 0.1F);
/* 2231 */       disableLightmap();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupOverlayRendering() {
/* 2240 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 2241 */     GlStateManager.clear(256);
/* 2242 */     GlStateManager.matrixMode(5889);
/* 2243 */     GlStateManager.loadIdentity();
/* 2244 */     GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
/* 2245 */     GlStateManager.matrixMode(5888);
/* 2246 */     GlStateManager.loadIdentity();
/* 2247 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateFogColor(float partialTicks) {
/* 2255 */     WorldClient worldClient = this.mc.theWorld;
/* 2256 */     Entity entity = this.mc.getRenderViewEntity();
/* 2257 */     float f = 0.25F + 0.75F * this.mc.gameSettings.renderDistanceChunks / 32.0F;
/* 2258 */     f = 1.0F - (float)Math.pow(f, 0.25D);
/* 2259 */     Vec3 vec3 = worldClient.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
/* 2260 */     vec3 = CustomColors.getWorldSkyColor(vec3, (World)worldClient, this.mc.getRenderViewEntity(), partialTicks);
/* 2261 */     float f1 = (float)vec3.xCoord;
/* 2262 */     float f2 = (float)vec3.yCoord;
/* 2263 */     float f3 = (float)vec3.zCoord;
/* 2264 */     Vec3 vec31 = worldClient.getFogColor(partialTicks);
/* 2265 */     vec31 = CustomColors.getWorldFogColor(vec31, (World)worldClient, this.mc.getRenderViewEntity(), partialTicks);
/* 2266 */     this.fogColorRed = (float)vec31.xCoord;
/* 2267 */     this.fogColorGreen = (float)vec31.yCoord;
/* 2268 */     this.fogColorBlue = (float)vec31.zCoord;
/*      */     
/* 2270 */     if (this.mc.gameSettings.renderDistanceChunks >= 4) {
/*      */       
/* 2272 */       double d0 = -1.0D;
/* 2273 */       Vec3 vec32 = (MathHelper.sin(worldClient.getCelestialAngleRadians(partialTicks)) > 0.0F) ? new Vec3(d0, 0.0D, 0.0D) : new Vec3(1.0D, 0.0D, 0.0D);
/* 2274 */       float f5 = (float)entity.getLook(partialTicks).dotProduct(vec32);
/*      */       
/* 2276 */       if (f5 < 0.0F)
/*      */       {
/* 2278 */         f5 = 0.0F;
/*      */       }
/*      */       
/* 2281 */       if (f5 > 0.0F) {
/*      */         
/* 2283 */         float[] afloat = ((World)worldClient).provider.calcSunriseSunsetColors(worldClient.getCelestialAngle(partialTicks), partialTicks);
/*      */         
/* 2285 */         if (afloat != null) {
/*      */           
/* 2287 */           f5 *= afloat[3];
/* 2288 */           this.fogColorRed = this.fogColorRed * (1.0F - f5) + afloat[0] * f5;
/* 2289 */           this.fogColorGreen = this.fogColorGreen * (1.0F - f5) + afloat[1] * f5;
/* 2290 */           this.fogColorBlue = this.fogColorBlue * (1.0F - f5) + afloat[2] * f5;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2295 */     this.fogColorRed += (f1 - this.fogColorRed) * f;
/* 2296 */     this.fogColorGreen += (f2 - this.fogColorGreen) * f;
/* 2297 */     this.fogColorBlue += (f3 - this.fogColorBlue) * f;
/* 2298 */     float f8 = worldClient.getRainStrength(partialTicks);
/*      */     
/* 2300 */     if (f8 > 0.0F) {
/*      */       
/* 2302 */       float f4 = 1.0F - f8 * 0.5F;
/* 2303 */       float f10 = 1.0F - f8 * 0.4F;
/* 2304 */       this.fogColorRed *= f4;
/* 2305 */       this.fogColorGreen *= f4;
/* 2306 */       this.fogColorBlue *= f10;
/*      */     } 
/*      */     
/* 2309 */     float f9 = worldClient.getThunderStrength(partialTicks);
/*      */     
/* 2311 */     if (f9 > 0.0F) {
/*      */       
/* 2313 */       float f11 = 1.0F - f9 * 0.5F;
/* 2314 */       this.fogColorRed *= f11;
/* 2315 */       this.fogColorGreen *= f11;
/* 2316 */       this.fogColorBlue *= f11;
/*      */     } 
/*      */     
/* 2319 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/*      */     
/* 2321 */     if (this.cloudFog) {
/*      */       
/* 2323 */       Vec3 vec33 = worldClient.getCloudColour(partialTicks);
/* 2324 */       this.fogColorRed = (float)vec33.xCoord;
/* 2325 */       this.fogColorGreen = (float)vec33.yCoord;
/* 2326 */       this.fogColorBlue = (float)vec33.zCoord;
/*      */     }
/* 2328 */     else if (block.getMaterial() == Material.water) {
/*      */       
/* 2330 */       float f12 = EnchantmentHelper.getRespiration(entity) * 0.2F;
/* 2331 */       f12 = Config.limit(f12, 0.0F, 0.6F);
/*      */       
/* 2333 */       if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing))
/*      */       {
/* 2335 */         f12 = f12 * 0.3F + 0.6F;
/*      */       }
/*      */       
/* 2338 */       this.fogColorRed = 0.02F + f12;
/* 2339 */       this.fogColorGreen = 0.02F + f12;
/* 2340 */       this.fogColorBlue = 0.2F + f12;
/* 2341 */       Vec3 vec35 = CustomColors.getUnderwaterColor((IBlockAccess)this.mc.theWorld, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 2343 */       if (vec35 != null)
/*      */       {
/* 2345 */         this.fogColorRed = (float)vec35.xCoord;
/* 2346 */         this.fogColorGreen = (float)vec35.yCoord;
/* 2347 */         this.fogColorBlue = (float)vec35.zCoord;
/*      */       }
/*      */     
/* 2350 */     } else if (block.getMaterial() == Material.lava) {
/*      */       
/* 2352 */       this.fogColorRed = 0.6F;
/* 2353 */       this.fogColorGreen = 0.1F;
/* 2354 */       this.fogColorBlue = 0.0F;
/* 2355 */       Vec3 vec34 = CustomColors.getUnderlavaColor((IBlockAccess)this.mc.theWorld, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 2357 */       if (vec34 != null) {
/*      */         
/* 2359 */         this.fogColorRed = (float)vec34.xCoord;
/* 2360 */         this.fogColorGreen = (float)vec34.yCoord;
/* 2361 */         this.fogColorBlue = (float)vec34.zCoord;
/*      */       } 
/*      */     } 
/*      */     
/* 2365 */     float f13 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * partialTicks;
/* 2366 */     this.fogColorRed *= f13;
/* 2367 */     this.fogColorGreen *= f13;
/* 2368 */     this.fogColorBlue *= f13;
/* 2369 */     double d1 = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) * ((World)worldClient).provider.getVoidFogYFactor();
/*      */     
/* 2371 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.blindness)) {
/*      */       
/* 2373 */       int i = ((EntityLivingBase)entity).getActivePotionEffect(Potion.blindness).getDuration();
/*      */       
/* 2375 */       if (i < 20) {
/*      */         
/* 2377 */         d1 *= (1.0F - i / 20.0F);
/*      */       }
/*      */       else {
/*      */         
/* 2381 */         d1 = 0.0D;
/*      */       } 
/*      */     } 
/*      */     
/* 2385 */     if (d1 < 1.0D) {
/*      */       
/* 2387 */       if (d1 < 0.0D)
/*      */       {
/* 2389 */         d1 = 0.0D;
/*      */       }
/*      */       
/* 2392 */       d1 *= d1;
/* 2393 */       this.fogColorRed = (float)(this.fogColorRed * d1);
/* 2394 */       this.fogColorGreen = (float)(this.fogColorGreen * d1);
/* 2395 */       this.fogColorBlue = (float)(this.fogColorBlue * d1);
/*      */     } 
/*      */     
/* 2398 */     if (this.bossColorModifier > 0.0F) {
/*      */       
/* 2400 */       float f14 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
/* 2401 */       this.fogColorRed = this.fogColorRed * (1.0F - f14) + this.fogColorRed * 0.7F * f14;
/* 2402 */       this.fogColorGreen = this.fogColorGreen * (1.0F - f14) + this.fogColorGreen * 0.6F * f14;
/* 2403 */       this.fogColorBlue = this.fogColorBlue * (1.0F - f14) + this.fogColorBlue * 0.6F * f14;
/*      */     } 
/*      */     
/* 2406 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.nightVision)) {
/*      */       
/* 2408 */       float f15 = getNightVisionBrightness((EntityLivingBase)entity, partialTicks);
/* 2409 */       float f6 = 1.0F / this.fogColorRed;
/*      */       
/* 2411 */       if (f6 > 1.0F / this.fogColorGreen)
/*      */       {
/* 2413 */         f6 = 1.0F / this.fogColorGreen;
/*      */       }
/*      */       
/* 2416 */       if (f6 > 1.0F / this.fogColorBlue)
/*      */       {
/* 2418 */         f6 = 1.0F / this.fogColorBlue;
/*      */       }
/*      */       
/* 2421 */       if (Float.isInfinite(f6))
/*      */       {
/* 2423 */         f6 = Math.nextAfter(f6, 0.0D);
/*      */       }
/*      */       
/* 2426 */       this.fogColorRed = this.fogColorRed * (1.0F - f15) + this.fogColorRed * f6 * f15;
/* 2427 */       this.fogColorGreen = this.fogColorGreen * (1.0F - f15) + this.fogColorGreen * f6 * f15;
/* 2428 */       this.fogColorBlue = this.fogColorBlue * (1.0F - f15) + this.fogColorBlue * f6 * f15;
/*      */     } 
/*      */     
/* 2431 */     if (this.mc.gameSettings.anaglyph) {
/*      */       
/* 2433 */       float f16 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
/* 2434 */       float f17 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
/* 2435 */       float f7 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
/* 2436 */       this.fogColorRed = f16;
/* 2437 */       this.fogColorGreen = f17;
/* 2438 */       this.fogColorBlue = f7;
/*      */     } 
/*      */     
/* 2441 */     if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
/*      */       
/* 2443 */       Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(this.fogColorRed), Float.valueOf(this.fogColorGreen), Float.valueOf(this.fogColorBlue) });
/* 2444 */       Reflector.postForgeBusEvent(object);
/* 2445 */       this.fogColorRed = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_red, this.fogColorRed);
/* 2446 */       this.fogColorGreen = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_green, this.fogColorGreen);
/* 2447 */       this.fogColorBlue = Reflector.getFieldValueFloat(object, Reflector.EntityViewRenderEvent_FogColors_blue, this.fogColorBlue);
/*      */     } 
/*      */     
/* 2450 */     Shaders.setClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupFog(int p_78468_1_, float partialTicks) {
/* 2459 */     this.fogStandard = false;
/* 2460 */     Entity entity = this.mc.getRenderViewEntity();
/* 2461 */     boolean flag = false;
/*      */     
/* 2463 */     if (entity instanceof EntityPlayer)
/*      */     {
/* 2465 */       flag = ((EntityPlayer)entity).capabilities.isCreativeMode;
/*      */     }
/*      */     
/* 2468 */     GL11.glFog(2918, setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
/* 2469 */     GL11.glNormal3f(0.0F, -1.0F, 0.0F);
/* 2470 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2471 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)this.mc.theWorld, entity, partialTicks);
/* 2472 */     float f = -1.0F;
/*      */     
/* 2474 */     if (Reflector.ForgeHooksClient_getFogDensity.exists())
/*      */     {
/* 2476 */       f = Reflector.callFloat(Reflector.ForgeHooksClient_getFogDensity, new Object[] { this, entity, block, Float.valueOf(partialTicks), Float.valueOf(0.1F) });
/*      */     }
/*      */     
/* 2479 */     if (f >= 0.0F) {
/*      */       
/* 2481 */       GlStateManager.setFogDensity(f);
/*      */     }
/* 2483 */     else if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.blindness)) {
/*      */       
/* 2485 */       float f4 = 5.0F;
/* 2486 */       int i = ((EntityLivingBase)entity).getActivePotionEffect(Potion.blindness).getDuration();
/*      */       
/* 2488 */       if (i < 20)
/*      */       {
/* 2490 */         f4 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - i / 20.0F);
/*      */       }
/*      */       
/* 2493 */       GlStateManager.setFog(9729);
/*      */       
/* 2495 */       if (p_78468_1_ == -1) {
/*      */         
/* 2497 */         GlStateManager.setFogStart(0.0F);
/* 2498 */         GlStateManager.setFogEnd(f4 * 0.8F);
/*      */       }
/*      */       else {
/*      */         
/* 2502 */         GlStateManager.setFogStart(f4 * 0.25F);
/* 2503 */         GlStateManager.setFogEnd(f4);
/*      */       } 
/*      */       
/* 2506 */       if ((GLContext.getCapabilities()).GL_NV_fog_distance && Config.isFogFancy())
/*      */       {
/* 2508 */         GL11.glFogi(34138, 34139);
/*      */       }
/*      */     }
/* 2511 */     else if (this.cloudFog) {
/*      */       
/* 2513 */       GlStateManager.setFog(2048);
/* 2514 */       GlStateManager.setFogDensity(0.1F);
/*      */     }
/* 2516 */     else if (block.getMaterial() == Material.water) {
/*      */       
/* 2518 */       GlStateManager.setFog(2048);
/* 2519 */       float f1 = Config.isClearWater() ? 0.02F : 0.1F;
/*      */       
/* 2521 */       if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(Potion.waterBreathing))
/*      */       {
/* 2523 */         GlStateManager.setFogDensity(0.01F);
/*      */       }
/*      */       else
/*      */       {
/* 2527 */         float f2 = 0.1F - EnchantmentHelper.getRespiration(entity) * 0.03F;
/* 2528 */         GlStateManager.setFogDensity(Config.limit(f2, 0.0F, f1));
/*      */       }
/*      */     
/* 2531 */     } else if (block.getMaterial() == Material.lava) {
/*      */       
/* 2533 */       GlStateManager.setFog(2048);
/* 2534 */       GlStateManager.setFogDensity(2.0F);
/*      */     }
/*      */     else {
/*      */       
/* 2538 */       float f3 = this.farPlaneDistance;
/* 2539 */       this.fogStandard = true;
/* 2540 */       GlStateManager.setFog(9729);
/*      */       
/* 2542 */       if (p_78468_1_ == -1) {
/*      */         
/* 2544 */         GlStateManager.setFogStart(0.0F);
/* 2545 */         GlStateManager.setFogEnd(f3);
/*      */       }
/*      */       else {
/*      */         
/* 2549 */         GlStateManager.setFogStart(f3 * Config.getFogStart());
/* 2550 */         GlStateManager.setFogEnd(f3);
/*      */       } 
/*      */       
/* 2553 */       if ((GLContext.getCapabilities()).GL_NV_fog_distance) {
/*      */         
/* 2555 */         if (Config.isFogFancy())
/*      */         {
/* 2557 */           GL11.glFogi(34138, 34139);
/*      */         }
/*      */         
/* 2560 */         if (Config.isFogFast())
/*      */         {
/* 2562 */           GL11.glFogi(34138, 34140);
/*      */         }
/*      */       } 
/*      */       
/* 2566 */       if (this.mc.theWorld.provider.doesXZShowFog((int)entity.posX, (int)entity.posZ)) {
/*      */         
/* 2568 */         GlStateManager.setFogStart(f3 * 0.05F);
/* 2569 */         GlStateManager.setFogEnd(f3);
/*      */       } 
/*      */       
/* 2572 */       if (Reflector.ForgeHooksClient_onFogRender.exists())
/*      */       {
/* 2574 */         Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, new Object[] { this, entity, block, Float.valueOf(partialTicks), Integer.valueOf(p_78468_1_), Float.valueOf(f3) });
/*      */       }
/*      */     } 
/*      */     
/* 2578 */     GlStateManager.enableColorMaterial();
/* 2579 */     GlStateManager.enableFog();
/* 2580 */     GlStateManager.colorMaterial(1028, 4608);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FloatBuffer setFogColorBuffer(float red, float green, float blue, float alpha) {
/* 2588 */     if (Config.isShaders())
/*      */     {
/* 2590 */       Shaders.setFogColor(red, green, blue);
/*      */     }
/*      */     
/* 2593 */     this.fogColorBuffer.clear();
/* 2594 */     this.fogColorBuffer.put(red).put(green).put(blue).put(alpha);
/* 2595 */     this.fogColorBuffer.flip();
/* 2596 */     return this.fogColorBuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public MapItemRenderer getMapItemRenderer() {
/* 2601 */     return this.theMapItemRenderer;
/*      */   }
/*      */ 
/*      */   
/*      */   private void waitForServerThread() {
/* 2606 */     this.serverWaitTimeCurrent = 0;
/*      */     
/* 2608 */     if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
/*      */       
/* 2610 */       if (this.mc.isIntegratedServerRunning()) {
/*      */         
/* 2612 */         IntegratedServer integratedserver = this.mc.getIntegratedServer();
/*      */         
/* 2614 */         if (integratedserver != null) {
/*      */           
/* 2616 */           boolean flag = this.mc.isGamePaused();
/*      */           
/* 2618 */           if (!flag && !(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiDownloadTerrain)) {
/*      */             
/* 2620 */             if (this.serverWaitTime > 0) {
/*      */               
/* 2622 */               Lagometer.timerServer.start();
/* 2623 */               Config.sleep(this.serverWaitTime);
/* 2624 */               Lagometer.timerServer.end();
/* 2625 */               this.serverWaitTimeCurrent = this.serverWaitTime;
/*      */             } 
/*      */             
/* 2628 */             long i = System.nanoTime() / 1000000L;
/*      */             
/* 2630 */             if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
/*      */               
/* 2632 */               long j = i - this.lastServerTime;
/*      */               
/* 2634 */               if (j < 0L) {
/*      */                 
/* 2636 */                 this.lastServerTime = i;
/* 2637 */                 j = 0L;
/*      */               } 
/*      */               
/* 2640 */               if (j >= 50L)
/*      */               {
/* 2642 */                 this.lastServerTime = i;
/* 2643 */                 int k = integratedserver.getTickCounter();
/* 2644 */                 int l = k - this.lastServerTicks;
/*      */                 
/* 2646 */                 if (l < 0) {
/*      */                   
/* 2648 */                   this.lastServerTicks = k;
/* 2649 */                   l = 0;
/*      */                 } 
/*      */                 
/* 2652 */                 if (l < 1 && this.serverWaitTime < 100)
/*      */                 {
/* 2654 */                   this.serverWaitTime += 2;
/*      */                 }
/*      */                 
/* 2657 */                 if (l > 1 && this.serverWaitTime > 0)
/*      */                 {
/* 2659 */                   this.serverWaitTime--;
/*      */                 }
/*      */                 
/* 2662 */                 this.lastServerTicks = k;
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/* 2667 */               this.lastServerTime = i;
/* 2668 */               this.lastServerTicks = integratedserver.getTickCounter();
/* 2669 */               this.avgServerTickDiff = 1.0F;
/* 2670 */               this.avgServerTimeDiff = 50.0F;
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 2675 */             if (this.mc.currentScreen instanceof net.minecraft.client.gui.GuiDownloadTerrain)
/*      */             {
/* 2677 */               Config.sleep(20L);
/*      */             }
/*      */             
/* 2680 */             this.lastServerTime = 0L;
/* 2681 */             this.lastServerTicks = 0;
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 2688 */       this.lastServerTime = 0L;
/* 2689 */       this.lastServerTicks = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void frameInit() {
/* 2695 */     GlErrors.frameStart();
/*      */     
/* 2697 */     if (!this.initialized) {
/*      */       
/* 2699 */       TextureUtils.registerResourceListener();
/*      */       
/* 2701 */       if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32)
/*      */       {
/* 2703 */         Config.setNotify64BitJava(true);
/*      */       }
/*      */       
/* 2706 */       this.initialized = true;
/*      */     } 
/*      */     
/* 2709 */     Config.checkDisplayMode();
/* 2710 */     WorldClient worldClient = this.mc.theWorld;
/*      */     
/* 2712 */     if (worldClient != null)
/*      */     {
/*      */       
/* 2715 */       if (Config.isNotify64BitJava()) {
/*      */         
/* 2717 */         Config.setNotify64BitJava(false);
/* 2718 */         ChatComponentText chatcomponenttext1 = new ChatComponentText(I18n.format("of.message.java64Bit", new Object[0]));
/* 2719 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext1);
/*      */       } 
/*      */     }
/*      */     
/* 2723 */     if (this.mc.currentScreen instanceof GuiMainMenu)
/*      */     {
/* 2725 */       updateMainMenu((GuiMainMenu)this.mc.currentScreen);
/*      */     }
/*      */     
/* 2728 */     if (this.updatedWorld != worldClient) {
/*      */       
/* 2730 */       RandomEntities.worldChanged(this.updatedWorld, (World)worldClient);
/* 2731 */       Config.updateThreadPriorities();
/* 2732 */       this.lastServerTime = 0L;
/* 2733 */       this.lastServerTicks = 0;
/* 2734 */       this.updatedWorld = (World)worldClient;
/*      */     } 
/*      */     
/* 2737 */     if (!setFxaaShader(Shaders.configAntialiasingLevel))
/*      */     {
/* 2739 */       Shaders.configAntialiasingLevel = 0;
/*      */     }
/*      */     
/* 2742 */     if (this.mc.currentScreen != null && this.mc.currentScreen.getClass() == GuiChat.class)
/*      */     {
/* 2744 */       this.mc.displayGuiScreen((GuiScreen)new GuiChatOF((GuiChat)this.mc.currentScreen));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void frameFinish() {
/* 2750 */     if (this.mc.theWorld != null && Config.isShowGlErrors() && TimedEvent.isActive("CheckGlErrorFrameFinish", 10000L)) {
/*      */       
/* 2752 */       int i = GlStateManager.glGetError();
/*      */       
/* 2754 */       if (i != 0 && GlErrors.isEnabled(i)) {
/*      */         
/* 2756 */         String s = Config.getGlErrorString(i);
/* 2757 */         ChatComponentText chatcomponenttext = new ChatComponentText(I18n.format("of.message.openglError", new Object[] { Integer.valueOf(i), s }));
/* 2758 */         this.mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)chatcomponenttext);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateMainMenu(GuiMainMenu p_updateMainMenu_1_) {
/*      */     try {
/* 2767 */       String s = null;
/* 2768 */       Calendar calendar = Calendar.getInstance();
/* 2769 */       calendar.setTime(new Date());
/* 2770 */       int i = calendar.get(5);
/* 2771 */       int j = calendar.get(2) + 1;
/*      */       
/* 2773 */       if (i == 8 && j == 4)
/*      */       {
/* 2775 */         s = "Happy birthday, OptiFine!";
/*      */       }
/*      */       
/* 2778 */       if (i == 14 && j == 8)
/*      */       {
/* 2780 */         s = "Happy birthday, sp614x!";
/*      */       }
/*      */       
/* 2783 */       if (s == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 2788 */       Reflector.setFieldValue(p_updateMainMenu_1_, Reflector.GuiMainMenu_splashText, s);
/*      */     }
/* 2790 */     catch (Throwable throwable) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setFxaaShader(int p_setFxaaShader_1_) {
/* 2798 */     if (!OpenGlHelper.isFramebufferEnabled())
/*      */     {
/* 2800 */       return false;
/*      */     }
/* 2802 */     if (this.theShaderGroup != null && this.theShaderGroup != this.fxaaShaders[2] && this.theShaderGroup != this.fxaaShaders[4])
/*      */     {
/* 2804 */       return true;
/*      */     }
/* 2806 */     if (p_setFxaaShader_1_ != 2 && p_setFxaaShader_1_ != 4) {
/*      */       
/* 2808 */       if (this.theShaderGroup == null)
/*      */       {
/* 2810 */         return true;
/*      */       }
/*      */ 
/*      */       
/* 2814 */       this.theShaderGroup.deleteShaderGroup();
/* 2815 */       this.theShaderGroup = null;
/* 2816 */       return true;
/*      */     } 
/*      */     
/* 2819 */     if (this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[p_setFxaaShader_1_])
/*      */     {
/* 2821 */       return true;
/*      */     }
/* 2823 */     if (this.mc.theWorld == null)
/*      */     {
/* 2825 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2829 */     loadShader(new ResourceLocation("shaders/post/fxaa_of_" + p_setFxaaShader_1_ + "x.json"));
/* 2830 */     this.fxaaShaders[p_setFxaaShader_1_] = this.theShaderGroup;
/* 2831 */     return this.useShader;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkLoadVisibleChunks(Entity p_checkLoadVisibleChunks_1_, float p_checkLoadVisibleChunks_2_, ICamera p_checkLoadVisibleChunks_3_, boolean p_checkLoadVisibleChunks_4_) {
/* 2837 */     int i = 201435902;
/*      */     
/* 2839 */     if (this.loadVisibleChunks) {
/*      */       
/* 2841 */       this.loadVisibleChunks = false;
/* 2842 */       loadAllVisibleChunks(p_checkLoadVisibleChunks_1_, p_checkLoadVisibleChunks_2_, p_checkLoadVisibleChunks_3_, p_checkLoadVisibleChunks_4_);
/* 2843 */       this.mc.ingameGUI.getChatGUI().deleteChatLine(i);
/*      */     } 
/*      */     
/* 2846 */     if (Keyboard.isKeyDown(61) && Keyboard.isKeyDown(38)) {
/*      */       
/* 2848 */       if (this.mc.currentScreen != null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 2853 */       this.loadVisibleChunks = true;
/* 2854 */       ChatComponentText chatcomponenttext = new ChatComponentText(I18n.format("of.message.loadingVisibleChunks", new Object[0]));
/* 2855 */       this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((IChatComponent)chatcomponenttext, i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadAllVisibleChunks(Entity p_loadAllVisibleChunks_1_, double p_loadAllVisibleChunks_2_, ICamera p_loadAllVisibleChunks_4_, boolean p_loadAllVisibleChunks_5_) {
/* 2861 */     int i = this.mc.gameSettings.ofChunkUpdates;
/* 2862 */     boolean flag = this.mc.gameSettings.ofLazyChunkLoading;
/*      */ 
/*      */     
/*      */     try {
/* 2866 */       this.mc.gameSettings.ofChunkUpdates = 1000;
/* 2867 */       this.mc.gameSettings.ofLazyChunkLoading = false;
/* 2868 */       RenderGlobal renderglobal = Config.getRenderGlobal();
/* 2869 */       int j = renderglobal.getCountLoadedChunks();
/* 2870 */       long k = System.currentTimeMillis();
/* 2871 */       Config.dbg("Loading visible chunks");
/* 2872 */       long l = System.currentTimeMillis() + 5000L;
/* 2873 */       int i1 = 0;
/* 2874 */       boolean flag1 = false;
/*      */ 
/*      */       
/*      */       do {
/* 2878 */         flag1 = false;
/*      */         
/* 2880 */         for (int j1 = 0; j1 < 100; j1++) {
/*      */           
/* 2882 */           renderglobal.displayListEntitiesDirty = true;
/* 2883 */           renderglobal.setupTerrain(p_loadAllVisibleChunks_1_, p_loadAllVisibleChunks_2_, p_loadAllVisibleChunks_4_, this.frameCount++, p_loadAllVisibleChunks_5_);
/*      */           
/* 2885 */           if (!renderglobal.hasNoChunkUpdates())
/*      */           {
/* 2887 */             flag1 = true;
/*      */           }
/*      */           
/* 2890 */           i1 += renderglobal.getCountChunksToUpdate();
/*      */           
/* 2892 */           while (!renderglobal.hasNoChunkUpdates())
/*      */           {
/* 2894 */             renderglobal.updateChunks(System.nanoTime() + 1000000000L);
/*      */           }
/*      */           
/* 2897 */           i1 -= renderglobal.getCountChunksToUpdate();
/*      */           
/* 2899 */           if (!flag1) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 2905 */         if (renderglobal.getCountLoadedChunks() != j) {
/*      */           
/* 2907 */           flag1 = true;
/* 2908 */           j = renderglobal.getCountLoadedChunks();
/*      */         } 
/*      */         
/* 2911 */         if (System.currentTimeMillis() <= l)
/*      */           continue; 
/* 2913 */         Config.log("Chunks loaded: " + i1);
/* 2914 */         l = System.currentTimeMillis() + 5000L;
/*      */       
/*      */       }
/* 2917 */       while (flag1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2923 */       Config.log("Chunks loaded: " + i1);
/* 2924 */       Config.log("Finished loading visible chunks");
/* 2925 */       RenderChunk.renderChunksUpdated = 0;
/*      */     }
/*      */     finally {
/*      */       
/* 2929 */       this.mc.gameSettings.ofChunkUpdates = i;
/* 2930 */       this.mc.gameSettings.ofLazyChunkLoading = flag;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\EntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */