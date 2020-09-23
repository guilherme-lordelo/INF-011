package logic;

import java.util.Random;

public class Monitoramento {
	
	private static Unidade[] units = new Unidade[10];
	
	
	private static Unidade newUnit(boolean mobilidade,String id, float abcissa, float ordenada,
			boolean video, boolean termometro, boolean co2, boolean ch4) {
		if(mobilidade)
			return new Euclidiana(id, abcissa, ordenada, video, termometro, co2, ch4);
		else
			return new Manhattan(id, abcissa, ordenada, video, termometro, co2, ch4);
	}
	
	public static String monitorar(float abcissa, float ordenada, boolean video, boolean
			termometro, boolean co2, boolean ch4) {
		
		Unidade unit = null;
		
		for(Unidade temp : units) {
			if(validar(temp, video, termometro, co2, ch4)) {
				if(unit== null)
					unit = temp;
				else if(unit.getDistancia(abcissa, ordenada)>temp.getDistancia(abcissa, ordenada))
					unit = temp;
			}
		}
		if(unit != null) {
			unit.setAbcissa(abcissa);
			unit.setOrdenada(ordenada);
			return unit.getId();
		}
		else return null;
	}
	
	private static boolean validar(Unidade unit, boolean video, boolean termometro, boolean co2, boolean ch4) {
		
		if((video && !unit.getVideo()) || (termometro && !unit.getTermometro()) || 
			(co2 && !unit.getCo2()) || (ch4 && !unit.getCh4()))
			return false;
		else
			return true;
	}
	
	public static void main(String args[] ) {
		
		Random rand = new Random();
		for(int i = 0;i<10;i++) {
		    
		    units[i]= newUnit(rand.nextBoolean(), Integer.toString(i),
							  rand.nextFloat()+(rand.nextFloat()*10)+(rand.nextFloat()*100),
							  rand.nextFloat()+(rand.nextFloat()*10)+(rand.nextFloat()*100),
							  rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean());
		    System.out.println(units[i]);
		}
		
		System.out.println();
		String s = monitorar(50, 50, true, true, false, false);
		if(s !=null)
			System.out.println("ID da unidade que atende os requisitos e"
								+ " se encontra na menor distancia efetiva: "+ s + ".");
		else
			System.out.println("Nenhuma unidade atende aos requisitos");
			

	}
}
