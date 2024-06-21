/*    */ package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*    */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*    */ import java.util.regex.Pattern;
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
/*    */ 
/*    */ 
/*    */ public class ChatItemRewriter
/*    */ {
/* 28 */   private static final Pattern indexRemoval = Pattern.compile("(?<![\\w-.+])\\d+:(?=([^\"\\\\]*(\\\\.|\"([^\"\\\\]*\\\\.)*[^\"\\\\]*\"))*[^\"]*$)");
/*    */ 
/*    */   
/*    */   public static void toClient(JsonElement element, UserConnection user) {
/* 32 */     if (element instanceof JsonObject) {
/* 33 */       JsonObject obj = (JsonObject)element;
/* 34 */       if (obj.has("hoverEvent")) {
/* 35 */         if (obj.get("hoverEvent") instanceof JsonObject) {
/* 36 */           JsonObject hoverEvent = (JsonObject)obj.get("hoverEvent");
/* 37 */           if (hoverEvent.has("action") && hoverEvent.has("value")) {
/* 38 */             String type = hoverEvent.get("action").getAsString();
/* 39 */             if (type.equals("show_item") || type.equals("show_entity")) {
/* 40 */               JsonElement value = hoverEvent.get("value");
/*    */               
/* 42 */               if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
/* 43 */                 String newValue = indexRemoval.matcher(value.getAsString()).replaceAll("");
/* 44 */                 hoverEvent.addProperty("value", newValue);
/* 45 */               } else if (value.isJsonArray()) {
/* 46 */                 JsonArray newArray = new JsonArray();
/*    */                 
/* 48 */                 for (JsonElement valueElement : value.getAsJsonArray()) {
/* 49 */                   if (valueElement.isJsonPrimitive() && valueElement.getAsJsonPrimitive().isString()) {
/* 50 */                     String newValue = indexRemoval.matcher(valueElement.getAsString()).replaceAll("");
/* 51 */                     newArray.add((JsonElement)new JsonPrimitive(newValue));
/*    */                   } 
/*    */                 } 
/*    */                 
/* 55 */                 hoverEvent.add("value", (JsonElement)newArray);
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         } 
/* 60 */       } else if (obj.has("extra")) {
/* 61 */         toClient(obj.get("extra"), user);
/*    */       } 
/* 63 */     } else if (element instanceof JsonArray) {
/* 64 */       JsonArray array = (JsonArray)element;
/* 65 */       for (JsonElement value : array)
/* 66 */         toClient(value, user); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_12to1_11_1\ChatItemRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */