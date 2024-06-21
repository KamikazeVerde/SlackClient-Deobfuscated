/*     */ package com.viaversion.viaversion.api.protocol.packet;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
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
/*     */ public class PacketTracker
/*     */ {
/*     */   private final UserConnection connection;
/*     */   private long sentPackets;
/*     */   private long receivedPackets;
/*     */   private long startTime;
/*     */   private long intervalPackets;
/*  36 */   private long packetsPerSecond = -1L;
/*     */   
/*     */   private int secondsObserved;
/*     */   private int warnings;
/*     */   
/*     */   public PacketTracker(UserConnection connection) {
/*  42 */     this.connection = connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementSent() {
/*  49 */     this.sentPackets++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean incrementReceived() {
/*  59 */     long diff = System.currentTimeMillis() - this.startTime;
/*  60 */     if (diff >= 1000L) {
/*  61 */       this.packetsPerSecond = this.intervalPackets;
/*  62 */       this.startTime = System.currentTimeMillis();
/*  63 */       this.intervalPackets = 1L;
/*  64 */       return true;
/*     */     } 
/*  66 */     this.intervalPackets++;
/*     */ 
/*     */     
/*  69 */     this.receivedPackets++;
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean exceedsMaxPPS() {
/*  81 */     if (this.connection.isClientSide()) return false; 
/*  82 */     ViaVersionConfig conf = Via.getConfig();
/*     */     
/*  84 */     if (conf.getMaxPPS() > 0 && 
/*  85 */       this.packetsPerSecond >= conf.getMaxPPS()) {
/*  86 */       this.connection.disconnect(conf.getMaxPPSKickMessage().replace("%pps", Long.toString(this.packetsPerSecond)));
/*  87 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  92 */     if (conf.getMaxWarnings() > 0 && conf.getTrackingPeriod() > 0) {
/*  93 */       if (this.secondsObserved > conf.getTrackingPeriod()) {
/*     */         
/*  95 */         this.warnings = 0;
/*  96 */         this.secondsObserved = 1;
/*     */       } else {
/*  98 */         this.secondsObserved++;
/*  99 */         if (this.packetsPerSecond >= conf.getWarningPPS()) {
/* 100 */           this.warnings++;
/*     */         }
/*     */         
/* 103 */         if (this.warnings >= conf.getMaxWarnings()) {
/* 104 */           this.connection.disconnect(conf.getMaxWarningsKickMessage().replace("%pps", Long.toString(this.packetsPerSecond)));
/* 105 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/* 109 */     return false;
/*     */   }
/*     */   
/*     */   public long getSentPackets() {
/* 113 */     return this.sentPackets;
/*     */   }
/*     */   
/*     */   public void setSentPackets(long sentPackets) {
/* 117 */     this.sentPackets = sentPackets;
/*     */   }
/*     */   
/*     */   public long getReceivedPackets() {
/* 121 */     return this.receivedPackets;
/*     */   }
/*     */   
/*     */   public void setReceivedPackets(long receivedPackets) {
/* 125 */     this.receivedPackets = receivedPackets;
/*     */   }
/*     */   
/*     */   public long getStartTime() {
/* 129 */     return this.startTime;
/*     */   }
/*     */   
/*     */   public void setStartTime(long startTime) {
/* 133 */     this.startTime = startTime;
/*     */   }
/*     */   
/*     */   public long getIntervalPackets() {
/* 137 */     return this.intervalPackets;
/*     */   }
/*     */   
/*     */   public void setIntervalPackets(long intervalPackets) {
/* 141 */     this.intervalPackets = intervalPackets;
/*     */   }
/*     */   
/*     */   public long getPacketsPerSecond() {
/* 145 */     return this.packetsPerSecond;
/*     */   }
/*     */   
/*     */   public void setPacketsPerSecond(long packetsPerSecond) {
/* 149 */     this.packetsPerSecond = packetsPerSecond;
/*     */   }
/*     */   
/*     */   public int getSecondsObserved() {
/* 153 */     return this.secondsObserved;
/*     */   }
/*     */   
/*     */   public void setSecondsObserved(int secondsObserved) {
/* 157 */     this.secondsObserved = secondsObserved;
/*     */   }
/*     */   
/*     */   public int getWarnings() {
/* 161 */     return this.warnings;
/*     */   }
/*     */   
/*     */   public void setWarnings(int warnings) {
/* 165 */     this.warnings = warnings;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\packet\PacketTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */