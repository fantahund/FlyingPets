package be.feeps.flying.utilities.packets;


import io.netty.channel.Channel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class OnPacketOutEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player receiver;
    private final Channel channel;
    private final Object packet;
    private boolean cancelled;

    public OnPacketOutEvent(Player receiver, Channel channel, Object packet, boolean async) {
        super(async);
        this.receiver = receiver;
        this.channel = channel;
        this.packet = packet;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Channel getChannel() {
        return channel;
    }

    public Object getPacket() {
        return packet;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}