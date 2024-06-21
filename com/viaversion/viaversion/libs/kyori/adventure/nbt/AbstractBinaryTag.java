/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.examination.Examiner;
/*    */ import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
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
/*    */ abstract class AbstractBinaryTag
/*    */   implements BinaryTag
/*    */ {
/*    */   @NotNull
/*    */   public final String examinableName() {
/* 32 */     return type().toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public final String toString() {
/* 37 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\AbstractBinaryTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */