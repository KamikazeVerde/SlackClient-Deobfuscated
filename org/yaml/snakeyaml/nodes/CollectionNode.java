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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CollectionNode<T>
/*    */   extends Node
/*    */ {
/*    */   private DumperOptions.FlowStyle flowStyle;
/*    */   
/*    */   public CollectionNode(Tag tag, Mark startMark, Mark endMark, DumperOptions.FlowStyle flowStyle) {
/* 37 */     super(tag, startMark, endMark);
/* 38 */     setFlowStyle(flowStyle);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract List<T> getValue();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DumperOptions.FlowStyle getFlowStyle() {
/* 54 */     return this.flowStyle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFlowStyle(DumperOptions.FlowStyle flowStyle) {
/* 63 */     if (flowStyle == null) {
/* 64 */       throw new NullPointerException("Flow style must be provided.");
/*    */     }
/* 66 */     this.flowStyle = flowStyle;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEndMark(Mark endMark) {
/* 75 */     this.endMark = endMark;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\nodes\CollectionNode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */