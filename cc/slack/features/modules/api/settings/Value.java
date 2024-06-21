/*    */ package cc.slack.features.modules.api.settings;
/*    */ 
/*    */ public abstract class Value<T>
/*    */ {
/*    */   private final String name;
/*    */   private T value;
/*    */   private VisibilityCheck check;
/*    */   
/*    */   public Value(String name, T value, VisibilityCheck check) {
/* 10 */     this.name = name; this.value = value; this.check = check;
/*    */   }
/*    */   
/* 13 */   public String getName() { return this.name; }
/* 14 */   public void setValue(T value) { this.value = value; }
/* 15 */   public T getValue() { return this.value; } public VisibilityCheck getCheck() {
/* 16 */     return this.check;
/*    */   }
/*    */   public <Type extends Value<T>> Type require(VisibilityCheck check) {
/* 19 */     this.check = check;
/* 20 */     return (Type)this;
/*    */   }
/*    */   
/*    */   public boolean isVisible() {
/* 24 */     if (this.check != null)
/* 25 */       return this.check.check(); 
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   public static interface VisibilityCheck {
/*    */     boolean check();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\api\settings\Value.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */