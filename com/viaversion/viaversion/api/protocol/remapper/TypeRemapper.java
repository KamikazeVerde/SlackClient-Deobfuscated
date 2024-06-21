/*    */ package com.viaversion.viaversion.api.protocol.remapper;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ public class TypeRemapper<T>
/*    */   implements ValueReader<T>, ValueWriter<T>
/*    */ {
/*    */   private final Type<T> type;
/*    */   
/*    */   public TypeRemapper(Type<T> type) {
/* 32 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public T read(PacketWrapper wrapper) throws Exception {
/* 37 */     return (T)wrapper.read(this.type);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(PacketWrapper output, T inputValue) {
/* 42 */     output.write(this.type, inputValue);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\remapper\TypeRemapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */