/*    */ package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.data;
/*    */ 
/*    */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*    */ import com.viaversion.viaversion.api.data.MappingDataLoader;
/*    */ import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MappingData
/*    */   extends MappingDataBase
/*    */ {
/* 34 */   private final Int2ObjectMap<CompoundTag> defaultChatTypes = (Int2ObjectMap<CompoundTag>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   public MappingData() {
/* 37 */     super("1.18", "1.19");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadExtras(CompoundTag daata) {
/*    */     try {
/* 43 */       ListTag chatTypes = (ListTag)BinaryTagIO.readInputStream(MappingDataLoader.getResource("chat-types-1.19.nbt")).get("values");
/* 44 */       for (Tag chatType : chatTypes) {
/* 45 */         CompoundTag chatTypeCompound = (CompoundTag)chatType;
/* 46 */         NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
/* 47 */         this.defaultChatTypes.put(idTag.asInt(), chatTypeCompound);
/*    */       } 
/* 49 */     } catch (IOException e) {
/* 50 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public CompoundTag chatType(int id) {
/* 55 */     return (CompoundTag)this.defaultChatTypes.get(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19to1_18_2\data\MappingData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */