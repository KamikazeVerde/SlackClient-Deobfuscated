/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*    */ import java.util.stream.Stream;
/*    */ import org.jetbrains.annotations.Debug.Renderer;
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
/*    */ @Renderer(text = "\"\\\"\" + this.value + \"\\\"\"", hasChildren = "false")
/*    */ final class StringBinaryTagImpl
/*    */   extends AbstractBinaryTag
/*    */   implements StringBinaryTag
/*    */ {
/*    */   private final String value;
/*    */   
/*    */   StringBinaryTagImpl(String value) {
/* 37 */     this.value = value;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String value() {
/* 42 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object other) {
/* 47 */     if (this == other) return true; 
/* 48 */     if (other == null || getClass() != other.getClass()) return false; 
/* 49 */     StringBinaryTagImpl that = (StringBinaryTagImpl)other;
/* 50 */     return this.value.equals(that.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 55 */     return this.value.hashCode();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 60 */     return Stream.of(ExaminableProperty.of("value", this.value));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\StringBinaryTagImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */