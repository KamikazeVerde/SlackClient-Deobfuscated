/*     */ package com.viaversion.viaversion.api.protocol.remapper;
/*     */ 
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.exception.CancelException;
/*     */ import com.viaversion.viaversion.exception.InformativeException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
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
/*     */ @Deprecated
/*     */ public abstract class PacketRemapper
/*     */ {
/*  38 */   private final List<PacketHandler> valueRemappers = new ArrayList<>();
/*     */   
/*     */   protected PacketRemapper() {
/*  41 */     registerMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void map(Type type) {
/*  50 */     handler(wrapper -> wrapper.write(type, wrapper.read(type)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void map(Type oldType, Type newType) {
/*  60 */     handler(wrapper -> wrapper.write(newType, wrapper.read(oldType)));
/*     */   }
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
/*     */   public <T1, T2> void map(Type<T1> oldType, Type<T2> newType, final Function<T1, T2> transformer) {
/*  73 */     map(oldType, new ValueTransformer<T1, T2>(newType)
/*     */         {
/*     */           public T2 transform(PacketWrapper wrapper, T1 inputValue) throws Exception {
/*  76 */             return transformer.apply(inputValue);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T1, T2> void map(ValueTransformer<T1, T2> transformer) {
/*  89 */     if (transformer.getInputType() == null) {
/*  90 */       throw new IllegalArgumentException("Use map(Type<T1>, ValueTransformer<T1, T2>) for value transformers without specified input type!");
/*     */     }
/*  92 */     map(transformer.getInputType(), transformer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T1, T2> void map(Type<T1> oldType, ValueTransformer<T1, T2> transformer) {
/* 104 */     map(new TypeRemapper<>(oldType), transformer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void map(ValueReader<T> inputReader, ValueWriter<T> outputWriter) {
/* 115 */     handler(wrapper -> outputWriter.write(wrapper, inputReader.read(wrapper)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handler(PacketHandler handler) {
/* 124 */     this.valueRemappers.add(handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void create(Type<T> type, T value) {
/* 134 */     handler(wrapper -> wrapper.write(type, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(Type type) {
/* 143 */     handler(wrapper -> wrapper.read(type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketHandler asPacketHandler() {
/* 152 */     return PacketHandlers.fromRemapper(this.valueRemappers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void remap(PacketWrapper packetWrapper) throws Exception {
/*     */     try {
/* 165 */       for (PacketHandler handler : this.valueRemappers) {
/* 166 */         handler.handle(packetWrapper);
/*     */       }
/* 168 */     } catch (CancelException e) {
/*     */       
/* 170 */       throw e;
/* 171 */     } catch (InformativeException e) {
/* 172 */       e.addSource(getClass());
/* 173 */       throw e;
/* 174 */     } catch (Exception e) {
/*     */       
/* 176 */       InformativeException ex = new InformativeException(e);
/* 177 */       ex.addSource(getClass());
/* 178 */       throw ex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract void registerMap();
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\remapper\PacketRemapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */