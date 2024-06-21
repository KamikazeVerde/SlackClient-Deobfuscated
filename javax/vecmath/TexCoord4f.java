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
/*    */ 
/*    */ public class TexCoord4f
/*    */   extends Tuple4f
/*    */   implements Serializable
/*    */ {
/*    */   static final long serialVersionUID = -3517736544731446513L;
/*    */   
/*    */   public TexCoord4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 57 */     super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord4f(float[] paramArrayOffloat) {
/* 67 */     super(paramArrayOffloat);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord4f(TexCoord4f paramTexCoord4f) {
/* 77 */     super(paramTexCoord4f);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord4f(Tuple4f paramTuple4f) {
/* 87 */     super(paramTuple4f);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord4f(Tuple4d paramTuple4d) {
/* 97 */     super(paramTuple4d);
/*    */   }
/*    */   
/*    */   public TexCoord4f() {}
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\TexCoord4f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */