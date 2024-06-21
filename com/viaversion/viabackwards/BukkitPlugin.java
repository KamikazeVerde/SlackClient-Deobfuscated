/*    */ package com.viaversion.viabackwards;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
/*    */ import com.viaversion.viabackwards.listener.FireDamageListener;
/*    */ import com.viaversion.viabackwards.listener.FireExtinguishListener;
/*    */ import com.viaversion.viabackwards.listener.LecternInteractListener;
/*    */ import com.viaversion.viabackwards.listener.PlayerItemDropListener;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
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
/*    */ public class BukkitPlugin
/*    */   extends JavaPlugin
/*    */   implements ViaBackwardsPlatform
/*    */ {
/*    */   public BukkitPlugin() {
/* 33 */     Via.getManager().addEnableListener(() -> init(getDataFolder()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 38 */     if (Via.getManager().getInjector().lateProtocolVersionSetting()) {
/*    */       
/* 40 */       Via.getPlatform().runSync(this::enable);
/*    */     } else {
/* 42 */       enable();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void enable() {
/* 47 */     int protocolVersion = Via.getAPI().getServerVersion().highestSupportedVersion();
/* 48 */     if (protocolVersion >= ProtocolVersion.v1_17.getVersion()) {
/* 49 */       (new PlayerItemDropListener(this)).register();
/*    */     }
/* 51 */     if (protocolVersion >= ProtocolVersion.v1_16.getVersion()) {
/* 52 */       (new FireExtinguishListener(this)).register();
/*    */     }
/* 54 */     if (protocolVersion >= ProtocolVersion.v1_14.getVersion()) {
/* 55 */       (new LecternInteractListener(this)).register();
/*    */     }
/* 57 */     if (protocolVersion >= ProtocolVersion.v1_12.getVersion()) {
/* 58 */       (new FireDamageListener(this)).register();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void disable() {
/* 64 */     getPluginLoader().disablePlugin((Plugin)this);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\BukkitPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */