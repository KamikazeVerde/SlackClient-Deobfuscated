/*     */ package net.minecraft.client.network;
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import io.netty.channel.socket.nio.NioSocketChannel;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerAddress;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.ServerStatusResponse;
/*     */ import net.minecraft.network.status.INetHandlerStatusClient;
/*     */ import net.minecraft.network.status.client.C00PacketServerQuery;
/*     */ import net.minecraft.network.status.client.C01PacketPing;
/*     */ import net.minecraft.network.status.server.S00PacketServerInfo;
/*     */ import net.minecraft.network.status.server.S01PacketPong;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class OldServerPinger {
/*  47 */   private static final Splitter PING_RESPONSE_SPLITTER = Splitter.on(false).limit(6);
/*  48 */   private static final Logger logger = LogManager.getLogger();
/*  49 */   private final List<NetworkManager> pingDestinations = Collections.synchronizedList(Lists.newArrayList());
/*     */ 
/*     */   
/*     */   public void ping(final ServerData server) throws UnknownHostException {
/*  53 */     ServerAddress serveraddress = ServerAddress.func_78860_a(server.serverIP);
/*  54 */     final NetworkManager networkmanager = NetworkManager.func_181124_a(InetAddress.getByName(serveraddress.getIP()), serveraddress.getPort(), false);
/*  55 */     this.pingDestinations.add(networkmanager);
/*  56 */     server.serverMOTD = "Pinging...";
/*  57 */     server.pingToServer = -1L;
/*  58 */     server.playerList = null;
/*  59 */     networkmanager.setNetHandler((INetHandler)new INetHandlerStatusClient()
/*     */         {
/*     */           private boolean field_147403_d = false;
/*     */           private boolean field_183009_e = false;
/*  63 */           private long field_175092_e = 0L;
/*     */           
/*     */           public void handleServerInfo(S00PacketServerInfo packetIn) {
/*  66 */             if (this.field_183009_e) {
/*     */               
/*  68 */               networkmanager.closeChannel((IChatComponent)new ChatComponentText("Received unrequested status"));
/*     */             }
/*     */             else {
/*     */               
/*  72 */               this.field_183009_e = true;
/*  73 */               ServerStatusResponse serverstatusresponse = packetIn.getResponse();
/*     */               
/*  75 */               if (serverstatusresponse.getServerDescription() != null) {
/*     */                 
/*  77 */                 server.serverMOTD = serverstatusresponse.getServerDescription().getFormattedText();
/*     */               }
/*     */               else {
/*     */                 
/*  81 */                 server.serverMOTD = "";
/*     */               } 
/*     */               
/*  84 */               if (serverstatusresponse.getProtocolVersionInfo() != null) {
/*     */                 
/*  86 */                 server.gameVersion = serverstatusresponse.getProtocolVersionInfo().getName();
/*  87 */                 server.version = serverstatusresponse.getProtocolVersionInfo().getProtocol();
/*     */               }
/*     */               else {
/*     */                 
/*  91 */                 server.gameVersion = "Old";
/*  92 */                 server.version = 0;
/*     */               } 
/*     */               
/*  95 */               if (serverstatusresponse.getPlayerCountData() != null) {
/*     */                 
/*  97 */                 server.populationInfo = ChatFormatting.GRAY + "" + serverstatusresponse.getPlayerCountData().getOnlinePlayerCount() + "" + ChatFormatting.DARK_GRAY + "/" + ChatFormatting.GRAY + serverstatusresponse.getPlayerCountData().getMaxPlayers();
/*     */                 
/*  99 */                 if (ArrayUtils.isNotEmpty((Object[])serverstatusresponse.getPlayerCountData().getPlayers()))
/*     */                 {
/* 101 */                   StringBuilder stringbuilder = new StringBuilder();
/*     */                   
/* 103 */                   for (GameProfile gameprofile : serverstatusresponse.getPlayerCountData().getPlayers()) {
/*     */                     
/* 105 */                     if (stringbuilder.length() > 0)
/*     */                     {
/* 107 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/* 110 */                     stringbuilder.append(gameprofile.getName());
/*     */                   } 
/*     */                   
/* 113 */                   if ((serverstatusresponse.getPlayerCountData().getPlayers()).length < serverstatusresponse.getPlayerCountData().getOnlinePlayerCount()) {
/*     */                     
/* 115 */                     if (stringbuilder.length() > 0)
/*     */                     {
/* 117 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/* 120 */                     stringbuilder.append("... and ").append(serverstatusresponse.getPlayerCountData().getOnlinePlayerCount() - (serverstatusresponse.getPlayerCountData().getPlayers()).length).append(" more ...");
/*     */                   } 
/*     */                   
/* 123 */                   server.playerList = stringbuilder.toString();
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 128 */                 server.populationInfo = ChatFormatting.DARK_GRAY + "???";
/*     */               } 
/*     */               
/* 131 */               if (serverstatusresponse.getFavicon() != null) {
/*     */                 
/* 133 */                 String s = serverstatusresponse.getFavicon();
/*     */                 
/* 135 */                 if (s.startsWith("data:image/png;base64,"))
/*     */                 {
/* 137 */                   server.setBase64EncodedIconData(s.substring("data:image/png;base64,".length()));
/*     */                 }
/*     */                 else
/*     */                 {
/* 141 */                   OldServerPinger.logger.error("Invalid server icon (unknown format)");
/*     */                 }
/*     */               
/*     */               } else {
/*     */                 
/* 146 */                 server.setBase64EncodedIconData(null);
/*     */               } 
/*     */               
/* 149 */               this.field_175092_e = Minecraft.getSystemTime();
/* 150 */               networkmanager.sendPacket((Packet)new C01PacketPing(this.field_175092_e));
/* 151 */               this.field_147403_d = true;
/*     */             } 
/*     */           }
/*     */           
/*     */           public void handlePong(S01PacketPong packetIn) {
/* 156 */             long i = this.field_175092_e;
/* 157 */             long j = Minecraft.getSystemTime();
/* 158 */             server.pingToServer = j - i;
/* 159 */             networkmanager.closeChannel((IChatComponent)new ChatComponentText("Finished"));
/*     */           }
/*     */           
/*     */           public void onDisconnect(IChatComponent reason) {
/* 163 */             if (!this.field_147403_d) {
/*     */               
/* 165 */               OldServerPinger.logger.error("Can't ping " + server.serverIP + ": " + reason.getUnformattedText());
/* 166 */               server.serverMOTD = ChatFormatting.DARK_RED + "Can't connect to server.";
/* 167 */               server.populationInfo = "";
/* 168 */               OldServerPinger.this.tryCompatibilityPing(server);
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*     */     try {
/* 175 */       networkmanager.sendPacket((Packet)new C00Handshake(47, serveraddress.getIP(), serveraddress.getPort(), EnumConnectionState.STATUS));
/* 176 */       networkmanager.sendPacket((Packet)new C00PacketServerQuery());
/*     */     }
/* 178 */     catch (Throwable throwable) {
/*     */       
/* 180 */       logger.error(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void tryCompatibilityPing(final ServerData server) {
/* 186 */     final ServerAddress serveraddress = ServerAddress.func_78860_a(server.serverIP);
/* 187 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */           {
/*     */             try {
/* 193 */               p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
/*     */             }
/* 195 */             catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 200 */             p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new SimpleChannelInboundHandler<ByteBuf>()
/*     */                   {
/*     */                     public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception
/*     */                     {
/* 204 */                       super.channelActive(p_channelActive_1_);
/* 205 */                       ByteBuf bytebuf = Unpooled.buffer();
/*     */ 
/*     */                       
/*     */                       try {
/* 209 */                         bytebuf.writeByte(254);
/* 210 */                         bytebuf.writeByte(1);
/* 211 */                         bytebuf.writeByte(250);
/* 212 */                         char[] achar = "MC|PingHost".toCharArray();
/* 213 */                         bytebuf.writeShort(achar.length);
/*     */                         
/* 215 */                         for (char c0 : achar)
/*     */                         {
/* 217 */                           bytebuf.writeChar(c0);
/*     */                         }
/*     */                         
/* 220 */                         bytebuf.writeShort(7 + 2 * serveraddress.getIP().length());
/* 221 */                         bytebuf.writeByte(127);
/* 222 */                         achar = serveraddress.getIP().toCharArray();
/* 223 */                         bytebuf.writeShort(achar.length);
/*     */                         
/* 225 */                         for (char c1 : achar)
/*     */                         {
/* 227 */                           bytebuf.writeChar(c1);
/*     */                         }
/*     */                         
/* 230 */                         bytebuf.writeInt(serveraddress.getPort());
/* 231 */                         p_channelActive_1_.channel().writeAndFlush(bytebuf).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */                       }
/*     */                       finally {
/*     */                         
/* 235 */                         bytebuf.release();
/*     */                       } 
/*     */                     }
/*     */                     
/*     */                     protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, ByteBuf p_channelRead0_2_) throws Exception {
/* 240 */                       short short1 = p_channelRead0_2_.readUnsignedByte();
/*     */                       
/* 242 */                       if (short1 == 255) {
/*     */                         
/* 244 */                         String s = new String(p_channelRead0_2_.readBytes(p_channelRead0_2_.readShort() * 2).array(), Charsets.UTF_16BE);
/* 245 */                         String[] astring = (String[])Iterables.toArray(OldServerPinger.PING_RESPONSE_SPLITTER.split(s), String.class);
/*     */                         
/* 247 */                         if ("§1".equals(astring[0])) {
/*     */                           
/* 249 */                           int i = MathHelper.parseIntWithDefault(astring[1], 0);
/* 250 */                           String s1 = astring[2];
/* 251 */                           String s2 = astring[3];
/* 252 */                           int j = MathHelper.parseIntWithDefault(astring[4], -1);
/* 253 */                           int k = MathHelper.parseIntWithDefault(astring[5], -1);
/* 254 */                           server.version = -1;
/* 255 */                           server.gameVersion = s1;
/* 256 */                           server.serverMOTD = s2;
/* 257 */                           server.populationInfo = ChatFormatting.GRAY + "" + j + "" + ChatFormatting.DARK_GRAY + "/" + ChatFormatting.GRAY + k;
/*     */                         } 
/*     */                       } 
/*     */                       
/* 261 */                       p_channelRead0_1_.close();
/*     */                     }
/*     */                     
/*     */                     public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) throws Exception {
/* 265 */                       p_exceptionCaught_1_.close();
/*     */                     }
/*     */                   }
/*     */                 });
/*     */           }
/* 270 */         })).channel(NioSocketChannel.class)).connect(serveraddress.getIP(), serveraddress.getPort());
/*     */   }
/*     */ 
/*     */   
/*     */   public void pingPendingNetworks() {
/* 275 */     synchronized (this.pingDestinations) {
/*     */       
/* 277 */       Iterator<NetworkManager> iterator = this.pingDestinations.iterator();
/*     */       
/* 279 */       while (iterator.hasNext()) {
/*     */         
/* 281 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 283 */         if (networkmanager.isChannelOpen()) {
/*     */           
/* 285 */           networkmanager.processReceivedPackets();
/*     */           
/*     */           continue;
/*     */         } 
/* 289 */         iterator.remove();
/* 290 */         networkmanager.checkDisconnected();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPendingNetworks() {
/* 298 */     synchronized (this.pingDestinations) {
/*     */       
/* 300 */       Iterator<NetworkManager> iterator = this.pingDestinations.iterator();
/*     */       
/* 302 */       while (iterator.hasNext()) {
/*     */         
/* 304 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 306 */         if (networkmanager.isChannelOpen()) {
/*     */           
/* 308 */           iterator.remove();
/* 309 */           networkmanager.closeChannel((IChatComponent)new ChatComponentText("Cancelled"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\network\OldServerPinger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */