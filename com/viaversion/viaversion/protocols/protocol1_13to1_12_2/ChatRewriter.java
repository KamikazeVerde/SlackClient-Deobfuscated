/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*    */ import com.viaversion.viaversion.libs.gson.JsonParser;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl.NBTLegacyHoverEventSerializer;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ChatRewriter
/*    */ {
/* 31 */   public static final GsonComponentSerializer HOVER_GSON_SERIALIZER = GsonComponentSerializer.builder().emitLegacyHoverEvent().legacyHoverEventSerializer(NBTLegacyHoverEventSerializer.get()).build();
/*    */   
/*    */   public static JsonObject emptyComponent() {
/* 34 */     JsonObject object = new JsonObject();
/* 35 */     object.addProperty("text", "");
/* 36 */     return object;
/*    */   }
/*    */   
/*    */   public static String emptyComponentString() {
/* 40 */     return "{\"text\":\"\"}";
/*    */   }
/*    */   
/*    */   public static String legacyTextToJsonString(String message, boolean itemData) {
/*    */     BuildableComponent buildableComponent;
/* 45 */     TextComponent textComponent = LegacyComponentSerializer.legacySection().deserialize(message);
/* 46 */     if (itemData) {
/* 47 */       buildableComponent = ((TextComponent.Builder)((TextComponent.Builder)Component.text().decoration(TextDecoration.ITALIC, false)).append((Component)textComponent)).build();
/*    */     }
/* 49 */     return (String)GsonComponentSerializer.gson().serialize((Component)buildableComponent);
/*    */   }
/*    */   
/*    */   public static String legacyTextToJsonString(String legacyText) {
/* 53 */     return legacyTextToJsonString(legacyText, false);
/*    */   }
/*    */   
/*    */   public static JsonElement legacyTextToJson(String legacyText) {
/* 57 */     return JsonParser.parseString(legacyTextToJsonString(legacyText, false));
/*    */   }
/*    */   
/*    */   public static String jsonToLegacyText(String value) {
/*    */     try {
/* 62 */       Component component = HOVER_GSON_SERIALIZER.deserialize(value);
/* 63 */       return LegacyComponentSerializer.legacySection().serialize(component);
/* 64 */     } catch (Exception e) {
/* 65 */       Via.getPlatform().getLogger().warning("Error converting json text to legacy: " + value);
/* 66 */       e.printStackTrace();
/* 67 */       return "";
/*    */     } 
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static void processTranslate(JsonElement value) {
/* 73 */     ((Protocol1_13To1_12_2)Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class)).getComponentRewriter().processText(value);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\ChatRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */