package fr.eni.tp.filmotheque.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.exception.BusinessException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/films")
@SessionAttributes({ "genresSession", "participantsSession" })
public class FilmController {

	private FilmService filmService;

	public FilmController(FilmService filmService) {
		this.filmService = filmService;
	}

	@GetMapping
	public String afficherFilms(Model model) {
		// System.out.println(this.filmService.consulterFilms());

		List<Film> films = this.filmService.consulterFilms();

		model.addAttribute("films", films);
		return "view-films";
	}

	@GetMapping("/detail")
	public String afficherUnFilm(@RequestParam("id") int id, Model model) {
		// System.out.println(this.filmService.consulterFilmParId(id));
		Film films = this.filmService.consulterFilmParId(id);
		model.addAttribute("films", films);

		return "view-films-detail";
	}

	@GetMapping("/creer")
	public String afficherCreationFilm(Model model) {
		model.addAttribute("film", new Film());
		return "view-films-creer";
	}

	@PostMapping("/creer")
	public String creerFilm(@Valid @ModelAttribute Film film, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "view-films-creer";
		} else {
			try {
				this.filmService.creerFilm(film);
				return "redirect:/films";
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getListeMessage().forEach(m-> {
					ObjectError error = new ObjectError("globalErrors", m);
					bindingResult.addError(error);
				});
				return "view-films-creer";
			}
		}
		
		
	}

	/**
	 * Méthode appelée une seule fois pour chaque utilisateur de l'application
	 * 
	 * @return
	 */
	@ModelAttribute("genresSession")
	public List<Genre> chargerGenresEnSession() {
		System.out.println("chargement des genres");
		return this.filmService.consulterGenres();
	}

	@ModelAttribute("participantsSession")
	public List<Participant> chargerParticipantsSession() {
		System.out.println("chargement des participants");
		return this.filmService.consulterParticipants();
	}
}
