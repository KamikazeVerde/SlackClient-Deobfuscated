/*    */ package com.viaversion.viaversion.libs.kyori.adventure.util;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ public abstract class Nag
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -695562541413409498L;
/*    */   
/*    */   public static void print(@NotNull Nag nag) {
/* 43 */     nag.printStackTrace();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Nag(String message) {
/* 53 */     super(message);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventur\\util\Nag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */