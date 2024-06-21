/*    */ package com.viaversion.viaversion.api.type.types;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.lang.reflect.Array;
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
/*    */ public class ArrayType<T>
/*    */   extends Type<T[]>
/*    */ {
/*    */   private final Type<T> elementType;
/*    */   
/*    */   public ArrayType(Type<T> type) {
/* 33 */     super(type.getTypeName() + " Array", getArrayClass(type.getOutputClass()));
/* 34 */     this.elementType = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Class<?> getArrayClass(Class<?> componentType) {
/* 39 */     return Array.newInstance(componentType, 0).getClass();
/*    */   }
/*    */ 
/*    */   
/*    */   public T[] read(ByteBuf buffer) throws Exception {
/* 44 */     int amount = Type.VAR_INT.readPrimitive(buffer);
/* 45 */     T[] array = (T[])Array.newInstance(this.elementType.getOutputClass(), amount);
/*    */     
/* 47 */     for (int i = 0; i < amount; i++) {
/* 48 */       array[i] = (T)this.elementType.read(buffer);
/*    */     }
/* 50 */     return array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, T[] object) throws Exception {
/* 55 */     Type.VAR_INT.writePrimitive(buffer, object.length);
/* 56 */     for (T o : object)
/* 57 */       this.elementType.write(buffer, o); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\ArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */