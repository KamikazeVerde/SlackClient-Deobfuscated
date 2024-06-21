/*    */ package com.viaversion.viaversion.libs.kyori.adventure.permission;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.pointer.Pointer;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.util.TriState;
/*    */ import java.util.function.Predicate;
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
/*    */ public interface PermissionChecker
/*    */   extends Predicate<String>
/*    */ {
/* 44 */   public static final Pointer<PermissionChecker> POINTER = Pointer.pointer(PermissionChecker.class, Key.key("adventure", "permission"));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static PermissionChecker always(TriState state) {
/* 54 */     if (state == TriState.TRUE) return PermissionCheckers.TRUE; 
/* 55 */     if (state == TriState.FALSE) return PermissionCheckers.FALSE; 
/* 56 */     return PermissionCheckers.NOT_SET;
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
/*    */   
/*    */   default boolean test(String permission) {
/* 70 */     return (value(permission) == TriState.TRUE);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   TriState value(String paramString);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\permission\PermissionChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */