/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.OptionalType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.UUID;
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
/*    */ public class UUIDType
/*    */   extends Type<UUID>
/*    */ {
/*    */   public UUIDType() {
/* 33 */     super(UUID.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID read(ByteBuf buffer) {
/* 38 */     return new UUID(buffer.readLong(), buffer.readLong());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, UUID object) {
/* 43 */     buffer.writeLong(object.getMostSignificantBits());
/* 44 */     buffer.writeLong(object.getLeastSignificantBits());
/*    */   }
/*    */   
/*    */   public static final class OptionalUUIDType
/*    */     extends OptionalType<UUID> {
/*    */     public OptionalUUIDType() {
/* 50 */       super(Type.UUID);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\UUIDType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */