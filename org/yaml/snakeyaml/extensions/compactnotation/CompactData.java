/*    */ package org.yaml.snakeyaml.extensions.compactnotation;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
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
/*    */ public class CompactData
/*    */ {
/*    */   private final String prefix;
/* 28 */   private final List<String> arguments = new ArrayList<>();
/* 29 */   private final Map<String, String> properties = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CompactData(String prefix) {
/* 37 */     this.prefix = prefix;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPrefix() {
/* 46 */     return this.prefix;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, String> getProperties() {
/* 55 */     return this.properties;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getArguments() {
/* 64 */     return this.arguments;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 74 */     return "CompactData: " + this.prefix + " " + this.properties;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\extensions\compactnotation\CompactData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */