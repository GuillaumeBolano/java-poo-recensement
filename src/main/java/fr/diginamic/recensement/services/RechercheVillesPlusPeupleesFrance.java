package fr.diginamic.recensement.services;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.RecensementException;
import fr.diginamic.recensement.exceptions.ValidationException;
import fr.diginamic.recensement.exceptions.VilleException;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Cas d'utilisation: affichage des N villes les plus peuplées de France
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheVillesPlusPeupleesFrance extends MenuService {

	@Override
	public void traiter(Recensement recensement, Scanner scanner) throws RecensementException {

		System.out.println("Veuillez saisir un nombre de villes:");
		String nbVillesStr = scanner.nextLine();

		if (!NumberUtils.isDigits(nbVillesStr)) {
			throw new ValidationException("Le nombre de villes doit être un entier.");
		}

		int nbVilles = Integer.parseInt(nbVillesStr);

		if (nbVilles <= 0) {
			throw new ValidationException("le nombre de villes doit être supérieur à 0.");
		}

		List<Ville> villes = recensement.getVilles();

		if (nbVilles > villes.size()) {
			throw new VilleException("Il n'existe que " + villes.size() + " villes.");
		}

		System.out.println("Les " + nbVilles + " villes les plus peuplées de France sont :");
		Collections.sort(villes, new EnsemblePopComparateur(false));
		for (int i = 0; i < nbVilles; i++) {
			Ville ville = villes.get(i);
			System.out.println(ville.getNom() + " : " + ville.getPopulation() + " habitants.");
		}
	}

}
