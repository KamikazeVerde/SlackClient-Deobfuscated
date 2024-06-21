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
/*    */ public class TexCoord3f
/*    */   extends Tuple3f
/*    */   implements Serializable
/*    */ {
/*    */   static final long serialVersionUID = -3517736544731446513L;
/*    */   
/*    */   public TexCoord3f(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 55 */     super(paramFloat1, paramFloat2, paramFloat3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord3f(float[] paramArrayOffloat) {
/* 65 */     super(paramArrayOffloat);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord3f(TexCoord3f paramTexCoord3f) {
/* 75 */     super(paramTexCoord3f);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord3f(Tuple3f paramTuple3f) {
/* 85 */     super(paramTuple3f);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord3f(Tuple3d paramTuple3d) {
/* 95 */     super(paramTuple3d);
/*    */   }
/*    */   
/*    */   public TexCoord3f() {}
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\javax\vecmath\TexCoord3f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */