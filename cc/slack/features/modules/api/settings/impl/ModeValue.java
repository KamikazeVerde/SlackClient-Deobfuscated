/*    */ package cc.slack.features.modules.api.settings.impl;
/*    */ 
/*    */ import cc.slack.features.modules.api.settings.Value;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class ModeValue<T>
/*    */   extends Value<T>
/*    */ {
/*    */   private final T[] modes;
/*    */   
/*    */   public T[] getModes() {
/* 14 */     return this.modes;
/*    */   }
/*    */   public ModeValue(String name, T[] modes) {
/* 17 */     super(name, modes[0], null);
/* 18 */     this.modes = modes;
/*    */   }
/*    */   
/*    */   public ModeValue(T[] modes) {
/* 22 */     super(null, modes[0], null);
/* 23 */     this.modes = modes;
/*    */   }
/*    */   
/*    */   public T increment() {
/* 27 */     List<T> choices = Arrays.asList(this.modes);
/* 28 */     int currentIndex = choices.indexOf(getValue());
/* 29 */     currentIndex++;
/* 30 */     if (currentIndex >= choices.size()) currentIndex = 0; 
/* 31 */     setValue(choices.get(currentIndex));
/* 32 */     return choices.get(currentIndex);
/*    */   }
/*    */   
/*    */   public T decrement() {
/* 36 */     List<T> choices = Arrays.asList(this.modes);
/* 37 */     int currentIndex = choices.indexOf(getValue());
/* 38 */     currentIndex--;
/* 39 */     if (currentIndex < 0) currentIndex = choices.size() - 1; 
/* 40 */     setValue(choices.get(currentIndex));
/* 41 */     return choices.get(currentIndex);
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 45 */     List<T> choices = Arrays.asList(this.modes);
/* 46 */     return choices.indexOf(getValue());
/*    */   }
/*    */   
/*    */   public void setIndex(Integer index) {
/* 50 */     List<T> choices = Arrays.asList(this.modes);
/* 51 */     setValue(choices.get(index.intValue()));
/*    */   }
/*    */   
/*    */   public boolean is(T mode) {
/* 55 */     for (T m : this.modes) {
/* 56 */       if (m instanceof String && (
/* 57 */         (String)m).toLowerCase() == mode) {
/* 58 */         return true;
/*    */       }
/* 60 */       if (m == mode)
/* 61 */         return true; 
/*    */     } 
/* 63 */     return false;
/*    */   }
/*    */   
/*    */   public void setValueFromString(String value) {
/* 67 */     for (T choice : this.modes) {
/* 68 */       if (choice.toString().equalsIgnoreCase(value))
/* 69 */         setValue(choice); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\api\settings\impl\ModeValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */