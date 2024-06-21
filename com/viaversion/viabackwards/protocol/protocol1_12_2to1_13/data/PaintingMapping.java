/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
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
/*    */ public class PaintingMapping
/*    */ {
/* 24 */   private static final Int2ObjectMap<String> PAINTINGS = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap(26, 0.99F);
/*    */   
/*    */   public static void init() {
/* 27 */     add("Kebab");
/* 28 */     add("Aztec");
/* 29 */     add("Alban");
/* 30 */     add("Aztec2");
/* 31 */     add("Bomb");
/* 32 */     add("Plant");
/* 33 */     add("Wasteland");
/* 34 */     add("Pool");
/* 35 */     add("Courbet");
/* 36 */     add("Sea");
/* 37 */     add("Sunset");
/* 38 */     add("Creebet");
/* 39 */     add("Wanderer");
/* 40 */     add("Graham");
/* 41 */     add("Match");
/* 42 */     add("Bust");
/* 43 */     add("Stage");
/* 44 */     add("Void");
/* 45 */     add("SkullAndRoses");
/* 46 */     add("Wither");
/* 47 */     add("Fighters");
/* 48 */     add("Pointer");
/* 49 */     add("Pigscene");
/* 50 */     add("BurningSkull");
/* 51 */     add("Skeleton");
/* 52 */     add("DonkeyKong");
/*    */   }
/*    */   
/*    */   private static void add(String motive) {
/* 56 */     PAINTINGS.put(PAINTINGS.size(), motive);
/*    */   }
/*    */   
/*    */   public static String getStringId(int id) {
/* 60 */     return (String)PAINTINGS.getOrDefault(id, "kebab");
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\data\PaintingMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */