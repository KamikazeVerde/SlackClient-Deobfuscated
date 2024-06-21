/*    */ package cc.slack.features.modules.impl.render.hud.arraylist.impl;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.events.impl.render.RenderEvent;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.impl.render.hud.arraylist.IArraylist;
/*    */ import cc.slack.utils.font.Fonts;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicArrayList
/*    */   implements IArraylist
/*    */ {
/* 19 */   List<String> modules = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public void onUpdate(UpdateEvent event) {
/* 23 */     this.modules.clear();
/* 24 */     for (Module module : Slack.getInstance().getModuleManager().getModules()) {
/* 25 */       if (module.isToggle()) this.modules.add(module.getDisplayName()); 
/*    */     } 
/* 27 */     this.modules.sort(Comparator.comparingInt(Fonts.apple18::getStringWidth));
/* 28 */     Collections.reverse(this.modules);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender(RenderEvent event) {
/* 33 */     int y = 3;
/* 34 */     for (String module : this.modules) {
/* 35 */       Fonts.apple18.drawStringWithShadow(module, event.getWidth() - Fonts.apple18.getStringWidth(module) - 3.0F, y, 5544391);
/* 36 */       y += Fonts.apple18.getHeight() + 2;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 42 */     return "Basic";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\hud\arraylist\impl\BasicArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */