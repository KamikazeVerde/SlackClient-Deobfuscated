/*    */ package com.viaversion.viaversion.util;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Int2IntBiMap
/*    */   extends Int2IntMap
/*    */ {
/*    */   Int2IntBiMap inverse();
/*    */   
/*    */   int put(int paramInt1, int paramInt2);
/*    */   
/*    */   @Deprecated
/*    */   default void putAll(Map<? extends Integer, ? extends Integer> m) {
/* 57 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\Int2IntBiMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */