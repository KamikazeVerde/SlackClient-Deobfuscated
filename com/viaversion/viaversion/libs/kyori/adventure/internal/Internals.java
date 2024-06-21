/*    */ package com.viaversion.viaversion.libs.kyori.adventure.internal;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*    */ import com.viaversion.viaversion.libs.kyori.examination.Examiner;
/*    */ import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*    */ 
/*    */ @Internal
/*    */ public final class Internals
/*    */ {
/*    */   @NotNull
/*    */   public static String toString(@NotNull Examinable examinable) {
/* 47 */     return (String)examinable.examine((Examiner)StringExaminer.simpleEscaping());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\internal\Internals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */