/*    */ package com.viaversion.viaversion.api.minecraft.metadata.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_13;
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
/*    */ @Deprecated
/*    */ public enum MetaType1_13
/*    */   implements MetaType
/*    */ {
/* 31 */   Byte(0, (Type)Type.BYTE),
/* 32 */   VarInt(1, (Type)Type.VAR_INT),
/* 33 */   Float(2, (Type)Type.FLOAT),
/* 34 */   String(3, Type.STRING),
/* 35 */   Chat(4, Type.COMPONENT),
/* 36 */   OptChat(5, Type.OPTIONAL_COMPONENT),
/* 37 */   Slot(6, Type.FLAT_ITEM),
/* 38 */   Boolean(7, (Type)Type.BOOLEAN),
/* 39 */   Vector3F(8, Type.ROTATION),
/* 40 */   Position(9, Type.POSITION),
/* 41 */   OptPosition(10, Type.OPTIONAL_POSITION),
/* 42 */   Direction(11, (Type)Type.VAR_INT),
/* 43 */   OptUUID(12, Type.OPTIONAL_UUID),
/* 44 */   BlockID(13, (Type)Type.VAR_INT),
/* 45 */   NBTTag(14, Type.NBT),
/* 46 */   PARTICLE(15, (Type)Types1_13.PARTICLE);
/*    */   
/*    */   private final int typeID;
/*    */   private final Type type;
/*    */   
/*    */   MetaType1_13(int typeID, Type type) {
/* 52 */     this.typeID = typeID;
/* 53 */     this.type = type;
/*    */   }
/*    */   
/*    */   public static MetaType1_13 byId(int id) {
/* 57 */     return values()[id];
/*    */   }
/*    */ 
/*    */   
/*    */   public int typeId() {
/* 62 */     return this.typeID;
/*    */   }
/*    */ 
/*    */   
/*    */   public Type type() {
/* 67 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\metadata\types\MetaType1_13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */