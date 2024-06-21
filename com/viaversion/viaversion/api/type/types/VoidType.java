/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.TypeConverter;
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
/*    */ public class VoidType
/*    */   extends Type<Void>
/*    */   implements TypeConverter<Void>
/*    */ {
/*    */   public VoidType() {
/* 31 */     super(Void.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Void read(ByteBuf buffer) {
/* 36 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Void object) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public Void from(Object o) {
/* 46 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\VoidType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */