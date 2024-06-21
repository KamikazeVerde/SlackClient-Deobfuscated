/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.http.HttpPipeline;
/*     */ import net.optifine.http.HttpRequest;
/*     */ import net.optifine.http.HttpResponse;
/*     */ import net.optifine.player.CapeImageBuffer;
/*     */ import net.optifine.shaders.ShadersTex;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ThreadDownloadImageData
/*     */   extends SimpleTexture
/*     */ {
/*  30 */   private static final Logger logger = LogManager.getLogger();
/*  31 */   private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
/*     */   private final File cacheFile;
/*     */   private final String imageUrl;
/*     */   private final IImageBuffer imageBuffer;
/*     */   private BufferedImage bufferedImage;
/*     */   private Thread imageThread;
/*     */   private boolean textureUploaded;
/*  38 */   public Boolean imageFound = null;
/*     */   
/*     */   public boolean pipeline = false;
/*     */   
/*     */   public ThreadDownloadImageData(File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation, IImageBuffer imageBufferIn) {
/*  43 */     super(textureResourceLocation);
/*  44 */     this.cacheFile = cacheFileIn;
/*  45 */     this.imageUrl = imageUrlIn;
/*  46 */     this.imageBuffer = imageBufferIn;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkTextureUploaded() {
/*  51 */     if (!this.textureUploaded && this.bufferedImage != null) {
/*     */       
/*  53 */       this.textureUploaded = true;
/*     */       
/*  55 */       if (this.textureLocation != null)
/*     */       {
/*  57 */         deleteGlTexture();
/*     */       }
/*     */       
/*  60 */       if (Config.isShaders()) {
/*     */         
/*  62 */         ShadersTex.loadSimpleTexture(super.getGlTextureId(), this.bufferedImage, false, false, Config.getResourceManager(), this.textureLocation, getMultiTexID());
/*     */       }
/*     */       else {
/*     */         
/*  66 */         TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGlTextureId() {
/*  73 */     checkTextureUploaded();
/*  74 */     return super.getGlTextureId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBufferedImage(BufferedImage bufferedImageIn) {
/*  79 */     this.bufferedImage = bufferedImageIn;
/*     */     
/*  81 */     if (this.imageBuffer != null)
/*     */     {
/*  83 */       this.imageBuffer.skinAvailable();
/*     */     }
/*     */     
/*  86 */     this.imageFound = Boolean.valueOf((this.bufferedImage != null));
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/*  91 */     if (this.bufferedImage == null && this.textureLocation != null)
/*     */     {
/*  93 */       super.loadTexture(resourceManager);
/*     */     }
/*     */     
/*  96 */     if (this.imageThread == null)
/*     */     {
/*  98 */       if (this.cacheFile != null && this.cacheFile.isFile()) {
/*     */         
/* 100 */         logger.debug("Loading http texture from local cache ({})", new Object[] { this.cacheFile });
/*     */ 
/*     */         
/*     */         try {
/* 104 */           this.bufferedImage = ImageIO.read(this.cacheFile);
/*     */           
/* 106 */           if (this.imageBuffer != null)
/*     */           {
/* 108 */             setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
/*     */           }
/*     */           
/* 111 */           loadingFinished();
/*     */         }
/* 113 */         catch (IOException ioexception) {
/*     */           
/* 115 */           logger.error("Couldn't load skin " + this.cacheFile, ioexception);
/* 116 */           loadTextureFromServer();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 121 */         loadTextureFromServer();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadTextureFromServer() {
/* 128 */     this.imageThread = new Thread("Texture Downloader #" + threadDownloadCounter.incrementAndGet())
/*     */       {
/*     */         public void run()
/*     */         {
/* 132 */           HttpURLConnection httpurlconnection = null;
/* 133 */           ThreadDownloadImageData.logger.debug("Downloading http texture from {} to {}", new Object[] { ThreadDownloadImageData.access$000(this.this$0), ThreadDownloadImageData.access$100(this.this$0) });
/*     */           
/* 135 */           if (ThreadDownloadImageData.this.shouldPipeline()) {
/*     */             
/* 137 */             ThreadDownloadImageData.this.loadPipelined();
/*     */           } else {
/*     */             try {
/*     */               BufferedImage bufferedimage;
/*     */ 
/*     */               
/* 143 */               httpurlconnection = (HttpURLConnection)(new URL(ThreadDownloadImageData.this.imageUrl)).openConnection(Minecraft.getMinecraft().getProxy());
/* 144 */               httpurlconnection.setDoInput(true);
/* 145 */               httpurlconnection.setDoOutput(false);
/* 146 */               httpurlconnection.connect();
/*     */               
/* 148 */               if (httpurlconnection.getResponseCode() / 100 != 2) {
/*     */                 
/* 150 */                 if (httpurlconnection.getErrorStream() != null)
/*     */                 {
/* 152 */                   Config.readAll(httpurlconnection.getErrorStream());
/*     */                 }
/*     */ 
/*     */                 
/*     */                 return;
/*     */               } 
/*     */ 
/*     */               
/* 160 */               if (ThreadDownloadImageData.this.cacheFile != null) {
/*     */                 
/* 162 */                 FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), ThreadDownloadImageData.this.cacheFile);
/* 163 */                 bufferedimage = ImageIO.read(ThreadDownloadImageData.this.cacheFile);
/*     */               }
/*     */               else {
/*     */                 
/* 167 */                 bufferedimage = TextureUtil.readBufferedImage(httpurlconnection.getInputStream());
/*     */               } 
/*     */               
/* 170 */               if (ThreadDownloadImageData.this.imageBuffer != null)
/*     */               {
/* 172 */                 bufferedimage = ThreadDownloadImageData.this.imageBuffer.parseUserSkin(bufferedimage);
/*     */               }
/*     */               
/* 175 */               ThreadDownloadImageData.this.setBufferedImage(bufferedimage);
/*     */             }
/* 177 */             catch (Exception exception) {
/*     */               
/* 179 */               ThreadDownloadImageData.logger.error("Couldn't download http texture: " + exception.getClass().getName() + ": " + exception.getMessage());
/*     */ 
/*     */               
/*     */               return;
/*     */             } finally {
/* 184 */               if (httpurlconnection != null)
/*     */               {
/* 186 */                 httpurlconnection.disconnect();
/*     */               }
/*     */               
/* 189 */               ThreadDownloadImageData.this.loadingFinished();
/*     */             } 
/*     */           } 
/*     */         }
/*     */       };
/* 194 */     this.imageThread.setDaemon(true);
/* 195 */     this.imageThread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldPipeline() {
/* 200 */     if (!this.pipeline)
/*     */     {
/* 202 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 206 */     Proxy proxy = Minecraft.getMinecraft().getProxy();
/* 207 */     return (proxy.type() != Proxy.Type.DIRECT && proxy.type() != Proxy.Type.SOCKS) ? false : this.imageUrl.startsWith("http://");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadPipelined() {
/*     */     try {
/*     */       BufferedImage bufferedimage;
/* 215 */       HttpRequest httprequest = HttpPipeline.makeRequest(this.imageUrl, Minecraft.getMinecraft().getProxy());
/* 216 */       HttpResponse httpresponse = HttpPipeline.executeRequest(httprequest);
/*     */       
/* 218 */       if (httpresponse.getStatus() / 100 != 2) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 223 */       byte[] abyte = httpresponse.getBody();
/* 224 */       ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte);
/*     */ 
/*     */       
/* 227 */       if (this.cacheFile != null) {
/*     */         
/* 229 */         FileUtils.copyInputStreamToFile(bytearrayinputstream, this.cacheFile);
/* 230 */         bufferedimage = ImageIO.read(this.cacheFile);
/*     */       }
/*     */       else {
/*     */         
/* 234 */         bufferedimage = TextureUtil.readBufferedImage(bytearrayinputstream);
/*     */       } 
/*     */       
/* 237 */       if (this.imageBuffer != null)
/*     */       {
/* 239 */         bufferedimage = this.imageBuffer.parseUserSkin(bufferedimage);
/*     */       }
/*     */       
/* 242 */       setBufferedImage(bufferedimage);
/*     */     }
/* 244 */     catch (Exception exception) {
/*     */       
/* 246 */       logger.error("Couldn't download http texture: " + exception.getClass().getName() + ": " + exception.getMessage());
/*     */ 
/*     */       
/*     */       return;
/*     */     } finally {
/* 251 */       loadingFinished();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadingFinished() {
/* 257 */     this.imageFound = Boolean.valueOf((this.bufferedImage != null));
/*     */     
/* 259 */     if (this.imageBuffer instanceof CapeImageBuffer) {
/*     */       
/* 261 */       CapeImageBuffer capeimagebuffer = (CapeImageBuffer)this.imageBuffer;
/* 262 */       capeimagebuffer.cleanup();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IImageBuffer getImageBuffer() {
/* 268 */     return this.imageBuffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\ThreadDownloadImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */