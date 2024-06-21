/*    */ package com.viaversion.viaversion.classgenerator.generated;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.bukkit.handlers.BukkitDecodeHandler;
/*    */ import com.viaversion.viaversion.bukkit.handlers.BukkitEncodeHandler;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import io.netty.handler.codec.MessageToMessageEncoder;
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
/*    */ public interface HandlerSupplier
/*    */ {
/*    */   MessageToMessageEncoder<ByteBuf> newEncodeHandler(UserConnection paramUserConnection);
/*    */   
/*    */   MessageToMessageDecoder<ByteBuf> newDecodeHandler(UserConnection paramUserConnection);
/*    */   
/*    */   public static final class DefaultHandlerSupplier
/*    */     implements HandlerSupplier
/*    */   {
/*    */     public MessageToMessageEncoder<ByteBuf> newEncodeHandler(UserConnection connection) {
/* 36 */       return (MessageToMessageEncoder<ByteBuf>)new BukkitEncodeHandler(connection);
/*    */     }
/*    */ 
/*    */     
/*    */     public MessageToMessageDecoder<ByteBuf> newDecodeHandler(UserConnection connection) {
/* 41 */       return (MessageToMessageDecoder<ByteBuf>)new BukkitDecodeHandler(connection);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\classgenerator\generated\HandlerSupplier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */