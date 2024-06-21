/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class PropertyBool
/*    */   extends PropertyHelper<Boolean> {
/*  8 */   private final ImmutableSet<Boolean> allowedValues = ImmutableSet.of(Boolean.TRUE, Boolean.FALSE);
/*    */ 
/*    */   
/*    */   protected PropertyBool(String name) {
/* 12 */     super(name, Boolean.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<Boolean> getAllowedValues() {
/* 17 */     return (Collection<Boolean>)this.allowedValues;
/*    */   }
/*    */ 
/*    */   
/*    */   public static PropertyBool create(String name) {
/* 22 */     return new PropertyBool(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName(Boolean value) {
/* 30 */     return value.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\block\properties\PropertyBool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */