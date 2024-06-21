/*    */ package com.viaversion.viaversion.api.minecraft.metadata.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
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
/*    */ public final class MetaTypes1_19_4
/*    */   extends AbstractMetaTypes
/*    */ {
/* 31 */   public final MetaType byteType = add(0, (Type<?>)Type.BYTE);
/* 32 */   public final MetaType varIntType = add(1, (Type<?>)Type.VAR_INT);
/* 33 */   public final MetaType longType = add(2, (Type<?>)Type.VAR_LONG);
/* 34 */   public final MetaType floatType = add(3, (Type<?>)Type.FLOAT);
/* 35 */   public final MetaType stringType = add(4, Type.STRING);
/* 36 */   public final MetaType componentType = add(5, Type.COMPONENT);
/* 37 */   public final MetaType optionalComponentType = add(6, Type.OPTIONAL_COMPONENT);
/* 38 */   public final MetaType itemType = add(7, Type.FLAT_VAR_INT_ITEM);
/* 39 */   public final MetaType booleanType = add(8, (Type<?>)Type.BOOLEAN);
/* 40 */   public final MetaType rotationType = add(9, Type.ROTATION);
/* 41 */   public final MetaType positionType = add(10, Type.POSITION1_14);
/* 42 */   public final MetaType optionalPositionType = add(11, Type.OPTIONAL_POSITION_1_14);
/* 43 */   public final MetaType directionType = add(12, (Type<?>)Type.VAR_INT);
/* 44 */   public final MetaType optionalUUIDType = add(13, Type.OPTIONAL_UUID);
/* 45 */   public final MetaType blockStateType = add(14, (Type<?>)Type.VAR_INT);
/* 46 */   public final MetaType optionalBlockStateType = add(15, (Type<?>)Type.VAR_INT);
/* 47 */   public final MetaType nbtType = add(16, Type.NBT);
/*    */   public final MetaType particleType;
/* 49 */   public final MetaType villagerDatatType = add(18, Type.VILLAGER_DATA);
/* 50 */   public final MetaType optionalVarIntType = add(19, (Type<?>)Type.OPTIONAL_VAR_INT);
/* 51 */   public final MetaType poseType = add(20, (Type<?>)Type.VAR_INT);
/* 52 */   public final MetaType catVariantType = add(21, (Type<?>)Type.VAR_INT);
/* 53 */   public final MetaType frogVariantType = add(22, (Type<?>)Type.VAR_INT);
/* 54 */   public final MetaType optionalGlobalPosition = add(23, Type.OPTIONAL_GLOBAL_POSITION);
/* 55 */   public final MetaType paintingVariantType = add(24, (Type<?>)Type.VAR_INT);
/* 56 */   public final MetaType snifferState = add(25, (Type<?>)Type.VAR_INT);
/* 57 */   public final MetaType vectorType = add(26, Type.VECTOR3F);
/* 58 */   public final MetaType quaternionType = add(27, Type.QUATERNION);
/*    */   
/*    */   public MetaTypes1_19_4(ParticleType particleType) {
/* 61 */     super(28);
/* 62 */     this.particleType = add(17, (Type<?>)particleType);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\metadata\types\MetaTypes1_19_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */