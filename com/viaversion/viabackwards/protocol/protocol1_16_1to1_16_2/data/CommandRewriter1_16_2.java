/*    */ package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
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
/*    */ public class CommandRewriter1_16_2
/*    */   extends CommandRewriter<ClientboundPackets1_16_2>
/*    */ {
/*    */   public CommandRewriter1_16_2(Protocol1_16_1To1_16_2 protocol) {
/* 29 */     super((Protocol)protocol);
/*    */     
/* 31 */     this.parserHandlers.put("minecraft:angle", wrapper -> wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String handleArgumentType(String argumentType) {
/* 38 */     if (argumentType.equals("minecraft:angle")) {
/* 39 */       return "brigadier:string";
/*    */     }
/* 41 */     return super.handleArgumentType(argumentType);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_16_1to1_16_2\data\CommandRewriter1_16_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */