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
/*    */ public final class UnTrustedTagInspector
/*    */   implements TagInspector
/*    */ {
/*    */   public boolean isGlobalTagAllowed(Tag tag) {
/* 32 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\inspector\UnTrustedTagInspector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */