/*    */ package com.viaversion.viaversion.sponge.commands;
/*    */ 
/*    */ import com.viaversion.viaversion.SpongePlugin;
/*    */ import com.viaversion.viaversion.api.command.ViaCommandSender;
/*    */ import java.util.UUID;
/*    */ import net.kyori.adventure.identity.Identity;
/*    */ import net.kyori.adventure.text.Component;
/*    */ import org.spongepowered.api.command.CommandCause;
/*    */ import org.spongepowered.api.util.Identifiable;
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
/*    */ public class SpongeCommandSender
/*    */   implements ViaCommandSender
/*    */ {
/*    */   private final CommandCause source;
/*    */   
/*    */   public SpongeCommandSender(CommandCause source) {
/* 31 */     this.source = source;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPermission(String permission) {
/* 36 */     return this.source.hasPermission(permission);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String msg) {
/* 41 */     this.source.sendMessage(Identity.nil(), (Component)SpongePlugin.LEGACY_SERIALIZER.deserialize(msg));
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getUUID() {
/* 46 */     if (this.source instanceof Identifiable) {
/* 47 */       return ((Identifiable)this.source).uniqueId();
/*    */     }
/* 49 */     return new UUID(0L, 0L);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 56 */     return this.source.friendlyIdentifier().orElse(this.source.identifier());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\commands\SpongeCommandSender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */