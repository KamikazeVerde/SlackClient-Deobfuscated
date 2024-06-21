/*    */ package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
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
/*    */ public class CommandRewriter1_13_1
/*    */   extends CommandRewriter<ClientboundPackets1_13>
/*    */ {
/*    */   public CommandRewriter1_13_1(Protocol1_13To1_13_1 protocol) {
/* 29 */     super((Protocol)protocol);
/*    */     
/* 31 */     this.parserHandlers.put("minecraft:dimension", wrapper -> wrapper.write((Type)Type.VAR_INT, Integer.valueOf(0)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String handleArgumentType(String argumentType) {
/* 38 */     if (argumentType.equals("minecraft:column_pos"))
/* 39 */       return "minecraft:vec2"; 
/* 40 */     if (argumentType.equals("minecraft:dimension")) {
/* 41 */       return "brigadier:string";
/*    */     }
/* 43 */     return super.handleArgumentType(argumentType);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13to1_13_1\data\CommandRewriter1_13_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */