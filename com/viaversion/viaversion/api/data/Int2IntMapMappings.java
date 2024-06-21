/*    */ package com.viaversion.viaversion.api.data;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
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
/*    */ public class Int2IntMapMappings
/*    */   implements Mappings
/*    */ {
/*    */   private final Int2IntMap mappings;
/*    */   private final int mappedIds;
/*    */   
/*    */   protected Int2IntMapMappings(Int2IntMap mappings, int mappedIds) {
/* 33 */     this.mappings = mappings;
/* 34 */     this.mappedIds = mappedIds;
/* 35 */     mappings.defaultReturnValue(-1);
/*    */   }
/*    */   
/*    */   public static Int2IntMapMappings of(Int2IntMap mappings, int mappedIds) {
/* 39 */     return new Int2IntMapMappings(mappings, mappedIds);
/*    */   }
/*    */   
/*    */   public static Int2IntMapMappings of() {
/* 43 */     return new Int2IntMapMappings((Int2IntMap)new Int2IntOpenHashMap(), -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNewId(int id) {
/* 48 */     return this.mappings.get(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setNewId(int id, int mappedId) {
/* 53 */     this.mappings.put(id, mappedId);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 58 */     return this.mappings.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public int mappedSize() {
/* 63 */     return this.mappedIds;
/*    */   }
/*    */ 
/*    */   
/*    */   public Mappings inverse() {
/* 68 */     Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap();
/* 69 */     int2IntOpenHashMap.defaultReturnValue(-1);
/* 70 */     for (ObjectIterator<Int2IntMap.Entry> objectIterator = this.mappings.int2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Int2IntMap.Entry entry = objectIterator.next();
/* 71 */       if (entry.getIntValue() != -1) {
/* 72 */         int2IntOpenHashMap.putIfAbsent(entry.getIntValue(), entry.getIntKey());
/*    */       } }
/*    */     
/* 75 */     return of((Int2IntMap)int2IntOpenHashMap, size());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\Int2IntMapMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */