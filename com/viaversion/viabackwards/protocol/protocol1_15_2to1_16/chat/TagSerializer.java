/*     */ package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonPrimitive;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class TagSerializer
/*     */ {
/*  37 */   private static final Pattern PLAIN_TEXT = Pattern.compile("[A-Za-z0-9._+-]+");
/*     */   
/*     */   public static String toString(JsonObject object) {
/*  40 */     StringBuilder builder = new StringBuilder("{");
/*  41 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)object.entrySet()) {
/*  42 */       Preconditions.checkArgument(((JsonElement)entry.getValue()).isJsonPrimitive());
/*  43 */       if (builder.length() != 1) {
/*  44 */         builder.append(',');
/*     */       }
/*     */       
/*  47 */       String escapedText = escape(((JsonElement)entry.getValue()).getAsString());
/*  48 */       builder.append(entry.getKey()).append(':').append(escapedText);
/*     */     } 
/*  50 */     return builder.append('}').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JsonObject toJson(CompoundTag tag) {
/*  57 */     JsonObject object = new JsonObject();
/*  58 */     for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)tag.entrySet()) {
/*  59 */       object.add(entry.getKey(), toJson(entry.getValue()));
/*     */     }
/*  61 */     return object;
/*     */   }
/*     */   
/*     */   private static JsonElement toJson(Tag tag) {
/*  65 */     if (tag instanceof CompoundTag)
/*  66 */       return (JsonElement)toJson((CompoundTag)tag); 
/*  67 */     if (tag instanceof ListTag) {
/*  68 */       ListTag list = (ListTag)tag;
/*  69 */       JsonArray array = new JsonArray();
/*  70 */       for (Tag listEntry : list) {
/*  71 */         array.add(toJson(listEntry));
/*     */       }
/*  73 */       return (JsonElement)array;
/*     */     } 
/*  75 */     return (JsonElement)new JsonPrimitive(tag.getValue().toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String escape(String s) {
/*  80 */     if (PLAIN_TEXT.matcher(s).matches()) return s;
/*     */     
/*  82 */     StringBuilder builder = new StringBuilder(" ");
/*  83 */     char currentQuote = Character.MIN_VALUE;
/*  84 */     for (int i = 0; i < s.length(); i++) {
/*  85 */       char c = s.charAt(i);
/*  86 */       if (c == '\\') {
/*  87 */         builder.append('\\');
/*  88 */       } else if (c == '"' || c == '\'') {
/*  89 */         if (currentQuote == '\000') {
/*  90 */           currentQuote = (c == '"') ? '\'' : '"';
/*     */         }
/*  92 */         if (currentQuote == c) {
/*  93 */           builder.append('\\');
/*     */         }
/*     */       } 
/*  96 */       builder.append(c);
/*     */     } 
/*     */     
/*  99 */     if (currentQuote == '\000') {
/* 100 */       currentQuote = '"';
/*     */     }
/*     */     
/* 103 */     builder.setCharAt(0, currentQuote);
/* 104 */     builder.append(currentQuote);
/* 105 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_15_2to1_16\chat\TagSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */