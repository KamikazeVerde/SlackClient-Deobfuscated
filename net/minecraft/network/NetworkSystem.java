/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.bootstrap.ServerBootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollServerSocketChannel;
/*     */ import io.netty.channel.local.LocalAddress;
/*     */ import io.netty.channel.local.LocalEventLoopGroup;
/*     */ import io.netty.channel.local.LocalServerChannel;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioServerSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.network.NetHandlerHandshakeMemory;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.network.NetHandlerHandshakeTCP;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.LazyLoadBase;
/*     */ import net.minecraft.util.MessageDeserializer;
/*     */ import net.minecraft.util.MessageDeserializer2;
/*     */ import net.minecraft.util.MessageSerializer;
/*     */ import net.minecraft.util.MessageSerializer2;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NetworkSystem {
/*  49 */   private static final Logger logger = LogManager.getLogger();
/*  50 */   public static final LazyLoadBase<NioEventLoopGroup> eventLoops = new LazyLoadBase<NioEventLoopGroup>()
/*     */     {
/*     */       protected NioEventLoopGroup load()
/*     */       {
/*  54 */         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  57 */   public static final LazyLoadBase<EpollEventLoopGroup> field_181141_b = new LazyLoadBase<EpollEventLoopGroup>()
/*     */     {
/*     */       protected EpollEventLoopGroup load()
/*     */       {
/*  61 */         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  64 */   public static final LazyLoadBase<LocalEventLoopGroup> SERVER_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>()
/*     */     {
/*     */       protected LocalEventLoopGroup load()
/*     */       {
/*  68 */         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final MinecraftServer mcServer;
/*     */   
/*     */   public volatile boolean isAlive;
/*     */   
/*  77 */   private final List<ChannelFuture> endpoints = Collections.synchronizedList(Lists.newArrayList());
/*  78 */   private final List<NetworkManager> networkManagers = Collections.synchronizedList(Lists.newArrayList());
/*     */ 
/*     */   
/*     */   public NetworkSystem(MinecraftServer server) {
/*  82 */     this.mcServer = server;
/*  83 */     this.isAlive = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLanEndpoint(InetAddress address, int port) throws IOException {
/*  91 */     synchronized (this.endpoints) {
/*     */       Class<NioServerSocketChannel> clazz;
/*     */       
/*     */       LazyLoadBase<NioEventLoopGroup> lazyLoadBase;
/*     */       
/*  96 */       if (Epoll.isAvailable() && this.mcServer.func_181035_ah()) {
/*     */         
/*  98 */         Class<EpollServerSocketChannel> clazz1 = EpollServerSocketChannel.class;
/*  99 */         LazyLoadBase<EpollEventLoopGroup> lazyLoadBase1 = field_181141_b;
/* 100 */         logger.info("Using epoll channel type");
/*     */       }
/*     */       else {
/*     */         
/* 104 */         clazz = NioServerSocketChannel.class;
/* 105 */         lazyLoadBase = eventLoops;
/* 106 */         logger.info("Using default channel type");
/*     */       } 
/*     */       
/* 109 */       this.endpoints.add(((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(clazz)).childHandler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */             {
/*     */               
/*     */               protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */               {
/*     */                 try {
/* 115 */                   p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
/*     */                 }
/* 117 */                 catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 122 */                 p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new PingResponseHandler(NetworkSystem.this)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(PacketDirection.OUTGOING)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(PacketDirection.INCOMING));
/* 123 */                 NetworkManager networkmanager = new NetworkManager(PacketDirection.OUTGOING);
/* 124 */                 NetworkSystem.this.networkManagers.add(networkmanager);
/* 125 */                 p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/* 126 */                 networkmanager.setNetHandler((INetHandler)new NetHandlerHandshakeTCP(NetworkSystem.this.mcServer, networkmanager));
/*     */               }
/* 128 */             }).group((EventLoopGroup)lazyLoadBase.getValue()).localAddress(address, port)).bind().syncUninterruptibly());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress addLocalEndpoint() {
/*     */     ChannelFuture channelfuture;
/* 139 */     synchronized (this.endpoints) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       channelfuture = ((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer<Channel>() { protected void initChannel(Channel p_initChannel_1_) throws Exception { NetworkManager networkmanager = new NetworkManager(PacketDirection.OUTGOING); networkmanager.setNetHandler((INetHandler)new NetHandlerHandshakeMemory(NetworkSystem.this.mcServer, networkmanager)); NetworkSystem.this.networkManagers.add(networkmanager); p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager); } }).group((EventLoopGroup)eventLoops.getValue()).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
/* 151 */       this.endpoints.add(channelfuture);
/*     */     } 
/*     */     
/* 154 */     return channelfuture.channel().localAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminateEndpoints() {
/* 162 */     this.isAlive = false;
/*     */     
/* 164 */     for (ChannelFuture channelfuture : this.endpoints) {
/*     */ 
/*     */       
/*     */       try {
/* 168 */         channelfuture.channel().close().sync();
/*     */       }
/* 170 */       catch (InterruptedException var4) {
/*     */         
/* 172 */         logger.error("Interrupted whilst closing channel");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void networkTick() {
/* 183 */     synchronized (this.networkManagers) {
/*     */       
/* 185 */       Iterator<NetworkManager> iterator = this.networkManagers.iterator();
/*     */       
/* 187 */       while (iterator.hasNext()) {
/*     */         
/* 189 */         final NetworkManager networkmanager = iterator.next();
/*     */         
/* 191 */         if (!networkmanager.hasNoChannel()) {
/*     */           
/* 193 */           if (!networkmanager.isChannelOpen()) {
/*     */             
/* 195 */             iterator.remove();
/* 196 */             networkmanager.checkDisconnected();
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/*     */           try {
/* 202 */             networkmanager.processReceivedPackets();
/*     */           }
/* 204 */           catch (Exception exception) {
/*     */             
/* 206 */             if (networkmanager.isLocalChannel()) {
/*     */               
/* 208 */               CrashReport crashreport = CrashReport.makeCrashReport(exception, "Ticking memory connection");
/* 209 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Ticking connection");
/* 210 */               crashreportcategory.addCrashSectionCallable("Connection", new Callable<String>()
/*     */                   {
/*     */                     public String call() throws Exception
/*     */                     {
/* 214 */                       return networkmanager.toString();
/*     */                     }
/*     */                   });
/* 217 */               throw new ReportedException(crashreport);
/*     */             } 
/*     */             
/* 220 */             logger.warn("Failed to handle packet for " + networkmanager.getRemoteAddress(), exception);
/* 221 */             final ChatComponentText chatcomponenttext = new ChatComponentText("Internal server error");
/* 222 */             networkmanager.sendPacket((Packet)new S40PacketDisconnect((IChatComponent)chatcomponenttext), new GenericFutureListener<Future<? super Void>>()
/*     */                 {
/*     */                   public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception
/*     */                   {
/* 226 */                     networkmanager.closeChannel((IChatComponent)chatcomponenttext);
/*     */                   }
/*     */                 },  (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]);
/* 229 */             networkmanager.disableAutoRead();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MinecraftServer getServer() {
/* 239 */     return this.mcServer;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\network\NetworkSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */