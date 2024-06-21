/*    */ package de.gerrygames.viarewind.utils;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*    */ import com.viaversion.viaversion.libs.gson.JsonParser;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*    */ import com.viaversion.viaversion.rewriter.ComponentRewriter;
/*    */ import de.gerrygames.viarewind.ViaRewind;
/*    */ import java.util.logging.Level;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class ChatUtil
/*    */ {
/* 19 */   private static final Pattern UNUSED_COLOR_PATTERN = Pattern.compile("(?>(?>§[0-fk-or])*(§r|\\Z))|(?>(?>§[0-f])*(§[0-f]))");
/* 20 */   private static final ComponentRewriter<ClientboundPacketType> LEGACY_REWRITER = new ComponentRewriter<ClientboundPacketType>()
/*    */     {
/*    */       protected void handleTranslate(JsonObject object, String translate) {
/* 23 */         String text = (String)Protocol1_13To1_12_2.MAPPINGS.getMojangTranslation().get(translate);
/* 24 */         if (text != null) {
/* 25 */           object.addProperty("translate", text);
/*    */         }
/*    */       }
/*    */     };
/*    */   
/*    */   public static String jsonToLegacy(String json) {
/* 31 */     if (json == null || json.equals("null") || json.isEmpty()) return ""; 
/*    */     try {
/* 33 */       return jsonToLegacy(JsonParser.parseString(json));
/* 34 */     } catch (Exception e) {
/* 35 */       ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Could not convert component to legacy text: " + json, e);
/*    */       
/* 37 */       return "";
/*    */     } 
/*    */   }
/*    */   public static String jsonToLegacy(JsonElement component) {
/* 41 */     if (component.isJsonNull() || (component.isJsonArray() && component.getAsJsonArray().isEmpty()) || (component.isJsonObject() && component.getAsJsonObject().size() == 0))
/* 42 */       return ""; 
/* 43 */     if (component.isJsonPrimitive()) {
/* 44 */       return component.getAsString();
/*    */     }
/*    */     try {
/* 47 */       LEGACY_REWRITER.processText(component);
/* 48 */       String legacy = LegacyComponentSerializer.legacySection().serialize(ChatRewriter.HOVER_GSON_SERIALIZER.deserializeFromTree(component));
/* 49 */       for (; legacy.startsWith("§f"); legacy = legacy.substring(2));
/* 50 */       return legacy;
/* 51 */     } catch (Exception ex) {
/* 52 */       ViaRewind.getPlatform().getLogger().log(Level.WARNING, "Could not convert component to legacy text: " + component, ex);
/*    */       
/* 54 */       return "";
/*    */     } 
/*    */   }
/*    */   
/*    */   public static String legacyToJson(String legacy) {
/* 59 */     if (legacy == null) return ""; 
/* 60 */     return (String)GsonComponentSerializer.gson().serialize((Component)LegacyComponentSerializer.legacySection().deserialize(legacy));
/*    */   }
/*    */   
/*    */   public static String removeUnusedColor(String legacy, char last) {
/* 64 */     if (legacy == null) return null; 
/* 65 */     legacy = UNUSED_COLOR_PATTERN.matcher(legacy).replaceAll("$1$2");
/* 66 */     StringBuilder builder = new StringBuilder();
/* 67 */     for (int i = 0; i < legacy.length(); i++) {
/* 68 */       char current = legacy.charAt(i);
/* 69 */       if (current != '§' || i == legacy.length() - 1) {
/* 70 */         builder.append(current);
/*    */       } else {
/*    */         
/* 73 */         current = legacy.charAt(++i);
/* 74 */         if (current != last)
/* 75 */         { builder.append('§').append(current);
/* 76 */           last = current; } 
/*    */       } 
/* 78 */     }  return builder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewin\\utils\ChatUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */