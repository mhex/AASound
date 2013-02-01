
	/*File AudioCapture01.java
	This program demonstrates the capture
	and subsequent playback of audio data.

	A GUI appears on the screen containing
	the following buttons:
	Capture
	Stop
	Playback

	Input data from a microphone is
	captured and saved in a
	ByteArrayOutputStream object when the
	user clicks the Capture button.

	Data capture stops when the user clicks
	the Stop button.

	Playback begins when the user clicks
	the Playback button.

	Tested using SDK 1.4.0 under Win2000
	**************************************/

	import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;

	  //This method generates a monaural tone
	  // consisting of the sum of three sinusoids.

	
	public class AASound  extends JFrame {

	  /**
		 * 
	  */
	private static final long serialVersionUID = 1L;
		
	boolean stopCapture = false;
	  ByteArrayOutputStream
	                 byteArrayOutputStream;
	  AudioFormat audioFormat;
	  TargetDataLine targetDataLine;
	  AudioInputStream audioInputStream;
	  SourceDataLine sourceDataLine;
	  
	  // Path of the wav file
      File wavFile = new File("3J18.wav");
	  
	  // >3J18:A|PDBID|CHAIN|SEQUENCE
	  StringBuffer sequence = new StringBuffer("MLKAGVHFGHQTRYWNPKMKPFIFGARNKVHIINLEKTVPMFNEALAELNKIASRKGKILFVGTKRAASEAVKDAALSCDQFFVNHRWLGGMLTNWKTVRQSIKRLKDLETQSQDGTFDKLTKKEALMRTRELEKLENSLGGIKDMGGLPDALFVIDADHEHIAIKEANNLGIPVFAIVDTNSDPDGVDFVIPGNDDAIRAVTLYLGAVAATVREGRSGQKVHPNGIRLGIVKPWNSTWFANTKEFADNLDSDFKVRQYLTKELAKASVSRIVIERPAKSIRVTIHTARPGIVIGKKGEDVEKLRKVVADIAGVPAQINIAEVRKPELDAKLVADSITSQLERRVMFRRAMKRAVQNAMRLGAKGIKVEVSGRLGGAEIARTEWYREGRVPLHTLRADIDYNTSEAHTTYGVIGVKVWIFKGEIARYLGPKLKLSRREGTDLFLKSGVRAIDTKCKIEQAPGQHGARKPRLSDYGVQLREKQKVRRIYGVLERQFRNYYKEAARLKGNTGENLLALLEGRLDNVVYRMGFGATRAEARQLVSHKAIMVNGRVVNIASYQVSPNDVVSIREKAKKQSRVKAALELAEQREKPTWLEVDAGKMEGTFKRKPERSDLSADINEHLIVELYSKELQEKLIAVNRVSKTVKGGRIFSFTALTVVGDGNGRVGFGYGKAREVPAAIQKAMEKARRNMINVALNNGTLQHPVKGVHTGSRVFMQPASEGTGIIAGGAMRAVLEVAGVHNVLAKAYGSTNPINVVRATIDGLENMNSPEMVAAKRGKMRHYEIVFMVHPDQSEQVPGMIERYTAAITGAEGKIHRLEDWGRRQLAYPINKLHKAHYVLMNVEAPQEVIDELETTFRFNDAVIRSMVMRTKHAVTEASPRRRVIGQRKILPDPKFGSELLAKFVNILMVDGKKSTAESIVYSALETLAQRSGKSELEAFEVALENVRPTVEVKSRRVGGSTYQVPVEVRPVRRNALAMRWIVEAARKRGDKSMALRLANELSDAAENKGTAVKKREDVHRMAEANKAFASMQDPIADMLTRIRNGQAANKAAVTMPSSKLKVAIANVLKEEGFIEDFKVEGDTKPELELTLKYFQGKAVVESIQRVSRPGLRIYKRKDELPKVMAGLGIAVVSTSKGVMTDRAARQAGLGGEIICYVANQYYGTGRRKSSAARVFIKPGNGKIVINQRSLEQYFGRETARMVVRQPLELVDMVEKLDLYITVKGGGISGQAGAIRHGITRALMEYDESLRSELRKAGFVTRDARQVERKKVGLRKARRRPQFSKRRIRIRLKAFDHRLIDQATAEIVETAKRTGAQVRGPIPLPTRKERFTVLISPHVNKDARDQYEIRTHLRLVDIVEPTEKTVDALMRLDLAAGVDVQISLRKQVSDGVAHIHASFNNTIVTITDRQGNALGWATAGGSGFRGSRKSTPFAAQVAAERCADAVKEYGIKNLEVMVKGPGPGRESTIRALNAAGFRITNITDVTPIPHNGCRPPKKRRVATVNQLVRKPRARKVAKSNVPALEACPQKRGVCTRVYTTTPKKPNSALRKVCRVRLTNGFEVTSYIGGEGHNLQEHSVILIRGGRVKDLPGVRYHTVRGALDCSGVKDRKQARSKYGVKRPKAARIAGINIPDHKHAVIALTSIYGVGKTRSKAILAAAGIAEDVKISELSEGQIDTLRDEVAKFVVEGDLRREISMSIKRLMDLGCYRGLRHRRGLPVRGQRTKTNARTRKGPRKPAKQSMKAREVKRVALADKYFAKRAELKAIISDVNASDEDRWNAVLKLQTLPRDSSPSRQRNRCRQTGRPHGFLRKFGLSRIKVREAAMRGEIPGLKKASWSLSTEATAKIVSEFGRDANDTGSTEVQVALLTAQINHLQGHFAEHKKDHHSRRGLLRMVSQRRKLLDYLKRKDVARYTQLIERLGLRRMVTIRLARHGAKKRPFYQVVVADSRNARNGRFIERVGFFNPIASEKEEGTRLDLDRIAHWVGQGATISDRVAALIKEVNKAAKIRTLQGRVVSDKMEKSIVVAIERFVKHPIYGKFIKRTTKLHVHDENNECGIGDVVEIRECRPLSKTKSWTLVRVVEKAVEIDYKDIATLKNYITESGKIVPSRITGTRAKYQRQLARAIKRARYLSLLPYTDRHRSLKKGPFIDLHLLKKVEKAVESGDKKPLRTWSRRSTIFPNMIGLTIAVHNGRQHVPVFVTDEMVGHKLGEFAPTRTYRNIKSAKKRAIQSEKARKHNASRRSMMRTFIKKVYAAIEAGDKAAAQKAFNEMQPIVDRQAAKGLIHKNKAARHKANLTAQINKLAIKVRENEPFDVALRRFKRSCEKAGVLAEVRRREFYEKPTTERKRAKASAVKMAPVLENRRARHDYEILETYEAGIALKGTEVKSLRAGKVDFTGSFARFEDGELYLENLYIAPYEKGSYANVDPRRKRKLLLHKHELRRLLGKVEQKGLTLVPLKIYFNERGYAKVLLGLARGKMAVKVEYDLKRLRNIGIAAHIDAGKTTTTERILYYTGRIHKIGEVHEGAATMDFMEQERERGITITAAVTTCFWKDHRINIIDTPGHVDFTIEVERSMRVLDGAIVVFDSSQGVEPQSETVWRQAEKYKVPRIAFANKMDKTGADLWLVIRTMQERLGARPVVMQLPIGREDTFSGIIDVLRMKAYTYGNDLGTDIREIPIPEEYLDQAREYHEKLVEVAADFDENIMLKYLEGEEPTEEELVAAIRKGTIDLKITPVFLGSALKNKGVQLLLDAVVDYLPSPLDIPPIKGTTPEGEVVEIHPDPNGPLAALAFKIMADPYVGRLTFIRVYSGTLTSGSYVYNTTKGRKERVARLLRMHANHREEVEELKAGDLGAVVGLKETITGDTLVGEDAPRVILESIEVPEPVIDVAIEPKTKADQEKLSQALARLAEEDPTFRVSTHPETGQTIISGMGELHLEIIVDRLKREFKVDANVGKPQVAYRETITKPVDVEGKFIRQTGGRGQYGHVKIKVEPLPRGSGFEFVNAIVGGVIPKEYIPAVQKGIEEAMQSGPLIGFPVVDIKVTLYDGSYHEVDSSEMAFKIAGSMAIKEAVQKGDPVILEPIMRVEVTTPEEYMGDVIGDLNARRGQILGMEPRGNAQVIRAFVPLAEMFGYATDLRSKTQGRGSFVMFFDHYQEVPKQVQEKLIKGQMLKAGVHFGHQTRYWNPKMKPFIFGARNKVHIINLEKTVPMFNEALAELNKIASRKGKILFVGTKRAASEAVKDAALSCDQFFVNHRWLGGMLTNWKTVRQSIKRLKDLETQSQDGTFDKLTKKEALMRTRELEKLENSLGGIKDMGGLPDALFVIDADHEHIAIKEANNLGIPVFAIVDTNSDPDGVDFVIPGNDDAIRAVTLYLGAVAATVREGRSGQKVHPNGIRLGIVKPWNSTWFANTKEFADNLDSDFKVRQYLTKELAKASVSRIVIERPAKSIRVTIHTARPGIVIGKKGEDVEKLRKVVADIAGVPAQINIAEVRKPELDAKLVADSITSQLERRVMFRRAMKRAVQNAMRLGAKGIKVEVSGRLGGAEIARTEWYREGRVPLHTLRADIDYNTSEAHTTYGVIGVKVWIFKGEIARYLGPKLKLSRREGTDLFLKSGVRAIDTKCKIEQAPGQHGARKPRLSDYGVQLREKQKVRRIYGVLERQFRNYYKEAARLKGNTGENLLALLEGRLDNVVYRMGFGATRAEARQLVSHKAIMVNGRVVNIASYQVSPNDVVSIREKAKKQSRVKAALELAEQREKPTWLEVDAGKMEGTFKRKPERSDLSADINEHLIVELYSKELQEKLIAVNRVSKTVKGGRIFSFTALTVVGDGNGRVGFGYGKAREVPAAIQKAMEKARRNMINVALNNGTLQHPVKGVHTGSRVFMQPASEGTGIIAGGAMRAVLEVAGVHNVLAKAYGSTNPINVVRATIDGLENMNSPEMVAAKRGKMRHYEIVFMVHPDQSEQVPGMIERYTAAITGAEGKIHRLEDWGRRQLAYPINKLHKAHYVLMNVEAPQEVIDELETTFRFNDAVIRSMVMRTKHAVTEASPRRRVIGQRKILPDPKFGSELLAKFVNILMVDGKKSTAESIVYSALETLAQRSGKSELEAFEVALENVRPTVEVKSRRVGGSTYQVPVEVRPVRRNALAMRWIVEAARKRGDKSMALRLANELSDAAENKGTAVKKREDVHRMAEANKAFASMQDPIADMLTRIRNGQAANKAAVTMPSSKLKVAIANVLKEEGFIEDFKVEGDTKPELELTLKYFQGKAVVESIQRVSRPGLRIYKRKDELPKVMAGLGIAVVSTSKGVMTDRAARQAGLGGEIICYVANQYYGTGRRKSSAARVFIKPGNGKIVINQRSLEQYFGRETARMVVRQPLELVDMVEKLDLYITVKGGGISGQAGAIRHGITRALMEYDESLRSELRKAGFVTRDARQVERKKVGLRKARRRPQFSKRRIRIRLKAFDHRLIDQATAEIVETAKRTGAQVRGPIPLPTRKERFTVLISPHVNKDARDQYEIRTHLRLVDIVEPTEKTVDALMRLDLAAGVDVQISLRKQVSDGVAHIHASFNNTIVTITDRQGNALGWATAGGSGFRGSRKSTPFAAQVAAERCADAVKEYGIKNLEVMVKGPGPGRESTIRALNAAGFRITNITDVTPIPHNGCRPPKKRRVATVNQLVRKPRARKVAKSNVPALEACPQKRGVCTRVYTTTPKKPNSALRKVCRVRLTNGFEVTSYIGGEGHNLQEHSVILIRGGRVKDLPGVRYHTVRGALDCSGVKDRKQARSKYGVKRPKAARIAGINIPDHKHAVIALTSIYGVGKTRSKAILAAAGIAEDVKISELSEGQIDTLRDEVAKFVVEGDLRREISMSIKRLMDLGCYRGLRHRRGLPVRGQRTKTNARTRKGPRKPAKQSMKAREVKRVALADKYFAKRAELKAIISDVNASDEDRWNAVLKLQTLPRDSSPSRQRNRCRQTGRPHGFLRKFGLSRIKVREAAMRGEIPGLKKASWSLSTEATAKIVSEFGRDANDTGSTEVQVALLTAQINHLQGHFAEHKKDHHSRRGLLRMVSQRRKLLDYLKRKDVARYTQLIERLGLRRMVTIRLARHGAKKRPFYQVVVADSRNARNGRFIERVGFFNPIASEKEEGTRLDLDRIAHWVGQGATISDRVAALIKEVNKAAKIRTLQGRVVSDKMEKSIVVAIERFVKHPIYGKFIKRTTKLHVHDENNECGIGDVVEIRECRPLSKTKSWTLVRVVEKAVEIDYKDIATLKNYITESGKIVPSRITGTRAKYQRQLARAIKRARYLSLLPYTDRHRSLKKGPFIDLHLLKKVEKAVESGDKKPLRTWSRRSTIFPNMIGLTIAVHNGRQHVPVFVTDEMVGHKLGEFAPTRTYRNIKSAKKRAIQSEKARKHNASRRSMMRTFIKKVYAAIEAGDKAAAQKAFNEMQPIVDRQAAKGLIHKNKAARHKANLTAQINKLAIKVRENEPFDVALRRFKRSCEKAGVLAEVRRREFYEKPTTERKRAKASAVKMAPVLENRRARHDYEILETYEAGIALKGTEVKSLRAGKVDFTGSFARFEDGELYLENLYIAPYEKGSYANVDPRRKRKLLLHKHELRRLLGKVEQKGLTLVPLKIYFNERGYAKVLLGLARGKMAVKVEYDLKRLRNIGIAAHIDAGKTTTTERILYYTGRIHKIGEVHEGAATMDFMEQERERGITITAAVTTCFWKDHRINIIDTPGHVDFTIEVERSMRVLDGAIVVFDSSQGVEPQSETVWRQAEKYKVPRIAFANKMDKTGADLWLVIRTMQERLGARPVVMQLPIGREDTFSGIIDVLRMKAYTYGNDLGTDIREIPIPEEYLDQAREYHEKLVEVAADFDENIMLKYLEGEEPTEEELVAAIRKGTIDLKITPVFLGSALKNKGVQLLLDAVVDYLPSPLDIPPIKGTTPEGEVVEIHPDPNGPLAALAFKIMADPYVGRLTFIRVYSGTLTSGSYVYNTTKGRKERVARLLRMHANHREEVEELKAGDLGAVVGLKETITGDTLVGEDAPRVILESIEVPEPVIDVAIEPKTKADQEKLSQALARLAEEDPTFRVSTHPETGQTIISGMGELHLEIIVDRLKREFKVDANVGKPQVAYRETITKPVDVEGKFIRQTGGRGQYGHVKIKVEPLPRGSGFEFVNAIVGGVIPKEYIPAVQKGIEEAMQSGPLIGFPVVDIKVTLYDGSYHEVDSSEMAFKIAGSMAIKEAVQKGDPVILEPIMRVEVTTPEEYMGDVIGDLNARRGQILGMEPRGNAQVIRAFVPLAEMFGYATDLRSKTQGRGSFVMFFDHYQEVPKQVQEKLIKGQ");	 
	  //StringBuffer sequence = new StringBuffer("GQKVHPNGIRLGIVKPWNSTWFANTKEFADNLDSDFKVRQYLTKELAKASVSRIVIERPAKSIRVTIHTARPGIVIGKKGEDVEKLRKVVADIAGVPAQINIAEVRKPELDAKLVADSITSQLERRVMFRRAMKRAVQNAMRLGAKGIKVEVSGRLGGAEIARTEWYREGRVPLHTLRADIDYNTSEAHTTYGVIGVKVWIFKGEI");
	  //StringBuffer sequence = new StringBuffer("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
	  
	  private byte[] tone(char aa){
		    //int channels = 1;//Java allows 1 or 2
		    //Each channel requires two 8-bit bytes per
		    // 16-bit sample.
		    double seconds = 1;
		    double sampleRate = 16000;
		    double freqMult = 10.0;
		    // Allowable 8000,11025,16000,22050,44100
		    byte audioData[] = new byte[(int)(sampleRate * seconds)];
		    
		    
		    double freq = (int)(Math.pow((double)aa / 10, 5) / 50);
		    //double freq = (int)(aa * freqMult);
		    
		    System.out.println(freq);
		    
		    double RAD = 2.0 * Math.PI;
		    
	        for ( int i=0; i < audioData.length; i++ ) {
	            audioData[i] = (byte)( Math.sin( RAD * freq / sampleRate * i ) * 127.0 );
	        }
		    
		    return(audioData);
		  }//end method tones

	  
	  
	  
	  public static void main(
	                        String args[]){
	    new AASound();
	  }//end main

	  public AASound(){//constructor
	    
	    final JButton playBtn =
	               new JButton("Playback");
	    
	    playBtn.setEnabled(true);

	    //Register anonymous listeners
	    
	    playBtn.addActionListener(
	      new ActionListener(){
	        public void actionPerformed(
	                        ActionEvent e){
	          //Play back all of the data
	          // that was saved during
	          // capture.
	          playAudio();
	        }//end actionPerformed
	      }//end ActionListener
	    );//end addActionListener()
	    getContentPane().add(playBtn);

	    getContentPane().setLayout(
	                     new FlowLayout());
	    setTitle("Capture/Playback Demo");
	    setDefaultCloseOperation(
	                        EXIT_ON_CLOSE);
	    setSize(250,70);
	    setVisible(true);
	  }//end constructor
	  
      private byte[] toTones(StringBuffer sequence) {
    	  
		  char[] aas = (String.valueOf(sequence)).toCharArray();
		  
		  ByteArrayOutputStream tones = new ByteArrayOutputStream( );
		  
		  for (char aa: aas) {
			  
			try {
				tones.write(tone(aa));
			} catch (IOException e) {
				e.printStackTrace();
			}
			  
		  }
		  
		  return tones.toByteArray();
		  
	  }
	  
	  private StringBuffer pumpUp(StringBuffer sequence) {
		  
		  StringBuffer sequenceUp = new StringBuffer();
		  
		  String[] aas = (String.valueOf(sequence)).split("");
		  
		  for (String aa: aas) {
			  
			  for (int i = 0; i < 10; i++) {
				  sequenceUp.append(aa);
			  }
			  
		  }
		  
		  return sequenceUp;
		  
	  }
	  
	  //This method plays back the audio
	  // data that has been saved in the
	  // ByteArrayOutputStream
	  private void playAudio() {
	    try{
	      //Get everything set up for
	      // playback.
	      //Get the previously-saved data
	      // into a byte array object.

	      
	      byte audioData[] = toTones(sequence);
	      
	      //Get an input stream on the
	      // byte array containing the data
	      InputStream byteArrayInputStream
	            = new ByteArrayInputStream(
	                            audioData);
	      AudioFormat audioFormat =
	                      getAudioFormat();
	      audioInputStream =
	        new AudioInputStream(
	          byteArrayInputStream,
	          audioFormat,
	          audioData.length/audioFormat.getFrameSize());
	      DataLine.Info dataLineInfo =
	                new DataLine.Info(
	                  SourceDataLine.class,
	                          audioFormat);
	      
	   
	   
	      // format of audio file
	      AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	      
	      AudioSystem.write(audioInputStream, fileType, wavFile);
	      
	      audioInputStream.reset();
	      
	      sourceDataLine = (SourceDataLine)
	                   AudioSystem.getLine(
	                         dataLineInfo);
	      sourceDataLine.open(audioFormat);
	      sourceDataLine.start();

	      //Create a thread to play back
	      // the data and start it
	      // running.  It will run until
	      // all the data has been played
	      // back.
	      Thread playThread =
	          new Thread(new PlayThread());
	      playThread.start();
	    } catch (Exception e) {
	      System.out.println(e);
	      System.exit(0);
	    }//end catch
	  }//end playAudio

	  //This method creates and returns an
	  // AudioFormat object for a given set
	  // of format parameters.  If these
	  // parameters don't work well for
	  // you, try some of the other
	  // allowable parameter values, which
	  // are shown in comments following
	  // the declarations.
	  private AudioFormat getAudioFormat(){
	    float sampleRate = 16000.0F;
	    //8000,11025,16000,22050,44100
	    int sampleSizeInBits = 16;
	    //8,16
	    int channels = 1;
	    //1,2
	    boolean signed = true;
	    //true,false
	    boolean bigEndian = true;
	    //true,false
	    return new AudioFormat(
	                      sampleRate,
	                      sampleSizeInBits,
	                      channels,
	                      signed,
	                      bigEndian);
	  }//end getAudioFormat

	//===================================//
	//Inner class to play back the data
	// that was saved.
	class PlayThread extends Thread{
	  byte tempBuffer[] = new byte[10000];

	  public void run(){
	    try{
	      int cnt;
	      //Keep looping until the input
	      // read method returns -1 for
	      // empty stream.
	      while((cnt = audioInputStream.
	        read(tempBuffer, 0,
	            tempBuffer.length)) != -1){
	        if(cnt > 0){
	          //Write data to the internal
	          // buffer of the data line
	          // where it will be delivered
	          // to the speaker.
	          sourceDataLine.write(
	                   tempBuffer, 0, cnt);
	        }//end if
	      }//end while
	      //Block and wait for internal
	      // buffer of the data line to
	      // empty.
	      sourceDataLine.drain();
	      sourceDataLine.close();
	    }catch (Exception e) {
	      System.out.println(e);
	      System.exit(0);
	    }//end catch
	  }//end run
	}//end inner class PlayThread
	//===================================//

	}//end outer class AudioCapture01.java

