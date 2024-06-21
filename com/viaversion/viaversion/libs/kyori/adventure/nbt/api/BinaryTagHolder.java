/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt.api;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface BinaryTagHolder
/*    */ {
/*    */   @NotNull
/*    */   static <T, EX extends Exception> BinaryTagHolder encode(@NotNull T nbt, @NotNull Codec<? super T, String, ?, EX> codec) throws EX {
/* 53 */     return new BinaryTagHolderImpl((String)codec.encode(nbt));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   static BinaryTagHolder binaryTagHolder(@NotNull String string) {
/* 64 */     return new BinaryTagHolderImpl(string);
/*    */   }
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
/*    */   static BinaryTagHolder of(@NotNull String string) {
/* 78 */     return new BinaryTagHolderImpl(string);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   String string();
/*    */   
/*    */   @NotNull
/*    */   <T, DX extends Exception> T get(@NotNull Codec<T, String, DX, ?> paramCodec) throws DX;
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\api\BinaryTagHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */