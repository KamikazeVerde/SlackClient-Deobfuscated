/*    */ package com.viaversion.viaversion.sponge.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.SpongePlugin;
/*    */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*    */ import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
/*    */ import com.viaversion.viaversion.sponge.listeners.UpdateListener;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.spongepowered.api.Sponge;
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
/*    */ public class SpongeViaLoader
/*    */   implements ViaPlatformLoader
/*    */ {
/*    */   private final SpongePlugin plugin;
/* 32 */   private final Set<Object> listeners = new HashSet();
/* 33 */   private final Set<PlatformTask> tasks = new HashSet<>();
/*    */   
/*    */   public SpongeViaLoader(SpongePlugin plugin) {
/* 36 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   private void registerListener(Object listener) {
/* 40 */     Sponge.eventManager().registerListeners(this.plugin.container(), storeListener(listener));
/*    */   }
/*    */   
/*    */   private <T> T storeListener(T listener) {
/* 44 */     this.listeners.add(listener);
/* 45 */     return listener;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void load() {
/* 51 */     registerListener(new UpdateListener());
/*    */   }
/*    */ 
/*    */   
/*    */   public void unload() {
/* 56 */     this.listeners.forEach(Sponge.eventManager()::unregisterListeners);
/* 57 */     this.listeners.clear();
/* 58 */     this.tasks.forEach(PlatformTask::cancel);
/* 59 */     this.tasks.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\platform\SpongeViaLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */