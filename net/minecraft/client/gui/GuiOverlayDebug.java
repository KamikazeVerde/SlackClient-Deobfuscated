/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.SmartAnimations;
/*     */ import net.optifine.TextureAnimations;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.NativeMemory;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiOverlayDebug extends Gui {
/*     */   private final Minecraft mc;
/*  39 */   private String debugOF = null;
/*     */   private final FontRenderer fontRenderer;
/*     */   
/*     */   public GuiOverlayDebug(Minecraft mc) {
/*  43 */     this.mc = mc;
/*  44 */     this.fontRenderer = mc.MCfontRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderDebugInfo(ScaledResolution scaledResolutionIn) {
/*  49 */     this.mc.mcProfiler.startSection("debug");
/*  50 */     GlStateManager.pushMatrix();
/*  51 */     renderDebugInfoLeft();
/*  52 */     renderDebugInfoRight(scaledResolutionIn);
/*  53 */     GlStateManager.popMatrix();
/*     */     
/*  55 */     if (this.mc.gameSettings.field_181657_aC)
/*     */     {
/*  57 */       func_181554_e();
/*     */     }
/*     */     
/*  60 */     this.mc.mcProfiler.endSection();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isReducedDebug() {
/*  65 */     return (this.mc.thePlayer.hasReducedDebug() || this.mc.gameSettings.reducedDebugInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderDebugInfoLeft() {
/*  70 */     List<String> list = call();
/*     */     
/*  72 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/*  74 */       String s = list.get(i);
/*     */       
/*  76 */       if (!Strings.isNullOrEmpty(s)) {
/*     */         
/*  78 */         int j = this.fontRenderer.FONT_HEIGHT;
/*  79 */         int k = this.fontRenderer.getStringWidth(s);
/*  80 */         int l = 2;
/*  81 */         int i1 = 2 + j * i;
/*  82 */         drawRect(1, i1 - 1, 2 + k + 1, i1 + j - 1, -1873784752);
/*  83 */         this.fontRenderer.drawString(s, 2, i1, 14737632);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderDebugInfoRight(ScaledResolution p_175239_1_) {
/*  90 */     List<String> list = getDebugInfoRight();
/*     */     
/*  92 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/*  94 */       String s = list.get(i);
/*     */       
/*  96 */       if (!Strings.isNullOrEmpty(s)) {
/*     */         
/*  98 */         int j = this.fontRenderer.FONT_HEIGHT;
/*  99 */         int k = this.fontRenderer.getStringWidth(s);
/* 100 */         int l = p_175239_1_.getScaledWidth() - 2 - k;
/* 101 */         int i1 = 2 + j * i;
/* 102 */         drawRect(l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
/* 103 */         this.fontRenderer.drawString(s, l, i1, 14737632);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> call() {
/* 111 */     BlockPos blockpos = new BlockPos((this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity().getEntityBoundingBox()).minY, (this.mc.getRenderViewEntity()).posZ);
/*     */     
/* 113 */     if (this.mc.debug != this.debugOF) {
/*     */       
/* 115 */       StringBuffer stringbuffer = new StringBuffer(this.mc.debug);
/* 116 */       int i = Config.getFpsMin();
/* 117 */       int j = this.mc.debug.indexOf(" fps ");
/*     */       
/* 119 */       if (j >= 0)
/*     */       {
/* 121 */         stringbuffer.insert(j, "/" + i);
/*     */       }
/*     */       
/* 124 */       if (Config.isSmoothFps())
/*     */       {
/* 126 */         stringbuffer.append(" sf");
/*     */       }
/*     */       
/* 129 */       if (Config.isFastRender())
/*     */       {
/* 131 */         stringbuffer.append(" fr");
/*     */       }
/*     */       
/* 134 */       if (Config.isAnisotropicFiltering())
/*     */       {
/* 136 */         stringbuffer.append(" af");
/*     */       }
/*     */       
/* 139 */       if (Config.isAntialiasing())
/*     */       {
/* 141 */         stringbuffer.append(" aa");
/*     */       }
/*     */       
/* 144 */       if (Config.isRenderRegions())
/*     */       {
/* 146 */         stringbuffer.append(" reg");
/*     */       }
/*     */       
/* 149 */       if (Config.isShaders())
/*     */       {
/* 151 */         stringbuffer.append(" sh");
/*     */       }
/*     */       
/* 154 */       this.mc.debug = stringbuffer.toString();
/* 155 */       this.debugOF = this.mc.debug;
/*     */     } 
/*     */     
/* 158 */     StringBuilder stringbuilder = new StringBuilder();
/* 159 */     TextureMap texturemap = Config.getTextureMap();
/* 160 */     stringbuilder.append(", A: ");
/*     */     
/* 162 */     if (SmartAnimations.isActive()) {
/*     */       
/* 164 */       stringbuilder.append(texturemap.getCountAnimationsActive() + TextureAnimations.getCountAnimationsActive());
/* 165 */       stringbuilder.append("/");
/*     */     } 
/*     */     
/* 168 */     stringbuilder.append(texturemap.getCountAnimations() + TextureAnimations.getCountAnimations());
/* 169 */     String s1 = stringbuilder.toString();
/*     */     
/* 171 */     if (isReducedDebug())
/*     */     {
/* 173 */       return Lists.newArrayList((Object[])new String[] { "Minecraft 1.8.9 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.theWorld.getDebugLoadedEntities() + s1, this.mc.theWorld.getProviderName(), "", String.format("Chunk-relative: %d %d %d", new Object[] { Integer.valueOf(blockpos.getX() & 0xF), Integer.valueOf(blockpos.getY() & 0xF), Integer.valueOf(blockpos.getZ() & 0xF) }) });
/*     */     }
/*     */ 
/*     */     
/* 177 */     Entity entity = this.mc.getRenderViewEntity();
/* 178 */     EnumFacing enumfacing = entity.getHorizontalFacing();
/* 179 */     String s = "Invalid";
/*     */     
/* 181 */     switch (enumfacing) {
/*     */       
/*     */       case NORTH:
/* 184 */         s = "Towards negative Z";
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 188 */         s = "Towards positive Z";
/*     */         break;
/*     */       
/*     */       case WEST:
/* 192 */         s = "Towards negative X";
/*     */         break;
/*     */       
/*     */       case EAST:
/* 196 */         s = "Towards positive X";
/*     */         break;
/*     */     } 
/* 199 */     List<String> list = Lists.newArrayList((Object[])new String[] { "Minecraft 1.8.9 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.theWorld.getDebugLoadedEntities() + s1, this.mc.theWorld.getProviderName(), "", String.format("XYZ: %.3f / %.5f / %.3f", new Object[] { Double.valueOf((this.mc.getRenderViewEntity()).posX), Double.valueOf((this.mc.getRenderViewEntity().getEntityBoundingBox()).minY), Double.valueOf((this.mc.getRenderViewEntity()).posZ) }), String.format("Block: %d %d %d", new Object[] { Integer.valueOf(blockpos.getX()), Integer.valueOf(blockpos.getY()), Integer.valueOf(blockpos.getZ()) }), String.format("Chunk: %d %d %d in %d %d %d", new Object[] { Integer.valueOf(blockpos.getX() & 0xF), Integer.valueOf(blockpos.getY() & 0xF), Integer.valueOf(blockpos.getZ() & 0xF), Integer.valueOf(blockpos.getX() >> 4), Integer.valueOf(blockpos.getY() >> 4), Integer.valueOf(blockpos.getZ() >> 4) }), String.format("Facing: %s (%s) (%.1f / %.1f)", new Object[] { enumfacing, s, Float.valueOf(MathHelper.wrapAngleTo180_float(entity.rotationYaw)), Float.valueOf(MathHelper.wrapAngleTo180_float(entity.rotationPitch)) }) });
/*     */     
/* 201 */     if (this.mc.theWorld != null && this.mc.theWorld.isBlockLoaded(blockpos)) {
/*     */       
/* 203 */       Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(blockpos);
/* 204 */       list.add("Biome: " + (chunk.getBiome(blockpos, this.mc.theWorld.getWorldChunkManager())).biomeName);
/* 205 */       list.add("Light: " + chunk.getLightSubtracted(blockpos, 0) + " (" + chunk.getLightFor(EnumSkyBlock.SKY, blockpos) + " sky, " + chunk.getLightFor(EnumSkyBlock.BLOCK, blockpos) + " block)");
/* 206 */       DifficultyInstance difficultyinstance = this.mc.theWorld.getDifficultyForLocation(blockpos);
/*     */       
/* 208 */       if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
/*     */         
/* 210 */         EntityPlayerMP entityplayermp = this.mc.getIntegratedServer().getConfigurationManager().getPlayerByUUID(this.mc.thePlayer.getUniqueID());
/*     */         
/* 212 */         if (entityplayermp != null) {
/*     */           
/* 214 */           DifficultyInstance difficultyinstance1 = this.mc.getIntegratedServer().getDifficultyAsync(entityplayermp.worldObj, new BlockPos((Entity)entityplayermp));
/*     */           
/* 216 */           if (difficultyinstance1 != null)
/*     */           {
/* 218 */             difficultyinstance = difficultyinstance1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 223 */       list.add(String.format("Local Difficulty: %.2f (Day %d)", new Object[] { Float.valueOf(difficultyinstance.getAdditionalDifficulty()), Long.valueOf(this.mc.theWorld.getWorldTime() / 24000L) }));
/*     */     } 
/*     */     
/* 226 */     if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive())
/*     */     {
/* 228 */       list.add("Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
/*     */     }
/*     */     
/* 231 */     if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
/*     */       
/* 233 */       BlockPos blockpos1 = this.mc.objectMouseOver.getBlockPos();
/* 234 */       list.add(String.format("Looking at: %d %d %d", new Object[] { Integer.valueOf(blockpos1.getX()), Integer.valueOf(blockpos1.getY()), Integer.valueOf(blockpos1.getZ()) }));
/*     */     } 
/*     */     
/* 237 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> getDebugInfoRight() {
/* 243 */     long i = Runtime.getRuntime().maxMemory();
/* 244 */     long j = Runtime.getRuntime().totalMemory();
/* 245 */     long k = Runtime.getRuntime().freeMemory();
/* 246 */     long l = j - k;
/* 247 */     List<String> list = Lists.newArrayList((Object[])new String[] { String.format("Java: %s %dbit", new Object[] { System.getProperty("java.version"), Integer.valueOf(this.mc.isJava64bit() ? 64 : 32) }), String.format("Mem: % 2d%% %03d/%03dMB", new Object[] { Long.valueOf(l * 100L / i), Long.valueOf(bytesToMb(l)), Long.valueOf(bytesToMb(i)) }), String.format("Allocated: % 2d%% %03dMB", new Object[] { Long.valueOf(j * 100L / i), Long.valueOf(bytesToMb(j)) }), "", String.format("CPU: %s", new Object[] { OpenGlHelper.func_183029_j() }), "", String.format("Display: %dx%d (%s)", new Object[] { Integer.valueOf(Display.getWidth()), Integer.valueOf(Display.getHeight()), GL11.glGetString(7936) }), GL11.glGetString(7937), GL11.glGetString(7938) });
/* 248 */     long i1 = NativeMemory.getBufferAllocated();
/* 249 */     long j1 = NativeMemory.getBufferMaximum();
/* 250 */     String s = "Native: " + bytesToMb(i1) + "/" + bytesToMb(j1) + "MB";
/* 251 */     list.add(4, s);
/*     */     
/* 253 */     if (Reflector.FMLCommonHandler_getBrandings.exists()) {
/*     */       
/* 255 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 256 */       list.add("");
/* 257 */       list.addAll((Collection<? extends String>)Reflector.call(object, Reflector.FMLCommonHandler_getBrandings, new Object[] { Boolean.FALSE }));
/*     */     } 
/*     */     
/* 260 */     if (!isReducedDebug() && 
/* 261 */       this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
/* 262 */       BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/* 263 */       IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
/*     */       
/* 265 */       if (this.mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
/* 266 */         iblockstate = iblockstate.getBlock().getActualState(iblockstate, (IBlockAccess)this.mc.theWorld, blockpos);
/*     */       }
/*     */       
/* 269 */       list.add("");
/* 270 */       list.add(String.valueOf(Block.blockRegistry.getNameForObject(iblockstate.getBlock())));
/*     */       
/* 272 */       for (UnmodifiableIterator<Map.Entry<IProperty, Comparable>> unmodifiableIterator = iblockstate.getProperties().entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<IProperty, Comparable> entry = unmodifiableIterator.next();
/* 273 */         String s1 = ((Comparable)entry.getValue()).toString();
/*     */         
/* 275 */         if (entry.getValue() == Boolean.TRUE) {
/* 276 */           s1 = ChatFormatting.GREEN + s1;
/* 277 */         } else if (entry.getValue() == Boolean.FALSE) {
/* 278 */           s1 = ChatFormatting.RED + s1;
/*     */         } 
/*     */         
/* 281 */         list.add(((IProperty)entry.getKey()).getName() + ": " + s1); }
/*     */     
/*     */     } 
/*     */ 
/*     */     
/* 286 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_181554_e() {}
/*     */ 
/*     */   
/*     */   private int func_181552_c(int p_181552_1_, int p_181552_2_, int p_181552_3_, int p_181552_4_) {
/* 295 */     return (p_181552_1_ < p_181552_3_) ? func_181553_a(-16711936, -256, p_181552_1_ / p_181552_3_) : func_181553_a(-256, -65536, (p_181552_1_ - p_181552_3_) / (p_181552_4_ - p_181552_3_));
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_181553_a(int p_181553_1_, int p_181553_2_, float p_181553_3_) {
/* 300 */     int i = p_181553_1_ >> 24 & 0xFF;
/* 301 */     int j = p_181553_1_ >> 16 & 0xFF;
/* 302 */     int k = p_181553_1_ >> 8 & 0xFF;
/* 303 */     int l = p_181553_1_ & 0xFF;
/* 304 */     int i1 = p_181553_2_ >> 24 & 0xFF;
/* 305 */     int j1 = p_181553_2_ >> 16 & 0xFF;
/* 306 */     int k1 = p_181553_2_ >> 8 & 0xFF;
/* 307 */     int l1 = p_181553_2_ & 0xFF;
/* 308 */     int i2 = MathHelper.clamp_int((int)(i + (i1 - i) * p_181553_3_), 0, 255);
/* 309 */     int j2 = MathHelper.clamp_int((int)(j + (j1 - j) * p_181553_3_), 0, 255);
/* 310 */     int k2 = MathHelper.clamp_int((int)(k + (k1 - k) * p_181553_3_), 0, 255);
/* 311 */     int l2 = MathHelper.clamp_int((int)(l + (l1 - l) * p_181553_3_), 0, 255);
/* 312 */     return i2 << 24 | j2 << 16 | k2 << 8 | l2;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long bytesToMb(long bytes) {
/* 317 */     return bytes / 1024L / 1024L;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiOverlayDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */