package classes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.sampled.*;

/**
 * A class that allows for Music to be played in the program.
 * 
 * Each MusicHandler object will be able to play one track at a time.
 * 
 * If a track is played while another one is playing, the current
 * track being played will be overwritten.
 *
 * Author: Andrew Tacoi
 */

public class MusicHandler 
{
    // *********************  Fields  *********************
    
    private HashMap<String, File> sounds;
    private Clip clip;
    
    // *********************  Constructors  *********************
    
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
	
    // *********************  Public Methods  *********************
    
    /**
     * 
     * Plays a certain track based on the key given.
     * 
     * If no track exits, a MusicHandlerException
     * is thrown.
     * 
     */

	public void play(String key) throws MusicHandlerException
	{
		play(key, false);
	}
	
	public void play(String key, boolean loop) throws MusicHandlerException
	{
		if (sounds.containsKey(key))
		{
			AudioInputStream stream = null;
	        
			try {
			    
				stream = AudioSystem.getAudioInputStream(sounds.get(key));
				
			} catch (UnsupportedAudioFileException e) {
			    
				e.printStackTrace();
			
			} catch (IOException e) {
			    
				e.printStackTrace();
			}
			
			if (clip == null)
			    
				try {
				    
					clip = AudioSystem.getClip();
					
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			
			if (clip.isRunning())
				clip.stop();
			
			try {
			    
				clip = AudioSystem.getClip();
				
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
			
			try {
				clip.open(stream);
				
			} catch (LineUnavailableException e) {
			    
				e.printStackTrace();
				
			} catch (IOException e) {
			    
				e.printStackTrace();
			}
			
			if (loop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
		else
		{
		    throw new MusicHandlerException("Does not contain the track: " + key);
		}
	}
	
	// Adds a song to the MusicHandler and formats it correctly. 
	public void addSong(File musicFile)
    {
        String name = musicFile.getName();
        int indexOfFileType = name.indexOf('.');
        name = name.substring(0, indexOfFileType);
        sounds.put(name, musicFile);
    }
	
	// Returns an array of String of every music track currently in the MusicHandler
	public String[] getMusicTrackNames()
	{
	    String[] temp = new String[sounds.size()];
	    
	    int index = 0;
	    for (HashMap.Entry<String, File> entry : sounds.entrySet()) 
	    {
	        temp[index] = entry.getKey();
	        index++;
	    }
	    
	    return temp;
	}
	
	public boolean isPlaying()
	{ return clip.isRunning(); }
	
	//Stops the current track playing. If no track is playing, a MusicHandlerException is thrown.
	public void stop() throws MusicHandlerException
	{
		if (clip.isRunning())
			clip.stop();
		else
			throw new MusicHandlerException("Nothing is playing");
	}
	
	 // *********************  Public Sub-Classes  *********************
	
	public class MusicHandlerException extends Exception
	{
	    public MusicHandlerException(String message)
	    {
	        super(message);
	    }
	}
}
