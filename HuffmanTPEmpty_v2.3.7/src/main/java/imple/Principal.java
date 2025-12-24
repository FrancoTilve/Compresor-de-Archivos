package imple;

import java.util.List;
import java.util.Scanner;

import huffman.def.Compresor;
import huffman.def.Descompresor;
import huffman.def.HuffmanInfo;
import huffman.def.HuffmanTable;
import huffman.util.Console;
public class Principal
{

	    public static void main(String[] args) {
	    	
	        Console consola = null;
	        consola = consola.get(true);
	        consola.println("Pasame el COSO");
	        	        
	        String nomArchivo = consola.fileExplorer();
	        if(nomArchivo==null) {
	        	consola.closeAndExit();
	        }
	        
	       if(nomArchivo.endsWith(".huf")) {
	    	   String resultado = nomArchivo.substring(0, nomArchivo.length() - 4);
	    	   Descompresor dc= Factory.getDescompresor();
	    	   HuffmanInfo arbol = new HuffmanInfo();
	    	   long n=dc.recomponerArbol(resultado,arbol);
	    	   dc.descomprimirArchivo(arbol,n,resultado);
	    	   consola.println("se termino de descomprimir");
	       }
	       else {
	    	   Compresor cp = Factory.getCompresor();	    	   
	    	   HuffmanTable[] ocurrencias=cp.contarOcurrencias(nomArchivo);
	    	   List<HuffmanInfo> lista=cp.crearListaEnlazada(ocurrencias);
	    	   HuffmanInfo root=cp.convertirListaEnArbol(lista);
	    	   cp.generarCodigosHuffman(root,ocurrencias);
	    	   cp.escribirEncabezado(nomArchivo,ocurrencias);
	    	   cp.escribirContenido(nomArchivo,ocurrencias);
	    	   consola.println("se termino de comprimir");
	       }
	        consola.println("aprete un botón para salir");
	        consola.readString(); 
	        consola.closeAndExit();
	        
	    }
	

}
