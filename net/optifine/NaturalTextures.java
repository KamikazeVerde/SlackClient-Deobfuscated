/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class NaturalTextures
/*     */ {
/*  16 */   private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];
/*     */ 
/*     */   
/*     */   public static void update() {
/*  20 */     propertiesByIndex = new NaturalProperties[0];
/*     */     
/*  22 */     if (Config.isNaturalTextures()) {
/*     */       
/*  24 */       String s = "optifine/natural.properties";
/*     */ 
/*     */       
/*     */       try {
/*  28 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */         
/*  30 */         if (!Config.hasResource(resourcelocation)) {
/*     */           
/*  32 */           Config.dbg("NaturalTextures: configuration \"" + s + "\" not found");
/*     */           
/*     */           return;
/*     */         } 
/*  36 */         boolean flag = Config.isFromDefaultResourcePack(resourcelocation);
/*  37 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  38 */         ArrayList<NaturalProperties> arraylist = new ArrayList(256);
/*  39 */         String s1 = Config.readInputStream(inputstream);
/*  40 */         inputstream.close();
/*  41 */         String[] astring = Config.tokenize(s1, "\n\r");
/*     */         
/*  43 */         if (flag) {
/*     */           
/*  45 */           Config.dbg("Natural Textures: Parsing default configuration \"" + s + "\"");
/*  46 */           Config.dbg("Natural Textures: Valid only for textures from default resource pack");
/*     */         }
/*     */         else {
/*     */           
/*  50 */           Config.dbg("Natural Textures: Parsing configuration \"" + s + "\"");
/*     */         } 
/*     */         
/*  53 */         TextureMap texturemap = TextureUtils.getTextureMapBlocks();
/*     */         
/*  55 */         for (int i = 0; i < astring.length; i++) {
/*     */           
/*  57 */           String s2 = astring[i].trim();
/*     */           
/*  59 */           if (!s2.startsWith("#")) {
/*     */             
/*  61 */             String[] astring1 = Config.tokenize(s2, "=");
/*     */             
/*  63 */             if (astring1.length != 2) {
/*     */               
/*  65 */               Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
/*     */             }
/*     */             else {
/*     */               
/*  69 */               String s3 = astring1[0].trim();
/*  70 */               String s4 = astring1[1].trim();
/*  71 */               TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe("minecraft:blocks/" + s3);
/*     */               
/*  73 */               if (textureatlassprite == null) {
/*     */                 
/*  75 */                 Config.warn("Natural Textures: Texture not found: \"" + s + "\" line: " + s2);
/*     */               }
/*     */               else {
/*     */                 
/*  79 */                 int j = textureatlassprite.getIndexInMap();
/*     */                 
/*  81 */                 if (j < 0) {
/*     */                   
/*  83 */                   Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
/*     */                 }
/*     */                 else {
/*     */                   
/*  87 */                   if (flag && !Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + s3 + ".png"))) {
/*     */                     return;
/*     */                   }
/*     */ 
/*     */                   
/*  92 */                   NaturalProperties naturalproperties = new NaturalProperties(s4);
/*     */                   
/*  94 */                   if (naturalproperties.isValid()) {
/*     */                     
/*  96 */                     while (arraylist.size() <= j)
/*     */                     {
/*  98 */                       arraylist.add(null);
/*     */                     }
/*     */                     
/* 101 */                     arraylist.set(j, naturalproperties);
/* 102 */                     Config.dbg("NaturalTextures: " + s3 + " = " + s4);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 110 */         propertiesByIndex = arraylist.<NaturalProperties>toArray(new NaturalProperties[arraylist.size()]);
/*     */       }
/* 112 */       catch (FileNotFoundException var17) {
/*     */         
/* 114 */         Config.warn("NaturalTextures: configuration \"" + s + "\" not found");
/*     */         
/*     */         return;
/* 117 */       } catch (Exception exception) {
/*     */         
/* 119 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static BakedQuad getNaturalTexture(BlockPos blockPosIn, BakedQuad quad) {
/* 126 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*     */     
/* 128 */     if (textureatlassprite == null)
/*     */     {
/* 130 */       return quad;
/*     */     }
/*     */ 
/*     */     
/* 134 */     NaturalProperties naturalproperties = getNaturalProperties(textureatlassprite);
/*     */     
/* 136 */     if (naturalproperties == null)
/*     */     {
/* 138 */       return quad;
/*     */     }
/*     */ 
/*     */     
/* 142 */     int i = ConnectedTextures.getSide(quad.getFace());
/* 143 */     int j = Config.getRandom(blockPosIn, i);
/* 144 */     int k = 0;
/* 145 */     boolean flag = false;
/*     */     
/* 147 */     if (naturalproperties.rotation > 1)
/*     */     {
/* 149 */       k = j & 0x3;
/*     */     }
/*     */     
/* 152 */     if (naturalproperties.rotation == 2)
/*     */     {
/* 154 */       k = k / 2 * 2;
/*     */     }
/*     */     
/* 157 */     if (naturalproperties.flip)
/*     */     {
/* 159 */       flag = ((j & 0x4) != 0);
/*     */     }
/*     */     
/* 162 */     return naturalproperties.getQuad(quad, k, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NaturalProperties getNaturalProperties(TextureAtlasSprite icon) {
/* 169 */     if (!(icon instanceof TextureAtlasSprite))
/*     */     {
/* 171 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 175 */     int i = icon.getIndexInMap();
/*     */     
/* 177 */     if (i >= 0 && i < propertiesByIndex.length) {
/*     */       
/* 179 */       NaturalProperties naturalproperties = propertiesByIndex[i];
/* 180 */       return naturalproperties;
/*     */     } 
/*     */ 
/*     */     
/* 184 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\NaturalTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */