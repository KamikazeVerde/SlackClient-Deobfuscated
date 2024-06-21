/*    */ package com.viaversion.viaversion.api.minecraft.metadata.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.version.Types1_17;
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
/*    */ public enum MetaType1_17
/*    */   implements MetaType
/*    */ {
/* 31 */   BYTE(0, (Type)Type.BYTE),
/* 32 */   VAR_INT(1, (Type)Type.VAR_INT),
/* 33 */   FLOAT(2, (Type)Type.FLOAT),
/* 34 */   STRING(3, Type.STRING),
/* 35 */   COMPONENT(4, Type.COMPONENT),
/* 36 */   OPT_COMPONENT(5, Type.OPTIONAL_COMPONENT),
/* 37 */   ITEM(6, Type.FLAT_VAR_INT_ITEM),
/* 38 */   BOOLEAN(7, (Type)Type.BOOLEAN),
/* 39 */   ROTATION(8, Type.ROTATION),
/* 40 */   POSITION(9, Type.POSITION1_14),
/* 41 */   OPT_POSITION(10, Type.OPTIONAL_POSITION_1_14),
/* 42 */   DIRECTION(11, (Type)Type.VAR_INT),
/* 43 */   OPT_UUID(12, Type.OPTIONAL_UUID),
/* 44 */   BLOCK_STATE(13, (Type)Type.VAR_INT),
/* 45 */   NBT(14, Type.NBT),
/* 46 */   PARTICLE(15, (Type)Types1_17.PARTICLE),
/* 47 */   VILLAGER_DATA(16, Type.VILLAGER_DATA),
/* 48 */   OPT_VAR_INT(17, (Type)Type.OPTIONAL_VAR_INT),
/* 49 */   POSE(18, (Type)Type.VAR_INT); private static final MetaType1_17[] VALUES;
/*    */   static {
/* 51 */     VALUES = values();
/*    */   }
/*    */   private final int typeId; private final Type type;
/*    */   
/*    */   MetaType1_17(int typeId, Type type) {
/* 56 */     this.typeId = typeId;
/* 57 */     this.type = type;
/*    */   }
/*    */   
/*    */   public static MetaType1_17 byId(int id) {
/* 61 */     return VALUES[id];
/*    */   }
/*    */ 
/*    */   
/*    */   public int typeId() {
/* 66 */     return this.typeId;
/*    */   }
/*    */ 
/*    */   
/*    */   public Type type() {
/* 71 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\metadata\types\MetaType1_17.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */