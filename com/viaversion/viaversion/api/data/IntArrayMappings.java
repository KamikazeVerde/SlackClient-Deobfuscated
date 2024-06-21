/*    */ package com.viaversion.viaversion.api.data;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public class IntArrayMappings
/*    */   implements Mappings
/*    */ {
/*    */   private final int[] mappings;
/*    */   private final int mappedIds;
/*    */   
/*    */   protected IntArrayMappings(int[] mappings, int mappedIds) {
/* 32 */     this.mappings = mappings;
/* 33 */     this.mappedIds = mappedIds;
/*    */   }
/*    */   
/*    */   public static IntArrayMappings of(int[] mappings, int mappedIds) {
/* 37 */     return new IntArrayMappings(mappings, mappedIds);
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public static Mappings.Builder<IntArrayMappings> builder() {
/* 42 */     return Mappings.builder(IntArrayMappings::new);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNewId(int id) {
/* 47 */     return (id >= 0 && id < this.mappings.length) ? this.mappings[id] : -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setNewId(int id, int mappedId) {
/* 52 */     this.mappings[id] = mappedId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 57 */     return this.mappings.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public int mappedSize() {
/* 62 */     return this.mappedIds;
/*    */   }
/*    */ 
/*    */   
/*    */   public Mappings inverse() {
/* 67 */     int[] inverse = new int[this.mappedIds];
/* 68 */     Arrays.fill(inverse, -1);
/* 69 */     for (int id = 0; id < this.mappings.length; id++) {
/* 70 */       int mappedId = this.mappings[id];
/* 71 */       if (mappedId != -1 && inverse[mappedId] == -1) {
/* 72 */         inverse[mappedId] = id;
/*    */       }
/*    */     } 
/* 75 */     return of(inverse, this.mappings.length);
/*    */   }
/*    */   
/*    */   public int[] raw() {
/* 79 */     return this.mappings;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\IntArrayMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */