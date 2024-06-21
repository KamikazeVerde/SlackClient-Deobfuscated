/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import net.optifine.texture.InternalFormat;
/*    */ import net.optifine.texture.PixelFormat;
/*    */ import net.optifine.texture.PixelType;
/*    */ import net.optifine.texture.TextureType;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.opengl.GL12;
/*    */ 
/*    */ public class CustomTextureRaw
/*    */   implements ICustomTexture
/*    */ {
/*    */   private TextureType type;
/*    */   private int textureUnit;
/*    */   private int textureId;
/*    */   
/*    */   public CustomTextureRaw(TextureType type, InternalFormat internalFormat, int width, int height, int depth, PixelFormat pixelFormat, PixelType pixelType, ByteBuffer data, int textureUnit) {
/* 19 */     this.type = type;
/* 20 */     this.textureUnit = textureUnit;
/* 21 */     this.textureId = GL11.glGenTextures();
/* 22 */     GL11.glBindTexture(getTarget(), this.textureId);
/*    */     
/* 24 */     switch (type) {
/*    */       
/*    */       case TEXTURE_1D:
/* 27 */         GL11.glTexImage1D(3552, 0, internalFormat.getId(), width, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 28 */         GL11.glTexParameteri(3552, 10242, 33071);
/* 29 */         GL11.glTexParameteri(3552, 10240, 9729);
/* 30 */         GL11.glTexParameteri(3552, 10241, 9729);
/*    */         break;
/*    */       
/*    */       case TEXTURE_2D:
/* 34 */         GL11.glTexImage2D(3553, 0, internalFormat.getId(), width, height, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 35 */         GL11.glTexParameteri(3553, 10242, 33071);
/* 36 */         GL11.glTexParameteri(3553, 10243, 33071);
/* 37 */         GL11.glTexParameteri(3553, 10240, 9729);
/* 38 */         GL11.glTexParameteri(3553, 10241, 9729);
/*    */         break;
/*    */       
/*    */       case TEXTURE_3D:
/* 42 */         GL12.glTexImage3D(32879, 0, internalFormat.getId(), width, height, depth, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 43 */         GL11.glTexParameteri(32879, 10242, 33071);
/* 44 */         GL11.glTexParameteri(32879, 10243, 33071);
/* 45 */         GL11.glTexParameteri(32879, 32882, 33071);
/* 46 */         GL11.glTexParameteri(32879, 10240, 9729);
/* 47 */         GL11.glTexParameteri(32879, 10241, 9729);
/*    */         break;
/*    */       
/*    */       case TEXTURE_RECTANGLE:
/* 51 */         GL11.glTexImage2D(34037, 0, internalFormat.getId(), width, height, 0, pixelFormat.getId(), pixelType.getId(), data);
/* 52 */         GL11.glTexParameteri(34037, 10242, 33071);
/* 53 */         GL11.glTexParameteri(34037, 10243, 33071);
/* 54 */         GL11.glTexParameteri(34037, 10240, 9729);
/* 55 */         GL11.glTexParameteri(34037, 10241, 9729);
/*    */         break;
/*    */     } 
/* 58 */     GL11.glBindTexture(getTarget(), 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTarget() {
/* 63 */     return this.type.getId();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureId() {
/* 68 */     return this.textureId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTextureUnit() {
/* 73 */     return this.textureUnit;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteTexture() {
/* 78 */     if (this.textureId > 0) {
/*    */       
/* 80 */       GL11.glDeleteTextures(this.textureId);
/* 81 */       this.textureId = 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\CustomTextureRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */