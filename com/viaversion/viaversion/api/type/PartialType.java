/*    */ package com.viaversion.viaversion.api.type;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public abstract class PartialType<T, X>
/*    */   extends Type<T>
/*    */ {
/*    */   private final X param;
/*    */   
/*    */   protected PartialType(X param, Class<T> type) {
/* 31 */     super(type);
/* 32 */     this.param = param;
/*    */   }
/*    */   
/*    */   protected PartialType(X param, String name, Class<T> type) {
/* 36 */     super(name, type);
/* 37 */     this.param = param;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract T read(ByteBuf paramByteBuf, X paramX) throws Exception;
/*    */   
/*    */   public abstract void write(ByteBuf paramByteBuf, X paramX, T paramT) throws Exception;
/*    */   
/*    */   public final T read(ByteBuf buffer) throws Exception {
/* 46 */     return read(buffer, this.param);
/*    */   }
/*    */ 
/*    */   
/*    */   public final void write(ByteBuf buffer, T object) throws Exception {
/* 51 */     write(buffer, this.param, object);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\PartialType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */