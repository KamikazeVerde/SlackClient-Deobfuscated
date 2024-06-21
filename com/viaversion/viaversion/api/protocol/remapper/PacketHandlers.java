/*     */ package com.viaversion.viaversion.api.protocol.remapper;
/*     */ 
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
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
/*     */ public abstract class PacketHandlers
/*     */   implements PacketHandler
/*     */ {
/*  32 */   private final List<PacketHandler> packetHandlers = new ArrayList<>();
/*     */   
/*     */   protected PacketHandlers() {
/*  35 */     register();
/*     */   }
/*     */   
/*     */   static PacketHandler fromRemapper(List<PacketHandler> valueRemappers) {
/*  39 */     PacketHandlers handlers = new PacketHandlers()
/*     */       {
/*     */         public void register() {}
/*     */       };
/*     */     
/*  44 */     handlers.packetHandlers.addAll(valueRemappers);
/*  45 */     return handlers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void map(Type<T> type) {
/*  54 */     handler(wrapper -> wrapper.write(type, wrapper.read(type)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void map(Type oldType, Type newType) {
/*  64 */     handler(wrapper -> wrapper.write(newType, wrapper.read(oldType)));
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
/*  77 */     map(oldType, new ValueTransformer<T1, T2>(newType)
/*     */         {
/*     */           public T2 transform(PacketWrapper wrapper, T1 inputValue) {
/*  80 */             return transformer.apply(inputValue);
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
/*  93 */     if (transformer.getInputType() == null) {
/*  94 */       throw new IllegalArgumentException("Use map(Type<T1>, ValueTransformer<T1, T2>) for value transformers without specified input type!");
/*     */     }
/*  96 */     map(transformer.getInputType(), transformer);
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
/* 108 */     map(new TypeRemapper<>(oldType), transformer);
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
/* 119 */     handler(wrapper -> outputWriter.write(wrapper, inputReader.read(wrapper)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handler(PacketHandler handler) {
/* 128 */     this.packetHandlers.add(handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void create(Type<T> type, T value) {
/* 138 */     handler(wrapper -> wrapper.write(type, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(Type<?> type) {
/* 147 */     handler(wrapper -> wrapper.read(type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void handle(PacketWrapper wrapper) throws Exception {
/* 157 */     for (PacketHandler handler : this.packetHandlers) {
/* 158 */       handler.handle(wrapper);
/*     */     }
/*     */   }
/*     */   
/*     */   public int handlersSize() {
/* 163 */     return this.packetHandlers.size();
/*     */   }
/*     */   
/*     */   protected abstract void register();
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\remapper\PacketHandlers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */