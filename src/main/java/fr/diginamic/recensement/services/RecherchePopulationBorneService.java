package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.RecensementException;
import fr.diginamic.recensement.exceptions.ValidationException;
import fr.diginamic.recensement.services.existence.DepartementVerification;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws RecensementException {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();

		System.out.println("Choisissez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine();
		if (!NumberUtils.isDigits(saisieMin)) {
			throw new ValidationException("La population minimum doit être un entier.");
		}

		int minVal = Integer.parseInt(saisieMin);
		
		System.out.println("Choisissez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine();
		if (!NumberUtils.isDigits(saisieMax)) {
			throw new ValidationException("La population maximum doit être un entier.");
		}

		int maxVal = Integer.parseInt(saisieMax);

		if (maxVal <= 0) {
			throw new ValidationException("La population maximum doit être supérieure à 0.");
		}

		if (maxVal < minVal) {
			throw new ValidationException("La population maximum ne peut pas être inférieure à la population minimum.");
		}

		int min = minVal * 1000;
		int max = maxVal * 1000;

		
		List<Ville> villes = rec.getVilles();

		DepartementVerification.estReel(choix, villes);

		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
					System.out.println(ville);
				}
			}
		}
	}

}
