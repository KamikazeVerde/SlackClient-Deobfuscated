/*    */ package com.viaversion.viaversion.libs.fastutil;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutableSortedPair;
/*    */ import java.util.Objects;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface SortedPair<K extends Comparable<K>>
/*    */   extends Pair<K, K>
/*    */ {
/*    */   static <K extends Comparable<K>> SortedPair<K> of(K l, K r) {
/* 59 */     return (SortedPair<K>)ObjectObjectImmutableSortedPair.of((Comparable)l, (Comparable)r);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean contains(Object o) {
/* 69 */     return (Objects.equals(o, left()) || Objects.equals(o, right()));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\fastutil\SortedPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */