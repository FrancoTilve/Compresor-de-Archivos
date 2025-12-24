package imple;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

import huffman.def.BitReader;

public class BitReaderImple implements BitReader
{
	
	InputStream archivo; //creo el archivo
	Queue<Integer> bitsGuardados = new LinkedList<>(); // Agregar el bit a la cola
	@Override
	public void using(InputStream is)
	{
		archivo=is; //guardo
	}

	@Override
	public int readBit()
	{
		if(bitsGuardados.size()==0){
		//subo a la cola
		try
		{
			int byteLeido =archivo.read();
			
			if(byteLeido== -1) {
				return -1;
			}
			
			for (int i = 7; i >= 0; i--) { //* Guardo el byte, bit por bit, en la cola
                int bit = (byteLeido >> i) & 1; // Obtener el bit en la posición i
                bitsGuardados.add(bit); 
            }
			
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return bitsGuardados.poll();
	}
	
	@Override
	public void flush()
	{
		while (bitsGuardados.size()>0) {
			bitsGuardados.poll();
		}
	}
}

