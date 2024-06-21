/*     */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers;
/*     */ 
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.BannerHandler;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.BedHandler;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.PistonHandler;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.SkullHandler;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.SpawnerHandler;
/*     */ import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.minecraft.Position;
/*     */ import com.viaversion.viaversion.api.platform.providers.Provider;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
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
/*     */ public class BackwardsBlockEntityProvider
/*     */   implements Provider
/*     */ {
/*  39 */   private final Map<String, BackwardsBlockEntityHandler> handlers = new HashMap<>();
/*     */   
/*     */   public BackwardsBlockEntityProvider() {
/*  42 */     this.handlers.put("minecraft:flower_pot", new FlowerPotHandler());
/*  43 */     this.handlers.put("minecraft:bed", new BedHandler());
/*  44 */     this.handlers.put("minecraft:banner", new BannerHandler());
/*  45 */     this.handlers.put("minecraft:skull", new SkullHandler());
/*  46 */     this.handlers.put("minecraft:mob_spawner", new SpawnerHandler());
/*  47 */     this.handlers.put("minecraft:piston", new PistonHandler());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHandled(String key) {
/*  57 */     return this.handlers.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompoundTag transform(UserConnection user, Position position, CompoundTag tag) throws Exception {
/*  68 */     Tag idTag = tag.get("id");
/*  69 */     if (!(idTag instanceof StringTag)) {
/*  70 */       return tag;
/*     */     }
/*     */     
/*  73 */     String id = (String)idTag.getValue();
/*  74 */     BackwardsBlockEntityHandler handler = this.handlers.get(id);
/*  75 */     if (handler == null) {
/*  76 */       return tag;
/*     */     }
/*     */     
/*  79 */     BackwardsBlockStorage storage = (BackwardsBlockStorage)user.get(BackwardsBlockStorage.class);
/*  80 */     Integer blockId = storage.get(position);
/*  81 */     if (blockId == null) {
/*  82 */       return tag;
/*     */     }
/*     */     
/*  85 */     return handler.transform(user, blockId.intValue(), tag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompoundTag transform(UserConnection user, Position position, String id) throws Exception {
/*  96 */     CompoundTag tag = new CompoundTag();
/*  97 */     tag.put("id", (Tag)new StringTag(id));
/*  98 */     tag.put("x", (Tag)new IntTag(Math.toIntExact(position.x())));
/*  99 */     tag.put("y", (Tag)new IntTag(Math.toIntExact(position.y())));
/* 100 */     tag.put("z", (Tag)new IntTag(Math.toIntExact(position.z())));
/*     */     
/* 102 */     return transform(user, position, tag);
/*     */   }
/*     */   
/*     */   public static interface BackwardsBlockEntityHandler {
/*     */     CompoundTag transform(UserConnection param1UserConnection, int param1Int, CompoundTag param1CompoundTag);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\providers\BackwardsBlockEntityProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */