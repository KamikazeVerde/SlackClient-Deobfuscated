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
/*    */ 
/*    */ public abstract class OptionalType<T>
/*    */   extends Type<T>
/*    */ {
/*    */   private final Type<T> type;
/*    */   
/*    */   protected OptionalType(Type<T> type) {
/* 32 */     super(type.getOutputClass());
/* 33 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public T read(ByteBuf buffer) throws Exception {
/* 38 */     return buffer.readBoolean() ? this.type.read(buffer) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, T value) throws Exception {
/* 43 */     if (value == null) {
/* 44 */       buffer.writeBoolean(false);
/*    */     } else {
/* 46 */       buffer.writeBoolean(true);
/* 47 */       this.type.write(buffer, value);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\OptionalType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */