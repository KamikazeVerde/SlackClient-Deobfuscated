/*    */ package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.api.data.BackwardsMappings;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class BackwardsMappings
/*    */   extends BackwardsMappings
/*    */ {
/* 26 */   private final Map<String, String> attributeMappings = new HashMap<>();
/*    */   
/*    */   public BackwardsMappings() {
/* 29 */     super("1.16", "1.15", Protocol1_16To1_15_2.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadExtras(CompoundTag data) {
/* 34 */     super.loadExtras(data);
/* 35 */     for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)Protocol1_16To1_15_2.MAPPINGS.getAttributeMappings().entrySet()) {
/* 36 */       this.attributeMappings.put(entry.getValue(), entry.getKey());
/*    */     }
/*    */   }
/*    */   
/*    */   public Map<String, String> getAttributeMappings() {
/* 41 */     return this.attributeMappings;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_15_2to1_16\data\BackwardsMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */