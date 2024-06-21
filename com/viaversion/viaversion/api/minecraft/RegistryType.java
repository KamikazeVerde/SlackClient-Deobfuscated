/*    */ package com.viaversion.viaversion.api.minecraft;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum RegistryType
/*    */ {
/* 31 */   BLOCK("block"),
/* 32 */   ITEM("item"),
/* 33 */   FLUID("fluid"),
/* 34 */   ENTITY("entity_type"),
/* 35 */   GAME_EVENT("game_event"); private static final Map<String, RegistryType> MAP;
/*    */   static {
/* 37 */     MAP = new HashMap<>();
/* 38 */     VALUES = values();
/*    */ 
/*    */     
/* 41 */     for (RegistryType type : getValues())
/* 42 */       MAP.put(type.resourceLocation, type); 
/*    */   }
/*    */   private static final RegistryType[] VALUES; private final String resourceLocation;
/*    */   
/*    */   public static RegistryType[] getValues() {
/* 47 */     return VALUES;
/*    */   }
/*    */   
/*    */   public static RegistryType getByKey(String resourceKey) {
/* 51 */     return MAP.get(resourceKey);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   RegistryType(String resourceLocation) {
/* 57 */     this.resourceLocation = resourceLocation;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public String getResourceLocation() {
/* 62 */     return this.resourceLocation;
/*    */   }
/*    */   
/*    */   public String resourceLocation() {
/* 66 */     return this.resourceLocation;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\RegistryType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */