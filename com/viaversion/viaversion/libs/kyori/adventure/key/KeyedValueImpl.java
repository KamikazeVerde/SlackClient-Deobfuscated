/*    */ package com.viaversion.viaversion.libs.kyori.adventure.key;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.examination.Examinable;
/*    */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*    */ import com.viaversion.viaversion.libs.kyori.examination.Examiner;
/*    */ import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
/*    */ import java.util.stream.Stream;
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
/*    */ final class KeyedValueImpl<T>
/*    */   implements Examinable, KeyedValue<T>
/*    */ {
/*    */   private final Key key;
/*    */   private final T value;
/*    */   
/*    */   KeyedValueImpl(Key key, T value) {
/* 38 */     this.key = key;
/* 39 */     this.value = value;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Key key() {
/* 44 */     return this.key;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public T value() {
/* 49 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object other) {
/* 54 */     if (this == other) return true; 
/* 55 */     if (other == null || getClass() != other.getClass()) return false; 
/* 56 */     KeyedValueImpl<?> that = (KeyedValueImpl)other;
/* 57 */     return (this.key.equals(that.key) && this.value.equals(that.value));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 62 */     int result = this.key.hashCode();
/* 63 */     result = 31 * result + this.value.hashCode();
/* 64 */     return result;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 69 */     return Stream.of(new ExaminableProperty[] {
/* 70 */           ExaminableProperty.of("key", this.key), 
/* 71 */           ExaminableProperty.of("value", this.value)
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 77 */     return (String)examine((Examiner)StringExaminer.simpleEscaping());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\key\KeyedValueImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */