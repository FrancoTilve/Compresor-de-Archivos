package imple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import huffman.def.BitWriter;
import huffman.def.Compresor;
import huffman.def.HuffmanInfo;
import huffman.def.HuffmanTable;
import huffman.util.Console;
import huffman.util.HuffmanTree;
import huffman.util.IntUtil;


public class CompresorImple implements Compresor
{
	int cantHojasPorcentaje=0;
	FileOutputStream fos;
	@Override
	public HuffmanTable[] contarOcurrencias(String filename) {
	    HuffmanTable[] arr = new HuffmanTable[256];
	    
	    Console consola= null;
		boolean tru = true;
		consola = consola.get(tru );

		boolean bPorcentaje = false;
		
		consola.print("Estado de las ocurrencias [          ]");
		consola.skipBkp(11);
	    
		File file= new File (filename);
		long largo=file.length();
		int cantEncontrados=0;
		int k=1;
	    for (int i = 0; i < 256; i++) {
	        arr[i] = new HuffmanTable();
	    }

	    
	    try (FileInputStream fis = new FileInputStream(filename)) {
	        int letra;
	        while ((letra = fis.read()) != -1) {
	        	if(arr[letra].getN() == 0) {
	        		cantHojasPorcentaje++;
	        	}
	            arr[letra].increment();	       
	            cantEncontrados++;
	            long porc=(cantEncontrados*100)/largo;
	            if(porc>=k*10) {
	            	consola.print("#");
					
					k++;
	            }
	        }
	        while(k<=9) {
				consola.print("#");
				k++;
			}
			
			consola.skipFwd();
			consola.println();
	    } 
	    catch (IOException e) {
	        e.printStackTrace(); 
	    }
	    
	    
	    return arr;
	}

	@Override
	public List<HuffmanInfo> crearListaEnlazada(HuffmanTable[] arr)
	{
		List<HuffmanInfo> lista= new LinkedList<HuffmanInfo>();
		
		
		
		for (int i=0; i<256; i++) {
			
			if(arr[i].getN()>0) {
				
				HuffmanInfo info = new HuffmanInfo();
				info.setC(i);
				info.setN(arr[i].getN());							
				lista.add(info);			
			}
		// Ordenar 
		lista.sort((a, b) -> Integer.compare(a.getN(), b.getN()));
		}
		return lista;
	}

	@Override public HuffmanInfo convertirListaEnArbol(List<HuffmanInfo> lista) 
	{ 
		
		Collections.sort(lista, HuffmanInfo.comparadorPorN); 
		while (lista.size() > 1) 
		{ 
			HuffmanInfo padre = new HuffmanInfo(); 
			HuffmanInfo info1 = lista.remove(0);
			HuffmanInfo info2 = lista.remove(0); 
			
			
			padre.setLeft(info1); 
			padre.setRight(info2); 
			padre.setN(info1.getN() + info2.getN()); 
			lista.add(padre); 
			Collections.sort(lista, HuffmanInfo.comparadorPorN); 
			
		} 
	return lista.remove(0); 
	}

	@Override
	public void generarCodigosHuffman(HuffmanInfo root, HuffmanTable[] arr)
	{
		int contHoj=0;
		HuffmanTree arbol= new HuffmanTree(root);
		StringBuffer codigo= new StringBuffer();
		HuffmanInfo hoja;
		Console consola= null;
		boolean tru = true;
		consola = consola.get(tru );

		boolean bPorcentaje = false;

		consola.print("Estado de codigos [          ]");
		consola.skipBkp(11);
		
		int j=1;
		while ((hoja = arbol.next(codigo)) != null) {

			long porc=(contHoj*100)/cantHojasPorcentaje;
			if(porc>=10*j) {
				consola.print("#");
				
				j++;
			}
			//obtengo codigo y lo pongo
	        arr[hoja.getC()].setCod(codigo.toString());
	        contHoj++;
	        
		}
		while(j<=9) {
			consola.print("#");
			j++;
		}
		consola.print("#");
		consola.skipFwd();
		consola.println();
	}

	@Override
	public long escribirEncabezado(String filename, HuffmanTable[] arr)
	{

	try
	{

		
	fos = new FileOutputStream(filename + ".huf");
	BitWriter bitWriter = Factory.getBitWriter();
	bitWriter.using(fos);

	Console consola= null;
	boolean tru = true;
	consola = consola.get(tru );

	boolean bPorcentaje = false;

	consola.print("Estado del encabezado [          ]");
	consola.skipBkp(11);

	int contadorDeChars = 0;
	int contadorDeLetras = 0;
	int k = 1;

	for(int i=0; i < arr.length; i++){

	if(arr[i].getN()>0){
	contadorDeChars++;

	long porcentajeActual = ((contadorDeChars * 100) / 1);

	if((10.00*k) <= porcentajeActual  && k < 10){
	consola.print("#");
	k++;
	}
	}
	contadorDeLetras = contadorDeLetras + arr[i].getN();
	}

	while(k <= 9) {
	consola.print("#");
	k++;
	}

	consola.print("#");
	consola.skipFwd();
	consola.println();

	if(contadorDeChars == 256) {
	fos.write(0);
	}
	else {
	fos.write(contadorDeChars);
	}

	for(int i=0; i < arr.length; i++){

	if(arr[i].getN() > 0) {

	fos.write(i);
	fos.write(arr[i].getCod().length());

	String codigo = arr[i].getCod();
	for(int j=0; j < arr[i].getCod().length(); j++)
	{
	int bit = codigo.charAt(j)-'0';
	bitWriter.writeBit(bit);
	}

	bitWriter.flush();
	}
	}

	IntUtil.write(contadorDeLetras,4,fos);

	}

	catch(Exception e){e.printStackTrace();}

	File archivo = new File(filename+".huf");
	return (int)archivo.length();
	}
	
	@Override
	public void escribirContenido(String filename, HuffmanTable[] arr)
	{
		try (FileInputStream fis = new FileInputStream(filename);
		         FileOutputStream fos = new FileOutputStream(filename + ".huf", true)) {

		        BitWriterImple bitWriter = new BitWriterImple();
		        bitWriter.using(fos);

		        Console consola= null;
		    	boolean tru = true;
		    	consola = consola.get(tru );

		    	boolean bPorcentaje = false;

		    	consola.print("Estado del contenido [          ]");
		    	consola.skipBkp(11);
		    	File file= new File(filename);
		    	long largo=file.length();
		    	int k=1;
		    	int cantEncontrados=0;
		        int byteValue;
		        while ((byteValue = fis.read()) != -1) {
		            // Obtener el código Huffman del byte leído
		            String codigo = arr[byteValue].getCod();
		            cantEncontrados++;
		    	            long porc=(cantEncontrados*100)/largo;
		    	            if(porc>=k*10) {
		    	            	consola.print("#");		    					
		    					k++;
		    	            }
		    	            if (codigo != null) {
		                for (char bit : codigo.toCharArray()) {
		                    bitWriter.writeBit(bit - '0'); // Convertir el carácter '0' o '1' a int 0 o 1
		                    
		                }
		            }
		        }
		        while(k<=9) {
					consola.print("#");
					k++;
				}
				
				consola.skipFwd();
				consola.println();
			    
		        // Vaciar cualquier bit restante
		        bitWriter.flush();
		    } catch (IOException e) {
		        e.printStackTrace();
		        throw new RuntimeException(e);
		    }
		
		}
	}
	


