/*     */ package net.minecraft.client.gui.achievement;
/*     */ 
/*     */ import cc.slack.Slack;
/*     */ import cc.slack.features.modules.impl.other.Tweaks;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class GuiAchievement
/*     */   extends Gui {
/*  17 */   private static final ResourceLocation achievementBg = new ResourceLocation("textures/gui/achievement/achievement_background.png");
/*     */   
/*     */   private Minecraft mc;
/*     */   private int width;
/*     */   private int height;
/*     */   private String achievementTitle;
/*     */   private String achievementDescription;
/*     */   private Achievement theAchievement;
/*     */   private long notificationTime;
/*     */   private RenderItem renderItem;
/*     */   private boolean permanentNotification;
/*     */   
/*     */   public GuiAchievement(Minecraft mc) {
/*  30 */     this.mc = mc;
/*  31 */     this.renderItem = mc.getRenderItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayAchievement(Achievement ach) {
/*  36 */     this.achievementTitle = I18n.format("achievement.get", new Object[0]);
/*  37 */     this.achievementDescription = ach.getStatName().getUnformattedText();
/*  38 */     this.notificationTime = Minecraft.getSystemTime();
/*  39 */     this.theAchievement = ach;
/*  40 */     this.permanentNotification = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void displayUnformattedAchievement(Achievement achievementIn) {
/*  45 */     this.achievementTitle = achievementIn.getStatName().getUnformattedText();
/*  46 */     this.achievementDescription = achievementIn.getDescription();
/*  47 */     this.notificationTime = Minecraft.getSystemTime() + 2500L;
/*  48 */     this.theAchievement = achievementIn;
/*  49 */     this.permanentNotification = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateAchievementWindowScale() {
/*  54 */     GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/*  55 */     GlStateManager.matrixMode(5889);
/*  56 */     GlStateManager.loadIdentity();
/*  57 */     GlStateManager.matrixMode(5888);
/*  58 */     GlStateManager.loadIdentity();
/*  59 */     this.width = this.mc.displayWidth;
/*  60 */     this.height = this.mc.displayHeight;
/*  61 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  62 */     this.width = scaledresolution.getScaledWidth();
/*  63 */     this.height = scaledresolution.getScaledHeight();
/*  64 */     GlStateManager.clear(256);
/*  65 */     GlStateManager.matrixMode(5889);
/*  66 */     GlStateManager.loadIdentity();
/*  67 */     GlStateManager.ortho(0.0D, this.width, this.height, 0.0D, 1000.0D, 3000.0D);
/*  68 */     GlStateManager.matrixMode(5888);
/*  69 */     GlStateManager.loadIdentity();
/*  70 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAchievementWindow() {
/*  75 */     if (this.theAchievement != null && this.notificationTime != 0L && (Minecraft.getMinecraft()).thePlayer != null && !((Boolean)((Tweaks)Slack.getInstance().getModuleManager().getInstance(Tweaks.class)).noachievement.getValue()).booleanValue()) {
/*     */       
/*  77 */       double d0 = (Minecraft.getSystemTime() - this.notificationTime) / 3000.0D;
/*     */       
/*  79 */       if (!this.permanentNotification) {
/*     */         
/*  81 */         if (d0 < 0.0D || d0 > 1.0D) {
/*     */           
/*  83 */           this.notificationTime = 0L;
/*     */           
/*     */           return;
/*     */         } 
/*  87 */       } else if (d0 > 0.5D) {
/*     */         
/*  89 */         d0 = 0.5D;
/*     */       } 
/*     */       
/*  92 */       updateAchievementWindowScale();
/*  93 */       GlStateManager.disableDepth();
/*  94 */       GlStateManager.depthMask(false);
/*  95 */       double d1 = d0 * 2.0D;
/*     */       
/*  97 */       if (d1 > 1.0D)
/*     */       {
/*  99 */         d1 = 2.0D - d1;
/*     */       }
/*     */       
/* 102 */       d1 *= 4.0D;
/* 103 */       d1 = 1.0D - d1;
/*     */       
/* 105 */       if (d1 < 0.0D)
/*     */       {
/* 107 */         d1 = 0.0D;
/*     */       }
/*     */       
/* 110 */       d1 *= d1;
/* 111 */       d1 *= d1;
/* 112 */       int i = this.width - 160;
/* 113 */       int j = 0 - (int)(d1 * 36.0D);
/* 114 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 115 */       GlStateManager.enableTexture2D();
/* 116 */       this.mc.getTextureManager().bindTexture(achievementBg);
/* 117 */       GlStateManager.disableLighting();
/* 118 */       drawTexturedModalRect(i, j, 96, 202, 160, 32);
/*     */       
/* 120 */       if (this.permanentNotification) {
/*     */         
/* 122 */         this.mc.MCfontRenderer.drawSplitString(this.achievementDescription, i + 30, j + 7, 120, -1);
/*     */       }
/*     */       else {
/*     */         
/* 126 */         this.mc.MCfontRenderer.drawString(this.achievementTitle, i + 30, j + 7, -256);
/* 127 */         this.mc.MCfontRenderer.drawString(this.achievementDescription, i + 30, j + 18, -1);
/*     */       } 
/*     */       
/* 130 */       RenderHelper.enableGUIStandardItemLighting();
/* 131 */       GlStateManager.disableLighting();
/* 132 */       GlStateManager.enableRescaleNormal();
/* 133 */       GlStateManager.enableColorMaterial();
/* 134 */       GlStateManager.enableLighting();
/* 135 */       this.renderItem.renderItemAndEffectIntoGUI(this.theAchievement.theItemStack, i + 8, j + 8);
/* 136 */       GlStateManager.disableLighting();
/* 137 */       GlStateManager.depthMask(true);
/* 138 */       GlStateManager.enableDepth();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearAchievements() {
/* 144 */     this.theAchievement = null;
/* 145 */     this.notificationTime = 0L;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\achievement\GuiAchievement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */