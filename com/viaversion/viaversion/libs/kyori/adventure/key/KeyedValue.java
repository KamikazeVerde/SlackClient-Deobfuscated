/*    */ package com.viaversion.viaversion.libs.kyori.adventure.key;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface KeyedValue<T>
/*    */   extends Keyed
/*    */ {
/*    */   @NotNull
/*    */   static <T> KeyedValue<T> keyedValue(@NotNull Key key, @NotNull T value) {
/* 48 */     return new KeyedValueImpl<>(key, Objects.requireNonNull(value, "value"));
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
/*    */   @Deprecated
/*    */   @ScheduledForRemoval(inVersion = "5.0.0")
/*    */   @NotNull
/*    */   static <T> KeyedValue<T> of(@NotNull Key key, @NotNull T value) {
/* 64 */     return new KeyedValueImpl<>(key, Objects.requireNonNull(value, "value"));
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   T value();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\key\KeyedValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */