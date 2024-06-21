/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RegistryNamespaced<K, V>
/*    */   extends RegistrySimple<K, V> implements IObjectIntIterable<V> {
/* 10 */   protected final ObjectIntIdentityMap<V> underlyingIntegerMap = new ObjectIntIdentityMap<>();
/*    */   
/*    */   protected final Map<V, K> inverseObjectRegistry;
/*    */   
/*    */   public RegistryNamespaced() {
/* 15 */     this.inverseObjectRegistry = (Map<V, K>)((BiMap)this.registryObjects).inverse();
/*    */   }
/*    */ 
/*    */   
/*    */   public void register(int id, K p_177775_2_, V p_177775_3_) {
/* 20 */     this.underlyingIntegerMap.put(p_177775_3_, id);
/* 21 */     putObject(p_177775_2_, p_177775_3_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Map<K, V> createUnderlyingMap() {
/* 26 */     return (Map<K, V>)HashBiMap.create();
/*    */   }
/*    */ 
/*    */   
/*    */   public V getObject(K name) {
/* 31 */     return super.getObject(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public K getNameForObject(V p_177774_1_) {
/* 39 */     return this.inverseObjectRegistry.get(p_177774_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean containsKey(K p_148741_1_) {
/* 47 */     return super.containsKey(p_148741_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIDForObject(V p_148757_1_) {
/* 55 */     return this.underlyingIntegerMap.get(p_148757_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public V getObjectById(int id) {
/* 65 */     return this.underlyingIntegerMap.getByValue(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<V> iterator() {
/* 70 */     return this.underlyingIntegerMap.iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\RegistryNamespaced.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */