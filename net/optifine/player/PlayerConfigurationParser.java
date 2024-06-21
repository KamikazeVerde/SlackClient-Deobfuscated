/*     */ package net.optifine.player;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.http.HttpPipeline;
/*     */ import net.optifine.http.HttpUtils;
/*     */ import net.optifine.util.Json;
/*     */ 
/*     */ 
/*     */ public class PlayerConfigurationParser
/*     */ {
/*  23 */   private String player = null;
/*     */   
/*     */   public static final String CONFIG_ITEMS = "items";
/*     */   public static final String ITEM_TYPE = "type";
/*     */   public static final String ITEM_ACTIVE = "active";
/*     */   
/*     */   public PlayerConfigurationParser(String player) {
/*  30 */     this.player = player;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerConfiguration parsePlayerConfiguration(JsonElement je) {
/*  35 */     if (je == null)
/*     */     {
/*  37 */       throw new JsonParseException("JSON object is null, player: " + this.player);
/*     */     }
/*     */ 
/*     */     
/*  41 */     JsonObject jsonobject = (JsonObject)je;
/*  42 */     PlayerConfiguration playerconfiguration = new PlayerConfiguration();
/*  43 */     JsonArray jsonarray = (JsonArray)jsonobject.get("items");
/*     */     
/*  45 */     if (jsonarray != null)
/*     */     {
/*  47 */       for (int i = 0; i < jsonarray.size(); i++) {
/*     */         
/*  49 */         JsonObject jsonobject1 = (JsonObject)jsonarray.get(i);
/*  50 */         boolean flag = Json.getBoolean(jsonobject1, "active", true);
/*     */         
/*  52 */         if (flag) {
/*     */           
/*  54 */           String s = Json.getString(jsonobject1, "type");
/*     */           
/*  56 */           if (s == null) {
/*     */             
/*  58 */             Config.warn("Item type is null, player: " + this.player);
/*     */             
/*     */             continue;
/*     */           } 
/*  62 */           String s1 = Json.getString(jsonobject1, "model");
/*     */           
/*  64 */           if (s1 == null)
/*     */           {
/*  66 */             s1 = "items/" + s + "/model.cfg";
/*     */           }
/*     */           
/*  69 */           PlayerItemModel playeritemmodel = downloadModel(s1);
/*     */           
/*  71 */           if (playeritemmodel != null) {
/*     */             
/*  73 */             if (!playeritemmodel.isUsePlayerTexture()) {
/*     */               
/*  75 */               String s2 = Json.getString(jsonobject1, "texture");
/*     */               
/*  77 */               if (s2 == null)
/*     */               {
/*  79 */                 s2 = "items/" + s + "/users/" + this.player + ".png";
/*     */               }
/*     */               
/*  82 */               BufferedImage bufferedimage = downloadTextureImage(s2);
/*     */               
/*  84 */               if (bufferedimage == null) {
/*     */                 continue;
/*     */               }
/*     */ 
/*     */               
/*  89 */               playeritemmodel.setTextureImage(bufferedimage);
/*  90 */               ResourceLocation resourcelocation = new ResourceLocation("optifine.net", s2);
/*  91 */               playeritemmodel.setTextureLocation(resourcelocation);
/*     */             } 
/*     */             
/*  94 */             playerconfiguration.addPlayerItemModel(playeritemmodel);
/*     */           } 
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/*     */     }
/* 101 */     return playerconfiguration;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage downloadTextureImage(String texturePath) {
/* 107 */     String s = HttpUtils.getPlayerItemsUrl() + "/" + texturePath;
/*     */ 
/*     */     
/*     */     try {
/* 111 */       byte[] abyte = HttpPipeline.get(s, Minecraft.getMinecraft().getProxy());
/* 112 */       BufferedImage bufferedimage = ImageIO.read(new ByteArrayInputStream(abyte));
/* 113 */       return bufferedimage;
/*     */     }
/* 115 */     catch (IOException ioexception) {
/*     */       
/* 117 */       Config.warn("Error loading item texture " + texturePath + ": " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 118 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private PlayerItemModel downloadModel(String modelPath) {
/* 124 */     String s = HttpUtils.getPlayerItemsUrl() + "/" + modelPath;
/*     */ 
/*     */     
/*     */     try {
/* 128 */       byte[] abyte = HttpPipeline.get(s, Minecraft.getMinecraft().getProxy());
/* 129 */       String s1 = new String(abyte, StandardCharsets.US_ASCII);
/* 130 */       JsonParser jsonparser = new JsonParser();
/* 131 */       JsonObject jsonobject = (JsonObject)jsonparser.parse(s1);
/* 132 */       PlayerItemModel playeritemmodel = PlayerItemParser.parseItemModel(jsonobject);
/* 133 */       return playeritemmodel;
/*     */     }
/* 135 */     catch (Exception exception) {
/*     */       
/* 137 */       Config.warn("Error loading item model " + modelPath + ": " + exception.getClass().getName() + ": " + exception.getMessage());
/* 138 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\player\PlayerConfigurationParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */