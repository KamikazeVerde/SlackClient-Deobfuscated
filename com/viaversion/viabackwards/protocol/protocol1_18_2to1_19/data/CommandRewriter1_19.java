/*    */ package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
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
/*    */ public final class CommandRewriter1_19
/*    */   extends CommandRewriter<ClientboundPackets1_19>
/*    */ {
/*    */   public CommandRewriter1_19(Protocol1_18_2To1_19 protocol) {
/* 28 */     super((Protocol)protocol);
/* 29 */     this.parserHandlers.put("minecraft:template_mirror", wrapper -> wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0)));
/* 30 */     this.parserHandlers.put("minecraft:template_rotation", wrapper -> wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0)));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_18_2to1_19\data\CommandRewriter1_19.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */