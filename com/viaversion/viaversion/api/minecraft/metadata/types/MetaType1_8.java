/*    */ package com.viaversion.viaversion.api.minecraft.metadata.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
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
/*    */ public enum MetaType1_8
/*    */   implements MetaType
/*    */ {
/* 29 */   Byte(0, (Type)Type.BYTE),
/* 30 */   Short(1, (Type)Type.SHORT),
/* 31 */   Int(2, (Type)Type.INT),
/* 32 */   Float(3, (Type)Type.FLOAT),
/* 33 */   String(4, Type.STRING),
/* 34 */   Slot(5, Type.ITEM),
/* 35 */   Position(6, Type.VECTOR),
/* 36 */   Rotation(7, Type.ROTATION),
/* 37 */   NonExistent(-1, (Type)Type.NOTHING);
/*    */   
/*    */   private final int typeID;
/*    */   
/*    */   private final Type type;
/*    */   
/*    */   MetaType1_8(int typeID, Type type) {
/* 44 */     this.typeID = typeID;
/* 45 */     this.type = type;
/*    */   }
/*    */   
/*    */   public static MetaType1_8 byId(int id) {
/* 49 */     return values()[id];
/*    */   }
/*    */ 
/*    */   
/*    */   public int typeId() {
/* 54 */     return this.typeID;
/*    */   }
/*    */ 
/*    */   
/*    */   public Type type() {
/* 59 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\metadata\types\MetaType1_8.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */