/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt.api;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
/*    */ import java.util.Objects;
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
/*    */ final class BinaryTagHolderImpl
/*    */   implements BinaryTagHolder
/*    */ {
/*    */   private final String string;
/*    */   
/*    */   BinaryTagHolderImpl(String string) {
/* 35 */     this.string = Objects.<String>requireNonNull(string, "string");
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public String string() {
/* 40 */     return this.string;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public <T, DX extends Exception> T get(@NotNull Codec<T, String, DX, ?> codec) throws DX {
/* 45 */     return (T)codec.decode(this.string);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     return 31 * this.string.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object that) {
/* 55 */     if (!(that instanceof BinaryTagHolderImpl)) {
/* 56 */       return false;
/*    */     }
/*    */     
/* 59 */     return this.string.equals(((BinaryTagHolderImpl)that).string);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 64 */     return this.string;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\api\BinaryTagHolderImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */