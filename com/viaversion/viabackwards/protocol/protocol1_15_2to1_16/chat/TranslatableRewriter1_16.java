/*     */ package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viabackwards.api.BackwardsProtocol;
/*     */ import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*     */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*     */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*     */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TranslatableRewriter1_16
/*     */   extends TranslatableRewriter<ClientboundPackets1_16>
/*     */ {
/*  34 */   private static final ChatColor[] COLORS = new ChatColor[] { new ChatColor("black", 0), new ChatColor("dark_blue", 170), new ChatColor("dark_green", 43520), new ChatColor("dark_aqua", 43690), new ChatColor("dark_red", 11141120), new ChatColor("dark_purple", 11141290), new ChatColor("gold", 16755200), new ChatColor("gray", 11184810), new ChatColor("dark_gray", 5592405), new ChatColor("blue", 5592575), new ChatColor("green", 5635925), new ChatColor("aqua", 5636095), new ChatColor("red", 16733525), new ChatColor("light_purple", 16733695), new ChatColor("yellow", 16777045), new ChatColor("white", 16777215) };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TranslatableRewriter1_16(Protocol1_15_2To1_16 protocol) {
/*  54 */     super((BackwardsProtocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   public void processText(JsonElement value) {
/*  59 */     super.processText(value);
/*     */     
/*  61 */     if (value == null || !value.isJsonObject()) {
/*     */       return;
/*     */     }
/*  64 */     JsonObject object = value.getAsJsonObject();
/*  65 */     JsonPrimitive color = object.getAsJsonPrimitive("color");
/*  66 */     if (color != null) {
/*  67 */       String colorName = color.getAsString();
/*  68 */       if (!colorName.isEmpty() && colorName.charAt(0) == '#') {
/*  69 */         int rgb = Integer.parseInt(colorName.substring(1), 16);
/*  70 */         String closestChatColor = getClosestChatColor(rgb);
/*  71 */         object.addProperty("color", closestChatColor);
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     JsonObject hoverEvent = object.getAsJsonObject("hoverEvent");
/*  76 */     if (hoverEvent == null || !hoverEvent.has("contents")) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*     */       JsonObject convertedObject;
/*     */       
/*  83 */       Component component = ChatRewriter.HOVER_GSON_SERIALIZER.deserializeFromTree((JsonElement)object);
/*     */       
/*     */       try {
/*  86 */         convertedObject = (JsonObject)ChatRewriter.HOVER_GSON_SERIALIZER.serializeToTree(component);
/*  87 */       } catch (JsonParseException e) {
/*  88 */         JsonObject contents = hoverEvent.getAsJsonObject("contents");
/*  89 */         if (contents.remove("tag") == null) {
/*  90 */           throw e;
/*     */         }
/*     */ 
/*     */         
/*  94 */         component = ChatRewriter.HOVER_GSON_SERIALIZER.deserializeFromTree((JsonElement)object);
/*  95 */         convertedObject = (JsonObject)ChatRewriter.HOVER_GSON_SERIALIZER.serializeToTree(component);
/*     */       } 
/*     */ 
/*     */       
/*  99 */       JsonObject processedHoverEvent = convertedObject.getAsJsonObject("hoverEvent");
/* 100 */       processedHoverEvent.remove("contents");
/* 101 */       object.add("hoverEvent", (JsonElement)processedHoverEvent);
/* 102 */     } catch (Exception e) {
/* 103 */       if (!Via.getConfig().isSuppressConversionWarnings()) {
/* 104 */         ViaBackwards.getPlatform().getLogger().severe("Error converting hover event component: " + object);
/* 105 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getClosestChatColor(int rgb) {
/* 111 */     int r = rgb >> 16 & 0xFF;
/* 112 */     int g = rgb >> 8 & 0xFF;
/* 113 */     int b = rgb & 0xFF;
/*     */     
/* 115 */     ChatColor closest = null;
/* 116 */     int smallestDiff = 0;
/*     */     
/* 118 */     for (ChatColor color : COLORS) {
/* 119 */       if (color.rgb == rgb) {
/* 120 */         return color.colorName;
/*     */       }
/*     */ 
/*     */       
/* 124 */       int rAverage = (color.r + r) / 2;
/* 125 */       int rDiff = color.r - r;
/* 126 */       int gDiff = color.g - g;
/* 127 */       int bDiff = color.b - b;
/* 128 */       int diff = (2 + (rAverage >> 8)) * rDiff * rDiff + 4 * gDiff * gDiff + (2 + (255 - rAverage >> 8)) * bDiff * bDiff;
/*     */ 
/*     */       
/* 131 */       if (closest == null || diff < smallestDiff) {
/* 132 */         closest = color;
/* 133 */         smallestDiff = diff;
/*     */       } 
/*     */     } 
/* 136 */     return closest.colorName;
/*     */   }
/*     */   
/*     */   private static final class ChatColor { private final String colorName;
/*     */     private final int rgb;
/*     */     private final int r;
/*     */     private final int g;
/*     */     private final int b;
/*     */     
/*     */     ChatColor(String colorName, int rgb) {
/* 146 */       this.colorName = colorName;
/* 147 */       this.rgb = rgb;
/* 148 */       this.r = rgb >> 16 & 0xFF;
/* 149 */       this.g = rgb >> 8 & 0xFF;
/* 150 */       this.b = rgb & 0xFF;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_15_2to1_16\chat\TranslatableRewriter1_16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */