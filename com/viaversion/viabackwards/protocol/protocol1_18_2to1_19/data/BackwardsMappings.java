/*    */ package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*    */ import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
/*    */ import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
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
/*    */ public final class BackwardsMappings
/*    */   extends BackwardsMappings
/*    */ {
/* 34 */   private final Int2ObjectMap<CompoundTag> defaultChatTypes = (Int2ObjectMap<CompoundTag>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   public BackwardsMappings() {
/* 37 */     super("1.19", "1.18", Protocol1_19To1_18_2.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadExtras(CompoundTag data) {
/* 42 */     super.loadExtras(data);
/*    */     
/*    */     try {
/* 45 */       ListTag chatTypes = (ListTag)BinaryTagIO.readInputStream(VBMappingDataLoader.getResource("chat-types-1.19.1.nbt")).get("values");
/* 46 */       for (Tag chatType : chatTypes) {
/* 47 */         CompoundTag chatTypeCompound = (CompoundTag)chatType;
/* 48 */         NumberTag idTag = (NumberTag)chatTypeCompound.get("id");
/* 49 */         this.defaultChatTypes.put(idTag.asInt(), chatTypeCompound);
/*    */       } 
/* 51 */     } catch (IOException e) {
/* 52 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public CompoundTag chatType(int id) {
/* 57 */     return (CompoundTag)this.defaultChatTypes.get(id);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_18_2to1_19\data\BackwardsMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */