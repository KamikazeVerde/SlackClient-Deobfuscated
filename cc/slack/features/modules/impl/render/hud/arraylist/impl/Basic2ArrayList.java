/*    */ package cc.slack.features.modules.impl.render.hud.arraylist.impl;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.events.impl.player.UpdateEvent;
/*    */ import cc.slack.events.impl.render.RenderEvent;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.impl.render.hud.arraylist.IArraylist;
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.font.Fonts;
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Basic2ArrayList
/*    */   implements IArraylist
/*    */ {
/* 23 */   List<String> modules = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public void onUpdate(UpdateEvent event) {
/* 27 */     this.modules.clear();
/* 28 */     for (Module module : Slack.getInstance().getModuleManager().getModules()) {
/* 29 */       if (module.isToggle()) this.modules.add(module.getDisplayName()); 
/*    */     } 
/* 31 */     this.modules.sort(Comparator.comparingInt(Fonts.apple18::getStringWidth));
/* 32 */     Collections.reverse(this.modules);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender(RenderEvent event) {
/* 37 */     int y = 1;
/* 38 */     for (String module : this.modules) {
/* 39 */       Gui.drawRect((int)event.getWidth() - 1, y, (int)event.getWidth(), (mc.getFontRenderer()).FONT_HEIGHT, Color.WHITE.getRGB());
/* 40 */       Fonts.SFBold18.drawString(module, event.getWidth() - Fonts.SFBold18.getStringWidth(module) - 4.0F, y, Color.WHITE.getRGB());
/* 41 */       y += (mc.getFontRenderer()).FONT_HEIGHT;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return "Basic 2";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\hud\arraylist\impl\Basic2ArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */