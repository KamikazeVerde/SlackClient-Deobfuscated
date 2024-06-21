/*    */ package javax.vecmath;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class Point4i
/*    */   extends Tuple4i
/*    */   implements Serializable
/*    */ {
/*    */   static final long serialVersionUID = 620124780244617983L;
/*    */   
/*    */   public Point4i(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 56 */     super(paramInt1, paramInt2, paramInt3, paramInt4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Point4i(int[] paramArrayOfint) {
/* 65 */     super(paramArrayOfint);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Point4i(Tuple4i paramTuple4i) {
/* 75 */     super(paramTuple4i);
/*    */   }
/*    */   
/*    */   public Point4i() {}
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\Point4i.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */