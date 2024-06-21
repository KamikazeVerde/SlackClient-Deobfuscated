/*    */ package com.viaversion.viaversion.sponge.listeners;
/*    */ 
/*    */ import com.viaversion.viaversion.SpongePlugin;
/*    */ import com.viaversion.viaversion.ViaListener;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import java.lang.reflect.Field;
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
/*    */ public class ViaSpongeListener
/*    */   extends ViaListener
/*    */ {
/*    */   private static Field entityIdField;
/*    */   private final SpongePlugin plugin;
/*    */   
/*    */   public ViaSpongeListener(SpongePlugin plugin, Class<? extends Protocol> requiredPipeline) {
/* 32 */     super(requiredPipeline);
/* 33 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public void register() {
/* 38 */     if (isRegistered())
/*    */       return; 
/* 40 */     Sponge.eventManager().registerListeners(this.plugin.container(), this);
/* 41 */     setRegistered(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\listeners\ViaSpongeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */