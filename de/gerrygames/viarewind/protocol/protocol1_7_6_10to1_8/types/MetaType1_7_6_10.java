/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ 
/*    */ public enum MetaType1_7_6_10 implements MetaType {
/*  7 */   Byte(0, (Type)Type.BYTE),
/*  8 */   Short(1, (Type)Type.SHORT),
/*  9 */   Int(2, (Type)Type.INT),
/* 10 */   Float(3, (Type)Type.FLOAT),
/* 11 */   String(4, Type.STRING),
/* 12 */   Slot(5, Types1_7_6_10.COMPRESSED_NBT_ITEM),
/* 13 */   Position(6, Type.VECTOR),
/* 14 */   NonExistent(-1, (Type)Type.NOTHING);
/*    */   
/*    */   private final int typeID;
/*    */   private final Type type;
/*    */   
/*    */   public static MetaType1_7_6_10 byId(int id) {
/* 20 */     return values()[id];
/*    */   }
/*    */   
/*    */   MetaType1_7_6_10(int typeID, Type type) {
/* 24 */     this.typeID = typeID;
/* 25 */     this.type = type;
/*    */   }
/*    */   
/*    */   public int typeId() {
/* 29 */     return this.typeID;
/*    */   }
/*    */   
/*    */   public Type type() {
/* 33 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\types\MetaType1_7_6_10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */