/*    */ package org.yaml.snakeyaml.inspector;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.yaml.snakeyaml.nodes.Tag;
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
/*    */ public final class TrustedPrefixesTagInspector
/*    */   implements TagInspector
/*    */ {
/*    */   private final List<String> trustedList;
/*    */   
/*    */   public TrustedPrefixesTagInspector(List<String> trustedList) {
/* 33 */     this.trustedList = trustedList;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isGlobalTagAllowed(Tag tag) {
/* 44 */     for (String trusted : this.trustedList) {
/* 45 */       if (tag.getClassName().startsWith(trusted)) {
/* 46 */         return true;
/*    */       }
/*    */     } 
/* 49 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\inspector\TrustedPrefixesTagInspector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */