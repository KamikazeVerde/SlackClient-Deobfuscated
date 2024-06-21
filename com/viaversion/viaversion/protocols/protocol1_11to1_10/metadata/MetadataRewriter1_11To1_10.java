/*     */ package com.viaversion.viaversion.protocols.protocol1_11to1_10.metadata;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_11Types;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
/*     */ import com.viaversion.viaversion.api.protocol.Protocol;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketType;
/*     */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*     */ import com.viaversion.viaversion.api.type.Type;
/*     */ import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
/*     */ import com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;
/*     */ import com.viaversion.viaversion.protocols.protocol1_11to1_10.storage.EntityTracker1_11;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
/*     */ import com.viaversion.viaversion.rewriter.EntityRewriter;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
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
/*     */ public class MetadataRewriter1_11To1_10
/*     */   extends EntityRewriter<ClientboundPackets1_9_3, Protocol1_11To1_10>
/*     */ {
/*     */   public MetadataRewriter1_11To1_10(Protocol1_11To1_10 protocol) {
/*  41 */     super((Protocol)protocol);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleMetadata(int entityId, EntityType type, Metadata metadata, List<Metadata> metadatas, UserConnection connection) {
/*  46 */     if (metadata.getValue() instanceof com.viaversion.viaversion.api.minecraft.item.DataItem)
/*     */     {
/*  48 */       EntityIdRewriter.toClientItem((Item)metadata.getValue());
/*     */     }
/*     */     
/*  51 */     if (type == null)
/*  52 */       return;  if (type.is((EntityType)Entity1_11Types.EntityType.ELDER_GUARDIAN) || type.is((EntityType)Entity1_11Types.EntityType.GUARDIAN)) {
/*  53 */       int oldid = metadata.id();
/*  54 */       if (oldid == 12) {
/*  55 */         boolean val = ((((Byte)metadata.getValue()).byteValue() & 0x2) == 2);
/*  56 */         metadata.setTypeAndValue((MetaType)MetaType1_9.Boolean, Boolean.valueOf(val));
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     if (type.isOrHasParent((EntityType)Entity1_11Types.EntityType.ABSTRACT_SKELETON)) {
/*  61 */       int oldid = metadata.id();
/*  62 */       if (oldid == 12) {
/*  63 */         metadatas.remove(metadata);
/*     */       }
/*  65 */       if (oldid == 13) {
/*  66 */         metadata.setId(12);
/*     */       }
/*     */     } 
/*     */     
/*  70 */     if (type.isOrHasParent((EntityType)Entity1_11Types.EntityType.ZOMBIE)) {
/*  71 */       if ((type == Entity1_11Types.EntityType.ZOMBIE || type == Entity1_11Types.EntityType.HUSK) && metadata.id() == 14) {
/*  72 */         metadatas.remove(metadata);
/*     */       }
/*  74 */       else if (metadata.id() == 15) {
/*  75 */         metadata.setId(14);
/*     */       }
/*  77 */       else if (metadata.id() == 14) {
/*  78 */         metadata.setId(15);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  84 */     if (type.isOrHasParent((EntityType)Entity1_11Types.EntityType.ABSTRACT_HORSE)) {
/*     */       
/*  86 */       int oldid = metadata.id();
/*  87 */       if (oldid == 14) {
/*  88 */         metadatas.remove(metadata);
/*     */       }
/*  90 */       if (oldid == 16) {
/*  91 */         metadata.setId(14);
/*     */       }
/*  93 */       if (oldid == 17) {
/*  94 */         metadata.setId(16);
/*     */       }
/*     */ 
/*     */       
/*  98 */       if (!type.is((EntityType)Entity1_11Types.EntityType.HORSE))
/*     */       {
/*     */ 
/*     */         
/* 102 */         if (metadata.id() == 15 || metadata.id() == 16) {
/* 103 */           metadatas.remove(metadata);
/*     */         }
/*     */       }
/* 106 */       if (type == Entity1_11Types.EntityType.DONKEY || type == Entity1_11Types.EntityType.MULE)
/*     */       {
/* 108 */         if (metadata.id() == 13) {
/* 109 */           if ((((Byte)metadata.getValue()).byteValue() & 0x8) == 8) {
/* 110 */             metadatas.add(new Metadata(15, (MetaType)MetaType1_9.Boolean, Boolean.valueOf(true)));
/*     */           } else {
/* 112 */             metadatas.add(new Metadata(15, (MetaType)MetaType1_9.Boolean, Boolean.valueOf(false)));
/*     */           } 
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 118 */     if (type.is((EntityType)Entity1_11Types.EntityType.ARMOR_STAND) && Via.getConfig().isHologramPatch()) {
/* 119 */       Metadata flags = metaByIndex(11, metadatas);
/* 120 */       Metadata customName = metaByIndex(2, metadatas);
/* 121 */       Metadata customNameVisible = metaByIndex(3, metadatas);
/* 122 */       if (metadata.id() == 0 && flags != null && customName != null && customNameVisible != null) {
/* 123 */         byte data = ((Byte)metadata.getValue()).byteValue();
/*     */         
/* 125 */         if ((data & 0x20) == 32 && (((Byte)flags.getValue()).byteValue() & 0x1) == 1 && 
/* 126 */           !((String)customName.getValue()).isEmpty() && ((Boolean)customNameVisible.getValue()).booleanValue()) {
/* 127 */           EntityTracker1_11 tracker = (EntityTracker1_11)tracker(connection);
/* 128 */           if (tracker.addHologram(entityId)) {
/*     */             
/*     */             try {
/* 131 */               PacketWrapper wrapper = PacketWrapper.create((PacketType)ClientboundPackets1_9_3.ENTITY_POSITION, null, connection);
/* 132 */               wrapper.write((Type)Type.VAR_INT, Integer.valueOf(entityId));
/* 133 */               wrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 134 */               wrapper.write((Type)Type.SHORT, Short.valueOf((short)(int)(128.0D * -Via.getConfig().getHologramYOffset() * 32.0D)));
/* 135 */               wrapper.write((Type)Type.SHORT, Short.valueOf((short)0));
/* 136 */               wrapper.write((Type)Type.BOOLEAN, Boolean.valueOf(true));
/*     */               
/* 138 */               wrapper.send(Protocol1_11To1_10.class);
/* 139 */             } catch (Exception e) {
/* 140 */               e.printStackTrace();
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType typeFromId(int type) {
/* 150 */     return (EntityType)Entity1_11Types.getTypeFromId(type, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityType objectTypeFromId(int type) {
/* 155 */     return (EntityType)Entity1_11Types.getTypeFromId(type, true);
/*     */   }
/*     */   
/*     */   public static Entity1_11Types.EntityType rewriteEntityType(int numType, List<Metadata> metadata) {
/* 159 */     Optional<Entity1_11Types.EntityType> optType = Entity1_11Types.EntityType.findById(numType);
/* 160 */     if (!optType.isPresent()) {
/* 161 */       Via.getManager().getPlatform().getLogger().severe("Error: could not find Entity type " + numType + " with metadata: " + metadata);
/* 162 */       return null;
/*     */     } 
/*     */     
/* 165 */     Entity1_11Types.EntityType type = optType.get();
/*     */     
/*     */     try {
/* 168 */       if (type.is((EntityType)Entity1_11Types.EntityType.GUARDIAN)) {
/*     */         
/* 170 */         Optional<Metadata> options = getById(metadata, 12);
/* 171 */         if (options.isPresent() && ((
/* 172 */           (Byte)((Metadata)options.get()).getValue()).byteValue() & 0x4) == 4) {
/* 173 */           return Entity1_11Types.EntityType.ELDER_GUARDIAN;
/*     */         }
/*     */       } 
/*     */       
/* 177 */       if (type.is((EntityType)Entity1_11Types.EntityType.SKELETON)) {
/*     */ 
/*     */         
/* 180 */         Optional<Metadata> options = getById(metadata, 12);
/* 181 */         if (options.isPresent()) {
/* 182 */           if (((Integer)((Metadata)options.get()).getValue()).intValue() == 1) {
/* 183 */             return Entity1_11Types.EntityType.WITHER_SKELETON;
/*     */           }
/* 185 */           if (((Integer)((Metadata)options.get()).getValue()).intValue() == 2) {
/* 186 */             return Entity1_11Types.EntityType.STRAY;
/*     */           }
/*     */         } 
/*     */       } 
/* 190 */       if (type.is((EntityType)Entity1_11Types.EntityType.ZOMBIE)) {
/*     */ 
/*     */         
/* 193 */         Optional<Metadata> options = getById(metadata, 13);
/* 194 */         if (options.isPresent()) {
/* 195 */           int value = ((Integer)((Metadata)options.get()).getValue()).intValue();
/* 196 */           if (value > 0 && value < 6) {
/* 197 */             metadata.add(new Metadata(16, (MetaType)MetaType1_9.VarInt, Integer.valueOf(value - 1)));
/* 198 */             return Entity1_11Types.EntityType.ZOMBIE_VILLAGER;
/*     */           } 
/* 200 */           if (value == 6) {
/* 201 */             return Entity1_11Types.EntityType.HUSK;
/*     */           }
/*     */         } 
/*     */       } 
/* 205 */       if (type.is((EntityType)Entity1_11Types.EntityType.HORSE)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 210 */         Optional<Metadata> options = getById(metadata, 14);
/* 211 */         if (options.isPresent()) {
/* 212 */           if (((Integer)((Metadata)options.get()).getValue()).intValue() == 0) {
/* 213 */             return Entity1_11Types.EntityType.HORSE;
/*     */           }
/* 215 */           if (((Integer)((Metadata)options.get()).getValue()).intValue() == 1) {
/* 216 */             return Entity1_11Types.EntityType.DONKEY;
/*     */           }
/* 218 */           if (((Integer)((Metadata)options.get()).getValue()).intValue() == 2) {
/* 219 */             return Entity1_11Types.EntityType.MULE;
/*     */           }
/* 221 */           if (((Integer)((Metadata)options.get()).getValue()).intValue() == 3) {
/* 222 */             return Entity1_11Types.EntityType.ZOMBIE_HORSE;
/*     */           }
/* 224 */           if (((Integer)((Metadata)options.get()).getValue()).intValue() == 4) {
/* 225 */             return Entity1_11Types.EntityType.SKELETON_HORSE;
/*     */           }
/*     */         } 
/*     */       } 
/* 229 */     } catch (Exception e) {
/* 230 */       if (!Via.getConfig().isSuppressMetadataErrors() || Via.getManager().isDebug()) {
/* 231 */         Via.getPlatform().getLogger().warning("An error occurred with entity type rewriter");
/* 232 */         Via.getPlatform().getLogger().warning("Metadata: " + metadata);
/* 233 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 237 */     return type;
/*     */   }
/*     */   
/*     */   public static Optional<Metadata> getById(List<Metadata> metadatas, int id) {
/* 241 */     for (Metadata metadata : metadatas) {
/* 242 */       if (metadata.id() == id) return Optional.of(metadata); 
/*     */     } 
/* 244 */     return Optional.empty();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_11to1_10\metadata\MetadataRewriter1_11To1_10.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */