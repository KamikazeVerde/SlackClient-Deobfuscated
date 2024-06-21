/*    */ package com.viaversion.viaversion.data.entity;
/*    */ 
/*    */ import com.viaversion.viaversion.api.data.entity.StoredEntityData;
/*    */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
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
/*    */ public final class StoredEntityDataImpl
/*    */   implements StoredEntityData
/*    */ {
/* 27 */   private final Map<Class<?>, Object> storedObjects = new ConcurrentHashMap<>();
/*    */   private final EntityType type;
/*    */   
/*    */   public StoredEntityDataImpl(EntityType type) {
/* 31 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType type() {
/* 36 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> T get(Class<T> objectClass) {
/* 42 */     return (T)this.storedObjects.get(objectClass);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> T remove(Class<T> objectClass) {
/* 48 */     return (T)this.storedObjects.remove(objectClass);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean has(Class<?> objectClass) {
/* 53 */     return this.storedObjects.containsKey(objectClass);
/*    */   }
/*    */ 
/*    */   
/*    */   public void put(Object object) {
/* 58 */     this.storedObjects.put(object.getClass(), object);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\data\entity\StoredEntityDataImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */