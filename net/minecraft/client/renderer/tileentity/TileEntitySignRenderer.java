/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiUtilRenderComponents;
/*     */ import net.minecraft.client.model.ModelSign;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TileEntitySignRenderer extends TileEntitySpecialRenderer<TileEntitySign> {
/*  22 */   private static final ResourceLocation SIGN_TEXTURE = new ResourceLocation("textures/entity/sign.png");
/*     */ 
/*     */   
/*  25 */   private final ModelSign model = new ModelSign();
/*  26 */   private static double textRenderDistanceSq = 4096.0D;
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  30 */     Block block = te.getBlockType();
/*  31 */     GlStateManager.pushMatrix();
/*  32 */     float f = 0.6666667F;
/*     */     
/*  34 */     if (block == Blocks.standing_sign) {
/*     */       
/*  36 */       GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F * f, (float)z + 0.5F);
/*  37 */       float f1 = (te.getBlockMetadata() * 360) / 16.0F;
/*  38 */       GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
/*  39 */       this.model.signStick.showModel = true;
/*     */     }
/*     */     else {
/*     */       
/*  43 */       int k = te.getBlockMetadata();
/*  44 */       float f2 = 0.0F;
/*     */       
/*  46 */       if (k == 2)
/*     */       {
/*  48 */         f2 = 180.0F;
/*     */       }
/*     */       
/*  51 */       if (k == 4)
/*     */       {
/*  53 */         f2 = 90.0F;
/*     */       }
/*     */       
/*  56 */       if (k == 5)
/*     */       {
/*  58 */         f2 = -90.0F;
/*     */       }
/*     */       
/*  61 */       GlStateManager.translate((float)x + 0.5F, (float)y + 0.75F * f, (float)z + 0.5F);
/*  62 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  63 */       GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
/*  64 */       this.model.signStick.showModel = false;
/*     */     } 
/*     */     
/*  67 */     if (destroyStage >= 0) {
/*     */       
/*  69 */       bindTexture(DESTROY_STAGES[destroyStage]);
/*  70 */       GlStateManager.matrixMode(5890);
/*  71 */       GlStateManager.pushMatrix();
/*  72 */       GlStateManager.scale(4.0F, 2.0F, 1.0F);
/*  73 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  74 */       GlStateManager.matrixMode(5888);
/*     */     }
/*     */     else {
/*     */       
/*  78 */       bindTexture(SIGN_TEXTURE);
/*     */     } 
/*     */     
/*  81 */     GlStateManager.enableRescaleNormal();
/*  82 */     GlStateManager.pushMatrix();
/*  83 */     GlStateManager.scale(f, -f, -f);
/*  84 */     this.model.renderSign();
/*  85 */     GlStateManager.popMatrix();
/*     */     
/*  87 */     if (isRenderText(te)) {
/*     */       
/*  89 */       FontRenderer fontrenderer = getFontRenderer();
/*  90 */       float f3 = 0.015625F * f;
/*  91 */       GlStateManager.translate(0.0F, 0.5F * f, 0.07F * f);
/*  92 */       GlStateManager.scale(f3, -f3, f3);
/*  93 */       GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
/*  94 */       GlStateManager.depthMask(false);
/*  95 */       int i = 0;
/*     */       
/*  97 */       if (Config.isCustomColors())
/*     */       {
/*  99 */         i = CustomColors.getSignTextColor(i);
/*     */       }
/*     */       
/* 102 */       if (destroyStage < 0)
/*     */       {
/* 104 */         for (int j = 0; j < te.signText.length; j++) {
/*     */           
/* 106 */           if (te.signText[j] != null) {
/*     */             
/* 108 */             IChatComponent ichatcomponent = te.signText[j];
/* 109 */             List<IChatComponent> list = GuiUtilRenderComponents.func_178908_a(ichatcomponent, 90, fontrenderer, false, true);
/* 110 */             String s = (list != null && list.size() > 0) ? ((IChatComponent)list.get(0)).getFormattedText() : "";
/*     */             
/* 112 */             if (j == te.lineBeingEdited) {
/*     */               
/* 114 */               s = "> " + s + " <";
/* 115 */               fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, i);
/*     */             }
/*     */             else {
/*     */               
/* 119 */               fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, j * 10 - te.signText.length * 5, i);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 126 */     GlStateManager.depthMask(true);
/* 127 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 128 */     GlStateManager.popMatrix();
/*     */     
/* 130 */     if (destroyStage >= 0) {
/*     */       
/* 132 */       GlStateManager.matrixMode(5890);
/* 133 */       GlStateManager.popMatrix();
/* 134 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isRenderText(TileEntitySign p_isRenderText_0_) {
/* 140 */     if (Shaders.isShadowPass)
/*     */     {
/* 142 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 146 */     if (!Config.zoomMode && p_isRenderText_0_.lineBeingEdited < 0) {
/*     */       
/* 148 */       Entity entity = Config.getMinecraft().getRenderViewEntity();
/* 149 */       double d0 = p_isRenderText_0_.getDistanceSq(entity.posX, entity.posY, entity.posZ);
/*     */       
/* 151 */       if (d0 > textRenderDistanceSq)
/*     */       {
/* 153 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateTextRenderDistance() {
/* 163 */     Minecraft minecraft = Config.getMinecraft();
/* 164 */     double d0 = Config.limit(minecraft.gameSettings.fovSetting, 1.0F, 120.0F);
/* 165 */     double d1 = Math.max(1.5D * minecraft.displayHeight / d0, 16.0D);
/* 166 */     textRenderDistanceSq = d1 * d1;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\tileentity\TileEntitySignRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */