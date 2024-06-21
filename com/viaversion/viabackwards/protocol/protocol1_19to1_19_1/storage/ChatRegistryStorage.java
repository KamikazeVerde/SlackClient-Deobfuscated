/*    */ package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
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
/*    */ public abstract class ChatRegistryStorage
/*    */   implements StorableObject
/*    */ {
/* 29 */   private final Int2ObjectMap<CompoundTag> chatTypes = (Int2ObjectMap<CompoundTag>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   public CompoundTag chatType(int id) {
/* 32 */     return this.chatTypes.isEmpty() ? Protocol1_19To1_18_2.MAPPINGS.chatType(id) : (CompoundTag)this.chatTypes.get(id);
/*    */   }
/*    */   
/*    */   public void addChatType(int id, CompoundTag chatType) {
/* 36 */     this.chatTypes.put(id, chatType);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 40 */     this.chatTypes.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean clearOnServerSwitch() {
/* 45 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_19to1_19_1\storage\ChatRegistryStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */