import java.util.Scanner;

public class SistemaDeGestão {
	
	static final int MAX = 30;
	final int ADM_ID = 100;

	static String[] sala1 = new String[MAX];
	static String[] sala2 = new String[MAX];
	
	static String[] lab1 = new String[MAX];
	static String[] lab2 = new String[MAX];
	
	static String[] aud = new String[MAX];
	
	static String[] proj1 = new String[MAX];
	static String[] proj2 = new String[MAX];
	
	static String[] usuarios = new String[MAX];
	static int[] ocupacao = new int[MAX];
	static int[] privilegios = new int[MAX];
	static int[] id = new int[MAX];
		
	static int verificacao(String nome, int numUsers, int Id, int ocup) {
		for(int i = 0; i<numUsers; ++i) {
			if(nome.equals(usuarios[i]) && Id == id[i] && ocup == ocupacao[i] && i!=numUsers-1) {
				usuarios[numUsers-1] = null;
				id[numUsers-1] = 0;
				ocupacao[numUsers-1] = 0;
				return 0;
			}
		}
		return 1;
	}
	
	static int cadastro(int num) {
		Scanner scan = new Scanner(System.in);
		int opcao;
		
		System.out.println("Digite seu nome");
		usuarios[num] = scan.nextLine();
		
		System.out.println("Selecione o tipo de cadastro\n\n1-PROFESSOR\n2-PESQUISADOR\n3-ALUNO");
		opcao = scan.nextInt();
		ocupacao[num] = opcao;
		
		if(opcao == 1 || opcao == 2) {
			privilegios[num] = 1;
		}else {
			privilegios[num] = 0;
		}
		
		System.out.println("Insira o ID");
		id[num] = scan.nextInt();
		
		if(verificacao(usuarios[num], num+1, id[num], ocupacao[num]) == 1) {
			System.out.println("CADASTRO CONCLUIDO COM SUCESSO");
			return num+1;
		}else {
			System.out.println("ERRO, USUARIO JA CADASTRADO");
			return num;
		}
		
	}
	
	static String[] alocacao1(String[] array, String nome, int priv) {
		Scanner scan = new Scanner(System.in);
		
		String atvdd = new String();
		System.out.println("Selecione a atividade\n1- Aula Tradicional\n2- Apresentação\n3- Laboratorio");
		int atv = scan.nextInt();
		
		int Conf=1;
		if(atv == 2) { 
			atvdd = "Apresentação";
		}else if(atv == 1 && priv == 1) {
			atvdd = "Aula Tradicional";
		}else if (atv == 3 && priv == 1) {
			atvdd = "Laboratorio";
		}else if((atv==1 || atv==3) && priv == 0) {
			System.out.println("ALUNOS SO PODEM ALOCAR PARA APRESENTAÇÃO");
			Conf = 0;
		}
		
		if(Conf==1) {
			int i=0;
			while(array[i]!=null)
				++i;
			
			String[] horario = new String[6];
			horario[0] = "7h30 - 9h10";
			horario[1] = "9h20 - 11h00";
			horario[2] = "11h10 - 12h50";
			horario[3] = "13h30 - 15h10";
			horario[4] = "15h20 - 17h00";
			horario[5] = "17h10 - 18h50";
			
			System.out.println("Insira a data (DD/MM)");
			String data = scan.next();
			
			System.out.println("Escolha o horário");
			for(i=0; i<6; ++i) {
				System.out.println((i+1)+" "+horario[i]);
			}
			int opcao = scan.nextInt();
			
			String aloc = "DATA: " +data + "\nHORÁRIO:  " + horario[opcao-1] + "\nRESPONSÁVEL: " + nome + "\nATIVIDADE: " + atvdd;
			
			int conf = 1;
			i=0;
			while(array[i]!=null) {
				if(aloc.equals(array[i])) {
					System.out.println("ERRO, ESSE HORARIO JA FOI ALOCADO!");
					conf = 0;
				}
				++i;
			}
						
			if(conf == 1) {
				System.out.println("Informações sobre as atividades");
				Scanner scan1 = new Scanner(System.in);
				String info = scan1.nextLine();
				array[i] = aloc + "\nINFORMAÇÕES: " + info;
				System.out.println("ALOCADO COM SUCESSO");
			}
		}
		return array;
	}
	
	static void alocacao(int numUsers){
		
		System.out.println("Entre com seu ID");
		Scanner scan = new Scanner(System.in);
		int Id = scan.nextInt();
		int conf=0;
		int index=-1;
		
		for(int i=0; i<numUsers; ++i) {
			if(id[i] == Id) {
				conf = 1;	
				index = i;
			}
		}
		
		int opcao;
		if(conf == 1) {
		
			System.out.println("O que você deseja alocar?\n\n1-Sala\n2-Laboratório\n3-Auditório\n4-Projetores");
			opcao = scan.nextInt();
			
			if(opcao == 1) {
				System.out.println("Escolha a sala\n1-Sala 1    2-Sala 2");
				opcao = scan.nextInt();
				
				if(opcao == 1) {
					sala1 = alocacao1(sala1, usuarios[index], privilegios[index]);
				}else {
					sala2 = alocacao1(sala2, usuarios[index], privilegios[index]);
				}
				
			}else if (opcao == 2) {
				System.out.println("Escolha o laboratorio\n1-Lab 1    2-Lab 2");
				opcao = scan.nextInt();
				
				if(opcao == 1) {
					lab1 = alocacao1(lab1, usuarios[index], privilegios[index]);
				}else {
					lab2 = alocacao1(lab2, usuarios[index], privilegios[index]);
				}
				
			}else if(opcao == 3) {
				aud = alocacao1(aud, usuarios[index], privilegios[index]);
			}else if(opcao == 4) {
				System.out.println("Escolha o Projetor\n1-Proj 1    2-Proj 2");
				opcao = scan.nextInt();				

				if(opcao == 1) {
					proj1 = alocacao1(proj1, usuarios[index], privilegios[index]);
				}else {
					alocacao1(proj2, usuarios[index], privilegios[index]);
				}
				
			}
			
		}else {
			System.out.println("Usuario não cadastrado, recomenda-se fazer o cadastro antes de realizar alocação");
		}
		
		
				
	}
	
	static void consulta(Scanner scan, int numUsers) {
		System.out.println("Selecione o tipo de Consulta\n1- Usuário\n2- Recurso");
		int opcao = scan.nextInt();
		String buscaU = new String();
		int buscaR;
		
		if(opcao == 1) {
			System.out.println("Digite o nome do usuário:");
			buscaU = scan.next();
			int conf= 0;
			
			for(int i=0; i<numUsers; ++i){
				if(buscaU.equals(usuarios[i])) {
					System.out.println("Usuario encontrado");
					System.out.print(usuarios[i] + " " + id[i] + " ");
					
					if(ocupacao[i] == 1) {
						System.out.println("Professor");
					}else if (ocupacao[i] == 2) {
						System.out.println("Pesquisador");
					}else if (ocupacao[i] == 3) {
						System.out.println("Aluno");
					}
					conf = 1;
				}
			}
			if(conf == 0)
				System.out.println("O USUARIO SOLICITADO NÃO SE ENCONTRA CADASTRADO");
		}else if(opcao == 2) {
			System.out.println("Selecione o recurso desejado:\n"
					+ "1-Sala 1\n"
					+ "2-Sala 2\n"
					+ "3-Laboratório 1\n"
					+ "4-Laboratório 2\n"
					+ "5-Auditório\n"
					+ "6-Projetor 1\n"
					+ "7-Projetor 2");
			buscaR = scan.nextInt();
			
			String[] recurso = new String[MAX];
			
			if(buscaR == 1) {
				recurso = sala1;
			}else if(buscaR == 2) {
				recurso = sala2;
			}else if(buscaR == 3) {
				recurso = lab1;
			}else if(buscaR == 4) {
				recurso = lab2;
			}else if(buscaR == 5) {
				recurso = aud;
			}else if(buscaR == 6) {
				recurso = proj1;
			}else if(buscaR == 7) {
				recurso = proj2;
			}
			
			int i=0;
			while(recurso[i]!=null) {
				System.out.println(recurso[i]);
				++i;
			}
			
			if(i==0)
				System.out.println("ESTE RECUSO AINDA NÃO POSSUI ALOCAÇÕES");			
		}		
	}
	
	static void relatorio(int numUsers) {
		System.out.println("RELATORIO\n");
		
		System.out.println("NUMERO ATUAL DE USUARIOS NO SISTEMA -> "+numUsers+"\n");
		System.out.println("NUMERO TOTAL DE RECURSOS -> 7\n");
		
		int numAloc=0;
		int i=0;
		System.out.println("SALA 1");
		while (sala1[i]!=null) {
			System.out.println((i+1)+") "+sala1[i]+"\n");
			++i;
		}
		numAloc += i;
		if(i==0)
			System.out.println("ESTE RECUSO AINDA NÃO POSSUI ALOCAÇÕES");
		System.out.println("\n");
				
		i=0;
		System.out.println("SALA 2");
		while (sala2[i]!=null) {
			System.out.println((i+1)+") "+sala2[i]+"\n");
			++i;
		}
		numAloc += i;
		if(i==0)
			System.out.println("ESTE RECUSO AINDA NÃO POSSUI ALOCAÇÕES");
		System.out.println("\n");
				
		i=0;
		System.out.println("LABORATÓRIO 1");
		while (lab1[i]!=null) {
			System.out.println((i+1)+") "+lab1[i]+"\n");
			++i;
		}
		numAloc += i;
		if(i==0)
			System.out.println("ESTE RECUSO AINDA NÃO POSSUI ALOCAÇÕES");
		System.out.println("\n");
				
		i=0;
		System.out.println("LABORATÓRIO 2");
		while (lab2[i]!=null) {
			System.out.println((i+1)+") "+lab2[i]+"\n");
			++i;
		}
		numAloc += i;
		if(i==0)
			System.out.println("ESTE RECUSO AINDA NÃO POSSUI ALOCAÇÕES");
		System.out.println("\n");
				
		i=0;
		System.out.println("AUDITÓRIO");
		while (aud[i]!=null) {
			System.out.println((i+1)+") "+aud[i]+"\n");
			++i;
		}
		numAloc += i;
		if(i==0)
			System.out.println("ESTE RECUSO AINDA NÃO POSSUI ALOCAÇÕES");
		System.out.println("\n");
				
		i=0;
		System.out.println("PROJETOR 1");
		while (proj1[i]!=null) {
			System.out.println((i+1)+") "+proj1[i]+"\n");
			++i;
		}
		numAloc += i;
		if(i==0)
			System.out.println("ESTE RECUSO AINDA NÃO POSSUI ALOCAÇÕES");
		System.out.println("\n");
				
		i=0;
		System.out.println("PROJETOR 2");
		while (proj2[i]!=null) {
			System.out.println((i+1)+") "+proj2[i]+"\n");
			++i;
		}
		numAloc += i;
		if(i==0)
			System.out.println("ESTE RECUSO AINDA NÃO POSSUI ALOCAÇÕES");
		System.out.println("\n");
		
		System.out.println("NUMERO TOTAL DE ALOCAÇÕES -> "+numAloc);
		
			
	}
		
	public static void main(String[] args) {
		
		int opcao;
		int numUsers = 0;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("O que deseja fazer?\n1- Cadastro\n2- Alocar\n3- Consultar\n4- Emitir Relatório\n\n0- SAIR");		
		opcao = scan.nextInt();
		
		while(opcao!=0) {
			if(opcao == 1) {
				numUsers = cadastro(numUsers);
			}else if(opcao == 2){
				alocacao(numUsers);
			}else if(opcao == 3) {
				consulta(scan, numUsers);
			}else if(opcao == 4) {
				relatorio(numUsers);
			}
			
			System.out.println("Deseja realizar outra operação?\n1- SIM         0- NÃO");
			opcao = scan.nextInt();
			if(opcao == 1) {
				System.out.println("O que deseja fazer?\n1- Cadastro\n2- Alocar\n3- Consultar\n4- Emitir Relatório\n0- SAIR");
				opcao = scan.nextInt();
			}
		}	
		System.out.println("SISTEMA ENCERRADO");
	}
}


