/*    */ package de.florianmichael.vialoadingbase.platform;
/*    */ 
/*    */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
/*    */ import de.gerrygames.viarewind.api.ViaRewindConfig;
/*    */ import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
/*    */ import de.gerrygames.viarewind.api.ViaRewindPlatform;
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
/*    */ public class ViaRewindPlatformImpl
/*    */   implements ViaRewindPlatform
/*    */ {
/*    */   public ViaRewindPlatformImpl(File directory) {
/* 30 */     ViaRewindConfigImpl config = new ViaRewindConfigImpl(new File(directory, "viarewind.yml"));
/* 31 */     config.reloadConfig();
/* 32 */     init((ViaRewindConfig)config);
/*    */   }
/*    */ 
/*    */   
/*    */   public Logger getLogger() {
/* 37 */     return ViaLoadingBase.LOGGER;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\platform\ViaRewindPlatformImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */