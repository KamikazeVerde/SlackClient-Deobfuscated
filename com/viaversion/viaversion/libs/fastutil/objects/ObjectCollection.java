/*    */ package com.viaversion.viaversion.libs.fastutil.objects;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.Size64;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Spliterator;
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
/*    */ public interface ObjectCollection<K>
/*    */   extends Collection<K>, ObjectIterable<K>
/*    */ {
/*    */   default ObjectSpliterator<K> spliterator() {
/* 81 */     return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 64);
/*    */   }
/*    */   
/*    */   ObjectIterator<K> iterator();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\fastutil\objects\ObjectCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */