/*    */ package org.yaml.snakeyaml.nodes;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.yaml.snakeyaml.DumperOptions;
/*    */ import org.yaml.snakeyaml.error.Mark;
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
/*    */ public class SequenceNode
/*    */   extends CollectionNode<Node>
/*    */ {
/*    */   private final List<Node> value;
/*    */   
/*    */   public SequenceNode(Tag tag, boolean resolved, List<Node> value, Mark startMark, Mark endMark, DumperOptions.FlowStyle flowStyle) {
/* 32 */     super(tag, startMark, endMark, flowStyle);
/* 33 */     if (value == null) {
/* 34 */       throw new NullPointerException("value in a Node is required.");
/*    */     }
/* 36 */     this.value = value;
/* 37 */     this.resolved = resolved;
/*    */   }
/*    */   
/*    */   public SequenceNode(Tag tag, List<Node> value, DumperOptions.FlowStyle flowStyle) {
/* 41 */     this(tag, true, value, (Mark)null, (Mark)null, flowStyle);
/*    */   }
/*    */ 
/*    */   
/*    */   public NodeId getNodeId() {
/* 46 */     return NodeId.sequence;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Node> getValue() {
/* 55 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setListType(Class<? extends Object> listType) {
/* 59 */     for (Node node : this.value) {
/* 60 */       node.setType(listType);
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString() {
/* 65 */     return "<" + getClass().getName() + " (tag=" + getTag() + ", value=" + getValue() + ")>";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\nodes\SequenceNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */