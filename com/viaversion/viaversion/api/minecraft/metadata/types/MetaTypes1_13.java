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
/*    */ public final class MetaTypes1_13
/*    */   extends AbstractMetaTypes
/*    */ {
/* 31 */   public final MetaType byteType = add(0, (Type<?>)Type.BYTE);
/* 32 */   public final MetaType varIntType = add(1, (Type<?>)Type.VAR_INT);
/* 33 */   public final MetaType floatType = add(2, (Type<?>)Type.FLOAT);
/* 34 */   public final MetaType stringType = add(3, Type.STRING);
/* 35 */   public final MetaType componentType = add(4, Type.COMPONENT);
/* 36 */   public final MetaType optionalComponentType = add(5, Type.OPTIONAL_COMPONENT);
/* 37 */   public final MetaType itemType = add(6, Type.FLAT_ITEM);
/* 38 */   public final MetaType booleanType = add(7, (Type<?>)Type.BOOLEAN);
/* 39 */   public final MetaType rotationType = add(8, Type.ROTATION);
/* 40 */   public final MetaType positionType = add(9, Type.POSITION);
/* 41 */   public final MetaType optionalPositionType = add(10, Type.OPTIONAL_POSITION);
/* 42 */   public final MetaType directionType = add(11, (Type<?>)Type.VAR_INT);
/* 43 */   public final MetaType optionalUUIDType = add(12, Type.OPTIONAL_UUID);
/* 44 */   public final MetaType blockStateType = add(13, (Type<?>)Type.VAR_INT);
/* 45 */   public final MetaType nbtType = add(14, Type.NBT);
/*    */   public final MetaType particleType;
/*    */   
/*    */   public MetaTypes1_13(ParticleType particleType) {
/* 49 */     super(16);
/* 50 */     this.particleType = add(15, (Type<?>)particleType);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\metadata\types\MetaTypes1_13.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */