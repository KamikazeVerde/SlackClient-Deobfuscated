/*    */ package com.viaversion.viaversion.debug;
/*    */ 
/*    */ import com.viaversion.viaversion.api.debug.DebugHandler;
/*    */ import com.viaversion.viaversion.api.protocol.packet.Direction;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ public final class DebugHandlerImpl
/*    */   implements DebugHandler
/*    */ {
/* 31 */   private final Set<String> packetTypesToLog = new HashSet<>();
/* 32 */   private final IntSet clientboundPacketIdsToLog = (IntSet)new IntOpenHashSet();
/* 33 */   private final IntSet serverboundPacketIdsToLog = (IntSet)new IntOpenHashSet();
/*    */   
/*    */   private boolean logPostPacketTransform;
/*    */   private boolean enabled;
/*    */   
/*    */   public boolean enabled() {
/* 39 */     return this.enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEnabled(boolean enabled) {
/* 44 */     this.enabled = enabled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addPacketTypeNameToLog(String packetTypeName) {
/* 49 */     this.packetTypesToLog.add(packetTypeName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addPacketTypeToLog(PacketType packetType) {
/* 54 */     ((packetType.direction() == Direction.SERVERBOUND) ? this.serverboundPacketIdsToLog : this.clientboundPacketIdsToLog).add(packetType.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean removePacketTypeNameToLog(String packetTypeName) {
/* 59 */     return this.packetTypesToLog.remove(packetTypeName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearPacketTypesToLog() {
/* 64 */     this.packetTypesToLog.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean logPostPacketTransform() {
/* 69 */     return this.logPostPacketTransform;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLogPostPacketTransform(boolean logPostPacketTransform) {
/* 74 */     this.logPostPacketTransform = logPostPacketTransform;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldLog(PacketWrapper wrapper, Direction direction) {
/* 79 */     return ((this.packetTypesToLog.isEmpty() && this.serverboundPacketIdsToLog.isEmpty() && this.clientboundPacketIdsToLog.isEmpty()) || (wrapper
/* 80 */       .getPacketType() != null && this.packetTypesToLog.contains(wrapper.getPacketType().getName())) || ((direction == Direction.SERVERBOUND) ? this.serverboundPacketIdsToLog : this.clientboundPacketIdsToLog)
/* 81 */       .contains(wrapper.getId()));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\debug\DebugHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */