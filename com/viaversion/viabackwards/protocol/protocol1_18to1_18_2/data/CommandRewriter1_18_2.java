/*    */ package com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_18to1_18_2.Protocol1_18To1_18_2;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
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
/*    */ public final class CommandRewriter1_18_2
/*    */   extends CommandRewriter<ClientboundPackets1_18>
/*    */ {
/*    */   public CommandRewriter1_18_2(Protocol1_18To1_18_2 protocol) {
/* 29 */     super((Protocol)protocol);
/*    */ 
/*    */     
/* 32 */     this.parserHandlers.put("minecraft:resource", wrapper -> {
/*    */           wrapper.read(Type.STRING);
/*    */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(1));
/*    */         });
/* 36 */     this.parserHandlers.put("minecraft:resource_or_tag", wrapper -> {
/*    */           wrapper.read(Type.STRING);
/*    */           wrapper.write((Type)Type.VAR_INT, Integer.valueOf(1));
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public String handleArgumentType(String argumentType) {
/* 44 */     if (argumentType.equals("minecraft:resource") || argumentType.equals("minecraft:resource_or_tag")) {
/* 45 */       return "brigadier:string";
/*    */     }
/* 47 */     return super.handleArgumentType(argumentType);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_18to1_18_2\data\CommandRewriter1_18_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */