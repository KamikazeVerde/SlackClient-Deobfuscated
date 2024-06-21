/*     */ package net.minecraft.network;
/*     */ import cc.slack.events.impl.network.PacketEvent;
/*     */ import cc.slack.utils.player.BlinkUtil;
/*     */ import cc.slack.utils.player.RotationUtil;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import com.viaversion.viaversion.connection.UserConnectionImpl;
/*     */ import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
/*     */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
/*     */ import de.florianmichael.viamcp.MCPVLBPipeline;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollSocketChannel;
/*     */ import io.netty.channel.local.LocalChannel;
/*     */ import io.netty.channel.local.LocalEventLoopGroup;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.LazyLoadBase;
/*     */ import net.minecraft.util.MessageDeserializer;
/*     */ import net.minecraft.util.MessageDeserializer2;
/*     */ import net.minecraft.util.MessageSerializer;
/*     */ import net.minecraft.util.MessageSerializer2;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.MarkerManager;
/*     */ 
/*     */ public class NetworkManager extends SimpleChannelInboundHandler<Packet> {
/*  58 */   private static final Logger logger = LogManager.getLogger();
/*  59 */   public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
/*  60 */   public static final Marker logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
/*  61 */   public static final AttributeKey<EnumConnectionState> attrKeyConnectionState = AttributeKey.valueOf("protocol");
/*  62 */   public static final LazyLoadBase<NioEventLoopGroup> CLIENT_NIO_EVENTLOOP = new LazyLoadBase<NioEventLoopGroup>()
/*     */     {
/*     */       protected NioEventLoopGroup load()
/*     */       {
/*  66 */         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  69 */   public static final LazyLoadBase<EpollEventLoopGroup> field_181125_e = new LazyLoadBase<EpollEventLoopGroup>()
/*     */     {
/*     */       protected EpollEventLoopGroup load()
/*     */       {
/*  73 */         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  76 */   public static final LazyLoadBase<LocalEventLoopGroup> CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>()
/*     */     {
/*     */       protected LocalEventLoopGroup load()
/*     */       {
/*  80 */         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*     */   private final PacketDirection direction;
/*  84 */   private final Queue<InboundHandlerTuplePacketListener> outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
/*  85 */   private final ReentrantReadWriteLock field_181680_j = new ReentrantReadWriteLock();
/*     */ 
/*     */   
/*     */   public Channel channel;
/*     */ 
/*     */   
/*     */   private SocketAddress socketAddress;
/*     */   
/*     */   private INetHandler packetListener;
/*     */   
/*     */   private IChatComponent terminationReason;
/*     */   
/*     */   private boolean isEncrypted;
/*     */   
/*     */   private boolean disconnected;
/*     */ 
/*     */   
/*     */   public NetworkManager(PacketDirection packetDirection) {
/* 103 */     this.direction = packetDirection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception {
/* 108 */     super.channelActive(p_channelActive_1_);
/* 109 */     this.channel = p_channelActive_1_.channel();
/* 110 */     this.socketAddress = this.channel.remoteAddress();
/*     */ 
/*     */     
/*     */     try {
/* 114 */       setConnectionState(EnumConnectionState.HANDSHAKING);
/*     */     }
/* 116 */     catch (Throwable throwable) {
/*     */       
/* 118 */       logger.fatal(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectionState(EnumConnectionState newState) {
/* 127 */     this.channel.attr(attrKeyConnectionState).set(newState);
/* 128 */     this.channel.config().setAutoRead(true);
/* 129 */     logger.debug("Enabled auto read");
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext p_channelInactive_1_) throws Exception {
/* 134 */     closeChannel((IChatComponent)new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) throws Exception {
/*     */     ChatComponentTranslation chatcomponenttranslation;
/* 141 */     if (p_exceptionCaught_2_ instanceof io.netty.handler.timeout.TimeoutException) {
/*     */       
/* 143 */       chatcomponenttranslation = new ChatComponentTranslation("disconnect.timeout", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 147 */       chatcomponenttranslation = new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + p_exceptionCaught_2_ });
/*     */     } 
/*     */     
/* 150 */     closeChannel((IChatComponent)chatcomponenttranslation);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void channelRead0(ChannelHandlerContext cHandler, Packet<INetHandler> packet) throws Exception {
/* 155 */     if (this.channel.isOpen()) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 160 */         PacketEvent packetEvent = new PacketEvent(packet, PacketDirection.INCOMING);
/* 161 */         if (packetEvent.call().isCanceled())
/*     */           return; 
/* 163 */         if (BlinkUtil.handlePacket(packetEvent))
/*     */           return; 
/* 165 */         packet.processPacket(this.packetListener);
/*     */       }
/* 167 */       catch (ThreadQuickExitException threadQuickExitException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNetHandler(INetHandler handler) {
/* 180 */     Validate.notNull(handler, "packetListener", new Object[0]);
/* 181 */     logger.debug("Set listener of {} to {}", new Object[] { this, handler });
/* 182 */     this.packetListener = handler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPacket(Packet packetIn) {
/* 187 */     if (isChannelOpen()) {
/*     */       
/* 189 */       flushOutboundQueue();
/* 190 */       dispatchPacket(packetIn, null);
/*     */     }
/*     */     else {
/*     */       
/* 194 */       this.field_181680_j.writeLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 198 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])null));
/*     */       }
/*     */       finally {
/*     */         
/* 202 */         this.field_181680_j.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendPacket(Packet packetIn, GenericFutureListener<? extends Future<? super Void>> listener, GenericFutureListener<? extends Future<? super Void>>... listeners) {
/* 209 */     if (isChannelOpen()) {
/*     */       
/* 211 */       flushOutboundQueue();
/* 212 */       dispatchPacket(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])listeners, 0, listener));
/*     */     }
/*     */     else {
/*     */       
/* 216 */       this.field_181680_j.writeLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 220 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])listeners, 0, listener)));
/*     */       }
/*     */       finally {
/*     */         
/* 224 */         this.field_181680_j.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendPacketNoEvent(Packet packetIn) {
/* 230 */     if (isChannelOpen()) {
/*     */       
/* 232 */       flushOutboundQueue();
/* 233 */       dispatchPacketNoEvent(packetIn, null);
/*     */     } else {
/* 235 */       this.field_181680_j.writeLock().lock();
/*     */       
/*     */       try {
/* 238 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])null));
/*     */       } finally {
/* 240 */         this.field_181680_j.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dispatchPacket(final Packet inPacket, final GenericFutureListener<? extends Future<? super Void>>[] futureListeners) {
/* 250 */     final EnumConnectionState enumconnectionstate = EnumConnectionState.getFromPacket(inPacket);
/* 251 */     final EnumConnectionState enumconnectionstate1 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();
/*     */     
/* 253 */     PacketEvent packetEvent = new PacketEvent(inPacket, PacketDirection.OUTGOING);
/* 254 */     if (packetEvent.call().isCanceled())
/*     */       return; 
/* 256 */     if (BlinkUtil.handlePacket(packetEvent))
/*     */       return; 
/* 258 */     if (enumconnectionstate1 != enumconnectionstate) {
/*     */       
/* 260 */       logger.debug("Disabled auto read");
/* 261 */       this.channel.config().setAutoRead(false);
/*     */     } 
/*     */     
/* 264 */     if (this.channel.eventLoop().inEventLoop()) {
/*     */       
/* 266 */       if (enumconnectionstate != enumconnectionstate1)
/*     */       {
/* 268 */         setConnectionState(enumconnectionstate);
/*     */       }
/*     */       
/* 271 */       ChannelFuture channelfuture = this.channel.writeAndFlush(inPacket);
/*     */       
/* 273 */       if (futureListeners != null)
/*     */       {
/* 275 */         channelfuture.addListeners((GenericFutureListener[])futureListeners);
/*     */       }
/*     */       
/* 278 */       channelfuture.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */     }
/*     */     else {
/*     */       
/* 282 */       this.channel.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 286 */               if (enumconnectionstate != enumconnectionstate1)
/*     */               {
/* 288 */                 NetworkManager.this.setConnectionState(enumconnectionstate);
/*     */               }
/*     */               
/* 291 */               ChannelFuture channelfuture1 = NetworkManager.this.channel.writeAndFlush(inPacket);
/*     */               
/* 293 */               if (futureListeners != null)
/*     */               {
/* 295 */                 channelfuture1.addListeners(futureListeners);
/*     */               }
/*     */               
/* 298 */               channelfuture1.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private void dispatchPacketNoEvent(final Packet inPacket, final GenericFutureListener<? extends Future<? super Void>>[] futureListeners) {
/* 305 */     final EnumConnectionState enumconnectionstate = EnumConnectionState.getFromPacket(inPacket);
/* 306 */     final EnumConnectionState enumconnectionstate1 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();
/*     */     
/* 308 */     if (inPacket instanceof C03PacketPlayer && 
/* 309 */       RotationUtil.isEnabled) {
/* 310 */       ((C03PacketPlayer)inPacket).yaw = RotationUtil.clientRotation[0];
/* 311 */       ((C03PacketPlayer)inPacket).pitch = RotationUtil.clientRotation[1];
/*     */     } 
/*     */ 
/*     */     
/* 315 */     if (enumconnectionstate1 != enumconnectionstate) {
/*     */       
/* 317 */       logger.debug("Disabled auto read");
/* 318 */       this.channel.config().setAutoRead(false);
/*     */     } 
/*     */     
/* 321 */     if (this.channel.eventLoop().inEventLoop()) {
/*     */       
/* 323 */       if (enumconnectionstate != enumconnectionstate1)
/*     */       {
/* 325 */         setConnectionState(enumconnectionstate);
/*     */       }
/*     */       
/* 328 */       ChannelFuture channelfuture = this.channel.writeAndFlush(inPacket);
/*     */       
/* 330 */       if (futureListeners != null)
/*     */       {
/* 332 */         channelfuture.addListeners((GenericFutureListener[])futureListeners);
/*     */       }
/*     */       
/* 335 */       channelfuture.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */     }
/*     */     else {
/*     */       
/* 339 */       this.channel.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 343 */               if (enumconnectionstate != enumconnectionstate1)
/*     */               {
/* 345 */                 NetworkManager.this.setConnectionState(enumconnectionstate);
/*     */               }
/*     */               
/* 348 */               ChannelFuture channelfuture1 = NetworkManager.this.channel.writeAndFlush(inPacket);
/*     */               
/* 350 */               if (futureListeners != null)
/*     */               {
/* 352 */                 channelfuture1.addListeners(futureListeners);
/*     */               }
/*     */               
/* 355 */               channelfuture1.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushOutboundQueue() {
/* 366 */     if (this.channel != null && this.channel.isOpen()) {
/*     */       
/* 368 */       this.field_181680_j.readLock().lock();
/*     */ 
/*     */       
/*     */       try {
/* 372 */         while (!this.outboundPacketsQueue.isEmpty())
/*     */         {
/* 374 */           InboundHandlerTuplePacketListener networkmanager$inboundhandlertuplepacketlistener = this.outboundPacketsQueue.poll();
/* 375 */           dispatchPacket(networkmanager$inboundhandlertuplepacketlistener.packet, networkmanager$inboundhandlertuplepacketlistener.futureListeners);
/*     */         }
/*     */       
/*     */       } finally {
/*     */         
/* 380 */         this.field_181680_j.readLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processReceivedPackets() {
/* 390 */     flushOutboundQueue();
/*     */     
/* 392 */     if (this.packetListener instanceof ITickable)
/*     */     {
/* 394 */       ((ITickable)this.packetListener).update();
/*     */     }
/*     */     
/* 397 */     this.channel.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress getRemoteAddress() {
/* 405 */     return this.socketAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeChannel(IChatComponent message) {
/* 413 */     if (this.channel.isOpen()) {
/*     */       
/* 415 */       this.channel.close().awaitUninterruptibly();
/* 416 */       this.terminationReason = message;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalChannel() {
/* 426 */     return (this.channel instanceof LocalChannel || this.channel instanceof io.netty.channel.local.LocalServerChannel);
/*     */   }
/*     */   public static NetworkManager func_181124_a(InetAddress p_181124_0_, int p_181124_1_, boolean p_181124_2_) {
/*     */     Class<NioSocketChannel> clazz;
/*     */     LazyLoadBase<NioEventLoopGroup> lazyLoadBase;
/* 431 */     final NetworkManager networkmanager = new NetworkManager(PacketDirection.INCOMING);
/*     */ 
/*     */ 
/*     */     
/* 435 */     if (Epoll.isAvailable() && p_181124_2_) {
/*     */       
/* 437 */       Class<EpollSocketChannel> clazz1 = EpollSocketChannel.class;
/* 438 */       LazyLoadBase<EpollEventLoopGroup> lazyLoadBase1 = field_181125_e;
/*     */     }
/*     */     else {
/*     */       
/* 442 */       clazz = NioSocketChannel.class;
/* 443 */       lazyLoadBase = CLIENT_NIO_EVENTLOOP;
/*     */     } 
/*     */     
/* 446 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)lazyLoadBase.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */           {
/*     */             try {
/* 452 */               p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
/*     */             }
/* 454 */             catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 459 */             if (!ViaLoadingBase.getInstance().getTargetVersion().isEqualTo(ProtocolVersion.v1_8)) {
/* 460 */               p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(PacketDirection.INCOMING)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(PacketDirection.OUTGOING)).addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */               
/* 462 */               if (p_initChannel_1_ instanceof io.netty.channel.socket.SocketChannel && ViaLoadingBase.getInstance().getTargetVersion().getVersion() != 47) {
/* 463 */                 UserConnectionImpl userConnectionImpl = new UserConnectionImpl(p_initChannel_1_, true);
/* 464 */                 new ProtocolPipelineImpl((UserConnection)userConnectionImpl);
/*     */                 
/* 466 */                 p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new MCPVLBPipeline((UserConnection)userConnectionImpl) });
/*     */               } 
/*     */             } else {
/* 469 */               p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(PacketDirection.INCOMING)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(PacketDirection.OUTGOING)).addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */             } 
/*     */           }
/* 472 */         })).channel(clazz)).connect(p_181124_0_, p_181124_1_).syncUninterruptibly();
/* 473 */     return networkmanager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetworkManager provideLocalClient(SocketAddress address) {
/* 482 */     final NetworkManager networkmanager = new NetworkManager(PacketDirection.INCOMING);
/* 483 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)CLIENT_LOCAL_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>()
/*     */         {
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception
/*     */           {
/* 487 */             p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */           }
/* 489 */         })).channel(LocalChannel.class)).connect(address).syncUninterruptibly();
/* 490 */     return networkmanager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableEncryption(SecretKey key) {
/* 498 */     this.isEncrypted = true;
/* 499 */     this.channel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new NettyEncryptingDecoder(CryptManager.createNetCipherInstance(2, key)));
/* 500 */     this.channel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new NettyEncryptingEncoder(CryptManager.createNetCipherInstance(1, key)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsencrypted() {
/* 505 */     return this.isEncrypted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChannelOpen() {
/* 513 */     return (this.channel != null && this.channel.isOpen());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNoChannel() {
/* 518 */     return (this.channel == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INetHandler getNetHandler() {
/* 526 */     return this.packetListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getExitMessage() {
/* 534 */     return this.terminationReason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableAutoRead() {
/* 542 */     this.channel.config().setAutoRead(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompressionTreshold(int treshold) {
/* 547 */     if (treshold >= 0) {
/*     */       
/* 549 */       if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
/*     */         
/* 551 */         ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
/*     */       }
/*     */       else {
/*     */         
/* 555 */         this.channel.pipeline().addBefore("decoder", "decompress", (ChannelHandler)new NettyCompressionDecoder(treshold));
/*     */       } 
/*     */       
/* 558 */       if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder)
/*     */       {
/* 560 */         ((NettyCompressionEncoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
/*     */       }
/*     */       else
/*     */       {
/* 564 */         this.channel.pipeline().addBefore("encoder", "compress", (ChannelHandler)new NettyCompressionEncoder(treshold));
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 569 */       if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder)
/*     */       {
/* 571 */         this.channel.pipeline().remove("decompress");
/*     */       }
/*     */       
/* 574 */       if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder)
/*     */       {
/* 576 */         this.channel.pipeline().remove("compress");
/*     */       }
/*     */     } 
/* 579 */     if (!ViaLoadingBase.getInstance().getTargetVersion().isEqualTo(ProtocolVersion.v1_8)) {
/* 580 */       this.channel.pipeline().fireUserEventTriggered(new CompressionReorderEvent());
/*     */     }
/*     */   }
/*     */   
/*     */   public void checkDisconnected() {
/* 585 */     if (this.channel != null && !this.channel.isOpen())
/*     */     {
/* 587 */       if (!this.disconnected) {
/*     */         
/* 589 */         this.disconnected = true;
/*     */         
/* 591 */         if (getExitMessage() != null)
/*     */         {
/* 593 */           getNetHandler().onDisconnect(getExitMessage());
/*     */         }
/* 595 */         else if (getNetHandler() != null)
/*     */         {
/* 597 */           getNetHandler().onDisconnect((IChatComponent)new ChatComponentText("Disconnected"));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 602 */         logger.warn("handleDisconnection() called twice");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class InboundHandlerTuplePacketListener
/*     */   {
/*     */     private final Packet packet;
/*     */     private final GenericFutureListener<? extends Future<? super Void>>[] futureListeners;
/*     */     
/*     */     public InboundHandlerTuplePacketListener(Packet inPacket, GenericFutureListener<? extends Future<? super Void>>... inFutureListeners) {
/* 614 */       this.packet = inPacket;
/* 615 */       this.futureListeners = inFutureListeners;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\network\NetworkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */