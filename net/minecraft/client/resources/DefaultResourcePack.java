/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.reflect.ReflectorForge;
/*    */ 
/*    */ public class DefaultResourcePack
/*    */   implements IResourcePack
/*    */ {
/* 20 */   public static final Set<String> defaultResourceDomains = (Set<String>)ImmutableSet.of("minecraft");
/*    */   
/*    */   private final Map<String, File> mapAssets;
/*    */   
/*    */   public DefaultResourcePack(Map<String, File> mapAssetsIn) {
/* 25 */     this.mapAssets = mapAssetsIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream getInputStream(ResourceLocation location) throws IOException {
/* 30 */     InputStream inputstream = getResourceStream(location);
/*    */     
/* 32 */     if (inputstream != null)
/*    */     {
/* 34 */       return inputstream;
/*    */     }
/*    */ 
/*    */     
/* 38 */     InputStream inputstream1 = getInputStreamAssets(location);
/*    */     
/* 40 */     if (inputstream1 != null)
/*    */     {
/* 42 */       return inputstream1;
/*    */     }
/*    */ 
/*    */     
/* 46 */     throw new FileNotFoundException(location.getResourcePath());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getInputStreamAssets(ResourceLocation location) throws IOException, FileNotFoundException {
/* 53 */     File file1 = this.mapAssets.get(location.toString());
/* 54 */     return (file1 != null && file1.isFile()) ? new FileInputStream(file1) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   private InputStream getResourceStream(ResourceLocation location) {
/* 59 */     String s = "/assets/" + location.getResourceDomain() + "/" + location.getResourcePath();
/* 60 */     InputStream inputstream = ReflectorForge.getOptiFineResourceStream(s);
/* 61 */     return (inputstream != null) ? inputstream : DefaultResourcePack.class.getResourceAsStream(s);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean resourceExists(ResourceLocation location) {
/* 66 */     return (getResourceStream(location) != null || this.mapAssets.containsKey(location.toString()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getResourceDomains() {
/* 71 */     return defaultResourceDomains;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T extends net.minecraft.client.resources.data.IMetadataSection> T getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
/*    */     try {
/* 78 */       InputStream inputstream = new FileInputStream(this.mapAssets.get("pack.mcmeta"));
/* 79 */       return AbstractResourcePack.readMetadata(p_135058_1_, inputstream, p_135058_2_);
/*    */     }
/* 81 */     catch (RuntimeException var4) {
/*    */       
/* 83 */       return null;
/*    */     }
/* 85 */     catch (FileNotFoundException var5) {
/*    */       
/* 87 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferedImage getPackImage() throws IOException {
/* 93 */     return TextureUtil.readBufferedImage(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).getResourcePath()));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPackName() {
/* 98 */     return "Default";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\resources\DefaultResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */