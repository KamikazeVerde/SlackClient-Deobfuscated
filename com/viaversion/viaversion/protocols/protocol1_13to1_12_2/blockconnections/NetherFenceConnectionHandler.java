/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
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
/*    */ public class NetherFenceConnectionHandler
/*    */   extends AbstractFenceConnectionHandler
/*    */ {
/*    */   static ConnectionData.ConnectorInitAction init() {
/* 23 */     return (new NetherFenceConnectionHandler("netherFence")).getInitAction("minecraft:nether_brick_fence");
/*    */   }
/*    */   
/*    */   public NetherFenceConnectionHandler(String blockConnections) {
/* 27 */     super(blockConnections);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\NetherFenceConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */