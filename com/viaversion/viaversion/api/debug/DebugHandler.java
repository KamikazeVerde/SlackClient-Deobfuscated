/*     */ package com.viaversion.viaversion.api.debug;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.viaversion.viaversion.api.protocol.packet.Direction;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Beta
/*     */ public interface DebugHandler
/*     */ {
/*     */   boolean enabled();
/*     */   
/*     */   void setEnabled(boolean paramBoolean);
/*     */   
/*     */   void addPacketTypeNameToLog(String paramString);
/*     */   
/*     */   void addPacketTypeToLog(PacketType paramPacketType);
/*     */   
/*     */   boolean removePacketTypeNameToLog(String paramString);
/*     */   
/*     */   void clearPacketTypesToLog();
/*     */   
/*     */   boolean logPostPacketTransform();
/*     */   
/*     */   void setLogPostPacketTransform(boolean paramBoolean);
/*     */   
/*     */   boolean shouldLog(PacketWrapper paramPacketWrapper, Direction paramDirection);
/*     */   
/*     */   void enableAndLogIds(PacketType... packetTypes) {
/* 100 */     setEnabled(true);
/* 101 */     for (PacketType packetType : packetTypes)
/* 102 */       addPacketTypeToLog(packetType); 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\debug\DebugHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */