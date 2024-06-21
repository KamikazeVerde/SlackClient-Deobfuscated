/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class BasicFenceConnectionHandler
/*    */   extends AbstractFenceConnectionHandler
/*    */ {
/*    */   static List<ConnectionData.ConnectorInitAction> init() {
/* 26 */     List<ConnectionData.ConnectorInitAction> actions = new ArrayList<>();
/* 27 */     actions.add((new BasicFenceConnectionHandler("fence")).getInitAction("minecraft:oak_fence"));
/* 28 */     actions.add((new BasicFenceConnectionHandler("fence")).getInitAction("minecraft:birch_fence"));
/* 29 */     actions.add((new BasicFenceConnectionHandler("fence")).getInitAction("minecraft:jungle_fence"));
/* 30 */     actions.add((new BasicFenceConnectionHandler("fence")).getInitAction("minecraft:dark_oak_fence"));
/* 31 */     actions.add((new BasicFenceConnectionHandler("fence")).getInitAction("minecraft:acacia_fence"));
/* 32 */     actions.add((new BasicFenceConnectionHandler("fence")).getInitAction("minecraft:spruce_fence"));
/* 33 */     return actions;
/*    */   }
/*    */   
/*    */   public BasicFenceConnectionHandler(String blockConnections) {
/* 37 */     super(blockConnections);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\BasicFenceConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */