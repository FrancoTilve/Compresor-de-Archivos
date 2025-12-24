package imple;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

import huffman.def.BitWriter;

public class BitWriterImple implements BitWriter 
{
	OutputStream archivo; //creo el archivo
	Queue<Integer> bitsGuardados = new LinkedList<>();
	
	
    @Override
    public void using(OutputStream os)
    {
    	archivo = os;
    }

    @Override
    public void writeBit(int bit) 
    {
    	bitsGuardados.add(bit);
    	if (bitsGuardados.size()==8) {
    		byte result=0;
    		while(bitsGuardados.size() > 0){ //* Pongo los bit en un byte
                int actualBit = bitsGuardados.poll();
                result=(byte) ((result<< 1) | actualBit);
            }
    		
    		try
			{
				archivo.write(result);
			}
			catch(IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
    	}
    
        
    @Override
    public void flush() 
    {
    	if(bitsGuardados.size()>0) {
    		while(bitsGuardados.size()<8) {
    			bitsGuardados.add(0);
    		}
    	byte result = 0;
    	while(bitsGuardados.size() > 0){ //* Pongo los bit en un byte
            int actualBit = bitsGuardados.poll();
            result=(byte) ((result<< 1) | actualBit);
        }
    	
			try
			{
				archivo.write(result);
			}
			catch(IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    	
    }
}
}
