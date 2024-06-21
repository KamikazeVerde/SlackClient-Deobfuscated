/*     */ package cc.slack;
/*     */ 
/*     */ import cc.slack.events.Event;
/*     */ import cc.slack.events.impl.game.ChatEvent;
/*     */ import cc.slack.events.impl.input.KeyEvent;
/*     */ import cc.slack.features.commands.CMDManager;
/*     */ import cc.slack.features.commands.api.CMD;
/*     */ import cc.slack.features.config.configManager;
/*     */ import cc.slack.features.modules.ModuleManager;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.impl.movement.Sprint;
/*     */ import cc.slack.features.modules.impl.other.Targets;
/*     */ import cc.slack.features.modules.impl.other.Tweaks;
/*     */ import cc.slack.features.modules.impl.render.Animations;
/*     */ import cc.slack.features.modules.impl.render.HUD;
/*     */ import cc.slack.features.modules.impl.render.ScoreboardModule;
/*     */ import cc.slack.features.modules.impl.render.TargetHUD;
/*     */ import cc.slack.utils.EventUtil;
/*     */ import cc.slack.utils.client.ClientInfo;
/*     */ import cc.slack.utils.other.PrintUtil;
/*     */ import de.florianmichael.viamcp.ViaMCP;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import io.github.nevalackin.radbus.PubSub;
/*     */ import java.util.Arrays;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Slack
/*     */ {
/*     */   public static Slack getInstance() {
/*  32 */     return instance;
/*  33 */   } private static final Slack instance = new Slack();
/*  34 */   public final ClientInfo info = new ClientInfo("Slack", "v0.1", ClientInfo.VersionType.ALPHA); public ClientInfo getInfo() { return this.info; }
/*  35 */    private final PubSub<Event> eventBus = PubSub.newInstance(System.err::println); public PubSub<Event> getEventBus() { return this.eventBus; }
/*     */   
/*  37 */   private final ModuleManager moduleManager = new ModuleManager(); public ModuleManager getModuleManager() { return this.moduleManager; }
/*  38 */    private final CMDManager cmdManager = new CMDManager(); public CMDManager getCmdManager() { return this.cmdManager; }
/*     */   
/*  40 */   public final String changelog = "Release v0.01:\r\n-Added all modules (56)\r\n-Added SexModule"; public String getChangelog() { getClass(); return "Release v0.01:\r\n-Added all modules (56)\r\n-Added SexModule"; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  45 */     PrintUtil.print("Initializing " + this.info.getName());
/*  46 */     Display.setTitle(this.info.getName() + " " + this.info.getVersion() + " | " + this.info.getType() + " Build");
/*     */     
/*  48 */     EventUtil.register(this);
/*  49 */     this.moduleManager.initialize();
/*  50 */     this.cmdManager.initialize();
/*  51 */     configManager.init();
/*     */ 
/*     */ 
/*     */     
/*  55 */     ((ScoreboardModule)this.moduleManager.getInstance(ScoreboardModule.class)).toggle();
/*  56 */     ((Animations)this.moduleManager.getInstance(Animations.class)).toggle();
/*  57 */     ((HUD)this.moduleManager.getInstance(HUD.class)).toggle();
/*  58 */     ((Sprint)this.moduleManager.getInstance(Sprint.class)).toggle();
/*  59 */     ((Tweaks)this.moduleManager.getInstance(Tweaks.class)).toggle();
/*  60 */     ((TargetHUD)this.moduleManager.getInstance(TargetHUD.class)).toggle();
/*  61 */     ((Targets)this.moduleManager.getInstance(Targets.class)).toggle();
/*     */ 
/*     */     
/*     */     try {
/*  65 */       ViaMCP.create();
/*  66 */       ViaMCP.INSTANCE.initAsyncSlider();
/*  67 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/*  73 */     EventUtil.unRegister(this);
/*  74 */     configManager.stop();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Listen
/*     */   public void handleKey(KeyEvent e) {
/*  81 */     this.moduleManager.getModules().forEach(module -> {
/*     */           if (module.getKey() == e.getKey())
/*     */             module.toggle(); 
/*     */         });
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void handleChat(ChatEvent e) {
/*  89 */     String message = e.getMessage();
/*     */     
/*  91 */     if (!message.startsWith(this.cmdManager.getPrefix()))
/*  92 */       return;  e.cancel();
/*     */     
/*  94 */     message = message.substring(this.cmdManager.getPrefix().length());
/*     */     
/*  96 */     if (message.split("\\s")[0].equalsIgnoreCase("")) {
/*  97 */       PrintUtil.message("This is Â§cSlack'sÂ§f prefix for ingame client commands. Type Â§c.help Â§fto get started.");
/*     */       
/*     */       return;
/*     */     } 
/* 101 */     if ((message.split("\\s")).length > 0) {
/* 102 */       String cmdName = message.split("\\s")[0];
/*     */       
/* 104 */       for (CMD cmd : this.cmdManager.getCommands()) {
/* 105 */         if (cmd.getName().equalsIgnoreCase(cmdName) || cmd.getAlias().equalsIgnoreCase(cmdName)) {
/* 106 */           cmd.onCommand(Arrays.<String>copyOfRange(message.split("\\s"), 1, (message.split("\\s")).length), message);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 111 */       PrintUtil.message("\"" + message + "\" is not a recognized command. Use Â§c.help Â§fto get other commands.");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addNotification(String bigText, String smallText, Long duration) {
/* 116 */     ((HUD)instance.getModuleManager().getInstance(HUD.class)).addNotification(bigText, smallText, duration);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\Slack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */