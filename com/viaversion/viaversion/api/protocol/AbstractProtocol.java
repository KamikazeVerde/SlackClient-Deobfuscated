/*     */ package com.viaversion.viaversion.api.protocol;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.Direction;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.State;
/*     */ import com.viaversion.viaversion.api.protocol.packet.mapping.PacketMapping;
/*     */ import com.viaversion.viaversion.api.protocol.packet.mapping.PacketMappings;
/*     */ import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
/*     */ import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
/*     */ import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
/*     */ import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
/*     */ import com.viaversion.viaversion.api.rewriter.Rewriter;
/*     */ import com.viaversion.viaversion.exception.CancelException;
/*     */ import com.viaversion.viaversion.exception.InformativeException;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.logging.Level;
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
/*     */ 
/*     */ public abstract class AbstractProtocol<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
/*     */   implements Protocol<CU, CM, SM, SU>
/*     */ {
/*     */   protected final Class<CU> unmappedClientboundPacketType;
/*     */   protected final Class<CM> mappedClientboundPacketType;
/*     */   protected final Class<SM> mappedServerboundPacketType;
/*     */   protected final Class<SU> unmappedServerboundPacketType;
/*     */   protected final PacketTypesProvider<CU, CM, SM, SU> packetTypesProvider;
/*     */   protected final PacketMappings clientboundMappings;
/*     */   protected final PacketMappings serverboundMappings;
/*  70 */   private final Map<Class<?>, Object> storedObjects = new HashMap<>();
/*     */   private boolean initialized;
/*     */   
/*     */   @Deprecated
/*     */   protected AbstractProtocol() {
/*  75 */     this((Class<CU>)null, (Class<CM>)null, (Class<SM>)null, (Class<SU>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractProtocol(Class<CU> unmappedClientboundPacketType, Class<CM> mappedClientboundPacketType, Class<SM> mappedServerboundPacketType, Class<SU> unmappedServerboundPacketType) {
/*  83 */     this.unmappedClientboundPacketType = unmappedClientboundPacketType;
/*  84 */     this.mappedClientboundPacketType = mappedClientboundPacketType;
/*  85 */     this.mappedServerboundPacketType = mappedServerboundPacketType;
/*  86 */     this.unmappedServerboundPacketType = unmappedServerboundPacketType;
/*  87 */     this.packetTypesProvider = createPacketTypesProvider();
/*  88 */     this.clientboundMappings = createClientboundPacketMappings();
/*  89 */     this.serverboundMappings = createServerboundPacketMappings();
/*     */   }
/*     */ 
/*     */   
/*     */   public final void initialize() {
/*  94 */     Preconditions.checkArgument(!this.initialized, "Protocol has already been initialized");
/*  95 */     this.initialized = true;
/*     */     
/*  97 */     registerPackets();
/*     */ 
/*     */     
/* 100 */     if (this.unmappedClientboundPacketType != null && this.mappedClientboundPacketType != null && this.unmappedClientboundPacketType != this.mappedClientboundPacketType)
/*     */     {
/* 102 */       registerPacketIdChanges(this.packetTypesProvider
/* 103 */           .unmappedClientboundPacketTypes(), this.packetTypesProvider
/* 104 */           .mappedClientboundPacketTypes(), this::hasRegisteredClientbound, this::registerClientbound);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 109 */     if (this.mappedServerboundPacketType != null && this.unmappedServerboundPacketType != null && this.mappedServerboundPacketType != this.unmappedServerboundPacketType)
/*     */     {
/* 111 */       registerPacketIdChanges(this.packetTypesProvider
/* 112 */           .unmappedServerboundPacketTypes(), this.packetTypesProvider
/* 113 */           .mappedServerboundPacketTypes(), this::hasRegisteredServerbound, this::registerServerbound);
/*     */     }
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
/*     */   private <U extends PacketType, M extends PacketType> void registerPacketIdChanges(Map<State, PacketTypeMap<U>> unmappedPacketTypes, Map<State, PacketTypeMap<M>> mappedPacketTypes, Predicate<U> registeredPredicate, BiConsumer<U, M> registerConsumer) {
/* 126 */     for (Map.Entry<State, PacketTypeMap<M>> entry : mappedPacketTypes.entrySet()) {
/* 127 */       PacketTypeMap<M> mappedTypes = entry.getValue();
/* 128 */       for (PacketType packetType1 : ((PacketTypeMap)unmappedPacketTypes.get(entry.getKey())).types()) {
/* 129 */         PacketType packetType2 = (PacketType)mappedTypes.typeByName(packetType1.getName());
/* 130 */         if (packetType2 == null) {
/*     */           
/* 132 */           Preconditions.checkArgument(registeredPredicate.test((U)packetType1), "Packet %s in %s has no mapping - it needs to be manually cancelled or remapped", new Object[] { packetType1, 
/* 133 */                 getClass() });
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 138 */         if (packetType1.getId() != packetType2.getId() && !registeredPredicate.test((U)packetType1)) {
/* 139 */           registerConsumer.accept((U)packetType1, (M)packetType2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void loadMappingData() {
/* 147 */     getMappingData().load();
/* 148 */     onMappingDataLoaded();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerPackets() {
/* 155 */     callRegister((Rewriter<?>)getEntityRewriter());
/* 156 */     callRegister((Rewriter<?>)getItemRewriter());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMappingDataLoaded() {
/* 165 */     callOnMappingDataLoaded((Rewriter<?>)getEntityRewriter());
/* 166 */     callOnMappingDataLoaded((Rewriter<?>)getItemRewriter());
/*     */   }
/*     */   
/*     */   private void callRegister(Rewriter<?> rewriter) {
/* 170 */     if (rewriter != null) {
/* 171 */       rewriter.register();
/*     */     }
/*     */   }
/*     */   
/*     */   private void callOnMappingDataLoaded(Rewriter<?> rewriter) {
/* 176 */     if (rewriter != null) {
/* 177 */       rewriter.onMappingDataLoaded();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void addEntityTracker(UserConnection connection, EntityTracker tracker) {
/* 182 */     connection.addEntityTracker(getClass(), tracker);
/*     */   }
/*     */   
/*     */   protected PacketTypesProvider<CU, CM, SM, SU> createPacketTypesProvider() {
/* 186 */     return (PacketTypesProvider<CU, CM, SM, SU>)new SimplePacketTypesProvider(
/* 187 */         packetTypeMap((Class)this.unmappedClientboundPacketType), 
/* 188 */         packetTypeMap((Class)this.mappedClientboundPacketType), 
/* 189 */         packetTypeMap((Class)this.mappedServerboundPacketType), 
/* 190 */         packetTypeMap((Class)this.unmappedServerboundPacketType));
/*     */   }
/*     */ 
/*     */   
/*     */   protected PacketMappings createClientboundPacketMappings() {
/* 195 */     return PacketMappings.arrayMappings();
/*     */   }
/*     */   
/*     */   protected PacketMappings createServerboundPacketMappings() {
/* 199 */     return PacketMappings.arrayMappings();
/*     */   }
/*     */   
/*     */   private <P extends PacketType> Map<State, PacketTypeMap<P>> packetTypeMap(Class<P> packetTypeClass) {
/* 203 */     if (packetTypeClass != null) {
/* 204 */       Map<State, PacketTypeMap<P>> map = new EnumMap<>(State.class);
/* 205 */       map.put(State.PLAY, PacketTypeMap.of(packetTypeClass));
/* 206 */       return map;
/*     */     } 
/* 208 */     return Collections.emptyMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler, boolean override) {
/* 215 */     Preconditions.checkArgument((unmappedPacketId != -1), "Unmapped packet id cannot be -1");
/* 216 */     PacketMapping packetMapping = PacketMapping.of(mappedPacketId, handler);
/* 217 */     if (!override && this.serverboundMappings.hasMapping(state, unmappedPacketId)) {
/* 218 */       Via.getPlatform().getLogger().log(Level.WARNING, unmappedPacketId + " already registered! If this override is intentional, set override to true. Stacktrace: ", new Exception());
/*     */     }
/*     */     
/* 221 */     this.serverboundMappings.addMapping(state, unmappedPacketId, packetMapping);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelServerbound(State state, int unmappedPacketId) {
/* 226 */     registerServerbound(state, unmappedPacketId, unmappedPacketId, PacketWrapper::cancel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler, boolean override) {
/* 231 */     Preconditions.checkArgument((unmappedPacketId != -1), "Unmapped packet id cannot be -1");
/* 232 */     PacketMapping packetMapping = PacketMapping.of(mappedPacketId, handler);
/* 233 */     if (!override && this.clientboundMappings.hasMapping(state, unmappedPacketId)) {
/* 234 */       Via.getPlatform().getLogger().log(Level.WARNING, unmappedPacketId + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
/*     */     }
/*     */     
/* 237 */     this.clientboundMappings.addMapping(state, unmappedPacketId, packetMapping);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelClientbound(State state, int unmappedPacketId) {
/* 242 */     registerClientbound(state, unmappedPacketId, unmappedPacketId, PacketWrapper::cancel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerClientbound(CU packetType, PacketHandler handler) {
/* 249 */     PacketTypeMap<CM> mappedPacketTypes = (PacketTypeMap<CM>)this.packetTypesProvider.mappedClientboundPacketTypes().get(packetType.state());
/* 250 */     ClientboundPacketType clientboundPacketType = mappedPacketType(packetType, mappedPacketTypes, this.unmappedClientboundPacketType, this.mappedClientboundPacketType);
/* 251 */     registerClientbound(packetType, (CM)clientboundPacketType, handler);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerClientbound(CU packetType, CM mappedPacketType, PacketHandler handler, boolean override) {
/* 256 */     register(this.clientboundMappings, (PacketType)packetType, (PacketType)mappedPacketType, (Class)this.unmappedClientboundPacketType, (Class)this.mappedClientboundPacketType, handler, override);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelClientbound(CU packetType) {
/* 261 */     registerClientbound(packetType, (CM)null, PacketWrapper::cancel);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerServerbound(SU packetType, PacketHandler handler) {
/* 266 */     PacketTypeMap<SM> mappedPacketTypes = (PacketTypeMap<SM>)this.packetTypesProvider.mappedServerboundPacketTypes().get(packetType.state());
/* 267 */     ServerboundPacketType serverboundPacketType = mappedPacketType(packetType, mappedPacketTypes, this.unmappedServerboundPacketType, this.mappedServerboundPacketType);
/* 268 */     registerServerbound(packetType, (SM)serverboundPacketType, handler);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerServerbound(SU packetType, SM mappedPacketType, PacketHandler handler, boolean override) {
/* 273 */     register(this.serverboundMappings, (PacketType)packetType, (PacketType)mappedPacketType, (Class)this.unmappedServerboundPacketType, (Class)this.mappedServerboundPacketType, handler, override);
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelServerbound(SU packetType) {
/* 278 */     registerServerbound(packetType, (SM)null, PacketWrapper::cancel);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void register(PacketMappings packetMappings, PacketType packetType, PacketType mappedPacketType, Class<? extends PacketType> unmappedPacketClass, Class<? extends PacketType> mappedPacketClass, PacketHandler handler, boolean override) {
/* 284 */     checkPacketType(packetType, (unmappedPacketClass == null || unmappedPacketClass.isInstance(packetType)));
/* 285 */     if (mappedPacketType != null) {
/* 286 */       checkPacketType(mappedPacketType, (mappedPacketClass == null || mappedPacketClass.isInstance(mappedPacketType)));
/* 287 */       Preconditions.checkArgument((packetType.state() == mappedPacketType.state()), "Packet type state does not match mapped packet type state");
/*     */       
/* 289 */       Preconditions.checkArgument((packetType.direction() == mappedPacketType.direction()), "Packet type direction does not match mapped packet type state");
/*     */     } 
/*     */ 
/*     */     
/* 293 */     PacketMapping packetMapping = PacketMapping.of(mappedPacketType, handler);
/* 294 */     if (!override && packetMappings.hasMapping(packetType)) {
/* 295 */       Via.getPlatform().getLogger().log(Level.WARNING, packetType + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
/*     */     }
/*     */     
/* 298 */     packetMappings.addMapping(packetType, packetMapping);
/*     */   }
/*     */   
/*     */   private static <U extends PacketType, M extends PacketType> M mappedPacketType(U packetType, PacketTypeMap<M> mappedTypes, Class<U> unmappedPacketTypeClass, Class<M> mappedPacketTypeClass) {
/* 302 */     Preconditions.checkNotNull(packetType);
/* 303 */     checkPacketType((PacketType)packetType, (unmappedPacketTypeClass == null || unmappedPacketTypeClass.isInstance(packetType)));
/* 304 */     if (unmappedPacketTypeClass == mappedPacketTypeClass)
/*     */     {
/* 306 */       return (M)packetType;
/*     */     }
/*     */     
/* 309 */     Preconditions.checkNotNull(mappedTypes, "Mapped packet types not provided for state %s of type class %s", new Object[] { packetType.state(), mappedPacketTypeClass });
/* 310 */     PacketType packetType1 = (PacketType)mappedTypes.typeByName(packetType.getName());
/* 311 */     if (packetType1 != null) {
/* 312 */       return (M)packetType1;
/*     */     }
/* 314 */     throw new IllegalArgumentException("Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " could not be automatically mapped!");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRegisteredClientbound(State state, int unmappedPacketId) {
/* 319 */     return this.clientboundMappings.hasMapping(state, unmappedPacketId);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasRegisteredServerbound(State state, int unmappedPacketId) {
/* 324 */     return this.serverboundMappings.hasMapping(state, unmappedPacketId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
/* 329 */     PacketMappings mappings = (direction == Direction.CLIENTBOUND) ? this.clientboundMappings : this.serverboundMappings;
/* 330 */     int unmappedId = packetWrapper.getId();
/* 331 */     PacketMapping packetMapping = mappings.mappedPacket(state, unmappedId);
/* 332 */     if (packetMapping == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 337 */     packetMapping.applyType(packetWrapper);
/* 338 */     PacketHandler handler = packetMapping.handler();
/* 339 */     if (handler != null) {
/*     */       try {
/* 341 */         handler.handle(packetWrapper);
/* 342 */       } catch (CancelException e) {
/*     */         
/* 344 */         throw e;
/* 345 */       } catch (InformativeException e) {
/*     */         
/* 347 */         e.addSource(handler.getClass());
/* 348 */         throwRemapError(direction, state, unmappedId, packetWrapper.getId(), e);
/*     */         return;
/* 350 */       } catch (Exception e) {
/*     */         
/* 352 */         InformativeException ex = new InformativeException(e);
/* 353 */         ex.addSource(handler.getClass());
/* 354 */         throwRemapError(direction, state, unmappedId, packetWrapper.getId(), ex);
/*     */         
/*     */         return;
/*     */       } 
/* 358 */       if (packetWrapper.isCancelled()) {
/* 359 */         throw CancelException.generate();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void throwRemapError(Direction direction, State state, int unmappedPacketId, int mappedPacketId, InformativeException e) throws InformativeException {
/* 366 */     if (state != State.PLAY && direction == Direction.SERVERBOUND && !Via.getManager().debugHandler().enabled()) {
/* 367 */       e.setShouldBePrinted(false);
/* 368 */       throw e;
/*     */     } 
/*     */     
/* 371 */     PacketType packetType = (direction == Direction.CLIENTBOUND) ? (PacketType)unmappedClientboundPacketType(state, unmappedPacketId) : (PacketType)unmappedServerboundPacketType(state, unmappedPacketId);
/* 372 */     if (packetType != null) {
/* 373 */       Via.getPlatform().getLogger().warning("ERROR IN " + getClass().getSimpleName() + " IN REMAP OF " + packetType + " (" + toNiceHex(unmappedPacketId) + ")");
/*     */     } else {
/* 375 */       Via.getPlatform().getLogger().warning("ERROR IN " + getClass().getSimpleName() + " IN REMAP OF " + 
/* 376 */           toNiceHex(unmappedPacketId) + "->" + toNiceHex(mappedPacketId));
/*     */     } 
/* 378 */     throw e;
/*     */   }
/*     */   
/*     */   private CU unmappedClientboundPacketType(State state, int packetId) {
/* 382 */     PacketTypeMap<CU> map = (PacketTypeMap<CU>)this.packetTypesProvider.unmappedClientboundPacketTypes().get(state);
/* 383 */     return (map != null) ? (CU)map.typeById(packetId) : null;
/*     */   }
/*     */   
/*     */   private SU unmappedServerboundPacketType(State state, int packetId) {
/* 387 */     PacketTypeMap<SU> map = (PacketTypeMap<SU>)this.packetTypesProvider.unmappedServerboundPacketTypes().get(state);
/* 388 */     return (map != null) ? (SU)map.typeById(packetId) : null;
/*     */   }
/*     */   
/*     */   public static String toNiceHex(int id) {
/* 392 */     String hex = Integer.toHexString(id).toUpperCase();
/* 393 */     return ((hex.length() == 1) ? "0x0" : "0x") + hex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkPacketType(PacketType packetType, boolean isValid) {
/* 402 */     if (!isValid) {
/* 403 */       throw new IllegalArgumentException("Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " is taken from the wrong packet types class");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketTypesProvider<CU, CM, SM, SU> getPacketTypesProvider() {
/* 409 */     return this.packetTypesProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T get(Class<T> objectClass) {
/* 415 */     return (T)this.storedObjects.get(objectClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(Object object) {
/* 420 */     this.storedObjects.put(object.getClass(), object);
/*     */   }
/*     */   
/*     */   public PacketTypesProvider<CU, CM, SM, SU> packetTypesProvider() {
/* 424 */     return this.packetTypesProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 429 */     return "Protocol:" + getClass().getSimpleName();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\protocol\AbstractProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */