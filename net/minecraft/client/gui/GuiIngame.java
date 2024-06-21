/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import cc.slack.Slack;
/*      */ import cc.slack.events.State;
/*      */ import cc.slack.events.impl.render.RenderEvent;
/*      */ import cc.slack.events.impl.render.RenderScoreboard;
/*      */ import cc.slack.features.modules.impl.other.Tweaks;
/*      */ import cc.slack.features.modules.impl.render.ScoreboardModule;
/*      */ import cc.slack.utils.font.Fonts;
/*      */ import cc.slack.utils.player.ItemSpoofUtil;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatFormatting;
/*      */ import net.minecraft.util.FoodStats;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.optifine.CustomColors;
/*      */ 
/*      */ public class GuiIngame
/*      */   extends Gui {
/*   56 */   private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
/*   57 */   private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
/*   58 */   private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
/*   59 */   private final Random rand = new Random();
/*      */   
/*      */   private final Minecraft mc;
/*      */   
/*      */   private final RenderItem itemRenderer;
/*      */   
/*      */   private final GuiNewChat persistantChatGUI;
/*      */   
/*      */   private int updateCounter;
/*   68 */   private String recordPlaying = "";
/*      */ 
/*      */   
/*      */   private int recordPlayingUpFor;
/*      */   
/*      */   private boolean recordIsPlaying;
/*      */   
/*   75 */   public float prevVignetteBrightness = 1.0F;
/*      */   
/*      */   private int remainingHighlightTicks;
/*      */   
/*      */   private ItemStack highlightingItemStack;
/*      */   
/*      */   private final GuiOverlayDebug overlayDebug;
/*      */   
/*      */   private final GuiSpectator spectatorGui;
/*      */   
/*      */   private final GuiPlayerTabOverlay overlayPlayerList;
/*      */   
/*      */   private int field_175195_w;
/*   88 */   private String titleText = "";
/*   89 */   private String subtitleText = "";
/*      */   private int field_175199_z;
/*      */   private int field_175192_A;
/*      */   private int field_175193_B;
/*   93 */   private int playerHealth = 0;
/*   94 */   private int lastPlayerHealth = 0;
/*      */ 
/*      */   
/*   97 */   private long lastSystemTime = 0L;
/*      */ 
/*      */   
/*  100 */   private long healthUpdateCounter = 0L;
/*      */ 
/*      */   
/*      */   public GuiIngame(Minecraft mcIn) {
/*  104 */     this.mc = mcIn;
/*  105 */     this.itemRenderer = mcIn.getRenderItem();
/*  106 */     this.overlayDebug = new GuiOverlayDebug(mcIn);
/*  107 */     this.spectatorGui = new GuiSpectator(mcIn);
/*  108 */     this.persistantChatGUI = new GuiNewChat(mcIn);
/*  109 */     this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
/*  110 */     func_175177_a();
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_175177_a() {
/*  115 */     this.field_175199_z = 10;
/*  116 */     this.field_175192_A = 70;
/*  117 */     this.field_175193_B = 20;
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderGameOverlay(float partialTicks) {
/*  122 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  123 */     int i = scaledresolution.getScaledWidth();
/*  124 */     int j = scaledresolution.getScaledHeight();
/*  125 */     this.mc.entityRenderer.setupOverlayRendering();
/*  126 */     GlStateManager.enableBlend();
/*      */     
/*  128 */     if (Config.isVignetteEnabled()) {
/*      */       
/*  130 */       renderVignette(this.mc.thePlayer.getBrightness(partialTicks), scaledresolution);
/*      */     }
/*      */     else {
/*      */       
/*  134 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */     } 
/*      */     
/*  137 */     ItemStack itemstack = this.mc.thePlayer.inventory.armorItemInSlot(3);
/*      */     
/*  139 */     if (this.mc.gameSettings.thirdPersonView == 0 && itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin) && (
/*  140 */       !((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).isToggle() || 
/*  141 */       !((Boolean)((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).noPumpkin.getValue()).booleanValue()))
/*      */     {
/*      */       
/*  144 */       renderPumpkinOverlay(scaledresolution);
/*      */     }
/*      */     
/*  147 */     if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
/*      */       
/*  149 */       float f = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;
/*      */       
/*  151 */       if (f > 0.0F)
/*      */       {
/*  153 */         func_180474_b(f, scaledresolution);
/*      */       }
/*      */     } 
/*      */     
/*  157 */     if (this.mc.playerController.isSpectator()) {
/*      */       
/*  159 */       this.spectatorGui.renderTooltip(scaledresolution, partialTicks);
/*      */     }
/*      */     else {
/*      */       
/*  163 */       renderTooltip(scaledresolution, partialTicks);
/*      */     } 
/*      */     
/*  166 */     (new RenderEvent(RenderEvent.State.RENDER_2D, partialTicks, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight())).call();
/*      */     
/*  168 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  169 */     this.mc.getTextureManager().bindTexture(icons);
/*  170 */     GlStateManager.enableBlend();
/*      */     
/*  172 */     if (showCrosshair()) {
/*      */       
/*  174 */       GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
/*  175 */       GlStateManager.enableAlpha();
/*  176 */       drawTexturedModalRect(i / 2 - 7, j / 2 - 7, 0, 0, 16, 16);
/*      */     } 
/*      */     
/*  179 */     GlStateManager.enableAlpha();
/*  180 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  181 */     this.mc.mcProfiler.startSection("bossHealth");
/*  182 */     renderBossHealth();
/*  183 */     this.mc.mcProfiler.endSection();
/*      */     
/*  185 */     if (this.mc.playerController.shouldDrawHUD())
/*      */     {
/*  187 */       renderPlayerStats(scaledresolution);
/*      */     }
/*      */     
/*  190 */     GlStateManager.disableBlend();
/*      */     
/*  192 */     if (this.mc.thePlayer.getSleepTimer() > 0) {
/*      */       
/*  194 */       this.mc.mcProfiler.startSection("sleep");
/*  195 */       GlStateManager.disableDepth();
/*  196 */       GlStateManager.disableAlpha();
/*  197 */       int j1 = this.mc.thePlayer.getSleepTimer();
/*  198 */       float f1 = j1 / 100.0F;
/*      */       
/*  200 */       if (f1 > 1.0F)
/*      */       {
/*  202 */         f1 = 1.0F - (j1 - 100) / 10.0F;
/*      */       }
/*      */       
/*  205 */       int k = (int)(220.0F * f1) << 24 | 0x101020;
/*  206 */       drawRect(0, 0, i, j, k);
/*  207 */       GlStateManager.enableAlpha();
/*  208 */       GlStateManager.enableDepth();
/*  209 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/*  212 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  213 */     int k1 = i / 2 - 91;
/*      */     
/*  215 */     if (this.mc.thePlayer.isRidingHorse()) {
/*      */       
/*  217 */       renderHorseJumpBar(scaledresolution, k1);
/*      */     }
/*  219 */     else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
/*      */       
/*  221 */       renderExpBar(scaledresolution, k1);
/*      */     } 
/*      */     
/*  224 */     if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
/*      */       
/*  226 */       func_181551_a(scaledresolution);
/*      */     }
/*  228 */     else if (this.mc.thePlayer.isSpectator()) {
/*      */       
/*  230 */       this.spectatorGui.func_175263_a(scaledresolution);
/*      */     } 
/*      */     
/*  233 */     if (this.mc.gameSettings.showDebugInfo)
/*      */     {
/*  235 */       this.overlayDebug.renderDebugInfo(scaledresolution);
/*      */     }
/*      */     
/*  238 */     if (this.recordPlayingUpFor > 0) {
/*      */       
/*  240 */       this.mc.mcProfiler.startSection("overlayMessage");
/*  241 */       float f2 = this.recordPlayingUpFor - partialTicks;
/*  242 */       int l1 = (int)(f2 * 255.0F / 20.0F);
/*      */       
/*  244 */       if (l1 > 255)
/*      */       {
/*  246 */         l1 = 255;
/*      */       }
/*      */       
/*  249 */       if (l1 > 8) {
/*      */         
/*  251 */         GlStateManager.pushMatrix();
/*  252 */         GlStateManager.translate((i / 2), (j - 68), 0.0F);
/*  253 */         GlStateManager.enableBlend();
/*  254 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  255 */         int l = 16777215;
/*      */         
/*  257 */         if (this.recordIsPlaying)
/*      */         {
/*  259 */           l = MathHelper.func_181758_c(f2 / 50.0F, 0.7F, 0.6F) & 0xFFFFFF;
/*      */         }
/*      */         
/*  262 */         getFontRenderer().drawString(this.recordPlaying, -getFontRenderer().getStringWidth(this.recordPlaying) / 2, -4, l + (l1 << 24 & 0xFF000000));
/*  263 */         GlStateManager.disableBlend();
/*  264 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/*  267 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/*  270 */     if (this.field_175195_w > 0) {
/*      */       
/*  272 */       this.mc.mcProfiler.startSection("titleAndSubtitle");
/*  273 */       float f3 = this.field_175195_w - partialTicks;
/*  274 */       int i2 = 255;
/*      */       
/*  276 */       if (this.field_175195_w > this.field_175193_B + this.field_175192_A) {
/*      */         
/*  278 */         float f4 = (this.field_175199_z + this.field_175192_A + this.field_175193_B) - f3;
/*  279 */         i2 = (int)(f4 * 255.0F / this.field_175199_z);
/*      */       } 
/*      */       
/*  282 */       if (this.field_175195_w <= this.field_175193_B)
/*      */       {
/*  284 */         i2 = (int)(f3 * 255.0F / this.field_175193_B);
/*      */       }
/*      */       
/*  287 */       i2 = MathHelper.clamp_int(i2, 0, 255);
/*      */       
/*  289 */       if (i2 > 8) {
/*      */         
/*  291 */         boolean customTitle = (((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).isToggle() && ((Boolean)((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).customTitle.getValue()).booleanValue());
/*  292 */         GlStateManager.pushMatrix();
/*  293 */         if (customTitle) {
/*  294 */           GlStateManager.translate((i / 2), (j / 4), 0.0F);
/*      */         } else {
/*  296 */           GlStateManager.translate((i / 2), (j / 2), 0.0F);
/*      */         } 
/*  298 */         GlStateManager.enableBlend();
/*  299 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  300 */         GlStateManager.pushMatrix();
/*  301 */         int j2 = i2 << 24 & 0xFF000000;
/*  302 */         if (customTitle) {
/*  303 */           GlStateManager.scale(3.0F, 3.0F, 3.0F);
/*  304 */           Fonts.apple18.drawStringWithShadow(this.titleText, -Fonts.apple18.getStringWidth(this.titleText) / 2.0F, -10.0F, 0xFFFFFF | j2);
/*      */         } else {
/*  306 */           GlStateManager.scale(4.0F, 4.0F, 4.0F);
/*  307 */           getFontRenderer().drawString(this.titleText, (-getFontRenderer().getStringWidth(this.titleText) / 2), -10.0F, 0xFFFFFF | j2, true);
/*      */         } 
/*  309 */         GlStateManager.popMatrix();
/*  310 */         GlStateManager.pushMatrix();
/*  311 */         if (customTitle) {
/*  312 */           GlStateManager.scale(1.5F, 1.5F, 1.5F);
/*  313 */           Fonts.apple18.drawStringWithShadow(this.subtitleText, -Fonts.apple18.getStringWidth(this.subtitleText) / 2.0F, 5.0F, 0xFFFFFF | j2);
/*      */         } else {
/*  315 */           GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*  316 */           getFontRenderer().drawString(this.subtitleText, (-getFontRenderer().getStringWidth(this.subtitleText) / 2), 5.0F, 0xFFFFFF | j2, true);
/*      */         } 
/*  318 */         GlStateManager.popMatrix();
/*  319 */         GlStateManager.disableBlend();
/*  320 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/*  323 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/*  326 */     Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
/*  327 */     ScoreObjective scoreobjective = null;
/*  328 */     ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getCommandSenderName());
/*      */     
/*  330 */     if (scoreplayerteam != null) {
/*      */       
/*  332 */       int i1 = scoreplayerteam.getChatFormat().getColorIndex();
/*      */       
/*  334 */       if (i1 >= 0)
/*      */       {
/*  336 */         scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
/*      */       }
/*      */     } 
/*      */     
/*  340 */     ScoreObjective scoreobjective1 = (scoreobjective != null) ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
/*      */     
/*  342 */     if (scoreobjective1 != null && !((Boolean)((ScoreboardModule)Slack.getInstance().getModuleManager().getInstance(ScoreboardModule.class)).noscoreboard.getValue()).booleanValue()) {
/*      */       
/*  344 */       RenderScoreboard event = new RenderScoreboard(State.PRE);
/*  345 */       event.call();
/*  346 */       renderScoreboard(scoreobjective1, scaledresolution);
/*  347 */       RenderScoreboard event2 = new RenderScoreboard(State.POST);
/*  348 */       event2.call();
/*  349 */       if (event.isCanceled()) {
/*      */         return;
/*      */       }
/*  352 */       if (event2.isCanceled()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/*  357 */     GlStateManager.enableBlend();
/*  358 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  359 */     GlStateManager.disableAlpha();
/*  360 */     GlStateManager.pushMatrix();
/*  361 */     GlStateManager.translate(0.0F, (j - 48), 0.0F);
/*  362 */     this.mc.mcProfiler.startSection("chat");
/*  363 */     this.persistantChatGUI.drawChat(this.updateCounter);
/*  364 */     this.mc.mcProfiler.endSection();
/*  365 */     GlStateManager.popMatrix();
/*  366 */     scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
/*      */     
/*  368 */     if (this.mc.gameSettings.keyBindPlayerList.isKeyDown() && (!this.mc.isIntegratedServerRunning() || this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() > 1 || scoreobjective1 != null)) {
/*      */       
/*  370 */       this.overlayPlayerList.updatePlayerList(true);
/*  371 */       this.overlayPlayerList.renderPlayerlist(i, scoreboard, scoreobjective1);
/*      */     }
/*      */     else {
/*      */       
/*  375 */       this.overlayPlayerList.updatePlayerList(false);
/*      */     } 
/*      */     
/*  378 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  379 */     GlStateManager.disableLighting();
/*  380 */     GlStateManager.enableAlpha();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void renderTooltip(ScaledResolution sr, float partialTicks) {
/*  385 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*      */       
/*  387 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  388 */       this.mc.getTextureManager().bindTexture(widgetsTexPath);
/*  389 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  390 */       int i = sr.getScaledWidth() / 2;
/*  391 */       float f = this.zLevel;
/*  392 */       this.zLevel = -90.0F;
/*  393 */       drawTexturedModalRect(i - 91, sr.getScaledHeight() - 22, 0, 0, 182, 22);
/*      */       
/*  395 */       int currentItem = entityplayer.inventory.currentItem;
/*  396 */       if (ItemSpoofUtil.isEnabled) currentItem = ItemSpoofUtil.renderSlot; 
/*  397 */       drawTexturedModalRect(i - 91 - 1 + currentItem * 20, sr.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
/*  398 */       this.zLevel = f;
/*  399 */       GlStateManager.enableRescaleNormal();
/*  400 */       GlStateManager.enableBlend();
/*  401 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  402 */       RenderHelper.enableGUIStandardItemLighting();
/*      */       
/*  404 */       for (int j = 0; j < 9; j++) {
/*      */         
/*  406 */         int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
/*  407 */         int l = sr.getScaledHeight() - 16 - 3;
/*  408 */         renderHotbarItem(j, k, l, partialTicks, entityplayer);
/*      */       } 
/*      */       
/*  411 */       RenderHelper.disableStandardItemLighting();
/*  412 */       GlStateManager.disableRescaleNormal();
/*  413 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderHorseJumpBar(ScaledResolution p_175186_1_, int p_175186_2_) {
/*  419 */     this.mc.mcProfiler.startSection("jumpBar");
/*  420 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/*  421 */     float f = this.mc.thePlayer.getHorseJumpPower();
/*  422 */     int i = 182;
/*  423 */     int j = (int)(f * (i + 1));
/*  424 */     int k = p_175186_1_.getScaledHeight() - 32 + 3;
/*  425 */     drawTexturedModalRect(p_175186_2_, k, 0, 84, i, 5);
/*      */     
/*  427 */     if (j > 0)
/*      */     {
/*  429 */       drawTexturedModalRect(p_175186_2_, k, 0, 89, j, 5);
/*      */     }
/*      */     
/*  432 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderExpBar(ScaledResolution p_175176_1_, int p_175176_2_) {
/*  437 */     this.mc.mcProfiler.startSection("expBar");
/*  438 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/*  439 */     int i = this.mc.thePlayer.xpBarCap();
/*      */     
/*  441 */     if (i > 0) {
/*      */       
/*  443 */       int j = 182;
/*  444 */       int k = (int)(this.mc.thePlayer.experience * (j + 1));
/*  445 */       int l = p_175176_1_.getScaledHeight() - 32 + 3;
/*  446 */       drawTexturedModalRect(p_175176_2_, l, 0, 64, j, 5);
/*      */       
/*  448 */       if (k > 0)
/*      */       {
/*  450 */         drawTexturedModalRect(p_175176_2_, l, 0, 69, k, 5);
/*      */       }
/*      */     } 
/*      */     
/*  454 */     this.mc.mcProfiler.endSection();
/*      */     
/*  456 */     if (this.mc.thePlayer.experienceLevel > 0) {
/*      */       
/*  458 */       this.mc.mcProfiler.startSection("expLevel");
/*  459 */       int k1 = 8453920;
/*      */       
/*  461 */       if (Config.isCustomColors())
/*      */       {
/*  463 */         k1 = CustomColors.getExpBarTextColor(k1);
/*      */       }
/*      */       
/*  466 */       String s = "" + this.mc.thePlayer.experienceLevel;
/*  467 */       int l1 = (p_175176_1_.getScaledWidth() - getFontRenderer().getStringWidth(s)) / 2;
/*  468 */       int i1 = p_175176_1_.getScaledHeight() - 31 - 4;
/*  469 */       int j1 = 0;
/*  470 */       getFontRenderer().drawString(s, l1 + 1, i1, 0);
/*  471 */       getFontRenderer().drawString(s, l1 - 1, i1, 0);
/*  472 */       getFontRenderer().drawString(s, l1, i1 + 1, 0);
/*  473 */       getFontRenderer().drawString(s, l1, i1 - 1, 0);
/*  474 */       getFontRenderer().drawString(s, l1, i1, k1);
/*  475 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181551_a(ScaledResolution p_181551_1_) {
/*  481 */     this.mc.mcProfiler.startSection("selectedItemName");
/*      */     
/*  483 */     if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
/*      */       
/*  485 */       String s = this.highlightingItemStack.getDisplayName();
/*      */       
/*  487 */       if (this.highlightingItemStack.hasDisplayName())
/*      */       {
/*  489 */         s = ChatFormatting.ITALIC + s;
/*      */       }
/*      */       
/*  492 */       int i = (p_181551_1_.getScaledWidth() - getFontRenderer().getStringWidth(s)) / 2;
/*  493 */       int j = p_181551_1_.getScaledHeight() - 59;
/*      */       
/*  495 */       if (!this.mc.playerController.shouldDrawHUD())
/*      */       {
/*  497 */         j += 14;
/*      */       }
/*      */       
/*  500 */       int k = (int)(this.remainingHighlightTicks * 256.0F / 10.0F);
/*      */       
/*  502 */       if (k > 255)
/*      */       {
/*  504 */         k = 255;
/*      */       }
/*      */       
/*  507 */       if (k > 0) {
/*      */         
/*  509 */         GlStateManager.pushMatrix();
/*  510 */         GlStateManager.enableBlend();
/*  511 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  512 */         getFontRenderer().drawStringWithShadow(s, i, j, 16777215 + (k << 24));
/*  513 */         GlStateManager.disableBlend();
/*  514 */         GlStateManager.popMatrix();
/*      */       } 
/*      */     } 
/*      */     
/*  518 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   protected boolean showCrosshair() {
/*  522 */     if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo)
/*      */     {
/*  524 */       return false;
/*      */     }
/*  526 */     if (this.mc.playerController.isSpectator()) {
/*      */       
/*  528 */       if (this.mc.pointedEntity != null)
/*      */       {
/*  530 */         return true;
/*      */       }
/*      */ 
/*      */       
/*  534 */       if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*      */         
/*  536 */         BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/*      */         
/*  538 */         if (this.mc.theWorld.getTileEntity(blockpos) instanceof net.minecraft.inventory.IInventory)
/*      */         {
/*  540 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  544 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  549 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderScoreboard(ScoreObjective p_180475_1_, ScaledResolution p_180475_2_) {
/*  555 */     Scoreboard scoreboard = p_180475_1_.getScoreboard();
/*  556 */     Collection<Score> collection = scoreboard.getSortedScores(p_180475_1_);
/*  557 */     List<Score> list = Lists.newArrayList(Iterables.filter(collection, new Predicate<Score>()
/*      */           {
/*      */             public boolean apply(Score p_apply_1_)
/*      */             {
/*  561 */               return (p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#"));
/*      */             }
/*      */           }));
/*      */     
/*  565 */     if (list.size() > 15) {
/*      */       
/*  567 */       collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
/*      */     }
/*      */     else {
/*      */       
/*  571 */       collection = list;
/*      */     } 
/*      */     
/*  574 */     int i = getFontRenderer().getStringWidth(p_180475_1_.getDisplayName());
/*      */     
/*  576 */     for (Score score : collection) {
/*      */       
/*  578 */       ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
/*  579 */       String s = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam, score.getPlayerName()) + ": " + ChatFormatting.RED + score.getScorePoints();
/*  580 */       i = Math.max(i, getFontRenderer().getStringWidth(s));
/*      */     } 
/*      */     
/*  583 */     int i1 = collection.size() * (getFontRenderer()).FONT_HEIGHT;
/*  584 */     int j1 = p_180475_2_.getScaledHeight() / 2 + i1 / 3;
/*  585 */     int k1 = 3;
/*  586 */     int l1 = p_180475_2_.getScaledWidth() - i - k1;
/*  587 */     int j = 0;
/*      */     
/*  589 */     for (Score score1 : collection) {
/*      */       
/*  591 */       j++;
/*  592 */       ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
/*  593 */       String s1 = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam1, score1.getPlayerName());
/*  594 */       String s2 = ChatFormatting.RED + "" + score1.getScorePoints();
/*  595 */       int k = j1 - j * (getFontRenderer()).FONT_HEIGHT;
/*  596 */       int l = p_180475_2_.getScaledWidth() - k1 + 2;
/*  597 */       drawRect(l1 - 2, k, l, k + (getFontRenderer()).FONT_HEIGHT, 1342177280);
/*  598 */       getFontRenderer().drawString(s1, l1, k, 553648127);
/*  599 */       getFontRenderer().drawString(s2, l - getFontRenderer().getStringWidth(s2), k, 553648127);
/*      */       
/*  601 */       if (j == collection.size()) {
/*      */         
/*  603 */         String s3 = p_180475_1_.getDisplayName();
/*  604 */         drawRect(l1 - 2, k - (getFontRenderer()).FONT_HEIGHT - 1, l, k - 1, 1610612736);
/*  605 */         drawRect(l1 - 2, k - 1, l, k, 1342177280);
/*  606 */         getFontRenderer().drawString(s3, l1 + i / 2 - getFontRenderer().getStringWidth(s3) / 2, k - (getFontRenderer()).FONT_HEIGHT, 553648127);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderPlayerStats(ScaledResolution p_180477_1_) {
/*  613 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*      */       
/*  615 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  616 */       int i = MathHelper.ceiling_float_int(entityplayer.getHealth());
/*  617 */       boolean flag = (this.healthUpdateCounter > this.updateCounter && (this.healthUpdateCounter - this.updateCounter) / 3L % 2L == 1L);
/*      */       
/*  619 */       if (i < this.playerHealth && entityplayer.hurtResistantTime > 0) {
/*      */         
/*  621 */         this.lastSystemTime = Minecraft.getSystemTime();
/*  622 */         this.healthUpdateCounter = (this.updateCounter + 20);
/*      */       }
/*  624 */       else if (i > this.playerHealth && entityplayer.hurtResistantTime > 0) {
/*      */         
/*  626 */         this.lastSystemTime = Minecraft.getSystemTime();
/*  627 */         this.healthUpdateCounter = (this.updateCounter + 10);
/*      */       } 
/*      */       
/*  630 */       if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
/*      */         
/*  632 */         this.playerHealth = i;
/*  633 */         this.lastPlayerHealth = i;
/*  634 */         this.lastSystemTime = Minecraft.getSystemTime();
/*      */       } 
/*      */       
/*  637 */       this.playerHealth = i;
/*  638 */       int j = this.lastPlayerHealth;
/*  639 */       this.rand.setSeed((this.updateCounter * 312871));
/*  640 */       boolean flag1 = false;
/*  641 */       FoodStats foodstats = entityplayer.getFoodStats();
/*  642 */       int k = foodstats.getFoodLevel();
/*  643 */       int l = foodstats.getPrevFoodLevel();
/*  644 */       IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
/*  645 */       int i1 = p_180477_1_.getScaledWidth() / 2 - 91;
/*  646 */       int j1 = p_180477_1_.getScaledWidth() / 2 + 91;
/*  647 */       int k1 = p_180477_1_.getScaledHeight() - 39;
/*  648 */       float f = (float)iattributeinstance.getAttributeValue();
/*  649 */       float f1 = entityplayer.getAbsorptionAmount();
/*  650 */       int l1 = MathHelper.ceiling_float_int((f + f1) / 2.0F / 10.0F);
/*  651 */       int i2 = Math.max(10 - l1 - 2, 3);
/*  652 */       int j2 = k1 - (l1 - 1) * i2 - 10;
/*  653 */       float f2 = f1;
/*  654 */       int k2 = entityplayer.getTotalArmorValue();
/*  655 */       int l2 = -1;
/*      */       
/*  657 */       if (entityplayer.isPotionActive(Potion.regeneration))
/*      */       {
/*  659 */         l2 = this.updateCounter % MathHelper.ceiling_float_int(f + 5.0F);
/*      */       }
/*      */       
/*  662 */       this.mc.mcProfiler.startSection("armor");
/*      */       
/*  664 */       for (int i3 = 0; i3 < 10; i3++) {
/*      */         
/*  666 */         if (k2 > 0) {
/*      */           
/*  668 */           int j3 = i1 + i3 * 8;
/*      */           
/*  670 */           if (i3 * 2 + 1 < k2)
/*      */           {
/*  672 */             drawTexturedModalRect(j3, j2, 34, 9, 9, 9);
/*      */           }
/*      */           
/*  675 */           if (i3 * 2 + 1 == k2)
/*      */           {
/*  677 */             drawTexturedModalRect(j3, j2, 25, 9, 9, 9);
/*      */           }
/*      */           
/*  680 */           if (i3 * 2 + 1 > k2)
/*      */           {
/*  682 */             drawTexturedModalRect(j3, j2, 16, 9, 9, 9);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  687 */       this.mc.mcProfiler.endStartSection("health");
/*      */       
/*  689 */       for (int i6 = MathHelper.ceiling_float_int((f + f1) / 2.0F) - 1; i6 >= 0; i6--) {
/*      */         
/*  691 */         int j6 = 16;
/*      */         
/*  693 */         if (entityplayer.isPotionActive(Potion.poison)) {
/*      */           
/*  695 */           j6 += 36;
/*      */         }
/*  697 */         else if (entityplayer.isPotionActive(Potion.wither)) {
/*      */           
/*  699 */           j6 += 72;
/*      */         } 
/*      */         
/*  702 */         int k3 = 0;
/*      */         
/*  704 */         if (flag)
/*      */         {
/*  706 */           k3 = 1;
/*      */         }
/*      */         
/*  709 */         int l3 = MathHelper.ceiling_float_int((i6 + 1) / 10.0F) - 1;
/*  710 */         int i4 = i1 + i6 % 10 * 8;
/*  711 */         int j4 = k1 - l3 * i2;
/*      */         
/*  713 */         if (i <= 4)
/*      */         {
/*  715 */           j4 += this.rand.nextInt(2);
/*      */         }
/*      */         
/*  718 */         if (i6 == l2)
/*      */         {
/*  720 */           j4 -= 2;
/*      */         }
/*      */         
/*  723 */         int k4 = 0;
/*      */         
/*  725 */         if (entityplayer.worldObj.getWorldInfo().isHardcoreModeEnabled())
/*      */         {
/*  727 */           k4 = 5;
/*      */         }
/*      */         
/*  730 */         drawTexturedModalRect(i4, j4, 16 + k3 * 9, 9 * k4, 9, 9);
/*      */         
/*  732 */         if (flag) {
/*      */           
/*  734 */           if (i6 * 2 + 1 < j)
/*      */           {
/*  736 */             drawTexturedModalRect(i4, j4, j6 + 54, 9 * k4, 9, 9);
/*      */           }
/*      */           
/*  739 */           if (i6 * 2 + 1 == j)
/*      */           {
/*  741 */             drawTexturedModalRect(i4, j4, j6 + 63, 9 * k4, 9, 9);
/*      */           }
/*      */         } 
/*      */         
/*  745 */         if (f2 <= 0.0F) {
/*      */           
/*  747 */           if (i6 * 2 + 1 < i)
/*      */           {
/*  749 */             drawTexturedModalRect(i4, j4, j6 + 36, 9 * k4, 9, 9);
/*      */           }
/*      */           
/*  752 */           if (i6 * 2 + 1 == i)
/*      */           {
/*  754 */             drawTexturedModalRect(i4, j4, j6 + 45, 9 * k4, 9, 9);
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  759 */           if (f2 == f1 && f1 % 2.0F == 1.0F) {
/*      */             
/*  761 */             drawTexturedModalRect(i4, j4, j6 + 153, 9 * k4, 9, 9);
/*      */           }
/*      */           else {
/*      */             
/*  765 */             drawTexturedModalRect(i4, j4, j6 + 144, 9 * k4, 9, 9);
/*      */           } 
/*      */           
/*  768 */           f2 -= 2.0F;
/*      */         } 
/*      */       } 
/*      */       
/*  772 */       Entity entity = entityplayer.ridingEntity;
/*      */       
/*  774 */       if (entity == null) {
/*      */         
/*  776 */         this.mc.mcProfiler.endStartSection("food");
/*      */         
/*  778 */         for (int k6 = 0; k6 < 10; k6++)
/*      */         {
/*  780 */           int j7 = k1;
/*  781 */           int l7 = 16;
/*  782 */           int k8 = 0;
/*      */           
/*  784 */           if (entityplayer.isPotionActive(Potion.hunger)) {
/*      */             
/*  786 */             l7 += 36;
/*  787 */             k8 = 13;
/*      */           } 
/*      */           
/*  790 */           if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (k * 3 + 1) == 0)
/*      */           {
/*  792 */             j7 = k1 + this.rand.nextInt(3) - 1;
/*      */           }
/*      */           
/*  795 */           if (flag1)
/*      */           {
/*  797 */             k8 = 1;
/*      */           }
/*      */           
/*  800 */           int j9 = j1 - k6 * 8 - 9;
/*  801 */           drawTexturedModalRect(j9, j7, 16 + k8 * 9, 27, 9, 9);
/*      */           
/*  803 */           if (flag1) {
/*      */             
/*  805 */             if (k6 * 2 + 1 < l)
/*      */             {
/*  807 */               drawTexturedModalRect(j9, j7, l7 + 54, 27, 9, 9);
/*      */             }
/*      */             
/*  810 */             if (k6 * 2 + 1 == l)
/*      */             {
/*  812 */               drawTexturedModalRect(j9, j7, l7 + 63, 27, 9, 9);
/*      */             }
/*      */           } 
/*      */           
/*  816 */           if (k6 * 2 + 1 < k)
/*      */           {
/*  818 */             drawTexturedModalRect(j9, j7, l7 + 36, 27, 9, 9);
/*      */           }
/*      */           
/*  821 */           if (k6 * 2 + 1 == k)
/*      */           {
/*  823 */             drawTexturedModalRect(j9, j7, l7 + 45, 27, 9, 9);
/*      */           }
/*      */         }
/*      */       
/*  827 */       } else if (entity instanceof EntityLivingBase) {
/*      */         
/*  829 */         this.mc.mcProfiler.endStartSection("mountHealth");
/*  830 */         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/*  831 */         int i7 = (int)Math.ceil(entitylivingbase.getHealth());
/*  832 */         float f3 = entitylivingbase.getMaxHealth();
/*  833 */         int j8 = (int)(f3 + 0.5F) / 2;
/*      */         
/*  835 */         if (j8 > 30)
/*      */         {
/*  837 */           j8 = 30;
/*      */         }
/*      */         
/*  840 */         int i9 = k1;
/*      */         
/*  842 */         for (int k9 = 0; j8 > 0; k9 += 20) {
/*      */           
/*  844 */           int l4 = Math.min(j8, 10);
/*  845 */           j8 -= l4;
/*      */           
/*  847 */           for (int i5 = 0; i5 < l4; i5++) {
/*      */             
/*  849 */             int j5 = 52;
/*  850 */             int k5 = 0;
/*      */             
/*  852 */             if (flag1)
/*      */             {
/*  854 */               k5 = 1;
/*      */             }
/*      */             
/*  857 */             int l5 = j1 - i5 * 8 - 9;
/*  858 */             drawTexturedModalRect(l5, i9, j5 + k5 * 9, 9, 9, 9);
/*      */             
/*  860 */             if (i5 * 2 + 1 + k9 < i7)
/*      */             {
/*  862 */               drawTexturedModalRect(l5, i9, j5 + 36, 9, 9, 9);
/*      */             }
/*      */             
/*  865 */             if (i5 * 2 + 1 + k9 == i7)
/*      */             {
/*  867 */               drawTexturedModalRect(l5, i9, j5 + 45, 9, 9, 9);
/*      */             }
/*      */           } 
/*      */           
/*  871 */           i9 -= 10;
/*      */         } 
/*      */       } 
/*      */       
/*  875 */       this.mc.mcProfiler.endStartSection("air");
/*      */       
/*  877 */       if (entityplayer.isInsideOfMaterial(Material.water)) {
/*      */         
/*  879 */         int l6 = this.mc.thePlayer.getAir();
/*  880 */         int k7 = MathHelper.ceiling_double_int((l6 - 2) * 10.0D / 300.0D);
/*  881 */         int i8 = MathHelper.ceiling_double_int(l6 * 10.0D / 300.0D) - k7;
/*      */         
/*  883 */         for (int l8 = 0; l8 < k7 + i8; l8++) {
/*      */           
/*  885 */           if (l8 < k7) {
/*      */             
/*  887 */             drawTexturedModalRect(j1 - l8 * 8 - 9, j2, 16, 18, 9, 9);
/*      */           }
/*      */           else {
/*      */             
/*  891 */             drawTexturedModalRect(j1 - l8 * 8 - 9, j2, 25, 18, 9, 9);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  896 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderBossHealth() {
/*  905 */     if (BossStatus.bossName != null && BossStatus.statusBarTime > 0 && !((Boolean)((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).nobosshealth.getValue()).booleanValue()) {
/*      */       
/*  907 */       BossStatus.statusBarTime--;
/*  908 */       FontRenderer fontrenderer = this.mc.MCfontRenderer;
/*  909 */       ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  910 */       int i = scaledresolution.getScaledWidth();
/*  911 */       int j = 182;
/*  912 */       int k = i / 2 - j / 2;
/*  913 */       int l = (int)(BossStatus.healthScale * (j + 1));
/*  914 */       int i1 = 12;
/*  915 */       drawTexturedModalRect(k, i1, 0, 74, j, 5);
/*  916 */       drawTexturedModalRect(k, i1, 0, 74, j, 5);
/*      */       
/*  918 */       if (l > 0)
/*      */       {
/*  920 */         drawTexturedModalRect(k, i1, 0, 79, l, 5);
/*      */       }
/*      */       
/*  923 */       String s = BossStatus.bossName;
/*  924 */       getFontRenderer().drawStringWithShadow(s, (i / 2 - getFontRenderer().getStringWidth(s) / 2), (i1 - 10), 16777215);
/*  925 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  926 */       this.mc.getTextureManager().bindTexture(icons);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderPumpkinOverlay(ScaledResolution p_180476_1_) {
/*  932 */     GlStateManager.disableDepth();
/*  933 */     GlStateManager.depthMask(false);
/*  934 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  935 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  936 */     GlStateManager.disableAlpha();
/*  937 */     this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
/*  938 */     Tessellator tessellator = Tessellator.getInstance();
/*  939 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  940 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  941 */     worldrenderer.pos(0.0D, p_180476_1_.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/*  942 */     worldrenderer.pos(p_180476_1_.getScaledWidth(), p_180476_1_.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
/*  943 */     worldrenderer.pos(p_180476_1_.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/*  944 */     worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/*  945 */     tessellator.draw();
/*  946 */     GlStateManager.depthMask(true);
/*  947 */     GlStateManager.enableDepth();
/*  948 */     GlStateManager.enableAlpha();
/*  949 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderVignette(float p_180480_1_, ScaledResolution p_180480_2_) {
/*  957 */     if (!Config.isVignetteEnabled()) {
/*      */       
/*  959 */       GlStateManager.enableDepth();
/*  960 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */     }
/*      */     else {
/*      */       
/*  964 */       p_180480_1_ = 1.0F - p_180480_1_;
/*  965 */       p_180480_1_ = MathHelper.clamp_float(p_180480_1_, 0.0F, 1.0F);
/*  966 */       WorldBorder worldborder = this.mc.theWorld.getWorldBorder();
/*  967 */       float f = (float)worldborder.getClosestDistance((Entity)this.mc.thePlayer);
/*  968 */       double d0 = Math.min(worldborder.getResizeSpeed() * worldborder.getWarningTime() * 1000.0D, Math.abs(worldborder.getTargetSize() - worldborder.getDiameter()));
/*  969 */       double d1 = Math.max(worldborder.getWarningDistance(), d0);
/*      */       
/*  971 */       if (f < d1) {
/*      */         
/*  973 */         f = 1.0F - (float)(f / d1);
/*      */       }
/*      */       else {
/*      */         
/*  977 */         f = 0.0F;
/*      */       } 
/*      */       
/*  980 */       this.prevVignetteBrightness = (float)(this.prevVignetteBrightness + (p_180480_1_ - this.prevVignetteBrightness) * 0.01D);
/*  981 */       GlStateManager.disableDepth();
/*  982 */       GlStateManager.depthMask(false);
/*  983 */       GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
/*      */       
/*  985 */       if (f > 0.0F) {
/*      */         
/*  987 */         GlStateManager.color(0.0F, f, f, 1.0F);
/*      */       }
/*      */       else {
/*      */         
/*  991 */         GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
/*      */       } 
/*      */       
/*  994 */       this.mc.getTextureManager().bindTexture(vignetteTexPath);
/*  995 */       Tessellator tessellator = Tessellator.getInstance();
/*  996 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  997 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  998 */       worldrenderer.pos(0.0D, p_180480_2_.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/*  999 */       worldrenderer.pos(p_180480_2_.getScaledWidth(), p_180480_2_.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
/* 1000 */       worldrenderer.pos(p_180480_2_.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1001 */       worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1002 */       tessellator.draw();
/* 1003 */       GlStateManager.depthMask(true);
/* 1004 */       GlStateManager.enableDepth();
/* 1005 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1006 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_180474_b(float p_180474_1_, ScaledResolution p_180474_2_) {
/* 1012 */     if (p_180474_1_ < 1.0F) {
/*      */       
/* 1014 */       p_180474_1_ *= p_180474_1_;
/* 1015 */       p_180474_1_ *= p_180474_1_;
/* 1016 */       p_180474_1_ = p_180474_1_ * 0.8F + 0.2F;
/*      */     } 
/*      */     
/* 1019 */     GlStateManager.disableAlpha();
/* 1020 */     GlStateManager.disableDepth();
/* 1021 */     GlStateManager.depthMask(false);
/* 1022 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1023 */     GlStateManager.color(1.0F, 1.0F, 1.0F, p_180474_1_);
/* 1024 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1025 */     TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.portal.getDefaultState());
/* 1026 */     float f = textureatlassprite.getMinU();
/* 1027 */     float f1 = textureatlassprite.getMinV();
/* 1028 */     float f2 = textureatlassprite.getMaxU();
/* 1029 */     float f3 = textureatlassprite.getMaxV();
/* 1030 */     Tessellator tessellator = Tessellator.getInstance();
/* 1031 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1032 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1033 */     worldrenderer.pos(0.0D, p_180474_2_.getScaledHeight(), -90.0D).tex(f, f3).endVertex();
/* 1034 */     worldrenderer.pos(p_180474_2_.getScaledWidth(), p_180474_2_.getScaledHeight(), -90.0D).tex(f2, f3).endVertex();
/* 1035 */     worldrenderer.pos(p_180474_2_.getScaledWidth(), 0.0D, -90.0D).tex(f2, f1).endVertex();
/* 1036 */     worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(f, f1).endVertex();
/* 1037 */     tessellator.draw();
/* 1038 */     GlStateManager.depthMask(true);
/* 1039 */     GlStateManager.enableDepth();
/* 1040 */     GlStateManager.enableAlpha();
/* 1041 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer p_175184_5_) {
/* 1046 */     ItemStack itemstack = p_175184_5_.inventory.mainInventory[index];
/*      */     
/* 1048 */     if (itemstack != null) {
/*      */       
/* 1050 */       float f = itemstack.animationsToGo - partialTicks;
/*      */       
/* 1052 */       if (f > 0.0F) {
/*      */         
/* 1054 */         GlStateManager.pushMatrix();
/* 1055 */         float f1 = 1.0F + f / 5.0F;
/* 1056 */         GlStateManager.translate((xPos + 8), (yPos + 12), 0.0F);
/* 1057 */         GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
/* 1058 */         GlStateManager.translate(-(xPos + 8), -(yPos + 12), 0.0F);
/*      */       } 
/*      */       
/* 1061 */       this.itemRenderer.renderItemAndEffectIntoGUI(itemstack, xPos, yPos);
/*      */       
/* 1063 */       if (f > 0.0F)
/*      */       {
/* 1065 */         GlStateManager.popMatrix();
/*      */       }
/*      */       
/* 1068 */       this.itemRenderer.renderItemOverlays(this.mc.MCfontRenderer, itemstack, xPos, yPos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTick() {
/* 1077 */     if (this.recordPlayingUpFor > 0)
/*      */     {
/* 1079 */       this.recordPlayingUpFor--;
/*      */     }
/*      */     
/* 1082 */     if (this.field_175195_w > 0) {
/*      */       
/* 1084 */       this.field_175195_w--;
/*      */       
/* 1086 */       if (this.field_175195_w <= 0) {
/*      */         
/* 1088 */         this.titleText = "";
/* 1089 */         this.subtitleText = "";
/*      */       } 
/*      */     } 
/*      */     
/* 1093 */     this.updateCounter++;
/*      */     
/* 1095 */     if (this.mc.thePlayer != null) {
/*      */       
/* 1097 */       ItemStack itemstack = this.mc.thePlayer.inventory.getCurrentItem();
/*      */       
/* 1099 */       if (itemstack == null) {
/*      */         
/* 1101 */         this.remainingHighlightTicks = 0;
/*      */       }
/* 1103 */       else if (this.highlightingItemStack != null && itemstack.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.highlightingItemStack) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.highlightingItemStack.getMetadata())) {
/*      */         
/* 1105 */         if (this.remainingHighlightTicks > 0)
/*      */         {
/* 1107 */           this.remainingHighlightTicks--;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1112 */         this.remainingHighlightTicks = 40;
/*      */       } 
/*      */       
/* 1115 */       this.highlightingItemStack = itemstack;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRecordPlayingMessage(String p_73833_1_) {
/* 1121 */     setRecordPlaying(I18n.format("record.nowPlaying", new Object[] { p_73833_1_ }), true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRecordPlaying(String p_110326_1_, boolean p_110326_2_) {
/* 1126 */     this.recordPlaying = p_110326_1_;
/* 1127 */     this.recordPlayingUpFor = 60;
/* 1128 */     this.recordIsPlaying = p_110326_2_;
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayTitle(String title, String subTitle, int fadeInTime, int displayTime, int fadeOutTime) {
/* 1133 */     if (title == null && subTitle == null && fadeInTime < 0 && displayTime < 0 && fadeOutTime < 0) {
/*      */       
/* 1135 */       this.titleText = "";
/* 1136 */       this.subtitleText = "";
/* 1137 */       this.field_175195_w = 0;
/*      */     }
/* 1139 */     else if (title != null) {
/*      */       
/* 1141 */       this.titleText = title;
/* 1142 */       this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
/*      */     }
/* 1144 */     else if (subTitle != null) {
/*      */       
/* 1146 */       this.subtitleText = subTitle;
/*      */     }
/*      */     else {
/*      */       
/* 1150 */       if (fadeInTime >= 0)
/*      */       {
/* 1152 */         this.field_175199_z = fadeInTime;
/*      */       }
/*      */       
/* 1155 */       if (displayTime >= 0)
/*      */       {
/* 1157 */         this.field_175192_A = displayTime;
/*      */       }
/*      */       
/* 1160 */       if (fadeOutTime >= 0)
/*      */       {
/* 1162 */         this.field_175193_B = fadeOutTime;
/*      */       }
/*      */       
/* 1165 */       if (this.field_175195_w > 0)
/*      */       {
/* 1167 */         this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRecordPlaying(IChatComponent p_175188_1_, boolean p_175188_2_) {
/* 1174 */     setRecordPlaying(p_175188_1_.getUnformattedText(), p_175188_2_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiNewChat getChatGUI() {
/* 1182 */     return this.persistantChatGUI;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUpdateCounter() {
/* 1187 */     return this.updateCounter;
/*      */   }
/*      */ 
/*      */   
/*      */   public FontRenderer getFontRenderer() {
/* 1192 */     return this.mc.MCfontRenderer;
/*      */   }
/*      */ 
/*      */   
/*      */   public GuiSpectator getSpectatorGui() {
/* 1197 */     return this.spectatorGui;
/*      */   }
/*      */ 
/*      */   
/*      */   public GuiPlayerTabOverlay getTabList() {
/* 1202 */     return this.overlayPlayerList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181029_i() {
/* 1207 */     this.overlayPlayerList.func_181030_a();
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiIngame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */