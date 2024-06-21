/*    */ package cc.slack.features.modules.api;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import cc.slack.utils.EventUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Module
/*    */ {
/*    */   public void setKey(int key) {
/* 15 */     this.key = key;
/*    */   }
/*    */   
/* 18 */   private final List<Value> setting = new ArrayList<>(); public List<Value> getSetting() { return this.setting; }
/*    */   
/* 20 */   final ModuleInfo moduleInfo = getClass().<ModuleInfo>getAnnotation(ModuleInfo.class); public ModuleInfo getModuleInfo() { return this.moduleInfo; }
/* 21 */    private final String name = this.moduleInfo.name(); public String getName() { return this.name; }
/* 22 */    private final String displayName = this.moduleInfo.displayName().isEmpty() ? this.moduleInfo.name() : this.moduleInfo.displayName(); public String getDisplayName() { return this.displayName; }
/* 23 */    private final Category category = this.moduleInfo.category(); public Category getCategory() { return this.category; }
/*    */   
/* 25 */   private boolean toggle; private int key = this.moduleInfo.key(); public int getKey() { return this.key; } public boolean isToggle() {
/* 26 */     return this.toggle;
/*    */   }
/*    */   public void onToggled() {}
/*    */   public void onEnable() {}
/*    */   public void onDisable() {}
/*    */   
/*    */   public void toggle() {
/* 33 */     setToggle(!this.toggle);
/*    */   }
/*    */   
/*    */   public void setToggle(boolean toggle) {
/* 37 */     if (this.toggle == toggle)
/*    */       return; 
/* 39 */     this.toggle = toggle;
/*    */     
/* 41 */     if (toggle) {
/* 42 */       EventUtil.register(this);
/* 43 */       onEnable();
/* 44 */       Slack.getInstance().addNotification("Enabled module: " + this.name + ".", " ", Long.valueOf(2000L));
/*    */     } else {
/* 46 */       EventUtil.unRegister(this);
/* 47 */       onDisable();
/* 48 */       Slack.getInstance().addNotification("Disabled module: " + this.name + ".", " ", Long.valueOf(2000L));
/*    */     } 
/* 50 */     onToggled();
/*    */   }
/*    */   
/*    */   public Value getValueByName(String n) {
/* 54 */     for (Value m : this.setting) {
/* 55 */       if (m.getName().equals(n)) {
/* 56 */         return m;
/*    */       }
/*    */     } 
/* 59 */     return null;
/*    */   }
/*    */   
/*    */   public void addSettings(Value... settings) {
/* 63 */     this.setting.addAll(Arrays.asList(settings));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\api\Module.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */