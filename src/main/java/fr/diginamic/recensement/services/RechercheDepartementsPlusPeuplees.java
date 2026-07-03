package fr.diginamic.recensement.services;

import java.util.*;

import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.DepartementException;
import fr.diginamic.recensement.exceptions.RecensementException;
import fr.diginamic.recensement.exceptions.ValidationException;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Affichage des N départements les plus peuplés
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheDepartementsPlusPeuplees extends MenuService {

	@Override
	public void traiter(Recensement recensement, Scanner scanner) throws RecensementException {

		System.out.println("Veuillez saisir un nombre de départements:");
		String nbDeptsStr = scanner.nextLine();

		if (!NumberUtils.isDigits(nbDeptsStr)) {
			throw new ValidationException("Le nombre de départements doit être un entier.");
		}

		int nbDepts = Integer.parseInt(nbDeptsStr);

		if (nbDepts <= 0) {
			throw new ValidationException("Le nombre de départements doit être supérieur à 0.");
		}

		List<Ville> villes = recensement.getVilles();
		Map<String, Departement> mapDepts = new HashMap<>();

		for (Ville ville : villes) {

			Departement departement = mapDepts.get(ville.getCodeDepartement());
			if (departement == null) {
				departement = new Departement(ville.getCodeDepartement());
				mapDepts.put(ville.getCodeDepartement(), departement);
			}
			departement.addVille(ville);
		}

		List<Departement> departements = new ArrayList<Departement>();
		departements.addAll(mapDepts.values());

		Collections.sort(departements, new EnsemblePopComparateur(false));

		if (nbDepts > departements.size()) {
			throw new DepartementException("Il n'existe que " + departements.size() + " départements." );
		}

		for (int i = 0; i < nbDepts; i++) {
			Departement departement = departements.get(i);
			System.out.println(
					"Département " + departement.getCode() + " : " + departement.getPopulation() + " habitants.");
		}

	}

}
