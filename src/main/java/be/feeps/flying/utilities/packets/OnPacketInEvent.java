package be.feeps.flying.utilities.packets;

import io.netty.channel.Channel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class OnPacketInEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player sender;
    private final Channel channel;
    private final Object packet;
    private boolean cancelled;

    public OnPacketInEvent(Player sender, Channel channel, Object packet, boolean async) {
        super(async);
        this.sender = sender;
        this.channel = channel;
        this.packet = packet;
    }

    public Player getSender() {
        return sender;
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