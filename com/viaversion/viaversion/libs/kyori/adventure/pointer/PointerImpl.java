/*    */ package com.viaversion.viaversion.libs.kyori.adventure.pointer;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
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
/*    */ final class PointerImpl<T>
/*    */   implements Pointer<T>
/*    */ {
/*    */   private final Class<T> type;
/*    */   private final Key key;
/*    */   
/*    */   PointerImpl(Class<T> type, Key key) {
/* 36 */     this.type = type;
/* 37 */     this.key = key;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Class<T> type() {
/* 42 */     return this.type;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Key key() {
/* 47 */     return this.key;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return Internals.toString(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object other) {
/* 57 */     if (this == other) return true; 
/* 58 */     if (other == null || getClass() != other.getClass()) return false; 
/* 59 */     PointerImpl<?> that = (PointerImpl)other;
/* 60 */     return (this.type.equals(that.type) && this.key.equals(that.key));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 65 */     int result = this.type.hashCode();
/* 66 */     result = 31 * result + this.key.hashCode();
/* 67 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\pointer\PointerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */