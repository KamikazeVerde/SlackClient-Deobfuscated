/*    */ package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
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
/*    */ 
/*    */ public class CommandRewriter1_16
/*    */   extends CommandRewriter<ClientboundPackets1_16>
/*    */ {
/*    */   public CommandRewriter1_16(Protocol1_15_2To1_16 protocol) {
/* 28 */     super((Protocol)protocol);
/*    */   }
/*    */ 
/*    */   
/*    */   public String handleArgumentType(String argumentType) {
/* 33 */     if (argumentType.equals("minecraft:uuid")) {
/* 34 */       return "minecraft:game_profile";
/*    */     }
/* 36 */     return super.handleArgumentType(argumentType);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_15_2to1_16\data\CommandRewriter1_16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */