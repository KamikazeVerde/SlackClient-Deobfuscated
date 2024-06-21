/*    */ package org.yaml.snakeyaml.nodes;
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
/*    */ public class AnchorNode
/*    */   extends Node
/*    */ {
/*    */   private final Node realNode;
/*    */   
/*    */   public AnchorNode(Node realNode) {
/* 29 */     super(realNode.getTag(), realNode.getStartMark(), realNode.getEndMark());
/* 30 */     this.realNode = realNode;
/*    */   }
/*    */ 
/*    */   
/*    */   public NodeId getNodeId() {
/* 35 */     return NodeId.anchor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Node getRealNode() {
/* 44 */     return this.realNode;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\nodes\AnchorNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */