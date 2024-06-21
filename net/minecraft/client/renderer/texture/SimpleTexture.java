/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.EmissiveTextures;
/*    */ import net.optifine.shaders.ShadersTex;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class SimpleTexture
/*    */   extends AbstractTexture {
/* 18 */   private static final Logger logger = LogManager.getLogger();
/*    */   
/*    */   protected final ResourceLocation textureLocation;
/*    */   public ResourceLocation locationEmissive;
/*    */   public boolean isEmissive;
/*    */   
/*    */   public SimpleTexture(ResourceLocation textureResourceLocation) {
/* 25 */     this.textureLocation = textureResourceLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/* 30 */     deleteGlTexture();
/* 31 */     InputStream inputstream = null;
/*    */ 
/*    */     
/*    */     try {
/* 35 */       IResource iresource = resourceManager.getResource(this.textureLocation);
/* 36 */       inputstream = iresource.getInputStream();
/* 37 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/* 38 */       boolean flag = false;
/* 39 */       boolean flag1 = false;
/*    */       
/* 41 */       if (iresource.hasMetadata()) {
/*    */         
/*    */         try {
/*    */           
/* 45 */           TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
/*    */           
/* 47 */           if (texturemetadatasection != null)
/*    */           {
/* 49 */             flag = texturemetadatasection.getTextureBlur();
/* 50 */             flag1 = texturemetadatasection.getTextureClamp();
/*    */           }
/*    */         
/* 53 */         } catch (RuntimeException runtimeexception) {
/*    */           
/* 55 */           logger.warn("Failed reading metadata of: " + this.textureLocation, runtimeexception);
/*    */         } 
/*    */       }
/*    */       
/* 59 */       if (Config.isShaders()) {
/*    */         
/* 61 */         ShadersTex.loadSimpleTexture(getGlTextureId(), bufferedimage, flag, flag1, resourceManager, this.textureLocation, getMultiTexID());
/*    */       }
/*    */       else {
/*    */         
/* 65 */         TextureUtil.uploadTextureImageAllocate(getGlTextureId(), bufferedimage, flag, flag1);
/*    */       } 
/*    */       
/* 68 */       if (EmissiveTextures.isActive())
/*    */       {
/* 70 */         EmissiveTextures.loadTexture(this.textureLocation, this);
/*    */       }
/*    */     }
/*    */     finally {
/*    */       
/* 75 */       if (inputstream != null)
/*    */       {
/* 77 */         inputstream.close();
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\texture\SimpleTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */