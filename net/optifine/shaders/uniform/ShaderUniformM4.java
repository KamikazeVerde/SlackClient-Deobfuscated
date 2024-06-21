/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniformM4
/*    */   extends ShaderUniformBase
/*    */ {
/*    */   private boolean transpose;
/*    */   private FloatBuffer matrix;
/*    */   
/*    */   public ShaderUniformM4(String name) {
/* 13 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(boolean transpose, FloatBuffer matrix) {
/* 18 */     this.transpose = transpose;
/* 19 */     this.matrix = matrix;
/* 20 */     int i = getLocation();
/*    */     
/* 22 */     if (i >= 0) {
/*    */       
/* 24 */       ARBShaderObjects.glUniformMatrix4ARB(i, transpose, matrix);
/* 25 */       checkGLError();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValue(int row, int col) {
/* 31 */     if (this.matrix == null)
/*    */     {
/* 33 */       return 0.0F;
/*    */     }
/*    */ 
/*    */     
/* 37 */     int i = this.transpose ? (col * 4 + row) : (row * 4 + col);
/* 38 */     float f = this.matrix.get(i);
/* 39 */     return f;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onProgramSet(int program) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void resetValue() {
/* 49 */     this.matrix = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shader\\uniform\ShaderUniformM4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */