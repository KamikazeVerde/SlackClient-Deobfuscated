/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerArrow;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerCape;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderPlayer
/*     */   extends RendererLivingEntity<AbstractClientPlayer> {
/*     */   private boolean smallArms;
/*     */   
/*     */   public RenderPlayer(RenderManager renderManager) {
/*  28 */     this(renderManager, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderPlayer(RenderManager renderManager, boolean useSmallArms) {
/*  33 */     super(renderManager, (ModelBase)new ModelPlayer(0.0F, useSmallArms), 0.5F);
/*  34 */     this.smallArms = useSmallArms;
/*  35 */     addLayer(new LayerBipedArmor(this));
/*  36 */     addLayer(new LayerHeldItem(this));
/*  37 */     addLayer(new LayerArrow(this));
/*  38 */     addLayer(new LayerDeadmau5Head(this));
/*  39 */     addLayer(new LayerCape(this));
/*  40 */     addLayer(new LayerCustomHead((getMainModel()).bipedHead));
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPlayer getMainModel() {
/*  45 */     return (ModelPlayer)super.getMainModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  58 */     if (!entity.isUser() || this.renderManager.livingPlayer == entity) {
/*     */       
/*  60 */       double d0 = y;
/*     */       
/*  62 */       if (entity.isSneaking() && !(entity instanceof net.minecraft.client.entity.EntityPlayerSP))
/*     */       {
/*  64 */         d0 = y - 0.125D;
/*     */       }
/*     */       
/*  67 */       setModelVisibilities(entity);
/*  68 */       super.doRender(entity, x, d0, z, entityYaw, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
/*  74 */     ModelPlayer modelplayer = getMainModel();
/*     */     
/*  76 */     if (clientPlayer.isSpectator()) {
/*     */       
/*  78 */       modelplayer.setInvisible(false);
/*  79 */       modelplayer.bipedHead.showModel = true;
/*  80 */       modelplayer.bipedHeadwear.showModel = true;
/*     */     }
/*     */     else {
/*     */       
/*  84 */       ItemStack itemstack = clientPlayer.inventory.getCurrentItem();
/*  85 */       modelplayer.setInvisible(true);
/*  86 */       modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
/*  87 */       modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
/*  88 */       modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
/*  89 */       modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
/*  90 */       modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
/*  91 */       modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
/*  92 */       modelplayer.heldItemLeft = 0;
/*  93 */       modelplayer.aimedBow = false;
/*  94 */       modelplayer.isSneak = clientPlayer.isSneaking();
/*     */       
/*  96 */       if (itemstack == null) {
/*     */         
/*  98 */         modelplayer.heldItemRight = 0;
/*     */       }
/*     */       else {
/*     */         
/* 102 */         modelplayer.heldItemRight = 1;
/*     */         
/* 104 */         if (clientPlayer.getItemInUseCount() > 0) {
/*     */           
/* 106 */           EnumAction enumaction = itemstack.getItemUseAction();
/*     */           
/* 108 */           if (enumaction == EnumAction.BLOCK) {
/*     */             
/* 110 */             modelplayer.heldItemRight = 3;
/*     */           }
/* 112 */           else if (enumaction == EnumAction.BOW) {
/*     */             
/* 114 */             modelplayer.aimedBow = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
/* 126 */     return entity.getLocationSkin();
/*     */   }
/*     */ 
/*     */   
/*     */   public void transformHeldFull3DItemLayer() {
/* 131 */     GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
/* 140 */     float f = 0.9375F;
/* 141 */     GlStateManager.scale(f, f, f);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderOffsetLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_) {
/* 146 */     if (p_177069_10_ < 100.0D) {
/*     */       
/* 148 */       Scoreboard scoreboard = entityIn.getWorldScoreboard();
/* 149 */       ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
/*     */       
/* 151 */       if (scoreobjective != null) {
/*     */         
/* 153 */         Score score = scoreboard.getValueFromObjective(entityIn.getCommandSenderName(), scoreobjective);
/* 154 */         renderLivingLabel(entityIn, score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z, 64);
/* 155 */         y += ((getFontRendererFromRenderManager()).FONT_HEIGHT * 1.15F * p_177069_9_);
/*     */       } 
/*     */     } 
/*     */     
/* 159 */     super.renderOffsetLivingLabel(entityIn, x, y, z, str, p_177069_9_, p_177069_10_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderRightArm(AbstractClientPlayer clientPlayer) {
/* 164 */     float f = 1.0F;
/* 165 */     GlStateManager.color(f, f, f);
/* 166 */     ModelPlayer modelplayer = getMainModel();
/* 167 */     setModelVisibilities(clientPlayer);
/* 168 */     modelplayer.swingProgress = 0.0F;
/* 169 */     modelplayer.isSneak = false;
/* 170 */     modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
/* 171 */     modelplayer.renderRightArm();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderLeftArm(AbstractClientPlayer clientPlayer) {
/* 176 */     float f = 1.0F;
/* 177 */     GlStateManager.color(f, f, f);
/* 178 */     ModelPlayer modelplayer = getMainModel();
/* 179 */     setModelVisibilities(clientPlayer);
/* 180 */     modelplayer.isSneak = false;
/* 181 */     modelplayer.swingProgress = 0.0F;
/* 182 */     modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
/* 183 */     modelplayer.renderLeftArm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingAt(AbstractClientPlayer entityLivingBaseIn, double x, double y, double z) {
/* 191 */     if (entityLivingBaseIn.isEntityAlive() && entityLivingBaseIn.isPlayerSleeping()) {
/*     */       
/* 193 */       super.renderLivingAt(entityLivingBaseIn, x + entityLivingBaseIn.renderOffsetX, y + entityLivingBaseIn.renderOffsetY, z + entityLivingBaseIn.renderOffsetZ);
/*     */     }
/*     */     else {
/*     */       
/* 197 */       super.renderLivingAt(entityLivingBaseIn, x, y, z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rotateCorpse(AbstractClientPlayer bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 203 */     if (bat.isEntityAlive() && bat.isPlayerSleeping()) {
/*     */       
/* 205 */       GlStateManager.rotate(bat.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
/* 206 */       GlStateManager.rotate(getDeathMaxRotation(bat), 0.0F, 0.0F, 1.0F);
/* 207 */       GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
/*     */     }
/*     */     else {
/*     */       
/* 211 */       super.rotateCorpse(bat, p_77043_2_, p_77043_3_, partialTicks);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */