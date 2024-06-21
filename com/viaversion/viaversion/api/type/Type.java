/*     */ package com.viaversion.viaversion.api.type;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*     */ import com.viaversion.viaversion.api.minecraft.EulerAngle;
/*     */ import com.viaversion.viaversion.api.minecraft.GlobalPosition;
/*     */ import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.minecraft.ProfileKey;
/*     */ import com.viaversion.viaversion.api.minecraft.Quaternion;
/*     */ import com.viaversion.viaversion.api.minecraft.Vector;
/*     */ import com.viaversion.viaversion.api.minecraft.Vector3f;
/*     */ import com.viaversion.viaversion.api.minecraft.VillagerData;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.type.types.ArrayType;
/*     */ import com.viaversion.viaversion.api.type.types.BooleanType;
/*     */ import com.viaversion.viaversion.api.type.types.ByteArrayType;
/*     */ import com.viaversion.viaversion.api.type.types.ByteType;
/*     */ import com.viaversion.viaversion.api.type.types.ComponentType;
/*     */ import com.viaversion.viaversion.api.type.types.DoubleType;
/*     */ import com.viaversion.viaversion.api.type.types.FloatType;
/*     */ import com.viaversion.viaversion.api.type.types.IntType;
/*     */ import com.viaversion.viaversion.api.type.types.LongArrayType;
/*     */ import com.viaversion.viaversion.api.type.types.LongType;
/*     */ import com.viaversion.viaversion.api.type.types.RemainingBytesType;
/*     */ import com.viaversion.viaversion.api.type.types.ShortByteArrayType;
/*     */ import com.viaversion.viaversion.api.type.types.ShortType;
/*     */ import com.viaversion.viaversion.api.type.types.StringType;
/*     */ import com.viaversion.viaversion.api.type.types.UUIDIntArrayType;
/*     */ import com.viaversion.viaversion.api.type.types.UUIDType;
/*     */ import com.viaversion.viaversion.api.type.types.UnsignedByteType;
/*     */ import com.viaversion.viaversion.api.type.types.UnsignedShortType;
/*     */ import com.viaversion.viaversion.api.type.types.VarIntArrayType;
/*     */ import com.viaversion.viaversion.api.type.types.VarIntType;
/*     */ import com.viaversion.viaversion.api.type.types.VarLongType;
/*     */ import com.viaversion.viaversion.api.type.types.VoidType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.BlockChangeRecordType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.EulerAngleType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.FlatItemArrayType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.FlatItemType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.FlatVarIntItemArrayType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.FlatVarIntItemType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.GlobalPositionType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ItemArrayType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ItemType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.NBTType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.OptionalVarIntType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.PlayerMessageSignatureType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.Position1_14Type;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.PositionType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.ProfileKeyType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.QuaternionType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.VarLongBlockChangeRecordType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.Vector3fType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.VectorType;
/*     */ import com.viaversion.viaversion.api.type.types.minecraft.VillagerDataType;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Type<T>
/*     */   implements ByteBufReader<T>, ByteBufWriter<T>
/*     */ {
/*  90 */   public static final ByteType BYTE = new ByteType();
/*  91 */   public static final UnsignedByteType UNSIGNED_BYTE = new UnsignedByteType();
/*  92 */   public static final Type<byte[]> BYTE_ARRAY_PRIMITIVE = (Type<byte[]>)new ByteArrayType();
/*  93 */   public static final Type<byte[]> OPTIONAL_BYTE_ARRAY_PRIMITIVE = (Type<byte[]>)new ByteArrayType.OptionalByteArrayType();
/*  94 */   public static final Type<byte[]> SHORT_BYTE_ARRAY = (Type<byte[]>)new ShortByteArrayType();
/*  95 */   public static final Type<byte[]> REMAINING_BYTES = (Type<byte[]>)new RemainingBytesType();
/*     */   
/*  97 */   public static final ShortType SHORT = new ShortType();
/*  98 */   public static final UnsignedShortType UNSIGNED_SHORT = new UnsignedShortType();
/*     */   
/* 100 */   public static final IntType INT = new IntType();
/* 101 */   public static final FloatType FLOAT = new FloatType();
/* 102 */   public static final FloatType.OptionalFloatType OPTIONAL_FLOAT = new FloatType.OptionalFloatType();
/* 103 */   public static final DoubleType DOUBLE = new DoubleType();
/*     */   
/* 105 */   public static final LongType LONG = new LongType();
/* 106 */   public static final Type<long[]> LONG_ARRAY_PRIMITIVE = (Type<long[]>)new LongArrayType();
/*     */   
/* 108 */   public static final BooleanType BOOLEAN = new BooleanType();
/*     */ 
/*     */   
/* 111 */   public static final Type<JsonElement> COMPONENT = (Type<JsonElement>)new ComponentType();
/* 112 */   public static final Type<JsonElement> OPTIONAL_COMPONENT = (Type<JsonElement>)new ComponentType.OptionalComponentType();
/*     */   
/* 114 */   public static final Type<String> STRING = (Type<String>)new StringType();
/* 115 */   public static final Type<String> OPTIONAL_STRING = (Type<String>)new StringType.OptionalStringType();
/* 116 */   public static final Type<String[]> STRING_ARRAY = (Type<String[]>)new ArrayType(STRING);
/*     */   
/* 118 */   public static final Type<UUID> UUID = (Type<UUID>)new UUIDType();
/* 119 */   public static final Type<UUID> OPTIONAL_UUID = (Type<UUID>)new UUIDType.OptionalUUIDType();
/* 120 */   public static final Type<UUID[]> UUID_ARRAY = (Type<UUID[]>)new ArrayType(UUID);
/*     */   @Deprecated
/* 122 */   public static final Type<UUID> UUID_INT_ARRAY = (Type<UUID>)new UUIDIntArrayType();
/*     */   
/* 124 */   public static final VarIntType VAR_INT = new VarIntType();
/* 125 */   public static final OptionalVarIntType OPTIONAL_VAR_INT = new OptionalVarIntType();
/* 126 */   public static final Type<int[]> VAR_INT_ARRAY_PRIMITIVE = (Type<int[]>)new VarIntArrayType();
/* 127 */   public static final VarLongType VAR_LONG = new VarLongType();
/*     */ 
/*     */   
/*     */   @Deprecated
/* 131 */   public static final Type<Byte[]> BYTE_ARRAY = (Type<Byte[]>)new ArrayType((Type)BYTE);
/*     */   @Deprecated
/* 133 */   public static final Type<Short[]> UNSIGNED_BYTE_ARRAY = (Type<Short[]>)new ArrayType((Type)UNSIGNED_BYTE);
/*     */   @Deprecated
/* 135 */   public static final Type<Boolean[]> BOOLEAN_ARRAY = (Type<Boolean[]>)new ArrayType((Type)BOOLEAN);
/*     */   @Deprecated
/* 137 */   public static final Type<Integer[]> INT_ARRAY = (Type<Integer[]>)new ArrayType((Type)INT);
/*     */   @Deprecated
/* 139 */   public static final Type<Short[]> SHORT_ARRAY = (Type<Short[]>)new ArrayType((Type)SHORT);
/*     */   @Deprecated
/* 141 */   public static final Type<Integer[]> UNSIGNED_SHORT_ARRAY = (Type<Integer[]>)new ArrayType((Type)UNSIGNED_SHORT);
/*     */   @Deprecated
/* 143 */   public static final Type<Double[]> DOUBLE_ARRAY = (Type<Double[]>)new ArrayType((Type)DOUBLE);
/*     */   @Deprecated
/* 145 */   public static final Type<Long[]> LONG_ARRAY = (Type<Long[]>)new ArrayType((Type)LONG);
/*     */   @Deprecated
/* 147 */   public static final Type<Float[]> FLOAT_ARRAY = (Type<Float[]>)new ArrayType((Type)FLOAT);
/*     */   @Deprecated
/* 149 */   public static final Type<Integer[]> VAR_INT_ARRAY = (Type<Integer[]>)new ArrayType((Type)VAR_INT);
/*     */   @Deprecated
/* 151 */   public static final Type<Long[]> VAR_LONG_ARRAY = (Type<Long[]>)new ArrayType((Type)VAR_LONG);
/*     */ 
/*     */   
/* 154 */   public static final VoidType NOTHING = new VoidType();
/*     */ 
/*     */   
/* 157 */   public static final Type<Position> POSITION = (Type<Position>)new PositionType();
/* 158 */   public static final Type<Position> OPTIONAL_POSITION = (Type<Position>)new PositionType.OptionalPositionType();
/* 159 */   public static final Type<Position> POSITION1_14 = (Type<Position>)new Position1_14Type();
/* 160 */   public static final Type<Position> OPTIONAL_POSITION_1_14 = (Type<Position>)new Position1_14Type.OptionalPosition1_14Type();
/* 161 */   public static final Type<EulerAngle> ROTATION = (Type<EulerAngle>)new EulerAngleType();
/* 162 */   public static final Type<Vector> VECTOR = (Type<Vector>)new VectorType();
/* 163 */   public static final Type<Vector3f> VECTOR3F = (Type<Vector3f>)new Vector3fType();
/* 164 */   public static final Type<Quaternion> QUATERNION = (Type<Quaternion>)new QuaternionType();
/* 165 */   public static final Type<CompoundTag> NBT = (Type<CompoundTag>)new NBTType();
/* 166 */   public static final Type<CompoundTag[]> NBT_ARRAY = (Type<CompoundTag[]>)new ArrayType(NBT);
/*     */   
/* 168 */   public static final Type<GlobalPosition> GLOBAL_POSITION = (Type<GlobalPosition>)new GlobalPositionType();
/* 169 */   public static final Type<GlobalPosition> OPTIONAL_GLOBAL_POSITION = (Type<GlobalPosition>)new GlobalPositionType.OptionalGlobalPositionType();
/*     */   
/* 171 */   public static final Type<BlockChangeRecord> BLOCK_CHANGE_RECORD = (Type<BlockChangeRecord>)new BlockChangeRecordType();
/* 172 */   public static final Type<BlockChangeRecord[]> BLOCK_CHANGE_RECORD_ARRAY = (Type<BlockChangeRecord[]>)new ArrayType(BLOCK_CHANGE_RECORD);
/*     */   
/* 174 */   public static final Type<BlockChangeRecord> VAR_LONG_BLOCK_CHANGE_RECORD = (Type<BlockChangeRecord>)new VarLongBlockChangeRecordType();
/* 175 */   public static final Type<BlockChangeRecord[]> VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY = (Type<BlockChangeRecord[]>)new ArrayType(VAR_LONG_BLOCK_CHANGE_RECORD);
/*     */   
/* 177 */   public static final Type<VillagerData> VILLAGER_DATA = (Type<VillagerData>)new VillagerDataType();
/*     */   
/* 179 */   public static final Type<Item> ITEM = (Type<Item>)new ItemType();
/* 180 */   public static final Type<Item[]> ITEM_ARRAY = (Type<Item[]>)new ItemArrayType();
/*     */   
/* 182 */   public static final Type<ProfileKey> PROFILE_KEY = (Type<ProfileKey>)new ProfileKeyType();
/* 183 */   public static final Type<ProfileKey> OPTIONAL_PROFILE_KEY = (Type<ProfileKey>)new ProfileKeyType.OptionalProfileKeyType();
/*     */   
/* 185 */   public static final Type<PlayerMessageSignature> PLAYER_MESSAGE_SIGNATURE = (Type<PlayerMessageSignature>)new PlayerMessageSignatureType();
/* 186 */   public static final Type<PlayerMessageSignature> OPTIONAL_PLAYER_MESSAGE_SIGNATURE = (Type<PlayerMessageSignature>)new PlayerMessageSignatureType.OptionalPlayerMessageSignatureType();
/* 187 */   public static final Type<PlayerMessageSignature[]> PLAYER_MESSAGE_SIGNATURE_ARRAY = (Type<PlayerMessageSignature[]>)new ArrayType(PLAYER_MESSAGE_SIGNATURE);
/*     */ 
/*     */   
/* 190 */   public static final Type<Item> FLAT_ITEM = (Type<Item>)new FlatItemType();
/* 191 */   public static final Type<Item> FLAT_VAR_INT_ITEM = (Type<Item>)new FlatVarIntItemType();
/* 192 */   public static final Type<Item[]> FLAT_ITEM_ARRAY = (Type<Item[]>)new FlatItemArrayType();
/* 193 */   public static final Type<Item[]> FLAT_VAR_INT_ITEM_ARRAY = (Type<Item[]>)new FlatVarIntItemArrayType();
/* 194 */   public static final Type<Item[]> FLAT_ITEM_ARRAY_VAR_INT = (Type<Item[]>)new ArrayType(FLAT_ITEM);
/* 195 */   public static final Type<Item[]> FLAT_VAR_INT_ITEM_ARRAY_VAR_INT = (Type<Item[]>)new ArrayType(FLAT_VAR_INT_ITEM);
/*     */   
/*     */   private final Class<? super T> outputClass;
/*     */   
/*     */   private final String typeName;
/*     */   
/*     */   protected Type(Class<? super T> outputClass) {
/* 202 */     this(outputClass.getSimpleName(), outputClass);
/*     */   }
/*     */   
/*     */   protected Type(String typeName, Class<? super T> outputClass) {
/* 206 */     this.outputClass = outputClass;
/* 207 */     this.typeName = typeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? super T> getOutputClass() {
/* 216 */     return this.outputClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeName() {
/* 225 */     return this.typeName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends Type> getBaseClass() {
/* 235 */     return (Class)getClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 240 */     return this.typeName;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */