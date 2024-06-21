/*    */ package com.viaversion.viaversion.protocols.protocol1_15to1_14_4;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.api.data.MappingData;
/*    */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*    */ import com.viaversion.viaversion.api.data.entity.EntityTracker;
/*    */ import com.viaversion.viaversion.api.minecraft.RegistryType;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.Entity1_15Types;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.protocol.AbstractProtocol;
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
/*    */ import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
/*    */ import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
/*    */ import com.viaversion.viaversion.api.rewriter.EntityRewriter;
/*    */ import com.viaversion.viaversion.api.rewriter.ItemRewriter;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import com.viaversion.viaversion.data.entity.EntityTrackerBase;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.metadata.MetadataRewriter1_15To1_14_4;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.EntityPackets;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.InventoryPackets;
/*    */ import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.WorldPackets;
/*    */ import com.viaversion.viaversion.rewriter.SoundRewriter;
/*    */ import com.viaversion.viaversion.rewriter.StatisticsRewriter;
/*    */ import com.viaversion.viaversion.rewriter.TagRewriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Protocol1_15To1_14_4
/*    */   extends AbstractProtocol<ClientboundPackets1_14, ClientboundPackets1_15, ServerboundPackets1_14, ServerboundPackets1_14>
/*    */ {
/* 40 */   public static final MappingData MAPPINGS = (MappingData)new MappingDataBase("1.14", "1.15");
/* 41 */   private final MetadataRewriter1_15To1_14_4 metadataRewriter = new MetadataRewriter1_15To1_14_4(this);
/* 42 */   private final InventoryPackets itemRewriter = new InventoryPackets(this);
/*    */   private TagRewriter<ClientboundPackets1_14> tagRewriter;
/*    */   
/*    */   public Protocol1_15To1_14_4() {
/* 46 */     super(ClientboundPackets1_14.class, ClientboundPackets1_15.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void registerPackets() {
/* 51 */     this.metadataRewriter.register();
/* 52 */     this.itemRewriter.register();
/*    */     
/* 54 */     EntityPackets.register(this);
/* 55 */     WorldPackets.register(this);
/*    */     
/* 57 */     SoundRewriter<ClientboundPackets1_14> soundRewriter = new SoundRewriter((Protocol)this);
/* 58 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_14.ENTITY_SOUND);
/* 59 */     soundRewriter.registerSound((ClientboundPacketType)ClientboundPackets1_14.SOUND);
/*    */     
/* 61 */     (new StatisticsRewriter((Protocol)this)).register((ClientboundPacketType)ClientboundPackets1_14.STATISTICS);
/*    */     
/* 63 */     registerServerbound((ServerboundPacketType)ServerboundPackets1_14.EDIT_BOOK, wrapper -> this.itemRewriter.handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
/*    */     
/* 65 */     this.tagRewriter = new TagRewriter((Protocol)this);
/* 66 */     this.tagRewriter.register((ClientboundPacketType)ClientboundPackets1_14.TAGS, RegistryType.ENTITY);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onMappingDataLoaded() {
/* 71 */     int[] shulkerBoxes = new int[17];
/* 72 */     int shulkerBoxOffset = 501;
/* 73 */     for (int i = 0; i < 17; i++) {
/* 74 */       shulkerBoxes[i] = shulkerBoxOffset + i;
/*    */     }
/* 76 */     this.tagRewriter.addTag(RegistryType.BLOCK, "minecraft:shulker_boxes", shulkerBoxes);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init(UserConnection connection) {
/* 81 */     addEntityTracker(connection, (EntityTracker)new EntityTrackerBase(connection, (EntityType)Entity1_15Types.PLAYER));
/*    */   }
/*    */ 
/*    */   
/*    */   public MappingData getMappingData() {
/* 86 */     return MAPPINGS;
/*    */   }
/*    */ 
/*    */   
/*    */   public MetadataRewriter1_15To1_14_4 getEntityRewriter() {
/* 91 */     return this.metadataRewriter;
/*    */   }
/*    */ 
/*    */   
/*    */   public InventoryPackets getItemRewriter() {
/* 96 */     return this.itemRewriter;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_15to1_14_4\Protocol1_15To1_14_4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */