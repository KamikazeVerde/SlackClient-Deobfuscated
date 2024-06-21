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
/*    */ public class MelonConnectionHandler
/*    */   extends AbstractStempConnectionHandler
/*    */ {
/*    */   public MelonConnectionHandler(String baseStateId) {
/* 23 */     super(baseStateId);
/*    */   }
/*    */   
/*    */   static ConnectionData.ConnectorInitAction init() {
/* 27 */     return (new MelonConnectionHandler("minecraft:melon_stem[age=7]")).getInitAction("minecraft:melon", "minecraft:attached_melon_stem");
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\MelonConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */