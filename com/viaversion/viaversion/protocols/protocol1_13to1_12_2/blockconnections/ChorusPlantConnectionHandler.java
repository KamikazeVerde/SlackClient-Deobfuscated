/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockFace;
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
/*    */ public class ChorusPlantConnectionHandler
/*    */   extends AbstractFenceConnectionHandler
/*    */ {
/*    */   private final int endstone;
/*    */   
/*    */   static List<ConnectionData.ConnectorInitAction> init() {
/* 30 */     List<ConnectionData.ConnectorInitAction> actions = new ArrayList<>(2);
/* 31 */     ChorusPlantConnectionHandler handler = new ChorusPlantConnectionHandler();
/* 32 */     actions.add(handler.getInitAction("minecraft:chorus_plant"));
/* 33 */     actions.add(handler.getExtraAction());
/* 34 */     return actions;
/*    */   }
/*    */   
/*    */   public ChorusPlantConnectionHandler() {
/* 38 */     super(null);
/* 39 */     this.endstone = ConnectionData.getId("minecraft:end_stone");
/*    */   }
/*    */   
/*    */   public ConnectionData.ConnectorInitAction getExtraAction() {
/* 43 */     return blockData -> {
/*    */         if (blockData.getMinecraftKey().equals("minecraft:chorus_flower")) {
/*    */           getBlockStates().add(blockData.getSavedBlockStateId());
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   protected byte getStates(WrappedBlockData blockData) {
/* 52 */     byte states = super.getStates(blockData);
/* 53 */     if (blockData.getValue("up").equals("true")) states = (byte)(states | 0x10); 
/* 54 */     if (blockData.getValue("down").equals("true")) states = (byte)(states | 0x20); 
/* 55 */     return states;
/*    */   }
/*    */ 
/*    */   
/*    */   protected byte statesSize() {
/* 60 */     return 64;
/*    */   }
/*    */ 
/*    */   
/*    */   protected byte getStates(UserConnection user, Position position, int blockState) {
/* 65 */     byte states = super.getStates(user, position, blockState);
/* 66 */     if (connects(BlockFace.TOP, getBlockData(user, position.getRelative(BlockFace.TOP)), false)) states = (byte)(states | 0x10); 
/* 67 */     if (connects(BlockFace.BOTTOM, getBlockData(user, position.getRelative(BlockFace.BOTTOM)), false)) states = (byte)(states | 0x20); 
/* 68 */     return states;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean connects(BlockFace side, int blockState, boolean pre1_12) {
/* 73 */     return (getBlockStates().contains(blockState) || (side == BlockFace.BOTTOM && blockState == this.endstone));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\ChorusPlantConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */