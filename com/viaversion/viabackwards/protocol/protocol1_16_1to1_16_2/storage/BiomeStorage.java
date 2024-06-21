/*    */ package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data.BiomeMappings;
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
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
/*    */ public final class BiomeStorage
/*    */   implements StorableObject
/*    */ {
/* 27 */   private final Int2IntMap modernToLegacyBiomes = (Int2IntMap)new Int2IntOpenHashMap();
/*    */   
/*    */   public BiomeStorage() {
/* 30 */     this.modernToLegacyBiomes.defaultReturnValue(-1);
/*    */   }
/*    */   
/*    */   public void addBiome(String biome, int id) {
/* 34 */     this.modernToLegacyBiomes.put(id, BiomeMappings.toLegacyBiome(biome));
/*    */   }
/*    */   
/*    */   public int legacyBiome(int biome) {
/* 38 */     return this.modernToLegacyBiomes.get(biome);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 42 */     this.modernToLegacyBiomes.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_1to1_16_2\storage\BiomeStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */