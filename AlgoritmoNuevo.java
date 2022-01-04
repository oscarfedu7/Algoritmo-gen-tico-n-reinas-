
import java.util.*;
public class AlgoritmoNuevo{

//El método que implementa un algortimo genético, el cual recibe una población, y regresa o el arreglo que da una de las soluciones de las 8 reinas
// ó en este caso la cantidad de generaciones que tarda para encontrar una solución.
	public int Algoritmo(ArrayList<int[]> poblacion){ 
		int conteo=0;  //variable que determina cuantas generaciones han pasado
		boolean bol=true;
		while(bol){
			ArrayList<Integer> fitness=new ArrayList<Integer>();
			for(int i=0; i<poblacion.size(); i++){ //escojemos a los mejores
				fitness.add(i,getFitness(poblacion.get(i)));

			}
			//Selección   Parte en la cual se esocje al mejor de los individuos y de desecha al peor
			int max=0;
			int min=0;
			for(int j=1; j<fitness.size(); j++){
				if(fitness.get(j)<fitness.get(min))
					min=j;
			}
			poblacion.remove(min);
			fitness.remove(min);
			for(int j=1; j<fitness.size(); j++){
                                if(fitness.get(j)>fitness.get(max))
                                        max=j;
                        }
			int[] mejor=poblacion.get(max);
			poblacion.remove(max);
			fitness.remove(max);

			//Cruza  Se cruza el mejor individuo con los otros dos (pues en este algoritmo se considera que hay siempre una población de 4)
			int[] h1=Reproducir(mejor,poblacion.get(0));
			int[] h2=Reproducir(poblacion.get(0), mejor);
			int[] h3=Reproducir(mejor, poblacion.get(1));
			int[] h4=Reproducir(poblacion.get(1), mejor);
 
			//Mutación  hay una mutación aleatoria en tres de los individuos
			poblacion.clear();
			fitness.clear();
			poblacion.add(h1);  poblacion.add(h2); poblacion.add(h3); poblacion.add(h4);
			for(int k=0; k<(poblacion.size()-1);k++){
				int indice=(int)(Math.random()*8);
				int azar=(int)(Math.random()*8);
				poblacion.get(k)[indice]=azar;
			}

			//Aquí se checa si ya se encontró una solcioón de alguno de los 4 individuos y se regresa
			// ya sea conteo o el arreglo de la solución en caso de ser solución
			for(int k=0; k<poblacion.size();k++){
				if(isArreglo(poblacion.get(k))){
				//	System.out.println("Conteo1: "+conteo);
				//	return poblacion.get(k);
					return conteo;
				}
			}

			conteo++;
		}
	//	int[] h={1,2,3,4,5,6,7,0};
	//	return h;
		return 0;

	}


	//Metodo que reproduce al hijo (la hora feliz).
	public int[] Reproducir(int[] p1, int[] p2){ 
		int[] hijo=new int[8];
		for(int i=0;i<4;i++){
			hijo[i]=p1[i];
		}
		for(int i=4;i<8;i++){
                        hijo[i]=p2[i];
                }
		return hijo;
	}

	//metodo que checa la cantidad de ataques que hay en un individuo, cantidad es = 0, ya encontramos una solcuión, y como hay
	// máximo 28 atques posibles, se regresa 28-cantidad. Etso para saber cual individuo es más fuerte o más débil.
	public int getFitness(int[] individuo){
		int cantidad=0;
		int num=0;
		for(int i=0; i<7;i++){
			num=individuo[i];
			for(int j=i+1;j<8;j++){
				int p=j-i;
				int p1= num+p;
				int p2= num-p;
				if((individuo[j]==num) || (p1==individuo[j]) || (p2==individuo[j])){
					cantidad++;
				}
			}
		}

		return (28-cantidad);


	}

	// Es muy parecido a getFitness, pero este únicamente checa si ya se encontró una solución o no, por lo que es más rápido y práctico en ciertos casos.
	public boolean isArreglo(int[] individuo){
                int num=0;
                for(int i=0; i<7;i++){
                        num=individuo[i];
                        for(int j=i+1;j<8;j++){
                                int p=j-i;
                                int p1= num+p;
                                int p2= num-p;
                                if((individuo[j]==num) || (p1==individuo[j]) || (p2==individuo[j])){
                                        return false;
                                }
                        }
                }

                return true;

        }

	//Un algoritmo aleatorio pero inteligente, porque nunca permite que se repitan números
	public int Aleatorio(){
		int[] a=new int[8];
		int conteo=0;
		boolean bol=true;
		for(int j=0;j<a.length; j++){
                     	a[j]=j;
                }
		while(bol){
			conteo++;
			if(isArreglo(a)){
			//	System.out.println("Conteo2: "+conteo);
                	    	//return a;
				return conteo;
			}
			else{ //Hacer arreglo
				for(int i=0;i<20;i++){
					int azar1=(int)(Math.random()*8);
					int azar2=(int)(Math.random()*8);
					int var=a[azar1];
					a[azar1]=a[azar2];
					a[azar2]=var;
				}
			}
		}
		//int[] arreglo={1,2,3,4,5,6,7,0};
               // return arreglo;
		return 0;
	}

	//Algoritmo aleatroio chafa, pues si se pueden repetir números.
	public int AleatorioChafa(){
		int[] a={0,1,2,3,4,5,6,7};
                int conteo=0;
                boolean bol=true;
                while(bol){
                        conteo++;
                        if(getFitness(a)==28){
                                //System.out.println("Conteo2: "+conteo);
                               // return a;
				return conteo;
                        }
                        else{
                                for(int i=0;i<a.length;i++){
                                        int azar=(int)(Math.random()*8);
                                        a[i]=azar;
                                }
                        }
                }
                //int[] arreglo={1,2,3,4,5,6,7,0};
                //return arreglo;
		return 0;
        }



	public static void main(String[] args){
		AlgoritmoNuevo al=new AlgoritmoNuevo();
		//usa algoritmo genético
		int prom=0;
		for(int i=0; i<100; i++){
			int[][] h=new int[4][8];
			for(int a=0;a<4; a++){
				for(int b=0;b<8;b++){
                        		h[a][b]=b;
        			} 
			}
			 for(int a=0;a<4; a++){		//Se crea una población de manera aleatoria donde no se repiten números.
	                        for(int b=0;b<20;b++){
                                        int azar1=(int)(Math.random()*8);
                                        int azar2=(int)(Math.random()*8);
                                        int var=h[a][azar1];
                                        h[a][azar1]=h[a][azar2];
                                        h[a][azar2]=var;
                                }

        	        }
		
			ArrayList<int[]> poblacion=new ArrayList<int[]>();
			for(int a=0;a<4;a++){
				poblacion.add(h[a]);
			}
			int reinas=al.Algoritmo(poblacion);
			prom=prom+reinas;
		}
		prom=prom/100;
		System.out.println("prom Alg. genético: "+prom);
	

		//muestra el resultado de algoritmo genético
	/**	for(int i=0; i<reinas.length;i++){
                        System.out.print(reinas[i]+", ");
                }
                System.out.println();**/

		//Muestra cuanto tiempo tardó el algoritmo
	/**	long endTime=System.nanoTime();
		double tiempo=(endTime-startTime)/1000000;
		System.out.println(tiempo);**/

		int prom2=0;
		for(int i=0; i<100; i++){
			int reinas2=al.Aleatorio();
			prom2=prom2+reinas2;
		}
		prom2=prom2/100;
		System.out.println("prom Aleatorio chido: "+prom2);


/**		for(int i=0; i<reinas2.length;i++){
                        System.out.print(reinas2[i]+", ");
                }
                System.out.println();**/
		
		int prom3=0;
                for(int i=0; i<100; i++){
                        int reinas3=al.AleatorioChafa();
                        prom3=prom3+reinas3;
                }
                prom3=prom3/100;
                System.out.println("prom Aleatorio chafa: "+prom3);


		
	}

}





