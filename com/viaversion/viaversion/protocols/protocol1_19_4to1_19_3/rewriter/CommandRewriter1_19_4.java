/*    */ package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.rewriter.CommandRewriter;
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
/*    */ public class CommandRewriter1_19_4<C extends ClientboundPacketType>
/*    */   extends CommandRewriter<C>
/*    */ {
/*    */   public CommandRewriter1_19_4(Protocol<C, ?, ?, ?> protocol) {
/* 28 */     super(protocol);
/* 29 */     this.parserHandlers.put("minecraft:time", wrapper -> (Integer)wrapper.passthrough((Type)Type.INT));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_4to1_19_3\rewriter\CommandRewriter1_19_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */