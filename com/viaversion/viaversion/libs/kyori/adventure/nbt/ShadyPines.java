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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ShadyPines
/*    */ {
/*    */   static int floor(double dv) {
/* 31 */     int iv = (int)dv;
/* 32 */     return (dv < iv) ? (iv - 1) : iv;
/*    */   }
/*    */   
/*    */   static int floor(float fv) {
/* 36 */     int iv = (int)fv;
/* 37 */     return (fv < iv) ? (iv - 1) : iv;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\ShadyPines.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */