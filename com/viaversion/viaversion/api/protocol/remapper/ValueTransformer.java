/*    */ package com.viaversion.viaversion.api.protocol.remapper;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.exception.InformativeException;
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
/*    */ public abstract class ValueTransformer<T1, T2>
/*    */   implements ValueWriter<T1>
/*    */ {
/*    */   private final Type<T1> inputType;
/*    */   private final Type<T2> outputType;
/*    */   
/*    */   protected ValueTransformer(Type<T1> inputType, Type<T2> outputType) {
/* 35 */     this.inputType = inputType;
/* 36 */     this.outputType = outputType;
/*    */   }
/*    */   
/*    */   protected ValueTransformer(Type<T2> outputType) {
/* 40 */     this(null, outputType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract T2 transform(PacketWrapper paramPacketWrapper, T1 paramT1) throws Exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(PacketWrapper writer, T1 inputValue) throws Exception {
/*    */     try {
/* 56 */       writer.write(this.outputType, transform(writer, inputValue));
/* 57 */     } catch (InformativeException e) {
/* 58 */       e.addSource(getClass());
/* 59 */       throw e;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Type<T1> getInputType() {
/* 64 */     return this.inputType;
/*    */   }
/*    */   
/*    */   public Type<T2> getOutputType() {
/* 68 */     return this.outputType;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\remapper\ValueTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */