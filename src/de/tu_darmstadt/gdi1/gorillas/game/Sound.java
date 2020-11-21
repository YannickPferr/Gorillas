package de.tu_darmstadt.gdi1.gorillas.game;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * @author Yannick Pferr, Ludwig Koch, Kevin Rueckert
 *
 */

public class Sound {

	AudioInputStream audio;
	public Clip clip;
	public static boolean playing;

	public Sound(String filename, boolean loop) {
		File f = new File(filename);

		try {
			audio = AudioSystem.getAudioInputStream(f);
			clip = AudioSystem.getClip();
			clip.open(audio);
			if (loop)
				playLoop();
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// plays a clip
	public void play() {
		clip.start();
	}

	// stops a clip
	public void stop() {
		clip.stop();
		playing = false;
	}

	// loops the clip continuously
	public void playLoop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		playing = true;
	}
}
