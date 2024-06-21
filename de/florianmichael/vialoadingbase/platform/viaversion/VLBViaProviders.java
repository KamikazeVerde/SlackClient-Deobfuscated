/*    */ package de.florianmichael.vialoadingbase.platform.viaversion;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*    */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*    */ import com.viaversion.viaversion.api.platform.providers.ViaProviders;
/*    */ import com.viaversion.viaversion.api.protocol.version.VersionProvider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
/*    */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
/*    */ import de.florianmichael.vialoadingbase.platform.providers.VLBMovementTransmitterProvider;
/*    */ import de.florianmichael.vialoadingbase.provider.VLBBaseVersionProvider;
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
/*    */ 
/*    */ public class VLBViaProviders
/*    */   implements ViaPlatformLoader
/*    */ {
/*    */   public void load() {
/* 34 */     ViaProviders providers = Via.getManager().getProviders();
/* 35 */     providers.use(VersionProvider.class, (Provider)new VLBBaseVersionProvider());
/* 36 */     providers.use(MovementTransmitterProvider.class, (Provider)new VLBMovementTransmitterProvider());
/*    */     
/* 38 */     if (ViaLoadingBase.getInstance().getProviders() != null) ViaLoadingBase.getInstance().getProviders().accept(providers); 
/*    */   }
/*    */   
/*    */   public void unload() {}
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\platform\viaversion\VLBViaProviders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */