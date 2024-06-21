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
/*    */ @Renderer(text = "\"0x\" + Integer.toString(this.value, 16)", hasChildren = "false")
/*    */ final class ByteBinaryTagImpl
/*    */   extends AbstractBinaryTag
/*    */   implements ByteBinaryTag
/*    */ {
/*    */   private final byte value;
/*    */   
/*    */   ByteBinaryTagImpl(byte value) {
/* 37 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte value() {
/* 42 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte byteValue() {
/* 47 */     return this.value;
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
/* 62 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public long longValue() {
/* 67 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public short shortValue() {
/* 72 */     return (short)this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object other) {
/* 77 */     if (this == other) return true; 
/* 78 */     if (other == null || getClass() != other.getClass()) return false; 
/* 79 */     ByteBinaryTagImpl that = (ByteBinaryTagImpl)other;
/* 80 */     return (this.value == that.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 85 */     return Byte.hashCode(this.value);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 90 */     return Stream.of(ExaminableProperty.of("value", this.value));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\ByteBinaryTagImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */