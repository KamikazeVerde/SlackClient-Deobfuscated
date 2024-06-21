/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class Particle
/*    */ {
/* 30 */   private List<ParticleData> arguments = new ArrayList<>(4);
/*    */   private int id;
/*    */   
/*    */   public Particle(int id) {
/* 34 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 38 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 42 */     this.id = id;
/*    */   }
/*    */   
/*    */   public List<ParticleData> getArguments() {
/* 46 */     return this.arguments;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void setArguments(List<ParticleData> arguments) {
/* 51 */     this.arguments = arguments;
/*    */   }
/*    */   
/*    */   public <T> void add(Type<T> type, T value) {
/* 55 */     this.arguments.add(new ParticleData(type, value));
/*    */   }
/*    */   
/*    */   public static class ParticleData {
/*    */     private Type type;
/*    */     private Object value;
/*    */     
/*    */     public ParticleData(Type type, Object value) {
/* 63 */       this.type = type;
/* 64 */       this.value = value;
/*    */     }
/*    */     
/*    */     public Type getType() {
/* 68 */       return this.type;
/*    */     }
/*    */     
/*    */     public void setType(Type type) {
/* 72 */       this.type = type;
/*    */     }
/*    */     
/*    */     public Object getValue() {
/* 76 */       return this.value;
/*    */     }
/*    */     
/*    */     public <T> T get() {
/* 80 */       return (T)this.value;
/*    */     }
/*    */     
/*    */     public void setValue(Object value) {
/* 84 */       this.value = value;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\Particle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */