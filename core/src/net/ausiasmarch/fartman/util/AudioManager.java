package net.ausiasmarch.fartman.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * AudioManager.java
 * Gestiona la musica y sonidos
 * @author Luis
 *
 */

public class AudioManager {
	/** Administrador de audio */
	public static final AudioManager instance = new AudioManager();
	/** Musica reproduciendose*/
	private Music playingMusic;
	
	/** singleton: previene la instanciacion desde otras clases */
	private AudioManager () {
	}
	
	/** Reproduce sonido */
	public void play (Sound sound) {
		play(sound, 1);
	}
	
	/** Reproduce sonido */
	public void play (Sound sound, float volume) {
		play(sound, volume, 1);
	}
	
	/** Reproduce sonido */
	public void play (Sound sound, float volume, float pitch) {
		play(sound, volume, pitch, 0);
	}
	
	/** Reproduce sonido */
	public void play (Sound sound, float volume, float pitch, float pan) {
		if (!GamePreferences.instance.sound) return;
		sound.play(volume, pitch, pan);
	}
	
	/** Reproduce musica */
	public void play(Music music){		
		play(music, 1);
	}
	
	/** Reproduce musica */
	public void play (Music music, float volume) {
		stopMusic();
		playingMusic = music;
		if (GamePreferences.instance.music) {
			music.setLooping(true);
			music.setVolume(volume);
			music.play();
		}
	}
	
	/** Para la musica */
	public void stopMusic () {
		if (playingMusic != null) playingMusic.stop();
	}
	
	/** Obtiene la musica que se esta reproduciendo */
	public Music getPlayingMusic () {
		return playingMusic;
	}
	
	/** Reproduce/pausa la musica segun el archivo de preferencias */
	public void onSettingsUpdated () {
		if (playingMusic == null) return;
		if (GamePreferences.instance.music) {
			if (!playingMusic.isPlaying()) playingMusic.play();
		} else {
			playingMusic.pause();
		}
	}

}
