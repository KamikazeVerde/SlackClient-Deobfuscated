/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.ProfileKey;
/*    */ import com.viaversion.viaversion.api.type.OptionalType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public class ProfileKeyType
/*    */   extends Type<ProfileKey>
/*    */ {
/*    */   public ProfileKeyType() {
/* 33 */     super(ProfileKey.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public ProfileKey read(ByteBuf buffer) throws Exception {
/* 38 */     return new ProfileKey(buffer.readLong(), (byte[])Type.BYTE_ARRAY_PRIMITIVE.read(buffer), (byte[])Type.BYTE_ARRAY_PRIMITIVE.read(buffer));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, ProfileKey object) throws Exception {
/* 43 */     buffer.writeLong(object.expiresAt());
/* 44 */     Type.BYTE_ARRAY_PRIMITIVE.write(buffer, object.publicKey());
/* 45 */     Type.BYTE_ARRAY_PRIMITIVE.write(buffer, object.keySignature());
/*    */   }
/*    */   
/*    */   public static final class OptionalProfileKeyType
/*    */     extends OptionalType<ProfileKey> {
/*    */     public OptionalProfileKeyType() {
/* 51 */       super(Type.PROFILE_KEY);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\ProfileKeyType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */