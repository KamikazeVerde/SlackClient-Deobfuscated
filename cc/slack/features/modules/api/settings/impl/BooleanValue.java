/*    */ package cc.slack.features.modules.api.settings.impl;
/*    */ 
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ 
/*    */ 
/*    */ public class BooleanValue
/*    */   extends Value<Boolean>
/*    */ {
/*    */   public BooleanValue(String name, boolean defaultValue) {
/* 10 */     super(name, Boolean.valueOf(defaultValue), null);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\api\settings\impl\BooleanValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */