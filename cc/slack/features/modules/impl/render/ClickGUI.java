/*    */ package cc.slack.features.modules.impl.render;
/*    */ 
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.ModuleInfo;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.ui.NewCGUI.TransparentClickGUI;
/*    */ import cc.slack.ui.clickGUI.ClickGui;
/*    */ import cc.slack.utils.client.mc;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "ClickGUI", category = Category.RENDER, key = 54)
/*    */ public class ClickGUI<ClickGUIType extends GuiScreen>
/*    */   extends Module
/*    */ {
/* 23 */   private final ModeValue<String> mode = new ModeValue((Object[])new String[] { "Old", "New" });
/*    */   
/*    */   private ClickGUIType clickgui;
/*    */   
/*    */   public ClickGUI() {
/* 28 */     addSettings(new Value[] { (Value)this.mode });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 33 */     if (this.clickgui == null) {
/* 34 */       switch ((String)this.mode.getValue()) {
/*    */         case "New":
/* 36 */           this.clickgui = (ClickGUIType)new TransparentClickGUI();
/*    */           break;
/*    */         case "Old":
/* 39 */           this.clickgui = (ClickGUIType)new ClickGui();
/*    */           break;
/*    */         default:
/* 42 */           throw new RuntimeException("Unknown Type: ClickGUI");
/*    */       } 
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 48 */     mc.getMinecraft().displayGuiScreen((GuiScreen)this.clickgui);
/* 49 */     toggle();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\ClickGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */