/*    */ package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
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
/*    */ public class CommandRewriter1_14
/*    */   extends CommandRewriter<ClientboundPackets1_14>
/*    */ {
/*    */   public CommandRewriter1_14(Protocol1_13_2To1_14 protocol) {
/* 29 */     super((Protocol)protocol);
/*    */     
/* 31 */     this.parserHandlers.put("minecraft:nbt_tag", wrapper -> wrapper.write((Type)Type.VAR_INT, Integer.valueOf(2)));
/*    */ 
/*    */     
/* 34 */     this.parserHandlers.put("minecraft:time", wrapper -> {
/*    */           wrapper.write((Type)Type.BYTE, Byte.valueOf((byte)1));
/*    */           wrapper.write((Type)Type.INT, Integer.valueOf(0));
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public String handleArgumentType(String argumentType) {
/* 42 */     switch (argumentType) {
/*    */       case "minecraft:nbt_compound_tag":
/* 44 */         return "minecraft:nbt";
/*    */       case "minecraft:nbt_tag":
/* 46 */         return "brigadier:string";
/*    */       case "minecraft:time":
/* 48 */         return "brigadier:integer";
/*    */     } 
/* 50 */     return super.handleArgumentType(argumentType);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13_2to1_14\data\CommandRewriter1_14.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */