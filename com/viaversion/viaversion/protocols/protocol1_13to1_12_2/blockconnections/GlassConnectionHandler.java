/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.ProtocolInfo;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.Position;
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
/*    */ public class GlassConnectionHandler
/*    */   extends AbstractFenceConnectionHandler
/*    */ {
/*    */   static List<ConnectionData.ConnectorInitAction> init() {
/* 29 */     List<ConnectionData.ConnectorInitAction> actions = new ArrayList<>(18);
/* 30 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:white_stained_glass_pane"));
/* 31 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:orange_stained_glass_pane"));
/* 32 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:magenta_stained_glass_pane"));
/* 33 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:light_blue_stained_glass_pane"));
/* 34 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:yellow_stained_glass_pane"));
/* 35 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:lime_stained_glass_pane"));
/* 36 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:pink_stained_glass_pane"));
/* 37 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:gray_stained_glass_pane"));
/* 38 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:light_gray_stained_glass_pane"));
/* 39 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:cyan_stained_glass_pane"));
/* 40 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:purple_stained_glass_pane"));
/* 41 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:blue_stained_glass_pane"));
/* 42 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:brown_stained_glass_pane"));
/* 43 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:green_stained_glass_pane"));
/* 44 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:red_stained_glass_pane"));
/* 45 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:black_stained_glass_pane"));
/* 46 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:glass_pane"));
/* 47 */     actions.add((new GlassConnectionHandler("pane")).getInitAction("minecraft:iron_bars"));
/* 48 */     return actions;
/*    */   }
/*    */   
/*    */   public GlassConnectionHandler(String blockConnections) {
/* 52 */     super(blockConnections);
/*    */   }
/*    */ 
/*    */   
/*    */   protected byte getStates(UserConnection user, Position position, int blockState) {
/* 57 */     byte states = super.getStates(user, position, blockState);
/* 58 */     if (states != 0) return states;
/*    */     
/* 60 */     ProtocolInfo protocolInfo = user.getProtocolInfo();
/* 61 */     return (protocolInfo.getServerProtocolVersion() <= 47 && protocolInfo
/* 62 */       .getServerProtocolVersion() != -1) ? 15 : states;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\GlassConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */