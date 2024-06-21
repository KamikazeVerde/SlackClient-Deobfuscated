/*     */ package net.optifine.shaders.uniform;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.ARBShaderObjects;
/*     */ 
/*     */ public abstract class ShaderUniformBase
/*     */ {
/*     */   private String name;
/*  10 */   private int program = 0;
/*  11 */   private int[] locations = new int[] { -1 };
/*     */   
/*     */   private static final int LOCATION_UNDEFINED = -1;
/*     */   private static final int LOCATION_UNKNOWN = -2147483648;
/*     */   
/*     */   public ShaderUniformBase(String name) {
/*  17 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProgram(int program) {
/*  22 */     if (this.program != program) {
/*     */       
/*  24 */       this.program = program;
/*  25 */       expandLocations();
/*  26 */       onProgramSet(program);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void expandLocations() {
/*  32 */     if (this.program >= this.locations.length) {
/*     */       
/*  34 */       int[] aint = new int[this.program * 2];
/*  35 */       Arrays.fill(aint, -2147483648);
/*  36 */       System.arraycopy(this.locations, 0, aint, 0, this.locations.length);
/*  37 */       this.locations = aint;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void onProgramSet(int paramInt);
/*     */   
/*     */   public String getName() {
/*  45 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProgram() {
/*  50 */     return this.program;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLocation() {
/*  55 */     if (this.program <= 0)
/*     */     {
/*  57 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*  61 */     int i = this.locations[this.program];
/*     */     
/*  63 */     if (i == Integer.MIN_VALUE) {
/*     */       
/*  65 */       i = ARBShaderObjects.glGetUniformLocationARB(this.program, this.name);
/*  66 */       this.locations[this.program] = i;
/*     */     } 
/*     */     
/*  69 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefined() {
/*  75 */     return (getLocation() >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disable() {
/*  80 */     this.locations[this.program] = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/*  85 */     this.program = 0;
/*  86 */     this.locations = new int[] { -1 };
/*  87 */     resetValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void resetValue();
/*     */   
/*     */   protected void checkGLError() {
/*  94 */     if (Shaders.checkGLError(this.name) != 0)
/*     */     {
/*  96 */       disable();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 102 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shader\\uniform\ShaderUniformBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */