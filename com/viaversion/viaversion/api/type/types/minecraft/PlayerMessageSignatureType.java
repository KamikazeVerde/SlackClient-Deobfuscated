/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
/*    */ import com.viaversion.viaversion.api.type.OptionalType;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.UUID;
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
/*    */ public class PlayerMessageSignatureType
/*    */   extends Type<PlayerMessageSignature>
/*    */ {
/*    */   public PlayerMessageSignatureType() {
/* 33 */     super(PlayerMessageSignature.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlayerMessageSignature read(ByteBuf buffer) throws Exception {
/* 38 */     return new PlayerMessageSignature((UUID)Type.UUID.read(buffer), (byte[])Type.BYTE_ARRAY_PRIMITIVE.read(buffer));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, PlayerMessageSignature value) throws Exception {
/* 43 */     Type.UUID.write(buffer, value.uuid());
/* 44 */     Type.BYTE_ARRAY_PRIMITIVE.write(buffer, value.signatureBytes());
/*    */   }
/*    */   
/*    */   public static final class OptionalPlayerMessageSignatureType
/*    */     extends OptionalType<PlayerMessageSignature> {
/*    */     public OptionalPlayerMessageSignatureType() {
/* 50 */       super(Type.PLAYER_MESSAGE_SIGNATURE);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\PlayerMessageSignatureType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */