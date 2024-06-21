/*    */ package com.viaversion.viaversion.protocols.base;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ public class BaseProtocol1_16
/*    */   extends BaseProtocol1_7
/*    */ {
/*    */   protected UUID passthroughLoginUUID(PacketWrapper wrapper) throws Exception {
/* 28 */     return (UUID)wrapper.passthrough(Type.UUID);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\base\BaseProtocol1_16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */