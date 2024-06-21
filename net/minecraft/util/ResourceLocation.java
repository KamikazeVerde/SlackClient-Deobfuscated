/*    */ package net.minecraft.util;
/*    */ 
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class ResourceLocation
/*    */ {
/*    */   protected final String resourceDomain;
/*    */   protected final String resourcePath;
/*    */   
/*    */   protected ResourceLocation(int p_i45928_1_, String... resourceName) {
/* 12 */     this.resourceDomain = StringUtils.isEmpty(resourceName[0]) ? "minecraft" : resourceName[0].toLowerCase();
/* 13 */     this.resourcePath = resourceName[1];
/* 14 */     Validate.notNull(this.resourcePath);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation(String resourceName) {
/* 19 */     this(0, splitObjectName(resourceName));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation(String resourceDomainIn, String resourcePathIn) {
/* 24 */     this(0, new String[] { resourceDomainIn, resourcePathIn });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static String[] splitObjectName(String toSplit) {
/* 35 */     String[] astring = { null, toSplit };
/* 36 */     int i = toSplit.indexOf(':');
/*    */     
/* 38 */     if (i >= 0) {
/*    */       
/* 40 */       astring[1] = toSplit.substring(i + 1, toSplit.length());
/*    */       
/* 42 */       if (i > 1)
/*    */       {
/* 44 */         astring[0] = toSplit.substring(0, i);
/*    */       }
/*    */     } 
/*    */     
/* 48 */     return astring;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getResourcePath() {
/* 53 */     return this.resourcePath;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getResourceDomain() {
/* 58 */     return this.resourceDomain;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return this.resourceDomain + ':' + this.resourcePath;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 68 */     if (this == p_equals_1_)
/*    */     {
/* 70 */       return true;
/*    */     }
/* 72 */     if (!(p_equals_1_ instanceof ResourceLocation))
/*    */     {
/* 74 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 78 */     ResourceLocation resourcelocation = (ResourceLocation)p_equals_1_;
/* 79 */     return (this.resourceDomain.equals(resourcelocation.resourceDomain) && this.resourcePath.equals(resourcelocation.resourcePath));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 85 */     return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\ResourceLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */