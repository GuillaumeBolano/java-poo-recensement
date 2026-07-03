package fr.diginamic.recensement.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.RecensementException;
import fr.diginamic.recensement.exceptions.ValidationException;
import fr.diginamic.recensement.exceptions.VilleException;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;
import fr.diginamic.recensement.services.existence.RegionVerification;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Cas d'utilisation: affichage des N villes les plus peuplées d'une région
 * donnée
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheVillesPlusPeupleesRegion extends MenuService {

	@Override
	public void traiter(Recensement recensement, Scanner scanner) throws RecensementException {

		System.out.println("Veuillez saisir un nom de région:");
		String nomRegion = scanner.nextLine();

		System.out.println("Veuillez saisir un nombre de villes:");
		String nbVillesStr = scanner.nextLine();

		if (!NumberUtils.isDigits(nbVillesStr)) {
			throw new ValidationException("Le nombre de villes doit être un entier.");
		}

		int nbVilles = Integer.parseInt(nbVillesStr);

		if (nbVilles <= 0) {
			throw new ValidationException("le nombre de villes doit être supérieur à 0.");
		}

		List<Ville> villesRegions = new ArrayList<Ville>();

		List<Ville> villes = recensement.getVilles();

		RegionVerification.estReel(nomRegion, villes);

		for (Ville ville : villes) {
			if (ville.getNomRegion().toLowerCase().startsWith(nomRegion.toLowerCase())) {
				villesRegions.add(ville);
			}
		}

		Collections.sort(villesRegions, new EnsemblePopComparateur(false));

		if (nbVilles > villesRegions.size()) {
			throw new VilleException("Il n'existe que " + villesRegions.size() + " villes.");
		}

		System.out.println("Les " + nbVilles + " villes les plus peuplées de la région " + nomRegion + " sont :");
		if (villesRegions.size() > 0) {
			for (int i = 0; i < nbVilles; i++) {
				Ville ville = villesRegions.get(i);
				System.out.println(ville.getNom() + " : " + ville.getPopulation() + " habitants.");
			}
		}

	}

}
