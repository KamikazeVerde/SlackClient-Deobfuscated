/*     */ package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.data.AchievementTranslationMapping;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.rewriter.ComponentRewriter;
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
/*     */ public class TranslateRewriter
/*     */ {
/*  31 */   private static final ComponentRewriter<ClientboundPackets1_9_3> ACHIEVEMENT_TEXT_REWRITER = new ComponentRewriter<ClientboundPackets1_9_3>()
/*     */     {
/*     */       protected void handleTranslate(JsonObject object, String translate) {
/*  34 */         String text = AchievementTranslationMapping.get(translate);
/*  35 */         if (text != null) {
/*  36 */           object.addProperty("translate", text);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       protected void handleHoverEvent(JsonObject hoverEvent) {
/*  42 */         String textValue, action = hoverEvent.getAsJsonPrimitive("action").getAsString();
/*  43 */         if (!action.equals("show_achievement")) {
/*  44 */           super.handleHoverEvent(hoverEvent);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/*  49 */         JsonElement value = hoverEvent.get("value");
/*  50 */         if (value.isJsonObject()) {
/*  51 */           textValue = value.getAsJsonObject().get("text").getAsString();
/*     */         } else {
/*  53 */           textValue = value.getAsJsonPrimitive().getAsString();
/*     */         } 
/*     */         
/*  56 */         if (AchievementTranslationMapping.get(textValue) == null) {
/*  57 */           JsonObject invalidText = new JsonObject();
/*  58 */           invalidText.addProperty("text", "Invalid statistic/achievement!");
/*  59 */           invalidText.addProperty("color", "red");
/*  60 */           hoverEvent.addProperty("action", "show_text");
/*  61 */           hoverEvent.add("value", (JsonElement)invalidText);
/*  62 */           super.handleHoverEvent(hoverEvent);
/*     */           
/*     */           return;
/*     */         } 
/*     */         try {
/*  67 */           JsonObject newLine = new JsonObject();
/*  68 */           newLine.addProperty("text", "\n");
/*  69 */           JsonArray baseArray = new JsonArray();
/*  70 */           baseArray.add("");
/*  71 */           JsonObject namePart = new JsonObject();
/*  72 */           JsonObject typePart = new JsonObject();
/*  73 */           baseArray.add((JsonElement)namePart);
/*  74 */           baseArray.add((JsonElement)newLine);
/*  75 */           baseArray.add((JsonElement)typePart);
/*  76 */           if (textValue.startsWith("achievement")) {
/*  77 */             namePart.addProperty("translate", textValue);
/*  78 */             namePart.addProperty("color", AchievementTranslationMapping.isSpecial(textValue) ? "dark_purple" : "green");
/*  79 */             typePart.addProperty("translate", "stats.tooltip.type.achievement");
/*  80 */             JsonObject description = new JsonObject();
/*  81 */             typePart.addProperty("italic", Boolean.valueOf(true));
/*  82 */             description.addProperty("translate", value + ".desc");
/*  83 */             baseArray.add((JsonElement)newLine);
/*  84 */             baseArray.add((JsonElement)description);
/*  85 */           } else if (textValue.startsWith("stat")) {
/*  86 */             namePart.addProperty("translate", textValue);
/*  87 */             namePart.addProperty("color", "gray");
/*  88 */             typePart.addProperty("translate", "stats.tooltip.type.statistic");
/*  89 */             typePart.addProperty("italic", Boolean.valueOf(true));
/*     */           } 
/*  91 */           hoverEvent.addProperty("action", "show_text");
/*  92 */           hoverEvent.add("value", (JsonElement)baseArray);
/*  93 */         } catch (Exception e) {
/*  94 */           Via.getPlatform().getLogger().warning("Error rewriting show_achievement: " + hoverEvent);
/*  95 */           e.printStackTrace();
/*  96 */           JsonObject invalidText = new JsonObject();
/*  97 */           invalidText.addProperty("text", "Invalid statistic/achievement!");
/*  98 */           invalidText.addProperty("color", "red");
/*  99 */           hoverEvent.addProperty("action", "show_text");
/* 100 */           hoverEvent.add("value", (JsonElement)invalidText);
/*     */         } 
/* 102 */         super.handleHoverEvent(hoverEvent);
/*     */       }
/*     */     };
/*     */   
/*     */   public static void toClient(JsonElement element, UserConnection user) {
/* 107 */     if (element instanceof JsonObject) {
/* 108 */       JsonObject obj = (JsonObject)element;
/* 109 */       JsonElement translate = obj.get("translate");
/* 110 */       if (translate != null && 
/* 111 */         translate.getAsString().startsWith("chat.type.achievement"))
/* 112 */         ACHIEVEMENT_TEXT_REWRITER.processText((JsonElement)obj); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_12to1_11_1\TranslateRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */