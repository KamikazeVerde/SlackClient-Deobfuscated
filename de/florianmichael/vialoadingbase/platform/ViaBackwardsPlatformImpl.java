/*    */ package de.florianmichael.vialoadingbase.platform;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
/*    */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
/*    */ import java.io.File;
/*    */ import java.util.logging.Logger;
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
/*    */ public class ViaBackwardsPlatformImpl
/*    */   implements ViaBackwardsPlatform
/*    */ {
/*    */   private final File directory;
/*    */   
/*    */   public ViaBackwardsPlatformImpl(File directory) {
/* 30 */     init(this.directory = directory);
/*    */   }
/*    */ 
/*    */   
/*    */   public Logger getLogger() {
/* 35 */     return ViaLoadingBase.LOGGER;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOutdated() {
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void disable() {}
/*    */ 
/*    */   
/*    */   public File getDataFolder() {
/* 48 */     return this.directory;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\platform\ViaBackwardsPlatformImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */