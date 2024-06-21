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
/*    */ @Renderer(text = "String.valueOf(this.value) + \"f\"", hasChildren = "false")
/*    */ final class FloatBinaryTagImpl
/*    */   extends AbstractBinaryTag
/*    */   implements FloatBinaryTag
/*    */ {
/*    */   private final float value;
/*    */   
/*    */   FloatBinaryTagImpl(float value) {
/* 37 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public float value() {
/* 42 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte byteValue() {
/* 47 */     return (byte)(ShadyPines.floor(this.value) & 0xFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public double doubleValue() {
/* 52 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public float floatValue() {
/* 57 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int intValue() {
/* 62 */     return ShadyPines.floor(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public long longValue() {
/* 67 */     return (long)this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public short shortValue() {
/* 72 */     return (short)(ShadyPines.floor(this.value) & 0xFFFF);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object other) {
/* 77 */     if (this == other) return true; 
/* 78 */     if (other == null || getClass() != other.getClass()) return false; 
/* 79 */     FloatBinaryTagImpl that = (FloatBinaryTagImpl)other;
/* 80 */     return (Float.floatToIntBits(this.value) == Float.floatToIntBits(that.value));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 85 */     return Float.hashCode(this.value);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 90 */     return Stream.of(ExaminableProperty.of("value", this.value));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\FloatBinaryTagImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */