/*     */ package com.viaversion.viaversion.rewriter;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import java.util.HashMap;
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
/*     */ public class CommandRewriter<C extends ClientboundPacketType>
/*     */ {
/*     */   protected final Protocol<C, ?, ?, ?> protocol;
/*  33 */   protected final Map<String, CommandArgumentConsumer> parserHandlers = new HashMap<>();
/*     */   
/*     */   public CommandRewriter(Protocol<C, ?, ?, ?> protocol) {
/*  36 */     this.protocol = protocol;
/*     */ 
/*     */     
/*  39 */     this.parserHandlers.put("brigadier:double", wrapper -> {
/*     */           byte propertyFlags = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue(); if ((propertyFlags & 0x1) != 0)
/*     */             wrapper.passthrough((Type)Type.DOUBLE);  if ((propertyFlags & 0x2) != 0)
/*     */             wrapper.passthrough((Type)Type.DOUBLE); 
/*     */         });
/*  44 */     this.parserHandlers.put("brigadier:float", wrapper -> {
/*     */           byte propertyFlags = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue(); if ((propertyFlags & 0x1) != 0)
/*     */             wrapper.passthrough((Type)Type.FLOAT);  if ((propertyFlags & 0x2) != 0)
/*     */             wrapper.passthrough((Type)Type.FLOAT); 
/*     */         });
/*  49 */     this.parserHandlers.put("brigadier:integer", wrapper -> {
/*     */           byte propertyFlags = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue(); if ((propertyFlags & 0x1) != 0)
/*     */             wrapper.passthrough((Type)Type.INT);  if ((propertyFlags & 0x2) != 0)
/*     */             wrapper.passthrough((Type)Type.INT); 
/*     */         });
/*  54 */     this.parserHandlers.put("brigadier:long", wrapper -> {
/*     */           byte propertyFlags = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue(); if ((propertyFlags & 0x1) != 0)
/*     */             wrapper.passthrough((Type)Type.LONG);  if ((propertyFlags & 0x2) != 0)
/*     */             wrapper.passthrough((Type)Type.LONG); 
/*     */         });
/*  59 */     this.parserHandlers.put("brigadier:string", wrapper -> (Integer)wrapper.passthrough((Type)Type.VAR_INT));
/*  60 */     this.parserHandlers.put("minecraft:entity", wrapper -> (Byte)wrapper.passthrough((Type)Type.BYTE));
/*  61 */     this.parserHandlers.put("minecraft:score_holder", wrapper -> (Byte)wrapper.passthrough((Type)Type.BYTE));
/*  62 */     this.parserHandlers.put("minecraft:resource", wrapper -> (String)wrapper.passthrough(Type.STRING));
/*  63 */     this.parserHandlers.put("minecraft:resource_or_tag", wrapper -> (String)wrapper.passthrough(Type.STRING));
/*  64 */     this.parserHandlers.put("minecraft:resource_or_tag_key", wrapper -> (String)wrapper.passthrough(Type.STRING));
/*  65 */     this.parserHandlers.put("minecraft:resource_key", wrapper -> (String)wrapper.passthrough(Type.STRING));
/*     */   }
/*     */   
/*     */   public void registerDeclareCommands(C packetType) {
/*  69 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int i = 0; i < size; i++) {
/*     */             byte flags = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue();
/*     */             wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */             if ((flags & 0x8) != 0) {
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */             }
/*     */             byte nodeType = (byte)(flags & 0x3);
/*     */             if (nodeType == 1 || nodeType == 2) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */             }
/*     */             if (nodeType == 2) {
/*     */               String argumentType = (String)wrapper.read(Type.STRING);
/*     */               String newArgumentType = handleArgumentType(argumentType);
/*     */               if (newArgumentType != null) {
/*     */                 wrapper.write(Type.STRING, newArgumentType);
/*     */               }
/*     */               handleArgument(wrapper, argumentType);
/*     */             } 
/*     */             if ((flags & 0x10) != 0) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */             }
/*     */           } 
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerDeclareCommands1_19(C packetType) {
/* 104 */     this.protocol.registerClientbound((ClientboundPacketType)packetType, wrapper -> {
/*     */           int size = ((Integer)wrapper.passthrough((Type)Type.VAR_INT)).intValue();
/*     */           for (int i = 0; i < size; i++) {
/*     */             byte flags = ((Byte)wrapper.passthrough((Type)Type.BYTE)).byteValue();
/*     */             wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
/*     */             if ((flags & 0x8) != 0) {
/*     */               wrapper.passthrough((Type)Type.VAR_INT);
/*     */             }
/*     */             byte nodeType = (byte)(flags & 0x3);
/*     */             if (nodeType == 1 || nodeType == 2) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */             }
/*     */             if (nodeType == 2) {
/*     */               int argumentTypeId = ((Integer)wrapper.read((Type)Type.VAR_INT)).intValue();
/*     */               String argumentType = argumentType(argumentTypeId);
/*     */               String newArgumentType = handleArgumentType(argumentType);
/*     */               Preconditions.checkNotNull(newArgumentType, "No mapping for argument type %s", new Object[] { argumentType });
/*     */               wrapper.write((Type)Type.VAR_INT, Integer.valueOf(mappedArgumentTypeId(newArgumentType)));
/*     */               handleArgument(wrapper, argumentType);
/*     */             } 
/*     */             if ((flags & 0x10) != 0) {
/*     */               wrapper.passthrough(Type.STRING);
/*     */             }
/*     */           } 
/*     */           wrapper.passthrough((Type)Type.VAR_INT);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
/* 139 */     CommandArgumentConsumer handler = this.parserHandlers.get(argumentType);
/* 140 */     if (handler != null) {
/* 141 */       handler.accept(wrapper);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String handleArgumentType(String argumentType) {
/* 152 */     if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getArgumentTypeMappings() != null) {
/* 153 */       return this.protocol.getMappingData().getArgumentTypeMappings().mappedIdentifier(argumentType);
/*     */     }
/* 155 */     return argumentType;
/*     */   }
/*     */   
/*     */   protected String argumentType(int argumentTypeId) {
/* 159 */     return this.protocol.getMappingData().getArgumentTypeMappings().identifier(argumentTypeId);
/*     */   }
/*     */   
/*     */   protected int mappedArgumentTypeId(String mappedArgumentType) {
/* 163 */     return this.protocol.getMappingData().getArgumentTypeMappings().mappedId(mappedArgumentType);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface CommandArgumentConsumer {
/*     */     void accept(PacketWrapper param1PacketWrapper) throws Exception;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\CommandRewriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */