/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticMappings;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class BackwardsMappings
/*    */   extends BackwardsMappings
/*    */ {
/* 30 */   private final Int2ObjectMap<String> statisticMappings = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
/* 31 */   private final Map<String, String> translateMappings = new HashMap<>();
/*    */   
/*    */   public BackwardsMappings() {
/* 34 */     super("1.13", "1.12", Protocol1_13To1_12_2.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadExtras(CompoundTag data) {
/* 39 */     super.loadExtras(data);
/*    */     
/* 41 */     for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>)StatisticMappings.CUSTOM_STATS.entrySet()) {
/* 42 */       this.statisticMappings.put(((Integer)entry.getValue()).intValue(), entry.getKey());
/*    */     }
/* 44 */     for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().entrySet()) {
/* 45 */       this.translateMappings.put(entry.getValue(), entry.getKey());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getNewBlockStateId(int id) {
/* 52 */     if (id >= 5635 && id <= 5650) {
/* 53 */       if (id < 5639) {
/* 54 */         id += 4;
/* 55 */       } else if (id < 5643) {
/* 56 */         id -= 4;
/* 57 */       } else if (id < 5647) {
/* 58 */         id += 4;
/*    */       } else {
/* 60 */         id -= 4;
/*    */       } 
/*    */     }
/*    */     
/* 64 */     int mappedId = super.getNewBlockStateId(id);
/*    */ 
/*    */     
/* 67 */     switch (mappedId) {
/*    */       case 1595:
/*    */       case 1596:
/*    */       case 1597:
/* 71 */         return 1584;
/*    */       case 1611:
/*    */       case 1612:
/*    */       case 1613:
/* 75 */         return 1600;
/*    */     } 
/* 77 */     return mappedId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int checkValidity(int id, int mappedId, String type) {
/* 84 */     return mappedId;
/*    */   }
/*    */   
/*    */   public Int2ObjectMap<String> getStatisticMappings() {
/* 88 */     return this.statisticMappings;
/*    */   }
/*    */   
/*    */   public Map<String, String> getTranslateMappings() {
/* 92 */     return this.translateMappings;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\data\BackwardsMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */