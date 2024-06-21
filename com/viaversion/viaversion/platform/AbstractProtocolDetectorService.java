/*    */ package com.viaversion.viaversion.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.api.platform.ProtocolDetectorService;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.locks.ReadWriteLock;
/*    */ import java.util.concurrent.locks.ReentrantReadWriteLock;
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
/*    */ public abstract class AbstractProtocolDetectorService
/*    */   implements ProtocolDetectorService
/*    */ {
/* 28 */   protected final Object2IntMap<String> detectedProtocolIds = (Object2IntMap<String>)new Object2IntOpenHashMap();
/* 29 */   protected final ReadWriteLock lock = new ReentrantReadWriteLock();
/*    */   
/*    */   protected AbstractProtocolDetectorService() {
/* 32 */     this.detectedProtocolIds.defaultReturnValue(-1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int serverProtocolVersion(String serverName) {
/*    */     int detectedProtocol;
/* 38 */     this.lock.readLock().lock();
/*    */     
/*    */     try {
/* 41 */       detectedProtocol = this.detectedProtocolIds.getInt(serverName);
/*    */     } finally {
/* 43 */       this.lock.readLock().unlock();
/*    */     } 
/* 45 */     if (detectedProtocol != -1) {
/* 46 */       return detectedProtocol;
/*    */     }
/*    */ 
/*    */     
/* 50 */     Map<String, Integer> servers = configuredServers();
/* 51 */     Integer protocol = servers.get(serverName);
/* 52 */     if (protocol != null) {
/* 53 */       return protocol.intValue();
/*    */     }
/*    */ 
/*    */     
/* 57 */     Integer defaultProtocol = servers.get("default");
/* 58 */     if (defaultProtocol != null) {
/* 59 */       return defaultProtocol.intValue();
/*    */     }
/*    */ 
/*    */     
/* 63 */     return lowestSupportedProtocolVersion();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProtocolVersion(String serverName, int protocolVersion) {
/* 68 */     this.lock.writeLock().lock();
/*    */     try {
/* 70 */       this.detectedProtocolIds.put(serverName, protocolVersion);
/*    */     } finally {
/* 72 */       this.lock.writeLock().unlock();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int uncacheProtocolVersion(String serverName) {
/* 78 */     this.lock.writeLock().lock();
/*    */     try {
/* 80 */       return this.detectedProtocolIds.removeInt(serverName);
/*    */     } finally {
/* 82 */       this.lock.writeLock().unlock();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Object2IntMap<String> detectedProtocolVersions() {
/* 88 */     this.lock.readLock().lock();
/*    */     try {
/* 90 */       return (Object2IntMap<String>)new Object2IntOpenHashMap(this.detectedProtocolIds);
/*    */     } finally {
/* 92 */       this.lock.readLock().unlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract Map<String, Integer> configuredServers();
/*    */   
/*    */   protected abstract int lowestSupportedProtocolVersion();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\platform\AbstractProtocolDetectorService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */