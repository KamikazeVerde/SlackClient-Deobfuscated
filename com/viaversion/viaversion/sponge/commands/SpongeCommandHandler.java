/*    */ package com.viaversion.viaversion.sponge.commands;
/*    */ 
/*    */ import com.viaversion.viaversion.commands.ViaCommandHandler;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Collectors;
/*    */ import net.kyori.adventure.text.Component;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.spongepowered.api.command.Command;
/*    */ import org.spongepowered.api.command.CommandCause;
/*    */ import org.spongepowered.api.command.CommandCompletion;
/*    */ import org.spongepowered.api.command.CommandResult;
/*    */ import org.spongepowered.api.command.parameter.ArgumentReader;
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
/*    */ public class SpongeCommandHandler
/*    */   extends ViaCommandHandler
/*    */   implements Command.Raw
/*    */ {
/*    */   public CommandResult process(CommandCause cause, ArgumentReader.Mutable arguments) {
/* 36 */     String[] args = (arguments.input().length() > 0) ? arguments.input().split(" ") : new String[0];
/* 37 */     onCommand(new SpongeCommandSender(cause), args);
/* 38 */     return CommandResult.success();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<CommandCompletion> complete(CommandCause cause, ArgumentReader.Mutable arguments) {
/* 43 */     String[] args = arguments.input().split(" ", -1);
/* 44 */     return (List<CommandCompletion>)onTabComplete(new SpongeCommandSender(cause), args).stream().map(CommandCompletion::of).collect(Collectors.toList());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(CommandCause cause) {
/* 49 */     return cause.hasPermission("viaversion.admin");
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Component> shortDescription(CommandCause cause) {
/* 54 */     return (Optional)Optional.of(Component.text("Shows ViaVersion Version and more."));
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Component> extendedDescription(CommandCause cause) {
/* 59 */     return shortDescription(cause);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Component> help(@NotNull CommandCause cause) {
/* 64 */     return Optional.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public Component usage(CommandCause cause) {
/* 69 */     return (Component)Component.text("Usage /viaversion");
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\commands\SpongeCommandHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */