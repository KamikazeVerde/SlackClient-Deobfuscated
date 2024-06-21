/*    */ package com.viaversion.viaversion.protocols.protocol1_11_1to1_11;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*    */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*    */ import com.viaversion.viaversion.protocols.protocol1_11_1to1_11.packets.InventoryPackets;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
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
/*    */ public class Protocol1_11_1To1_11
/*    */   extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3>
/*    */ {
/* 27 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*    */   
/*    */   public Protocol1_11_1To1_11() {
/* 30 */     super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 35 */     this.itemRewriter.register();
/*    */   }
/*    */ 
/*    */   
/*    */   public InventoryPackets getItemRewriter() {
/* 40 */     return this.itemRewriter;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_11_1to1_11\Protocol1_11_1To1_11.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */