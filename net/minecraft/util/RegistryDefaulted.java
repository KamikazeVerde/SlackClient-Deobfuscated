/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistryDefaulted<K, V>
/*    */   extends RegistrySimple<K, V>
/*    */ {
/*    */   private final V defaultObject;
/*    */   
/*    */   public RegistryDefaulted(V defaultObjectIn) {
/* 12 */     this.defaultObject = defaultObjectIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public V getObject(K name) {
/* 17 */     V v = super.getObject(name);
/* 18 */     return (v == null) ? this.defaultObject : v;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\RegistryDefaulted.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */