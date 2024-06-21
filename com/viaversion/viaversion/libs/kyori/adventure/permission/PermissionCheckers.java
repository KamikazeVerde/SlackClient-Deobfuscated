/*    */ package com.viaversion.viaversion.libs.kyori.adventure.permission;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.util.TriState;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PermissionCheckers
/*    */ {
/* 31 */   static final PermissionChecker NOT_SET = new Always(TriState.NOT_SET);
/* 32 */   static final PermissionChecker FALSE = new Always(TriState.FALSE);
/* 33 */   static final PermissionChecker TRUE = new Always(TriState.TRUE);
/*    */ 
/*    */   
/*    */   private static final class Always
/*    */     implements PermissionChecker
/*    */   {
/*    */     private final TriState value;
/*    */     
/*    */     private Always(TriState value) {
/* 42 */       this.value = value;
/*    */     }
/*    */     
/*    */     @NotNull
/*    */     public TriState value(String permission) {
/* 47 */       return this.value;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 52 */       return PermissionChecker.class.getSimpleName() + ".always(" + this.value + ")";
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(@Nullable Object other) {
/* 57 */       if (this == other) return true; 
/* 58 */       if (other == null || getClass() != other.getClass()) return false; 
/* 59 */       Always always = (Always)other;
/* 60 */       return (this.value == always.value);
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 65 */       return this.value.hashCode();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\permission\PermissionCheckers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */