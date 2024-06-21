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
/*    */ public enum MetaType1_9
/*    */   implements MetaType
/*    */ {
/* 29 */   Byte(0, (Type)Type.BYTE),
/* 30 */   VarInt(1, (Type)Type.VAR_INT),
/* 31 */   Float(2, (Type)Type.FLOAT),
/* 32 */   String(3, Type.STRING),
/* 33 */   Chat(4, Type.COMPONENT),
/* 34 */   Slot(5, Type.ITEM),
/* 35 */   Boolean(6, (Type)Type.BOOLEAN),
/* 36 */   Vector3F(7, Type.ROTATION),
/* 37 */   Position(8, Type.POSITION),
/* 38 */   OptPosition(9, Type.OPTIONAL_POSITION),
/* 39 */   Direction(10, (Type)Type.VAR_INT),
/* 40 */   OptUUID(11, Type.OPTIONAL_UUID),
/* 41 */   BlockID(12, (Type)Type.VAR_INT);
/*    */   
/*    */   private final int typeID;
/*    */   private final Type type;
/*    */   
/*    */   MetaType1_9(int typeID, Type type) {
/* 47 */     this.typeID = typeID;
/* 48 */     this.type = type;
/*    */   }
/*    */   
/*    */   public static MetaType1_9 byId(int id) {
/* 52 */     return values()[id];
/*    */   }
/*    */ 
/*    */   
/*    */   public int typeId() {
/* 57 */     return this.typeID;
/*    */   }
/*    */ 
/*    */   
/*    */   public Type type() {
/* 62 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\metadata\types\MetaType1_9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */