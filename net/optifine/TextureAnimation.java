/*     */ package net.optifine;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.TextureUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TextureAnimation
/*     */ {
/*  15 */   private String srcTex = null;
/*  16 */   private String dstTex = null;
/*  17 */   ResourceLocation dstTexLoc = null;
/*  18 */   private int dstTextId = -1;
/*  19 */   private int dstX = 0;
/*  20 */   private int dstY = 0;
/*  21 */   private int frameWidth = 0;
/*  22 */   private int frameHeight = 0;
/*  23 */   private TextureAnimationFrame[] frames = null;
/*  24 */   private int currentFrameIndex = 0;
/*     */   private boolean interpolate = false;
/*  26 */   private int interpolateSkip = 0;
/*  27 */   private ByteBuffer interpolateData = null;
/*  28 */   byte[] srcData = null;
/*  29 */   private ByteBuffer imageData = null;
/*     */   
/*     */   private boolean active = true;
/*     */   private boolean valid = true;
/*     */   
/*     */   public TextureAnimation(String texFrom, byte[] srcData, String texTo, ResourceLocation locTexTo, int dstX, int dstY, int frameWidth, int frameHeight, Properties props) {
/*  35 */     this.srcTex = texFrom;
/*  36 */     this.dstTex = texTo;
/*  37 */     this.dstTexLoc = locTexTo;
/*  38 */     this.dstX = dstX;
/*  39 */     this.dstY = dstY;
/*  40 */     this.frameWidth = frameWidth;
/*  41 */     this.frameHeight = frameHeight;
/*  42 */     int i = frameWidth * frameHeight * 4;
/*     */     
/*  44 */     if (srcData.length % i != 0)
/*     */     {
/*  46 */       Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameWidth + ", frameHeight: " + frameHeight);
/*     */     }
/*     */     
/*  49 */     this.srcData = srcData;
/*  50 */     int j = srcData.length / i;
/*     */     
/*  52 */     if (props.get("tile.0") != null)
/*     */     {
/*  54 */       for (int k = 0; props.get("tile." + k) != null; k++)
/*     */       {
/*  56 */         j = k + 1;
/*     */       }
/*     */     }
/*     */     
/*  60 */     String s2 = (String)props.get("duration");
/*  61 */     int l = Math.max(Config.parseInt(s2, 1), 1);
/*  62 */     this.frames = new TextureAnimationFrame[j];
/*     */     
/*  64 */     for (int i1 = 0; i1 < this.frames.length; i1++) {
/*     */       
/*  66 */       String s = (String)props.get("tile." + i1);
/*  67 */       int j1 = Config.parseInt(s, i1);
/*  68 */       String s1 = (String)props.get("duration." + i1);
/*  69 */       int k1 = Math.max(Config.parseInt(s1, l), 1);
/*  70 */       TextureAnimationFrame textureanimationframe = new TextureAnimationFrame(j1, k1);
/*  71 */       this.frames[i1] = textureanimationframe;
/*     */     } 
/*     */     
/*  74 */     this.interpolate = Config.parseBoolean(props.getProperty("interpolate"), false);
/*  75 */     this.interpolateSkip = Config.parseInt(props.getProperty("skip"), 0);
/*     */     
/*  77 */     if (this.interpolate)
/*     */     {
/*  79 */       this.interpolateData = GLAllocation.createDirectByteBuffer(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean nextFrame() {
/*  85 */     TextureAnimationFrame textureanimationframe = getCurrentFrame();
/*     */     
/*  87 */     if (textureanimationframe == null)
/*     */     {
/*  89 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  93 */     textureanimationframe.counter++;
/*     */     
/*  95 */     if (textureanimationframe.counter < textureanimationframe.duration)
/*     */     {
/*  97 */       return this.interpolate;
/*     */     }
/*     */ 
/*     */     
/* 101 */     textureanimationframe.counter = 0;
/* 102 */     this.currentFrameIndex++;
/*     */     
/* 104 */     if (this.currentFrameIndex >= this.frames.length)
/*     */     {
/* 106 */       this.currentFrameIndex = 0;
/*     */     }
/*     */     
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureAnimationFrame getCurrentFrame() {
/* 116 */     return getFrame(this.currentFrameIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAnimationFrame getFrame(int index) {
/* 121 */     if (this.frames.length <= 0)
/*     */     {
/* 123 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 127 */     if (index < 0 || index >= this.frames.length)
/*     */     {
/* 129 */       index = 0;
/*     */     }
/*     */     
/* 132 */     TextureAnimationFrame textureanimationframe = this.frames[index];
/* 133 */     return textureanimationframe;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/* 139 */     return this.frames.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTexture() {
/* 144 */     if (this.valid) {
/*     */       
/* 146 */       if (this.dstTextId < 0) {
/*     */         
/* 148 */         ITextureObject itextureobject = TextureUtils.getTexture(this.dstTexLoc);
/*     */         
/* 150 */         if (itextureobject == null) {
/*     */           
/* 152 */           this.valid = false;
/*     */           
/*     */           return;
/*     */         } 
/* 156 */         this.dstTextId = itextureobject.getGlTextureId();
/*     */       } 
/*     */       
/* 159 */       if (this.imageData == null) {
/*     */         
/* 161 */         this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length);
/* 162 */         this.imageData.put(this.srcData);
/* 163 */         this.imageData.flip();
/* 164 */         this.srcData = null;
/*     */       } 
/*     */       
/* 167 */       this.active = SmartAnimations.isActive() ? SmartAnimations.isTextureRendered(this.dstTextId) : true;
/*     */       
/* 169 */       if (nextFrame())
/*     */       {
/* 171 */         if (this.active) {
/*     */           
/* 173 */           int j = this.frameWidth * this.frameHeight * 4;
/* 174 */           TextureAnimationFrame textureanimationframe = getCurrentFrame();
/*     */           
/* 176 */           if (textureanimationframe != null) {
/*     */             
/* 178 */             int i = j * textureanimationframe.index;
/*     */             
/* 180 */             if (i + j <= this.imageData.limit())
/*     */             {
/* 182 */               if (this.interpolate && textureanimationframe.counter > 0) {
/*     */                 
/* 184 */                 if (this.interpolateSkip <= 1 || textureanimationframe.counter % this.interpolateSkip == 0)
/*     */                 {
/* 186 */                   TextureAnimationFrame textureanimationframe1 = getFrame(this.currentFrameIndex + 1);
/* 187 */                   double d0 = 1.0D * textureanimationframe.counter / textureanimationframe.duration;
/* 188 */                   updateTextureInerpolate(textureanimationframe, textureanimationframe1, d0);
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 193 */                 this.imageData.position(i);
/* 194 */                 GlStateManager.bindTexture(this.dstTextId);
/* 195 */                 GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateTextureInerpolate(TextureAnimationFrame frame1, TextureAnimationFrame frame2, double dd) {
/* 205 */     int i = this.frameWidth * this.frameHeight * 4;
/* 206 */     int j = i * frame1.index;
/*     */     
/* 208 */     if (j + i <= this.imageData.limit()) {
/* 209 */       int k = i * frame2.index;
/*     */       
/* 211 */       if (k + i <= this.imageData.limit()) {
/* 212 */         this.interpolateData.clear();
/*     */         
/* 214 */         for (int l = 0; l < i; l++) {
/* 215 */           int i1 = this.imageData.get(j + l) & 0xFF;
/* 216 */           int j1 = this.imageData.get(k + l) & 0xFF;
/* 217 */           int k1 = mix(i1, j1, dd);
/* 218 */           byte b0 = (byte)k1;
/* 219 */           this.interpolateData.put(b0);
/*     */         } 
/*     */         
/* 222 */         this.interpolateData.flip();
/* 223 */         GlStateManager.bindTexture(this.dstTextId);
/* 224 */         GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.interpolateData);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int mix(int col1, int col2, double k) {
/* 231 */     return (int)(col1 * (1.0D - k) + col2 * k);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSrcTex() {
/* 236 */     return this.srcTex;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDstTex() {
/* 241 */     return this.dstTex;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getDstTexLoc() {
/* 246 */     return this.dstTexLoc;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 251 */     return this.active;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\TextureAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */