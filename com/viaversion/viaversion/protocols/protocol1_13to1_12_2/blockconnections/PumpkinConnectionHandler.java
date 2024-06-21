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
/*    */ public class PumpkinConnectionHandler
/*    */   extends AbstractStempConnectionHandler
/*    */ {
/*    */   static ConnectionData.ConnectorInitAction init() {
/* 23 */     return (new PumpkinConnectionHandler("minecraft:pumpkin_stem[age=7]")).getInitAction("minecraft:carved_pumpkin", "minecraft:attached_pumpkin_stem");
/*    */   }
/*    */   
/*    */   public PumpkinConnectionHandler(String baseStateId) {
/* 27 */     super(baseStateId);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\PumpkinConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */