/*    */ package cc.slack.features.modules.api.settings.impl;
/*    */ 
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ 
/*    */ public class NumberValue<T extends Number> extends Value<T> {
/*    */   private final T minimum;
/*    */   private final T maximum;
/*    */   private final T increment;
/*    */   
/* 10 */   public T getMinimum() { return this.minimum; }
/* 11 */   public T getMaximum() { return this.maximum; } public T getIncrement() {
/* 12 */     return this.increment;
/*    */   }
/*    */   public NumberValue(String name, T defaultValue, T minimum, T maximum, T increment) {
/* 15 */     super(name, defaultValue, null);
/* 16 */     this.minimum = minimum;
/* 17 */     this.maximum = maximum;
/* 18 */     this.increment = increment;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\api\settings\impl\NumberValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */