/*    */ package org.yaml.snakeyaml.inspector;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ public final class TrustedTagInspector
/*    */   implements TagInspector
/*    */ {
/*    */   public boolean isGlobalTagAllowed(Tag tag) {
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\inspector\TrustedTagInspector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */