package classes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.*;

public class MusicHandler 
{
	private HashMap<String, File> sounds;
	private Clip clip;
	
	public MusicHandler(ArrayList<File> musicFiles)
	{
		sounds = new HashMap<>();
		for (File musicFile : musicFiles)
		{
			String name = musicFile.getName();
			int indexOfFileType = name.indexOf('.');
			name = name.substring(0, indexOfFileType);
			
			sounds.put(name, musicFile);
		}
	}
	
	public MusicHandler(File[] musicFiles)
	{
		sounds = new HashMap<>();
		for (File musicFile : musicFiles)
		{
			String name = musicFile.getName();
			int indexOfFileType = name.indexOf('.');
			name = name.substring(0, indexOfFileType);
			
			sounds.put(name, musicFile);
		}
	}
	
	public MusicHandler(File musicFile)
	{
		sounds = new HashMap<>();
		String name = musicFile.getName();
		int indexOfFileType = name.indexOf('.');
		name = name.substring(0, indexOfFileType);
		sounds.put(name, musicFile);
	}
	
	public void addSong(File musicFile)
	{
		String name = musicFile.getName();
		int indexOfFileType = name.indexOf('.');
		name = name.substring(0, indexOfFileType);
		sounds.put(name, musicFile);
	}
	
	public void play(String key)
	{
		if (sounds.containsKey(key))
		{
			AudioInputStream stream = null;
			try {
				stream = AudioSystem.getAudioInputStream(sounds.get(key));
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (clip == null)
				try {
					clip = AudioSystem.getClip();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			if (clip.isRunning())
				clip.stop();
			
			try {
				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				clip.open(stream);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			clip.start();
		}
		else
		{
			System.out.println("Does not contain the file: " + key);
		}
	}
	
	public void play(String key, boolean loop)
	{
		if (sounds.containsKey(key))
		{
			AudioInputStream stream = null;
			try {
				stream = AudioSystem.getAudioInputStream(sounds.get(key));
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (clip == null)
				try {
					clip = AudioSystem.getClip();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			if (clip.isRunning())
				clip.stop();
			
			try {
				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				clip.open(stream);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (loop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
		else
		{
			System.out.println("Does not contain the file: " + key);
		}
	}
	
	public void stop() 
	{
		if (clip.isRunning())
			clip.stop();
		else
			System.out.println("Nothing is playing");
	}
}
