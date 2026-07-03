package fr.diginamic.recensement;

import java.util.InputMismatchException;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.exceptions.*;
import fr.diginamic.recensement.services.*;
import fr.diginamic.recensement.utils.RecensementUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Application de traitement des données de recensement de population
 *
 * @param args
 */
public class Application {

	/**
	 * Point d'entrée
	 * 
	 * @param args arguments (non utilisés ici)
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String filePath = ClassLoader.getSystemClassLoader().getResource("recensement.csv").getFile();
		Recensement recensement = RecensementUtils.lire(filePath);

		if (recensement == null) {
			System.out.println("L'application doit s'arrétée en raison d'une erreur d'exécution.");
			System.exit(-1);
		}

		// Menu
		int choix = 0;
		do {

			// Affichage du menu
			afficherMenu();

			// Poser une question à l'utilisateur
			String choixMenu = scanner.nextLine();

			try {
				if (!NumberUtils.isDigits(choixMenu)) {
					throw new InputMismatchException("Le choix doit être un entier.");
				}

				// Conversion du choix utilisateur en int
				choix = Integer.parseInt(choixMenu);
			} catch (InputMismatchException e) {
				System.out.println("Le choix doit être un entier.");
				continue;
			}

			// On exécute l'option correspondant au choix de l'utilisateur
			switch (choix) {
			case 1:
				executerService(new RecherchePopulationVilleService(), recensement, scanner);
                break;
			case 2:
				executerService(new RecherchePopulationDepartementService(), recensement, scanner);
                break;
			case 3:
				executerService(new RecherchePopulationRegionService(), recensement, scanner);
                break;
			case 4:
				executerService(new RecherchePopulationBorneService(), recensement, scanner);
				break;
			case 5:
				executerService(new RechercheVillesPlusPeupleesDepartement(), recensement, scanner);
                break;
			case 6:
				executerService(new RechercheVillesPlusPeupleesRegion(), recensement, scanner);
                break;
			case 7:
				executerService(new RechercheDepartementsPlusPeuplees(), recensement, scanner);
                break;
			case 8:
				executerService(new RechercheRegionsPlusPeuplees(), recensement, scanner);
                break;
			case 9:
				executerService(new RechercheVillesPlusPeupleesFrance(), recensement, scanner);
                break;
			}

		} while (choix != 99);

		scanner.close();

	}

	/**
	 * Affichage du menu
	 */
	private static void afficherMenu() {
		System.out.println("***** Recensement population *****");
		System.out.println("1. Rechercher la population d'une ville");
		System.out.println("2. Rechercher la population d'un département");
		System.out.println("3. Rechercher la population d'une région");
		System.out.println("4. Rechercher la population des villes d'un dept entre min et max");
		System.out.println("5. Rechercher les N plus grandes villes d'un département.");
		System.out.println("6. Rechercher les N plus grandes villes d'une région.");
		System.out.println("7. Rechercher les N plus grands départements de France.");
		System.out.println("8. Rechercher les N plus grandes régions de France.");
		System.out.println("9. Rechercher les N plus grandes villes de France.");
		System.out.println("99. Sortir");
	}

	private static void executerService(MenuService service, Recensement recensement, Scanner scanner) {
		try {
			service.traiter(recensement, scanner);
		} catch (RecensementException e) {
			System.out.println(e.getMessage());
		}
	}
}
