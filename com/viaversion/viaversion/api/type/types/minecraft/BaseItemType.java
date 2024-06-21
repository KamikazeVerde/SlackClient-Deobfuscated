/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseItemType
/*    */   extends Type<Item>
/*    */ {
/*    */   protected BaseItemType() {
/* 31 */     super(Item.class);
/*    */   }
/*    */   
/*    */   protected BaseItemType(String typeName) {
/* 35 */     super(typeName, Item.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends Type> getBaseClass() {
/* 40 */     return (Class)BaseItemType.class;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\BaseItemType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */