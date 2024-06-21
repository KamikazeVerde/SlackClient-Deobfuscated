/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.SVertexBuilder;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldVertexBufferUploader
/*     */ {
/*     */   public void func_181679_a(WorldRenderer p_181679_1_) {
/*  17 */     if (p_181679_1_.getVertexCount() > 0) {
/*     */       
/*  19 */       if (p_181679_1_.getDrawMode() == 7 && Config.isQuadsToTriangles())
/*     */       {
/*  21 */         p_181679_1_.quadsToTriangles();
/*     */       }
/*     */       
/*  24 */       VertexFormat vertexformat = p_181679_1_.getVertexFormat();
/*  25 */       int i = vertexformat.getNextOffset();
/*  26 */       ByteBuffer bytebuffer = p_181679_1_.getByteBuffer();
/*  27 */       List<VertexFormatElement> list = vertexformat.getElements();
/*  28 */       boolean flag = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
/*  29 */       boolean flag1 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();
/*     */       
/*  31 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/*  33 */         VertexFormatElement vertexformatelement = list.get(j);
/*  34 */         VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
/*     */         
/*  36 */         if (flag) {
/*     */           
/*  38 */           Reflector.callVoid(vertexformatelement$enumusage, Reflector.ForgeVertexFormatElementEnumUseage_preDraw, new Object[] { vertexformat, Integer.valueOf(j), Integer.valueOf(i), bytebuffer });
/*     */         }
/*     */         else {
/*     */           
/*  42 */           int k = vertexformatelement.getType().getGlConstant();
/*  43 */           int l = vertexformatelement.getIndex();
/*  44 */           bytebuffer.position(vertexformat.func_181720_d(j));
/*     */           
/*  46 */           switch (vertexformatelement$enumusage) {
/*     */             
/*     */             case POSITION:
/*  49 */               GL11.glVertexPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
/*  50 */               GL11.glEnableClientState(32884);
/*     */               break;
/*     */             
/*     */             case UV:
/*  54 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + l);
/*  55 */               GL11.glTexCoordPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
/*  56 */               GL11.glEnableClientState(32888);
/*  57 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */               break;
/*     */             
/*     */             case COLOR:
/*  61 */               GL11.glColorPointer(vertexformatelement.getElementCount(), k, i, bytebuffer);
/*  62 */               GL11.glEnableClientState(32886);
/*     */               break;
/*     */             
/*     */             case NORMAL:
/*  66 */               GL11.glNormalPointer(k, i, bytebuffer);
/*  67 */               GL11.glEnableClientState(32885);
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*  72 */       if (p_181679_1_.isMultiTexture()) {
/*     */         
/*  74 */         p_181679_1_.drawMultiTexture();
/*     */       }
/*  76 */       else if (Config.isShaders()) {
/*     */         
/*  78 */         SVertexBuilder.drawArrays(p_181679_1_.getDrawMode(), 0, p_181679_1_.getVertexCount(), p_181679_1_);
/*     */       }
/*     */       else {
/*     */         
/*  82 */         GL11.glDrawArrays(p_181679_1_.getDrawMode(), 0, p_181679_1_.getVertexCount());
/*     */       } 
/*     */       
/*  85 */       int j1 = 0;
/*     */       
/*  87 */       for (int k1 = list.size(); j1 < k1; j1++) {
/*     */         
/*  89 */         VertexFormatElement vertexformatelement1 = list.get(j1);
/*  90 */         VertexFormatElement.EnumUsage vertexformatelement$enumusage1 = vertexformatelement1.getUsage();
/*     */         
/*  92 */         if (flag1) {
/*     */           
/*  94 */           Reflector.callVoid(vertexformatelement$enumusage1, Reflector.ForgeVertexFormatElementEnumUseage_postDraw, new Object[] { vertexformat, Integer.valueOf(j1), Integer.valueOf(i), bytebuffer });
/*     */         }
/*     */         else {
/*     */           
/*  98 */           int i1 = vertexformatelement1.getIndex();
/*     */           
/* 100 */           switch (vertexformatelement$enumusage1) {
/*     */             
/*     */             case POSITION:
/* 103 */               GL11.glDisableClientState(32884);
/*     */               break;
/*     */             
/*     */             case UV:
/* 107 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i1);
/* 108 */               GL11.glDisableClientState(32888);
/* 109 */               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*     */               break;
/*     */             
/*     */             case COLOR:
/* 113 */               GL11.glDisableClientState(32886);
/* 114 */               GlStateManager.resetColor();
/*     */               break;
/*     */             
/*     */             case NORMAL:
/* 118 */               GL11.glDisableClientState(32885);
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 124 */     p_181679_1_.reset();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\WorldVertexBufferUploader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */