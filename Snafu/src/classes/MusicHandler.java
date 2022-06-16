package classes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.sampled.*;

/*
 * MusicHandler.java
 * 
 * A class that allows for Music to be played in the program.
 * 
 * A track is just a Clip. 
 * 
 * Each MusicHandler object will be able to play one track at a time.
 * 
 * If a track is played while another one is playing, the current
 * track being played will be stopped and overwritten.
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
    		
    		// Removes the File's type by finding the index of '.' Example: Music.wav becomes Music
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
    		
    		// Removes the File's type by finding the index of '.' Example: Music.wav becomes Music
    		int indexOfFileType = name.indexOf('.');
    		name = name.substring(0, indexOfFileType);
    		
    		sounds.put(name, musicFile);
    	}
    }
    
    public MusicHandler(File musicFile)
    {
    	sounds = new HashMap<>();
    	
    	String name = musicFile.getName();
    	
    	// Removes the File's type by finding the index of '.' Example: Music.wav becomes Music
    	int indexOfFileType = name.indexOf('.');
    	name = name.substring(0, indexOfFileType);
    	
    	sounds.put(name, musicFile);
    }
	
    // *********************  Public Methods  *********************
    
    /**
     * 
     * Plays a certain track based on the key given.
     * 
     * When the track is located and the clip is null, an AudioInputStream will be used
     * and will provide the clip variable with the clips in the 
     * AudioInputStream. 
     * 
     * It will stop the current track playing and add the
     * new audio clip. 
     * 
     * If no track exists, a MusicHandlerException
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
			    // Starts the directory at '/Resources' by cutting off all values before its index. 
			    int indexOfResources = sounds.get(key).getPath().indexOf("/Resources");
			    String path = sounds.get(key).getPath().substring(indexOfResources);
			    
			    // Gets the track by finding its URL.
				stream = AudioSystem.getAudioInputStream(getClass().getResource(path));
				
			} catch (UnsupportedAudioFileException e) {
			    
				e.printStackTrace();
			
			} catch (IOException e) {
			    
				e.printStackTrace();
			}
			
			if (clip == null)  
			{
    			try {
    			    
    				clip = AudioSystem.getClip();
    				
    			} catch (LineUnavailableException e) {
    				e.printStackTrace();
    			}
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
        name = name.substring(0, indexOfFileType); // Gets rid of the file's type. Example: Music.wav becomes Music.
        
        sounds.put(name, musicFile);
    }
	
	// Returns an array of Strings that contains every music track currently in the MusicHandler.
	public String[] getMusicTrackNames()
	{
	    String[] temp = new String[sounds.size()];
	    
	    int index = 0;
	    for (HashMap.Entry<String, File> entry : sounds.entrySet()) 
	    {
	        temp[index] = entry.getKey();
	        index++;
	    }
	    
	    sort(temp); // Sorts them in alphabetical order.
	    
	    return temp;
	}
	
	//Stops the current track playing. If no track is playing, a MusicHandlerException is thrown.
	public void stop() throws MusicHandlerException
	{
		if (clip.isRunning())
			clip.stop();
		else
			throw new MusicHandlerException("Nothing is playing");
	}
	
	public boolean isPlaying()
    { return clip.isRunning(); }
	
	// *********************  Private Methods  *********************
    
    private void sort(String[] arr) // Uses an Insertion Sort algorithm 
    {
        String temp = "";
        
        for (int i = 1, j = 0; i < arr.length; i++) 
        { 
            temp = arr[i];
            
            j = i - 1;
            
            while (j >= 0) 
            {
              if (temp.compareTo(arr[j]) > 0) 
                break;
              
              arr[j + 1] = arr[j];
              
              j--;
            }
            
            arr[j + 1] = temp;
        }
    }
	
	 // *********************  Public Sub-Classes  *********************
	
	@SuppressWarnings("serial")
    public class MusicHandlerException extends Exception // Used to prevent Clip errors from occurring. 
	{
	    public MusicHandlerException(String message)
	    {
	        super(message);
	    }
	}
}
