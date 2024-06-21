/*    */ package cc.slack.features.modules.impl.utilties;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.features.modules.impl.render.ChestESP;
/*    */ import cc.slack.features.modules.impl.render.ESP;
/*    */ import cc.slack.features.modules.impl.render.HUD;
/*    */ import cc.slack.features.modules.impl.render.TargetHUD;
/*    */ import java.io.File;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import org.lwjgl.opengl.Display;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "LegitMode", category = Category.UTILITIES)
/*    */ public class LegitMode
/*    */   extends Module
/*    */ {
/* 24 */   private final BooleanValue selfDestruct = new BooleanValue("Self Destruct", false);
/* 25 */   private final File logsDirectory = new File((Minecraft.getMinecraft()).mcDataDir + File.separator + "logs" + File.separator);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LegitMode() {
/* 31 */     addSettings(new Value[] { (Value)this.selfDestruct });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 36 */     Display.setTitle("Minecraft 1.8.8");
/* 37 */     toggleModule((Class)HUD.class);
/* 38 */     toggleModule((Class)TargetHUD.class);
/* 39 */     toggleModule((Class)ESP.class);
/* 40 */     toggleModule((Class)ChestESP.class);
/*    */     
/* 42 */     if (((Boolean)this.selfDestruct.getValue()).booleanValue()) {
/* 43 */       deleteLogs();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 49 */     Display.setTitle(Slack.getInstance().getInfo().getName() + " " + Slack.getInstance().getInfo().getVersion() + " | " + Slack.getInstance().getInfo().getType() + " Build");
/*    */   }
/*    */   
/*    */   private void toggleModule(Class<? extends Module> moduleClass) {
/* 53 */     Module module = Slack.getInstance().getModuleManager().getInstance(moduleClass);
/* 54 */     if (module.isToggle()) {
/* 55 */       module.toggle();
/*    */     }
/*    */   }
/*    */   
/*    */   private void deleteLogs() {
/* 60 */     if (this.logsDirectory.exists()) {
/* 61 */       File[] files = this.logsDirectory.listFiles();
/* 62 */       if (files == null) {
/*    */         return;
/*    */       }
/* 65 */       for (File file : files) {
/* 66 */         if (file.getName().endsWith("log.gz"))
/* 67 */           file.delete(); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\imp\\utilties\LegitMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */