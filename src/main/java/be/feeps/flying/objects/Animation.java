package be.feeps.flying.objects;

import be.feeps.flying.utilities.Updater;

/*
Simple animation class
 */
public class Animation {
    private int frame;
    private int min;
    private int max;
    private Updater.Type quality;
    private AnimPlayer animPlayer;
    private AnimPlayer stopPlayer;
    private boolean isInfinite;
    private boolean isPlaying;
    private boolean increment;

    public Animation(int min, int max, Updater.Type quality, AnimPlayer animPlayer){
        this.animPlayer = animPlayer;
        this.min = min;
        this.max = max;
        this.quality = quality;
        this.isInfinite = false; // Default
        this.isPlaying = false;
        this.increment = true;

        this.frame = this.min;
    }

    public void update(Updater.Type type){
        if (!this.isPlaying) return;
        if (this.quality != type) return;

        this.animPlayer.play(this);

        // Next frame
        if (isInfinite){
            if (this.frame == this.min)
                this.increment = true;
            else if (this.frame == this.max)
                this.increment = false;

            if (this.increment)
                this.frame++;
            else
                this.frame--;
        }else{
            if (this.frame == this.max) this.stop();
            this.frame++;
        }
    }

    public void play(){
        this.frame = this.min;
        this.isPlaying = true;
    }

    public void stop(){
        if (getStopPlayer() != null){
            getStopPlayer().play(this);
        }
        this.isPlaying = false;
    }

    public AnimPlayer getStopPlayer() {
        return stopPlayer;
    }

    public void setStopPlayer(AnimPlayer stopPlayer) {
        this.stopPlayer = stopPlayer;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Updater.Type getQuality() {
        return quality;
    }

    public void setQuality(Updater.Type quality) {
        this.quality = quality;
    }

    public AnimPlayer getAnimPlayer() {
        return animPlayer;
    }

    public void setAnimPlayer(AnimPlayer animPlayer) {
        this.animPlayer = animPlayer;
    }

    public boolean isInfinite() {
        return isInfinite;
    }

    public void setInfinite(boolean infinite) {
        isInfinite = infinite;
    }

    public int getFrame() {
        return this.frame;
    }
}
