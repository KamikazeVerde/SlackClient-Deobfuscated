/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*    */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*    */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatRewriter
/*    */ {
/*    */   public static void toClient(JsonObject obj, UserConnection user) {
/* 35 */     if (obj.get("translate") != null && obj.get("translate").getAsString().equals("gameMode.changed")) {
/* 36 */       EntityTracker1_9 tracker = (EntityTracker1_9)user.getEntityTracker(Protocol1_9To1_8.class);
/* 37 */       String gameMode = tracker.getGameMode().getText();
/*    */       
/* 39 */       JsonObject gameModeObject = new JsonObject();
/* 40 */       gameModeObject.addProperty("text", gameMode);
/* 41 */       gameModeObject.addProperty("color", "gray");
/* 42 */       gameModeObject.addProperty("italic", Boolean.valueOf(true));
/*    */       
/* 44 */       JsonArray array = new JsonArray();
/* 45 */       array.add((JsonElement)gameModeObject);
/*    */       
/* 47 */       obj.add("with", (JsonElement)array);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\chat\ChatRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */