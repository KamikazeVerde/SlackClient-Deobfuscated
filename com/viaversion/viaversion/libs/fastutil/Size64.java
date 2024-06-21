/*    */ package com.viaversion.viaversion.libs.fastutil;
/*    */ 
/*    */ import java.util.Collection;
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
/*    */ public interface Size64
/*    */ {
/*    */   long size64();
/*    */   
/*    */   @Deprecated
/*    */   default int size() {
/* 50 */     return (int)Math.min(2147483647L, size64());
/*    */   }
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
/*    */   static long sizeOf(Collection<?> c) {
/* 63 */     return (c instanceof Size64) ? ((Size64)c).size64() : c.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static long sizeOf(Map<?, ?> m) {
/* 73 */     return (m instanceof Size64) ? ((Size64)m).size64() : m.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\fastutil\Size64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */