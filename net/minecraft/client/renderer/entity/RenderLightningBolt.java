/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderLightningBolt
/*     */   extends Render<EntityLightningBolt> {
/*     */   public RenderLightningBolt(RenderManager renderManagerIn) {
/*  15 */     super(renderManagerIn);
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
/*     */   public void doRender(EntityLightningBolt entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  28 */     Tessellator tessellator = Tessellator.getInstance();
/*  29 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  30 */     GlStateManager.disableTexture2D();
/*  31 */     GlStateManager.disableLighting();
/*  32 */     GlStateManager.enableBlend();
/*  33 */     GlStateManager.blendFunc(770, 1);
/*  34 */     double[] adouble = new double[8];
/*  35 */     double[] adouble1 = new double[8];
/*  36 */     double d0 = 0.0D;
/*  37 */     double d1 = 0.0D;
/*  38 */     Random random = new Random(entity.boltVertex);
/*     */     
/*  40 */     for (int i = 7; i >= 0; i--) {
/*     */       
/*  42 */       adouble[i] = d0;
/*  43 */       adouble1[i] = d1;
/*  44 */       d0 += (random.nextInt(11) - 5);
/*  45 */       d1 += (random.nextInt(11) - 5);
/*     */     } 
/*     */     
/*  48 */     for (int k1 = 0; k1 < 4; k1++) {
/*     */       
/*  50 */       Random random1 = new Random(entity.boltVertex);
/*     */       
/*  52 */       for (int j = 0; j < 3; j++) {
/*     */         
/*  54 */         int k = 7;
/*  55 */         int l = 0;
/*     */         
/*  57 */         if (j > 0)
/*     */         {
/*  59 */           k = 7 - j;
/*     */         }
/*     */         
/*  62 */         if (j > 0)
/*     */         {
/*  64 */           l = k - 2;
/*     */         }
/*     */         
/*  67 */         double d2 = adouble[k] - d0;
/*  68 */         double d3 = adouble1[k] - d1;
/*     */         
/*  70 */         for (int i1 = k; i1 >= l; i1--) {
/*     */           
/*  72 */           double d4 = d2;
/*  73 */           double d5 = d3;
/*     */           
/*  75 */           if (j == 0) {
/*     */             
/*  77 */             d2 += (random1.nextInt(11) - 5);
/*  78 */             d3 += (random1.nextInt(11) - 5);
/*     */           }
/*     */           else {
/*     */             
/*  82 */             d2 += (random1.nextInt(31) - 15);
/*  83 */             d3 += (random1.nextInt(31) - 15);
/*     */           } 
/*     */           
/*  86 */           worldrenderer.begin(5, DefaultVertexFormats.field_181706_f);
/*  87 */           float f = 0.5F;
/*  88 */           float f1 = 0.45F;
/*  89 */           float f2 = 0.45F;
/*  90 */           float f3 = 0.5F;
/*  91 */           double d6 = 0.1D + k1 * 0.2D;
/*     */           
/*  93 */           if (j == 0)
/*     */           {
/*  95 */             d6 *= i1 * 0.1D + 1.0D;
/*     */           }
/*     */           
/*  98 */           double d7 = 0.1D + k1 * 0.2D;
/*     */           
/* 100 */           if (j == 0)
/*     */           {
/* 102 */             d7 *= (i1 - 1) * 0.1D + 1.0D;
/*     */           }
/*     */           
/* 105 */           for (int j1 = 0; j1 < 5; j1++) {
/*     */             
/* 107 */             double d8 = x + 0.5D - d6;
/* 108 */             double d9 = z + 0.5D - d6;
/*     */             
/* 110 */             if (j1 == 1 || j1 == 2)
/*     */             {
/* 112 */               d8 += d6 * 2.0D;
/*     */             }
/*     */             
/* 115 */             if (j1 == 2 || j1 == 3)
/*     */             {
/* 117 */               d9 += d6 * 2.0D;
/*     */             }
/*     */             
/* 120 */             double d10 = x + 0.5D - d7;
/* 121 */             double d11 = z + 0.5D - d7;
/*     */             
/* 123 */             if (j1 == 1 || j1 == 2)
/*     */             {
/* 125 */               d10 += d7 * 2.0D;
/*     */             }
/*     */             
/* 128 */             if (j1 == 2 || j1 == 3)
/*     */             {
/* 130 */               d11 += d7 * 2.0D;
/*     */             }
/*     */             
/* 133 */             worldrenderer.pos(d10 + d2, y + (i1 * 16), d11 + d3).func_181666_a(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
/* 134 */             worldrenderer.pos(d8 + d4, y + ((i1 + 1) * 16), d9 + d5).func_181666_a(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
/*     */           } 
/*     */           
/* 137 */           tessellator.draw();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     GlStateManager.disableBlend();
/* 143 */     GlStateManager.enableLighting();
/* 144 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityLightningBolt entity) {
/* 152 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderLightningBolt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */