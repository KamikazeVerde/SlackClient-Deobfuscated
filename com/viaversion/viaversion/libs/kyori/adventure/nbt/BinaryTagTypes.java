/*     */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ public final class BinaryTagTypes
/*     */ {
/*  42 */   public static final BinaryTagType<EndBinaryTag> END = BinaryTagType.register(EndBinaryTag.class, (byte)0, input -> EndBinaryTag.get(), null); public static final BinaryTagType<ByteBinaryTag> BYTE; public static final BinaryTagType<ShortBinaryTag> SHORT; public static final BinaryTagType<IntBinaryTag> INT; public static final BinaryTagType<LongBinaryTag> LONG; public static final BinaryTagType<FloatBinaryTag> FLOAT; public static final BinaryTagType<DoubleBinaryTag> DOUBLE; public static final BinaryTagType<ByteArrayBinaryTag> BYTE_ARRAY; public static final BinaryTagType<StringBinaryTag> STRING; public static final BinaryTagType<ListBinaryTag> LIST;
/*     */   public static final BinaryTagType<CompoundBinaryTag> COMPOUND;
/*     */   public static final BinaryTagType<IntArrayBinaryTag> INT_ARRAY;
/*     */   public static final BinaryTagType<LongArrayBinaryTag> LONG_ARRAY;
/*     */   
/*     */   static {
/*  48 */     BYTE = BinaryTagType.registerNumeric(ByteBinaryTag.class, (byte)1, input -> ByteBinaryTag.of(input.readByte()), (tag, output) -> output.writeByte(tag.value()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     SHORT = BinaryTagType.registerNumeric(ShortBinaryTag.class, (byte)2, input -> ShortBinaryTag.of(input.readShort()), (tag, output) -> output.writeShort(tag.value()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     INT = BinaryTagType.registerNumeric(IntBinaryTag.class, (byte)3, input -> IntBinaryTag.of(input.readInt()), (tag, output) -> output.writeInt(tag.value()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     LONG = BinaryTagType.registerNumeric(LongBinaryTag.class, (byte)4, input -> LongBinaryTag.of(input.readLong()), (tag, output) -> output.writeLong(tag.value()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     FLOAT = BinaryTagType.registerNumeric(FloatBinaryTag.class, (byte)5, input -> FloatBinaryTag.of(input.readFloat()), (tag, output) -> output.writeFloat(tag.value()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     DOUBLE = BinaryTagType.registerNumeric(DoubleBinaryTag.class, (byte)6, input -> DoubleBinaryTag.of(input.readDouble()), (tag, output) -> output.writeDouble(tag.value()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     BYTE_ARRAY = BinaryTagType.register(ByteArrayBinaryTag.class, (byte)7, input -> { int length = input.readInt(); BinaryTagScope ignored = TrackingDataInput.enter(input, length); try { byte[] value = new byte[length]; input.readFully(value); ByteArrayBinaryTag byteArrayBinaryTag = ByteArrayBinaryTag.of(value); if (ignored != null)
/*     */               ignored.close();  return byteArrayBinaryTag; }
/*  87 */           catch (Throwable throwable) { if (ignored != null) try { ignored.close(); } catch (Throwable throwable1)
/*     */               { throwable.addSuppressed(throwable1); }
/*     */             
/*     */ 
/*     */             
/*     */             throw throwable; }
/*     */         
/*     */         }(tag, output) -> {
/*     */           byte[] value = ByteArrayBinaryTagImpl.value(tag);
/*     */           
/*     */           output.writeInt(value.length);
/*     */           
/*     */           output.write(value);
/*     */         });
/*     */     
/* 102 */     STRING = BinaryTagType.register(StringBinaryTag.class, (byte)8, input -> StringBinaryTag.of(input.readUTF()), (tag, output) -> output.writeUTF(tag.value()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     LIST = BinaryTagType.register(ListBinaryTag.class, (byte)9, input -> { BinaryTagType<? extends BinaryTag> type = BinaryTagType.of(input.readByte()); int length = input.readInt(); BinaryTagScope ignored = TrackingDataInput.enter(input, length * 8L); try { List<BinaryTag> tags = new ArrayList<>(length); for (int i = 0; i < length; i++)
/*     */               tags.add(type.read(input));  ListBinaryTag listBinaryTag = ListBinaryTag.of(type, tags); if (ignored != null)
/*     */               ignored.close();  return listBinaryTag; }
/* 112 */           catch (Throwable throwable) { if (ignored != null) try { ignored.close(); } catch (Throwable throwable1)
/*     */               { throwable.addSuppressed(throwable1); }
/*     */             
/*     */ 
/*     */ 
/*     */             
/*     */             throw throwable; }
/*     */         
/*     */         }(tag, output) -> {
/*     */           output.writeByte(tag.elementType().id());
/*     */ 
/*     */           
/*     */           int size = tag.size();
/*     */           
/*     */           output.writeInt(size);
/*     */           
/*     */           for (BinaryTag item : tag) {
/*     */             BinaryTagType.writeUntyped(item.type(), item, output);
/*     */           }
/*     */         });
/*     */     
/* 133 */     COMPOUND = BinaryTagType.register(CompoundBinaryTag.class, (byte)10, input -> { BinaryTagScope ignored = TrackingDataInput.enter(input); try { Map<String, BinaryTag> tags = new HashMap<>(); BinaryTagType<? extends BinaryTag> type; while ((type = BinaryTagType.of(input.readByte())) != END) { String key = input.readUTF(); BinaryTag tag = type.read(input); tags.put(key, tag); }  CompoundBinaryTagImpl compoundBinaryTagImpl = new CompoundBinaryTagImpl(tags); if (ignored != null)
/* 134 */               ignored.close();  return compoundBinaryTagImpl; } catch (Throwable throwable) { if (ignored != null) try { ignored.close(); } catch (Throwable throwable1)
/*     */               { throwable.addSuppressed(throwable1); }
/*     */             
/*     */ 
/*     */ 
/*     */             
/*     */             throw throwable; }
/*     */         
/*     */         }(tag, output) -> {
/*     */           for (Map.Entry<String, ? extends BinaryTag> entry : (Iterable<Map.Entry<String, ? extends BinaryTag>>)tag) {
/*     */             BinaryTag value = entry.getValue();
/*     */ 
/*     */             
/*     */             if (value != null) {
/*     */               BinaryTagType<? extends BinaryTag> type = value.type();
/*     */ 
/*     */               
/*     */               output.writeByte(type.id());
/*     */ 
/*     */               
/*     */               if (type != END) {
/*     */                 output.writeUTF(entry.getKey());
/*     */                 
/*     */                 BinaryTagType.writeUntyped(type, value, output);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/*     */           output.writeByte(END.id());
/*     */         });
/*     */     
/* 165 */     INT_ARRAY = BinaryTagType.register(IntArrayBinaryTag.class, (byte)11, input -> { int length = input.readInt(); BinaryTagScope ignored = TrackingDataInput.enter(input, length * 4L); try { int[] value = new int[length]; for (int i = 0; i < length; i++)
/*     */               value[i] = input.readInt();  IntArrayBinaryTag intArrayBinaryTag = IntArrayBinaryTag.of(value); if (ignored != null)
/* 167 */               ignored.close();  return intArrayBinaryTag; } catch (Throwable throwable) { if (ignored != null) try { ignored.close(); } catch (Throwable throwable1)
/*     */               { throwable.addSuppressed(throwable1); }
/*     */             
/*     */ 
/*     */ 
/*     */             
/*     */             throw throwable; }
/*     */         
/*     */         }(tag, output) -> {
/*     */           int[] value = IntArrayBinaryTagImpl.value(tag);
/*     */ 
/*     */           
/*     */           int length = value.length;
/*     */ 
/*     */           
/*     */           output.writeInt(length);
/*     */           
/*     */           for (int i = 0; i < length; i++) {
/*     */             output.writeInt(value[i]);
/*     */           }
/*     */         });
/*     */     
/* 189 */     LONG_ARRAY = BinaryTagType.register(LongArrayBinaryTag.class, (byte)12, input -> { int length = input.readInt(); BinaryTagScope ignored = TrackingDataInput.enter(input, length * 8L); try { long[] value = new long[length]; for (int i = 0; i < length; i++)
/*     */               value[i] = input.readLong();  LongArrayBinaryTag longArrayBinaryTag = LongArrayBinaryTag.of(value); if (ignored != null)
/* 191 */               ignored.close();  return longArrayBinaryTag; } catch (Throwable throwable) { if (ignored != null) try { ignored.close(); } catch (Throwable throwable1)
/*     */               { throwable.addSuppressed(throwable1); }
/*     */                
/*     */             throw throwable; }
/*     */         
/*     */         }(tag, output) -> {
/*     */           long[] value = LongArrayBinaryTagImpl.value(tag);
/*     */           int length = value.length;
/*     */           output.writeInt(length);
/*     */           for (int i = 0; i < length; i++)
/*     */             output.writeLong(value[i]); 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\BinaryTagTypes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */