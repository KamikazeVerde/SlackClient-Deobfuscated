/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers;
/*    */ 
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
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
/*    */ public class PaintingProvider
/*    */   implements Provider
/*    */ {
/* 27 */   private final Map<String, Integer> paintings = new HashMap<>();
/*    */   
/*    */   public PaintingProvider() {
/* 30 */     add("kebab");
/* 31 */     add("aztec");
/* 32 */     add("alban");
/* 33 */     add("aztec2");
/* 34 */     add("bomb");
/* 35 */     add("plant");
/* 36 */     add("wasteland");
/* 37 */     add("pool");
/* 38 */     add("courbet");
/* 39 */     add("sea");
/* 40 */     add("sunset");
/* 41 */     add("creebet");
/* 42 */     add("wanderer");
/* 43 */     add("graham");
/* 44 */     add("match");
/* 45 */     add("bust");
/* 46 */     add("stage");
/* 47 */     add("void");
/* 48 */     add("skullandroses");
/* 49 */     add("wither");
/* 50 */     add("fighters");
/* 51 */     add("pointer");
/* 52 */     add("pigscene");
/* 53 */     add("burningskull");
/* 54 */     add("skeleton");
/* 55 */     add("donkeykong");
/*    */   }
/*    */   
/*    */   private void add(String motive) {
/* 59 */     this.paintings.put("minecraft:" + motive, Integer.valueOf(this.paintings.size()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Integer> getIntByIdentifier(String motive) {
/* 64 */     if (!motive.startsWith("minecraft:"))
/* 65 */       motive = "minecraft:" + motive.toLowerCase(Locale.ROOT); 
/* 66 */     return Optional.ofNullable(this.paintings.get(motive));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\providers\PaintingProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */