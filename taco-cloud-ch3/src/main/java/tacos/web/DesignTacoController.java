package tacos.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.TacoOrder;
import tacos.data.IngredientRepository;
@Controller
@Slf4j
@RequestMapping("/design")
@SessionAttributes("tacoOrder")

public class DesignTacoController {
	
	
	@Autowired
	private IngredientRepository ingredientRepo;
	
	
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
	  Iterable<Ingredient> ingredients = ingredientRepo.findAll();
		
		Type [] types = Ingredient.Type.values();
		
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase() , filterByType(ingredients , type));
			//System.out.println(filterByType(ingredients , type));
		}
		
	}
	
	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order() {
		return new TacoOrder();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	@GetMapping
	public String showDesignForm() {
		return "design";
	}
	
	private List<Ingredient> filterByType(Iterable<Ingredient> ingredients , Type type){
		
		return StreamSupport.stream(ingredients.spliterator() , false).filter(x-> x.getType().equals(type)).collect(Collectors.toList());
		
	}
	
	@PostMapping
	public String processTaco(@Valid Taco taco   , Errors errors, @ModelAttribute TacoOrder tacoOrder) {
		
		if (errors.hasErrors()) return "design";
		log.info("Process Taco :  =======================================================>" , taco);
		
	
		//System.out.println("in processTaco   " + model.getAttribute("sauce"));
		System.out.println(taco);
		tacoOrder.addTaco(taco);
		
		return "redirect:/orders/current";
	}
	
	

}
