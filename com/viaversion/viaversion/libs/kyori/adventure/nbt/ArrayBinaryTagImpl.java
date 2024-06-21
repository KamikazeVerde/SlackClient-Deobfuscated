/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
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
/*    */ abstract class ArrayBinaryTagImpl
/*    */   extends AbstractBinaryTag
/*    */   implements ArrayBinaryTag
/*    */ {
/*    */   static void checkIndex(int index, int length) {
/* 28 */     if (index < 0 || index >= length)
/* 29 */       throw new IndexOutOfBoundsException("Index out of bounds: " + index); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\ArrayBinaryTagImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */