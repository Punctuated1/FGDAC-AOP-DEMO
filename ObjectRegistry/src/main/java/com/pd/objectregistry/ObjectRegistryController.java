package com.pd.objectregistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ObjectRegistryController {
	@Autowired
	ObjectRegistryService objectRegistryService;
	
	@GetMapping("/")
	public String viewObjectRegistryHomePage(Model model) {
		System.out.println("Enter controller - viewObjectRegistryHomePage");
		model.addAttribute("listObjectRegistries", objectRegistryService.getAllObjectRegistryEntries());
		return "index";
	}
	
}