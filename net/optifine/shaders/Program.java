/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import net.optifine.render.GlAlphaState;
/*     */ import net.optifine.render.GlBlendState;
/*     */ import net.optifine.shaders.config.RenderScale;
/*     */ 
/*     */ public class Program
/*     */ {
/*     */   private final int index;
/*     */   private final String name;
/*     */   private final ProgramStage programStage;
/*     */   private final Program programBackup;
/*     */   private GlAlphaState alphaState;
/*     */   private GlBlendState blendState;
/*     */   private RenderScale renderScale;
/*  18 */   private final Boolean[] buffersFlip = new Boolean[8];
/*     */   private int id;
/*     */   private int ref;
/*     */   private String drawBufSettings;
/*     */   private IntBuffer drawBuffers;
/*     */   private IntBuffer drawBuffersBuffer;
/*     */   private int compositeMipmapSetting;
/*     */   private int countInstances;
/*  26 */   private final boolean[] toggleColorTextures = new boolean[8];
/*     */ 
/*     */   
/*     */   public Program(int index, String name, ProgramStage programStage, Program programBackup) {
/*  30 */     this.index = index;
/*  31 */     this.name = name;
/*  32 */     this.programStage = programStage;
/*  33 */     this.programBackup = programBackup;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program(int index, String name, ProgramStage programStage, boolean ownBackup) {
/*  38 */     this.index = index;
/*  39 */     this.name = name;
/*  40 */     this.programStage = programStage;
/*  41 */     this.programBackup = ownBackup ? this : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetProperties() {
/*  46 */     this.alphaState = null;
/*  47 */     this.blendState = null;
/*  48 */     this.renderScale = null;
/*  49 */     Arrays.fill((Object[])this.buffersFlip, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetId() {
/*  54 */     this.id = 0;
/*  55 */     this.ref = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetConfiguration() {
/*  60 */     this.drawBufSettings = null;
/*  61 */     this.compositeMipmapSetting = 0;
/*  62 */     this.countInstances = 0;
/*     */     
/*  64 */     if (this.drawBuffersBuffer == null)
/*     */     {
/*  66 */       this.drawBuffersBuffer = Shaders.nextIntBuffer(8);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyFrom(Program p) {
/*  72 */     this.id = p.getId();
/*  73 */     this.alphaState = p.getAlphaState();
/*  74 */     this.blendState = p.getBlendState();
/*  75 */     this.renderScale = p.getRenderScale();
/*  76 */     System.arraycopy(p.getBuffersFlip(), 0, this.buffersFlip, 0, this.buffersFlip.length);
/*  77 */     this.drawBufSettings = p.getDrawBufSettings();
/*  78 */     this.drawBuffers = p.getDrawBuffers();
/*  79 */     this.compositeMipmapSetting = p.getCompositeMipmapSetting();
/*  80 */     this.countInstances = p.getCountInstances();
/*  81 */     System.arraycopy(p.getToggleColorTextures(), 0, this.toggleColorTextures, 0, this.toggleColorTextures.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  86 */     return this.index;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  91 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public ProgramStage getProgramStage() {
/*  96 */     return this.programStage;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program getProgramBackup() {
/* 101 */     return this.programBackup;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 106 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRef() {
/* 111 */     return this.ref;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDrawBufSettings() {
/* 116 */     return this.drawBufSettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntBuffer getDrawBuffers() {
/* 121 */     return this.drawBuffers;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntBuffer getDrawBuffersBuffer() {
/* 126 */     return this.drawBuffersBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCompositeMipmapSetting() {
/* 131 */     return this.compositeMipmapSetting;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCountInstances() {
/* 136 */     return this.countInstances;
/*     */   }
/*     */ 
/*     */   
/*     */   public GlAlphaState getAlphaState() {
/* 141 */     return this.alphaState;
/*     */   }
/*     */ 
/*     */   
/*     */   public GlBlendState getBlendState() {
/* 146 */     return this.blendState;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderScale getRenderScale() {
/* 151 */     return this.renderScale;
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean[] getBuffersFlip() {
/* 156 */     return this.buffersFlip;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] getToggleColorTextures() {
/* 161 */     return this.toggleColorTextures;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(int id) {
/* 166 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRef(int ref) {
/* 171 */     this.ref = ref;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDrawBufSettings(String drawBufSettings) {
/* 176 */     this.drawBufSettings = drawBufSettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDrawBuffers(IntBuffer drawBuffers) {
/* 181 */     this.drawBuffers = drawBuffers;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompositeMipmapSetting(int compositeMipmapSetting) {
/* 186 */     this.compositeMipmapSetting = compositeMipmapSetting;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCountInstances(int countInstances) {
/* 191 */     this.countInstances = countInstances;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlphaState(GlAlphaState alphaState) {
/* 196 */     this.alphaState = alphaState;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlendState(GlBlendState blendState) {
/* 201 */     this.blendState = blendState;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRenderScale(RenderScale renderScale) {
/* 206 */     this.renderScale = renderScale;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRealProgramName() {
/* 211 */     if (this.id == 0)
/*     */     {
/* 213 */       return "none";
/*     */     }
/*     */ 
/*     */     
/*     */     Program program;
/*     */     
/* 219 */     for (program = this; program.getRef() != this.id; program = program.getProgramBackup()) {
/*     */       
/* 221 */       if (program.getProgramBackup() == null || program.getProgramBackup() == program)
/*     */       {
/* 223 */         return "unknown";
/*     */       }
/*     */     } 
/*     */     
/* 227 */     return program.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 233 */     return "name: " + this.name + ", id: " + this.id + ", ref: " + this.ref + ", real: " + getRealProgramName();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\Program.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */