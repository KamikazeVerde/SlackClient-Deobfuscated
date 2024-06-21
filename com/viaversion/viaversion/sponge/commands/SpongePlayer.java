/*    */ package com.viaversion.viaversion.sponge.commands;
/*    */ 
/*    */ import com.viaversion.viaversion.SpongePlugin;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import java.util.UUID;
/*    */ import net.kyori.adventure.text.Component;
/*    */ import org.spongepowered.api.entity.living.player.server.ServerPlayer;
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
/*    */ public class SpongePlayer
/*    */   implements ViaCommandSender
/*    */ {
/*    */   private final ServerPlayer player;
/*    */   
/*    */   public SpongePlayer(ServerPlayer player) {
/* 29 */     this.player = player;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPermission(String permission) {
/* 34 */     return this.player.hasPermission(permission);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String msg) {
/* 39 */     this.player.sendMessage((Component)SpongePlugin.LEGACY_SERIALIZER.deserialize(msg));
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getUUID() {
/* 44 */     return this.player.uniqueId();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 49 */     return this.player.friendlyIdentifier().orElse(this.player.identifier());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\commands\SpongePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */