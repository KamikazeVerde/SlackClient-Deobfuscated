/*     */ package com.viaversion.viaversion.protocol.packet;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.Direction;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.api.type.TypeConverter;
/*     */ import com.viaversion.viaversion.exception.CancelException;
/*     */ import com.viaversion.viaversion.exception.InformativeException;
/*     */ import com.viaversion.viaversion.util.PipelineUtil;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
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
/*     */ public class PacketWrapperImpl
/*     */   implements PacketWrapper
/*     */ {
/*  46 */   private static final Protocol[] PROTOCOL_ARRAY = new Protocol[0];
/*     */   
/*  48 */   private final Deque<PacketValue> readableObjects = new ArrayDeque<>();
/*  49 */   private final List<PacketValue> packetValues = new ArrayList<>();
/*     */   
/*     */   private final ByteBuf inputBuffer;
/*     */   
/*     */   private final UserConnection userConnection;
/*     */   
/*     */   private boolean send = true;
/*     */   private PacketType packetType;
/*     */   private int id;
/*     */   
/*     */   public PacketWrapperImpl(int packetId, ByteBuf inputBuffer, UserConnection userConnection) {
/*  60 */     this.id = packetId;
/*  61 */     this.inputBuffer = inputBuffer;
/*  62 */     this.userConnection = userConnection;
/*     */   }
/*     */   
/*     */   public PacketWrapperImpl(PacketType packetType, ByteBuf inputBuffer, UserConnection userConnection) {
/*  66 */     this.packetType = packetType;
/*  67 */     this.id = (packetType != null) ? packetType.getId() : -1;
/*  68 */     this.inputBuffer = inputBuffer;
/*  69 */     this.userConnection = userConnection;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T get(Type<T> type, int index) throws Exception {
/*  74 */     int currentIndex = 0;
/*  75 */     for (PacketValue packetValue : this.packetValues) {
/*  76 */       if (packetValue.type() != type) {
/*     */         continue;
/*     */       }
/*  79 */       if (currentIndex == index)
/*     */       {
/*  81 */         return (T)packetValue.value();
/*     */       }
/*  83 */       currentIndex++;
/*     */     } 
/*  85 */     throw createInformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index), type, index);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(Type type, int index) {
/*  90 */     int currentIndex = 0;
/*  91 */     for (PacketValue packetValue : this.packetValues) {
/*  92 */       if (packetValue.type() != type) {
/*     */         continue;
/*     */       }
/*  95 */       if (currentIndex == index) {
/*  96 */         return true;
/*     */       }
/*  98 */       currentIndex++;
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadable(Type type, int index) {
/* 105 */     int currentIndex = 0;
/* 106 */     for (PacketValue packetValue : this.readableObjects) {
/* 107 */       if (packetValue.type().getBaseClass() != type.getBaseClass()) {
/*     */         continue;
/*     */       }
/* 110 */       if (currentIndex == index) {
/* 111 */         return true;
/*     */       }
/* 113 */       currentIndex++;
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void set(Type<T> type, int index, T value) throws Exception {
/* 121 */     int currentIndex = 0;
/* 122 */     for (PacketValue packetValue : this.packetValues) {
/* 123 */       if (packetValue.type() != type) {
/*     */         continue;
/*     */       }
/* 126 */       if (currentIndex == index) {
/* 127 */         packetValue.setValue(attemptTransform(type, value));
/*     */         return;
/*     */       } 
/* 130 */       currentIndex++;
/*     */     } 
/* 132 */     throw createInformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index), type, index);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T read(Type<T> type) throws Exception {
/* 137 */     if (type == Type.NOTHING) {
/* 138 */       return null;
/*     */     }
/*     */     
/* 141 */     if (this.readableObjects.isEmpty()) {
/* 142 */       Preconditions.checkNotNull(this.inputBuffer, "This packet does not have an input buffer.");
/*     */       
/*     */       try {
/* 145 */         return (T)type.read(this.inputBuffer);
/* 146 */       } catch (Exception e) {
/* 147 */         throw createInformativeException(e, type, this.packetValues.size() + 1);
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     PacketValue readValue = this.readableObjects.poll();
/* 152 */     Type<T> readType = readValue.type();
/* 153 */     if (readType == type || (type
/* 154 */       .getBaseClass() == readType.getBaseClass() && type
/* 155 */       .getOutputClass() == readType.getOutputClass()))
/*     */     {
/* 157 */       return (T)readValue.value(); } 
/* 158 */     if (readType == Type.NOTHING) {
/* 159 */       return read(type);
/*     */     }
/* 161 */     throw createInformativeException(new IOException("Unable to read type " + type.getTypeName() + ", found " + readValue.type().getTypeName()), type, this.readableObjects.size());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void write(Type<T> type, T value) {
/* 167 */     this.packetValues.add(new PacketValue(type, attemptTransform(type, value)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object attemptTransform(Type<?> expectedType, Object value) {
/* 178 */     if (value != null && !expectedType.getOutputClass().isAssignableFrom(value.getClass())) {
/*     */       
/* 180 */       if (expectedType instanceof TypeConverter) {
/* 181 */         return ((TypeConverter)expectedType).from(value);
/*     */       }
/*     */       
/* 184 */       Via.getPlatform().getLogger().warning("Possible type mismatch: " + value.getClass().getName() + " -> " + expectedType.getOutputClass());
/*     */     } 
/* 186 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T passthrough(Type<T> type) throws Exception {
/* 191 */     T value = read(type);
/* 192 */     write(type, value);
/* 193 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void passthroughAll() throws Exception {
/* 199 */     this.packetValues.addAll(this.readableObjects);
/* 200 */     this.readableObjects.clear();
/*     */     
/* 202 */     if (this.inputBuffer.isReadable()) {
/* 203 */       passthrough(Type.REMAINING_BYTES);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToBuffer(ByteBuf buffer) throws Exception {
/* 209 */     if (this.id != -1) {
/* 210 */       Type.VAR_INT.writePrimitive(buffer, this.id);
/*     */     }
/* 212 */     if (!this.readableObjects.isEmpty()) {
/* 213 */       this.packetValues.addAll(this.readableObjects);
/* 214 */       this.readableObjects.clear();
/*     */     } 
/*     */     
/* 217 */     int index = 0;
/* 218 */     for (PacketValue packetValue : this.packetValues) {
/*     */       try {
/* 220 */         packetValue.type().write(buffer, packetValue.value());
/* 221 */       } catch (Exception e) {
/* 222 */         throw createInformativeException(e, packetValue.type(), index);
/*     */       } 
/* 224 */       index++;
/*     */     } 
/* 226 */     writeRemaining(buffer);
/*     */   }
/*     */   
/*     */   private InformativeException createInformativeException(Exception cause, Type<?> type, int index) {
/* 230 */     return (new InformativeException(cause))
/* 231 */       .set("Index", Integer.valueOf(index))
/* 232 */       .set("Type", type.getTypeName())
/* 233 */       .set("Packet ID", Integer.valueOf(this.id))
/* 234 */       .set("Packet Type", this.packetType)
/* 235 */       .set("Data", this.packetValues);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearInputBuffer() {
/* 240 */     if (this.inputBuffer != null) {
/* 241 */       this.inputBuffer.clear();
/*     */     }
/* 243 */     this.readableObjects.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearPacket() {
/* 248 */     clearInputBuffer();
/* 249 */     this.packetValues.clear();
/*     */   }
/*     */   
/*     */   private void writeRemaining(ByteBuf output) {
/* 253 */     if (this.inputBuffer != null) {
/* 254 */       output.writeBytes(this.inputBuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void send(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
/* 260 */     send0(protocol, skipCurrentPipeline, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void scheduleSend(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
/* 265 */     send0(protocol, skipCurrentPipeline, false);
/*     */   }
/*     */   
/*     */   private void send0(Class<? extends Protocol> protocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
/* 269 */     if (isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 273 */     UserConnection connection = user();
/* 274 */     if (currentThread) {
/*     */       try {
/* 276 */         ByteBuf output = constructPacket(protocol, skipCurrentPipeline, Direction.CLIENTBOUND);
/* 277 */         connection.sendRawPacket(output);
/* 278 */       } catch (Exception e) {
/* 279 */         if (!PipelineUtil.containsCause(e, CancelException.class)) {
/* 280 */           throw e;
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 286 */     connection.getChannel().eventLoop().submit(() -> {
/*     */           try {
/*     */             ByteBuf output = constructPacket(protocol, skipCurrentPipeline, Direction.CLIENTBOUND);
/*     */             connection.sendRawPacket(output);
/* 290 */           } catch (RuntimeException e) {
/*     */             if (!PipelineUtil.containsCause(e, CancelException.class)) {
/*     */               throw e;
/*     */             }
/* 294 */           } catch (Exception e) {
/*     */             if (!PipelineUtil.containsCause(e, CancelException.class)) {
/*     */               throw new RuntimeException(e);
/*     */             }
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
/*     */ 
/*     */   
/*     */   private ByteBuf constructPacket(Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, Direction direction) throws Exception {
/* 312 */     Protocol[] protocols = (Protocol[])user().getProtocolInfo().getPipeline().pipes().toArray((Object[])PROTOCOL_ARRAY);
/* 313 */     boolean reverse = (direction == Direction.CLIENTBOUND);
/* 314 */     int index = -1;
/* 315 */     for (int i = 0; i < protocols.length; i++) {
/* 316 */       if (protocols[i].getClass() == packetProtocol) {
/* 317 */         index = i;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 322 */     if (index == -1)
/*     */     {
/* 324 */       throw new NoSuchElementException(packetProtocol.getCanonicalName());
/*     */     }
/*     */     
/* 327 */     if (skipCurrentPipeline) {
/* 328 */       index = reverse ? (index - 1) : (index + 1);
/*     */     }
/*     */ 
/*     */     
/* 332 */     resetReader();
/*     */ 
/*     */     
/* 335 */     apply(direction, user().getProtocolInfo().getState(), index, protocols, reverse);
/* 336 */     ByteBuf output = (this.inputBuffer == null) ? user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
/*     */     try {
/* 338 */       writeToBuffer(output);
/* 339 */       return output.retain();
/*     */     } finally {
/* 341 */       output.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture sendFuture(Class<? extends Protocol> packetProtocol) throws Exception {
/* 347 */     if (!isCancelled()) {
/* 348 */       ByteBuf output = constructPacket(packetProtocol, true, Direction.CLIENTBOUND);
/* 349 */       return user().sendRawPacketFuture(output);
/*     */     } 
/* 351 */     return user().getChannel().newFailedFuture(new Exception("Cancelled packet"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendRaw() throws Exception {
/* 356 */     sendRaw(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void scheduleSendRaw() throws Exception {
/* 361 */     sendRaw(false);
/*     */   }
/*     */   
/*     */   private void sendRaw(boolean currentThread) throws Exception {
/* 365 */     if (isCancelled())
/*     */       return; 
/* 367 */     ByteBuf output = (this.inputBuffer == null) ? user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
/*     */     try {
/* 369 */       writeToBuffer(output);
/* 370 */       if (currentThread) {
/* 371 */         user().sendRawPacket(output.retain());
/*     */       } else {
/* 373 */         user().scheduleSendRawPacket(output.retain());
/*     */       } 
/*     */     } finally {
/* 376 */       output.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketWrapperImpl create(int packetId) {
/* 382 */     return new PacketWrapperImpl(packetId, null, user());
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketWrapperImpl create(int packetId, PacketHandler handler) throws Exception {
/* 387 */     PacketWrapperImpl wrapper = create(packetId);
/* 388 */     handler.handle(wrapper);
/* 389 */     return wrapper;
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketWrapperImpl apply(Direction direction, State state, int index, List<Protocol> pipeline, boolean reverse) throws Exception {
/* 394 */     Protocol[] array = pipeline.<Protocol>toArray(PROTOCOL_ARRAY);
/* 395 */     return apply(direction, state, reverse ? (array.length - 1) : index, array, reverse);
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketWrapperImpl apply(Direction direction, State state, int index, List<Protocol> pipeline) throws Exception {
/* 400 */     return apply(direction, state, index, pipeline.<Protocol>toArray(PROTOCOL_ARRAY), false);
/*     */   }
/*     */ 
/*     */   
/*     */   private PacketWrapperImpl apply(Direction direction, State state, int index, Protocol[] pipeline, boolean reverse) throws Exception {
/* 405 */     if (reverse) {
/* 406 */       for (int i = index; i >= 0; i--) {
/* 407 */         pipeline[i].transform(direction, state, this);
/* 408 */         resetReader();
/*     */       } 
/*     */     } else {
/* 411 */       for (int i = index; i < pipeline.length; i++) {
/* 412 */         pipeline[i].transform(direction, state, this);
/* 413 */         resetReader();
/*     */       } 
/*     */     } 
/* 416 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/* 421 */     return !this.send;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/* 426 */     this.send = !cancel;
/*     */   }
/*     */ 
/*     */   
/*     */   public UserConnection user() {
/* 431 */     return this.userConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetReader() {
/* 437 */     for (int i = this.packetValues.size() - 1; i >= 0; i--) {
/* 438 */       this.readableObjects.addFirst(this.packetValues.get(i));
/*     */     }
/* 440 */     this.packetValues.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendToServerRaw() throws Exception {
/* 445 */     sendToServerRaw(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void scheduleSendToServerRaw() throws Exception {
/* 450 */     sendToServerRaw(false);
/*     */   }
/*     */   
/*     */   private void sendToServerRaw(boolean currentThread) throws Exception {
/* 454 */     if (isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 458 */     ByteBuf output = (this.inputBuffer == null) ? user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
/*     */     try {
/* 460 */       writeToBuffer(output);
/* 461 */       if (currentThread) {
/* 462 */         user().sendRawPacketToServer(output.retain());
/*     */       } else {
/* 464 */         user().scheduleSendRawPacketToServer(output.retain());
/*     */       } 
/*     */     } finally {
/* 467 */       output.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendToServer(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
/* 473 */     sendToServer0(protocol, skipCurrentPipeline, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void scheduleSendToServer(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
/* 478 */     sendToServer0(protocol, skipCurrentPipeline, false);
/*     */   }
/*     */   
/*     */   private void sendToServer0(Class<? extends Protocol> protocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
/* 482 */     if (isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 486 */     UserConnection connection = user();
/* 487 */     if (currentThread) {
/*     */       try {
/* 489 */         ByteBuf output = constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
/* 490 */         connection.sendRawPacketToServer(output);
/* 491 */       } catch (Exception e) {
/* 492 */         if (!PipelineUtil.containsCause(e, CancelException.class)) {
/* 493 */           throw e;
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 499 */     connection.getChannel().eventLoop().submit(() -> {
/*     */           try {
/*     */             ByteBuf output = constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
/*     */             connection.sendRawPacketToServer(output);
/* 503 */           } catch (RuntimeException e) {
/*     */             if (!PipelineUtil.containsCause(e, CancelException.class)) {
/*     */               throw e;
/*     */             }
/* 507 */           } catch (Exception e) {
/*     */             if (!PipelineUtil.containsCause(e, CancelException.class)) {
/*     */               throw new RuntimeException(e);
/*     */             }
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketType getPacketType() {
/* 517 */     return this.packetType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPacketType(PacketType packetType) {
/* 522 */     this.packetType = packetType;
/* 523 */     this.id = (packetType != null) ? packetType.getId() : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 528 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setId(int id) {
/* 535 */     this.packetType = null;
/* 536 */     this.id = id;
/*     */   }
/*     */   
/*     */   public ByteBuf getInputBuffer() {
/* 540 */     return this.inputBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 545 */     return "PacketWrapper{type=" + this.packetType + ", id=" + this.id + ", values=" + this.packetValues + ", readable=" + this.readableObjects + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class PacketValue
/*     */   {
/*     */     private final Type type;
/*     */     
/*     */     private Object value;
/*     */ 
/*     */     
/*     */     private PacketValue(Type type, Object value) {
/* 558 */       this.type = type;
/* 559 */       this.value = value;
/*     */     }
/*     */     
/*     */     public Type type() {
/* 563 */       return this.type;
/*     */     }
/*     */     
/*     */     public Object value() {
/* 567 */       return this.value;
/*     */     }
/*     */     
/*     */     public void setValue(Object value) {
/* 571 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 576 */       if (this == o) return true; 
/* 577 */       if (o == null || getClass() != o.getClass()) return false; 
/* 578 */       PacketValue that = (PacketValue)o;
/* 579 */       if (!this.type.equals(that.type)) return false; 
/* 580 */       return Objects.equals(this.value, that.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 585 */       int result = this.type.hashCode();
/* 586 */       result = 31 * result + ((this.value != null) ? this.value.hashCode() : 0);
/* 587 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 592 */       return "{" + this.type + ": " + this.value + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocol\packet\PacketWrapperImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */